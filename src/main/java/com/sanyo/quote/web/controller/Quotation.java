package com.sanyo.quote.web.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanyo.quote.domain.*;
import com.sanyo.quote.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sanyo.quote.helper.Constants;
import com.sanyo.quote.helper.Utilities;

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
	
	@RequestMapping(method = RequestMethod.GET)
	public String getQuotationPage(@RequestParam(value="projectId", required=true) String projectId,
			Model uiModel,HttpServletRequest httpServletRequest) {
		Project project = projectService.findById(Integer.valueOf(projectId));
		uiModel.addAttribute("projectId", projectId);
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
		Maker maker = makerService.findById(Integer.valueOf(makerJson.getMakerId()));
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
	
	//get all assigned products of a specific project.
	@RequestMapping(value = "/{id}/addquotation", params = "form", method = RequestMethod.GET)
	public String showRegions(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
//		Region region = regionService.findById(id);
//		Encounter encounter = new Encounter();
//		if(region != null){
//			encounter.setRegion(region);
//		}
		uiModel.addAttribute("regionType", httpServletRequest.getParameter("type"));
		uiModel.addAttribute("project", projectService.findById(id));
		setUser(uiModel);
		return "quotation/create";
	}
	@RequestMapping(value = "/getAssignedProductOfRegion", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getProductsJson(@RequestParam(value="regionId", required=true) String regionId
			, @RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
//		Region region = regionService.findByIdAndFetchEncountersEagerly(Integer.valueOf(regionId));
		Region region = regionService.findById(Integer.valueOf(regionId));
		List<Encounter> encounters = encounterService.findByRegion(region);
		String result = Utilities.jSonSerialization(encounters);
		return result;
	}
	//save encounter
	@RequestMapping(value = "/{id}/addquotation", params = "form", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void saveEncounters(@RequestBody final EncounterJson encounterJson, @PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		System.out.println("=================================== saving encounter");
		saveEncounter(encounterJson);
	}
	private void saveEncounter(EncounterJson encounterJson){
		Encounter encounter;
		if(encounterJson.getEncounterID() != null && encounterJson.getEncounterID() >0){
			encounter = encounterService.findById(encounterJson.getEncounterID());
		}else{
			encounter = new Encounter();
			Product product = productService.findById(Integer.valueOf(encounterJson.getProductId()));
			encounter.setProduct(product); //set product
			ProductGroup productGroup =  productGroupService.findById(product.getProductGroup().getGroupId());
			//checking productGroup here to create record in ProductGroupRate
			Region region = regionService.findById(Integer.valueOf(encounterJson.getRegionId()));
			encounter.setRegion(region);
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
			, @RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		Project project = projectService.findByIdAndFetchLocationsEagerly(Integer.valueOf(projectId));
		Set<Location> locatoins = project.getLocations();
		String result = Utilities.jSonSerialization(locatoins);
		return result;
	}
	
	//function to delete encounter.
	@ResponseBody
	@RequestMapping(value = "/{id}", params = "delete", method = RequestMethod.POST)
    public void deleteEncounter(@PathVariable("id") String id, Model uiModel, HttpServletRequest httpServletRequest) {
		encounterService.delete(Integer.valueOf(id));
       
	}
	@RequestMapping(value = "/{id}/getLocationSum", method = RequestMethod.POST)
	@ResponseBody
	public String getLocationSum(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		Float total = 0f;
		Location location = locationService.findByIdAndFetchRegionsEagerly(id);
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
	return total.toString();
	}
}
