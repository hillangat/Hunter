package com.techmaster.hunter.imports.extractors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterImportBeanDao;
import com.techmaster.hunter.dao.types.HunterRawReceiverDao;
import com.techmaster.hunter.imports.beans.HunterImportBean;
import com.techmaster.hunter.imports.beans.ImportHelper;
import com.techmaster.hunter.imports.extraction.AbstractExcelExtractor;
import com.techmaster.hunter.imports.extraction.ExcelExtractor;
import com.techmaster.hunter.imports.extraction.ExcelExtractorUtil;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.Constituency;
import com.techmaster.hunter.obj.beans.ConstituencyWard;
import com.techmaster.hunter.obj.beans.Country;
import com.techmaster.hunter.obj.beans.County;
import com.techmaster.hunter.obj.beans.HunterRawReceiver;
import com.techmaster.hunter.rawreceivers.RawReceiverService;
import com.techmaster.hunter.region.RegionCache;
import com.techmaster.hunter.util.HunterUtility;

public class RawReceiverExtractor extends AbstractExcelExtractor<HunterRawReceiver>{
	
	public final String[] validHeaders = new String[]{"TYPE","CONTACT","FIRST NAME","LAST NAME","COUNTRY","COUNTY","CONSTITUENCY","WARD"};
	private Logger logger = Logger.getLogger(HunterMsgReceiverExtractor.class);
	private HunterRawReceiverDao hunterRawReceiverDao = HunterDaoFactory.getObject(HunterRawReceiverDao.class);
	private HunterImportBeanDao hunterImportBeanDao = HunterDaoFactory.getObject(HunterImportBeanDao.class);
	private RawReceiverService rawReceiverService = HunterDaoFactory.getObject(RawReceiverService.class);
	

	private String[] surfaceErrors = null;
	private boolean surfaceValid = false;
	private boolean status = false;
	private Map<String, Object> bundle = new HashMap<>();
	private Workbook workbook;
	private AuditInfo auditInfo;
	private String originalFileName;
	

	public RawReceiverExtractor(Workbook workbook, AuditInfo auditInfo, String originalFileName) {
		super();
		this.workbook = workbook;
		this.auditInfo = auditInfo;
		this.originalFileName = originalFileName;
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
	public Workbook getWorkbook() {
		return workbook;
	}
	public Map<String, Object> getBundle() {
		return bundle;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	
	@Override
	public Map<Integer, List<String>> validate(Map<Integer, List<Object>> data) {
		
		logger.debug("Validating hunter raw receiver data extracted..." ); 
		Map<Integer, List<String>> errors = new HashMap<>();
		List<Country> countries = RegionCache.getInstance().getCountries();
		List<String> contacts = rawReceiverService.getDistinctContactsForUser(auditInfo.getLastUpdatedBy());
		
		for(Map.Entry<Integer, List<Object>> dataRows : data.entrySet()){
			
			Integer dataRowNum = dataRows.getKey();
			List<Object> rowData = dataRows.getValue();
			List<String> rowErrors = new ArrayList<>();
			
			String contactType = null;
			String countryName = null;
			String countyName = null;
			String consName = null;
			
			
			for(int i=0; i<rowData.size(); i++){
				
				String objStr = rowData.get(i) != null ? rowData.get(i)+"" : null;
				
				// CONTACT TYPE
				if(i==0){
					
					if(objStr == null || objStr.equals("") ){
						rowErrors.add("Must be either 'EMAIL' or 'PHONE'");
					}else{
						if(!HunterConstants.CONTACT_TYPE_PHONE.equalsIgnoreCase(objStr) && !HunterConstants.CONTACT_TYPE_EMAIL.equalsIgnoreCase(objStr)){ 
							rowErrors.add("Must be either 'EMAIL' or 'PHONE'");
						}
					}
					
					contactType = objStr;
					
				// CONTACT
				} else if(i==1){
					
					if(objStr == null || objStr.equals("") ){
						rowErrors.add("Contact is required");
					}else{
						if(HunterConstants.CONTACT_TYPE_PHONE.equalsIgnoreCase(contactType)){
							if(objStr != null && objStr.contains("E") || objStr.contains("e")){
							    objStr = HunterUtility.getFlatNumFromExponetNumber(objStr);
							}
							boolean isValidPhone = HunterUtility.validatePhoneNumber(objStr);
							if(!isValidPhone){
								rowErrors.add("Invalid phone number");
							}
						}else if( HunterConstants.CONTACT_TYPE_EMAIL.equalsIgnoreCase(contactType) ) {
							boolean isValidEmail = HunterUtility.validateEmail(objStr);
							if(!isValidEmail){
								rowErrors.add("Invalid email");
							}
									
						}
						
					}
					
					if(contacts != null && !contacts.isEmpty()){
						for(String contact : contacts){	
							if(contact.equalsIgnoreCase(objStr)){
								rowErrors.add("Contact already submitted");
							}
						}
					}
					
				// FIRST NAME
				}else if(i==2){
				
					if(objStr == null || objStr.equals("") ){
						rowErrors.add("First name is required");
					}else{
						if(objStr.trim().length() == 0 || objStr.trim().length() > 50){
							rowErrors.add("First name cannot exceed 50 characters and cannot be empty");
						}
					}
					
				// LAST NAME
				}else if(i==3){
					
					if(objStr != null  && objStr.trim().length() > 50 ){
						if(objStr.trim().length() == 0 || objStr.trim().length() > 50){
							rowErrors.add("Last name cannot exceed 50 characters");
						}
					}
						
				
				// COUNTRY
				}else if(i==4){
					
					if(objStr == null || objStr.equals("")){
						rowErrors.add("Country is required");
					}else{						
						boolean isFound = false;
						for(Country country : countries){
							if(objStr.equals(country.getCountryName())){
								isFound = true;
								countryName = country.getCountryName();
								break;
							}
						}
						if(!isFound){
							rowErrors.add("Country given does not exist");
						}
					}
					
				// COUNTY
				}else if(i==5){
				
					if(objStr == null || objStr.equals("")){
						rowErrors.add("County is required");
					}else if( HunterUtility.notNullNotEmpty(objStr) && HunterUtility.notNullNotEmpty(countryName)  ) {						
						Set<County> counties = HunterCacheUtil.getInstance().getCountiesBeans(countryName);
						boolean isFound = false;
						for(County county : counties){
							if(county.getCountyName().equals(objStr)){
								isFound = true;
								countyName = county.getCountyName();
								break;
							}
						}
						if(!isFound){
							rowErrors.add("County does not exist in country( " + countryName + " )"); 
						}
					}
				
				// CONSTITUENCY
				}else if(i==6){
					
					if(objStr == null || objStr.equals("")){
						rowErrors.add("Constituency is required");
					}else if( HunterUtility.notNullNotEmpty(objStr) && HunterUtility.notNullNotEmpty(countryName)  ) {						
						Set<Constituency> constituencies = HunterCacheUtil.getInstance().getConsBeans(countryName, countyName);
						boolean isFound = false;
						for(Constituency constituency : constituencies){
							if(constituency.getCnsttncyName().equals(objStr)){
								isFound = true;
								consName = constituency.getCnsttncyName();
								break;
							}
						}
						if(!isFound){
							rowErrors.add("Constituency does not exist in county( " + countyName + " )"); 
						}
					}
					
					
				//WARD
				}else if(i==7){
					
					if( HunterUtility.notNullNotEmpty(objStr) && HunterUtility.notNullNotEmpty(consName) && HunterUtility.notNullNotEmpty(countyName) ) {						
						Set<ConstituencyWard> wards = HunterCacheUtil.getInstance().getConsWardBeans(countryName, countyName, consName);
						boolean isFound = false;
						for(ConstituencyWard ward : wards){
							if(ward.getWardName().equals(objStr)){
								isFound = true;
								consName = ward.getWardName();
								break;
							}
						}
						if(!isFound){
							rowErrors.add("Ward does not exist in constituency( " + consName + " )"); 
						}
					}
				
			}
			
				if(!rowErrors.isEmpty()){
					errors.put(dataRowNum, rowErrors);
				}
			}
		}
		
		logger.debug("Successfully finished validating Hunter Message Receiver data!");
		
		if(!errors.isEmpty()){
			logger.debug("Data did not pass validation. Number of errors( " + errors.size() + " )");  
		}else{
			logger.debug("Data passed validation!!");
		}
		return errors;
	}

	@Override
	public List<HunterRawReceiver> getDataBeans(Map<Integer, List<Object>> data) {
		logger.debug("Extracting data beans for hunter raw receivers. Number of rows ( "+ data.size() +" )"); 
		List<HunterRawReceiver> rawReceivers = new ArrayList<>();
		for(Map.Entry<Integer, List<Object>> entry : data.entrySet()){
			HunterRawReceiver rawReceiver = new HunterRawReceiver();
			List<Object> rowData = entry.getValue();
			rawReceiver.setAuditInfo(auditInfo); 
			rawReceiver.setGivenByUserName(auditInfo.getLastUpdatedBy());
			rawReceiver.setVerified(false); 
			rawReceiver.setReceiverVersion(0); 
			for(int i=0; i<rowData.size(); i++){
				String objStr = HunterUtility.getStringOrNullOfObj(rowData.get(i));
				//TYPE
				if( i == 0 ){
					rawReceiver.setReceiverType(objStr);
				//CONTACT
				}else if( i == 1 ){
					String phone = HunterUtility.getFlatNumFromExponetNumber(objStr);
					rawReceiver.setReceiverContact(phone); 
				
				//FIRST NAME
				}else if( i == 2 ){
					rawReceiver.setFirstName(objStr);
				
				//LAST NAME
				}else if( i == 3 ){
					rawReceiver.setLastName(objStr);
				
			    //COUNTRY
				}else if( i == 4 ){
					rawReceiver.setCountryName(objStr); 
			
			    //COUNTY
				}else if( i == 5 ){
					rawReceiver.setCountyName(objStr); 
				
				//CONSTITUENCY
				}else if( i == 6 ){
					rawReceiver.setConsName(objStr); 
				
				//WARD		
				}else if( i == 7 ){
					rawReceiver.setConsWardName(objStr); 
				}
			}
			rawReceivers.add(rawReceiver);
		}
		logger.debug("Finished extracting beans. Number of beans : " + rawReceivers.size()); 
		logger.debug(HunterUtility.stringifyList(rawReceivers));  
		return rawReceivers;
	}

	@Override
	public Map<String, Object> execute() {
		
		logger.info("Starting extraction execution process for Hunter Raw Receivers..."); 
		Map<String, Object> bundle = new HashMap<String, Object>();
		List<String> sheets = Arrays.asList(new String[]{ExcelExtractor.RAW_RECEIVERS_SHEET_NAME});
		List<String> sheetsMsgs = ExcelExtractorUtil.validateSheets(sheets, workbook);
		
		if(sheetsMsgs != null && sheetsMsgs.size() > 0){
			this.surfaceValid = false;
			for(String sheetMsg : sheetsMsgs){
				surfaceErrors = HunterUtility.initArrayAndInsert(surfaceErrors, sheetMsg);
			}
			bundle.put(ExcelExtractor.SURFACE_VALIDATION, false);
			bundle.put(ExcelExtractor.ERRORS_STR, surfaceErrors);
			this.bundle = bundle;
			this.status = false;
			logger.debug("Bad sheet! Errors > " + Arrays.toString(surfaceErrors));  
			return bundle;
		}
		

		List<HunterRawReceiver> hunterRawReceivers = null;
		int lastRowNum = ExcelExtractorUtil.getInstance().getLastRowNumber(ExcelExtractor.RAW_RECEIVERS_SHEET_NAME, workbook);
		
		// No data found
		if(lastRowNum == 0){
			this.surfaceValid = false;
			surfaceErrors = HunterUtility.initArrayAndInsert(surfaceErrors, "No data found in the uploaded file");
			bundle.put(ExcelExtractor.SURFACE_VALIDATION, false);
			bundle.put(ExcelExtractor.ERRORS_STR, surfaceErrors);
			this.bundle = bundle;
			this.status = false;
			logger.debug("Do data found > " + Arrays.toString(surfaceErrors));  
			return bundle;
		}
		
		String status = null;
		
		String[] headers = extractHeaders(ExcelExtractor.RAW_RECEIVERS_SHEET_NAME, workbook);
		String[] invalidHeaders = validateHeaders(headers, validHeaders);
		
		if(invalidHeaders != null && invalidHeaders.length > 0){
			this.surfaceValid = true;
			for(String header : invalidHeaders){
				surfaceErrors = HunterUtility.initArrayAndInsert(surfaceErrors, header + " : is not found");
			}
			bundle.put(ExcelExtractor.SURFACE_VALIDATION, false);
			bundle.put(ExcelExtractor.ERRORS_STR, surfaceErrors);
			this.bundle = bundle;
			this.status = false;
			logger.debug("Bad sheet! Errors > " + Arrays.toString(surfaceErrors));
			return bundle;
		}
		
		
		Map<Integer, List<Object>> data = extractData(RAW_RECEIVERS_SHEET_NAME, this.workbook); 
		Map<Integer, List<String>> errors = validate(data);
		
		
		if(!errors.isEmpty()){
			status = ExcelExtractor.STATUS_FAILED_STR;
			for(Map.Entry<Integer, List<String>> entry : errors.entrySet()){
				Integer rowNum = entry.getKey();
				List<String> rowErrors = entry.getValue();
				Object[] rowErrorsArray = rowErrors.toArray();
				/* create the error cells only if the errors are found for that row. */
				if(rowErrorsArray.length > 0){
					String [] stringArray = HunterUtility.convertToStringArray(rowErrorsArray);
	 				createErrorCell(RAW_RECEIVERS_SHEET_NAME, workbook, rowNum, stringArray); 
				}
			}
			
			bundle.put(RETURNED_WORKBOOK, workbook);
			this.bundle = bundle;
			this.status = false;
			
		}else{
			
			status = ExcelExtractor.STATUS_SUCCESS_STR;
			hunterRawReceivers = getDataBeans(data);
			logger.debug(HunterUtility.stringifyList(hunterRawReceivers));  
			logger.debug("Saving extracted hunter message receiver beans..."); 
			hunterRawReceiverDao.insertHunterRawReceivers(hunterRawReceivers); 
			rawReceiverService.updateRawReceiverCountsForUser(auditInfo.getLastUpdatedBy()); 
			bundle.put(DATA_BEANS, hunterRawReceivers);
			bundle.put(RETURNED_WORKBOOK, workbook);
			bundle.put(STATUS_STR, true);
			
			this.bundle = bundle;
			this.status = true;
			this.surfaceErrors = null;
			
		}
		
		createStatuCell(ExcelExtractor.RAW_RECEIVERS_SHEET_NAME, workbook, lastRowNum, status);
		
		logger.debug("Saving excel file to db.."); 
		String stsStr = this.isStatus() ? HunterConstants.STATUS_SUCCESS : HunterConstants.STATUS_FAILED;
		HunterImportBean hunterImportBean = ImportHelper.createHntrImprtBnFrmWrkbk(workbook, auditInfo, originalFileName, HunterRawReceiver.class.getSimpleName(),stsStr);
		hunterImportBeanDao.insertHunterImportBeanUsngJDBC(hunterImportBean); 
		
		return bundle;
		
	}

}
