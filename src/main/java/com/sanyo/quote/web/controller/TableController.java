package com.sanyo.quote.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanyo.quote.domain.Product;
import com.sanyo.quote.domain.TH_Category;
import com.sanyo.quote.domain.TH_Encounter;
import com.sanyo.quote.domain.TH_Table;
import com.sanyo.quote.domain.TH_TableStatus;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.CategoryService;
import com.sanyo.quote.service.EncounterService;
import com.sanyo.quote.service.ProductService;
import com.sanyo.quote.service.TableService;

@Controller
@RequestMapping(value = "/quanlyban")
public class TableController {
	final Logger logger = LoggerFactory.getLogger(TableController.class);

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private EncounterService encounterService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	//The method is to save a new table
	//@Transactional
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String saveNewTable(Model ciModel,@RequestBody final  TH_Table table,
			HttpServletRequest httpServletRequest) {
		Locale locale = LocaleContextHolder.getLocale();
		System.out.println("===========================================");
		List<TH_Encounter> encounters = table.getEncounters();
		long totalMoney = 0;
		
		// because i have changed the GUI to submit every order when user clicks on every drink.
		// we need to update that encounter to current existing table that is opening.
		String tableNumber = table.getTableNumber();
		TH_Table existingTable = null;
		List<TH_Table> existingTables = tableService.findOpeningTableByTableNumber(tableNumber);
		if (existingTables != null && existingTables.size() >0){
			existingTable = existingTables.get(0);
			existingTable.setOpenTime(new Date()); // set Open Time when
													// ordering the first
													// records
			existingTable.setStatus(TH_TableStatus.DRINKING);
			tableService.save(existingTable);
		}else{
			table.setEncounters(null);
			tableService.save(table);
		}
			
		if (encounters != null){
			for (TH_Encounter encounter : encounters){
			
				System.out.println("===========" + encounter.getQuantity());
				//Product product = productService.findById(encounter.getProduct().getProductID());
				Product product = productService.findByName(encounter.getProduct().getProductName());
				totalMoney = (encounter.getQuantity() * product.getProductPrice());
				encounter.setProduct(product);
				encounter.setEncounterTime(new Date());
				encounter.setProductPrice(product.getProductPrice());
				if(existingTable != null)
					encounter.setTable(existingTable);
				else
					encounter.setTable(table);
				encounterService.save(encounter);
				
			}
			if (existingTable != null) {
				existingTable.setEncounters(encounters);
				//existingTable.setTotalMoney(existingTable.getTotalMoney() + totalMoney);
				existingTable.setTotalMoney(totalMoney + existingTable.getTotalMoney());
				tableService.save(existingTable);
			} else {
				table.setEncounters(encounters);
				table.setOpenTime(new Date());
				table.setStatus(TH_TableStatus.DRINKING);
				table.setTotalMoney(totalMoney);
				httpServletRequest
						.setAttribute("sysDate", new Date().getTime());
				tableService.save(table);
			}
		}
		return "tables/new";
	}
	
	@Transactional
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
	public String updateTable(@PathVariable("id") Integer id, Model ciModel
			,@RequestBody final  TH_Table table
			, HttpServletRequest httpServletRequest) {
		System.out.println("====================================== save form");

		boolean isValid = false;
		boolean re_update = true;
		while (re_update) {
			try {
				isValid = savingTable( table);
				re_update = false;
				;
			} catch (StaleObjectStateException e) {
				System.out
						.println("========== Row was updated or deleted by another transaction");
				re_update = true;
			} catch (Exception e) {
				re_update = true;
				System.out
						.println("=============== Exception when saving table");

			}
		}
		if(!isValid){ // do not allow updating paid table
					 // Should show error message
			httpServletRequest.setAttribute("table_is_paid", true);
			System.out.println("======= table is paid");
			return "tables/updateerror";
		}
		httpServletRequest.setAttribute("sysDate", new Date().getTime());
		return "tables/update";
	}
	private boolean savingTable(TH_Table table){
		TH_Table existingTable = tableService.findById(table.getTableID());
		if(existingTable != null && existingTable.getStatus() != TH_TableStatus.DRINKING ){
			return false;
		}
		List<TH_Encounter> encounters = table.getEncounters();
		//update encounters. Actually, we dont update encounters. we just create new encounters and set them for the table
		// what happen with old encounter records? it will a garbage. Need to fix it!!!!!
		
		long totalMoney = 0;
		if (encounters != null && existingTable != null){
			
			totalMoney = existingTable.getTotalMoney();
			for (TH_Encounter encounter : encounters){
			
				Product product = productService.findByName(encounter.getProduct().getProductName());
				totalMoney = totalMoney + (encounter.getQuantity() * product.getProductPrice());
				encounter.setProduct(product);
				encounter.setEncounterTime(new Date());
				encounter.setTable(existingTable);
				encounter.setProductPrice(product.getProductPrice());
				encounterService.save(encounter);
				
			}
			//update table first
			
			
			
			existingTable.setTableNumber(table.getTableNumber());
			existingTable.setEncounters(encounters);
			existingTable.setTableName(table.getTableName());
			if(existingTable.getTotalMoney() == 0 || existingTable.getStatus() == null){
				existingTable.setOpenTime(new Date()); // set Open Time when
				// ordering the first drink
				existingTable.setStatus(TH_TableStatus.DRINKING);
				System.out.println("============ table acr = " + existingTable.getTableAcr() == null);
				if(existingTable.getTableAcr() == null)
					existingTable.setTableAcr(table.getTableAcr());
			}
			existingTable.setTotalMoney(totalMoney);
			if (table.getStatus() != null){
				if(table.getStatus() == TH_TableStatus.PAID)
					existingTable.setClosedTime(new Date());
				existingTable.setStatus(table.getStatus());
			}

		}
		
		System.out.println("=========== customer name is " + table.getCustomerName());
		existingTable.setCustomerName(table.getCustomerName());
		tableService.save(existingTable);
		return true;
		
	}
	
	@RequestMapping(value = "/{tableacr}", params = "tableacr", method = RequestMethod.GET)
	public String showTable(@PathVariable("tableacr") String tableNumber, Model uiModel, HttpServletRequest httpServletRequest) {
		
		List<TH_Table> tables = null;
		httpServletRequest.setAttribute("sysDate", new Date().getTime());
		
		String tableAcr = httpServletRequest.getParameter("tableacr") ;
		System.out.println("================= newtable = " + httpServletRequest.getParameter("newtable"));
		boolean isNewTable = Boolean.valueOf(httpServletRequest.getParameter("newtable"));
		
		System.err.println("========= tableAcr = " + tableAcr);
		
		List<TH_Category> categories = categoryService.findAll();
		uiModel.addAttribute("categories", categories);
		
		if (!isNewTable)
			tables = tableService.findTableBuyTableNumber(tableNumber);
		
		if (tables != null && tables.size() > 0) {

			System.out.println("==================== new method: table id = "
					+ tables.get(0).getTableID());

//			List<TH_Encounter> encounters = tables.get(0).getEncounters();

			uiModel.addAttribute("table", tables.get(0));
			tableAcr = tables.get(0).getTableAcr();
			httpServletRequest.setAttribute("tableAcr", tableAcr);
		}
		else{ 
			//There is no order in this table today. start creating the first one
			//OR create another table.
			System.out.println("============== new table today = " + tableNumber);
			httpServletRequest.setAttribute("tableNumber", tableNumber);
			tableAcr = tableNumber.replace(" ",  "")+"_" + String.valueOf(new Date().getTime());
			httpServletRequest.setAttribute("tableAcr", tableAcr);
			
			//create an empty table. It is to avoid creating many tables when user double or triple click on drinks
			TH_Table newTable = new TH_Table();
			newTable.setTableNumber(tableNumber);
			newTable.setTableAcr(tableAcr);
			newTable.setOpenTime(new Date());
			newTable.setStatus(TH_TableStatus.DRINKING);
			uiModel.addAttribute("table", newTable);
			
			tableService.save(newTable);
			
			return "tables/new";
		}
		
		return "tables/update";
	}
	@RequestMapping(value = "/tablelist", method = RequestMethod.GET)
	public String getListTable(){
		return "tablelist";
	}
	@RequestMapping(value = "/tablelistjson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getTablesJson(@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, @RequestParam(value="startdate", required=false) String startDateString
			, @RequestParam(value="enddate", required=false) String endDateString){
		Date startDate = null, endDate = null;
		if (startDateString != null && !startDateString.equals(""))
			startDate = Utilities.parseDate(startDateString);
		if (endDateString != null && !endDateString.equals("")){
			endDate = Utilities.parseDate(endDateString);
			endDate = Utilities.getLastTimeOfDate(endDate);
		}
		List<TH_Table> tables;
		if(startDate != null && endDate != null)
			tables =  tableService.findTableByDateRange(startDate, endDate);
		else
			tables = tableService.findAll();
		//exclude encounters from json serialization
		Iterator<TH_Table> iter = tables.iterator();
		while(iter.hasNext()){
			TH_Table table = iter.next();
			table.setEncounters(null);
			if(table.getTotalMoney() ==0)
				iter.remove();
		}
		String result = Utilities.jSonSerialization(tables);
		return result;
	}
	@RequestMapping(value = "/encounterlistjson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getEncountersJson(@RequestParam(value="tableid", required=true) String tableID
			, @RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, @RequestParam(value="startdate", required=false) String startDateString
			, @RequestParam(value="enddate", required=false) String endDateString){
		
		TH_Table table = tableService.findById(Integer.valueOf(tableID));
		List<TH_Encounter> encounters = table.getEncounters();
		for(TH_Encounter encounter : encounters){
			encounter.setTable(null);
		}
		String result = Utilities.jSonSerialization(encounters);
		return result;
	}
	// to filter products by categories
	@RequestMapping(value = "/findProductByCategories", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getProductList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "ids[]") int[] ids){
		System.out.println("============== findProductByCategories ");
		System.out.println(ids[0]);
		
		List<Integer> listIds = new ArrayList<Integer>();
		for(int i=0; i< ids.length; i++){
			listIds.add(ids[i]);
		}
		
		List<Product> products = new ArrayList<Product>();
		
		String allCates = request.getParameter("allCats");
		if(allCates != null && !allCates.equals("")){
			products = productService.findAll();
		}else{
			List<TH_Category> categories = categoryService.findByIds(listIds);
			for (TH_Category category : categories){
				List<Product> list = category.getProducts();
				for(Product product : list){
					if(!products.contains(product))
						products.add(product);
				}
			}
		}
		
		

		String productString = products.toString();

		productString = '{' + productString.substring(1,
				productString.length() - 1) + '}';
		
		return productString;
		
	}

}
