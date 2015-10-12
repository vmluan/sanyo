package com.sanyo.quote.web.controller;

import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sanyo.quote.domain.Encounter;
import com.sanyo.quote.domain.EncounterJson;
import com.sanyo.quote.domain.EncounterStatus;
import com.sanyo.quote.domain.Location;
import com.sanyo.quote.domain.LocationJson;
import com.sanyo.quote.domain.Product;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.EncounterService;
import com.sanyo.quote.service.LocationService;
import com.sanyo.quote.service.ProductService;
import com.sanyo.quote.service.ProjectService;
import com.sanyo.quote.service.RegionService;
/*
 * Controller for Encounter 
 */
@Controller
@RequestMapping(value = "/quotation")
public class Quotation {
	final Logger logger = LoggerFactory.getLogger(Quotation.class);
	@Autowired
	private EncounterService encounterService;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private ProductService productService;

	@RequestMapping(method = RequestMethod.GET)
	public String getQuotationPage(Model uiModel) {
		return "quotation/index";
	}
	
	//get all assigned products of a specific region. Based on that we know all items that assigned to a project
	@RequestMapping(value = "/{id}/addquotation", params = "form", method = RequestMethod.GET)
	public String showRegions(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		Region region = regionService.findById(id);
		Encounter encounter = new Encounter();
		if(region != null){
			encounter.setRegion(region);
		}
		uiModel.addAttribute("regionId", id);
		return "quotation/create";
	}
	@RequestMapping(value = "/getAssignedProductOfRegion", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getProductsJson(@RequestParam(value="regionId", required=true) String regionId
			, @RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		Region region = regionService.findByIdAndFetchEncountersEagerly(Integer.valueOf(regionId));
		Set<Encounter> encounters = region.getEncounters();
		String result = Utilities.jSonSerialization(encounters);
		return result;
	}
	//save encounter
	@RequestMapping(value = "/{id}/addquotation", params = "form", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void saveEncounters(@RequestBody final EncounterJson encounterJson, @PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		System.out.println("=================================== saving encounter");
		Set<LocationJson> locatonJsons = encounterJson.getLocations();
		if (locatonJsons.size() >0){
			java.util.Iterator<LocationJson> iterator = locatonJsons.iterator();
			while(iterator.hasNext()){
				LocationJson location = iterator.next();
				
			}
		}else{
			
		}
	}
	private void saveEncounter(EncounterJson encounterJson, LocationJson locationJson){
		Encounter encounter = new Encounter();
		if(locationJson != null){
			Location location = locationService.findById(locationJson.getLocationId());
			encounter.setLocation(location);
		}
		encounter.setActualQuantity(Float.valueOf(encounterJson.getActualQuantity()));
		encounter.setAllowance(Float.valueOf(encounterJson.getAllowance()));
		encounter.setAmount(Float.valueOf(encounterJson.getAmount()));
		encounter.setCost_Labour_Amount_USD(Float.valueOf(encounterJson.getCost_Labour_Amount_USD()));
		encounter.setCost_Mat_Amount_USD(Float.valueOf(encounterJson.getCost_Mat_Amount_USD()));
		encounter.setDiscount_rate(Float.valueOf(encounterJson.getDiscount_rate()));
		encounter.setEncounterTime(new Date());
		encounter.setImp_Tax(Float.valueOf(encounterJson.getImp_Tax()));
		encounter.setLabour(Float.valueOf(encounterJson.getLabour()));
		encounter.setMat_w_o_Tax_USD(Float.valueOf(encounterJson.getMat_w_o_Tax_USD()));
		encounter.setMat_w_o_Tax_VND(Float.valueOf(encounterJson.getMat_w_o_Tax_VND()));
		encounter.setOrder(Integer.valueOf(encounterJson.getOrderNo()));
		
		Product product = productService.findByCode(encounterJson.getProductCode());
		Region region = regionService.findById(Integer.valueOf(encounterJson.getRegionId()));
		encounter.setProduct(product); //set product
		encounter.setQuantity(Float.valueOf(encounterJson.getQuantity()));
		encounter.setRegion(region);
		encounter.setRemark(encounterJson.getRemark());
		encounter.setSpecial_Con_Tax(Float.valueOf(encounterJson.getSpecial_Con_Tax()));
		encounter.setStatus(EncounterStatus.PROCESSING);
		encounter.setSubcon_Profit(Float.valueOf(encounterJson.getSubcon_Profit()));
		encounter.setUnit_Price_After_Discount(Float.valueOf(encounterJson.getUnit_Price_After_Discount()));
		encounter.setUnit_Price_W_Tax_Labour(Float.valueOf(encounterJson.getUnit_Price_W_Tax_Labour()));
		encounter.setUnit_Price_W_Tax_Profit(Float.valueOf(encounterJson.getUnit_Price_W_Tax_Profit()));
		encounter.setUnitRate(Float.valueOf(encounterJson.getUnitRate()));
		encounter.setVAT(Short.valueOf(encounterJson.getvAT()));
		
	}
	
	//save encounter
	@RequestMapping(value = "/{id}/savequotation", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void saveEncounters2(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		System.out.println("=================================== saving encounter 2");
	}
	@RequestMapping(value = "/getAssignedLocationsJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getAssignedLocations(@RequestParam(value="regionId", required=true) String regionId
			, @RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		Region region = regionService.findById(Integer.valueOf(regionId));
		Project project = projectService.findByIdAndFetchLocationsEagerly(region.getProject().getProjectId());
		Set<Location> locatoins = project.getLocations();
		String result = Utilities.jSonSerialization(locatoins);
		return result;
	}
}
