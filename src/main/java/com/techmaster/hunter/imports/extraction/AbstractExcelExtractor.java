package com.techmaster.hunter.imports.extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.techmaster.hunter.util.HunterUtility;

public abstract class AbstractExcelExtractor<E> implements ExcelExtractor {
	
	@Override
	public String[] extractHeaders(String sheetName,Workbook workbook) {
		String[] headers = ExcelExtractorUtil.getInstance().extractHeaders(sheetName, workbook);
		return headers;
	}

	@Override
	public String[] validateHeaders(String[] inputHeaders, String[] validHeaders) {
		String[] errorHeaders = ExcelExtractorUtil.getInstance().validateHeaders(inputHeaders, validHeaders);
		return errorHeaders;
	}
	

	@Override
	public Map<Integer, List<Object>> extractData(String sheetName, Workbook workbook) {
		
		if(sheetName == null || sheetName.trim().equalsIgnoreCase(""))
			throw new IllegalArgumentException("Sheet name provided is not valid. Name >> " + sheetName);
		
		Sheet sheet = workbook.getSheet(sheetName); 
		if(sheet == null) throw new IllegalArgumentException("No sheet found >> " + sheetName);
		
		Map<Integer, List<Object>> rowDataMap = new HashMap<Integer, List<Object>>();
		
		int lastRowNum = sheet.getLastRowNum();
		
		for(int i=1; i<=lastRowNum; i++){
			Row row = sheet.getRow(i);
			List<Object> rowData = new ArrayList<Object>();
			int lastCellNum = row.getLastCellNum();
			for(int j=0; j< lastCellNum ; j++){
				Cell cell = row.getCell(j);
				Object value = getCellValue(cell);	
				rowData.add(value);
			}
			rowDataMap.put(i, rowData);
		}
		
		return rowDataMap;
	}

	@Override
	public void createErrorCell(String sheetName, Workbook workbook, Integer rowNum, String[] errors) {
		
		if(sheetName == null || sheetName.trim().equalsIgnoreCase(""))
			throw new IllegalArgumentException("Sheet name provided is not valid. Name >> " + sheetName);
		Sheet sheet = workbook.getSheet(sheetName); 
		if(sheet == null) throw new IllegalArgumentException("No sheet found >> " + sheetName);
		
		String[] headers = extractHeaders(sheetName,workbook);
		boolean errorHeadFound = false;
		
		for(String header : headers){
			if(header.equals(ExcelExtractor.ERRORS_STR)){
				errorHeadFound = true;
				break;
			}
		}
		
		int errorCellNum = 0;
		int firstRow = sheet.getFirstRowNum();
		Row headerRow = sheet.getRow(firstRow);
		
		if(!errorHeadFound){
			errorCellNum = headerRow.getLastCellNum();
			Cell cell = headerRow.createCell(errorCellNum);
			cell.setCellValue(ExcelExtractor.ERRORS_STR);
		}else{
			Iterator<Cell> headerItr = headerRow.cellIterator();
			while(headerItr.hasNext()){
				Cell cell = headerItr.next();
				String name = String.valueOf(getCellValue(cell));
				if(name != null && !name.trim().equals("") && name.equals(ExcelExtractor.ERRORS_STR)){
					errorCellNum = cell.getColumnIndex();
				}
			}
		}
		
		Row dataRow = sheet.getRow(rowNum);
		Cell errorCell = dataRow.createCell(errorCellNum);
		String errorsStr = HunterUtility.getCommaDelimitedStrings(errors);
		errorCell.setCellValue(errorsStr); 
		
	}

	@Override
	public void createStatuCell(String sheetName, Workbook workbook, Integer rowNum, String status) {
		if(sheetName == null || sheetName.trim().equalsIgnoreCase(""))
			throw new IllegalArgumentException("Sheet name provided is not valid. Name >> " + sheetName);
		Sheet sheet = workbook.getSheet(sheetName); 
		if(sheet == null) throw new IllegalArgumentException("No sheet found >> " + sheetName);
		
		String[] headers = extractHeaders(sheetName,workbook);
		boolean statusHeaderFound = false;
		
		for(String header : headers){
			if(header.equals(ExcelExtractor.STATUS_STR)){
				statusHeaderFound = true;
				break;
			}
		}
		
		int errorCellNum = 0;
		int firstRow = sheet.getFirstRowNum();
		Row headerRow = sheet.getRow(firstRow);
		
		if(!statusHeaderFound){
			errorCellNum = headerRow.getLastCellNum();
			Cell cell = headerRow.createCell(errorCellNum);
			cell.setCellValue(ExcelExtractor.STATUS_STR);
		}else{
			Iterator<Cell> headerItr = headerRow.cellIterator();
			while(headerItr.hasNext()){
				Cell cell = headerItr.next();
				String name = String.valueOf(getCellValue(cell));
				if(name != null && !name.trim().equals("") && name.equals(ExcelExtractor.STATUS_STR)){
					errorCellNum = cell.getColumnIndex();
				}
			}
		}
		
		Row lastRow = sheet.getRow(1);
		Cell errorCell = lastRow.createCell(errorCellNum);
		
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(new HSSFColor.RED().getIndex());
		cellStyle.setFillBackgroundColor(new HSSFColor.YELLOW().getIndex());
		errorCell.setCellStyle(cellStyle); 
		
		errorCell.setCellValue(status);
		
		int statusRow = extractHeaders(sheetName, workbook).length - 1;
		CellRangeAddress cRngAddrs = new CellRangeAddress(1, rowNum, statusRow, statusRow);
		sheet.addMergedRegion(cRngAddrs);
	}

	@Override
	public Object getCellValue(Cell cell) {
		Cell hcell = (Cell)cell;
		Object obj = null;
		if(cell != null){
			int type = hcell.getCellType();
			if(type== HSSFCell.CELL_TYPE_STRING){
				obj = hcell.getStringCellValue();
			}else if(type== HSSFCell.CELL_TYPE_NUMERIC){
				obj = hcell.getNumericCellValue();
			}else if(type== HSSFCell.CELL_TYPE_BOOLEAN){
				obj = hcell.getBooleanCellValue();
			}else if(type== HSSFCell.CELL_TYPE_ERROR){
				obj = hcell.getErrorCellValue();
			}else if(type== HSSFCell.CELL_TYPE_BLANK){
				obj = "";
			}
		}
		return obj;
	}


}
