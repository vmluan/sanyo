package com.sanyo.quote.web.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sanyo.quote.domain.Condition2;
import com.sanyo.quote.domain.Condition2Json;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.service.ProjectService;
import com.sanyo.quote.service.condition2service;

/**
 * Created by Chuong Thai on 10/8/2015.
 */

@Controller
@RequestMapping(value = "/condition_2")
public class Condition2Controller extends CommonController {
	final Logger logger = LoggerFactory.getLogger(CoverController.class);
	
	
	@Autowired
	private ProjectService projectService;
	@Autowired
    condition2service condition;
    /*@RequestMapping(method = RequestMethod.GET)
    public String getBOQPage(Model uiModel) {
        return "quotation/condition_2";
    }*/
    
    @RequestMapping(method = RequestMethod.GET)
	public String getCoverPage(@RequestParam(value="projectId", required=true) String projectId,
			Model uiModel,HttpServletRequest httpServletRequest) {
    	Project project = projectService.findById(Integer.parseInt(projectId));
		Condition2 Condition2Json = condition.findByProject(project);
		String Checkboxs=null;
		String Contents=null;
		if(Condition2Json!=null)
		{
			Checkboxs=Condition2Json.getCheckboxs();
			Contents=Condition2Json.getContents();
		}
		uiModel.addAttribute("Checkboxs", Checkboxs);		
		uiModel.addAttribute("Contents", Contents);	
		return "quotation/condition_2";
	}
    @RequestMapping(value = "/{id}/savecondition2", params = "form", method = RequestMethod.POST) 
    public String savecondition(@RequestBody final Condition2Json condition2Json, @PathVariable Integer id, Model uiModel, HttpServletRequest httpServletRequest, 
    								RedirectAttributes redirectAttributes, Locale locale) 
    { 
    	
    	Project project = projectService.findById(id);
    
    	Condition2 condtion2 = condition.findByProject(project);
    	if(condtion2==null)
    	{
    		condtion2 = new Condition2();
    	}
    		condtion2.setCheckboxs(condition2Json.getCheckbox());     	
	    	condtion2.setContents(condition2Json.getContents());
	    	condtion2.setProject(project);
	    	//if(id==2)
	    	//{
	    		//List<Condition2> condi= condition.findByProject(condtion2);
	    	//}
	    	condition.save(condtion2);
    	//String test = expensesJson.getExpenseElementRemark_1(); 
    	//String test2 = test; logger.info("Saving expense list"); 
    	//if (bindingResult.hasErrors()) { //uiModel.addAttribute("message", new Message("error", messageSource.getMessage("expense_save_fail", new Object[]{}, locale)));
    	//uiModel.addAttribute("expensesJson", expensesJson); //return "/quotation/expenses/create"; //} 
    	return "quotation/condition_2";
    }
}
