package com.sanyo.quote.init;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;

import com.sanyo.quote.domain.*;
import com.sanyo.quote.service.*;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;

import com.sanyo.quote.helper.Constants;

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
		String electrical = Constants.ELECT_BOQ;
		String machanical = Constants.MECH_BOQ;
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
		ProductService productService = ctx.getBean("productService", ProductService.class);
		ProductGroupService productGroupService = ctx.getBean("productGroupService", ProductGroupService.class);
		MakerService makerService = ctx.getBean("makerService", MakerService.class);
		ExpenseElementsService expenseElementsService = ctx.getBean("expenseElementsService", ExpenseElementsService.class);
		
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
		if(productService.findAll().size() ==0){
			addProductSample(productService, categoryService, productGroupService);
		}
		if(makerService.findAll() != null && makerService.findAll().size() ==0 ){
			addMakerData(makerService);
		}
		if(expenseElementsService.findAll() != null && expenseElementsService.findAll().size() == 0) {
			addExpenseElements(expenseElementsService);
		}
		ctx.destroy();
	}
	private String getNumberValue(String input){
		if(input == null)
			return null;
		else{
			return input.replace(",", "").replace(" ", "");
		}
	}
	private void addProductSample(ProductService productService, CategoryService categoryService, ProductGroupService productGroupService){
//		List<Category> categories = new ArrayList<Category>();
//		categories.add(categoryService.findById(2));
		// {name, specification, productGroup, unit, labour, max_w_o_tax_usd, max_w_o_tax_vnd, }
		String[][] sampleProducts = {
					 {"Concrete Pole", "12m", "151-HV", "set", "105.00",null, "4,500,000"}
					,{"Concrete casting for foundation", null, "151-HV", "set", "105.00",null, "2,500,000"}
					,{"Crossarm", "L75x75x8 2.4m c/w support HDG - VN", "151-HV", "set", "6.56",null, "650,000"}
		};
//		
		for(int i =0; i< sampleProducts.length; i++){
			String[] temp = sampleProducts[i];
			String productName = temp[0];
			String productCode =  temp[0];
			String specification = temp[1];
			String productGroupCode = temp[2];
			String unit = temp[3];
			String labour = getNumberValue(temp[4]);
			String max_wo_tax_usd = getNumberValue(temp[5]);
			String max_wo_tax_vnd = getNumberValue(temp[6]);
			
			ProductGroup productGroup = productGroupService.findByGroupName(productGroupCode);
			if(productGroup == null){
				productGroup = new ProductGroup();
				productGroup.setGroupCode(productGroupCode);
				productGroup.setGroupName(productGroupCode);
				productGroup = productGroupService.save(productGroup);
			}
			Product product = new Product();
			product.setProductCode(productCode);
			product.setProductName(productName);
			product.setLastModifiedBy("ADMIN");
			if(max_wo_tax_usd != null)
				product.setMat_w_o_Tax_USD(Float.valueOf(max_wo_tax_usd));
			if(max_wo_tax_vnd != null)
				product.setMat_w_o_Tax_VND(Float.valueOf(max_wo_tax_vnd));
			product.setUnit(unit);
			if(labour != null)
				product.setLabour(Float.valueOf(labour));
			product.setSpecification(specification);
			product.setProductGroup(productGroup);
			productService.save(product);
		}
		
		
	}
	private void addMakerData(MakerService makerService){
		String[] makerList = {"ABB or equivalent", "Hai Nam", "Sunlight Electric", "LS / or equivalent"
				,"LS-Vina", "Taisin cable", "THT", "An thy", "Henikwon", "Paragon", "Formular"
				, "Maxspid"};
		
		for(String name: makerList){
			Maker maker = new Maker();
			maker.setName(name);
			makerService.save(maker);
		}
		
	}

	private void addExpenseElements (ExpenseElementsService expenseElementsService){
		String [] expenseElements = {
				"JP Yen 29,000/Day", "JP Yen 35,000/Day", "JP Yen 40,000/Day"
				,"Japanese Site correction","3rd Country Engineer", "Local Site Manager", "Local Engineer 1","Local Engineer 2"
				, "Local Engineer 3", "Local Engineer 4", "Cad Operator", "Safety Supervisor"
				, "Site Office", "Site Storage", "Drawing & Documents", "Site Goods", "Transportation (MAT)", "Transportation (Engineer)", "Safety Goods"
				, "Accomodation", "others", "Import Tax", "Computer, etc,", "3rd Party Insurance", "Performance Bond", "Tender Fees"
				, "Prosonal Income Tax", "Personal Social Insurance", "Bank Loan Intarest"
				, "Japanese, 3rd country", "Air Ticket", "Telephone", "Electric & Water fee", "Administration", "Misce", "Entertainmant"
				, "Application cost", "Japanese", "3rd Country", "Vietnam", "Vietnam", "Vietnam", "Vietnam", "Vietnam", "Vietnam", "Vietnam", "Vietnam", "Vietnam"
				, "Japanese", "3rd Country", "Vietnam", "Vietnam", "Vietnam", "Vietnam", "Vietnam", "Vietnam", "Vietnam", "Vietnam", "Vietnam"
				, "Japanese", "Vietnam"

		};
		int i = 0;
		for (String name: expenseElements){
			i ++;

			ExpenseElements element = new ExpenseElements();
			element.setElementName(name);
			element.setDefaultOrder(i);
			expenseElementsService.save(element);
		}
	}
}
