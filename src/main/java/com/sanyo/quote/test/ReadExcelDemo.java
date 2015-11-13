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

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.sanyo.quote.domain.Encounter;
import com.sanyo.quote.domain.Location;
import com.sanyo.quote.domain.ProductGroupMaker;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.Region;
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
	private static ProductService productService;
	private static ProjectService projectService;
	private static CategoryService categoryService;
	private static GroupService groupService;
	private static UserService userService;
	private static RegionService regionService;
	private static EncounterService encounterService;
	private static LocationService locationService;
	private static UserRegionRoleService userRegionRoleService;
	private static PriceService priceService;
	private static CurrencyExchRateService currencyExchRateService;
	private static CurrencyService currencyService;
	
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
						cell.setCellValue("　　　施工会社名 :" + " " + project.getCustomerName());
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
		int startRow = rowCount;
		for(ProductGroupMaker pg : productGroupMakers){
			order ++;
			Row row = sheet.createRow(rowCount);
			rowCount ++;
			for(int i=0; i<7; i++){
				Cell cell = row.createCell(i);
				cell.setCellStyle(getSampleStyleWithBorder(sheet.getWorkbook()));
				
				if(i ==0){
					if(!hasOrderForCategory){
						cell.setCellValue(order);
					}
				}
				else if (i == 1){
					// set category
					if(!hasOrderForCategory){
						cell.setCellValue(pg.getCategory().getName());
						hasOrderForCategory = true;
					}
				}
				else if (i == 2){
					// set category
					cell.setCellValue(pg.getProductGroup().getGroupName());
				}
				else if(i ==3){
					//set model no
					cell.setCellValue(pg.getModelNo());
				}
				else if(i ==4){
					//set maker name
					cell.setCellValue(pg.getMaker().getName());
				}
				else if(i ==5){
					//set delivery
					cell.setCellValue(pg.getDelivery());
				}
				else if(i ==6){
					//set remarks
					cell.setCellValue(pg.getRemark());
				}
			}
		}
//		org.apache.poi.ss.util.CellRangeAddress region = new CellRangeAddress(startRow, rowCount -1, 0, 1);
//		sheet.addMergedRegion(region);
	}
	public static XSSFCellStyle getSampleStyleWithBorder(XSSFWorkbook workbook){
		
		XSSFCellStyle cellStyle =  workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		return cellStyle;
		
	}
	public static XSSFCellStyle getSampleStyleWithBorderDash(XSSFWorkbook workbook){
		
		XSSFCellStyle cellStyle =  workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.DASHED);
		cellStyle.setBorderTop(BorderStyle.DASHED);
		cellStyle.setBorderLeft(BorderStyle.DASHED);
		cellStyle.setBorderRight(BorderStyle.DASHED);
		return cellStyle;
		
	}	
	public static XSSFCellStyle getSampleStyleNoBorder(XSSFWorkbook workbook){
		
		XSSFCellStyle cellStyle =  workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.NONE);
		cellStyle.setBorderTop(BorderStyle.NONE);
		cellStyle.setBorderLeft(BorderStyle.NONE);
		cellStyle.setBorderRight(BorderStyle.NONE);
		return cellStyle;
		
	}
	private static void setDataFormatForFloat(XSSFCellStyle cellStyle, XSSFWorkbook workbook){
		XSSFDataFormat xssfDataFormat = workbook.createDataFormat(); 
		cellStyle.setDataFormat(xssfDataFormat.getFormat("#,##0.000"));
	}
	//print based on location.
	private static void createBoQSheet(Project project, XSSFWorkbook workbook){
		XSSFSheet sheet = workbook.getSheetAt(5);
		int rowCount =5;
		int order = 1;
		List<Location> locations = projectService.findLocations(project.getProjectId());
		for(Location location: locations){
			List<Region> regions = locationService.findRegions(location.getLocationId());
			for(Region region : regions){
				List<Encounter> encounters = encounterService.getEncountersByRegion(region);
				createBOQRows(encounters, sheet, rowCount, order);
				rowCount += encounters.size();
			}
		}
		
	}
	private static void writeCellValue(Cell cell, String text){
		if(text != null){
			cell.setCellValue(text);
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		}
	}
	private static void writeCellValue(Cell cell, Float text){
		XSSFWorkbook workbook = (XSSFWorkbook) cell.getRow().getSheet().getWorkbook();
		XSSFCellStyle cellStyle = getSampleStyleWithBorderDash(workbook);
		setDataFormatForFloat(cellStyle, workbook);
		if(text !=null){
			cell.setCellValue(text);
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellStyle(cellStyle);
		}
	}
	private static void writeCellValue(Cell cell, int text){
		cell.setCellValue(text);
	}
	private static void createBOQRows(List<Encounter> encounters, XSSFSheet sheet, int rowCount, int order ){
		boolean hasOrderForCategory = false;
		int startRow = rowCount;
		for(Encounter encounter : encounters){
			Row row = sheet.createRow(rowCount);
			rowCount++;
			for(int i=0; i< 31; i++){
				Cell cell = row.createCell(i);
				cell.setCellStyle(getSampleStyleWithBorderDash(sheet.getWorkbook()));
				if(i==0){
					
				}else if(i==1){ //product name
					writeCellValue(cell,encounter.getProduct().getProductName());
				}else if(i==2){
					//product specification
					writeCellValue(cell, encounter.getProduct().getSpecification());
				}else if(i==3){
					//product group
					writeCellValue(cell, encounter.getProduct().getProductGroup().getGroupCode());
				}else if(i==4){
					//product unit
					writeCellValue(cell, encounter.getProduct().getUnit());
				}else if(i==5){
					//product quantity
					writeCellValue(cell, encounter.getQuantity());
				}else if(i==6){
					//unit rate
					writeCellValue(cell, encounter.getUnitRate());
				}else if(i==7){
					//Amount
					writeCellValue(cell, encounter.getAmount());
				}else if(i==8){
					//remarks
					writeCellValue(cell, encounter.getRemark());
				}else if(i==9){
					//empty column
					//show order
					writeCellValue(cell, encounter.getOrder());
				}else if(i==10){
					//quantity of labour
					writeCellValue(cell, encounter.getQuantity());
				}else if(i==11){
					//labour unit (price)
					writeCellValue(cell, encounter.getLabour());
				}else if(i==12){
					//labour total = quantity * labour price
					writeCellValue(cell, encounter.getQuantity() * encounter.getLabour());
				}else if(i==13){
					//empty col
					
				}else if(i==14){
					//percent
					writeCellValue(cell, encounter.getNonamePercent());
				}else if(i==15){
					//range
					writeCellValue(cell, encounter.getNonameRange());
				}else if(i==16){
					//mat w_o_tax_usd
					writeCellValue(cell, encounter.getMat_w_o_Tax_USD());
				}else if(i==17){
					//mat w_o_tax_vnd
					writeCellValue(cell, encounter.getMat_w_o_Tax_VND());
				}else if(i==18){
					//labour w_o tax usd
					writeCellValue(cell, encounter.getCost_Labour_Amount_USD());
				}else if(i==19){
					//import duty
					writeCellValue(cell, encounter.getImp_Tax());
				}else if(i==20){
					//special tax
					writeCellValue(cell, encounter.getSpecial_Con_Tax());
				}else if(i==21){
					//vat
					writeCellValue(cell, encounter.getVAT());
				}else if(i==22){
					//discount rate
					writeCellValue(cell, encounter.getDiscount_rate());
				}else if(i==23){
					//unit price after discount
					writeCellValue(cell, encounter.getUnit_Price_After_Discount());
				}else if(i==24){
					//allowance
					writeCellValue(cell, encounter.getAllowance());
				}else if(i==25){
					//unit price with tax and profit - Mat submit usd
					// it==ROUND(Y117*Z117,2) = unit price after discount * allowance
					writeCellValue(cell, encounter.getUnit_Price_After_Discount() * encounter.getAllowance());
				}else if(i==26){
					//subCon profit
					writeCellValue(cell, encounter.getSubcon_Profit());
				}else if(i==27){
					//Unit price with tax  labour c/w tax us
					writeCellValue(cell, encounter.getUnit_Price_W_Tax_Labour());
				}else if(i==28){
					//Unit price w tax & profit labour submit us
				
				}else if(i==29){
					//mat amount usd
					writeCellValue(cell, encounter.getMat_w_o_Tax_USD());
				}else if(i==30){
					//lab amount usd
					writeCellValue(cell, encounter.getCost_Labour_Amount_USD());
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
			
			productService = ctx.getBean("productService", ProductService.class);
			projectService = ctx.getBean("projectService", ProjectService.class);
			categoryService = ctx.getBean("categoryService", CategoryService.class);
			groupService = ctx.getBean("groupService", GroupService.class);
			userService = ctx.getBean("userService", UserService.class);
			regionService = ctx.getBean("regionService", RegionService.class);
			encounterService = ctx.getBean("encounterService", EncounterService.class);
			locationService = ctx.getBean("locationService", LocationService.class);
			userRegionRoleService = ctx.getBean("userRegionRoleService", UserRegionRoleService.class);
			priceService = ctx.getBean("priceService", PriceService.class);
			currencyExchRateService = ctx.getBean("currencyExchRateService", CurrencyExchRateService.class);
			currencyService = ctx.getBean("currencyService", CurrencyService.class);
			
			FileInputStream file = new FileInputStream(new File("report_template.xlsx"));

			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			Project project = projectService.findAll().get(0);
			updateCover(project, workbook);
			updateCondition1(project, workbook);
			updateElecMaker(project, workbook, projectService);
			createBoQSheet(project, workbook);
			
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
