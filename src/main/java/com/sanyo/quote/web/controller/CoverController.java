package com.sanyo.quote.web.controller;

import java.io.Console;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sanyo.quote.domain.Condition2;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.ProjectRevision;
import com.sanyo.quote.domain.ProjectStatus;
import com.sanyo.quote.helper.Constants;
import com.sanyo.quote.service.ProjectRevisionService;
import com.sanyo.quote.service.ProjectService;
import com.sanyo.quote.service.condition2service;
import com.sanyo.quote.service.jpa.DefaultProjectRevisionService;
import com.sanyo.quote.web.form.Message;

/**
 * Created by Van Duoc on 11/30/2015.
 */

@Controller
@RequestMapping(value = "/cover")
public class CoverController extends CommonController {
	final Logger logger = LoggerFactory.getLogger(CoverController.class);
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectRevisionService projectrevisionService;
    /*@RequestMapping(method = RequestMethod.GET)
    public String getBOQPage(Model uiModel) {
        return "quotation/cover";
    }*/
    @RequestMapping(method = RequestMethod.GET)
	public String getCoverPage(@RequestParam(value="projectId", required=true) String projectId,
		Model uiModel,HttpServletRequest httpServletRequest) {
    	Project project = projectService.findById(Integer.parseInt(projectId));
    	List<ProjectRevision> revision = projectrevisionService.findRevisions(project);
    	convertDateToString convert =new convertDateToString();
    	List<convertDateToString> listRevision = new ArrayList<convertDateToString>() ;
    	for(ProjectRevision list : revision)
    	{
    		convert.setStrDate(convertDate(list.getDate()));
    		convert.setRevision_no(list.getRevisionNo());
    		listRevision.add(convert);
    	}
    	uiModel.addAttribute("project", project);
		uiModel.addAttribute("projectCreateDate", convertDate(project.getCreatedDate()));
		uiModel.addAttribute("revision",listRevision);
		uiModel.addAttribute("sizeRevision",revision.size());
		return "quotation/cover";
	}
    private String convertDate(Date date)
    {
    	//Date today;
    	String dateOut;
    	DateFormat dateFormatter;
    	Locale currentLocale = Locale.US;

    	dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, currentLocale);
    	//today = new Date();
    	dateOut = dateFormatter.format(date);
    	return dateOut;
    }
    private Date convertDate(String strdate)
    {
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    	Date date=new Date();
    	try
        {
             date= simpleDateFormat.parse(strdate);
        }
        catch (ParseException ex)
        {
            //System.out.println("Exception "+ex);
        }
    	return date;
    }
}
