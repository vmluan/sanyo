package com.sanyo.quote.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import com.sanyo.quote.domain.ProjectRevision;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.service.EncounterService;
import com.sanyo.quote.service.LocationService;
import com.sanyo.quote.service.MakerProjectService;
import com.sanyo.quote.service.ProjectRevisionService;
import com.sanyo.quote.service.ProjectService;

public class ReportExcel extends ExcelHelper{
	private class SummaryRegion{
		private Region region;
		private Integer rowNo;
		public Region getRegion() {
			return region;
		}
		public void setRegion(Region region) {
			this.region = region;
		}
		public Integer getRowNo() {
			return rowNo;
		}
		public void setRowNo(Integer rowNo) {
			this.rowNo = rowNo;
		}
		
	}
	private TreeMap<Integer, Integer> summRegionsTree = new TreeMap<Integer, Integer>();
	
	private ProjectService projectService;
	private LocationService locationService;
	private EncounterService encounterService;
	private MakerProjectService makerProjectService;
	private ProjectRevisionService projectRevisionService;
	private XSSFCellStyle sampleCellStyle;
	private boolean isClientVersion = true;
	private int maxBoQCol = 32;
	private static final int startBoQRow=6;
	private TreeMap<String, List<SummaryRegion>> elecSummaryTree = new TreeMap<String, List<SummaryRegion>>();
	private TreeMap<String, List<SummaryRegion>> mechSummaryTree = new TreeMap<String, List<SummaryRegion>>();
	
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
		Row rowProjectName = sheet.getRow(10);
		Cell cellProjectName = rowProjectName.getCell(3);
		cellProjectName.setCellValue(project.getProjectName());
		Row rowDate = sheet.getRow(0);
		Cell cellDate = rowDate.getCell(9);
//		cellDate.setCellValue(project.getCreatedDate());
		
		Cell cellRefNo = sheet.getRow(1).getCell(9);
		cellRefNo.setCellValue(project.getProjectCode());
		
		Cell cellPeriod = sheet.getRow(2).getCell(9);
		cellPeriod.setCellValue(project.getDuration());
		
		Cell cellClient = sheet.getRow(15).getCell(3);
		cellClient.setCellValue(project.getCustomerName());
		
		List<ProjectRevision> revisions = projectRevisionService.findRevisions(project);
		
		for(ProjectRevision revision : revisions){
			RowCount rowCount2 = new RowCount();
			rowCount2.setRowCount(18);
			Row row = sheet.createRow(rowCount2.getRowCount());
			Cell date = row.createCell(2);
			date.setCellValue("Date : ");
			
			Cell cellRvsDate = row.createCell(3);
			cellRvsDate.setCellValue(Utilities.formatDate(revision.getDate()));
			
			Cell cellRvsNo = row.createCell(6);
			cellRvsNo.setCellValue(revision.getRevisionNo());
		}
		
		Cell cellPeriodVald = sheet.getRow(25).getCell(6);
		cellPeriodVald.setCellValue("PERIOD OF VALIDITY :" + project.getDuration());
		
		
		
		//PERIOD OF VALIDITY : 1month 

	}
	private void updateCondition1(Project project,XSSFWorkbook workbook){
		XSSFSheet sheet = workbook.getSheetAt(1);
		
		Cell cellStartDate = sheet.getRow(10).getCell(2);
		cellStartDate.setCellValue(Utilities.formatDate(project.getStartDate()));
		
		Cell cellEndDate = sheet.getRow(11).getCell(2);
		cellEndDate.setCellValue(Utilities.formatDate(project.getEndDate()));
		
		Cell cellDuration = sheet.getRow(10).getCell(5);
		cellDuration.setCellValue("Minimum Requirement for duration is " + project.getDuration());
		//Minimum Requirement for duration is 10 months
		//updte payment condition
		// ■US$     □VND     □Japanese Yen
		Cell cellPayment = sheet.getRow(19).getCell(1);
		
		String textPayment = "□US$     ■VND     □Japanese Yen";
		if(project.getCurrency() != null){
			String currencyCode = project.getCurrency().getCurrencyCode();
			if(currencyCode.equalsIgnoreCase("USD")){
				textPayment = "■US$     □VND     □Japanese Yen";
			}else if(currencyCode.equalsIgnoreCase("JPY")){
				textPayment = "□US$     □VND     ■Japanese Yen";
			}
			
		}
		cellPayment.setCellValue(textPayment);
	}
	private void updateMaker(Project project,XSSFSheet sheet,ProjectService projectService, String parentCategoryName){
		List<MakerProject> makerProjects = makerProjectService.findByProject(project);
		Cell cell0 = sheet.getRow(0).getCell(0);
		cell0.setCellValue(project.getProjectName());
		
		int rowCount = 4;
		Row row1 = sheet.getRow(0);
		Cell cellClientName = row1.getCell(6);
		cellClientName.setCellValue(cellClientName.getStringCellValue() + project.getCustomerName());
		
		int stt =0;
		
		
		TreeMap<String, List<MakerProject>> list = new TreeMap<String, List<MakerProject>>();
		for(MakerProject pg : makerProjects){
			if(pg.getCategory().getParentCategory() != null
					&& pg.getCategory().getParentCategory().getName().equalsIgnoreCase(parentCategoryName)){
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
	private void createBOQCommon(Project project, XSSFSheet sheet, RowCount rowCount, String parentCategoryName){
		Cell cell0 = sheet.getRow(0).getCell(0);
		cell0.setCellValue(project.getProjectName());
		int order = 1;
		List<Location> locations = projectService.findLocations(project.getProjectId());
		
		createSummaryOfLocations(locations, sheet, rowCount, order,parentCategoryName);
		createBreakDownRow(sheet, rowCount, order);
		rowCount.addMoreValue(2);
	}
	private void createElecBoQSheet(Project project, XSSFSheet sheet, RowCount rowCount){
		summRegionsTree.clear();
		int order = 1;
		int startRowOfRegion=0;
		List<Location> locations = projectService.findLocations(project.getProjectId());
		
		createBOQCommon(project, sheet, rowCount, Constants.ELECT_BOQ);
		
		for(Location location: locations){
			//create location row
			createLocationRow(location, sheet, rowCount, order);
			List<Region> regions = locationService.findRegions(location.getLocationId());
			int numOfRegions = 0;
			List<SummaryRegion> summaryRegions = new ArrayList<ReportExcel.SummaryRegion>();
			for(Region region : regions){
				if(region.getCategory().getParentCategory() != null
						&& region.getCategory().getParentCategory().getName().equalsIgnoreCase(Constants.ELECT_BOQ)){
					numOfRegions ++;
					//create region row
					createRegionRow(region, sheet, rowCount, order);
	//				rowCount +=2;
					startRowOfRegion = rowCount.getRowCount();
					List<Encounter> encounters = encounterService.getEncountersByRegion(region);
					createBOQRows(encounters, sheet, rowCount, order);
					//create sub-total for each region.
					rowCount.addMoreValue(1);
					createSubTotal(startRowOfRegion, rowCount, numOfRegions, sheet, region.getRegionName());
					SummaryRegion summaryRegion = new SummaryRegion();
					summaryRegion.setRegion(region);
					summaryRegion.setRowNo(rowCount.getRowCount() -1);
					summaryRegions.add(summaryRegion);
					//update total summary for region.
					int regionSummRowNum = summRegionsTree.get(region.getRegionId());
					Row row = sheet.getRow(regionSummRowNum); // must getRow as we need to update the total value only.
					updateTotalSummaryForRegion(row,rowCount.getRowCount()-1);
				}
			}
			elecSummaryTree.put(location.getLocationName(), summaryRegions);
			//update total summary for location.
		}
		
	}
	private void createMechBoQSheet(Project project, XSSFSheet sheet, RowCount rowCount){
		summRegionsTree.clear();
		int order = 1;
		int startRowOfRegion=0;
		List<Location> locations = projectService.findLocations(project.getProjectId());
		
		createBOQCommon(project, sheet, rowCount,Constants.MECH_BOQ);
		
		for(Location location: locations){
			//create location row
			createLocationRow(location, sheet, rowCount, order);
			List<Region> regions = locationService.findRegions(location.getLocationId());
			int numOfRegions = 0;
			List<SummaryRegion> summaryRegions = new ArrayList<ReportExcel.SummaryRegion>();
			for(Region region : regions){
				if(region.getCategory().getParentCategory() != null
						&& region.getCategory().getParentCategory().getName().equalsIgnoreCase(Constants.MECH_BOQ)){
					numOfRegions ++;
					//create region row
					createRegionRow(region, sheet, rowCount, order);
	//				rowCount +=2;
					startRowOfRegion = rowCount.getRowCount();
					List<Encounter> encounters = encounterService.getEncountersByRegion(region);
					createBOQRows(encounters, sheet, rowCount, order);
					//create sub-total for each region.
					rowCount.addMoreValue(1);
					createSubTotal(startRowOfRegion, rowCount, numOfRegions, sheet, region.getRegionName());
					SummaryRegion summaryRegion = new SummaryRegion();
					summaryRegion.setRegion(region);
					summaryRegion.setRowNo(rowCount.getRowCount()-1);
					summaryRegions.add(summaryRegion);
					//update total summary for region.
					int regionSummRowNum = summRegionsTree.get(region.getRegionId());
					Row row = sheet.getRow(regionSummRowNum); // must getRow as we need to update the total value only.
					updateTotalSummaryForRegion(row,rowCount.getRowCount()-1);
				}
			}
			mechSummaryTree.put(location.getLocationName(), summaryRegions);
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
//		endRow.addMoreValue(1);
		Row row = sheet.createRow(endRow.getRowCount());
		Cell cell0 = row.createCell(0);
		cell0.setCellValue(order);
		Cell cell1 = row.createCell(1);
		writeCellValue(cell1, "Sub Total of " + regionName);
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
	private void createSummaryOfLocations(List<Location> locations, XSSFSheet sheet, RowCount rowCount, int order, String parentCategoryName ){
		int startRow = rowCount.getRowCount();
		String totalName = "Total ";
		if(parentCategoryName.equalsIgnoreCase(Constants.ELECT_BOQ))
			totalName += Constants.ELECT_WORKS;
		else if(parentCategoryName.equalsIgnoreCase(Constants.MECH_BOQ))
			totalName += Constants.MECH_WORKS;
		for(Location location: locations){
			createLocationRow(location, sheet, rowCount, order);
			Set<Region> regions = location.getRegions();
			Iterator<Region> iter = regions.iterator();
			int startRowOfEachLocation = rowCount.getRowCount();
			while(iter.hasNext()){
				Region region = iter.next();
				if(region.getCategory().getParentCategory() != null
						&& region.getCategory().getParentCategory().getName().equals(parentCategoryName)){
					createRegionHeaderRow(region, sheet, rowCount, order);
					summRegionsTree.put(region.getRegionId(), Integer.valueOf(rowCount.getRowCount() -1));
				}
			}
			//create total summary row for each location
			createTotalSummaryRowForLocation(location, sheet,startRowOfEachLocation, rowCount, order, totalName);
		}
		
		createTotalWorks(totalName, sheet,startRow, rowCount, order);
		

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
	private void createTotalSummaryRowForLocation(Location location,XSSFSheet sheet,int startRow, RowCount rowCount, int order, String parentCategoryName){
		rowCount.addMoreValue(1);
		int endRow = rowCount.getRowCount() -1;
		Row row = sheet.createRow(rowCount.getRowCount());
		rowCount.addMoreValue(2);
		Cell cell = row.createCell(1);
		cell.setCellValue(getTotalRowSumaryName(location, parentCategoryName));
		cell.setCellStyle(sampleCellStyle);
		
		updateSubTotal(row, startRow, endRow);
		updateCellStyleOfRowBoQ(row);
	}
	private String getTotalRowSumaryName(Location location, String parentCategoryName){
		String result = "Total " + location.getLocationName() 
				+ " " 
				+ parentCategoryName ;
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
			updateMaker(project, workbook.getSheetAt(6), projectService, Constants.ELECT_BOQ);
			rowCount.setRowCount(6);
			createElecBoQSheet(project, workbook.getSheetAt(5), rowCount);
			
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
			updateMaker(project, workbook.getSheetAt(6), projectService, Constants.ELECT_BOQ);
			rowCount.setRowCount(startBoQRow);
			createElecBoQSheet(project, workbook.getSheetAt(4), rowCount);
			
			RowCount mechCount = new RowCount();
			mechCount.setRowCount(startBoQRow);
			createMechBoQSheet(project, workbook.getSheetAt(5), mechCount);
			updateMaker(project, workbook.getSheetAt(7), projectService, Constants.MECH_BOQ);
			
			RowCount summaryCount = new RowCount();
			summaryCount.setRowCount(5);
			createSummarySheet(project, workbook.getSheetAt(3), summaryCount);
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
	private void createSummarySheet(Project project, XSSFSheet sheet, RowCount rowCount){
		//create Electrical Works row
		Row row1 = sheet.createRow(rowCount.getRowCount());
		rowCount.addMoreValue(1);
		Cell cell1 = row1.createCell(2);
		cell1.setCellValue(Constants.ELECT_WORKS);
		createSummarySheetCommon(sheet, elecSummaryTree, rowCount);
		
		rowCount.addMoreValue(1);
		Row row2 = sheet.createRow(rowCount.getRowCount());
		rowCount.addMoreValue(1);
		Cell cell2 = row2.createCell(2);
		cell2.setCellValue(Constants.MECH_WORKS);
		createSummarySheetCommon(sheet, mechSummaryTree, rowCount);
		rowCount.addMoreValue(1);
	}
	private void createSummarySheetCommon(XSSFSheet sheet, TreeMap<String, List<SummaryRegion>> tree, RowCount rowCount){
		Set<String> elecLocations = tree.keySet();
		Iterator<String> elecIter = elecLocations.iterator();
		while(elecIter.hasNext()){
			String locationName = elecIter.next();
			//create Location row
			Row row2 = sheet.createRow(rowCount.getRowCount());
			rowCount.addMoreValue(1);
			Cell cell2 = row2.createCell(2);
			cell2.setCellValue(locationName);
			List<SummaryRegion> summaryRegions = tree.get(locationName);
			int start=rowCount.getRowCount();
			for(SummaryRegion summaryRegion : summaryRegions){
				Row regionRow = sheet.getRow(rowCount.getRowCount());
				rowCount.addMoreValue(1);
				Cell regionNamCell = regionRow.createCell(2);
				regionNamCell.setCellValue(summaryRegion.getRegion().getRegionName());
				Cell regionTotalCell = regionRow.createCell(5);
				//='Electrical works'!H23
				String strFormula = "'Electrical works'!H" + summaryRegion.getRowNo();
				writeCellFomula(regionTotalCell, strFormula);
			}
			createSubTotalSummarySheet(start, rowCount, sheet, locationName);
		}
	}
	private void createSubTotalSummarySheet(int startRow, RowCount endRow, XSSFSheet sheet, String locationName){
		Row row = sheet.createRow(endRow.getRowCount());
		Cell cell1 = row.createCell(2);
		writeCellValue(cell1, "Sub Total of " + locationName);
		String strFomula = getSubTotalFormula(startRow, endRow.getRowCount(), "F");
		Cell cell5 = row.createCell(5); //amount
		writeCellFomula(cell5, strFomula);
		endRow.addMoreValue(2);
//		updateCellStyleSummarySheet(row);
	}
	private void updateCellStyleSummarySheet(Row row){
		for(int i=0; i< 7; i++){
			Cell cell = row.getCell(i);
			if(cell == null)
				cell = row.createCell(i);
			cell.setCellStyle(sampleCellStyle);
		}
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

	public ProjectRevisionService getProjectRevisionService() {
		return projectRevisionService;
	}

	public void setProjectRevisionService(ProjectRevisionService projectRevisionService) {
		this.projectRevisionService = projectRevisionService;
	}

	public boolean isClientVersion() {
		return isClientVersion;
	}

	public void setClientVersion(boolean isClientVersion) {
		this.isClientVersion = isClientVersion;
	}

	public int getMaxBoQCol() {
		return maxBoQCol;
	}

	public void setMaxBoQCol(int maxBoQCol) {
		this.maxBoQCol = maxBoQCol;
	}
	
}
