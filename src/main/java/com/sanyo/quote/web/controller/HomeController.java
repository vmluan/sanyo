/**
 * Copyright (c) 2015  osgicse group.
 * Filename   : HomeController.java
 * Description: 
 * @author    : Luan Vo
 * Created    : Sep 3, 2013
 */


package com.sanyo.quote.web.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sanyo.quote.service.ProjectService;

@Controller
@RequestMapping(value = "/")
public class HomeController extends BaseController{

	final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private ProjectService projectService;
	

	@RequestMapping(method = RequestMethod.GET)
	public String getHomePage(Model ciModel,@RequestParam(value="lang", required=false)String id,
			HttpServletRequest httpServletRequest) throws ParseException {
		return "default_bk";
	}
//	@RequestMapping(method = RequestMethod.GET)
//	public String getHomePage(Model ciModel,@RequestParam(value="lang", required=false)String id,
//			HttpServletRequest httpServletRequest) throws ParseException {
//		Locale locale = LocaleContextHolder.getLocale();	
//		if(StringUtils.isNotEmpty(id)){
//			if(id.equalsIgnoreCase(com.sanyo.quote.helper.Constants.VIETNAMESE))
//				locale.setDefault(new Locale(id));
//		}
//		List<Project> tablesOfDay;
//		
//		String tradeDate = httpServletRequest.getParameter("tradeDate");
//		String danguong = httpServletRequest.getParameter("danguong");
//		String datinhtien = httpServletRequest.getParameter("datinhtien");
//		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//		List<ProjectStatus> statuses = new ArrayList<ProjectStatus>();
//		if(danguong != null && danguong.equalsIgnoreCase("true"))
//			statuses.add(ProjectStatus.PROCESSING);
//		if (datinhtien != null && datinhtien.equalsIgnoreCase("true"))
//			statuses.add(ProjectStatus.PAID);
//		Date date = null;
//		if(tradeDate != null){
//			//get the next date
//			
//			date = format.parse(tradeDate);
//			
//
//		}else{
//			Date currentDate = new Date();
//			String dateString = format.format(currentDate);
//			date = format.parse(dateString);
//		}
//		if(statuses.size() == 1)
//			tablesOfDay = projectService.findTableByDate(date, statuses.get(0));
//		else if (statuses.size() > 1)
//			tablesOfDay = projectService.findTableByDate(date, statuses.get(0), statuses.get(1));
//		else
//			tablesOfDay = projectService.findTableByDate(date);
//		ciModel.addAttribute("table", tablesOfDay);
//		return "taphoa";
//	}
//	
	
	@RequestMapping(value = "/charts", method = RequestMethod.GET)
	@Transactional
	public String getCharts() {
		return "redirect:/charts?index";
	}
	
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	@Transactional
	public String getCategories() {
		return "redirect:/categories?index";
	}
	
	@RequestMapping(value = "/medias", method = RequestMethod.GET)
	public String getMedias() {
		return "home/medias";
	}
	
	@RequestMapping(value = "/stories", method = RequestMethod.GET)
	public String getStories() {
		return "home/stories";
	}
	
	@RequestMapping(value = "/downloads", method = RequestMethod.GET)
	public String getDownloads() {
		return "redirect:/downloads?index";
	}
}
