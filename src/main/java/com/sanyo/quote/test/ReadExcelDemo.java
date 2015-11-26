package com.sanyo.quote.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.sanyo.quote.domain.Encounter;
import com.sanyo.quote.domain.Location;
import com.sanyo.quote.domain.ProductGroupMaker;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.helper.Constants;
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
	
	private static TreeMap<Integer, Integer> summRegionsTree = new TreeMap<Integer, Integer>();
	
	private class RowCount{
		int rowCount = 0;

		public int getRowCount() {
			return rowCount;
		}

		public void setRowCount(int rowCount) {
			this.rowCount = rowCount;
		}
		public void addMoreValue(int value){
			this.rowCount += value;
		}
	}
	
	private static void updateCover(Project project,XSSFWorkbook workbook, RowCount rowCount){
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
//		List<ProductGroupMaker> productGroupMakers = projectService.findProductGroupMakers(project.getProjectId());
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
					}
				}
			}
		}
		
		int stt =0;
		
		
		TreeMap<String, List<ProductGroupMaker>> list = new TreeMap<String, List<ProductGroupMaker>>();
		
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
//		for(ProductGroupMaker pg : productGroupMakers){
//			order ++;
//			Row row = sheet.getRow(rowCount);
//			rowCount ++;
//			for(int i=0; i<7; i++){
//				Cell cell = row.createCell(i);
//				cell.setCellStyle(getSampleStyleWithBorder(sheet.getWorkbook()));
//				
//				if(i ==0){
//					if(!hasOrderForCategory){
//						cell.setCellValue(order);
//					}
//				}
//				else if (i == 1){
//					// set category
//					if(!hasOrderForCategory){
//						cell.setCellValue(pg.getCategory().getName());
//						hasOrderForCategory = true;
//					}
//				}
//				else if (i == 2){
//					// set category
//					cell.setCellValue(pg.getProductGroup().getGroupName());
//				}
//				else if(i ==3){
//					//set model no
//					cell.setCellValue(pg.getModelNo());
//				}
//				else if(i ==4){
//					//set maker name
//					cell.setCellValue(pg.getMaker().getName());
//				}
//				else if(i ==5){
//					//set delivery
//					cell.setCellValue(pg.getDelivery());
//				}
//				else if(i ==6){
//					//set remarks
//					cell.setCellValue(pg.getRemark());
//				}
//			}
//		}
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
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
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
	public static XSSFCellStyle getSampleStyleForLocation(XSSFWorkbook workbook){
		
		XSSFCellStyle cellStyle =  workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.DASHED);
		cellStyle.setBorderTop(BorderStyle.DASHED);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		XSSFFont font = workbook.createFont();
//		font.setBold(true);
		font.setFontHeightInPoints((short) 14);
		font.setFontName("Arial");
		cellStyle.setFont(font);
		return cellStyle;
		
	}
	public static XSSFCellStyle getSampleStyleForRegion(XSSFWorkbook workbook){
		
		XSSFCellStyle cellStyle =  workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.DASHED);
		cellStyle.setBorderTop(BorderStyle.DASHED);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		XSSFFont font = workbook.createFont();
//		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Arial");
		cellStyle.setFont(font);
		return cellStyle;
		
	}
	private static void setDataFormatForFloat(XSSFCellStyle cellStyle, XSSFWorkbook workbook){
		XSSFDataFormat xssfDataFormat = workbook.createDataFormat(); 
		cellStyle.setDataFormat(xssfDataFormat.getFormat("#,##0.000"));
	}
	//print based on location.
	private static void createBoQSheet(Project project, XSSFWorkbook workbook, RowCount rowCount){
		XSSFSheet sheet = workbook.getSheetAt(5);
//		rowCount.setRowCount(5);//change this number later if need
		int order = 1;
		int startRowOfRegion=0;
		List<Location> locations = projectService.findLocations(project.getProjectId());
		
		createSummaryOfLocations(locations, sheet, rowCount, order);
		createBreakDownRow(sheet, rowCount, order);
		rowCount.addMoreValue(2);
		
		for(Location location: locations){
			//create location row
			createLocationRow(location, sheet, rowCount, order);
//			rowCount +=2;s
			List<Region> regions = locationService.findRegions(location.getLocationId());
			int numOfRegions = 0;
			for(Region region : regions){
				numOfRegions ++;
				//create region row
				createRegionRow(region, sheet, rowCount, order);
//				rowCount +=2;
				startRowOfRegion = rowCount.getRowCount();
				List<Encounter> encounters = encounterService.getEncountersByRegion(region);
				createBOQRows(encounters, sheet, rowCount, order);
//				rowCount += encounters.size();
				//create sub-total for each region.
				createSubTotal(startRowOfRegion, rowCount, numOfRegions, sheet);
				
				//update total summary for region.
				int regionSummRowNum = summRegionsTree.get(region.getRegionId());
				Row row = sheet.getRow(regionSummRowNum);
				updateTotalSummaryForRegion(row,rowCount.getRowCount()-1);
			}
			//update total summary for location.
		}
		
	}
	private static void updateTotalSummaryForRegion(Row row, int rowNum){
		updateTotalRegionAmountFormula(row, rowNum);
		updateTotalRegionLabourAmountFormula(row, rowNum);
		updateTotalRegionAEFormula(row, rowNum);
		updateTotalRegionAFFormula(row, rowNum);
	}
	private static String getTotalRegionFormula(int row, String column){
		String strFormula = column + row;;
		return strFormula;
	}
	private static void updateTotalRegionAmountFormula(Row row, int rowNum){
		String strFomula = getTotalRegionFormula(rowNum, "H");
		Cell cell7 = row.createCell(Constants.BOQ_COLUMN_AMOUNT); //amount
		writeCellFomula(cell7, strFomula);
	}
	private static void updateTotalRegionLabourAmountFormula(Row row, int rowNum){
		String strFomula = getTotalRegionFormula(rowNum, "M");
		Cell cell7 = row.createCell(Constants.BOQ_COLUMN_LABOUR_AMOUNT); //amount
		writeCellFomula(cell7, strFomula);
	}
	private static void updateTotalRegionAEFormula(Row row, int rowNum){
		String strFomula = getTotalRegionFormula(rowNum, "AE");
		Cell cell7 = row.createCell(Constants.BOQ_COLUMN_Mat_w_o_Tax_USD); //amount
		writeCellFomula(cell7, strFomula);
	}
	private static void updateTotalRegionAFFormula(Row row, int rowNum){
		String strFomula = getTotalRegionFormula(rowNum, "AF");
		Cell cell7 = row.createCell(Constants.BOQ_COLUMN_Cost_Labour_Amount_USD); //amount
		writeCellFomula(cell7, strFomula);
	}
	private static String getSubTotalFormula(int start, int end, String column){
		String strFormula = "SUBTOTAL(9," + column + start + ":" + column + end + ")";
		return strFormula;
	}
	private static void createSubTotal(int startRow, RowCount endRow, int order, XSSFSheet sheet){
		endRow.addMoreValue(1);
		Row row = sheet.getRow(endRow.getRowCount());
//		String strFomula = "SUBTOTAL(9,H" + startRow + ":H" + endRow.getRowCount() + ")";
		Cell cell1 = row.createCell(1);
		writeCellValue(cell1, "Sub toal " + order);
		updateSubTotal(row, startRow, endRow.getRowCount());
		
		endRow.addMoreValue(2);
	}
	//update H column value
	private static void updateAmountFormula(Row row, int startRow, int endRow){
		String strFomula = getSubTotalFormula(startRow, endRow, "H");
		Cell cell7 = row.createCell(Constants.BOQ_COLUMN_AMOUNT); //amount
		writeCellFomula(cell7, strFomula);
	}
	//update M column value
	private static void updateLabourAmountFormula(Row row, int startRow, int endRow){
		String strFomula = getSubTotalFormula(startRow, endRow, "M");
		Cell cell7 = row.createCell(Constants.BOQ_COLUMN_LABOUR_AMOUNT); //amount
		writeCellFomula(cell7, strFomula);
	}
	private static void updateAEFormula(Row row, int startRow, int endRow){
		String strFomula = getSubTotalFormula(startRow, endRow, "AE");
		Cell cell7 = row.createCell(Constants.BOQ_COLUMN_Mat_w_o_Tax_USD); //amount
		writeCellFomula(cell7, strFomula);
	}
	private static void updateAFFormula(Row row, int startRow, int endRow){
		String strFomula = getSubTotalFormula(startRow, endRow, "AF");
		Cell cell7 = row.createCell(Constants.BOQ_COLUMN_Cost_Labour_Amount_USD); //amount
		writeCellFomula(cell7, strFomula);
	}
	
	private static void writeCellValue(Cell cell, String text){
		if(text != null){
			cell.setCellValue(text);
			cell.setCellType(Cell.CELL_TYPE_STRING);
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
	private static void writeCellFomula(Cell cell, String strFormula){
		cell.setCellType(Cell.CELL_TYPE_FORMULA);
		cell.setCellFormula(strFormula);
	}
	private static void writeCellValue(Cell cell, int text){
		cell.setCellValue(text);
	}
	private static void createLocationRow(Location location, XSSFSheet sheet, RowCount rowCount, int order ){
		
		Row row = sheet.getRow(rowCount.getRowCount());
		rowCount.addMoreValue(1);
		Cell cell = row.createCell(1);
		cell.setCellValue(location.getLocationName());
		cell.setCellStyle(getSampleStyleForLocation(sheet.getWorkbook()));
	}
private static void createRegionHeaderRow(Region region, XSSFSheet sheet, RowCount rowCount, int order ){
		
		Row row = sheet.getRow(rowCount.getRowCount());
		rowCount.addMoreValue(1);
		Cell cell = row.createCell(1);
		cell.setCellValue(region.getRegionName());
		cell.setCellStyle(getSampleStyleForLocation(sheet.getWorkbook()));
	}
	private static void createRegionRow(Region region, XSSFSheet sheet, RowCount rowCount, int order ){
		Row row = sheet.getRow(rowCount.getRowCount());
		rowCount.addMoreValue(1);;
		Cell cell = row.createCell(1);
		cell.setCellValue(region.getRegionName());
		cell.setCellStyle(getSampleStyleForRegion(sheet.getWorkbook()));
		sheet.getRow(rowCount.getRowCount());
	}
	private static void createBOQRows(List<Encounter> encounters, XSSFSheet sheet, RowCount rowCount, int order ){
		boolean hasOrderForCategory = false;
		int startRow = rowCount.getRowCount();
		for(Encounter encounter : encounters){
			Row row = sheet.getRow(rowCount.getRowCount());
			rowCount.addMoreValue(1);
			for(int i=0; i< 32; i++){
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
					String strFormula = "ROUND(" + encounter.getAmount()+ ",2)";
					writeCellFomula(cell, strFormula);
//					writeCellValue(cell, encounter.getAmount());
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
					
				}else if(i ==14){
					//Code column. 
					//show empty
				}
				else if(i==15){
					//percent
					writeCellValue(cell, encounter.getNonamePercent());
				}else if(i==16){
					//range
					writeCellValue(cell, encounter.getNonameRange());
				}else if(i==17){
					//mat w_o_tax_usd
					writeCellValue(cell, encounter.getMat_w_o_Tax_USD());
				}else if(i==18){
					//mat w_o_tax_vnd
					writeCellValue(cell, encounter.getMat_w_o_Tax_VND());
				}else if(i==19){
					//labour w_o tax usd
					writeCellValue(cell, encounter.getCost_Labour_Amount_USD());
				}else if(i==20){
					//import duty
					writeCellValue(cell, encounter.getImp_Tax());
				}else if(i==21){
					//special tax
					writeCellValue(cell, encounter.getSpecial_Con_Tax());
				}else if(i==22){
					//vat
					writeCellValue(cell, encounter.getVAT());
				}else if(i==23){
					//discount rate
					writeCellValue(cell, encounter.getDiscount_rate());
				}else if(i==24){
					//unit price after discount
					writeCellValue(cell, encounter.getUnit_Price_After_Discount());
				}else if(i==25){
					//allowance
					writeCellValue(cell, encounter.getAllowance());
				}else if(i==26){
					//unit price with tax and profit - Mat submit usd
					// it==ROUND(Y117*Z117,2) = unit price after discount * allowance
					writeCellValue(cell, encounter.getUnit_Price_After_Discount() * encounter.getAllowance());
				}else if(i==27){
					//subCon profit
					writeCellValue(cell, encounter.getSubcon_Profit());
				}else if(i==28){
					//Unit price with tax  
					//labour c/w tax us
					String strFormula= "ROUND((T" + rowCount.getRowCount() +"*(1+" + "AB"+ rowCount.getRowCount() +")),2)";
					System.out.println(strFormula);
					cell.setCellType(Cell.CELL_TYPE_FORMULA);
					cell.setCellFormula(strFormula);
				}else if(i==29){
					//Unit price w tax & profit 
					//labour submit us
					//ROUND(AC6*'Summary-E'!$R$50,2)
					
					String strFormula= "ROUND(AC" + rowCount.getRowCount() + "*'Summary-E'!$R$50,2)";
					System.out.println(strFormula);
					cell.setCellType(Cell.CELL_TYPE_FORMULA);
					cell.setCellFormula(strFormula);
				
				}else if(i==30){
					//mat amount usd
					writeCellValue(cell, encounter.getMat_w_o_Tax_USD());
				}else if(i==31){
					//lab amount usd
					writeCellValue(cell, encounter.getCost_Labour_Amount_USD());
				}
			}
		}
	}
	private static void createSummaryOfLocations(List<Location> locations, XSSFSheet sheet, RowCount rowCount, int order ){
		int startRow = rowCount.getRowCount();
		for(Location location: locations){
			createLocationRow(location, sheet, rowCount, order);
			Set<Region> regions = location.getRegions();
			Iterator<Region> iter = regions.iterator();
			int startRowOfEachLocation = rowCount.getRowCount();
			while(iter.hasNext()){
				Region region = iter.next();
				createRegionHeaderRow(region, sheet, rowCount, order);
				summRegionsTree.put(region.getRegionId(), Integer.valueOf(rowCount.getRowCount() -1));
			}
			//create total summary row for each location
			createTotalSummaryRowForLocation(location, sheet,startRowOfEachLocation, rowCount, order);
		}
		String mainRegion = "Total " + Constants.ELECT_WORKS;
		createTotalWorks(mainRegion, sheet,startRow, rowCount, order);
		

	}
	private static void createTotalWorks(String mainRegion, XSSFSheet sheet,int startRow, RowCount rowCount, int order){
		rowCount.addMoreValue(1);
		Row row = sheet.getRow(rowCount.getRowCount());
		int endRow = rowCount.getRowCount() -1;
		rowCount.addMoreValue(2);
		Cell cell = row.createCell(1);
		cell.setCellValue(mainRegion);
		cell.setCellStyle(getSampleStyleForLocation(sheet.getWorkbook()));
		updateSubTotal(row, startRow, endRow);
		
	}
	private static void createBreakDownRow(XSSFSheet sheet, RowCount rowCount, int order){
		rowCount.addMoreValue(1);
		Row row = sheet.getRow(rowCount.getRowCount());
		rowCount.addMoreValue(1);
		Cell cell = row.createCell(1);
		cell.setCellValue(Constants.BREAK_DOWN);
		cell.setCellStyle(getSampleStyleForLocation(sheet.getWorkbook()));		
	}
	private static void updateSubTotal(Row row, int startRow, int endRow){
		updateAmountFormula(row, startRow, endRow);
		updateLabourAmountFormula(row, startRow, endRow);
		updateAEFormula(row, startRow, endRow);
		updateAFFormula(row, startRow, endRow);
	}
	private static void createTotalSummaryRowForLocation(Location location,XSSFSheet sheet,int startRow, RowCount rowCount, int order){
		rowCount.addMoreValue(1);
		int endRow = rowCount.getRowCount() -1;
		Row row = sheet.getRow(rowCount.getRowCount());
		rowCount.addMoreValue(2);
		Cell cell = row.createCell(1);
		cell.setCellValue(getTotalRowSumaryName(location));
		cell.setCellStyle(getSampleStyleForLocation(sheet.getWorkbook()));
		
		updateSubTotal(row, startRow, endRow);
	}
	private static String getTotalRowSumaryName(Location location){
		String result = "Total " + location.getLocationName() 
				+ " " 
				+ Constants.ELECT_WORKS ;
		return result;
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
			ReadExcelDemo excelReader = new ReadExcelDemo();
			RowCount rowCount = excelReader.new RowCount();
			
			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			Project project = projectService.findAll().get(0);
			updateCover(project, workbook, rowCount);
			updateCondition1(project, workbook);
			updateElecMaker(project, workbook, projectService);
			rowCount.setRowCount(6);
			createBoQSheet(project, workbook, rowCount);
			
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
