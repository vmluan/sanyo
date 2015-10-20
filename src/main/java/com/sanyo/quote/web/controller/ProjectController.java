package com.sanyo.quote.web.controller;

import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sanyo.quote.domain.Category;
import com.sanyo.quote.domain.Location;
import com.sanyo.quote.domain.ProductGroup;
import com.sanyo.quote.domain.ProductGroupMaker;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.ProjectRevision;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.domain.RegionJson;
import com.sanyo.quote.domain.User;
import com.sanyo.quote.domain.UserJson;
import com.sanyo.quote.domain.UserRegionRole;
import com.sanyo.quote.helper.Constants;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.CategoryService;
import com.sanyo.quote.service.EncounterService;
import com.sanyo.quote.service.LocationService;
import com.sanyo.quote.service.ProductService;
import com.sanyo.quote.service.ProjectRevisionService;
import com.sanyo.quote.service.ProjectService;
import com.sanyo.quote.service.RegionService;
import com.sanyo.quote.service.UserRegionRoleService;
import com.sanyo.quote.service.UserService;
import com.sanyo.quote.web.form.Message;
import com.sanyo.quote.web.util.UrlUtil;

@Controller
@RequestMapping(value = "/projects")
public class ProjectController extends CommonController {
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
	
	@Autowired
	private UserRegionRoleService userRegionRoleService;
	
	@Autowired
	private ProjectRevisionService projectRevisionService;
	
	@Autowired
	private LocationService locationService;
	
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
		setBreadCrumb(uiModel, "/", "Home", "/projects", "Projects");
		setHeader(uiModel, "Projects", "List of all projects");
		
		return "projects/list";
	}
	
	// handle screen for updating. Just display form for form edit.
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		Project project = projectService.findById(id);
        uiModel.addAttribute("project", project);
        setBreadCrumb(uiModel, "/projects", "Projects", "", "Project Detail");
        setHeader(uiModel, "Project Detail", "Contains detail information including regions and assigned users");
        return "projects/update";
	}
	
	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
		Project project = new Project();
        uiModel.addAttribute("project", project);
        setBreadCrumb(uiModel, "/projects", "Projects", "", "New Project");
        setHeader(uiModel, "Create new project", "");
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
        Date date = new Date();
        project.setCreatedDate(date);
        project.setCreatedBy(Utilities.getCurrentUser().getUsername());
        project.setLastModifiedBy(Utilities.getCurrentUser().getUsername());
        project.setLmodDate(date);
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
        uiModel.addAttribute("message", new Message("success", messageSource.getMessage("project_save_success", new Object[]{}, locale)));        
        uiModel.addAttribute("project", project);
        project.setLmodDate(new Date());
        project.setLastModifiedBy(Utilities.getCurrentUser().getUsername());
        projectService.save(project);
        setBreadCrumb(uiModel, "/projects", "Projects", "", "Project Detail");
        setHeader(uiModel, "Project Detail", "Contains detail information including regions and assigned users");
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
		Project project = projectService.findByIdAndFetchLocationsEagerly(Integer.valueOf(projectId));
		
		Set<Location> locations = project.getLocations();
		Set<Region> totalRegions = new HashSet<Region>();
		for(Location location : locations){
			Set<Region> regions  = location.getRegions();
			totalRegions.addAll(regions);
		}
		Iterator<Region> iterator = totalRegions.iterator();
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
	@RequestMapping(value = "/getAssginedCategoriesJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getAssginedCategoriesJson(@RequestParam(value="projectId", required=true) String projectId,
			@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		System.out.println("start getting assinged categories");
		Project project = projectService.findByIdAndFetchLocationsEagerly(Integer.valueOf(projectId));
		String regionType = httpServletRequest.getParameter("regionType");
		
		Set<Location> locations = project.getLocations();
		Set<Region> totalRegions = new HashSet<Region>();
		for(Location location : locations){
			Set<Region> regions  = location.getRegions();
			totalRegions.addAll(regions);
		}
		Iterator<Region> iterator = totalRegions.iterator();
		Set<Category> categories = new HashSet<Category>();
		while(iterator.hasNext()){
			Region region = iterator.next();
			if(regionType != null && regionType.equalsIgnoreCase("ELEC")){ //get ELECTRICAL only
				Category category = region.getCategory().getParentCategory();
				if(category != null && category.getName().contains("ELECTRICAL BOQ")){
					if(!isExisingCategory(categories, region))
						categories.add(region.getCategory());
				}
			}
			else if(regionType != null && regionType.equalsIgnoreCase("MECH")){ //get MECHANICAL only
				Category category = region.getCategory().getParentCategory();
				if(category != null && category.getName().contains("MECHANICAL BOQ")){
					if(!isExisingCategory(categories, region))
						categories.add(region.getCategory());
				}
			}else
			{ //get all
				if(!isExisingCategory(categories, region))
					categories.add(region.getCategory());
			}

		}
		
		String result = Utilities.jSonSerialization(categories);
		return result;
	}
	private boolean isExisingCategory(Set<Category> categories, Region region){
		Iterator<Category> iterator = categories.iterator();
		while(iterator.hasNext()){
			Category category = iterator.next();
			if(category.getName().equalsIgnoreCase(region.getCategory().getName()))
				return true;
		}
		return false;
	}
	@RequestMapping(value = "/getAssginedRegionsOfLocationJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getAssginedRegionsOfLocationJson(@RequestParam(value="locationId", required=true) String locationId,
			@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		System.out.println("start getting assinged regions of location");
		Location location = locationService.findById(Integer.valueOf(locationId));
		Set<Region> totalRegions =location.getRegions();
		
		String result = Utilities.jSonSerialization(totalRegions);
		return result;
	}
	// handle screen for create new assigned regions.
	@RequestMapping(value = "/{id}", params = "regions", method = RequestMethod.GET)
    public String createNewAssignedRegions(@PathVariable("id") Integer id, Model uiModel) {
		Project project = projectService.findById(id);
        uiModel.addAttribute("project", project);
        setCategories(uiModel);
        setBreadCrumb(uiModel, "/projects", "Projects", "", "Project Detail");
        return "projects/update";
	}
	// handle screen for create new assigned regions.
	@RequestMapping(value = "/{id}", params = "regions", method = RequestMethod.POST)
    public String saveNewAssignedRegions(@ModelAttribute("region") Region Region, @PathVariable Integer id, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale, BindingResult bindingResult) {
		Project project = projectService.findById(id);
        uiModel.addAttribute("project", project);
        setCategories(uiModel);
        setBreadCrumb(uiModel, "/projects", "Projects", "", "Project Detail");
        return "projects/update";
	}
	
	private void setCategories(Model uiModel){
		uiModel.addAttribute("parentCategories", categoryService.findAll());
	}
	
	
	//method to save assigned regions to database. it also saves assigned users.
	@RequestMapping(value = "/{id}", params = "assignRegions", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
    public  void assignRegions(@RequestBody final RegionJson[] regionJsons ,@PathVariable Integer id, Model uiModel, 
    		HttpServletRequest httpServletRequest) {
		System.out.println("===================== saving assigned regions");
		Project existingProject = projectService.findByIdAndFetchLocationsEagerly(Integer.valueOf(id));
		Set<Location> locations = existingProject.getLocations();
		if(regionJsons != null && regionJsons.length >0){
			for(int i=0; i< regionJsons.length; i++){
				RegionJson regionJson = regionJsons[i];
				Location existingLocation = null;
				for(Location location : locations){
					if(location.getLocationName().equalsIgnoreCase(regionJson.getLocationName().trim())){
						existingLocation = location;
					}
				}
				
				Category category = categoryService.findById(regionJson.getRegionId());
				
				Region region = getExistingRegion(existingLocation, category.getName());
				if(region == null){
					region = new Region();
					region.setCategory(category);
					region.setRegionName(category.getName());
					region.setRegionDesc(category.getDesc());
					if(existingLocation != null)
						region.setLocation(existingLocation);
					region = regionService.save(region);
				}else{
					if(existingLocation != null)
						region.setLocation(existingLocation);
				}

				List<UserJson> userJsons = regionJson.getUsers();
//				Set<UserRegionRole> userRegionRoles = new HashSet<UserRegionRole>();
				for(UserJson userJson : userJsons){
					
					UserRegionRole userRegionRole = getExistingUser(region,userJson.getUserName());
					if(userRegionRole == null){
						User user = userService.findByUserName(userJson.getUserName());
						userRegionRole = new UserRegionRole();
						userRegionRole.setUser(user);
						userRegionRole.setRoleName(userJson.getRoleName()); //get from request
						userRegionRole.setRegion(region);
						userRegionRole.setUserName(user.getUsername());
						userRegionRole = userRegionRoleService.save(userRegionRole);
					}
					
//					userRegionRoles.add(userRegionRole);
				}
//				region.setUserRegionRoles(userRegionRoles);
								
			}
		}
	}
	@RequestMapping(value = "regions/{id}", params = "form", method = RequestMethod.GET)
	public String showRegions(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		Region region = regionService.findById(Integer.valueOf(id));
		uiModel.addAttribute("region", region);
//		uiModel.addAttribute("projectId", region.getProject().getProjectId());
//		String parentLink = "/projects/" + region.getProject().getProjectId() + "?form";
//		setBreadCrumb(uiModel, parentLink, "Project Detail", "", "Assign User");
		return "projects/region/new";
	}
	@RequestMapping(value = "regions/{id}", params = "form", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void saveUsersForRegion(@RequestBody final UserJson[] userJsons ,@PathVariable("id") Integer id, Model uiModel
			,HttpServletRequest httpServletRequest){
		
		Region region = regionService.findById(Integer.valueOf(id));
		System.out.println("start assigning users to region " + region.getRegionName());
		
		if(userJsons != null && userJsons.length >0){
			for(int i=0; i< userJsons.length; i++){
				UserJson userJson = userJsons[i];
				UserRegionRole userRegionRole = getExistingUser(region,userJson.getUserName());
				if(userRegionRole == null){
					userRegionRole = new UserRegionRole();
					userRegionRole.setRegion(region);
					userRegionRole.setUserName(userJson.getUserName());
					userRegionRole.setRoleName(userJson.getRoleName());
					User user = userService.findByUserName(userJson.getUserName());
					userRegionRole.setUser(user);
					userRegionRoleService.save(userRegionRole);
				}
			}
		}
	}
	private Region getExistingRegion(Location location, String regionName){
		if(location==null)
			return null;
		Set<Region> regions  = location.getRegions();
		Iterator<Region> iterator = regions.iterator();
		if(regions != null && regions.size() >0){
			while(iterator.hasNext()){
				Region region = iterator.next();
				if(regionName.equalsIgnoreCase(region.getCategory().getName())){
					return regionService.findByIdAndFetchUserRegionRolesEagerly(region.getRegionId()); 
				}
			}
		}
		return null;
	}
	private UserRegionRole getExistingUser(Region region, String userName){
		Set<UserRegionRole> userRegionRoles = region.getUserRegionRoles();
		if(userRegionRoles != null && userRegionRoles.size() >0){
			Iterator<UserRegionRole> iterator = userRegionRoles.iterator();
			while(iterator.hasNext()){
				UserRegionRole userRegionRole = iterator.next();
				if(userRegionRole.getUserName().equalsIgnoreCase(userName))
					return userRegionRole;
			}
		}
		return null;
	}
	//method to show form for creating new revision
	@RequestMapping(value = "/{id}/revisions", params = "form", method = RequestMethod.GET)
    public String callCreateProjectRevisions(@PathVariable("id") Integer id, Model uiModel) {
		ProjectRevision projectRevision = new ProjectRevision();
		Project project = projectService.findById(id);
		projectRevision.setProject(project);
        uiModel.addAttribute("projectRevision", projectRevision);
        setBreadCrumb(uiModel, "/projects", "Projects", "", "Create Revision");
        setHeader(uiModel, "Revision", "Create a new revision");
		return "projects/revisions/create";
		
	}
	//method to save form revision to database.
	@RequestMapping(value = "/{id}/revisions", params = "form", method = RequestMethod.POST)
	public String saveProjectRevisions(@ModelAttribute("projectRevision") ProjectRevision projectRevision, @PathVariable Integer id, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale, BindingResult bindingResult){
		logger.info("Saving Revision");
        if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new Message("error", messageSource.getMessage("revision_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("projectRevision", projectRevision);
            return "projects/revisions/create";
        }
		Project project = projectService.findById(id);
		projectRevision.setProject(project);
		projectRevision = projectRevisionService.save(projectRevision);
		uiModel.asMap().clear();
		setBreadCrumb(uiModel, "/projects/" + id + "?form", "Update Project", "", "Update Revision");
		setHeader(uiModel, "Revision", "Detail of revision");
		redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("revision_save_success", new Object[]{}, locale)));
		return "redirect:/projects/revisions/" + UrlUtil.encodeUrlPathSegment(projectRevision.getRevisionId().toString(), httpServletRequest) + "?form";
//		return "projects/revisions/update";
	}
	@RequestMapping(value = "/revisions/{id}", params = "form", method = RequestMethod.GET)
	public String  callEditProjectRevisions(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		ProjectRevision projectRevision = projectRevisionService.findById(id);
        uiModel.addAttribute("projectRevision", projectRevision);
        setBreadCrumb(uiModel, "/projects/" + projectRevision.getProject().getProjectId() + "?form", "Update Project", "", "Update Revision");
        setHeader(uiModel, "Revision", "Update revision");
		return "projects/revisions/update";
	}
	@RequestMapping(value = "/revisions/{id}", params = "form", method = RequestMethod.POST)
	public String  saveEditProjectRevisions(@ModelAttribute("projectRevision") ProjectRevision projectRevision, @PathVariable Integer id
			, Model uiModel, HttpServletRequest httpServletRequest,RedirectAttributes redirectAttributes
			,Locale locale, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new Message("error", messageSource.getMessage("revision_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("projectRevision", projectRevision);
            return "projects/revisions/update";
	        }  
		ProjectRevision exisingRevision = projectRevisionService.findById(id);
		projectRevision.setRevisionId(id);
		projectRevision.setProject(exisingRevision.getProject());
		projectRevision = projectRevisionService.save(projectRevision);
        uiModel.addAttribute("projectRevision", projectRevision);
        setBreadCrumb(uiModel, "/projects/" + projectRevision.getProject().getProjectId() + "?form", "Update Project", "", "Update Revision");
        setHeader(uiModel, "Revision", "Update revision");
        uiModel.addAttribute("message", new Message("success", messageSource.getMessage("revision_save_success", new Object[]{}, locale)));
		return "projects/revisions/update";
	}
	
	@RequestMapping(value = "/getRevisionsJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getRevisionJson(@RequestParam(value="projectId", required=true) String projectId,
			@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		Project project = projectService.findById(Integer.valueOf(projectId));
		Set<ProjectRevision> projectRevisions = project.getRevisions();
		String result = Utilities.jSonSerialization(projectRevisions);
		return result;
	}
	
	//method to show form for creating new location
	@RequestMapping(value = "/{id}/locations", params = "form", method = RequestMethod.GET)
    public String callCreateLocation(@PathVariable("id") Integer id, Model uiModel) {
		Location location = new Location();
		Project project = projectService.findById(id);
		location.setProject(project);
        uiModel.addAttribute("location", location);
        setBreadCrumb(uiModel, "/projects", "Projects", "", "Create Location");
        setHeader(uiModel, "Location", "Create a new Location");
		return "projects/locations/create";
		
	}
	//method to save form of new location to database.
	@RequestMapping(value = "/{id}/locations", params = "form", method = RequestMethod.POST)
	public String saveLocation(@ModelAttribute("location") Location location, @PathVariable Integer id, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale, BindingResult bindingResult){
		logger.info("Saving new Location");
        if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new Message("error", messageSource.getMessage("location_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("location", location);
            return "projects/location/create";
        }
		Project project = projectService.findById(id);
		location.setProject(project);
		location = locationService.save(location);
		uiModel.asMap().clear();
		setBreadCrumb(uiModel, "/projects/" + id + "?form", "Update Project", "", "Update Location");
		setHeader(uiModel, "Location", "Detail of Location");
		redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("location_save_success", new Object[]{}, locale)));
		return "redirect:/projects/locations/" + UrlUtil.encodeUrlPathSegment(location.getLocationId().toString(), httpServletRequest) + "?form";
	}
	
	@RequestMapping(value = "/locations/{id}", params = "form", method = RequestMethod.GET)
	public String  callEditLocation(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		Location location = locationService.findById(id);
        uiModel.addAttribute("location", location);
        setBreadCrumb(uiModel, "/projects/" + location.getProject().getProjectId() + "?form", "Update Project", "", "Update Location");
        setHeader(uiModel, "Location", "Update location");
		return "projects/locations/update";
	}
	@RequestMapping(value = "/locations/{id}", params = "form", method = RequestMethod.POST)
	public String  saveEditLocation(@ModelAttribute("location") Location location, @PathVariable Integer id
			, Model uiModel, HttpServletRequest httpServletRequest,RedirectAttributes redirectAttributes
			,Locale locale, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new Message("error", messageSource.getMessage("location_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("location", location);
            return "projects/location/update";
	        }  
		Location exitingLocation = locationService.findById(id);
		location.setLocationId(id);
		location.setProject(exitingLocation.getProject());
		
		location = locationService.save(location);
        uiModel.addAttribute("location", location);
        setBreadCrumb(uiModel, "/projects/" + location.getProject().getProjectId() + "?form", "Update Project", "", "Update Location");
        setHeader(uiModel, "Location", "Update Location");
        uiModel.addAttribute("message", new Message("success", messageSource.getMessage("location_save_success", new Object[]{}, locale)));
		return "projects/locations/update";
	}	
	@RequestMapping(value = "/getLocationsJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getLocationsJson(@RequestParam(value="projectId", required=true) String projectId,
			@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		Project project = projectService.findById(Integer.valueOf(projectId));
		Set<Location> locations = project.getLocations();
		String result = Utilities.jSonSerialization(locations);
		return result;
	}

	@RequestMapping(value = "/getProductGroupMakersJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getProductGroupMakersJsGon(@RequestParam(value="projectId", required=true) String projectId,
			@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		String regionType =  httpServletRequest.getParameter("regionType");
		System.out.println("============ start getting product group makers ");
		Project project = projectService.findByIdAndFetchMakers(Integer.valueOf(projectId));
		Set<ProductGroupMaker> productGroupMakers = project.getProductGroupMakers();
		Set<ProductGroup> productGroups = new HashSet<ProductGroup>();
		Iterator<ProductGroupMaker> iterator = productGroupMakers.iterator();
		while(iterator.hasNext()){
			ProductGroupMaker productGroupMaker = iterator.next();
			Category category = productGroupMaker.getCategory().getParentCategory();
			if(regionType.equalsIgnoreCase(Constants.ELEC_TYPE)){
				if(category.getName().equalsIgnoreCase(Constants.ELECT_BOQ)){
					ProductGroup productGroup = productGroupMaker.getProductGroup();
					productGroups.add(productGroup);
				}
			}else if(regionType.equalsIgnoreCase(Constants.MECH_TYPE)){
				if(category.getName().equalsIgnoreCase(Constants.MECH_BOQ)){
					ProductGroup productGroup = productGroupMaker.getProductGroup();
					productGroups.add(productGroup);
				}				
			}
		}
		String result = Utilities.jSonSerialization(productGroups);
		return result;
	}
	
}

