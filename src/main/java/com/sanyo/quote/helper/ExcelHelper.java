package com.sanyo.quote.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * class to export quotation report 
 */
public class ExcelHelper {
	
	public FileInputStream getExcelTemplateFile(String fileName) throws FileNotFoundException{
		FileInputStream file = new FileInputStream(new File(fileName));
		return file;
	}
	
	public XSSFWorkbook getWorkBook(FileInputStream file) throws IOException{
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		return workbook;
	}
	

}
