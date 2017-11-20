package com.techmaster.hunter.imports.extractors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.HunterMessageReceiverDao;
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
import com.techmaster.hunter.obj.beans.HunterMessageReceiver;
import com.techmaster.hunter.obj.beans.State;
import com.techmaster.hunter.region.RegionCache;
import com.techmaster.hunter.util.HunterUtility;

public class HunterMsgReceiverExtractor extends AbstractExcelExtractor<HunterMessageReceiver> {

	public static final String[] validHeaders = new String[]{"COUNTRY","STATE","COUNTY","CONSTITUENCY","WARD","CONTACT","RECEIVER TYPE","LEVEL","LEVEL NAME"};
	private static Logger logger = Logger.getLogger(HunterMsgReceiverExtractor.class);

	private static HunterMessageReceiverDao hunterMessageReceiverDao = HunterDaoFactory.getObject(HunterMessageReceiverDao.class);
	private static HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
	private static HunterImportBeanDao hunterImportBeanDao = HunterDaoFactory.getObject(HunterImportBeanDao.class);
	
	private static Map<Integer, List<Object>> rowLists;
	
	private String[] surfaceErrors = null;
	private boolean surfaceValid = false;
	private boolean status = false;
	private Map<String, Object> bundle = new HashMap<>();
	private Workbook workbook;
	private AuditInfo auditInfo;
	private String originalFileName;
	
	public HunterMsgReceiverExtractor(Workbook workbook, AuditInfo auditInfo, String originalFileName) {
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

	private static void loadContactTypePairs(){
		if(rowLists == null || rowLists.isEmpty()){
			logger.debug("rowList is empty, loading data..."); 
			String query = hunterJDBCExecutor.getQueryForSqlId("getAllReceiverTypesAndContacts");
			logger.debug("Using query >> " + query);
			rowLists = hunterJDBCExecutor.executeQueryRowList(query, null);
		}else{
			logger.debug("rowList has data, doing nothing...");
		}
	}
	
	private static boolean isContactExistent(String inContact, String inType ){
		for(Map.Entry<Integer, List<Object>> entry : rowLists.entrySet()){
			List<Object> cEntry = entry.getValue();
			String contact = cEntry.get(0).toString();
			String type = cEntry.get(1).toString();
			if(inContact.equalsIgnoreCase(contact) && inType.equalsIgnoreCase(type)){
				return true;
			}
		}
		return false;
	}
	
	private static void addFromHunterMsgReceivers(List<HunterMessageReceiver> hunterMessageReceivers){
		logger.debug("Now updating contact type cache..."); 
		for(HunterMessageReceiver hntrMsgRcvr : hunterMessageReceivers){
			String contact = hntrMsgRcvr.getReceiverContact();
			String type = hntrMsgRcvr.getReceiverType();
			addContactsToRowList(contact, type); 
		}
		logger.debug("Successfully updated contact type cache for all hunter message receivers"); 
	}
	
	private static void addContactsToRowList(String contact, String type){
		
		if(!isContactExistent(contact, type)){
			
			Set<Integer> keyset = rowLists.keySet();
			List<Integer> inKeyset = new ArrayList<>();
			inKeyset.addAll(keyset);
			
			Collections.sort(inKeyset, new Comparator<Integer>() {
			    public int compare(Integer o1, Integer o2) {
			        return (o1 > o2 ? -1 : (o1 == o2 ? 0 : 1));
			    }

			});
			Integer topKey = inKeyset.get(inKeyset.size() - 1);
			topKey++;
			
			List<Object> newPair = new ArrayList<>();
			newPair.add(contact);
			newPair.add(type);
			
			rowLists.put(topKey, newPair);
			
		}else{
			logger.debug("Contact ( " + contact + " ) and Type ( "+ type +" ) exist already and will not be added" ); 
		}
	}
	

	@Override
	public Map<Integer, List<String>> validate(Map<Integer, List<Object>> data) {
		
		logger.debug("Validating hunter message receiver data extracted..." ); 
		Map<Integer, List<String>> errors = new HashMap<>();
		List<Country> countries = RegionCache.getInstance().getCountries();
		List<String> localDuplicateCheck = new ArrayList<>();
		
		loadContactTypePairs();
		
		for(Map.Entry<Integer, List<Object>> dataRows : data.entrySet()){
			
			Integer dataRowNum = dataRows.getKey();
			List<Object> rowData = dataRows.getValue();
			List<String> rowErrors = new ArrayList<>();
			
			Country country = null;
			State state = null;
			County county = null;
			Constituency constituency = null;
			ConstituencyWard constituencyWard = null;
			String level = rowData.get(rowData.size()-1) != null ? rowData.get(rowData.size()-1).toString() : null;
			
			@SuppressWarnings("unused")
			String levelName = rowData.get(rowData.size()-2)!= null ? rowData.get(rowData.size()-2).toString() : null;
			String receiverType = rowData.get(rowData.size()-3)!= null ? rowData.get(rowData.size()-3).toString() : null;
			
			List<String> types = Arrays.asList(new String[]{
					HunterConstants.RECEIVER_TYPE_CALL,
					HunterConstants.RECEIVER_TYPE_EMAIL,
					HunterConstants.RECEIVER_TYPE_TEXT,
					HunterConstants.RECEIVER_TYPE_VOICE_MAIL
				});
			
			List<String> levels = Arrays.asList(new String[]{
					HunterConstants.RECEIVER_LEVEL_COUNTRY,
					HunterConstants.RECEIVER_LEVEL_COUNTY,
					HunterConstants.RECEIVER_LEVEL_CONSITUENCY,
					HunterConstants.RECEIVER_LEVEL_WARD,
					HunterConstants.RECEIVER_LEVEL_STATE,
					HunterConstants.RECEIVER_LEVEL_VILLAGE,
					HunterConstants.RECEIVER_LEVEL_CITY
				});
			
			for(int i=0; i<rowData.size(); i++){
				
				String objStr = rowData.get(i) != null ? rowData.get(i)+"" : null;
				
				// country
				if(i==0){
				
					if(objStr == null || objStr.equals("") ){
						rowErrors.add("Country cannot be blank");
					}else{
						boolean isFound = false;
						for(Country cntry : countries){
							if(cntry.getCountryName().equals(objStr)){
								isFound = true;
								country = cntry;
								break;
							}
						}
						if(!isFound){
							rowErrors.add("Coutry does not exist");
						}
					}
					
				// State
				}else if(i==1){
					
					if(objStr != null && !objStr.equals("")){
						
						if(!country.isHasState()){
							rowErrors.add("Country does not have state, state should be blank.");
						} else {
							Set<State> states = country.getStates();
							boolean isFound = false;
							if(states.size() > 0){
								for(State stts : states){
									if(stts.getStateName().equalsIgnoreCase(objStr)){
										isFound = true;
										state = stts;
										break;
									}
								}
							}
							if(!isFound){
								rowData.add("State provided does not exist or doesn't belong to the country given.");
							}
						}
						
					}else{
						if(country.isHasState()){
							rowErrors.add("Country has states, state cannot be blank.");
						}
					}
				
				// County
				}else if(i==2){
					
					if(objStr == null || objStr.equals("")){
						if(!country.isHasState()){
							rowErrors.add("Country has no state and so county cannot be blank");
						}
						if(level.equalsIgnoreCase(HunterConstants.RECEIVER_LEVEL_COUNTY)){
							rowErrors.add("Level is county so county cannot be blank");
						}
					}else{
						
						Set<County> counties = country.getCounties();
						boolean isFound = false;
						
						for(County cnty : counties){
							if(cnty.getCountyName().equalsIgnoreCase(objStr)){
								isFound = true;
								county = cnty;
								break;
							}
						}
						
						if(!isFound){
							rowErrors.add("County given does not exist");
						}
						
					}
					
				// constituency
				}else if(i==3){
				
					if((objStr == null || objStr.equalsIgnoreCase("")) && level.equalsIgnoreCase(HunterConstants.RECEIVER_LEVEL_CONSITUENCY)){  
						rowErrors.add("Level is Constituency and so Constituency cannot be blank");
					}else if(objStr != null  && !objStr.equalsIgnoreCase("")){
						
						// make sure that the constituency belongs to the state.
						if(!country.isHasState()){
							
							Set<Constituency> constituencies = county.getConstituencies();
							boolean isFound = false;
							
							if(!constituencies.isEmpty()){
								for(Constituency conscy : constituencies){
									if(conscy.getCnsttncyName().equalsIgnoreCase(objStr)){
										constituency = conscy;
										isFound = true;
										break;
									}
								}
							}
							
							if(!isFound){
								rowErrors.add("Constituency cannot be blank");
							}
						}
						
					}
				
				// ward
				}else if(i==4){
					
					if((objStr == null || objStr.trim().equalsIgnoreCase("")) && level.equalsIgnoreCase(HunterConstants.RECEIVER_LEVEL_WARD)  ){
						if(level.equals(HunterConstants.RECEIVER_LEVEL_WARD)){
							rowErrors.add("Receiver level is Ward and so ward cannot be blank");
						}
					}else if(objStr != null && !objStr.trim().equalsIgnoreCase("")){
						
						// make sure that the ward belongs to the constituency.
						Set<ConstituencyWard> constituencyWards = constituency.getConstituencyWards();
						boolean isFound = false;
						
						for(ConstituencyWard consWard : constituencyWards){
							if(consWard.getWardName().equalsIgnoreCase(objStr)){
								constituencyWard = consWard;
								isFound = true;
								break;
							}
						}
						
						if(!isFound){
							rowErrors.add("Ward not valid or does not belong to the constituency given");
						}
					}
					
				//CONTACT
				}else if(i==5){
					
					if(objStr == null || objStr.trim().equalsIgnoreCase("")){
						rowErrors.add("Contact cannot be blank");
					}else{
						
						String objStrHold = objStr;
						
						if( HunterUtility.notNullNotEmpty( receiverType ) && HunterConstants.RECEIVER_TYPE_TEXT.equals( receiverType ) ){
							
							if(objStr.startsWith("\\+")){
								objStrHold = objStrHold.substring(1,objStrHold.length());
							}
							if(objStrHold.contains("\\-")){
								objStrHold = objStrHold.replaceAll("\\-", "");
							}
							if(objStrHold.contains("\\")){
								objStrHold = objStrHold.replaceAll("\\", "");
							}
							if(objStrHold.contains("/")){
								objStrHold = objStrHold.replaceAll("/", "");
							}
							if(objStrHold.contains(".")){
								objStrHold = objStrHold.replaceAll(".", "");
							}
							if(objStrHold.contains("\n")){
								objStrHold = objStrHold.replaceAll("\n", "");
							}
							if(objStrHold.contains(" ")){
								objStrHold = objStrHold.replaceAll(" ", "");
							}
							if(objStrHold.contains("x") && receiverType.equalsIgnoreCase(HunterConstants.RECEIVER_TYPE_TEXT)){
								objStrHold = objStrHold.replaceAll("x", "");
							}
							if(objStrHold.contains("X") && receiverType.equalsIgnoreCase(HunterConstants.RECEIVER_TYPE_TEXT)){
								objStrHold = objStrHold.replaceAll("X", "");
							}
							
							int conLen = objStrHold.length();
							
							if(conLen < 4 || conLen > 15){
								rowErrors.add("Invalid phone number");
							}
							
							
						}else if( HunterUtility.notNullNotEmpty( receiverType ) && HunterConstants.RECEIVER_TYPE_EMAIL.equals( receiverType ) ){
							
							boolean isValidEmail = HunterUtility.validateEmail( objStrHold );
							if( !isValidEmail ){
								rowErrors.add("Invalid email!");
							}
							
						}
						
						
						if(localDuplicateCheck.contains(objStrHold)){
							rowErrors.add("Contact duplicated in the sheet");
						}else{
							localDuplicateCheck.add(objStrHold);
						}
					}
					
					if(isContactExistent(objStr, receiverType)){
						rowErrors.add("Contact already exists in the system");
					}
					
				//RECEIVER TYPE
				}else if(i==6){
					
					if(objStr == null){
						rowErrors.add("Receiver type cannot be blank");
					}else{
						
						boolean isFound = false;
						
						for(String type : types){
							if(type.equalsIgnoreCase(objStr)){
								isFound = true;
								receiverType = type;
								break;
							}
						}
						
						if(!isFound){
							rowErrors.add("Receiver type is invalid");
						}
						
					}
					
				//LEVEL
				}else if(i==7){
					
					if(objStr == null){
						rowErrors.add("Level cannot be blank");
					} else {
						boolean isFound = false;
						for(String lvl : levels){
							if(lvl.equalsIgnoreCase(objStr)){
								level = lvl;
								isFound = true;
								break;
							}
						}
						if(!isFound){
							rowErrors.add("Level provided is invalid");
						}
					}
					
				//LEVEL NAME
				}else if(i==8){
					
					if(objStr == null || objStr.equalsIgnoreCase("")){
						// this can be null so no validation error when it's blank.
					}else{
						if( level.equalsIgnoreCase(HunterConstants.RECEIVER_LEVEL_COUNTRY) && !objStr.equalsIgnoreCase(country.getCountryName()) ){
							rowErrors.add("Level is country but level name is not country name");
						}else if( level.equalsIgnoreCase(HunterConstants.RECEIVER_LEVEL_COUNTY) && !objStr.equalsIgnoreCase(county.getCountyName()) ){
							rowErrors.add("Level is county but level name is not county name");
						}else if( level.equalsIgnoreCase(HunterConstants.RECEIVER_LEVEL_CONSITUENCY) && !objStr.equalsIgnoreCase(constituency.getCnsttncyName()) ){
							rowErrors.add("Level is constituency but level name is not constituency name");
						}else if( level.equalsIgnoreCase(HunterConstants.RECEIVER_LEVEL_STATE) && !objStr.equalsIgnoreCase(state.getStateName()) ){
							rowErrors.add("Level is state but level name is not state name");
						}else if( level.equalsIgnoreCase(HunterConstants.RECEIVER_LEVEL_CITY) ){
							rowErrors.add("Hunter does not handle city region levels yet");
						}else if( level.equalsIgnoreCase(HunterConstants.RECEIVER_LEVEL_WARD) && !objStr.equalsIgnoreCase(constituencyWard.getWardName()) ){
							rowErrors.add("Level is ward but level name is not ward name");
						}else if( level.equalsIgnoreCase(HunterConstants.RECEIVER_LEVEL_VILLAGE) ){
							rowErrors.add("Hunter does not handle village region levels yet");
						}
					}
					
				}
				
			}
			
			if(!rowErrors.isEmpty()){
				errors.put(dataRowNum, rowErrors);
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
	public List<HunterMessageReceiver> getDataBeans(Map<Integer, List<Object>> data) {
		
		logger.debug("Starting bean construction process for Hunter Message Receiver extractor..."); 
		List<HunterMessageReceiver> hntrMsgRcvrs = new ArrayList<>();
		
		for(Map.Entry<Integer, List<Object>> dataRows : data.entrySet()){
			
			List<Object> dataRow = dataRows.getValue();
			HunterMessageReceiver hntrMsgRcvr = new HunterMessageReceiver();
			
			for(int i=0; i<dataRow.size(); i++){
				
				Object obj = dataRow.get(i);
				String objStr = obj == null ? null : obj.toString();
				
				// Country
				if(i==0){
					hntrMsgRcvr.setCountryName(objStr);
				
				// State
				}else if(i==1){
					hntrMsgRcvr.setStateName(objStr);
				
				// County
				}else if(i==2){
					hntrMsgRcvr.setCountyName(objStr); 
					
				//Constituency
				}else if(i==3){
					hntrMsgRcvr.setConsName(objStr);
				
				//Constituency ward
				}else if(i==4){
					hntrMsgRcvr.setConsWardName(objStr);;
					
				//Contact
				}else if(i==5){
					hntrMsgRcvr.setReceiverContact(objStr); 
					
				// Receiver type
				}else if(i==6){
					hntrMsgRcvr.setReceiverType(objStr);
					
				//Level
				}else if(i==7){
					hntrMsgRcvr.setReceiverRegionLevel(objStr);
					
				//Level Name
				}else if(i==8){
					hntrMsgRcvr.setReceiverRegionLevelName(objStr); 
					
				}
				
			}
			
			hntrMsgRcvr.setBlocked(false);
			hntrMsgRcvr.setActive(true); 
			hntrMsgRcvr.setAuditInfo(auditInfo);
			hntrMsgRcvrs.add(hntrMsgRcvr);
			
		}
		logger.debug("Successfully finished constructing data beans from data extracted! Size ( " + hntrMsgRcvrs.size() + " )");
		return hntrMsgRcvrs;
	}

	@Override
	public Map<String, Object> execute() {
		
		logger.info("Starting extraction execution process for HunterMessageReceiverExtractor..."); 
		Map<String, Object> bundle = new HashMap<String, Object>();
		List<String> sheets = Arrays.asList(new String[]{ExcelExtractor.HUNTER_MSG_EXTRACTOR});
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
		
		List<HunterMessageReceiver> hntrMsgRcvrs = null;
		int lastRowNum = ExcelExtractorUtil.getInstance().getLastRowNumber(ExcelExtractor.HUNTER_MSG_EXTRACTOR, workbook);
		String status = null;
		
		String[] headers = extractHeaders(ExcelExtractor.HUNTER_MSG_EXTRACTOR, workbook);
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
		
		Map<Integer, List<Object>> data = extractData(HUNTER_MSG_EXTRACTOR, this.workbook); 
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
	 				createErrorCell(HUNTER_MSG_EXTRACTOR, workbook, rowNum, stringArray); 
				}
			}
			
			bundle.put(RETURNED_WORKBOOK, workbook);
			this.bundle = bundle;
			this.status = false;
			
		}else{
			
			status = ExcelExtractor.STATUS_SUCCESS_STR;
			hntrMsgRcvrs = getDataBeans(data);
			logger.debug(HunterUtility.stringifyList(hntrMsgRcvrs));  
			logger.debug("Saving extracted hunter message receiver beans..."); 
			hunterMessageReceiverDao.insertHunterMessageReceivers(hntrMsgRcvrs);
			addFromHunterMsgReceivers(hntrMsgRcvrs);
			new LoadCacheWorker().start();
			bundle.put(DATA_BEANS, hntrMsgRcvrs);
			bundle.put(RETURNED_WORKBOOK, workbook);
			bundle.put(STATUS_STR, true);
			
			this.bundle = bundle;
			this.status = true;
			this.surfaceErrors = null;
			
		}
		
		createStatuCell(ExcelExtractor.HUNTER_MSG_EXTRACTOR, workbook, lastRowNum, status);
		
		logger.debug("Saving excel file to db.."); 
		String stsStr = this.isStatus() ? HunterConstants.STATUS_SUCCESS : HunterConstants.STATUS_FAILED;
		HunterImportBean hunterImportBean = ImportHelper.createHntrImprtBnFrmWrkbk(workbook, auditInfo, originalFileName, HunterMessageReceiver.class.getSimpleName(),stsStr);
		hunterImportBeanDao.insertHunterImportBeanUsngJDBC(hunterImportBean); 
		
		return bundle;
	}
	
	class LoadCacheWorker extends Thread{
		@Override
		public void run() {
			logger.debug("Loading receivers..."); 
			HunterCacheUtil.getInstance().loadReceivers();
			logger.debug("Done loading receivers!!");
		}
	}

}
