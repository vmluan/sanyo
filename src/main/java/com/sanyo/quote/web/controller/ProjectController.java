package com.sanyo.quote.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
import org.springframework.security.core.GrantedAuthority;
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
import com.sanyo.quote.domain.Currency;
import com.sanyo.quote.domain.CurrencyExchRate;
import com.sanyo.quote.domain.Encounter;
import com.sanyo.quote.domain.Location;
import com.sanyo.quote.domain.LocationJson;
import com.sanyo.quote.domain.LocationOrderHist;
import com.sanyo.quote.domain.MakerProject;
import com.sanyo.quote.domain.Product;
import com.sanyo.quote.domain.ProductGroup;
import com.sanyo.quote.domain.ProductGroupMaker;
import com.sanyo.quote.domain.ProductGroupRate;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.ProjectRevision;
import com.sanyo.quote.domain.ProjectStatus;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.domain.RegionJson;
import com.sanyo.quote.domain.TreeGrid;
import com.sanyo.quote.domain.User;
import com.sanyo.quote.domain.UserJson;
import com.sanyo.quote.domain.UserRegionRole;
import com.sanyo.quote.helper.Constants;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.CategoryService;
import com.sanyo.quote.service.CurrencyExchRateService;
import com.sanyo.quote.service.CurrencyService;
import com.sanyo.quote.service.EncounterService;
import com.sanyo.quote.service.LocationOrderHistService;
import com.sanyo.quote.service.LocationService;
import com.sanyo.quote.service.MakerProjectService;
import com.sanyo.quote.service.ProductGroupMakerService;
import com.sanyo.quote.service.ProductGroupRateService;
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
	String currentProjecs = "";

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
	
	@Autowired
	private ProductGroupMakerService productGroupMakerService;
	
	@Autowired
	private CurrencyService currencyService;
	
	@Autowired
	private CurrencyExchRateService currencyExchRateService;
	
	@Autowired
	private MakerProjectService makerProjectService;
	
	@Autowired
	private LocationOrderHistService locationOrderHistService;
	
	@Autowired
	private ProductGroupRateService productGroupRateService;
	
	private Validator validator;
	
	private String projectsUrl="/projects?status=ongoing";
	
	public ProjectController(){
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
	}
	

	//handle /projects
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel, HttpServletRequest request) {
		logger.info("Listing projects");
		
		setHeader(uiModel, "Projects", "List of all projects");
		setUser(uiModel);
		String status = request.getParameter("status");
		//String new_price_status = request.getParameter("needUpdatePrice");
		projectsUrl ="/projects?status=" + status;
		uiModel.addAttribute("projectStatus", status);
		if(status != null && status.equalsIgnoreCase(Constants.PROJECT_ONGOING))
		{
			this.currentProjecs = Constants.PROJECT_ONGOING_TEXT;
			
		}
		else if(status != null && status.equalsIgnoreCase(Constants.PROJECT_FINISHED))
			{
				this.currentProjecs = Constants.PROJECT_FINISHED_TEXT;
			}
		////////count project need update price and update column needUpdatePrice for project;
		int sum =0 ;
		ArrayList statusPrice = new ArrayList();
		if(Utilities.hasAdminRole()){ //if admin
			List<Project> project = projectService.findAll();		
			for(Project itemProject:project)
			{
				if(itemProject.getStatus().name()=="ONGOING")
				{
					if(getStatusNeedUpdatePrice(itemProject))
					{
						itemProject.setNeedUpdatePrice(true);
						//projectService.save(itemProject);
						sum++;
					}
					else
					{
						itemProject.setNeedUpdatePrice(false);					
					}
					projectService.save(itemProject);
					statusPrice.add(itemProject.isNeedUpdatePrice());
				}
			}
		}
		
		uiModel.addAttribute("StatusNeedUpdatePrice", statusPrice);
		uiModel.addAttribute("projectNeedUpdate", sum);
		resetLinks();
		addToLinks(currentProjecs, projectsUrl);
		setBreadCrumb(uiModel, "/", "Home", "/projects", "Projects");
		return "projects/list";
	}
	// find project do not update price
	private boolean getStatusNeedUpdatePrice(Project idproject)
	{
		List<Location> location = locationService.findByProject(idproject);
		for(Location itemLocation:location)
		{
			List<Region> region = regionService.findByLocation(itemLocation);
			for(Region itemRegion:region)
			{
				List<Encounter> encounter = encounterService.findByRegion(itemRegion);
				for(Encounter itemEncounter:encounter)
				{
					if(itemEncounter.isNeedUpdatePrice())
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	// handle screen for updating. Just display form for form edit.
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		Project project = projectService.findById(id);
        uiModel.addAttribute("project", project);
        
        setHeader(uiModel, "Project Detail", "Contains detail information including regions and assigned users");
        setUser(uiModel);
        initialize(uiModel);
        
        resetLinks();
        addToLinks(currentProjecs, projectsUrl);
        addToLinks("Project Detail", "");
        setBreadCrumb(uiModel, projectsUrl, "Projects", "", "Project Detail");
        
        return "projects/update";
	}
	
	private void initialize(Model uiModel){
		List<Currency> currencies = currencyService.findAll();
		Map<String,String> currencyList = new LinkedHashMap<String,String>();
		for(Currency currency : currencies){
			currencyList.put(currency.getCurrencyId().toString(),currency.getCurrencyName() );
		}
		uiModel.addAttribute("currencyList", currencyList);
	}
	private void loadDefaultCurrencies(Project project, Model uiModel){
		if(project.getCurrency() == null){
			// in case of new project
			//set VND as deafault value
			Currency vndCurrency = currencyService.findByCurrencyCode("VND");
			if(vndCurrency != null)
				project.setCurrencyId(vndCurrency.getCurrencyId());
			List<CurrencyExchRate> rates = currencyExchRateService.findLatestList();
			for(CurrencyExchRate rate : rates){
				if(rate.getSourceCurrency().getCurrencyCode().equalsIgnoreCase("USD")
						&& rate.getTargetCurrency().getCurrencyCode().equalsIgnoreCase("VND"))
					project.setUsdToVnd(rate.getExchangeRateValue());
				if(rate.getSourceCurrency().getCurrencyCode().equalsIgnoreCase("VND")
						&& rate.getTargetCurrency().getCurrencyCode().equalsIgnoreCase("USD"))
					project.setVndToUsd(rate.getExchangeRateValue());				
				if(rate.getSourceCurrency().getCurrencyCode().equalsIgnoreCase("USD")
						&& rate.getTargetCurrency().getCurrencyCode().equalsIgnoreCase("JPY"))
					project.setUsdToJpy(rate.getExchangeRateValue());
				if(rate.getSourceCurrency().getCurrencyCode().equalsIgnoreCase("JPY")
						&& rate.getTargetCurrency().getCurrencyCode().equalsIgnoreCase("USD"))
					project.setJpyToUsd(rate.getExchangeRateValue());
				if(rate.getSourceCurrency().getCurrencyCode().equalsIgnoreCase("JPY")
						&& rate.getTargetCurrency().getCurrencyCode().equalsIgnoreCase("VND"))
					project.setJpyToVnd(rate.getExchangeRateValue());
				if(rate.getSourceCurrency().getCurrencyCode().equalsIgnoreCase("VND")
						&& rate.getTargetCurrency().getCurrencyCode().equalsIgnoreCase("JPY"))
					project.setVndToJpy(rate.getExchangeRateValue());				
			}
		}
	}
	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
		Project project = new Project();
        uiModel.addAttribute("project", project);
        
        setHeader(uiModel, "Create new project", "");
        setUser(uiModel);
        initialize(uiModel);
        loadDefaultCurrencies(project, uiModel);
        
        resetLinks();
        addToLinks(currentProjecs, projectsUrl);
        addToLinks("New Project", "");
        setBreadCrumb(uiModel, projectsUrl, "Projects", "", "New Project");
        
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
        project.setStatus(ProjectStatus.ONGOING);
        project.setCurrency(currencyService.findById(project.getCurrencyId()));
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
		Project existingProject = projectService.findById(id);
		project.setStatus(existingProject.getStatus());
        uiModel.asMap().clear();
        project.setProjectId(id);
        uiModel.addAttribute("message", new Message("success", messageSource.getMessage("project_save_success", new Object[]{}, locale)));        
        uiModel.addAttribute("project", project);
        project.setLmodDate(new Date());
        project.setLastModifiedBy(Utilities.getCurrentUser().getUsername());
        project.setCurrency(currencyService.findById(project.getCurrencyId()));
        keepCurrentStatuses(existingProject, project);
        projectService.save(project);
        initialize(uiModel);
        setBreadCrumb(uiModel, projectsUrl, "Projects", "", "Project Detail");
        setHeader(uiModel, "Project Detail", "Contains detail information including regions and assigned users");
        return "projects/update";
    }
	private void keepCurrentStatuses(Project existingProject, Project newProject){
		newProject.setCreatedBy(existingProject.getCreatedBy());
		newProject.setCreatedDate(existingProject.getCreatedDate());
	}
	
	private Set<ProjectRevision> getLatestRevision(Project project){
		ProjectRevision latest = projectRevisionService.findLatestRevision(project);
		Set<ProjectRevision> tempRevisions = new HashSet<ProjectRevision>();
		if(latest != null)
			tempRevisions.add(latest);
		return tempRevisions;
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
		
		String status = httpServletRequest.getParameter("status");

		//if user has ROLE_ADMIN, he can see all projects
		//otherwise, he can see assigned project only
		org.springframework.security.core.userdetails.User user = Utilities.getCurrentUser();
		for( GrantedAuthority a : user.getAuthorities()){
			logger.info(" ========== GrantedAuthority =" + a.getAuthority());
		}
		List<Project> projects;
		if(!Utilities.hasAdminRole()){
			List<UserRegionRole> userRegionRoles = userRegionRoleService.findAssignedRegionsByUserName(user.getUsername());
			TreeMap<Integer, Project> projectTree = new TreeMap<Integer, Project>();
			for(UserRegionRole role : userRegionRoles){
				Region region = role.getRegion();
				Location location = region.getLocation();
				Project project = location.getProject();
				Set<ProjectRevision> tempRevisions = getLatestRevision(project);
				project.setRevisions(tempRevisions);
				
				if(status != null && status.equalsIgnoreCase("ongoing")){
					if(project.getStatus().toString().equals(ProjectStatus.ONGOING))
						projectTree.put(project.getProjectId(), project);
				}else if(status != null && status.equalsIgnoreCase("closed")){
					if(project.getStatus().toString().equals(ProjectStatus.FINISH))
						projectTree.put(project.getProjectId(), project);
				}
				logger.info(" ========== project name =" + project.getProjectName());
				
			}
			
			projects = new ArrayList<Project>(projectTree.values());
		}else{
			if(status != null && status.equalsIgnoreCase("ongoing"))
				projects = projectService.findByStatus(ProjectStatus.ONGOING);
			else if(status != null && status.equalsIgnoreCase("closed"))
				projects = projectService.findByStatus(ProjectStatus.FINISH);
			else
				projects = null;
		}
		for(Project project : projects){
			Set<ProjectRevision> tempRevisions = getLatestRevision(project);
			project.setRevisions(tempRevisions);
		}
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
		
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		Set<Location> locations = project.getLocations();
//		Set<Region> totalRegions = new HashSet<Region>();
		for(Location location : locations){
			Set<Region> regions  = location.getRegions();
			if(regions != null && regions.size() >0)
				treeGrids.addAll(Utilities.createJsonTreeGridForRegions(regions, location));
		}
//		Iterator<Region> iterator = totalRegions.iterator();
//		Set<Region> assginedRegions = new HashSet<Region>();
//		
//		while(iterator.hasNext()){
//			Region region = iterator.next();
//			Region regionWithUsers = regionService.findByIdAndFetchUserRegionRolesEagerly(region.getRegionId());
//			if(regionWithUsers != null)
//				assginedRegions.add(regionWithUsers);
//			else{
//				regionWithUsers = regionService.findById(region.getRegionId());
//				Set<UserRegionRole> emptyUsers = new HashSet<UserRegionRole>();
//				regionWithUsers.setUserRegionRoles(emptyUsers);
//				assginedRegions.add(regionWithUsers);
//			}
//		}
//		
		String result = Utilities.jSonSerialization(treeGrids);
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
			, @RequestParam(value="projectId", required=false) String projectId
			, @RequestParam(value="regionType", required=false) String regionType
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		System.out.println("start getting assinged regions of location");
		String[] locationIds = locationId.split(",");
		TreeMap<String, Region> treeRegions = new TreeMap<String, Region>();
		boolean isAllLocation = false;
//		for(String id : locationIds){
//			if(id.equalsIgnoreCase("0")){
//				isAllLocation =true;
//				break;
//			}
//		}
		if(isAllLocation){
			Project project = projectService.findById(Integer.valueOf(projectId));
			List<Location> locations = locationService.findByProject(project);
			for(Location location : locations){
				List<Region> regions = regionService.findByLocation(location);
				for(Region region: regions){
					treeRegions.put(String.valueOf(region.getRegionId()), region);
				}
			}
		}else{
			for(String id : locationIds){
				if(id.equalsIgnoreCase("0"))
					continue;
				Location location = locationService.findById(Integer.valueOf(id));
				List<Region> regions = regionService.findByLocation(location);
				for(Region region: regions){
					treeRegions.put(String.valueOf(region.getRegionId()), region);
				}
			}	
		}
		List<Region> finalRegions = new ArrayList<Region>(treeRegions.values());
		List<Region> selectedRegions = new ArrayList<Region>();
		Region regionAll = new Region();
		regionAll.setRegionName("All");
		regionAll.setRegionId(0);
		
		//next is filter by regionType
		if(regionType != null){
			if(regionType.equalsIgnoreCase(Constants.ELEC_TYPE)
					|| regionType.equalsIgnoreCase(Constants.MECH_TYPE)){
				selectedRegions.add(0, regionAll);
				for(Region region : finalRegions){
					Category category = region.getCategory().getParentCategory();
					if((category.getName().equalsIgnoreCase(Constants.MECH_BOQ)
							&& regionType.equalsIgnoreCase(Constants.MECH_TYPE))
							||(category.getName().equalsIgnoreCase(Constants.ELECT_BOQ)
							&& regionType.equalsIgnoreCase(Constants.ELEC_TYPE)
							)){
						selectedRegions.add(region);
					}
				}
				return Utilities.jSonSerialization(selectedRegions);
			}
		}
		 
		
		finalRegions.add(0,regionAll);
		String result = Utilities.jSonSerialization(finalRegions);
		return result;
	}
	// handle screen for create new assigned regions.
	@RequestMapping(value = "/{id}", params = "regions", method = RequestMethod.GET)
    public String createNewAssignedRegions(@PathVariable("id") Integer id, Model uiModel) {
		Project project = projectService.findById(id);
        uiModel.addAttribute("project", project);
        setCategories(uiModel);
        
        
        resetLinks();
        addToLinks(currentProjecs, projectsUrl);
        addToLinks("Project Detail", "/projects/" + id + "?form");
        addToLinks("New Region", "");
        setBreadCrumb(uiModel, projectsUrl, "Projects", "", "Project Detail");
        return "projects/update";
	}
	// handle screen for create new assigned regions.
	@RequestMapping(value = "/{id}", params = "regions", method = RequestMethod.POST)
    public String saveNewAssignedRegions(@ModelAttribute("region") Region Region, @PathVariable Integer id, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale, BindingResult bindingResult) {
		Project project = projectService.findById(id);
        uiModel.addAttribute("project", project);
        setCategories(uiModel);
        setBreadCrumb(uiModel, projectsUrl, "Projects", "", "Project Detail");
        return "projects/update";
	}
	
	private void setCategories(Model uiModel){
		uiModel.addAttribute("parentCategories", categoryService.findAll());
	}
	
	
	private void saveNewRegion(RegionJson regionJson, Location existingLocation ){
		Category category = categoryService.findById(regionJson.getRegionId());
		
		Region region = getExistingRegion(existingLocation, category.getName());
		if(region == null){
			region = new Region();
			region.setCategory(category);
			if(regionJson.getRegionName() != null)
				region.setRegionName(regionJson.getRegionName());
			else
				region.setRegionName(category.getName());
			region.setRegionDesc(category.getDesc());
			if(existingLocation != null)
				region.setLocation(existingLocation);
			region.setRegionNameVN(category.getNameVN());
			region = regionService.save(region);
		}else{
			if(existingLocation != null)
				region.setLocation(existingLocation);
			if(regionJson.getRegionName() != null)
				if(!regionJson.getRegionName().equalsIgnoreCase(region.getRegionName())){
					region.setRegionName(regionJson.getRegionName());
					region.setRegionNameVN(category.getNameVN());
					regionService.save(region);
				}
		}

		List<UserJson> userJsons = regionJson.getUsers();
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
			
		}
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
				boolean hasAllChecked = false;
//				for(LocationJson locationJson : regionJson.getLocations()){
//					if(locationJson.getLocationName().trim().equalsIgnoreCase("ALL")){
//						hasAllChecked = true;
//						break;
//					}
//				}
				if(hasAllChecked){
					for(Location location : locations){
						saveNewRegion(regionJson, location);
					}
				}else{
					
					for(LocationJson locationJson : regionJson.getLocations()){
						if(locationJson.getLocationName().trim().equalsIgnoreCase("ALL")){
							continue;
						}
						Location existingLocation = null;
						
						for(Location location : locations){
							if(location.getLocationName().equalsIgnoreCase(locationJson.getLocationName().trim())){
								existingLocation = location;
							}
						}
						if(existingLocation != null)
							saveNewRegion(regionJson, existingLocation);					
					}
				}
				
				
//				Category category = categoryService.findById(regionJson.getRegionId());
//				
//				Region region = getExistingRegion(existingLocation, category.getName());
//				if(region == null){
//					region = new Region();
//					region.setCategory(category);
//					if(regionJson.getRegionName() != null)
//						region.setRegionName(regionJson.getRegionName());
//					else
//						region.setRegionName(category.getName());
//					region.setRegionDesc(category.getDesc());
//					if(existingLocation != null)
//						region.setLocation(existingLocation);
//					region = regionService.save(region);
//				}else{
//					if(existingLocation != null)
//						region.setLocation(existingLocation);
//					if(regionJson.getRegionName() != null)
//						if(!regionJson.getRegionName().equalsIgnoreCase(region.getRegionName())){
//							region.setRegionName(regionJson.getRegionName());
//							regionService.save(region);
//						}
//				}
//
//				List<UserJson> userJsons = regionJson.getUsers();
//				for(UserJson userJson : userJsons){
//					
//					UserRegionRole userRegionRole = getExistingUser(region,userJson.getUserName());
//					if(userRegionRole == null){
//						User user = userService.findByUserName(userJson.getUserName());
//						userRegionRole = new UserRegionRole();
//						userRegionRole.setUser(user);
//						userRegionRole.setRoleName(userJson.getRoleName()); //get from request
//						userRegionRole.setRegion(region);
//						userRegionRole.setUserName(user.getUsername());
//						userRegionRole = userRegionRoleService.save(userRegionRole);
//					}
//					
//				}
								
			}
		}
	}
	@RequestMapping(value = "regions/{id}", params = "form", method = RequestMethod.GET)
	public String showRegions(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		Region region = regionService.findById(Integer.valueOf(id));
		uiModel.addAttribute("region", region);
		setUser(uiModel);
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
        
        setHeader(uiModel, "Revision", "Create a new revision");
        setUser(uiModel);
        
        resetLinks();
        addToLinks(currentProjecs, projectsUrl);
        addToLinks("Project Detail", "/projects/" + id + "?form");
        addToLinks("New Revision", "");
        setBreadCrumb(uiModel, projectsUrl, "Projects", "", "Create Revision");
        
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
		projectRevision.setLmodDate(new Date());
		projectRevision = projectRevisionService.save(projectRevision);
		uiModel.asMap().clear();
		setBreadCrumb(uiModel, "/projects/" + id + "?form", "Update Project", "", "Update Revision");
		setHeader(uiModel, "Revision", "Detail of revision");
		redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("revision_save_success", new Object[]{}, locale)));
//		return "redirect:/projects/revisions/" + UrlUtil.encodeUrlPathSegment(projectRevision.getRevisionId().toString(), httpServletRequest) + "?form";
		return "redirect:/projects/" +project.getProjectId() +"/revisions?form";
	}
	@RequestMapping(value = "/revisions/{id}", params = "form", method = RequestMethod.GET)
	public String  callEditProjectRevisions(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		ProjectRevision projectRevision = projectRevisionService.findById(id);
        uiModel.addAttribute("projectRevision", projectRevision);
        
        setHeader(uiModel, "Revision", "Update revision");
        setUser(uiModel);
        
        resetLinks();
        addToLinks(currentProjecs, projectsUrl);
        addToLinks("Project Detail", "/projects/" + id + "?form");
        addToLinks("Update Revision", "");
        setBreadCrumb(uiModel, "/projects/" + projectRevision.getProject().getProjectId() + "?form", "Update Project", "", "Update Revision");
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
        
        setHeader(uiModel, "Location", "Create a new Location");
        setUser(uiModel);
        
        resetLinks();
        addToLinks(currentProjecs, projectsUrl);
        addToLinks("Project Detail", "/projects/" + id + "?form");
        addToLinks("Update Location", "");
        setBreadCrumb(uiModel, "/projects", "Projects", "", "Create Location");
        
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
//		return "redirect:/projects/locations/" + UrlUtil.encodeUrlPathSegment(location.getLocationId().toString(), httpServletRequest) + "?form";
		return "redirect:/projects/" +project.getProjectId() +"/locations/?form";
	}
	
	@RequestMapping(value = "/locations/{id}", params = "form", method = RequestMethod.GET)
	public String  callEditLocation(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		Location location = locationService.findById(id);
        uiModel.addAttribute("location", location);
        
        setHeader(uiModel, "Location", "Update location");
        setUser(uiModel);
        
        resetLinks();
        addToLinks(currentProjecs, projectsUrl);
        addToLinks("Project Detail", "/projects/" + id + "?form");
        addToLinks("New Location", "");
        setBreadCrumb(uiModel, "/projects/" + location.getProject().getProjectId() + "?form", "Update Project", "", "Update Location");
        
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
	@RequestMapping(value = "/locations/{id}", params = "updateOrder", method = RequestMethod.POST)
	@ResponseBody
	public void  updateLocationOrder(@PathVariable Integer id
			, @RequestParam(value="orderNo", required=true) String orderNo
			, HttpServletRequest httpServletRequest){
		System.out.println("=============== update location order");
		Location location = locationService.findById(id);
		location.setOrderNo(Integer.valueOf(orderNo));
		locationService.save(location);
	}
	@RequestMapping(value = "/locations/{id}", params = "updateOrder2", method = RequestMethod.POST)
	@ResponseBody
	public void  updateLocationOrder2(@PathVariable Integer id
			, @RequestParam(value="startPos", required=true) Integer startPos
			, @RequestParam(value="endPos", required=true) Integer endPos
			, HttpServletRequest httpServletRequest){
		Location location = locationService.findById(id);
//		location.setOrderNo(endPos);
//		locationService.save(location);
		if(location.getOrderNo()==startPos){
			LocationOrderHist locationOrderHist = new LocationOrderHist();
			locationOrderHist.setLocationId(id);
			locationOrderHist.setFromPos(startPos);
			locationOrderHist.setToPos(endPos);
			locationOrderHistService.save(locationOrderHist);
		}
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
	@RequestMapping(value = "/getLocationsJsonWithAll", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getLocationsJsonWithAll(@RequestParam(value="projectId", required=true) String projectId,
			@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		Project project = projectService.findById(Integer.valueOf(projectId));
		Location  locationAll = new Location();
		locationAll.setLocationName("All");
		locationAll.setLocationId(0);
		Set<Location> locations = project.getLocations();
		
		TreeMap<Integer, Location> tree = new TreeMap<Integer, Location>();
		if(locations.size() > 0){
			tree.put(locationAll.getLocationId(), locationAll);
			for(Location location: locations){
				tree.put(location.getLocationId(), location);
			}
		}
		List<Location> list = new ArrayList<Location>();
		for(Integer locationId : tree.keySet()){
			list.add(tree.get(locationId));
		}
		String result = Utilities.jSonSerialization(list);
		return result;
	}
//	@RequestMapping(value = "/getProductGroupMakersJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
//	@ResponseBody
//	public String getProductGroupMakersJsGon(@RequestParam(value="projectId", required=true) String projectId,
//			@RequestParam(value="filterscount", required=false) String filterscount
//			, @RequestParam(value="groupscount", required=false) String groupscount
//			, @RequestParam(value="pagenum", required=false) Integer pagenum
//			, @RequestParam(value="pagesize", required=false) Integer pagesize
//			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
//			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
//			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
//		
//		String regionType =  httpServletRequest.getParameter("regionType");
//		System.out.println("============ start getting product group makers ");
//		Project project = projectService.findByIdAndFetchMakers(Integer.valueOf(projectId));
//		Set<ProductGroupMaker> productGroupMakers = project.getProductGroupMakers();
//		Set<ProductGroup> productGroups = new HashSet<ProductGroup>();
//		Iterator<ProductGroupMaker> iterator = productGroupMakers.iterator();
//		while(iterator.hasNext()){
//			ProductGroupMaker productGroupMaker = iterator.next();
//			Category category = productGroupMaker.getCategory().getParentCategory();
//			if(regionType.equalsIgnoreCase(Constants.ELEC_TYPE)){
//				if(category.getName().equalsIgnoreCase(Constants.ELECT_BOQ)){
//					ProductGroup productGroup = productGroupMaker.getProductGroup();
//					productGroups.add(productGroup);
//				}
//			}else if(regionType.equalsIgnoreCase(Constants.MECH_TYPE)){
//				if(category.getName().equalsIgnoreCase(Constants.MECH_BOQ)){
//					ProductGroup productGroup = productGroupMaker.getProductGroup();
//					productGroups.add(productGroup);
//				}				
//			}
//		}
//		String result = Utilities.jSonSerialization(productGroups);
//		return result;
//	}
	
	private void setRatesForProductGroup(ProductGroup productGroup){
		List<ProductGroupRate> productGroupRates = productGroupRateService.findByProductGroup(productGroup);
		if(productGroupRates != null && productGroupRates.size()>0){
			ProductGroupRate rate = productGroupRates.get(0);
			productGroup.setAllowance(rate.getAllowance());
			productGroup.setDiscount(rate.getDiscount());
		}
	}
	
	/*
	 * get list of product group that are assigned to the project. it is defined in Maker sheet
	 */
	@RequestMapping(value = "/getProductGroupMakersJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getProductGroupMakersJsGon(@RequestParam(value="projectId", required=true) String projectId,
			@RequestParam(value="regionId", required=false) Integer regionId,
			@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		String regionType =  httpServletRequest.getParameter("regionType");
		System.out.println("============ start getting maker of project ");
		Project project = projectService.findById(Integer.valueOf(projectId));
		
		if(regionId != null){
			Region region = regionService.findById(regionId);
			Category assignedCategory = region.getCategory();
			List<MakerProject> makerProjects = makerProjectService.findByProjectAndCategory(project, assignedCategory);
			Set<ProductGroup> productGroups = new HashSet<ProductGroup>();
			for(MakerProject makerProject : makerProjects){
				ProductGroupMaker productGroupMaker = makerProject.getProductGroupMaker();
				Category category = makerProject.getCategory().getParentCategory();
				if(regionType.equalsIgnoreCase(Constants.ELEC_TYPE)){
					if(category.getName().equalsIgnoreCase(Constants.ELECT_BOQ)){
						ProductGroup productGroup = productGroupMaker.getProductGroup();
						setRatesForProductGroup(productGroup);
						productGroups.add(productGroup);
					}
				}else if(regionType.equalsIgnoreCase(Constants.MECH_TYPE)){
					if(category.getName().equalsIgnoreCase(Constants.MECH_BOQ)){
						ProductGroup productGroup = productGroupMaker.getProductGroup();
						setRatesForProductGroup(productGroup);
						productGroups.add(productGroup);
					}				
				}
			}
			String result = Utilities.jSonSerialization(productGroups);
			return result;
		}
		
		return "[]";

	}

	@RequestMapping(value = "/{id}", params = "clone", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void cloneProject(@PathVariable("id") Integer id, Model uiModel
			,HttpServletRequest httpServletRequest) throws CloneNotSupportedException{
		Project project = projectService.findById(id);
		
		Project clonedProject = project.clone();
		clonedProject.setProjectId(null);
		clonedProject = projectService.save(clonedProject);
		cloneMakerProject(project, clonedProject);
		cloneProductGroupMakers(project.getProjectId(), clonedProject);
		this.cloneLocation(project.getProjectId(), clonedProject);

	}
	public void cloneLocation(Integer projectId, Project clonedProject) throws CloneNotSupportedException{
		List<Location> locations = projectService.findLocations(projectId);
		Set<Location> clonedLocations = Utilities.cloneLocations(locations);
		for(Location location : clonedLocations){
			location.setProject(clonedProject);
			Integer locationId = location.getLocationId();
			location.setLocationId(null);
			location = locationService.save(location);
			this.cloneRegion(locationId, location);
		}
	}
	public void cloneRegion(Integer locationId, Location clonedLocation) throws CloneNotSupportedException{
		List<Region> regions = locationService.findRegions(locationId);
		Set<Region> clonedRegions = Utilities.cloneRegions(regions);
		for(Region region : clonedRegions){
			Integer regionId = region.getRegionId();
			
			region.setRegionId(null);
			region.setLocation(clonedLocation);
			region = regionService.save(region);
			this.cloneUserRegionRole(regionId, region);
			this.cloneEncounters(regionId, region);
		}
		
	}
	public void cloneEncounters(Integer regionId, Region clonedRegion) throws CloneNotSupportedException{
		List<Encounter> encounters = regionService.getEncounters(regionId);
		Set<Encounter> clonedEncounters = Utilities.cloneEncounters(encounters);
		for(Encounter encounter : clonedEncounters){
			encounter.setEncounterID(null);
			encounter.setRegion(clonedRegion);
			encounterService.save(encounter);
		}
	}
	public void cloneProductGroupMakers(Integer sourceProjectId, Project clonedObj) throws CloneNotSupportedException{
//		List<ProductGroupMaker> productGroupMakers = projectService.findProductGroupMakers(sourceProjectId);
//		Set<ProductGroupMaker> clonedList = Utilities.cloneProductGroupMaker(productGroupMakers);
//		for(ProductGroupMaker pg : clonedList){
//			pg.setId(null);
//			productGroupMakerService.save(pg);
//		}
	}
	public void cloneUserRegionRole(Integer sourceRegionId, Region clonedRegion) throws CloneNotSupportedException{
		List<UserRegionRole> userRegionRoles = regionService.getUserRegionRoles(sourceRegionId);
		Set<UserRegionRole> clonedList = Utilities.cloneUserRegionRole(userRegionRoles);
		for(UserRegionRole role : clonedList){
			role.setId(null);
			role.setRegion(clonedRegion);
			userRegionRoleService.save(role);
		}
	}
	public void cloneMakerProject(Project sourecProject, Project clonedProject) throws CloneNotSupportedException{
		List<MakerProject> makerProjects = makerProjectService.findByProject(sourecProject);
		for(MakerProject makerProject : makerProjects){
			MakerProject clonedMP = makerProject.clone();
			clonedMP.setProject(clonedProject);
			makerProjectService.save(clonedMP);
		}
	}
	@Transactional
	@RequestMapping(value = "/{id}", params = "delete", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteProject(@PathVariable("id") Integer id, Model uiModel
			,HttpServletRequest httpServletRequest) throws CloneNotSupportedException{
		projectService.delete(id);

	}
	@Transactional
	@RequestMapping(value = "/{id}", params = "close", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void closeProject(@PathVariable("id") Integer id, Model uiModel
			,HttpServletRequest httpServletRequest) throws CloneNotSupportedException{
		Project project = projectService.findById(id);
		project.setLmodDate(new Date());
		project.setLastModifiedBy(Utilities.getCurrentUser().getUsername());
		project.setStatus(ProjectStatus.FINISH);
		projectService.save(project);
		

	}
	////post new price update for project when click update in ongoing project
	//@Transactional
	@RequestMapping(value = "/{id}", params = "update", method = RequestMethod.POST)
	//@ResponseStatus(value = HttpStatus.OK)
	//@ResponseBody
	public @ResponseBody
	int UpdatePriceProject(@PathVariable("id") Integer id, Model uiModel
			,HttpServletRequest httpServletRequest) throws CloneNotSupportedException{
		//return 1;
		int sum =0;
		Project project = projectService.findById(id);
		if(project!=null)		
		{
			if(project.getStatus().name()=="ONGOING") //ongoing project
			{
				// get all loaction in project
				List<Location> location = locationService.findByProject(project);
				for(Location itemLocation:location)
				{
					// get all region in location
					List<Region> region = regionService.findByLocation(itemLocation);
					for(Region itemRegion:region)
					{
						// get all encounter in region
						List<Encounter> encounter = encounterService.findByRegion(itemRegion);
						for(Encounter itemEncounter:encounter)
						{
							if(itemEncounter.isNeedUpdatePrice()) // 
							{
								updateEncounter(itemEncounter.getProduct(),itemEncounter,project.getUsdToVnd()); //update table encounter
								sum+=1;
							}
						}
					}
				}
			}
		}
		return sum;
	}
	private void updateEncounter(Product idproduct,Encounter encounter,Float usdToVnd)
	{
		Product product = productService.findById(idproduct.getProductID());
		
		float USD = 0;
		float VND =0;
		float labour = 0 ;
		if(product.getMat_w_o_Tax_USD()!=null)
			USD = product.getMat_w_o_Tax_USD();
		if(product.getMat_w_o_Tax_VND()!=null)
			VND = product.getMat_w_o_Tax_VND();
		if(product.getLabour()!=null)
			labour = product.getLabour();
		
		encounter.setMat_w_o_Tax_USD(USD);
		encounter.setMat_w_o_Tax_VND(VND);
		encounter.setLabour(labour);
		encounter.setUnit_Price_After_Discount(VND/usdToVnd+USD);
		encounter.setUnit_Price_W_Tax_Labour(labour*encounter.getSubcon_Profit());
		encounter.setNeedUpdatePrice(false);
		encounterService.save(encounter);	
		
	}
	
	@Transactional
	@RequestMapping(value = "/revisions/{id}", params = "delete", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
    public void deleteProjectRevisions(@PathVariable("id") Integer id, Model uiModel) {
		projectRevisionService.delete(id);
		
	}
	
	@Transactional
	@RequestMapping(value = "/locations/{id}", params = "delete", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
    public void deleteLocation(@PathVariable("id") Integer id, Model uiModel) {
		locationService.delete(id);
		
	}
}

