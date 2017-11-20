package com.techmaster.hunter.imports.extractors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.ReceiverRegionDaoImpl;
import com.techmaster.hunter.dao.types.ReceiverRegionDao;
import com.techmaster.hunter.imports.extraction.AbstractExcelExtractor;
import com.techmaster.hunter.imports.extraction.ExcelExtractor;
import com.techmaster.hunter.imports.extraction.ExcelExtractorUtil;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.ReceiverRegion;
import com.techmaster.hunter.util.HunterUtility;

public class RegionExtractor extends AbstractExcelExtractor<ReceiverRegion> {

	public static final String[] validHeaders = new String[]{"COUNTRY","STATE","COUNTY","CONSTITUENCY","CONSTITUENCY WARD"};
	private ReceiverRegionDao receiverRegionDao = new ReceiverRegionDaoImpl();
	
	private String[] surfaceErrors = null;
	private boolean surfaceValid = false;
	private boolean status = false;
	private Map<String, Object> bundle = new HashMap<>();
	private Workbook workbook;
	

	private static Logger logger = Logger.getLogger(RegionExtractor.class);
	
	public RegionExtractor(Workbook workbook) {
		super();
		this.workbook = workbook;
	}
	public String[] getSurfaceErrors() {
		return surfaceErrors;
	}
	public boolean isSurfaceValid() {
		return surfaceValid;
	}
	public boolean isStatus() {
		return status;
	}
	public Map<String, Object> getBundler() {
		return bundle;
	}
	public Workbook getWorkbook() {
		return workbook;
	}
	
	
	
	@Override
	public List<ReceiverRegion> getDataBeans(Map<Integer, List<Object>> data) {
		
		logger.debug("Extracting receiver region beans from data..."); 
		
		List<ReceiverRegion> receiverRegions = new ArrayList<>();
		
		for(Map.Entry<Integer, List<Object>> dataRows : data.entrySet()){
			
			List<Object> rowData = dataRows.getValue();
			ReceiverRegion region = new ReceiverRegion();
			
			for(int i=0; i<rowData.size(); i++){
				
				Object datum = rowData.get(i);
				
				//Country
				if(i==0){
					region.setCountry(datum != null ? datum.toString() : null);
				}
				
				// State
				if(i==1 && datum != null){
					region.setState(datum != null ? datum.toString() : null);
				}
				
				// County
				if(i==2 && datum != null){
					region.setCounty(datum != null ? datum.toString() : null); 
				}
				
				// Constituency
				if(i==3 && datum != null){
					region.setConstituency(datum != null ? datum.toString() : null); 
				}
				
				
				// Constituency Ward
				if(i==4 && datum != null){
					region.setWard(datum != null ? datum.toString() : null); 
				}
				
			}
			
			AuditInfo auditInfo = HunterUtility.getAuditInforForNow(new Date(), HunterConstants.HUNTER_ADMIN_USER_NAME, new Date(), HunterConstants.HUNTER_ADMIN_USER_NAME);
			region.setAuditInfo(auditInfo);
			receiverRegions.add(region);
			
		}
		
		logger.debug("Finished extracting receiver region beans! Size( " + receiverRegions.size() + " )" ); 
		logger.debug(HunterUtility.stringifyList(receiverRegions)); 
		
		
		return receiverRegions;		
	}
	@Override
	public Map<Integer, List<String>> validate(Map<Integer, List<Object>> data) {
		
		logger.debug("Validating receiver regions data extracted..." ); 
		Map<Integer, List<String>> errors = new HashMap<>();
		
		for(Map.Entry<Integer, List<Object>> dataRows : data.entrySet()){
			
			Integer dataRowNum = dataRows.getKey();
			List<Object> rowData = dataRows.getValue();
			List<String> rowErrors = new ArrayList<>();
			
			boolean isStatePresent = false;
			boolean isCountyPresent = false;
			boolean isConstituencyPresent = false;
			
			for(int i=0; i<rowData.size(); i++){
				
				Object obj = rowData.get(i);
				
				// Country
				if(i==0){
					if(obj == null || obj.toString() == ""){
						rowErrors.add("Country is required,");
					}else if(HunterUtility.isNumeric(obj)){ 
						rowErrors.add("Country cannot be a number,");
					}
				}
				
				// State
				if(i==1){
					// state is not mandatory so do nothing
					if(obj == null || obj.toString() == ""){
						isStatePresent = false;
					}else if(HunterUtility.isNumeric(obj)){ 
						rowErrors.add("Country cannot be a number,");
					}else{
						isStatePresent = true;
					}
				}
				
				
				//County
				if(i==2){
					if(obj == null || obj.toString() == ""){
						isCountyPresent = false;
					}else if(HunterUtility.isNumeric(obj)){ 
						rowErrors.add("County cannot be a number,");
					}else{
						isCountyPresent = true;
					}
				}
				
				// Constituency
				if(i==3){
					if(obj == null || obj.toString() == ""){
						// Constituency is optional so do nothing
						isConstituencyPresent = false;
					}else if(HunterUtility.isNumeric(obj)){ 
						rowErrors.add("Constituency cannot be a number,");
					}else{
						if(!isCountyPresent && !isStatePresent){
							rowErrors.add("Constituency cannot stand without either state or county");
						}
					}
				}
				
				
				// Constituency Ward
				if(i==4){
					if(obj == null || obj.toString() == ""){
						// ward is optional so do nothing
					}else if(HunterUtility.isNumeric(obj)){ 
						rowErrors.add("Constituency cannot be a number,");
					}else{
						if(!isConstituencyPresent){
							rowErrors.add("Constituency Ward cannot stand without constituency");
						}
					}
				}
				
				
				// we believe error message is not empty or null so remove the last comma
				if(!rowErrors.isEmpty() && i == rowErrors.size() - 1){
					String lastErrorMessage = rowErrors.get(rowErrors.size() - 1) + "";
					lastErrorMessage.substring(0, lastErrorMessage.length() - 1);
					 rowErrors.add(lastErrorMessage);
				}
				
			}
			
			if(!rowErrors.isEmpty()){
				errors.put(dataRowNum, rowErrors);
			}
		}
		
		
		if(errors.size() > 0)
			logger.debug("Validation errors found for extracted receiver regions!");
		
		return errors;
	}
	@Override
	public Map<String, Object> execute() {
		
		logger.info("Starting extraction execution process for RegionExtractor..."); 
		List<String> sheets = Arrays.asList(new String[]{ExcelExtractor.REGION_SHEET_NAME});
		List<String> sheetsMsgs = ExcelExtractorUtil.validateSheets(sheets, workbook);
		Map<String, Object> bundle = new HashMap<String, Object>();

		if(sheetsMsgs != null && sheetsMsgs.size() > 0){
			this.surfaceValid = false;
			for(String sheetMsg : sheetsMsgs){
				surfaceErrors = HunterUtility.initArrayAndInsert(surfaceErrors, sheetMsg);
			}
			bundle.put(ExcelExtractor.SURFACE_VALIDATION, false);
			bundle.put(ExcelExtractor.ERRORS_STR, surfaceErrors);
			this.bundle = bundle;
			this.status = false;
			return bundle;
		}
		
		List<ReceiverRegion> regions = null;
		int lastRowNum = ExcelExtractorUtil.getInstance().getLastRowNumber(ExcelExtractor.REGION_EXTRACTOR, workbook);
		String status = null;
		
		String[] headers = extractHeaders(ExcelExtractor.REGION_SHEET_NAME, workbook);
		String[] invalidHeaders = validateHeaders(headers, validHeaders);
		
		if(invalidHeaders != null && invalidHeaders.length > 0){
			this.surfaceValid = false;
			for(String header : invalidHeaders){
				surfaceErrors = HunterUtility.initArrayAndInsert(surfaceErrors, header + " : is not found");
			}
			bundle.put(ExcelExtractor.SURFACE_VALIDATION, false);
			bundle.put(ExcelExtractor.ERRORS_STR, surfaceErrors);
			this.bundle = bundle;
			this.status = false;
			return bundle;
		}
		
		Map<Integer, List<Object>> data = extractData(REGION_SHEET_NAME, this.workbook); 
		Map<Integer, List<String>> errors = validate(data);
		
		if(errors.isEmpty()){
			status = ExcelExtractor.STATUS_FAILED_STR;
			for(Map.Entry<Integer, List<String>> entry : errors.entrySet()){
				Integer rowNum = entry.getKey();
				List<String> rowErrors = entry.getValue();
				Object[] rowErrorsArray = rowErrors.toArray();
				/* create the error cells only if the errors are found for that row. */
				if(rowErrorsArray.length > 0){
					String [] stringArray = HunterUtility.convertToStringArray(rowErrorsArray);
	 				createErrorCell(REGION_SHEET_NAME, workbook, rowNum, stringArray); 
				}
			}
			
			bundle.put(RETURNED_WORKBOOK, workbook);
			this.bundle = bundle;
			this.status = false;
			
		}else{
			
			status = ExcelExtractor.STATUS_SUCCESS_STR;
			regions = getDataBeans(data);
			receiverRegionDao.insertReceicerRegions(regions); 
			bundle.put(DATA_BEANS, regions);
			bundle.put(RETURNED_WORKBOOK, workbook);
			bundle.put(STATUS_STR, true);
			
			this.bundle = bundle;
			this.status = true;
			this.surfaceErrors = null;
			
		}
		
		createStatuCell(ExcelExtractor.REGION_EXTRACTOR, workbook, lastRowNum, status);
		
		boolean isStatus = this.isStatus();
		boolean isSurfaceValid = this.isSurfaceValid();
		
		if(!isStatus){
			if(isSurfaceValid){
				logger.warn("Extraction failed surface validations!"); 
				logger.warn("SurfaceErrors >>> "  + Arrays.toString(this.getSurfaceErrors())); 
			}else{
				logger.warn("Region extraction passed surface validations but failed data validations!"); 
			}
		}else{
			logger.warn("Region extraction passed data validation!!");
		}

		logger.debug("Going to create RegionRangeBeans..."); 
		
		return bundle;
	}
	
	
	
	

	
	
	
}


















