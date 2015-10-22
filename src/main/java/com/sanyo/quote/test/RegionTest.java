/**
 * Copyright (c) 2015  osgicse group.
 * Filename   : ContactServiceTest.java
 * Description: 
 * @author    : Luan Vo
 * Created    : Sep 3, 2015
 */
package com.sanyo.quote.test;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.domain.User;
import com.sanyo.quote.domain.UserRegionRole;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.CategoryService;
import com.sanyo.quote.service.GroupService;
import com.sanyo.quote.service.ProductService;
import com.sanyo.quote.service.ProjectService;
import com.sanyo.quote.service.RegionService;
import com.sanyo.quote.service.UserService;


public class RegionTest {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	
	public static void main(String[] args) throws ParseException {

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
//
		
		ContactServiceTest test = new ContactServiceTest();
//		test.addProject(projectService);
//		test.addCategory(categoryService);
//		Region region = regionService.findById(2);
//		Region region = regionService.findByIdAndFetchUsersEagerly(2);
//		Set<User> users= region.getUsers();
//		Iterator<User> iterator = users.iterator();
//		while(iterator.hasNext()){
//			System.out.println("======== next");
//			User user = iterator.next();
//			System.out.println("user name = " + user.getUsername());
//		}
		
		
		Project project = projectService.findById(1);
		
		//Set<Region> regions  = project.getRegions();
		//Iterator<Region> iterator = regions.iterator();
		Set<Region> assginedRegions = new HashSet<Region>();
//		
//		while(iterator.hasNext()){
//			Region region = iterator.next();
//			System.out.println(region.getCategory().getName());
//			Set<UserRegionRole> userRegionRoles = region.getUserRegionRoles();
//			Iterator<UserRegionRole> iterator2 = userRegionRoles.iterator();
//			while(iterator2.hasNext()){
//				UserRegionRole userRegionRole = iterator2.next();
//				System.out.println(userRegionRole.getRoleName());
//			}
//		}
		Region region = regionService.findByIdAndFetchUserRegionRolesEagerly(5);
		Set<UserRegionRole> userRegionRoles = region.getUserRegionRoles();
		Iterator<UserRegionRole> iterator2 = userRegionRoles.iterator();
		while(iterator2.hasNext()){
			UserRegionRole userRegionRole = iterator2.next();
			System.out.println(userRegionRole.getUserName());
		}
		
		//we got final set of regions here. Next is to get set of userRegionRole.
		
		
		
		
	}

}
