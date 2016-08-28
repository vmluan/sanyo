package com.sanyo.quote.web.controller;

import com.sanyo.quote.domain.*;
import com.sanyo.quote.domain.Currency;
import com.sanyo.quote.helper.Constants;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.*;
import com.sanyo.quote.web.form.DataTableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/*
 * Controller for Encounter 
 */
@Controller
@RequestMapping(value = "/quotation")
public class Quotation extends CommonController {
	final Logger logger = LoggerFactory.getLogger(Quotation.class);
	@Autowired
	private EncounterService encounterService;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private MakerService makerService;
	
	@Autowired
	private ProductGroupService productGroupService;
	
	@Autowired
	private ProductGroupMakerService productGroupMakerService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MakerProjectService makerProjectService;

	@Autowired
	private ProductGroupRateService productGroupRateService;

	@Autowired
	private TotalMaterialLabourService totalMaterialLabourService;

	@Autowired
	private EncounterOrderHistService encounterOrderHistService;
	@Autowired
	private UserRegionRoleService userRegionRoleService;

	@RequestMapping(method = RequestMethod.GET)
	public String getQuotationPage(@RequestParam(value="projectId", required=true) String projectId,
			Model uiModel,HttpServletRequest httpServletRequest) {
		Project project = projectService.findById(Integer.valueOf(projectId));
		uiModel.addAttribute("projectId", projectId);
		uiModel.addAttribute("hasPrivilegeElec", hasPrivilege(project, Constants.ELECT_BOQ));
		uiModel.addAttribute("hasPrivilegeMec", hasPrivilege(project, Constants.MECH_BOQ));
		uiModel.addAttribute("hasAdminRole", hasAdminRole());
		uiModel.addAttribute("defautTab", httpServletRequest.getParameter("defautTab"));
		String status = "";
		String currentProjecs = "";
		if(project.getStatus().equals(ProjectStatus.ONGOING)){
			status=Constants.PROJECT_ONGOING;
			currentProjecs = Constants.PROJECT_ONGOING_TEXT;
		}
		else if(project.getStatus().equals(ProjectStatus.FINISH)){
			status = Constants.PROJECT_FINISHED;
			currentProjecs = Constants.PROJECT_FINISHED_TEXT;
		}
		String projectsUrl ="/projects?status=" + status;
		uiModel.addAttribute("projectStatus", status);

		boolean isAdminrole = Utilities.hasAdminRole();
		uiModel.addAttribute("isAdmin", isAdminrole);

		resetLinks();
		addToLinks(currentProjecs, projectsUrl);
		addToLinks("Quotation", "");
		addToLinks(project.getProjectName(), "");
		setUser(uiModel);
		setBreadCrumb(uiModel);
		return "quotation/index";
	}
	@RequestMapping(value = "/{id}/addmakerlist", params = "form", method = RequestMethod.GET)
	public String getMakerPage(@PathVariable("id") String projectId,
			Model uiModel,HttpServletRequest httpServletRequest) {
		uiModel.addAttribute("projectId", projectId);
		uiModel.addAttribute("regionType", httpServletRequest.getParameter("type"));
		setUser(uiModel);
		return "quotation/makerlist";
	}
	//save MakerList
	@RequestMapping(value = "/{id}/addmakerlist", params = "form", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void saveMakers(@RequestBody final MakerJson makerJson, @PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		System.out.println("=================================== saving Maker list");
		String makerIds = makerJson.getMakerId();
		String[] tempArr = makerIds.split(",");
		for(String makerId : tempArr){
			Maker maker = makerService.findById(Integer.valueOf(makerId.trim().replace(" ","")));
			ProductGroup productGroup = productGroupService.findById(Integer.valueOf(makerJson.getProductGroupId()));
			List<ProductGroupMaker> productGroupMakers = productGroupMakerService.findByProductGroupAndMaker(productGroup, maker);
			
			MakerProject makerProject = null;
			if(makerJson.getId() != null && makerJson.getId() >0){
				makerProject = makerProjectService.findById(makerJson.getId());
			}
			if(makerProject == null){
				makerProject = new MakerProject();
				makerProject.setCreatedBy(Utilities.getCurrentUser().getUsername());
			}
			
			if(productGroupMakers != null && productGroupMakers.size() >0){
				makerProject.setProductGroupMaker(productGroupMakers.get(0));
			}
			Category category = categoryService.findById(Integer.valueOf(makerJson.getCategoryId()));
			Project project = projectService.findById(id);
			
			
			makerProject.setDelivery(makerJson.getDelivery());
			makerProject.setModelNo(makerJson.getModelNo());
			makerProject.setRemark(makerJson.getRemarks());
			makerProject.setCategory(category);
			makerProject.setProject(project);
			makerProject.setEquivalent(makerJson.getEquivalent());
			
			makerProjectService.save(makerProject);
		}

	}
	
	//get all assigned products of a specific project.
	@RequestMapping(value = "/{id}/addquotation", params = "form", method = RequestMethod.GET)
	public String addQuotation(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest) {
		
		Project project = projectService.findById(id);
		if(isNeedUpdatePrice(project))
			uiModel.addAttribute("needUpdatePrice", true);
		uiModel.addAttribute("regionType", httpServletRequest.getParameter("type"));
		uiModel.addAttribute("project", project);
		uiModel.addAttribute("hasPrivilegeElec", hasPrivilege(project, Constants.ELECT_BOQ));
		uiModel.addAttribute("hasPrivilegeMec", hasPrivilege(project, Constants.MECH_BOQ));
		uiModel.addAttribute("currency", project.getCurrency().getCurrencyCode());
		setUser(uiModel);

		return "quotation/create";
	}
	private com.sanyo.quote.domain.User getLoggedInUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.isAuthenticated()){
			if(!authentication.getPrincipal().toString().equalsIgnoreCase("anonymousUser")){
				org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
				logger.info("========= preHandle. username = " + user.getUsername());
				com.sanyo.quote.domain.User userSanyo = userService.findByUserName(user.getUsername());
				return userSanyo;
			}
		}
		return null;
	}
	private boolean hasAdminRole(){
		com.sanyo.quote.domain.User userSanyo = getLoggedInUser();
		List<Group> rolesList = userSanyo.getGrouplist();
		for(Group role : rolesList){
			if("ROLE_ADMIN".equalsIgnoreCase(role.getGroupName())){
				return true;
			}
		}
		return false;
	}
	private boolean hasPrivilege(Project project, String boq){
		com.sanyo.quote.domain.User userSanyo = getLoggedInUser();
		List<Group> rolesList = userSanyo.getGrouplist();
		for(Group role : rolesList){
			if("ROLE_ADMIN".equalsIgnoreCase(role.getGroupName())){
				return true;
			}
		}
		List<Location> locations = locationService.findByProject(project);
		for(Location location : locations){
			List<Region> regions = regionService.findByLocation(location);
			for(Region region : regions){
				List<UserRegionRole> userRegionRoleList = userRegionRoleService.findByRegionAndUser(region, userSanyo);
				if(userRegionRoleList != null && !userRegionRoleList.isEmpty()){
					for(UserRegionRole userRegionRole : userRegionRoleList){
						String roleName = userRegionRole.getRoleName();
						if((roleName.equalsIgnoreCase("EDIT") || roleName.equalsIgnoreCase("VIEW"))
								&& region.getCategory().getParentCategory() != null
								&& region.getCategory().getParentCategory().getName().equalsIgnoreCase(boq)
								){
							return true;
						}else{
							return false;
						}
					}
				}else{
					return false;
				}
			}
		}
		return false;
	}
	private boolean isNeedUpdatePrice(Project project){
		List<Location> locations = locationService.findByProject(project);
		boolean result = false;
		for(Location location : locations){
			List<Region> regions = regionService.findByLocation(location);
			for(Region region : regions){
				List<Encounter> encounters = encounterService.findByRegion(region);
				for(Encounter encounter : encounters){
					if(encounter.isNeedUpdatePrice()){
						result = true;
						return result;
					}
				}
			}
		}
		return result;
	}
	@RequestMapping(value = "/getAssignedProductOfRegion", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getProductsJson(@RequestParam(value="regionId", required=true) String regionId,
				@RequestParam(value="locationIds", required=false) String locationIds
			, @RequestParam(value="projectId", required=false) String projectId
			, @RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		String[] regionIds = regionId.split(",");
		List<Encounter> finalEncounters = new ArrayList<Encounter>();
		boolean isAllLocation = false;
		boolean isAllRegion = false;
//		for(String id : regionIds){
//			if(id.equalsIgnoreCase("0")){
//				isAllRegion = true;
//				break;
//			}
//		}
		if(!isAllRegion){
			for(String id : regionIds){
				if(id.equalsIgnoreCase("0"))
					continue;
				Region region = regionService.findById(Integer.valueOf(id));
				List<Encounter> encounters = encounterService.findByRegion(region);
				finalEncounters.addAll(encounters);
			}
		}else{
			if(locationIds !=null){
//				for(String id : locationIds.split(",")){
//					if(id.equalsIgnoreCase("0")){
//						isAllLocation = true;
//						break;
//					}
//				}
				if(!isAllLocation){
					for(String id : locationIds.split(",")){
						if(id.equalsIgnoreCase("0"))
							continue;
						Location location = locationService.findById(Integer.valueOf(id));
						List<Region> regions = regionService.findByLocation(location);
						for(Region region : regions){
							List<Encounter> encounters = encounterService.findByRegion(region);
							finalEncounters.addAll(encounters);
						}
					}	
				}else{
					//find all locations of the project, then find all regions of each location.
					Project project = projectService.findById(Integer.valueOf(projectId));
					List<Location> locations = locationService.findByProject(project);
					for(Location location : locations){
						List<Region> regions = regionService.findByLocation(location);
						for(Region region : regions){
							List<Encounter> encounters = encounterService.findByRegion(region);
							finalEncounters.addAll(encounters);
						}
					}
				}
			}
		}
		Collections.sort(finalEncounters, new Comparator<Encounter>() {
			@Override
			public int compare(Encounter o1, Encounter o2) {
				//ascending order
				return o1.getOrder() - o2.getOrder();
			}
		});
		String result = Utilities.jSonSerialization(finalEncounters);
		return result;
	}
	//save encounter
	@RequestMapping(value = "/{id}/addquotation", params = "form", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String saveEncounters(@RequestBody final EncounterJson encounterJson, @PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		System.out.println("=================================== saving encounter");
		saveEncounter(encounterJson);
		return "success";
	}
	private void saveEncounter(EncounterJson encounterJson){
		Encounter encounter;
		ProductGroup productGroup;
		if(encounterJson.getEncounterID() != null && encounterJson.getEncounterID() >0){
			encounter = encounterService.findById(encounterJson.getEncounterID());
		}else{
			//Create new encounter
			encounter = new Encounter();
			Product product = productService.findById(Integer.valueOf(encounterJson.getProductId()));
			encounter.setProduct(product); //set product
			Region region = regionService.findById(Integer.valueOf(encounterJson.getRegionId()));
			encounter.setRegion(region);

			//checking productGroup here to create record in ProductGroupRate
			productGroup =  productGroupService.findById(product.getProductGroup().getGroupId());
			Project project = region.getLocation().getProject();
			List<ProductGroupRate> productGroupRates = productGroupRateService.findByProjectAndProductGroup(project, productGroup);
			if (productGroupRates.size()>0){
				//do nothing-don't need to add new record to ProductGroupRate
			}
			else{
				ProductGroupRate productGroupRate = new ProductGroupRate();
				TotalMaterialLabour totalMaterialLabour = new TotalMaterialLabour();
				//create a new record in totalmateriallabour to link into productgrouprate for later use
				//used to save totalMaterial and totalLabour of a revelant productgroup
				totalMaterialLabourService.save(totalMaterialLabour);

				productGroupRate.setTotalMaterialLabour(totalMaterialLabour);
				productGroupRate.setDiscount(project.getDiscountRate());
				productGroupRate.setAllowance(project.getAllowance());
				productGroupRate.setProductGroup(productGroup);
				productGroupRate.setProject(project);

				productGroupRateService.save(productGroupRate);
			}


		}
		if(Utilities.isValidInputNumber(encounterJson.getActualQuantity()))
			encounter.setActualQuantity(Float.valueOf(encounterJson.getActualQuantity()));
		if (Utilities.isValidInputNumber(encounterJson.getAllowance()))
			encounter.setAllowance(Float.valueOf(encounterJson.getAllowance()));
		if (Utilities.isValidInputNumber(encounterJson.getAmount()))
			encounter.setAmount(Float.valueOf(encounterJson.getAmount()));
		if(Utilities.isValidInputNumber(encounterJson.getCost_Labour_Amount_USD()))
			encounter.setCost_Labour_Amount_USD(Float.valueOf(encounterJson.getCost_Labour_Amount_USD()));
		if (Utilities.isValidInputNumber(encounterJson.getCost_Mat_Amount_USD()))
			encounter.setCost_Mat_Amount_USD(Float.valueOf(encounterJson.getCost_Mat_Amount_USD()));
		if (Utilities.isValidInputNumber(encounterJson.getDiscount_rate())) 
			encounter.setDiscount_rate(Float.valueOf(encounterJson.getDiscount_rate()));
		encounter.setEncounterTime(new Date());
		if(Utilities.isValidInputNumber(encounterJson.getImp_Tax()))
			encounter.setImp_Tax(Float.valueOf(encounterJson.getImp_Tax()));
		if (Utilities.isValidInputNumber(encounterJson.getLabour())) 
			encounter.setLabour(Float.valueOf(encounterJson.getLabour()));
		if(Utilities.isValidInputNumber(encounterJson.getMat_w_o_Tax_USD())) 
			encounter.setMat_w_o_Tax_USD(Float.valueOf(encounterJson.getMat_w_o_Tax_USD()));
		if (Utilities.isValidInputNumber(encounterJson.getMat_w_o_Tax_VND())) 
			encounter.setMat_w_o_Tax_VND(Float.valueOf(encounterJson.getMat_w_o_Tax_VND()));
		encounter.setOrder(Integer.valueOf(encounterJson.getOrder()));
		
		
		
		
		if(Utilities.isValidInputNumber(encounterJson.getQuantity()))
			encounter.setQuantity(Float.valueOf(encounterJson.getQuantity()));
		
		encounter.setRemark(encounterJson.getRemark());
		if(Utilities.isValidInputNumber(encounterJson.getSpecial_Con_Tax()))
			encounter.setSpecial_Con_Tax(Float.valueOf(encounterJson.getSpecial_Con_Tax()));
		encounter.setStatus(EncounterStatus.PROCESSING);
		if(Utilities.isValidInputNumber(encounterJson.getSubcon_Profit()))
			encounter.setSubcon_Profit(Float.valueOf(encounterJson.getSubcon_Profit()));
		if(Utilities.isValidInputNumber(encounterJson.getUnit_Price_After_Discount()))
			encounter.setUnit_Price_After_Discount(Float.valueOf(encounterJson.getUnit_Price_After_Discount()));
		if(Utilities.isValidInputNumber(encounterJson.getUnit_Price_W_Tax_Labour()))
			encounter.setUnit_Price_W_Tax_Labour(Float.valueOf(encounterJson.getUnit_Price_W_Tax_Labour()));
		if(Utilities.isValidInputNumber(encounterJson.getUnit_Price_W_Tax_Profit()))
			encounter.setUnit_Price_W_Tax_Profit(Float.valueOf(encounterJson.getUnit_Price_W_Tax_Profit()));
		if(Utilities.isValidInputNumber(encounterJson.getUnitRate()))
			encounter.setUnitRate(Float.valueOf(encounterJson.getUnitRate()));
		if(Utilities.isValidInputNumber(encounterJson.getVat()))
			encounter.setVAT(Float.valueOf(encounterJson.getVat()));
		if(Utilities.isValidInputNumber(encounterJson.getLabourAfterTax()))
			encounter.setLabourAfterTax(Float.valueOf(encounterJson.getLabourAfterTax()));
		if(Utilities.isValidInputNumber(encounterJson.getNonamePercent()))
			encounter.setNonamePercent(Float.valueOf(encounterJson.getNonamePercent()));
		if(encounterJson.getNonameRange() != null)
			encounter.setNonameRange(encounterJson.getNonameRange());
		encounterService.save(encounter);
		
	}
	
	//save encounter
	@RequestMapping(value = "/{id}/savequotation", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void saveEncounters2(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		System.out.println("=================================== saving encounter 2");
	}
	@RequestMapping(value = "/getAssignedLocationsJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getAssignedLocations(@RequestParam(value="projectId", required=true) String projectId
			, @RequestParam(value="regionType", required=false) String regionType
			, @RequestParam(value="filterscount", required=false) String filterscount
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
		
		List<Location> locations = locationService.findByProjectOrderByOrderNoAsc(project);
		List<Location> selectedLocations = new ArrayList<Location>();
		
		if(regionType != null && (regionType.equalsIgnoreCase(Constants.ELEC_TYPE)
									|| regionType.equalsIgnoreCase(Constants.MECH_TYPE))
				){
			for(Location location : locations){
				List<Region> regions = regionService.findByLocation(location);
				for(Region region : regions){
					Category category = region.getCategory().getParentCategory();
					if((category.getName().equalsIgnoreCase(Constants.MECH_BOQ)
							&& regionType.equalsIgnoreCase(Constants.MECH_TYPE))
							||(category.getName().equalsIgnoreCase(Constants.ELECT_BOQ)
							&& regionType.equalsIgnoreCase(Constants.ELEC_TYPE)
							)){
						selectedLocations.add(location);
						break;
					}
				}
			}
		}else{
			//find all
		}
		
		selectedLocations.add(0, locationAll);
		String result = Utilities.jSonSerialization(selectedLocations);
		return result;
	}
	//function to delete encounter.
	@ResponseBody
	@RequestMapping(value = "/{id}", params = "delete", method = RequestMethod.POST)
    public void deleteEncounter(@PathVariable("id") String id, Model uiModel, HttpServletRequest httpServletRequest) {
		encounterService.delete(Integer.valueOf(id));
       
	}
	private Float convertPrice(Float total, Project project){
		Currency currency = project.getCurrency();
		if(currency != null
				&& currency.getCurrencyCode() != null
				&& currency.getCurrencyCode().equalsIgnoreCase("VND")){
			// convert usd to vnd
			Float value = project.getUsdToVnd();
			if(value == null)
				value =0f;
			total = total * value;


		}else if(currency != null
				&& currency.getCurrencyCode() !=null
				&& currency.getCurrencyCode().equalsIgnoreCase("JPY")){
			// convert usd to jpy
			Float value = project.getUsdToJpy();
			if(value == null)
				value =0f;
			total = total * value;
		}
		return total;
	}
	/*@RequestMapping(value = "/{id}/getLocationSum", method = RequestMethod.POST)
	@ResponseBody
	public String getLocationSum(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		Float total = 0f;
		Location location = locationService.findByIdAndFetchRegionsEagerly(id);
		Project project = location.getProject();
		if(location != null){
			Set<Region> regions = location.getRegions();
			Iterator<Region> iterator = regions.iterator();
			while(iterator.hasNext()){
				Region region = iterator.next();
				Region region2 = regionService.findByIdAndFetchEncountersEagerly(region.getRegionId());
				if(region2 != null){
					Set<Encounter> encounters = region2.getEncounters();
					Iterator<Encounter> iter2 = encounters.iterator();
					
					while(iter2.hasNext()){
						Encounter encounter = iter2.next();
						total += encounter.getAmount();
					}
				}

			}
		}
	if(total >0)
		total = convertPrice(total, project);
	return total.toString();
	}*/
	//function to update price for encounters of specific project.
		@ResponseBody
		@RequestMapping(value = "/{id}", params = "updatePrice", method = RequestMethod.POST)
	    public void updatePrice(@PathVariable("id") String id, Model uiModel, HttpServletRequest httpServletRequest) {
			Project project = projectService.findByIdAndFetchLocationsEagerly(Integer.valueOf(id));
			Set<Location> locations = project.getLocations();
			Iterator<Location> iterLocation = locations.iterator();
			while(iterLocation.hasNext()){
				Location location = iterLocation.next();
				List<Region> regions = regionService.findByLocation(location);
				for(Region region : regions){
					List<Encounter> encounters = encounterService.findByRegion(region);
					for(Encounter encounter : encounters){
						if(encounter.isNeedUpdatePrice()){
							//update encounter
							Product product = encounter.getProduct();
							if(product.getMat_w_o_Tax_USD() != null)
								encounter.setMat_w_o_Tax_USD(product.getMat_w_o_Tax_USD());
							if(product.getMat_w_o_Tax_VND() != null)
								encounter.setMat_w_o_Tax_VND(product.getMat_w_o_Tax_VND());
							if(product.getLabour() != null)
								encounter.setLabour(product.getLabour());
							
							//update related fields
							updateEncounterFields(encounter);
						}
					}
				}
			}
			
	       
		}
		private void updateEncounterFields(Encounter encounter){
			Project project = encounter.getRegion().getLocation().getProject();
			updatePriceAfterDiscount(encounter, project);
			float allowance, specialCon, impTax, discountRate, vat;
			allowance = specialCon = impTax = discountRate = vat= 0f;
			List<ProductGroupRate> productGroupRates = productGroupRateService.findByProjectAndProductGroup(project, encounter.getProduct().getProductGroup());
			if(productGroupRates != null && productGroupRates.size() >0){
				//get 1 productGroupRate. Should not have multiple productGroupRate for each pair of project and productGroup
				ProductGroupRate productGroupRate = productGroupRates.get(0);
				allowance =productGroupRate.getAllowance();
				discountRate = productGroupRate.getDiscount();
			}else{
				allowance = project.getAllowance();
				discountRate = project.getDiscountRate();
			}
			impTax = project.getImpTax();
			specialCon = project.getSpecialTax();
			
			updateUnitRate(encounter, allowance);
			updateUnitPriceWTaxProfit(encounter, specialCon, impTax, discountRate, vat);
			updateUnitPriceWTaxLabour(encounter);
			updateCostMatAmountUsd(encounter);
			updateCostMatAmountVnd(encounter);
			updateLabour(encounter);
			updateAmount(encounter);
		}
		private void updatePriceAfterDiscount(Encounter encounter, Project project){
			if(project.getVndToUsd() != null && project.getVndToUsd() != 0){
				float result = encounter.getMat_w_o_Tax_USD() + encounter.getMat_w_o_Tax_VND()/project.getVndToUsd();
				encounter.setUnit_Price_After_Discount(result);
			}
		}
		
		private void updateUnitRate(Encounter encounter, float allowance){
			//var result = unit_Price_After_Discount * allowance/100;
			encounter.setUnitRate(encounter.getUnit_Price_After_Discount() * allowance / 100);
		}
		private void updateUnitPriceWTaxProfit(Encounter encounter, float specialCon, float impTax, float discountRate, float vat){
			//var result = unit_Price_After_Discount*(1+(1+specialCon*(1+impTax))*vat)*discountRate;
			float result = encounter.getUnit_Price_After_Discount()*(1+(1+specialCon*(1+impTax))*vat)*discountRate;
			encounter.setUnit_Price_W_Tax_Profit(result);
		}
		private void updateUnitPriceWTaxLabour(Encounter encounter){
			//var result = labour * subcon_Profit;
			float result = encounter.getLabour() * encounter.getSubcon_Profit()/100;
			encounter.setUnit_Price_W_Tax_Labour(result);
			
		}
		private void updateCostMatAmountUsd(Encounter encounter){
			//var result = unit_Price_After_Discount * qtyManual;
			float result = encounter.getUnit_Price_After_Discount()*encounter.getQuantity();
			encounter.setCost_Mat_Amount_USD(result);
		}
		private void updateCostMatAmountVnd(Encounter encounter){
			//var result = unit_Price_W_Tax_Labour * qtyManual;
			float result = encounter.getUnit_Price_W_Tax_Labour() * encounter.getQuantity();
			encounter.setCost_Labour_Amount_USD(result);
		}
		private void updateLabour(Encounter encounter){
			//var result = unit_Price_W_Tax_Labour * quantity;
			float result = encounter.getUnit_Price_W_Tax_Labour() * encounter.getQuantity();
			encounter.setLabour(result);
		}
		private void updateAmount(Encounter encounter){
			//var result = quantity * unitRate;
			float result = encounter.getActualQuantity() * encounter.getUnitRate();
		}

	private void updateMat_w_o_Tax_USD() {
			//update later for range and percent
		}

	//get quotation list and display in data table.
	@RequestMapping(value = "/getquotationlistdatatables", method = RequestMethod.GET)
	public String getquotationlistdatatables(@RequestParam(value = "projectId", required = false) String projectId,
											 @RequestParam(value = "locationIds", required = true) String locationIds,
											 @RequestParam(value = "regionIds", required = true) String regionIds,
											 Model uiModel, HttpServletRequest httpServletRequest) {
		if (projectId != null)
			uiModel.addAttribute("projectId", projectId);
		uiModel.addAttribute("locationIds", locationIds);
		uiModel.addAttribute("regionIds", regionIds);
		return "quotation/createDataTable";
	}

	@RequestMapping(value = "/getLocationSum", method = RequestMethod.GET)
	@ResponseBody
	public String getLocationSum(
			@RequestParam(value = "locationIds", required = true) String locationIds
			, @RequestParam(value = "projectId", required = true) String projectId
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		Float total = 0f;
		boolean isAllLocation = false;
		Project project = projectService.findById(Integer.valueOf(projectId));
//			for(String id : locationIds.split(",")){
//				if(id.equalsIgnoreCase("0")){
//					isAllLocation = true;
//					break;
//				}
//			}
		if (isAllLocation) {

			List<Location> locations = locationService.findByProject(project);
			for (Location location : locations) {
				total += getSummOfLocation(location);
			}
		} else {
			for (String id : locationIds.split(",")) {
				if (id.equalsIgnoreCase("0"))
					continue;
				Location location = locationService.findById(Integer.valueOf(id));
				total += getSummOfLocation(location);
			}
		}
		Currency currency = project.getCurrency();
		if (currency != null
				&& currency.getCurrencyCode().equalsIgnoreCase("VND")) {
			return String.valueOf((int)Math.ceil(total));

		}else{
			//round up 2 decimal number
			return roundOffTo2DecPlaces(total);

		}
		//return total.toString();
	}
	private String roundOffTo2DecPlaces(float val)
	{
		return String.format("%.2f", val);
	}

	private float getSummOfLocation(Location location) {
			Float total = 0f;
			if(location != null){
				List<Region> regions = regionService.findByLocation(location);
				for(Region region : regions){
					total += getSumOfRegion(region);
				}
			}
			return total;
		}

	@RequestMapping(value = "/getRegionSum", method = RequestMethod.GET)
		@ResponseBody
	public String getRegionSum(
			@RequestParam(value = "regionIds", required = true) String locationIds
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		Float total = 0f;
		Project project = null;
		for (String id : locationIds.split(",")) {
			if (id.equalsIgnoreCase("0"))
				continue;
			Region region = regionService.findById(Integer.valueOf(id));
			total += getSumOfRegion(region);
			if(project == null)
				project = region.getLocation().getProject();
		}
		if(project != null){
			Currency currency = project.getCurrency();
			if (currency != null
					&& currency.getCurrencyCode().equalsIgnoreCase("VND")) {
				return String.valueOf((int)Math.ceil(total));
			}
		}
		return roundOffTo2DecPlaces(total);
		//return total.toString();
	}

	private float getSumOfRegion(Region region) {
			Float total = 0f;
			List<Encounter> encounters = encounterService.findByRegion(region);
			Project project = null;
			for(Encounter encounter : encounters) {
				float amount = encounter.getAmount();
				if(project == null)
					project = encounter.getRegion().getLocation().getProject();
				Currency currency = project.getCurrency();
				if(currency != null
						&& currency.getCurrencyCode() != null
						&& currency.getCurrencyCode().equalsIgnoreCase("USD")){
					//do nothing
				}else
					amount = (float)Math.ceil(convertPrice(amount, project));
				total += amount;
			}
			return total;

		}

	@RequestMapping(value = "/getAssignedProductOfRegionForDatatables", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		@ResponseBody
		public String getProductsJsonForDataTables(@RequestParam(value="regionId", required=true) String regionId,
					@RequestParam(value="locationIds", required=false) String locationIds
				, @RequestParam(value="projectId", required=false) String projectId
				, @RequestParam(value="filterscount", required=false) String filterscount
				, @RequestParam(value="groupscount", required=false) String groupscount
				, @RequestParam(value="pagenum", required=false) Integer pagenum
				, @RequestParam(value="pagesize", required=false) Integer pagesize
				, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
				, @RequestParam(value="recordendindex", required=false) Integer recordendindex
				, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

			String[] regionIds = regionId.split(",");
			List<Encounter> finalEncounters = new ArrayList<Encounter>();
			boolean isAllLocation = false;
			boolean isAllRegion = false;

//			for(String id : regionIds){
//				if(id.equalsIgnoreCase("0")){
//					isAllRegion = true;
//					break;
//				}
//			}
			if(!isAllRegion){
				for(String id : regionIds){
					if(id.equalsIgnoreCase("0"))
						continue;
					Region region = regionService.findById(Integer.valueOf(id));
					List<Encounter> encounters = encounterService.findByRegion(region);
					finalEncounters.addAll(encounters);
					updatePriceBasedOnCurrency(finalEncounters, region.getLocation().getProject());
				}
			}else{
				if(locationIds !=null){
//					for(String id : locationIds.split(",")){
//						if(id.equalsIgnoreCase("0")){
//							isAllLocation = true;
//							break;
//						}
//					}
					if(!isAllLocation){
						Project project = null;
						for(String id : locationIds.split(",")){
							if(id.equalsIgnoreCase("0"))
								continue;
							Location location = locationService.findById(Integer.valueOf(id));
							if(project == null)
								project = location.getProject();
							List<Region> regions = regionService.findByLocation(location);
							for(Region region : regions){
								List<Encounter> encounters = encounterService.findByRegion(region);
								finalEncounters.addAll(encounters);
							}
						}
						if(project != null)
							updatePriceBasedOnCurrency(finalEncounters,project);
					}else{
						//find all locations of the project, then find all regions of each location.
						Project project = projectService.findById(Integer.valueOf(projectId));
						List<Location> locations = locationService.findByProject(project);
						for(Location location : locations){
							List<Region> regions = regionService.findByLocation(location);
							for(Region region : regions){
								List<Encounter> encounters = encounterService.findByRegion(region);
								finalEncounters.addAll(encounters);
							}
						}
						updatePriceBasedOnCurrency(finalEncounters,project);
					}
				}
			}

			DataTableObject<Encounter> dataTableObject = new DataTableObject<Encounter>();
			dataTableObject.setAaData(finalEncounters);
			dataTableObject.setiTotalRecords(finalEncounters.size());
			dataTableObject.setiTotalDisplayRecords(finalEncounters.size());

		String result = Utilities.jSonSerialization(dataTableObject);
			return result;
		}
		//get quotation list and display in data table.
		@RequestMapping(value = "/updateQuotaitonOrder", method = RequestMethod.GET)
		@ResponseBody
		public void updateQuotaitonOrder(@RequestParam(value="id", required=true) Integer encounterId,
				@RequestParam(value="fromPosition", required=true) Integer fromPosition,
				@RequestParam(value="toPosition", required=true) Integer toPosition,
				Model uiModel, HttpServletRequest httpServletRequest){
			Encounter encounter = encounterService.findById(encounterId);
			if(encounter.getOrder() == fromPosition.intValue()){
				encounter.setOrder(toPosition);
				encounter.setDataTableChange(true);

				encounterService.save(encounter);

				EncounterOrderHist encounterOrderHist = new EncounterOrderHist();
				encounterOrderHist.setEncounterId(encounterId);
				encounterOrderHist.setFromPos(fromPosition.intValue());
				encounterOrderHist.setToPos(toPosition.intValue());
				encounterOrderHistService.save(encounterOrderHist);
				
			}


		}

	//get mat_w_o_tax_usd based on percent and range
	@RequestMapping(value = "/getmat_w_o_tax_usd", method = RequestMethod.GET)
	@ResponseBody
	public String getmat_w_o_tax_usd(@RequestParam(value = "projectId", required = false) String projectId,
									 @RequestParam(value = "locationId", required = true) String locationId,
									 @RequestParam(value = "regionId", required = true) String regionId,
									 @RequestParam(value = "percent", required = true) String percent,
									 @RequestParam(value = "range", required = true) String range,
									 Model uiModel, HttpServletRequest httpServletRequest) {
		Region region = regionService.findById(Integer.valueOf(regionId));
		String[] arrRange = range.split("-");
		int start = Integer.valueOf(arrRange[0]);
		int end = Integer.valueOf(arrRange[1]);
		Float total = 0f;
		List<Encounter> encounters = encounterService.findByRegion(region);
		for (Encounter encounter : encounters) {
			if (encounter.getOrder() <= end
					&& encounter.getOrder() >= start) {
				total += encounter.getCost_Mat_Amount_USD();
			}
		}
		Float result = Float.valueOf(percent) * total / 100;
		logger.info("============ mat_w_o_tax_usd = " + result);
		return result.toString();
	}
	//default currency is USD.
	//if user use VND, we should convert price first, then display value in VND.
	private void updatePriceBasedOnCurrency(List<Encounter> encounters, Project project){
		Currency currency = project.getCurrency();
		float rate = 0f;
		if (currency != null
				&& currency.getCurrencyCode().equalsIgnoreCase("VND")) {
			rate = project.getUsdToVnd()!=null?project.getUsdToVnd():0;
		}else if (currency != null
				&& currency.getCurrencyCode().equalsIgnoreCase("VND")) {
			rate = project.getUsdToJpy()!=null?project.getUsdToJpy():0;
		}
		if(rate == 0f)
			return;

		for(Encounter encounter : encounters){
			float amount = encounter.getAmount();
			float cost_mat_amount_usd = encounter.getCost_Mat_Amount_USD();
			encounter.setAmount((int) Math.ceil(amount * rate));
			//encounter.setCost_Mat_Amount_USD((int)Math.ceil(cost_mat_amount_usd * rate));

		}
	}
	/*
	 * to generate json with Location and Regions in detail. Then GUI will reuse it without connecting server every
	 * user selecting different location.
	 */
	@RequestMapping(value = "/getAllAssignedRegionsJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getAllAssignedRegionsJson(@RequestParam(value="projectId", required=true) String projectId
			, @RequestParam(value="regionType", required=false) String regionType
			, @RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		Project project = projectService.findById(Integer.valueOf(projectId));
		Region  all = new Region();
		all.setRegionName("All");
		all.setRegionId(0);
		
		List<Location> locations = locationService.findByProjectOrderByOrderNoAsc(project);
		List<Region> selectedRegions = new ArrayList<Region>();
		if(regionType != null && (regionType.equalsIgnoreCase(Constants.ELEC_TYPE)
									|| regionType.equalsIgnoreCase(Constants.MECH_TYPE))
				){
			for(Location location : locations){
				List<Region> regions = regionService.findByLocation(location);
				for(Region region : regions){
					Category category = region.getCategory().getParentCategory();
					if((category.getName().equalsIgnoreCase(Constants.MECH_BOQ)
							&& regionType.equalsIgnoreCase(Constants.MECH_TYPE))
							||(category.getName().equalsIgnoreCase(Constants.ELECT_BOQ)
							&& regionType.equalsIgnoreCase(Constants.ELEC_TYPE)
							)){
						selectedRegions.add(region);
						//break;
					}
				}
			}
		}else{
			//find all
		}
		
		
		Collections.sort(selectedRegions, new Comparator<Region>() {
			@Override
			public int compare(Region o1, Region o2) {
				return o1.getRegionName().compareToIgnoreCase(o2.getRegionName());
			}
		});
		selectedRegions.add(0, all);
		String result = Utilities.jSonSerialization(selectedRegions);
		return result;
	}

	/*
	new method to update new order of an encounter. Assume that User re-order the list, we need to have a loop to update every item that has been re-ordered.
	 */
	@RequestMapping(value = "/updateQuotaitonOrderAngularJS", method = RequestMethod.GET)
	@ResponseBody
	public String updateQuotaitonOrderAngularJS(@RequestParam(value="id", required=true) Integer encounterId,
									 @RequestParam(value="newPosition", required=true) Integer newPosition,
									 Model uiModel, HttpServletRequest httpServletRequest){
		Encounter encounter = encounterService.findById(encounterId);
		if(encounter.getOrder() != newPosition.intValue()){
			encounter.setOrder(newPosition);
			encounterService.save(encounter);

			List<Encounter> encounters = encounterService.findAll();
			String results = Utilities.jSonSerialization(encounters);
			return results;
		}
		return  "[]";
	}
	/*
new method to update new order of an encounter. Assume that User re-order the list, a list of encounter will be passed to this method.
 */
	@RequestMapping(value = "/updateQuotationListOrderByAngularJS", method = RequestMethod.GET)
	@ResponseBody
	public String updateQuotationListOrderByAngularJS(@RequestParam(value="params", required=true) String params,
												Model uiModel, HttpServletRequest httpServletRequest){
		// sample params is: encounterId_1|newOrder_2,encounter_2|newOrder_1

		StringTokenizer stringTokenizer = new StringTokenizer(params, ",");
		while (stringTokenizer.hasMoreElements()){
			String elem = (String) stringTokenizer.nextElement();
			System.out.println(elem);
			StringTokenizer stringTokenizer2 = new StringTokenizer(elem, "|");
			while(stringTokenizer2.hasMoreElements()){

				String elemtEncounter = (String )stringTokenizer2.nextElement();
				String elemtOrder = (String) stringTokenizer2.nextElement();

				StringTokenizer stringTokenizer3 = new StringTokenizer(elemtEncounter, "_");
				StringTokenizer stringTokenizer4 = new StringTokenizer(elemtOrder, "_");
				String encounterId = null, newPosition = null;
				while(stringTokenizer3.hasMoreElements()){
					stringTokenizer3.nextElement();
					encounterId = (String)stringTokenizer3.nextElement();
				}
				while(stringTokenizer4.hasMoreElements()){
					stringTokenizer4.nextElement();
					newPosition = (String) stringTokenizer4.nextElement();
				}
				// update every encounter
				if(encounterId != null && newPosition != null) {
					Encounter encounter = encounterService.findById(Integer.valueOf(encounterId));
					if (encounter.getOrder() != Integer.valueOf(newPosition)) {
						encounter.setOrder(Integer.valueOf(newPosition));
						encounterService.save(encounter);
					}
				}
			}
		}
		return  "[]";
	}
}


