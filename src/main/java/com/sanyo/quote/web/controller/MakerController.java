package com.sanyo.quote.web.controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanyo.quote.domain.Category;
import com.sanyo.quote.domain.Maker;
import com.sanyo.quote.domain.ProductGroupMaker;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.MakerService;
import com.sanyo.quote.service.ProjectService;

@Controller
@RequestMapping(value = "/makers")
public final class MakerController {
	final Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private MakerService makerService;
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = "/getMakersJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getMakersJson(@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		List<Maker> makers = makerService.findAll();
		String result = Utilities.jSonSerialization(makers);
		return result;
	}
	
	@RequestMapping(value = "/getAssignedMakersJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getAssignedMakersJson(@RequestParam(value="projectId", required=true) String projectId
			, @RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		String regionType =  httpServletRequest.getParameter("regionType");
		Project project = projectService.findByIdAndFetchMakers(Integer.valueOf(projectId));
		Set<ProductGroupMaker> makers = getCollection(project.getProductGroupMakers(), regionType);
		
		String result = Utilities.jSonSerialization(makers);
		return result;
	}
	private Set<ProductGroupMaker> getCollection(Set<ProductGroupMaker> makers, String regionType){
		Set<ProductGroupMaker> results = new HashSet<ProductGroupMaker>();
		Iterator<ProductGroupMaker> iterator = makers.iterator();
		while(iterator.hasNext()){
			ProductGroupMaker productGroupMaker = iterator.next();
			if(regionType != null && regionType.equalsIgnoreCase("ELEC")){ //get ELECTRICAL only
				Category category = productGroupMaker.getCategory().getParentCategory();
				if(category != null && category.getName().contains("ELECTRICAL BOQ")){
					results.add(productGroupMaker);
				}
				
			}else if(regionType != null && regionType.equalsIgnoreCase("MECH")){ //get ELECTRICAL only
				Category category = productGroupMaker.getCategory().getParentCategory();
				if(category != null && category.getName().contains("MECHANICAL BOQ")){
					results.add(productGroupMaker);
				}
			}
		}
		return results;
	}
}
