package com.sanyo.quote.helper;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * class to export quotation report 
 */
public class ExcelHelper {
	
	public  XSSFCellStyle getSampleStyleWithBorder(XSSFWorkbook workbook){
		
		XSSFCellStyle cellStyle =  workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.DASH_DOT_DOT);
		cellStyle.setBorderTop(BorderStyle.DASH_DOT_DOT);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		return cellStyle;
		
	}public  XSSFCellStyle getSampleStyleWithBorder(Cell cell){
		
		XSSFCellStyle cellStyle = (XSSFCellStyle) cell.getCellStyle();
		cellStyle.setBorderBottom(BorderStyle.DASH_DOT_DOT);
		cellStyle.setBorderTop(BorderStyle.DASH_DOT_DOT);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		return cellStyle;
		
	}
	public  XSSFCellStyle getSampleStyleWithBorderDash(XSSFWorkbook workbook){
		
		XSSFCellStyle cellStyle =  workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.DASHED);
		cellStyle.setBorderTop(BorderStyle.DASHED);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		return cellStyle;
		
	}	
	public  XSSFCellStyle getSampleStyleNoBorder(XSSFWorkbook workbook){
		
		XSSFCellStyle cellStyle =  workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.NONE);
		cellStyle.setBorderTop(BorderStyle.NONE);
		cellStyle.setBorderLeft(BorderStyle.NONE);
		cellStyle.setBorderRight(BorderStyle.NONE);
		return cellStyle;
		
	}
	public  XSSFCellStyle getSampleStyleForLocation(XSSFWorkbook workbook){
		
		XSSFCellStyle cellStyle =  getSampleStyleWithBorder(workbook);
		XSSFFont font = workbook.createFont();
//		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Arial");
		font.setBold(true);
		cellStyle.setFont(font);
		return cellStyle;
		
	}
	public  XSSFCellStyle getSampleStyleForSubTotal(XSSFWorkbook workbook){
		
		XSSFCellStyle cellStyle =  getSampleStyleWithBorder(workbook);
		XSSFFont font = workbook.createFont();
//		font.setBold(true);
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Arial");
		font.setBold(true);
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(113, 214, 245)));
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return cellStyle;
		
	}
	public  XSSFCellStyle getSampleStyleForTotalWork(XSSFWorkbook workbook){
		
		XSSFCellStyle cellStyle =  getSampleStyleWithBorder(workbook);
		XSSFFont font = workbook.createFont();
//		font.setBold(true);
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Arial");
		font.setBold(true);
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(113, 214, 245)));
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return cellStyle;
		
	}
	public  XSSFCellStyle getSampleStyleForBreakDown(XSSFWorkbook workbook){
		
		XSSFCellStyle cellStyle =  getSampleStyleWithBorder(workbook);
		XSSFFont font = workbook.createFont();
//		font.setBold(true);
		font.setFontHeightInPoints((short) 14);
		font.setFontName("Arial");
		font.setBold(true);
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(103, 114, 205)));
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return cellStyle;
		
	}		
	public  XSSFCellStyle getSampleStyleForRegion(XSSFWorkbook workbook){
		
		XSSFCellStyle cellStyle =  getSampleStyleWithBorder(workbook);
		XSSFFont font = workbook.createFont();
//		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Arial");
		cellStyle.setFont(font);
		return cellStyle;
		
	}
	public void writeCellValue(Cell cell, String text){
		if(text != null){
			cell.setCellValue(text);
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
	}
	public void writeCellValue(Cell cell, Float text){
		XSSFWorkbook workbook = (XSSFWorkbook) cell.getRow().getSheet().getWorkbook();
		XSSFCellStyle cellStyle = getSampleStyleWithBorder(workbook);
		setDataFormatForFloat(cellStyle, workbook);
		if(text !=null){
			cell.setCellValue(text);
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellStyle(cellStyle);
		}
	}
	public static void writeCellFomula(Cell cell, String strFormula){
		cell.setCellType(Cell.CELL_TYPE_FORMULA);
		cell.setCellFormula(strFormula);
	}
	public static void writeCellValue(Cell cell, int text){
		cell.setCellValue(text);
	}
	public void setDataFormatForFloat(XSSFCellStyle cellStyle, XSSFWorkbook workbook){
		XSSFDataFormat xssfDataFormat = workbook.createDataFormat(); 
		cellStyle.setDataFormat(xssfDataFormat.getFormat("#,##0.000"));
	}
	

}
