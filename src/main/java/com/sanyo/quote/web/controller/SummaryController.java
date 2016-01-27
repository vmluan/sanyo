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

import com.sanyo.quote.service.ProjectService;
import com.sanyo.quote.service.RegionService;
import com.sanyo.quote.service.EncounterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.domain.Location;
import com.sanyo.quote.domain.Encounter;

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
    EncounterService encounterService;
    @Autowired
    ProductGroupRateService productGroupRateService;    
    @Autowired
    LocationService location;
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getSummaryPage(@PathVariable("id") Integer projectId,Model uiModel) {
    	Project project = projectService.findById(projectId);
    	List<Location> getlocation = location.findByProjectOrderByOrderNoDesc(project);
    	
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
    	System.out.println(metricalRigon.size());
    	System.out.println(electricalRigon.size());
    	
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
