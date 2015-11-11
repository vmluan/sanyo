package com.sanyo.quote.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.sanyo.quote.domain.ProductGroupMaker;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.service.CategoryService;
import com.sanyo.quote.service.CurrencyExchRateService;
import com.sanyo.quote.service.CurrencyService;
import com.sanyo.quote.service.EncounterService;
import com.sanyo.quote.service.GroupService;
import com.sanyo.quote.service.LocationService;
import com.sanyo.quote.service.PriceService;
import com.sanyo.quote.service.ProductService;
import com.sanyo.quote.service.ProjectService;
import com.sanyo.quote.service.RegionService;
import com.sanyo.quote.service.UserRegionRoleService;
import com.sanyo.quote.service.UserService;

public class ReadExcelDemo 
{
	private static void updateCover(Project project,XSSFWorkbook workbook){
		XSSFSheet sheet = workbook.getSheetAt(0);

		//Iterate through each rows one by one
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) 
		{
			Row row = rowIterator.next();
			//For each row, iterate through all the columns
			Iterator<Cell> cellIterator = row.cellIterator();
			
			while (cellIterator.hasNext()) 
			{
				Cell cell = cellIterator.next();
				if(cell.getStringCellValue().equalsIgnoreCase("${projectName}")){
					cell.setCellValue(project.getProjectName());
				}
				else if(cell.getStringCellValue().equalsIgnoreCase("${clientName}")){
					cell.setCellValue(project.getCustomerName());
				}
				else if(cell.getStringCellValue().equalsIgnoreCase("${projectCode}")){
					cell.setCellValue(project.getProjectCode());
				}
				else if(cell.getStringCellValue().equalsIgnoreCase("${reportDate}")){
					cell.setCellValue(new Date());
				}
				else if(cell.getStringCellValue().equalsIgnoreCase("${duration}")){
					cell.setCellValue(project.getDuration().toString());
				}
//				switch (cell.getCellType()) 
//				{
//					case Cell.CELL_TYPE_NUMERIC:
//						break;
//					case Cell.CELL_TYPE_STRING:
////						cell.setCellValue(cell.getStringCellValue() + " test updated");
//						break;
//				}
			}
		}
	}
	private static void updateCondition1(Project project,XSSFWorkbook workbook){
		XSSFSheet sheet = workbook.getSheetAt(1);

		//Iterate through each rows one by one
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) 
		{
			Row row = rowIterator.next();
			//For each row, iterate through all the columns
			Iterator<Cell> cellIterator = row.cellIterator();
			
			while (cellIterator.hasNext()) 
			{
				Cell cell = cellIterator.next();
				if(cell.getCellType() == Cell.CELL_TYPE_STRING){
					if(cell.getStringCellValue().contains("${projectName}")){
						cell.setCellValue(cell.getStringCellValue() + " " + project.getProjectName());
					}
					if(cell.getStringCellValue().contains("${clientName}")){
						cell.setCellValue(cell.getStringCellValue() + " " + project.getCustomerName());
					}
					if(cell.getStringCellValue().contains("${projectCode}")){
						cell.setCellValue(cell.getStringCellValue() + " " + project.getProjectCode());
					}
					if(cell.getStringCellValue().contains("${reportDate}")){
						cell.setCellValue(cell.getStringCellValue() + " " + new Date());
					}
					if(cell.getStringCellValue().contains("${duration}")){
						cell.setCellValue(cell.getStringCellValue() + " " + project.getDuration().toString());
					}
				}
			}
		}
	}
	private static void updateElecMaker(Project project,XSSFWorkbook workbook,ProjectService projectService){
		XSSFSheet sheet = workbook.getSheetAt(6);
		List<ProductGroupMaker> productGroupMakers = projectService.findProductGroupMakers(project.getProjectId());
		//Iterate through each rows one by one
		Iterator<Row> rowIterator = sheet.iterator();
		int rowCount = 0;
		while (rowIterator.hasNext()) 
		{
			Row row = rowIterator.next();
			rowCount ++;
			
			//For each row, iterate through all the columns
			Iterator<Cell> cellIterator = row.cellIterator();
			
			while (cellIterator.hasNext()) 
			{
				Cell cell = cellIterator.next();
				if(cell.getCellType() == Cell.CELL_TYPE_STRING){
					if(cell.getStringCellValue().contains("${clientName}")){
						cell.setCellValue(cell.getStringCellValue() + " " + project.getCustomerName());
					}
				}
			}
		}
		
		int stt =0;
		
		
		TreeMap<String, List<ProductGroupMaker>> list = new TreeMap<String, List<ProductGroupMaker>>();
		for(ProductGroupMaker pg : productGroupMakers){
			List<ProductGroupMaker> existingValues = list.get(pg.getCategory().getName());
			if(existingValues != null){
				existingValues.add(pg);
			}
			else{
				existingValues = new ArrayList<ProductGroupMaker>();
				existingValues.add(pg);
				list.put(pg.getCategory().getName(), existingValues);
			}
		}
		
		for(Map.Entry<String,List<ProductGroupMaker>> entry : list.entrySet()) {
			  String key = entry.getKey();
			  List<ProductGroupMaker> childList = entry.getValue();
			  createMakerRows(childList, sheet, rowCount, stt);
			  stt += 1;
			  rowCount += childList.size();
			  
			  
			}
		
		
	}
	private static void createMakerRows(List<ProductGroupMaker> productGroupMakers, XSSFSheet sheet, int rowCount, int order ){
		boolean hasOrderForCategory = false;
		for(ProductGroupMaker pg : productGroupMakers){
			order ++;
			Row row = sheet.createRow(rowCount);
			rowCount ++;
			for(int i=0; i<8; i++){
				Cell cell = row.createCell(i);
				if(i ==0){
					if(!hasOrderForCategory){
						cell.setCellValue(order);
					}
				}
				else if (i == 2){
					// set category
					if(!hasOrderForCategory){
						cell.setCellValue(pg.getCategory().getName());
						hasOrderForCategory = true;
					}
				}
				else if (i == 3){
					// set category
					cell.setCellValue(pg.getProductGroup().getGroupName());
				}
				else if(i ==4){
					//set model no
					cell.setCellValue(pg.getModelNo());
				}
				else if(i ==5){
					//set maker name
					cell.setCellValue(pg.getMaker().getName());
				}
				else if(i ==6){
					//set delivery
					cell.setCellValue(pg.getDelivery());
				}
				else if(i ==7){
					//set remarks
					cell.setCellValue(pg.getRemark());
				}
			}
		}
	}
	public static void main(String[] args) 
	{
		try
		{
			GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
			ctx.load("classpath:jpa-app-context.xml");
			ctx.refresh();
			
			ProductService productService = ctx.getBean("productService", ProductService.class);
			ProjectService projectService = ctx.getBean("projectService", ProjectService.class);
			CategoryService categoryService = ctx.getBean("categoryService", CategoryService.class);
			GroupService groupService = ctx.getBean("groupService", GroupService.class);
			UserService userService = ctx.getBean("userService", UserService.class);
			RegionService regionService = ctx.getBean("regionService", RegionService.class);
			EncounterService encounterService = ctx.getBean("encounterService", EncounterService.class);
			LocationService locationService = ctx.getBean("locationService", LocationService.class);
			UserRegionRoleService userRegionRoleService = ctx.getBean("userRegionRoleService", UserRegionRoleService.class);
			PriceService priceService = ctx.getBean("priceService", PriceService.class);
			CurrencyExchRateService currencyExchRateService = ctx.getBean("currencyExchRateService", CurrencyExchRateService.class);
			CurrencyService currencyService = ctx.getBean("currencyService", CurrencyService.class);
			
			FileInputStream file = new FileInputStream(new File("report_template.xlsx"));

			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			Project project = projectService.findAll().get(0);
			updateCover(project, workbook);
			updateCondition1(project, workbook);
			updateElecMaker(project, workbook, projectService);
			file.close();
			
			String outFileName = project.getProjectName() + ".xlsx";
			FileOutputStream out = new FileOutputStream(new File(outFileName));
			workbook.write(out);
		ctx.destroy();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
