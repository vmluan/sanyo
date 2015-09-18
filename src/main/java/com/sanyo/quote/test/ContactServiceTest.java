/**
 * Copyright (c) 2015  osgicse group.
 * Filename   : ContactServiceTest.java
 * Description: 
 * @author    : Luan Vo
 * Created    : Sep 3, 2015
 */
package com.sanyo.quote.test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.sanyo.quote.domain.Group;
import com.sanyo.quote.domain.Product;
import com.sanyo.quote.domain.Category;
import com.sanyo.quote.domain.User;
import com.sanyo.quote.service.CategoryService;
import com.sanyo.quote.service.GroupService;
import com.sanyo.quote.service.ProductService;
import com.sanyo.quote.service.ProjectService;
import com.sanyo.quote.service.UserService;


public class ContactServiceTest {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	private void testCategory(CategoryService categoryService, String categoryName){
		Category category = new Category();
//		category.setCategoryName(categoryName);
		categoryService.save(category);
		
	}
	private void testProductCategory(ProductService productService, CategoryService categoryService){
		Product product = productService.findById(1);
		Category category = categoryService.findById(1);
		ArrayList<Category> categories = new ArrayList<Category>();
		categories.add(category);
		product.setCategories(categories);
		
		productService.save(product);
	}
	private Group testGroup(GroupService groupService){
		Group group = new Group();
		group.setGroupName("ROLE_USER");
		return groupService.save(group);
		
		
	}
	private User addUser(UserService userService){
		User user = new User();
		user.setUsername("admin");
		user.setUsercode("01234");
		user.setPassword("ee10c315eba2c75b403ea99136f5b48d"); // admin in reserve,
		user.setActive(true);
		return userService.save(user);
	}
	
	public static void main(String[] args) throws ParseException {

		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:jpa-app-context.xml");
		ctx.refresh();
		
		System.out.println("App context initialized successfully");
		ProductService productService = ctx.getBean("productService", ProductService.class);
		ProjectService projectService = ctx.getBean("tableService", ProjectService.class);
		CategoryService categoryService = ctx.getBean("categoryService", CategoryService.class);
		GroupService groupService = ctx.getBean("groupService", GroupService.class);
		UserService userService = ctx.getBean("userService", UserService.class);
//
//		Product product= new Product();
//		product.setProductName("cafe den");
//		product.setProductPrice(10000);
		
//		productService.save(product);
		
		ContactServiceTest test = new ContactServiceTest();
		
//		test.testCategory(categoryService, "Nuoc giai khat");
		Group group = test.testGroup(groupService);
		List<Group> groups = new ArrayList<Group>();
		groups.add(group);
		User user = test.addUser(userService);
		user.setGrouplist(groups);
		userService.save(user);
		
		
		
	}

}
