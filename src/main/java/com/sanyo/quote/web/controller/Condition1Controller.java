package com.sanyo.quote.web.controller;

import java.util.Date;

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
import com.sanyo.quote.service.ProjectService;

/**
 * Created by Van Duoc on 12/03/2015.
 */

@Controller
@RequestMapping(value = "/condition_1")
public class Condition1Controller extends CommonController {
	final Logger logger = LoggerFactory.getLogger(Condition1Controller.class);

	@Autowired
	private ProjectService projectService;
	
    /*@RequestMapping(method = RequestMethod.GET)
    public String getBOQPage(Model uiModel) {
        return "quotation/condition_1";
    }*/
    
    @RequestMapping(method = RequestMethod.GET)
	public String getCoverPage(@RequestParam(value="projectId", required=true) String projectId,
			Model uiModel,HttpServletRequest httpServletRequest) {
		Project project = projectService.findById(Integer.valueOf(projectId));
		Date startDate=project.getStartDate();
		Date endate = project.getEndDate();
		
		int startmonth = startDate.getMonth();
		int startyear = startDate.getYear();
		int endmonth = endate.getMonth();
		int endyear = endate.getYear();
		int summonth=0;
		if(startyear==endyear)
		{
			summonth=endmonth-startmonth;
		}
		else
			if(startyear < endyear)
			{
				int month=12*(endyear-startyear);
				summonth = (month - startmonth) + endmonth;
			}
		
		uiModel.addAttribute("project", project);
		uiModel.addAttribute("summonth", summonth);
		return "quotation/condition_1";
	}
}
