package com.sanyo.quote.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.UserService;
import com.sanyo.quote.web.form.BreadCrumb;
import com.sanyo.quote.web.form.Link;

@Controller
public class CommonController {
	@Autowired
	UserService userService;
	public List<Link> links = new ArrayList<Link>();
	
	public void resetLinks(){
		this.links.clear();
		addToLinks("Home", "/");
	}
	
	public void addToLinks(String name, String url){
		Link link = new Link();
		link.setLinkName(name);
		link.setLinkUrl(url);
		if(url.equalsIgnoreCase("/"))
			link.setHomeLink(true);
		links.add(link);
	}
	public void setModel(Model model){
		model.addAttribute("links", links);
	}
	public void setBreadCrumb(Model uiModel){
		uiModel.addAttribute("links", links);
	}
	public void setBreadCrumb(Model uiModel, String parentLinkUrl, String parentLinkName, String currentLinkUrl, String currentLinkName){
		BreadCrumb breadCrumb = new BreadCrumb();
		Link currentLink = new Link();
		currentLink.setLinkName(currentLinkName);
		currentLink.setLinkUrl(currentLinkUrl);
		if(parentLinkUrl != null && parentLinkName != null){
			Link parentLink = new Link();
			parentLink.setLinkName(parentLinkName);
			parentLink.setLinkUrl(parentLinkUrl);
			breadCrumb.setParentLink(parentLink);
		}
		breadCrumb.setCurrentLink(currentLink);
		
		uiModel.addAttribute("breadCrumb", breadCrumb);
		uiModel.addAttribute("links", links);
	}
	public void setHeader(Model uiModel, String pageHeader, String pageDesc){
		uiModel.addAttribute("pageHeader", pageHeader);
		uiModel.addAttribute("pageDesc", pageDesc);
	}
	public void setUser(Model uiModel){
		org.springframework.security.core.userdetails.User user = Utilities.getCurrentUser();
		uiModel.addAttribute("userName", user.getUsername());
		com.sanyo.quote.domain.User userSanyo = userService.findByUserName(user.getUsername());
		uiModel.addAttribute("user",userSanyo);
	}
	public void throwOverlappedDateException(String message) throws Exception{
		Exception e = new Exception(message);
		throw e;
	}
	public void checkAdminRole() throws Exception{
		if(!Utilities.hasAdminRole()){
			Exception e = new Exception("You have not permission to execute this request. Please contact admin for the support");
			throw e;
		}
	}
}
