package com.sanyo.quote.init;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;

import com.sanyo.quote.domain.Category;
import com.sanyo.quote.domain.Group;
import com.sanyo.quote.domain.User;
import com.sanyo.quote.service.CategoryService;
import com.sanyo.quote.service.GroupService;
import com.sanyo.quote.service.UserService;

@Component
public class StartupContextListener extends ContextLoaderListener{
	
	@Override
	public void contextInitialized(ServletContextEvent event){
		try{
			super.contextInitialized(event);
		}
		catch(Exception ex){
			System.out.println("=========================== application is on start with error:" + ex);
		}
		System.out.println("=========================== application is on start up");
		addSampleData();
	}
	private void addCategorySampleData(CategoryService categoryService){
		String electrical = "ELECTRICAL BOQ";
		String machanical = "MECHANICAL BOQ";
		Category electricalCategory = new Category();
		electricalCategory.setName(electrical);
		electricalCategory.setDesc(" ");
		categoryService.save(electricalCategory);
		
		
		String[] elecChildsName = { "HIGH VOLTAGE SYSTEM", "LV MAIN SYSTEM", "SUBMAIN SYSTEM",
				"POWER SUPPLY TO PRODUCTION", "EMERGENCY POWER BACK-UP GENERATOR", "LIGHTING SYSTEM & SOCKET OUTLET",
				"ELECTRICAL FOR MECHANICAL EQUIPMENT (A.C, FAN, AIR COMP., PUMPS)", "TELEPHONE SYSTEM EMPTY CONDUIT",
				"LAN SYSTEM EMPTY CONDUIT", "PUBLIC ADDRESS SYSTEM", "FIRE ALARM SYSTEM", "LIGHTNING PROTECTION SYSTEM",
				"WATER LEAKING ALARM (FOR PLATING AREA)" };
		for(int i=0; i< elecChildsName.length; i++){
			saveCategory(elecChildsName[i], categoryService, electricalCategory);
		}
		
		Category mechCategory = new Category();
		mechCategory.setName(machanical);
		mechCategory.setDesc(" ");
		categoryService.save(mechCategory);
		
		String[] mechList = { "AIR CONDITIONING SYSTEM", "VENTILATION SYSTEM", "COLD WATER SUPPLY",
				"SEWAGE DRAINAGE SYSTEM", "SANITARY WARE", "FIRE FIGHTING SYSTEM", "AIR COMPRESSOR SYSTEM",
				"STEAM BOILER SYSTEM", "LOCAL PRODUCTION HEAT EXHAUST SYSTEM (Heat Treatment & Plating Area )" };
		for(int i=0; i< mechList.length; i++){
			saveCategory(mechList[i], categoryService, mechCategory);
		}

	}
	private void saveCategory(String categoryName, CategoryService categoryService, Category parent){
		Category category = new Category();
//		category.setCategoryId(id);
		category.setName(categoryName);
		category.setDesc(" ");
		if(parent != null)
			category.setParentCategory(parent);
		categoryService.save(category);
	}
	private void addSampleData(){
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:jpa-app-context.xml");
		ctx.refresh();
		
		System.out.println("App context initialized successfully");
		
		UserService userService = ctx.getBean("userService", UserService.class);
		GroupService groupService = ctx.getBean("groupService", GroupService.class);
		CategoryService categoryService = ctx.getBean("categoryService", CategoryService.class);
		for(int i=0; i < 100; i++){
			String userName = "admin" + i;
			if(i ==0)
				userName = "admin";
			User admin = userService.findByUserName(userName);
			if(admin == null){
				
				
			
				
				User user = new User();
				user.setUsername(userName);
				user.setUsercode("01234");
				user.setPassword("ee10c315eba2c75b403ea99136f5b48d"); // admin in reserve,
				user.setActive(true);
				user.setEmail(userName + "@gmail.com"); 
				
				
				userService.save(user);
				Group userGroup = groupService.findByGroupName("ROLE_USER");
				if(userGroup == null){
					userGroup = new Group();
					userGroup.setGroupName("ROLE_USER");
					groupService.save(userGroup);
					
					List<Group> groupList = new ArrayList<Group>();
					groupList.add(userGroup);
					if(userName.equals("admin")){
						Group roleAdmin = new Group();
						roleAdmin.setGroupName("ROLE_ADMIN");
						groupList.add(roleAdmin);
					}
					user.setGrouplist(groupList);
				}else{
					List<Group> groupList = user.getGrouplist();
					if(groupList == null)
						groupList = new ArrayList<Group>();
					groupList.add(userGroup);
					user.setGrouplist(groupList);
				}
				userService.save(user);
				
			}else if(admin != null && userName.equals("admin")){
				Group adminRole = groupService.findByGroupName("ROLE_ADMIN");
				if(adminRole == null){
					adminRole = new Group();
					adminRole.setGroupName("ROLE_ADMIN");
				}
				List<Group> groupList = admin.getGrouplist();
				if(groupList == null )
					groupList = new ArrayList<Group>();
				groupList.add(adminRole);
				userService.save(admin);
			}
			
		}
		
		List<Category> categories = categoryService.findAll();
		if(categories.size() ==0){
			addCategorySampleData(categoryService);
		}
		
		ctx.destroy();
	}
}
