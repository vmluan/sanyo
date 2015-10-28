package com.sanyo.quote.web.controller;

import org.springframework.ui.Model;

import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.web.form.BreadCrumb;
import com.sanyo.quote.web.form.Link;

public class CommonController {
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
	}
	public void setHeader(Model uiModel, String pageHeader, String pageDesc){
		uiModel.addAttribute("pageHeader", pageHeader);
		uiModel.addAttribute("pageDesc", pageDesc);
	}
	public void setUser(Model uiModel){
		org.springframework.security.core.userdetails.User user = Utilities.getCurrentUser();
		uiModel.addAttribute("userName", user.getUsername());
	}
}
