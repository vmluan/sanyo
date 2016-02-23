package com.sanyo.quote.web.controller;

import com.sanyo.quote.domain.EncounterJson;
import com.sanyo.quote.domain.Maker;
import com.sanyo.quote.domain.ProductGroupRate;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.LocationService;
import com.sanyo.quote.service.ProductGroupRateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sanyo.quote.service.ExpensesService;
import com.sanyo.quote.service.ProjectService;
import com.sanyo.quote.service.RegionService;
import com.sanyo.quote.service.EncounterService;
import com.sanyo.quote.service.SummaryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanyo.quote.domain.ExpenseElements;
import com.sanyo.quote.domain.Expenses;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.domain.Location;
import com.sanyo.quote.domain.Encounter;
import com.sanyo.quote.domain.Summary;
import com.sanyo.quote.domain.SummaryJson;
import com.sanyo.quote.service.ExpenseElementsService;

import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by Chuong Thai on 10/8/2015.
 */

@Controller
@RequestMapping(value = "/summary")
public class SummaryController {
    final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
	private ProjectService projectService;
    @Autowired
    RegionService rigionService;
    @Autowired
    ExpenseElementsService ExpenseElementsService;
    @Autowired
    EncounterService encounterService;
    @Autowired
    ExpensesService expensesService;
    @Autowired
    ProductGroupRateService productGroupRateService;    
    @Autowired
    LocationService location;
    @Autowired
    SummaryService summary;
    
    
    
    @RequestMapping(value = "/{id}/savesumary2", params = "form",method = RequestMethod.POST)
    public String postSummaryPage(@RequestBody final SummaryJson summaryjson,@PathVariable("id") Integer projectId,Model uiModel,HttpServletRequest httpServletRequest) {
    	Project project = projectService.findById(projectId);
    	Summary summ = summary.findByProject(project);
    	if(summ==null)
    		summ = new Summary();
    	summ.setEngineer(summaryjson.getEngineer());
    	summ.setJapanese(summaryjson.getJapanese());
    	summ.setSiteexpenses(summaryjson.getSiteexpenses());
    	summ.setProfit(summaryjson.getProfit());
    	summ.setProject(project);
    	summary.save(summ);
    	return "quotation/summary";
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getSummaryPage(@PathVariable("id") Integer projectId,Model uiModel) {
    	Project project = projectService.findById(projectId);
    	List<Location> getlocation = location.findByProjectOrderByOrderNoDesc(project);
    	Summary summar = summary.findByProject(project);
    	List<Region> regionlist = new ArrayList<Region>();
    	for(Location item:getlocation){
    		for(Region values:getRigion(item))
    		{
    			regionlist.add(values);
    		}		
    	 }
    	// tách ra danh sách region thuộc electrical hay metrical
    	List<Region> metricalRigon = new ArrayList<Region>();
    	List<Region> electricalRigon = new ArrayList<Region>();
    	for(Region item:regionlist)
    	{
    		//region thuộc metrical
    		if(item.getCategory().getParentCategory().getCategoryId()!=1)
    		{
    			metricalRigon.add(item);
    			
    		}
    		else //region thuộc electrical
    		{
    			electricalRigon.add(item);
    		}
    	}
    	// tách ra danh sách location thuộc electrical hay metrical
    	List<Location> metricalLocation = new ArrayList<Location>();
    	List<Location> electricalLocation = new ArrayList<Location>();
    	for(Location item:getlocation)
    	{
    		//lấy danh sách location thuộc electrical;
    		for(Region itemRegionElectrical:electricalRigon)
    		{
    			if(itemRegionElectrical.getLocation().getLocationId()==item.getLocationId())
    			{
    				 Boolean flag =true;
    				for(Location values:electricalLocation)
    				{
    					//nếu tồn tại location trong list rồi thì không cần thêm vào nữa
    					if(itemRegionElectrical.getLocation().getLocationId()==values.getLocationId())
    					{
    						flag=false;
    					}
    				}
    				if(flag)
    				{
    					electricalLocation.add(item);
    					flag=false;
    				}
    			}
    		}
    		//lấy danh sách location thuộc metrical
    		for(Region itemRegionMetrical:metricalRigon)
    		{
    			if(itemRegionMetrical.getLocation().getLocationId()==item.getLocationId())
    			{
    				Boolean flag =true;
    				for(Location values:metricalLocation)
    				{
    					if(itemRegionMetrical.getLocation().getLocationId()==values.getLocationId())
    					{
    						flag=false;
    					}
    				}
    				if(flag)
    				{
    					metricalLocation.add(item);
    					flag=false;
    				}
    				
    			}
    		}
    	}
    	
    	List<Encounter> EncounterMetrical = new ArrayList<Encounter>();
    	List<Encounter> EncounterElectrical = new ArrayList<Encounter>();
    	DecimalFormat myFormatter = new DecimalFormat("###,#####.#####");
    	//lấy ra danh sách encounter thuộc metrical 
    	for(Region values:metricalRigon)
		{
    		if(getListEncounter(values).size()>0)
    		{
	    		Encounter encounter =new Encounter();
	    		encounter.setRegion(RegionEncounter(getListEncounter(values)));
	    		encounter.setLabour(LabourEncounter(getListEncounter(values)));
	    		encounter.setRemark(RemarkEncounter(getListEncounter(values)));
	    		encounter.setUnitRate(UnitRateEncounter(getListEncounter(values)));
	    		encounter.setQuantity(QuantityEncounter(getListEncounter(values)));
	    		encounter.setCost_Mat_Amount_USD(ForeignPortionEncounter(getListEncounter(values)));
	    		encounter.setAmount(AmountEncounter(getListEncounter(values)));    		
	    		EncounterMetrical.add(encounter);
    		}
		}
    	//lấy ra danh sách encounter thuộc electrical
    	for(Region values:electricalRigon)
		{
    		if(getListEncounter(values).size()>0)
    		{
	    		Encounter encounter =new Encounter();
	    		encounter.setRegion(RegionEncounter(getListEncounter(values)));
	    		encounter.setLabour(LabourEncounter(getListEncounter(values)));
	    		encounter.setRemark(RemarkEncounter(getListEncounter(values)));
	    		encounter.setUnitRate(UnitRateEncounter(getListEncounter(values)));
	    		encounter.setQuantity(QuantityEncounter(getListEncounter(values)));
	    		encounter.setCost_Mat_Amount_USD(ForeignPortionEncounter(getListEncounter(values)));
	    		encounter.setAmount(AmountEncounter(getListEncounter(values)));    		
	    		EncounterElectrical.add(encounter);
    		}
		}
    	//Site Expenses = 'M&E Expenses'!H23 - 'M&E Expenses'!G44 - 'M&E Expenses'!G57
    	//'M&E Expenses'!H23 = SELECT SUM(SUM) FROM expenses WHERE PROJECT_ID = @PROJECT_ID AND (EXPENSEELEMENT_ID BETWEEN 13 AND 29)
    	//'M&E Expenses'!G44 = SELECT SUM FROM expenses WHERE PROJECT_ID = @PROJECT_ID AND EXPENSEELEMENT_ID = 30
    	//'M&E Expenses'!G57 = SELECT SUM FROM expenses WHERE PROJECT_ID = @PROJECT_ID AND EXPENSEELEMENT_ID = 38
    			    	
    	List<Expenses> H23 = getSumOfSumExpenses(project,ExpenseElementsService.findById(13),ExpenseElementsService.findById(29));
    	List<Expenses> G44 = getSumExpenses(project,ExpenseElementsService.findById(30));
    	List<Expenses> G57 = getSumExpenses(project,ExpenseElementsService.findById(38));
    	float _H23 = 0;
    	float _G44 = 0;
    	float _G57 = 0;
    	for(Expenses values:H23)
    	{
    		_H23+=values.getSum();
    	}
    	Float count = (float) 0;
    	for(Expenses values:G44)
    	{
    		_G44+=values.getSum();
    		
    	}
    	for(Expenses values:G57)
    	{
    		_G57+=values.getSum();
    		
    	}
    	float SiteExpenses = _H23-_G44-_G57;
    	//Japanese = 'M&E Expenses'!H6 + 'M&E Expenses'!G13 + 'M&E Expenses'!G44 + 'M&E Expenses'!G57
    	//'M&E Expenses'!H6 = SELECT SUM(SUM) FROM expenses WHERE PROJECT_ID = @PROJECT_ID AND (EXPENSEELEMENT_ID BETWEEN 1 AND 3)
    	//'M&E Expenses'!G13 = SELECT SUM FROM expenses WHERE PROJECT_ID = @PROJECT_ID AND EXPENSEELEMENT_ID = 4
    	List<Expenses> H6 = getSumOfSumExpenses(project,ExpenseElementsService.findById(1),ExpenseElementsService.findById(3));
    	List<Expenses> G13 = getSumExpenses(project,ExpenseElementsService.findById(4));
    	float _H6 = 0;
    	float _G13 = 0;
    	for(Expenses values:H6)
    	{
    		_H6+=values.getSum();
    		
    	}
    	for(Expenses values:G13)
    	{
    		_G13+=values.getSum();
    		
    	}
    	float Japanese = _H6+_G13+_G44+_G57;
    	//Engineer = 'M&E Expenses'!H11 - 'M&E Expenses'!G13
    	//'M&E Expenses'!H11 = SELECT SUM(SUM) FROM expenses WHERE PROJECT_ID = @PROJECT_ID AND (EXPENSEELEMENT_IDBETWEEN 4 AND 12)?
    	List<Expenses> H11 = getSumOfSumExpenses(project,ExpenseElementsService.findById(4),ExpenseElementsService.findById(12));
    	float _H11 = 0;
    	for(Expenses values:H11)
    	{
    		_H11+=values.getSum();
    		
    	}
    	float Engineer = _H11-_G13;
    	//System.out.println(count);
    	//Set summary
    	SummaryJson summJ = new SummaryJson();
    	if(summar==null)
    	{
    		summJ.setEngineer(0);
        	summJ.setJapanese(0);
        	summJ.setProfit(0);
        	summJ.setSiteexpenses(0);        	
    	}
    	else
    	{
    		summJ.setEngineer(summar.getEngineer());
	    	summJ.setJapanese(summar.getJapanese());
	    	summJ.setProfit(summar.getProfit());
	    	summJ.setSiteexpenses(summar.getSiteexpenses());	    	
    	}
    	uiModel.addAttribute("SiteExpenses",SiteExpenses);
    	uiModel.addAttribute("Summary",summJ);
    	uiModel.addAttribute("Japanese",Japanese);
    	uiModel.addAttribute("Engineer",Engineer);
    	uiModel.addAttribute("myFormatter",myFormatter);
    	uiModel.addAttribute("EncounterMetrical",EncounterMetrical);
    	uiModel.addAttribute("EncounterElectrical",EncounterElectrical);
    	uiModel.addAttribute("metricalRigon",metricalRigon);
    	uiModel.addAttribute("electricalRigon",electricalRigon);
    	uiModel.addAttribute("metricalLocation",metricalLocation);
    	uiModel.addAttribute("electricalLocation",electricalLocation);
    	uiModel.addAttribute("projectId", projectId);
        return "quotation/summary";
    }
    private List<Expenses> getSumExpenses(Project project,ExpenseElements expenelements)
    {
    	return expensesService.getSumExpensesByProjectID(project,expenelements);
    }
    private List<Expenses> getSumOfSumExpenses(Project project,ExpenseElements expenelements1,ExpenseElements expenelements2)
    {
    	return expensesService.getSumOfSumExpensesByProjectID(project,expenelements1,expenelements2);
    }
    private float AmountEncounter(List<Encounter> listencounter)
    {
    	float amount=0;
    	for(Encounter items:listencounter)
    	{
    		amount+=items.getAmount();
    	}
    	return amount;
    }
    private Region RegionEncounter(List<Encounter> listencounter)
    {
    	Region region=new Region();
    	for(Encounter items:listencounter)
    	{
    		region=items.getRegion();
    	}
    	return region;
    }
    private int QuantityEncounter(List<Encounter> listencounter)
    {
    	int quantity=0;
    	for(Encounter items:listencounter)
    	{
    		quantity+=items.getQuantity();
    	}
    	return quantity;
    }
    private float ForeignPortionEncounter(List<Encounter> listencounter)
    {
    	float ForeignPortion=0;
    	for(Encounter items:listencounter)
    	{
    		ForeignPortion+=items.getCost_Mat_Amount_USD();
    	}
    	return ForeignPortion;
    }
    private float UnitRateEncounter(List<Encounter> listencounter)
    {
    	float unitRate=0;
    	for(Encounter items:listencounter)
    	{
    		unitRate+=items.getUnitRate();
    	}
    	return unitRate;
    }
    private String RemarkEncounter(List<Encounter> listencounter)
    {
    	String Remark="";
    	for(Encounter items:listencounter)
    	{
    		Remark=items.getRemark();
    	}
    	return Remark;
    }
    private float LabourEncounter(List<Encounter> listencounter)
    {
    	float Labour=0;
    	for(Encounter items:listencounter)
    	{
    		Labour+=items.getLabour();
    	}
    	return Labour;
    }
    private List<Encounter> getListEncounter(Region region)
    {
    	List<Encounter> getencounter = encounterService.findByRegion(region);
    	return getencounter;    	
    }
    private List<Region> getRigion(Location location)
    {
    	List<Region> getrigion = rigionService.findByLocation(location);
    	return getrigion;    	
    }
    @RequestMapping(value = "/getProductGroupRateJson/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getProductGroupRateJson(@RequestParam(value="filterscount", required=false) String filterscount,
                                          @RequestParam(value="productGroudCode", required=false) String productGroupCode,
            @RequestParam(value="groupscount", required=false) String groupscount,
            @RequestParam(value="pagenum", required=false) Integer pagenum,
            @RequestParam(value="pagesize", required=false) Integer pagesize,
            @RequestParam(value="recordstartindex", required=false) Integer recordstartindex,
            @RequestParam(value="recordendindex", required=false) Integer recordendindex,
            @PathVariable("id") Integer projectId,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Model uiModel) {
        if (projectId>0) {
            List<ProductGroupRate> productGroupRates = productGroupRateService.findByProjectId(projectId);
            if (productGroupRates != null) {
                return Utilities.jSonSerialization(productGroupRates);
            }
        }
        List<ProductGroupRate> productGroupRates = productGroupRateService.findAll();
        String result = Utilities.jSonSerialization(productGroupRates);
        return result;
    }

    @RequestMapping(value = "/{id}/updateRate", params = "form", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void saveEncounters(@RequestBody final ProductGroupRate productGroupRate, @PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
        System.out.println("=================================== updaing productGroupRate");
        float discount = productGroupRate.getDiscount();
        float allowance = productGroupRate.getAllowance();

        ProductGroupRate productGroupRate1 = productGroupRateService.findById(id); //Temp object to update value
        productGroupRate1.setDiscount(discount);
        productGroupRate1.setAllowance(allowance);
        productGroupRateService.save(productGroupRate1);
    }
}
