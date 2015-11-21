package com.sanyo.quote.init;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;

import com.sanyo.quote.domain.Category;
import com.sanyo.quote.domain.Currency;
import com.sanyo.quote.domain.ExpenseElements;
import com.sanyo.quote.domain.Group;
import com.sanyo.quote.domain.Maker;
import com.sanyo.quote.domain.Product;
import com.sanyo.quote.domain.ProductGroup;
import com.sanyo.quote.domain.ProductGroupMaker;
import com.sanyo.quote.domain.User;
import com.sanyo.quote.helper.Constants;
import com.sanyo.quote.service.CategoryService;
import com.sanyo.quote.service.CurrencyService;
import com.sanyo.quote.service.ExpenseElementsService;
import com.sanyo.quote.service.GroupService;
import com.sanyo.quote.service.MakerService;
import com.sanyo.quote.service.ProductGroupMakerService;
import com.sanyo.quote.service.ProductGroupService;
import com.sanyo.quote.service.ProductService;
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
		CurrencyService currencyService = ctx.getBean("currencyService", CurrencyService.class);
		ProductGroupMakerService productGroupMakerService = ctx.getBean("productGroupMakerService", ProductGroupMakerService.class);
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
		if(productGroupService.findAll() != null && productGroupService.findAll().size() ==0){
			addProductGroup(productGroupService);
		}
		if(makerService.findAll() != null && makerService.findAll().size() ==0 ){
			addMakerData(makerService, productGroupService, productGroupMakerService);
		}
		if(expenseElementsService.findAll() != null && expenseElementsService.findAll().size() == 0) {
			addExpenseElements(expenseElementsService);
		}
		if(currencyService.findAll() != null && currencyService.findAll().size() ==0){
			addCurrency(currencyService);
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
	private void addMakerData(MakerService makerService, ProductGroupService productGroupService,ProductGroupMakerService productGroupMakerService){
		String[] makerList = {"ABB or equivalent", "Hai Nam", "Sunlight Electric", "LS / or equivalent"
				,"LS-Vina", "Taisin cable", "THT", "An thy", "Henikwon", "Paragon", "Formular"
				, "Maxspid"};
		
		for(String name: makerList){
			Maker maker = new Maker();
			maker.setName(name);
			maker = makerService.save(maker);
			
			//add productGroupMaker data;
			ProductGroupMaker pgm = new ProductGroupMaker();
			pgm.setCategory(null);
			pgm.setMaker(maker);
			pgm.setProductGroup(productGroupService.findByGroupCode("151-HV"));
			
			productGroupMakerService.save(pgm);
		}
		
		
		
		
	}

	private void addExpenseElements (ExpenseElementsService expenseElementsService){
		String [] expenseElements = {
				"JP Yen 29,000/Day;6571.43", "JP Yen 35,000/Day;7979.59", "JP Yen 40,000/Day"
				,"Japanese Site correction;918.37","3rd Country Engineer;4000", "Local Site Manager;1200", "Local Engineer 1;1000","Local Engineer 2;900"
				, "Local Engineer 3;800", "Local Engineer 4", "Cad Operator;600", "Safety Supervisor;500"
				, "Site Office", "Site Storage;200", "Drawing & Documents;500", "Site Goods;300", "Transportation (MAT)", "Transportation (Engineer);1800", "Safety Goods"
				, "Accomodation", "others", "Import Tax", "Computer, etc,", "3rd Party Insurance", "Performance Bond", "Tender Fees"
				, "Prosonal Income Tax", "Personal Social Insurance", "Bank Loan Intarest"
				, "Japanese, 3rd country;900", "Air Ticket;1000", "Telephone;30", "Electric & Water fee", "Administration;120", "Misce;200", "Entertainmant;100"
				, "Application cost", "Japanese;900", "3rd Country;550", "Vietnam;171.43", "Vietnam;142.86", "Vietnam;128.57", "Vietnam;114.29", "Vietnam;100", "Vietnam;85.71", "Vietnam;71.43", "Vietnam;57.14", "Vietnam;42.86"
				, "Japanese", "3rd Country;600", "Vietnam;300", "Vietnam;250", "Vietnam;225", "Vietnam;200", "Vietnam;175", "Vietnam;150", "Vietnam;125", "Vietnam;100", "Vietnam;75"
				, "Japanese", "Vietnam;23.67"

		};
		int i = 0;
		for (String name: expenseElements){
			i ++;

			ExpenseElements element = new ExpenseElements();
			String [] NameAndRate = name.split(";");
			element.setElementName(NameAndRate[0]);
			if (NameAndRate.length>1)
				element.setDefaultRate(Float.valueOf(NameAndRate[1]));
			element.setDefaultOrder(i);
			expenseElementsService.save(element);
		}
	}
	private void addCurrency(CurrencyService currencyService){
		String[] list = {"USD", "VND", "JPY"};
		for(String name : list){
			Currency c = new Currency();
			c.setCurrencyCode(name);
			c.setCurrencyName(name);
			currencyService.save(c);
		}
	}
	private void addProductGroup(ProductGroupService productGroupService){
		String[] list = {"151-HV", "Code 111"};
		for(String name : list){
			ProductGroup pg = new ProductGroup();
			pg.setGroupCode(name);
			pg.setGroupName(name);
			productGroupService.save(pg);
			}
	}
}
