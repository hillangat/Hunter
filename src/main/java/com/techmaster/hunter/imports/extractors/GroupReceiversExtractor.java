package com.techmaster.hunter.imports.extractors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.types.HunterImportBeanDao;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.ReceiverGroupDao;
import com.techmaster.hunter.dao.types.ReceiverGroupReceiverDao;
import com.techmaster.hunter.imports.beans.HunterImportBean;
import com.techmaster.hunter.imports.beans.ImportHelper;
import com.techmaster.hunter.imports.extraction.AbstractExcelExtractor;
import com.techmaster.hunter.imports.extraction.ExcelExtractor;
import com.techmaster.hunter.imports.extraction.ExcelExtractorUtil;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.ReceiverGroup;
import com.techmaster.hunter.obj.beans.ReceiverGroupReceiver;
import com.techmaster.hunter.util.HunterUtility;

public class GroupReceiversExtractor extends AbstractExcelExtractor<ReceiverGroupReceiver>{
	
	private static String[] validHeaders = new String[]{"CONTACT","RECEIVER TYPE","ACTIVE", "APPROVED"};
	private static Logger logger = Logger.getLogger(GroupReceiversExtractor.class);
	private static HunterJDBCExecutor hunterJDBCExecutor;
	private static HunterImportBeanDao hunterImportBeanDao;
	private static ReceiverGroupDao receiverGroupDao;
	private static ReceiverGroupReceiverDao receiverGroupReceiverDao;
	
	
	private String[] surfaceErrors = null;
	private boolean surfaceValid = false;
	private boolean status = false;
	private Map<String, Object> bundle = new HashMap<>();
	private Workbook workbook;
	private AuditInfo auditInfo;
	private String originalFileName;
	private HunterImportBean hunterImportBean;
	private Long groupId;
	
	
	public GroupReceiversExtractor(Workbook workbook, AuditInfo auditInfo, String originalFileName, Long groupId) {
		super();
		this.workbook = workbook;
		this.auditInfo = auditInfo;
		this.originalFileName = originalFileName;
		this.groupId = groupId;
	}
	
	public static void injectStatisDAOs(HunterJDBCExecutor hunterJDBCExecutor, HunterImportBeanDao hunterImportBeanDao, ReceiverGroupDao receiverGroupDao, ReceiverGroupReceiverDao receiverGroupReceiverDao){
		logger.debug("Injecting static beans!!"); 
		GroupReceiversExtractor.hunterJDBCExecutor = hunterJDBCExecutor;
		GroupReceiversExtractor.hunterImportBeanDao = hunterImportBeanDao;
		GroupReceiversExtractor.receiverGroupDao = receiverGroupDao;
		GroupReceiversExtractor.receiverGroupReceiverDao = receiverGroupReceiverDao;
		logger.debug("Successfully injected static beans!!"); 
	}

	public String[] getSurfaceErrors() {
		return surfaceErrors;
	}
	public void setSurfaceErrors(String[] surfaceErrors) {
		this.surfaceErrors = surfaceErrors;
	}
	public boolean isSurfaceValid() {
		return surfaceValid;
	}
	public void setSurfaceValid(boolean surfaceValid) {
		this.surfaceValid = surfaceValid;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Map<String, Object> getBundle() {
		return bundle;
	}
	public void setBundle(Map<String, Object> bundle) {
		this.bundle = bundle;
	}
	public Workbook getWorkbook() {
		return workbook;
	}
	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public HunterImportBean getHunterImportBean() {
		return hunterImportBean;
	}
	public void setHunterImportBean(HunterImportBean hunterImportBean) {
		this.hunterImportBean = hunterImportBean;
	}

	@Override
	public Map<Integer, List<String>> validate(Map<Integer, List<Object>> data) {
		
		logger.debug("Validating hunter message receiver data extracted..." );
		
		String query = "SELECT RCVR_TYP FROM RCVR_GRP g WHERE g.GRP_ID = ?";
		List<Object> values = new ArrayList<>();
		values.add(this.groupId);
		Map<Integer, List<Object>> rowListMap = hunterJDBCExecutor.executeQueryRowList(query, values);
		List<Object> rowList = rowListMap.get(1);
		String groupType = rowList != null && !rowList.isEmpty() ? rowList.get(0).toString() : null;
		logger.debug("Group type obtained : " + groupType); 
		
		Map<Integer, List<String>> errors = new HashMap<>();
		for(Map.Entry<Integer, List<Object>> dataRows : data.entrySet()){
			Integer dataRowNum = dataRows.getKey();
			List<Object> rowData = dataRows.getValue();
			List<String> rowErrors = new ArrayList<>();
			for(int i=0; i<rowData.size(); i++){
				Object obj = rowData.get(i);
				String objStr = obj == null ? null : obj.toString();
				String receiverType = null;  
				if(i<rowData.size()-1){
					receiverType = HunterUtility.getStringOrNullOfObj(objStr);
				}
				// Receiver contact
				if(i==0){
					if(!HunterUtility.notNullNotEmpty(objStr)){
						rowErrors.add("Contact is required");
					}else{
						boolean isEmail = receiverType != null && receiverType.equals(HunterConstants.RECEIVER_TYPE_EMAIL);
						boolean isText = receiverType != null && receiverType.equals(HunterConstants.RECEIVER_TYPE_TEXT);
						boolean isCall = receiverType != null && receiverType.equals(HunterConstants.RECEIVER_TYPE_CALL);
						boolean isVoiceMail = receiverType != null && receiverType.equals(HunterConstants.RECEIVER_TYPE_VOICE_MAIL);
						boolean isValidEmail = HunterUtility.validateEmail(objStr);
						boolean isValidPhoneNumber = HunterUtility.validatePhoneNumber(objStr);
						if(isEmail && !isValidEmail){
							rowErrors.add("Type is email but contact is not valid email");
						}else if((isText || isCall || isVoiceMail) && !isValidPhoneNumber){
							rowErrors.add("Type is one of (email, voice mail, call, text) but contact is invalid phone number");
						}
					}
				// Receiver type
				}else if(i==1){
					if(!HunterUtility.validateReceiverType(objStr)){
						rowErrors.add("Invalid receiver type"); 
					}
					if(!groupType.equals(objStr)){
						rowErrors.add("Group selected is of type (" + groupType + " ) but contact type is ( " + objStr + " )" ); 
					}
				// Active
				}else if(i==2){
					if(objStr == null || (!objStr.equalsIgnoreCase("Y") && !objStr.equalsIgnoreCase("N"))){ 
						rowErrors.add("Invalid active value"); 
					}
				
				// Approved
				}else if(i==3){
					if(objStr == null || (!objStr.equalsIgnoreCase("Y") && !objStr.equalsIgnoreCase("N"))){ 
						rowErrors.add("Invalid active value"); 
					}
				}
			}
			if(!rowErrors.isEmpty()){
				errors.put(dataRowNum, rowErrors);
			}
		}
		logger.debug("Done validating extracted data!"); 
		if(errors.isEmpty()){
			logger.debug("Data passed validation!");
		}else{
			logger.debug("Data did not pass validations : \n" + HunterUtility.stringifyMap(errors));  
		}
		return errors;
	}

	@Override
	public List<ReceiverGroupReceiver> getDataBeans(Map<Integer, List<Object>> data) {
		logger.debug("Extracting beans for GroupReiverExtractor..."); 
		List<ReceiverGroupReceiver> receivers = new ArrayList<>();
		for(Map.Entry<Integer, List<Object>> dataRows : data.entrySet()){
			List<Object> rowData = dataRows.getValue();
			ReceiverGroupReceiver receiver = new ReceiverGroupReceiver();
			for(int i=0; i<rowData.size(); i++){
				Object obj = rowData.get(i);
				String objStr = obj == null ? null : obj.toString();
				// Receiver contact
				if(i==0){
					receiver.setReceiverContact(objStr);		  
				// Receiver type
				}else if(i==1){
					receiver.setReceiverType(objStr); 				
				// Active
				}else if(i==2){
					receiver.setActive( HunterUtility.getBooleanForYN(objStr) );
				}
				// Approved
				else if(i==3){
					receiver.setApproved( HunterUtility.getBooleanForYN(objStr) );
				}
			}
			if(receiver.isApproved()){
				receiver.setApprover(auditInfo.getCreatedBy());
			}
			receiver.setAuditInfo(auditInfo); 
			receivers.add(receiver);
		}
		logger.debug("Done extracting beans. Size ( " + receivers.size()  +" )"); 
		return receivers;
	}

	@Override
	public Map<String, Object> execute() {
		
		logger.info("Starting extraction execution process for GroupReceiversExtractor..."); 
		Map<String, Object> bundle = new HashMap<String, Object>();
		List<String> sheets = Arrays.asList(new String[]{ExcelExtractor.RECEIVER_GROUP_RECEIVERS});
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
		
		List<ReceiverGroupReceiver> receiverGroupReceivers = null;
		int lastRowNum = ExcelExtractorUtil.getInstance().getLastRowNumber(ExcelExtractor.RECEIVER_GROUP_RECEIVERS, workbook);
		String status = null;
		
		String[] headers = extractHeaders(ExcelExtractor.RECEIVER_GROUP_RECEIVERS, workbook);
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
		ReceiverGroup group = receiverGroupDao.getReceiverGroupById(groupId);
		
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
			receiverGroupReceivers = getDataBeans(data);
			logger.debug(HunterUtility.stringifyList(receiverGroupReceivers));  
			logger.debug("Saving extracted hunter message receiver beans..."); 
			bundle.put(DATA_BEANS, receiverGroupReceivers);
			bundle.put(RETURNED_WORKBOOK, workbook);
			bundle.put(STATUS_STR, true);
			
			this.bundle = bundle;
			this.status = true;
			this.surfaceErrors = null;
			
			
			/* If everything goes well, we persist the beans and establish relationships. */
			receiverGroupReceiverDao.insertReceiverGroupReceivers(receiverGroupReceivers);
			Set<ReceiverGroupReceiver> existent = group.getReceiverGroupReceivers();
			int existentCount = existent.size();
			logger.debug("Existing receivers count in the group : " + existentCount);
			int newCount = receiverGroupReceivers.size();
			logger.debug("New receivers count in the group : " + newCount);
			int totalCount = newCount + existentCount;
			receiverGroupReceiverDao.insertReceiverGroupReceivers(receiverGroupReceivers); 
			group.setReceiverCount(totalCount);
			existent.addAll(receiverGroupReceivers);
			receiverGroupDao.updateGroup(group);
			
			//update receiverGroupsJsons of the same group.
			String updateGroup = "UPDATE RCVR_GRP_JSN rgj set rgj.RCVR_CNT = ?  WHERE rgj.GRP_ID = ?";
			List<Object> values = new ArrayList<>();
			values.add(totalCount);
			values.add(groupId);
			logger.debug("Executing query for update groupJsons " + updateGroup); 
			hunterJDBCExecutor.executeUpdate(updateGroup, values); 
			
			
		}
		
		createStatuCell(ExcelExtractor.RECEIVER_GROUP_RECEIVERS, workbook, lastRowNum, status);
		
		/* Save import excel file */
		logger.debug("Saving excel file to db.."); 
		String stsStr = this.isStatus() ? HunterConstants.STATUS_SUCCESS : HunterConstants.STATUS_FAILED;
		HunterImportBean hunterImportBean = ImportHelper.createHntrImprtBnFrmWrkbk(workbook, auditInfo, originalFileName, ReceiverGroupReceiver.class.getSimpleName(),stsStr);
		hunterImportBeanDao.insertHunterImportBeanUsngJDBC(hunterImportBean); 
		this.setHunterImportBean(hunterImportBean);
		logger.debug("Adding group id to the importBeanIds of the group..."); 
		String importIds = group.getImportBeanIds();
		if(importIds == null){
			group.setImportBeanIds(hunterImportBean.getImportId().toString()); 
		}else{
			importIds = importIds + "," + hunterImportBean.getImportId().toString();
			group.setImportBeanIds(importIds);
		}
		receiverGroupDao.updateGroup(group);
		logger.debug("Done adding group id to the importBeanIds of the group!!"); 
		return bundle;
		
	}
	

}
