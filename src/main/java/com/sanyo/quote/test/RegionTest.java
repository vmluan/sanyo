/**
 * Copyright (c) 2015  osgicse group.
 * Filename   : ContactServiceTest.java
 * Description: 
 * @author    : Luan Vo
 * Created    : Sep 3, 2015
 */
package com.sanyo.quote.test;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.sanyo.quote.domain.Currency;
import com.sanyo.quote.domain.CurrencyExchRate;
import com.sanyo.quote.domain.Encounter;
import com.sanyo.quote.domain.Location;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.ProjectRevision;
import com.sanyo.quote.domain.ProjectStatus;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.CategoryService;
import com.sanyo.quote.service.CurrencyExchRateService;
import com.sanyo.quote.service.CurrencyService;
import com.sanyo.quote.service.EncounterService;
import com.sanyo.quote.service.GroupService;
import com.sanyo.quote.service.LocationService;
import com.sanyo.quote.service.ProductService;
import com.sanyo.quote.service.ProjectRevisionService;
import com.sanyo.quote.service.ProjectService;
import com.sanyo.quote.service.RegionService;
import com.sanyo.quote.service.UserService;


public class RegionTest {

	/**
	 * @param args
	 * @throws ParseException 
	 * @throws CloneNotSupportedException 
	 */
	
	public void cloneRegion(Integer locationId, Location clonedLocation, RegionService regionService, LocationService locationService) throws CloneNotSupportedException{
		List<Region> regions = locationService.findRegions(locationId);
		Set<Region> clonedRegions = Utilities.cloneRegions(regions);
		for(Region region : clonedRegions){
			Integer regionId = region.getRegionId();
			
			region.setRegionId(null);
			region.setLocation(clonedLocation);
			region = regionService.save(region);
		}
		
	}
	public void cloneEncounters(Integer regionId, Region clonedRegion, RegionService regionService, EncounterService encounterService) throws CloneNotSupportedException{
		List<Encounter> encounters = regionService.getEncounters(regionId);
		Set<Encounter> clonedEncounters = Utilities.cloneEncounters(encounters);
		for(Encounter encounter : clonedEncounters){
			encounter.setEncounterID(null);
			encounterService.save(encounter);
		}
	}
	public static void main(String[] args) throws ParseException, CloneNotSupportedException {

		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:jpa-app-context.xml");
		ctx.refresh();
		
		System.out.println("App context initialized successfully");
		ProductService productService = ctx.getBean("productService", ProductService.class);
		ProjectService projectService = ctx.getBean("projectService", ProjectService.class);
		CategoryService categoryService = ctx.getBean("categoryService", CategoryService.class);
		GroupService groupService = ctx.getBean("groupService", GroupService.class);
		UserService userService = ctx.getBean("userService", UserService.class);
		RegionService regionService = ctx.getBean("regionService", RegionService.class);
		EncounterService encounterService = ctx.getBean("encounterService", EncounterService.class);
		LocationService locationService = ctx.getBean("locationService", LocationService.class);
		CurrencyService currencyService = ctx.getBean("currencyService", CurrencyService.class);
		CurrencyExchRateService currencyExchRateService = ctx.getBean("currencyExchRateService", CurrencyExchRateService.class);
		ProjectRevisionService projectRevisionService = ctx.getBean("projectRevisionService", ProjectRevisionService.class);
//
		
//		RegionTest test = new RegionTest();
//		
//		Project project = projectService.findById(1);
//		
//		Project clonedProject = project.clone();
//		clonedProject.setProjectId(null);
//		clonedProject = projectService.save(clonedProject);
//		
//		List<Location> locations = projectService.findLocations(project.getProjectId());
//		Set<Location> clonedLocations = Utilities.cloneLocations(locations);
//		for(Location location : clonedLocations){
//			location.setProject(clonedProject);
//			Integer locationId = location.getLocationId();
//			location.setLocationId(null);
//			location = locationService.save(location);
//			test.cloneRegion(locationId, location, regionService, locationService);
//		}
//		System.out.println(projects.size());
//		String[] currencyList = {"USD", "VND", "JPY"};
//		for(String a : currencyList){
//			Currency currency = new Currency();
//			currency.setCurrencyCode(a);
//			currency.setCurrencyName(a);
//			currencyService.save(currency);
//		}
		Currency source = currencyService.findByCurrencyCode("VND");
		System.out.println(source.getCurrencyName());
		Project project = projectService.findByIdAndFetchLocationsEagerly(Integer.valueOf(1));
		Set<Location> locations = project.getLocations();
		Set<Region> totalRegions = new HashSet<Region>();
		for(Location location : locations){
			Set<Region> regions  = location.getRegions();
			totalRegions.addAll(regions);
		}
		String result = Utilities.createJsonTreeGridForRegions(totalRegions, locations);
		System.out.println(result);
	}

}
