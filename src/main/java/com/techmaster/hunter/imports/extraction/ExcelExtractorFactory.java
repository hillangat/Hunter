package com.techmaster.hunter.imports.extraction;

import org.apache.poi.ss.usermodel.Workbook;

import com.techmaster.hunter.imports.extractors.RegionExtractor;



public class ExcelExtractorFactory {
	
	private static ExcelExtractorFactory  excelExtractorFactory = null;
	
	static{
		if(excelExtractorFactory == null){
			synchronized (ExcelExtractorFactory.class) {
				excelExtractorFactory = new ExcelExtractorFactory();
			}
		}
	}
	
	public static ExcelExtractorFactory getIntance(){
		return excelExtractorFactory;
	}
	
	// private constructor
	private ExcelExtractorFactory(){
		super();
	}
	
	public static ExcelExtractor getExtractor(String jndi, Workbook workbook){
		ExcelExtractor extractor = null;
		if(jndi != null && jndi.equalsIgnoreCase(ExcelExtractor.REGION_EXTRACTOR)){
			extractor =  new RegionExtractor(workbook);
		}
		return extractor;
	}
	
	

}
