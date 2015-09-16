package com.sanyo.quote.web.controller.admin;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sanyo.quote.domain.Group;
import com.sanyo.quote.domain.User;
import com.sanyo.quote.helper.ImageHelper;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.GroupService;
import com.sanyo.quote.web.form.Message;
import com.sanyo.quote.web.util.UrlUtil;

@RequestMapping("/admin/roles")
@Controller
public class RoleController {
	final Logger logger = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	MessageSource messageSource;
	@Autowired
	GroupService groupService;
	private Validator validator;
	
	public RoleController(){
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
	}
	
	//handle /admin/roles
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		logger.info("Listing roles");

		List<Group> groups = groupService.findAll();
		uiModel.addAttribute("groups", groups);
		return "roles/list";
	}
	
	// handle screen for updating. Just display form for form edit.
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		Group group = groupService.findById(id);
        uiModel.addAttribute("group", group);
        return "roles/update";
	}
	
	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
		Group group = new Group();
        uiModel.addAttribute("group", group);
        return "roles/create";
	}
	//create new role, save to database
	@RequestMapping(params = "form", method = RequestMethod.POST)
    public String create(@Valid Group group, BindingResult bindingResult, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
		logger.info("Creating role");
        if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new Message("error", messageSource.getMessage("role_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("group", group);
            return "roles/create";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("role_save_success", new Object[]{}, locale)));
        groupService.save(group);
        return "redirect:/admin/roles/" + UrlUtil.encodeUrlPathSegment(group.getGroupid().toString(), httpServletRequest) + "?form";
    }
	
	//update an existing group, save to database
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
	@Transactional
    public String update(@ModelAttribute("group") Group group, @PathVariable Integer id, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
		logger.info("Updating role");
        uiModel.asMap().clear();
        group.setGroupid(id);
        
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("role_save_success", new Object[]{}, locale)));        
        uiModel.addAttribute("group", group);
        groupService.save(group);
        return "roles/update";
    }
	
	@RequestMapping(value = "/getListJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getProductsJson(@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		
		// Constructs page request for current page
		PageRequest pageRequest = null;
		pageRequest = new PageRequest(pagenum, pagesize);

		
		List<Group> groups  = groupService.findAll();
		String result = Utilities.jSonSerialization(groups);
		return result;
	}	
	

}
