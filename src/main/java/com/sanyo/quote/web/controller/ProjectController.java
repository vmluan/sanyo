package com.sanyo.quote.web.controller;

import java.util.HashSet;
import java.util.Iterator;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sanyo.quote.domain.Category;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.domain.User;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.CategoryService;
import com.sanyo.quote.service.EncounterService;
import com.sanyo.quote.service.ProductService;
import com.sanyo.quote.service.ProjectService;
import com.sanyo.quote.service.RegionService;
import com.sanyo.quote.service.UserService;
import com.sanyo.quote.web.form.Message;
import com.sanyo.quote.web.util.UrlUtil;

@Controller
@RequestMapping(value = "/projects")
public class ProjectController {
	final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private EncounterService encounterService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private UserService userService;
	
	private Validator validator;
	
	public ProjectController(){
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
	}
	
	//handle /projects
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		logger.info("Listing projects");

		List<Project> projects = projectService.findAll();
		uiModel.addAttribute("projects", projects);
		return "projects/list";
	}
	
	// handle screen for updating. Just display form for form edit.
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		Project project = projectService.findById(id);
        uiModel.addAttribute("project", project);
        return "projects/update";
	}
	
	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
		Project project = new Project();
        uiModel.addAttribute("project", project);
        return "projects/create";
	}
	//create new project, save to database
	@RequestMapping(params = "form", method = RequestMethod.POST)
    public String create(@Valid Project project, BindingResult bindingResult, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
		logger.info("Creating Project");
        if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new Message("error", messageSource.getMessage("project_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("project", project);
            return "projects/create";
        }
        uiModel.asMap().clear();
        projectService.save(project);
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("project_save_success", new Object[]{}, locale)));
        return "redirect:/projects/" + UrlUtil.encodeUrlPathSegment(project.getProjectId().toString(), httpServletRequest) + "?form";
    }
	
	//update an existing project, save to database
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
	@Transactional
    public String update(@ModelAttribute("project") Project project, @PathVariable Integer id, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale, BindingResult bindingResult) {
		logger.info("Updating project");
		 Set<ConstraintViolation<Project>> violations = validator.validate(project);
	     
	    for (ConstraintViolation<Project> violation : violations)
	    {
	        String propertyPath = violation.getPropertyPath().toString();
	        String message = violation.getMessage();
	        // Add JSR-303 errors to BindingResult
	        // This allows Spring to display them in view via a FieldError
	        bindingResult.addError(new FieldError("user",propertyPath,
	                               "Invalid "+ propertyPath + "(" + message + ")"));
	    }
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new Message("error", messageSource.getMessage("project_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("project", project);
            return "projects/update";
	        }    
        uiModel.asMap().clear();
        project.setProjectId(id);
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("project_save_success", new Object[]{}, locale)));        
        uiModel.addAttribute("project", project);
        projectService.save(project);
        return "projects/update";
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

		
		List<Project> projects  = projectService.findAll();
		String result = Utilities.jSonSerialization(projects);
		return result;
	}
	@RequestMapping(value = "/getAssginedRegionsJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getAssginedRegionsJson(@RequestParam(value="projectId", required=true) String projectId,
			@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		System.out.println("start getting assinged regions");
		Project project = projectService.findById(Integer.valueOf(projectId));
		// Constructs page request for current page
		PageRequest pageRequest = null;
		pageRequest = new PageRequest(pagenum, pagesize);

		
		Set<Region> regions  = project.getRegions();
		Iterator<Region> iterator = regions.iterator();
		Set<Region> assginedRegions = new HashSet<Region>();
		
		while(iterator.hasNext()){
			Region region = iterator.next();
			Region regionWithUsers = regionService.findByIdAndFetchUsersEagerly(region.getRegionId());
			if(regionWithUsers != null)
				assginedRegions.add(regionWithUsers);
			else{
				regionWithUsers = regionService.findById(region.getRegionId());
				Set<User> emptyUsers = new HashSet<User>();
				regionWithUsers.setUsers(emptyUsers);
				assginedRegions.add(regionWithUsers);
			}
		}
		
		String result = Utilities.jSonSerialization(assginedRegions);
		return result;
	}
	// handle screen for create new assigned regions.
	@RequestMapping(value = "/{id}", params = "regions", method = RequestMethod.GET)
    public String createNewAssignedRegions(@PathVariable("id") Integer id, Model uiModel) {
		Project project = projectService.findById(id);
        uiModel.addAttribute("project", project);
        setCategories(uiModel);
        return "projects/update";
	}
	// handle screen for create new assigned regions.
	@RequestMapping(value = "/{id}", params = "regions", method = RequestMethod.POST)
    public String saveNewAssignedRegions(@ModelAttribute("region") Region Region, @PathVariable Integer id, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale, BindingResult bindingResult) {
		Project project = projectService.findById(id);
        uiModel.addAttribute("project", project);
        setCategories(uiModel);
        return "projects/update";
	}
	
	private void setCategories(Model uiModel){
		uiModel.addAttribute("parentCategories", categoryService.findAll());
	}
	
	//saving assigned regions
	@RequestMapping(value = "/{id}", params = "assignRegions", method = RequestMethod.POST)
    public String assignRegions(@ModelAttribute("project") Project project,@PathVariable Integer id, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale, BindingResult bindingResult,
    		@RequestParam(value = "param[]") String[] paramValues) {
		System.out.println("saving assigned regions");
		Project existingProject = projectService.findById(Integer.valueOf(id));
		if(paramValues != null){
//			Set<Region> regions = new HashSet<Region>();
			for(int i=0 ; i<paramValues.length; i++){
				Region region = new Region();
				Category category = categoryService.findById(Integer.valueOf(paramValues[i]));
				region.setCategory(category);
				region.setRegionName(category.getName());
				region.setRegionDesc(category.getDesc());
				region.setProject(existingProject);
				
				regionService.save(region);
//				regions.add(region);
			}
		}
		return "projects/update";
	}
	@RequestMapping(value = "regions/{id}", params = "form", method = RequestMethod.GET)
	public String showRegions(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		Region region = regionService.findById(Integer.valueOf(id));
		uiModel.addAttribute("region", region);
		uiModel.addAttribute("projectId", region.getProject().getProjectId());
		return "projects/region/new";
	}
	@RequestMapping(value = "regions/{id}", params = "form", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void saveUsersForRegion(@PathVariable("id") Integer id, Model uiModel
			,@RequestParam(value = "param[]") String[] paramValues
			,HttpServletRequest httpServletRequest){
		System.out.println("start assigning users to region");
		Region region = regionService.findById(Integer.valueOf(id));
		
		if(paramValues != null){
			Set<User> users = new HashSet<User>();
			for(int i=0 ; i<paramValues.length; i++){
				User user = userService.findById(Integer.valueOf(paramValues[i]));
				users.add(user);
			}
			region.setUsers(users);
			regionService.save(region);
		}
	}	
}

