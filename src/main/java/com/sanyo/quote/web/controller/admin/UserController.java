package com.sanyo.quote.web.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.Group;
import com.sanyo.quote.domain.User;
import com.sanyo.quote.helper.ImageHelper;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.GroupService;
import com.sanyo.quote.service.UserService;
import com.sanyo.quote.web.controller.CommonController;
import com.sanyo.quote.web.form.GenericGrid;
import com.sanyo.quote.web.form.Message;
import com.sanyo.quote.web.util.UrlUtil;

@RequestMapping("/admin/users")
@Controller
public class UserController extends CommonController {

	final Logger logger = LoggerFactory.getLogger(UserController.class);
	final String UPLOAD_DIRECTORY = "/images/profile/";
	@Autowired
	MessageSource messageSource;

	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
	
	
	private Validator validator;
	
	public UserController(){
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		logger.info("Listing users");

		List<User> users = userService.findAll();
		uiModel.addAttribute("users", users);
		setBreadcrumbLink(uiModel, "/", "");
		setPageHeader(uiModel, "User List", "All of users");
		logger.info("No. of users: " + users.size());
		setBreadCrumb(uiModel, "/", "Home", "", "Users");
		return "users/list";
	}

	@RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public GenericGrid<User> listGrid(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order) {

		logger.info("Listing users for grid with page: {}, rows: {}", page,
				rows);
		logger.info("Listing users for grid with sort: {}, order: {}", sortBy,
				order);

		// Process order by
		Sort sort = null;
		String orderBy = sortBy;
		

		if (orderBy != null && order != null) {
			if (order.equals("desc")) {
				sort = new Sort(Sort.Direction.DESC, orderBy);
			} else
				sort = new Sort(Sort.Direction.ASC, orderBy);
		}

		// Constructs page request for current page
		// Note: page number for Spring Data JPA starts with 0, while jqGrid
		// starts with 1
		PageRequest pageRequest = null;

		if (sort != null) {
			pageRequest = new PageRequest(page - 1, rows, sort);
		} else {
			pageRequest = new PageRequest(page - 1, rows);
		}

		Page<User> userPage = userService.findAllByPage(pageRequest);

		// Construct the grid data that will return as JSON data
		GenericGrid<User> userGrid = new GenericGrid<User>();

		userGrid.setCurrentPage(userPage.getNumber() + 1);
		userGrid.setTotalPages(userPage.getTotalPages());
		userGrid.setTotalRecords(userPage.getTotalElements());
		userGrid.setEntityData(Lists.newArrayList(userPage.iterator()));

		return userGrid;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        User user = userService.findById(id);
		uiModel.addAttribute("user", user);
		setBreadCrumb(uiModel, "/admin/users", "Users", "", "View User");
        return "users/show";
    }
	
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		User user = userService.findById(id);
        uiModel.addAttribute("user", user);
        resetGroups(user,uiModel);
        setBreadCrumb(uiModel, "/admin/users", "Users", "", "Update User");
        return "users/update";
	}
	
	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
		User user = new User();
        uiModel.addAttribute("user", user);
        resetGroups(user,uiModel);
        setPageHeader(uiModel, "Create User", "");
        setBreadCrumb(uiModel, "/admin/users", "Users", "", "Create User");
        return "users/create";
	}
	//update an existing user, save to database
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
	@Transactional
    public String update(@ModelAttribute("user") User user, @PathVariable Integer id, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale, @RequestParam(value="password", required=true) String password,BindingResult bindingResult) {
		 Set<ConstraintViolation<User>> violations = validator.validate(user);
	     
		    for (ConstraintViolation<User> violation : violations)
		    {
		        String propertyPath = violation.getPropertyPath().toString();
		        String message = violation.getMessage();
		        // Add JSR-303 errors to BindingResult
		        // This allows Spring to display them in view via a FieldError
		        bindingResult.addError(new FieldError("user",propertyPath,
		 
		                               "Invalid "+ propertyPath + "(" + message + ")"));
		    }
		    
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new Message("error", messageSource.getMessage("user_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("user", user);
            resetGroups(user, uiModel);
            return "users/update";
        }
		logger.info("Updating user");
        uiModel.asMap().clear();
        user.setUserid(id);
        
        String oldPassword = userService.findById(id).getPassword();
        if (!oldPassword.equals(password))
        {
        	user.setPassword(DigestUtils.md5Hex(password));	
        }
        
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("user_save_success", new Object[]{}, locale)));        
        String fileName = "";
        fileName = ImageHelper.getInstances().saveImages(httpServletRequest,UPLOAD_DIRECTORY);
        user.setAvatar(fileName);
        setPageHeader(uiModel, "Edit User", "");
        setBreadcrumbLink(uiModel, "/users", "");
        setGroupList(user);
        uiModel.addAttribute("user", user);
        resetGroups(user,uiModel);
        setPageHeader(uiModel, "Update User", "");
        userService.save(user);
        return "users/update";
//        return "redirect:/admin/users/" + UrlUtil.encodeUrlPathSegment(user.getUserid().toString(), httpServletRequest);
    }
	//create new user, save to database
	@RequestMapping(params = "form", method = RequestMethod.POST)
    public String create(@Valid User user, BindingResult bindingResult, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale,
    		@RequestParam(value="password", required=true) String password) {
		logger.info("Creating user");
		httpServletRequest.getParameter("grouplist");
        if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new Message("error", messageSource.getMessage("user_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("user", user);
            resetGroups(user, uiModel);
            return "users/create";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("user_save_success", new Object[]{}, locale)));

        user.setPassword(DigestUtils.md5Hex(password));
        logger.info("User id: " + user.getUserid());
        
      //handle attachments
        String fileName = "";
        fileName = ImageHelper.getInstances().saveImages(httpServletRequest,UPLOAD_DIRECTORY);
        user.setAvatar(fileName);
        setPageHeader(uiModel, "Create new User", "");
        setBreadcrumbLink(uiModel, "/users", "");
        setGroupList(user);
        userService.save(user);
        return "redirect:/admin/users/" + UrlUtil.encodeUrlPathSegment(user.getUserid().toString(), httpServletRequest);
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
		
		/*
		// Constructs page request for current page
		PageRequest pageRequest = null;
		pageRequest = new PageRequest(pagenum, pagesize);

		
//		List<User> users = (List<User>) userService.findAllByPage(pageRequest).getContent();
 * */
		List<User> users  = userService.findAll();
		String result = Utilities.jSonSerialization(users);
		//httpServletResponse.setContentType("application/json; charset=UTF-8");
		return result;
	}
	private void setPageHeader(Model uiModel, String pageHeader, String pageDesc){
		uiModel.addAttribute("pageHeader", pageHeader);
		uiModel.addAttribute("pageDesc", pageDesc);
	}
	private void setBreadcrumbLink(Model uiModel, String homeLink, String currentLink){
		uiModel.addAttribute("homeLink", homeLink);
		uiModel.addAttribute("currentLink", currentLink);
	}
	private void resetGroups(User user, Model uiModel){
		uiModel.addAttribute("allGroups", groupService.findAll());
		List<Group> groupList = user.getGrouplist();
		if(groupList != null){
			 String[] groupIDs = new String[groupList.size()];
		        for(int i=0; i< groupList.size(); i++){
		        	groupIDs[i] = groupList.get(i).getGroupid().toString();
		        }
			user.setGroupIDs(groupIDs);
		}
	}
	private void setGroupList(User user){
        String[] groupIDs = user.getGroupIDs();
        if(groupIDs != null){
        	List<Group> groupList = new ArrayList<Group>();
	        for(int i=0; i< groupIDs.length; i++){
	        	Group group = groupService.findById(Integer.valueOf(groupIDs[i]));
	        	groupList.add(group);
	        }
	        user.setGrouplist(groupList);
	        
        }
	}
}
