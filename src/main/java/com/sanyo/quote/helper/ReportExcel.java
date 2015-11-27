package com.sanyo.quote.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sanyo.quote.domain.Encounter;
import com.sanyo.quote.domain.Location;
import com.sanyo.quote.domain.MakerProject;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.service.EncounterService;
import com.sanyo.quote.service.LocationService;
import com.sanyo.quote.service.MakerProjectService;
import com.sanyo.quote.service.ProjectService;

public class ReportExcel extends ExcelHelper{
	private TreeMap<Integer, Integer> summRegionsTree = new TreeMap<Integer, Integer>();
	
	private ProjectService projectService;
	private LocationService locationService;
	private EncounterService encounterService;
	private MakerProjectService makerProjectService;
	private XSSFCellStyle sampleCellStyle;
	private boolean isClientVersion = true;
	private int maxBoQCol = 32;
	
	public ReportExcel(){
		if(isClientVersion)
			this.maxBoQCol = 9;
	}
	
	public FileInputStream getFile(String fileName){
		FileInputStream file;
		try {
			file = new FileInputStream(new File(fileName));
			return file;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public XSSFWorkbook getWorkBook(FileInputStream file){
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			return workbook;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	private void updateCover(Project project,XSSFWorkbook workbook, RowCount rowCount){
		XSSFSheet sheet = workbook.getSheetAt(0);

		//Iterate through each rows one by one
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) 
		{
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
//			
//			while (cellIterator.hasNext()) 
//			{
//				Cell cell = cellIterator.next();
//				if(cell.getStringCellValue().equalsIgnoreCase("${projectName}")){
//					cell.setCellValue(project.getProjectName());
//				}
//				else if(cell.getStringCellValue().equalsIgnoreCase("${clientName}")){
//					cell.setCellValue(project.getCustomerName());
//				}
//				else if(cell.getStringCellValue().equalsIgnoreCase("${projectCode}")){
//					cell.setCellValue(project.getProjectCode());
//				}
//				else if(cell.getStringCellValue().equalsIgnoreCase("${reportDate}")){
//					cell.setCellValue(new Date());
//				}
//				else if(cell.getStringCellValue().equalsIgnoreCase("${duration}")){
//					cell.setCellValue(project.getDuration().toString());
//				}
//			}
		}
	}
	private void updateCondition1(Project project,XSSFWorkbook workbook){
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
	private void updateElecMaker(Project project,XSSFSheet sheet,ProjectService projectService){
		List<MakerProject> makerProjects = makerProjectService.findByProject(project);
		int rowCount = 4;
		Row row1 = sheet.getRow(0);
		Cell cellClientName = row1.getCell(5);
		cellClientName.setCellValue(cellClientName.getStringCellValue() + project.getCustomerName());
		
		int stt =0;
		
		
		TreeMap<String, List<MakerProject>> list = new TreeMap<String, List<MakerProject>>();
		for(MakerProject pg : makerProjects){
			List<MakerProject> existingValues = list.get(pg.getCategory().getName());
			if(existingValues != null){
				existingValues.add(pg);
			}
			else{
				existingValues = new ArrayList<MakerProject>();
				existingValues.add(pg);
				list.put(pg.getCategory().getName(), existingValues);
			}
		}
		
		for(Map.Entry<String,List<MakerProject>> entry : list.entrySet()) {
			  String key = entry.getKey();
			  List<MakerProject> childList = entry.getValue();
			  createMakerRows(childList, sheet, rowCount, stt);
			  stt += 1;
			  rowCount += childList.size();
			  
			  
			}
		
		
	}
	
	private void createMakerRows(List<MakerProject> productGroupMakers, XSSFSheet sheet, int rowCount, int order ){
		boolean hasOrderForCategory = false;
		int startRow = rowCount;
		for(MakerProject pg : productGroupMakers){
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
					// set product group
					cell.setCellValue(pg.getProductGroupMaker().getProductGroup().getGroupName());
				}
				else if(i ==3){
					//set model no
					cell.setCellValue(pg.getModelNo());
				}
				else if(i ==4){
					//set maker name
					cell.setCellValue(pg.getProductGroupMaker().getMaker().getName());
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
	private void createBoQSheet(Project project, XSSFSheet sheet, RowCount rowCount){
		int order = 1;
		int startRowOfRegion=0;
		List<Location> locations = projectService.findLocations(project.getProjectId());
		
		createSummaryOfLocations(locations, sheet, rowCount, order);
		createBreakDownRow(sheet, rowCount, order);
		rowCount.addMoreValue(2);
		
		for(Location location: locations){
			//create location row
			createLocationRow(location, sheet, rowCount, order);
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
				//create sub-total for each region.
				createSubTotal(startRowOfRegion, rowCount, numOfRegions, sheet, region.getRegionName());
				
				//update total summary for region.
				int regionSummRowNum = summRegionsTree.get(region.getRegionId());
				Row row = sheet.getRow(regionSummRowNum); // must getRow as we need to update the total value only.
				updateTotalSummaryForRegion(row,rowCount.getRowCount()-1);
			}
			//update total summary for location.
		}
		
	}
	private void updateTotalSummaryForRegion(Row row, int rowNum){
		updateTotalRegionAmountFormula(row, rowNum);
		if(!this.isClientVersion){
			updateTotalRegionLabourAmountFormula(row, rowNum);
			updateTotalRegionAEFormula(row, rowNum);
			updateTotalRegionAFFormula(row, rowNum);
		}
	}
	public String getTotalRegionFormula(int row, String column){
		String strFormula = column + row;;
		return strFormula;
	}
	private void updateTotalRegionAmountFormula(Row row, int rowNum){
		String strFomula = getTotalRegionFormula(rowNum, "H");
		Cell cell7 = row.createCell(Constants.BOQ_COLUMN_AMOUNT); //amount
		writeCellFomula(cell7, strFomula);
		cell7.setCellStyle(sampleCellStyle);
	}
	private void updateTotalRegionLabourAmountFormula(Row row, int rowNum){
		String strFomula = getTotalRegionFormula(rowNum, "M");
		Cell cell7 = row.createCell(Constants.BOQ_COLUMN_LABOUR_AMOUNT); //amount
		writeCellFomula(cell7, strFomula);
		cell7.setCellStyle(sampleCellStyle);
	}
	private void updateTotalRegionAEFormula(Row row, int rowNum){
		String strFomula = getTotalRegionFormula(rowNum, "AE");
		Cell cell7 = row.createCell(Constants.BOQ_COLUMN_Mat_w_o_Tax_USD); //amount
		writeCellFomula(cell7, strFomula);
		cell7.setCellStyle(sampleCellStyle);
	}
	private void updateTotalRegionAFFormula(Row row, int rowNum){
		String strFomula = getTotalRegionFormula(rowNum, "AF");
		Cell cell7 = row.createCell(Constants.BOQ_COLUMN_Cost_Labour_Amount_USD); //amount
		writeCellFomula(cell7, strFomula);
		cell7.setCellStyle(sampleCellStyle);
	}
	public String getSubTotalFormula(int start, int end, String column){
		String strFormula = "SUBTOTAL(9," + column + start + ":" + column + end + ")";
		return strFormula;
	}
	private void createSubTotal(int startRow, RowCount endRow, int order, XSSFSheet sheet, String regionName){
		endRow.addMoreValue(1);
		Row row = sheet.createRow(endRow.getRowCount());
		Cell cell0 = row.createCell(0);
		cell0.setCellValue(order);
		Cell cell1 = row.createCell(1);
		writeCellValue(cell1, "Sub-total of " + regionName);
		updateSubTotal(row, startRow, endRow.getRowCount());
		
		endRow.addMoreValue(2);
		updateCellStyleOfRowBoQ(row);
	}
	//update H column value
	private void updateAmountFormula(Row row, int startRow, int endRow){
		String strFomula = getSubTotalFormula(startRow, endRow, "H");
		Cell cell7 = row.createCell(Constants.BOQ_COLUMN_AMOUNT); //amount
		writeCellFomula(cell7, strFomula);
	}
	//update M column value
	private void updateLabourAmountFormula(Row row, int startRow, int endRow){
		String strFomula = getSubTotalFormula(startRow, endRow, "M");
		Cell cell7 = row.createCell(Constants.BOQ_COLUMN_LABOUR_AMOUNT); //amount
		writeCellFomula(cell7, strFomula);
	}
	private void updateAEFormula(Row row, int startRow, int endRow){
		String strFomula = getSubTotalFormula(startRow, endRow, "AE");
		Cell cell7 = row.createCell(Constants.BOQ_COLUMN_Mat_w_o_Tax_USD); //amount
		writeCellFomula(cell7, strFomula);
	}
	private void updateAFFormula(Row row, int startRow, int endRow){
		String strFomula = getSubTotalFormula(startRow, endRow, "AF");
		Cell cell7 = row.createCell(Constants.BOQ_COLUMN_Cost_Labour_Amount_USD); //amount
		writeCellFomula(cell7, strFomula);
	}
	
	private void createLocationRow(Location location, XSSFSheet sheet, RowCount rowCount, int order ){
		
		Row row = sheet.createRow(rowCount.getRowCount());
		rowCount.addMoreValue(1);
		Cell cell = row.createCell(1);
		cell.setCellValue(location.getLocationName());
		cell.setCellStyle(sampleCellStyle);
		
		updateCellStyleOfRowBoQ(row);
	}
	private void updateCellStyleOfRowBoQ(Row row){
		for(int i=0; i< this.maxBoQCol; i++){
			Cell cell = row.getCell(i);
			if(cell == null)
				cell = row.createCell(i);
			cell.setCellStyle(sampleCellStyle);
		}
	}
private void createRegionHeaderRow(Region region, XSSFSheet sheet, RowCount rowCount, int order ){
		
		Row row = sheet.createRow(rowCount.getRowCount());
		rowCount.addMoreValue(1);
		Cell cell = row.createCell(1);
		cell.setCellValue(region.getRegionName());
		cell.setCellStyle(sampleCellStyle);
		updateCellStyleOfRowBoQ(row);
	}
	private void createRegionRow(Region region, XSSFSheet sheet, RowCount rowCount, int order ){
		Row row = sheet.createRow(rowCount.getRowCount());
		rowCount.addMoreValue(1);;
		Cell cell = row.createCell(1);
		cell.setCellValue(region.getRegionName());
		cell.setCellStyle(getSampleStyleForRegion(sheet.getWorkbook()));
		sheet.createRow(rowCount.getRowCount());
		updateCellStyleOfRowBoQ(row);
	}
	private void createBOQRows(List<Encounter> encounters, XSSFSheet sheet, RowCount rowCount, int order ){
		boolean hasOrderForCategory = false;
		int startRow = rowCount.getRowCount();
		for(Encounter encounter : encounters){
			Row row = sheet.createRow(rowCount.getRowCount());
			rowCount.addMoreValue(1);
			for(int i=0; i< this.maxBoQCol; i++){
				Cell cell = row.createCell(i);
				cell.setCellStyle(getSampleStyleWithBorder(sheet.getWorkbook()));
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
	private void createSummaryOfLocations(List<Location> locations, XSSFSheet sheet, RowCount rowCount, int order ){
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
	private void createTotalWorks(String mainRegion, XSSFSheet sheet,int startRow, RowCount rowCount, int order){
		rowCount.addMoreValue(1);
		Row row = sheet.createRow(rowCount.getRowCount());
		int endRow = rowCount.getRowCount() -1;
		rowCount.addMoreValue(2);
		Cell cell = row.createCell(1);
		cell.setCellValue(mainRegion);
		cell.setCellStyle(sampleCellStyle);
		updateSubTotal(row, startRow, endRow);
		updateCellStyleOfRowBoQ(row);
		
	}
	private void createBreakDownRow(XSSFSheet sheet, RowCount rowCount, int order){
		rowCount.addMoreValue(1);
		Row row = sheet.createRow(rowCount.getRowCount());
		rowCount.addMoreValue(1);
		Cell cell = row.createCell(1);
		cell.setCellValue(Constants.BREAK_DOWN);
		cell.setCellStyle(sampleCellStyle);	
		updateCellStyleOfRowBoQ(row);
	}
	private void updateSubTotal(Row row, int startRow, int endRow){
		updateAmountFormula(row, startRow, endRow);
		if(!isClientVersion){
			updateLabourAmountFormula(row, startRow, endRow);
			updateAEFormula(row, startRow, endRow);
			updateAFFormula(row, startRow, endRow);
		}
	}
	private void createTotalSummaryRowForLocation(Location location,XSSFSheet sheet,int startRow, RowCount rowCount, int order){
		rowCount.addMoreValue(1);
		int endRow = rowCount.getRowCount() -1;
		Row row = sheet.createRow(rowCount.getRowCount());
		rowCount.addMoreValue(2);
		Cell cell = row.createCell(1);
		cell.setCellValue(getTotalRowSumaryName(location));
		cell.setCellStyle(sampleCellStyle);
		
		updateSubTotal(row, startRow, endRow);
		updateCellStyleOfRowBoQ(row);
	}
	private String getTotalRowSumaryName(Location location){
		String result = "Total " + location.getLocationName() 
				+ " " 
				+ Constants.ELECT_WORKS ;
		return result;
	}

	public XSSFWorkbook writeExcelReportForProject(Project project, String reportTemplate){
		RowCount rowCount = new RowCount();
		FileInputStream file = getFile(reportTemplate);
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(file);
			sampleCellStyle = getSampleStyleWithBorder(workbook);
			updateCover(project, workbook, rowCount);
			updateCondition1(project, workbook);
			updateElecMaker(project, workbook.getSheetAt(6), projectService);
			rowCount.setRowCount(6);
			createBoQSheet(project, workbook.getSheetAt(5), rowCount);
			
			file.close();
			
			String outFileName = project.getProjectName() + ".xlsx";
			FileOutputStream out = new FileOutputStream(new File(outFileName));
			workbook.write(out);
			return workbook;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public XSSFWorkbook writeExcelReportClientForProject(Project project, String reportTemplate){
		RowCount rowCount = new RowCount();
		FileInputStream file = getFile(reportTemplate);
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(file);
			sampleCellStyle = getSampleStyleWithBorder(workbook);
			updateCover(project, workbook, rowCount);
			updateCondition1(project, workbook);
			updateElecMaker(project, workbook.getSheetAt(6), projectService);
			rowCount.setRowCount(6);
			createBoQSheet(project, workbook.getSheetAt(4), rowCount);
			
			file.close();
			
			String outFileName = project.getProjectName() + ".xlsx";
			FileOutputStream out = new FileOutputStream(new File(outFileName));
			workbook.write(out);
			return workbook;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public ProjectService getProjectService() {
		return projectService;
	}
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	public LocationService getLocationService() {
		return locationService;
	}
	public void setLocationService(LocationService locationService) {
		this.locationService = locationService;
	}
	public EncounterService getEncounterService() {
		return encounterService;
	}
	public void setEncounterService(EncounterService encounterService) {
		this.encounterService = encounterService;
	}
	public MakerProjectService getMakerProjectService() {
		return makerProjectService;
	}
	public void setMakerProjectService(MakerProjectService makerProjectService) {
		this.makerProjectService = makerProjectService;
	}
	
}
