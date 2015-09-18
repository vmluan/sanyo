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
import com.sanyo.quote.domain.Category;
import com.sanyo.quote.domain.Encounter;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.ProjectStatus;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.CategoryService;
import com.sanyo.quote.service.EncounterService;
import com.sanyo.quote.service.ProductService;
import com.sanyo.quote.service.ProjectService;

@Controller
@RequestMapping(value = "/quanlyban")
public class ProjectController {
	final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private EncounterService encounterService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	//The method is to save a new table
	//@Transactional
//	@RequestMapping(params = "form", method = RequestMethod.POST)
//	public String saveNewTable(Model ciModel,@RequestBody final  Project table,
//			HttpServletRequest httpServletRequest) {
//		Locale locale = LocaleContextHolder.getLocale();
//		System.out.println("===========================================");
//		List<Encounter> encounters = table.getEncounters();
//		long totalMoney = 0;
//		
//		// because i have changed the GUI to submit every order when user clicks on every drink.
//		// we need to update that encounter to current existing table that is opening.
//		String tableNumber = table.getProjectName();
//		Project existingTable = null;
//		List<Project> existingTables = projectService.findOpeningTableByTableNumber(tableNumber);
//		if (existingTables != null && existingTables.size() >0){
//			existingTable = existingTables.get(0);
//			existingTable.setOpenTime(new Date()); // set Open Time when
//													// ordering the first
//													// records
//			existingTable.setStatus(ProjectStatus.PROCESSING);
//			projectService.save(existingTable);
//		}else{
//			table.setEncounters(null);
//			projectService.save(table);
//		}
//			
//		if (encounters != null){
//			for (Encounter encounter : encounters){
//			
//				System.out.println("===========" + encounter.getQuantity());
//				//Product product = productService.findById(encounter.getProduct().getProductID());
//				Product product = productService.findByName(encounter.getProduct().getProductName());
//				totalMoney = (encounter.getQuantity() * product.getProductPrice());
//				encounter.setProduct(product);
//				encounter.setEncounterTime(new Date());
//				encounter.setProductPrice(product.getProductPrice());
//				if(existingTable != null)
//					encounter.setProject(existingTable);
//				else
//					encounter.setProject(table);
//				encounterService.save(encounter);
//				
//			}
//			if (existingTable != null) {
//				existingTable.setEncounters(encounters);
//				//existingTable.setTotalMoney(existingTable.getTotalMoney() + totalMoney);
//				existingTable.setTotalMoney(totalMoney + existingTable.getTotalMoney());
//				projectService.save(existingTable);
//			} else {
//				table.setEncounters(encounters);
//				table.setOpenTime(new Date());
//				table.setStatus(ProjectStatus.PROCESSING);
//				table.setTotalMoney(totalMoney);
//				httpServletRequest
//						.setAttribute("sysDate", new Date().getTime());
//				projectService.save(table);
//			}
//		}
//		return "tables/new";
//	}
//	
//	@Transactional
//	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
//	public String updateTable(@PathVariable("id") Integer id, Model ciModel
//			,@RequestBody final  Project table
//			, HttpServletRequest httpServletRequest) {
//		System.out.println("====================================== save form");
//
//		boolean isValid = false;
//		boolean re_update = true;
//		while (re_update) {
//			try {
////				isValid = savingTable( table);
//				re_update = false;
//				;
//			} catch (StaleObjectStateException e) {
//				System.out
//						.println("========== Row was updated or deleted by another transaction");
//				re_update = true;
//			} catch (Exception e) {
//				re_update = true;
//				System.out
//						.println("=============== Exception when saving table");
//
//			}
//		}
//		if(!isValid){ // do not allow updating paid table
//					 // Should show error message
//			httpServletRequest.setAttribute("table_is_paid", true);
//			System.out.println("======= table is paid");
//			return "tables/updateerror";
//		}
//		httpServletRequest.setAttribute("sysDate", new Date().getTime());
//		return "tables/update";
//	}
////	private boolean savingTable(Project table){
////		Project existingTable = projectService.findById(table.getProjectId());
////		if(existingTable != null && existingTable.getStatus() != ProjectStatus.PROCESSING ){
////			return false;
////		}
////		List<Encounter> encounters = table.getEncounters();
////		//update encounters. Actually, we dont update encounters. we just create new encounters and set them for the table
////		// what happen with old encounter records? it will a garbage. Need to fix it!!!!!
////		
////		long totalMoney = 0;
////		if (encounters != null && existingTable != null){
////			
////			totalMoney = existingTable.getTotalMoney();
////			for (Encounter encounter : encounters){
////			
////				Product product = productService.findByName(encounter.getProduct().getProductName());
////				totalMoney = totalMoney + (encounter.getQuantity() * product.getProductPrice());
////				encounter.setProduct(product);
////				encounter.setEncounterTime(new Date());
////				encounter.setProject(existingTable);
////				encounter.setProductPrice(product.getProductPrice());
////				encounterService.save(encounter);
////				
////			}
////			//update table first
////			
////			
////			
////			existingTable.setProjectName(table.getProjectName());
////			existingTable.setEncounters(encounters);
////			existingTable.setTableName(table.getTableName());
////			if(existingTable.getTotalMoney() == 0 || existingTable.getStatus() == null){
////				existingTable.setOpenTime(new Date()); // set Open Time when
////				// ordering the first drink
////				existingTable.setStatus(ProjectStatus.PROCESSING);
////				System.out.println("============ table acr = " + existingTable.getProjectCode() == null);
////				if(existingTable.getProjectCode() == null)
////					existingTable.setProjectCode(table.getProjectCode());
////			}
////			existingTable.setTotalMoney(totalMoney);
////			if (table.getStatus() != null){
////				if(table.getStatus() == ProjectStatus.PAID)
////					existingTable.setClosedTime(new Date());
////				existingTable.setStatus(table.getStatus());
////			}
////
////		}
////		
////		System.out.println("=========== customer name is " + table.getCustomerName());
////		existingTable.setCustomerName(table.getCustomerName());
////		projectService.save(existingTable);
////		return true;
////		
////	}
//	
//	@RequestMapping(value = "/{tableacr}", params = "tableacr", method = RequestMethod.GET)
//	public String showTable(@PathVariable("tableacr") String tableNumber, Model uiModel, HttpServletRequest httpServletRequest) {
//		
//		List<Project> tables = null;
//		httpServletRequest.setAttribute("sysDate", new Date().getTime());
//		
//		String tableAcr = httpServletRequest.getParameter("tableacr") ;
//		System.out.println("================= newtable = " + httpServletRequest.getParameter("newtable"));
//		boolean isNewTable = Boolean.valueOf(httpServletRequest.getParameter("newtable"));
//		
//		System.err.println("========= tableAcr = " + tableAcr);
//		
//		List<Category> categories = categoryService.findAll();
//		uiModel.addAttribute("categories", categories);
//		
//		if (!isNewTable)
//			tables = projectService.findTableBuyTableNumber(tableNumber);
//		
//		if (tables != null && tables.size() > 0) {
//
//			System.out.println("==================== new method: table id = "
//					+ tables.get(0).getProjectId());
//
////			List<Encounter> encounters = tables.get(0).getEncounters();
//
//			uiModel.addAttribute("table", tables.get(0));
//			tableAcr = tables.get(0).getProjectCode();
//			httpServletRequest.setAttribute("tableAcr", tableAcr);
//		}
//		else{ 
//			//There is no order in this table today. start creating the first one
//			//OR create another table.
//			System.out.println("============== new table today = " + tableNumber);
//			httpServletRequest.setAttribute("tableNumber", tableNumber);
//			tableAcr = tableNumber.replace(" ",  "")+"_" + String.valueOf(new Date().getTime());
//			httpServletRequest.setAttribute("tableAcr", tableAcr);
//			
//			//create an empty table. It is to avoid creating many tables when user double or triple click on drinks
//			Project newTable = new Project();
//			newTable.setProjectName(tableNumber);
//			newTable.setProjectCode(tableAcr);
//			newTable.setOpenTime(new Date());
//			newTable.setStatus(ProjectStatus.PROCESSING);
//			uiModel.addAttribute("table", newTable);
//			
//			projectService.save(newTable);
//			
//			return "tables/new";
//		}
//		
//		return "tables/update";
//	}
//	@RequestMapping(value = "/tablelist", method = RequestMethod.GET)
//	public String getListTable(){
//		return "tablelist";
//	}
//	@RequestMapping(value = "/tablelistjson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
//	@ResponseBody
//	public String getTablesJson(@RequestParam(value="filterscount", required=false) String filterscount
//			, @RequestParam(value="groupscount", required=false) String groupscount
//			, @RequestParam(value="pagenum", required=false) Integer pagenum
//			, @RequestParam(value="pagesize", required=false) Integer pagesize
//			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
//			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
//			, @RequestParam(value="startdate", required=false) String startDateString
//			, @RequestParam(value="enddate", required=false) String endDateString){
//		Date startDate = null, endDate = null;
//		if (startDateString != null && !startDateString.equals(""))
//			startDate = Utilities.parseDate(startDateString);
//		if (endDateString != null && !endDateString.equals("")){
//			endDate = Utilities.parseDate(endDateString);
//			endDate = Utilities.getLastTimeOfDate(endDate);
//		}
//		List<Project> tables;
//		if(startDate != null && endDate != null)
//			tables =  projectService.findTableByDateRange(startDate, endDate);
//		else
//			tables = projectService.findAll();
//		//exclude encounters from json serialization
//		Iterator<Project> iter = tables.iterator();
//		while(iter.hasNext()){
//			Project table = iter.next();
//			table.setEncounters(null);
//			if(table.getTotalMoney() ==0)
//				iter.remove();
//		}
//		String result = Utilities.jSonSerialization(tables);
//		return result;
//	}
//	@RequestMapping(value = "/encounterlistjson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
//	@ResponseBody
//	public String getEncountersJson(@RequestParam(value="tableid", required=true) String tableID
//			, @RequestParam(value="filterscount", required=false) String filterscount
//			, @RequestParam(value="groupscount", required=false) String groupscount
//			, @RequestParam(value="pagenum", required=false) Integer pagenum
//			, @RequestParam(value="pagesize", required=false) Integer pagesize
//			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
//			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
//			, @RequestParam(value="startdate", required=false) String startDateString
//			, @RequestParam(value="enddate", required=false) String endDateString){
//		
//		Project table = projectService.findById(Integer.valueOf(tableID));
//		List<Encounter> encounters = table.getEncounters();
//		for(Encounter encounter : encounters){
//			encounter.setProject(null);
//		}
//		String result = Utilities.jSonSerialization(encounters);
//		return result;
//	}
//	// to filter products by categories
//	@RequestMapping(value = "/findProductByCategories", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
//	@ResponseBody
//	public String getProductList(HttpServletRequest request, HttpServletResponse response,
//			@RequestParam(value = "ids[]") int[] ids){
//		System.out.println("============== findProductByCategories ");
//		System.out.println(ids[0]);
//		
//		List<Integer> listIds = new ArrayList<Integer>();
//		for(int i=0; i< ids.length; i++){
//			listIds.add(ids[i]);
//		}
//		
//		List<Product> products = new ArrayList<Product>();
//		
////		String allCates = request.getParameter("allCats");
////		if(allCates != null && !allCates.equals("")){
////			products = productService.findAll();
////		}else{
////			List<Category> categories = categoryService.findByIds(listIds);
////			for (Category category : categories){
////				List<Product> list = category.getProducts();
////				for(Product product : list){
////					if(!products.contains(product))
////						products.add(product);
////				}
////			}
////		}
//		
//		
//
//		String productString = products.toString();
//
//		productString = '{' + productString.substring(1,
//				productString.length() - 1) + '}';
//		
//		return productString;
//		
//	}

}
