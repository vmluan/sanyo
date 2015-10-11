package com.sanyo.quote.web.controller;

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
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.EncounterService;
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
	//get all assigned products of a specific region. Based on that we know all items that assigned to a project
	@RequestMapping(value = "/{id}/addquotation", params = "form", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void saveEncounters(@RequestBody final EncounterJson encounterJson, @PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		System.out.println("=================================== saving encounter");;
	}

}
