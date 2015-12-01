package com.sanyo.quote.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.ProjectStatus;
import com.sanyo.quote.helper.Constants;
import com.sanyo.quote.service.ProjectService;

/**
 * Created by Van Duoc on 11/30/2015.
 */

@Controller
@RequestMapping(value = "/cover")
public class CoverController extends CommonController {
	final Logger logger = LoggerFactory.getLogger(CoverController.class);
	
	@Autowired
	private ProjectService projectService;
	
    /*@RequestMapping(method = RequestMethod.GET)
    public String getBOQPage(Model uiModel) {
        return "quotation/cover";
    }*/
    
    @RequestMapping(method = RequestMethod.GET)
	public String getCoverPage(@RequestParam(value="projectId", required=true) String projectId,
			Model uiModel,HttpServletRequest httpServletRequest) {
		Project project = projectService.findById(Integer.valueOf(projectId));
		uiModel.addAttribute("project", project);		
		return "quotation/cover";
	}
}
