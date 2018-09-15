package com.techmaster.hunter.controllers;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.constants.HunterURLConstants;
import com.techmaster.hunter.dao.types.HunterImportBeanDao;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.ReceiverGroupDao;
import com.techmaster.hunter.dao.types.ReceiverGroupJsonDao;
import com.techmaster.hunter.dao.types.TaskDao;
import com.techmaster.hunter.imports.beans.HunterImportBean;
import com.techmaster.hunter.imports.extractors.GroupReceiversExtractor;
import com.techmaster.hunter.imports.extractors.HunterMsgReceiverExtractor;
import com.techmaster.hunter.json.ReceiverGroupDropDownJson;
import com.techmaster.hunter.json.ReceiverGroupJson;
import com.techmaster.hunter.json.ReceiverRegionJson;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.ReceiverGroup;
import com.techmaster.hunter.obj.beans.ReceiverGroupReceiver;
import com.techmaster.hunter.obj.converters.ReceiverGroupConverter;
import com.techmaster.hunter.region.RegionService;
import com.techmaster.hunter.task.TaskManager;
import com.techmaster.hunter.util.HunterUtility;

@Controller
@RequestMapping(value="/messageReceiver") 
public class MessageReceiverController extends HunterBaseController{
	
	private Logger logger = Logger.getLogger(MessageReceiverController.class);
	@Autowired private ReceiverGroupDao receiverGroupDao;
	@Autowired private HunterJDBCExecutor hunterJDBCExecutor;
	@Autowired private HunterImportBeanDao hunterImportBeanDao;
	@Autowired private ReceiverGroupJsonDao receiverGroupJsonDao;
	@Autowired private RegionService regionService;
	@Autowired private TaskManager taskManager;
	@Autowired private TaskDao taskDao;

	@RequestMapping(value = "/action/import/receivers/post/save", method = RequestMethod.POST)
	public @ResponseBody String  importMessageReceivers(MultipartHttpServletRequest request, HttpServletResponse response){
		
		logger.debug("Extracting workbook and creating message receiver extractor.."); 
		
		Object[] wbkExtracts = HunterUtility.getWorkbookFromMultiPartRequest(request);
		Workbook workbook = (Workbook)wbkExtracts[0];
		String fileName = (String)wbkExtracts[1];
		
		AuditInfo auditInfo = HunterUtility.getAuditInfoFromRequestForNow(request, HunterConstants.HUNTER_ADMIN_USER_NAME);
		HunterMsgReceiverExtractor hntMsgRcvrExrctr = new HunterMsgReceiverExtractor(workbook, auditInfo, fileName);
		
		logger.debug("Done Extracting workbook and creating message receiver extractor!!"); 
		
		@SuppressWarnings("unused")
		Map<String, Object> bundle = hntMsgRcvrExrctr.execute();
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("status", HunterConstants.STATUS_SUCCESS);
		
		return HunterUtility.stringifyMap(results); 
		
	}
	
	@RequestMapping(value="/action/group/home", method=RequestMethod.GET)
	public String receiverGroupHome(){
		return "views/receiverGroups";
	}
	
	@RequestMapping(value="/action/group/dropDown/{selTaskId}", method=RequestMethod.POST)
	@Produces("application/json")
	@Consumes("application/json")
	public @ResponseBody List<ReceiverGroupDropDownJson> groupDropDown(@PathVariable("selTaskId") Long selTaskId){
		String msgType = taskDao.getTaskMsgType(selTaskId);
		logger.debug("Fetching dropdown group values for message type : " + msgType); 
		List<ReceiverGroupDropDownJson> dropDownJsons = receiverGroupDao.getAllRcvrGrpDrpDwnJsnForMsgTyp(msgType.toLowerCase());
		return dropDownJsons;
	}
	
	@RequestMapping(value="/action/group/read", method=RequestMethod.POST)
	@Produces("application/json")
	@Consumes("application/json")
	public @ResponseBody List<ReceiverGroupJson> groupsRead(){
		 List<ReceiverGroupJson> receiverGroupJsons = ReceiverGroupConverter.getInstance().getAllReceiverGroupJsons();
		 return receiverGroupJsons;
	}
	
	@RequestMapping(value="/action/group/create", method=RequestMethod.POST)
	@Produces("application/json")
	@Consumes("application/json")
	public @ResponseBody ReceiverGroupJson createGroup(@RequestBody ReceiverGroupJson receiverGroupJson, HttpServletRequest request){
		String userName = getUserName();
		AuditInfo auditInfo = HunterUtility.getAuditInfoFromRequestForNow(request, userName);
		receiverGroupJson.setCreatedBy(auditInfo.getCreatedBy());
		receiverGroupJson.setCretDate(HunterUtility.formatDate(auditInfo.getCretDate(), HunterConstants.DATE_FORMAT_STRING));
		receiverGroupJson.setLastUpdatedBy(auditInfo.getLastUpdatedBy()); 
		receiverGroupJson.setLastUpdate(HunterUtility.formatDate(auditInfo.getLastUpdate(), HunterConstants.DATE_FORMAT_STRING)); 
		ReceiverGroup receiverGroup = ReceiverGroupConverter.getInstance().getReceiverGroupForJson(receiverGroupJson);
		receiverGroupDao.insertReceiverGroup(receiverGroup); 
		receiverGroupJson.setGroupId(receiverGroup.getGroupId()); 
		receiverGroupJsonDao.insertReceiverGroup(receiverGroupJson); 
		receiverGroupJson.setGroupId(receiverGroup.getGroupId()); 
		return receiverGroupJson;
	}
	
	@RequestMapping(value="/action/group/update", method=RequestMethod.POST)
	@Produces("application/json")
	@Consumes("application/json")
	public @ResponseBody ReceiverGroupJson updateGroup(@RequestBody ReceiverGroupJson receiverGroupJson, HttpServletResponse response){
		
		// if the change is to receiver type, cannot be update if it already has receivers.
		if(receiverGroupJson.getReceiverCount() != 0){
			String query= "select RCVR_TYP from RCVR_GRP where GRP_ID = ?";
			List<Object> values = new ArrayList<>();
			values.add(receiverGroupJson.getGroupId());
			Map<Integer, List<Object>> rowListsMap = hunterJDBCExecutor.executeQueryRowList(query, values);
			List<Object> rowList = rowListsMap.isEmpty() ? null : rowListsMap.get(1);
			String oldType = rowList != null && !rowList.isEmpty() ? HunterUtility.getStringOrNullOfObj(rowList.get(0)) : null; 
			String newType = receiverGroupJson.getReceiverType();
			if(!oldType.equalsIgnoreCase(newType)){
				logger.debug("Cannot update the receiver type since it already has receivers in it"); 
				receiverGroupJson.setReceiverType(oldType); 
			}
		}
		String userName = receiverGroupJson.getOwnerUserName();
		String query = hunterJDBCExecutor.getQueryForSqlId("getClientDetailsForTaskOwner"); 
		List<Object> values = new ArrayList<>();
		values.add(userName);
		Map<Integer, List<Object>> results = hunterJDBCExecutor.executeQueryRowList(query, values);
		List<Object> clientDetailsList = results.get(1);
		String firstName = clientDetailsList.get(3) + "";
		String lastName = clientDetailsList.get(4) + "";
		String groupName = receiverGroupJson.getGroupName();
		String groupDesc = receiverGroupJson.getGroupDesc();
		String receiverType = receiverGroupJson.getReceiverType();
		
		query = "UPDATE RCVR_GRP SET OWNR_USR_NAM = ?, GRP_NAME = ?, GRP_DESC = ?, RCVR_TYP = ? WHERE GRP_ID = ?";
		values.clear();
		values.add(userName);
		values.add(groupName);
		values.add(groupDesc);
		values.add(receiverType);
		values.add(receiverGroupJson.getGroupId());
		hunterJDBCExecutor.executeUpdate(query, values); 

		receiverGroupJson.setFirstName(firstName);
		receiverGroupJson.setLastName(lastName);
		//ReceiverGroup receiverGroup = ReceiverGroupConverter.getInstance().getReceiverGroupForJson(receiverGroupJson);
		//receiverGroupDao.updateGroup(receiverGroup); 
		return receiverGroupJson;
	}
	
	@RequestMapping(value="/action/group/destroy", method=RequestMethod.POST)
	@Produces("application/json")
	@Consumes("application/json")
	public @ResponseBody String destroyGroup(@RequestBody ReceiverGroupJson receiverGroupJson){
		JSONObject json = new JSONObject();
		try {
			ReceiverGroup receiverGroup = ReceiverGroupConverter.getInstance().getReceiverGroupForJson(receiverGroupJson);
			String results = receiverGroupDao.deleteGroup(receiverGroup);
			if(results != null){
				json.put(HunterConstants.STATUS_STRING, HunterConstants.STATUS_FAILED);
				json.put("message", results);
				return json.toString();
			}else{
				logger.debug("Successfully deleted group. Deleting corresponding json..."); 
				String results2 = receiverGroupJsonDao.deleteGroup(receiverGroupJson);
				if(results2 != null){
					json.put(HunterConstants.STATUS_STRING, HunterConstants.STATUS_FAILED);
					json.put("message", results);
					return json.toString();
				}
			}
			json.put(HunterConstants.STATUS_STRING, HunterConstants.STATUS_SUCCESS);
			json.put("message", "Successfully deleted receiver group!");
		} catch (Exception e) {
			json.put(HunterConstants.STATUS_FAILED, HunterConstants.STATUS_FAILED);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}

	@RequestMapping(value="/action/group/getImportDetails", method=RequestMethod.POST)
	@Produces("application/json")
	@Consumes("application/json")
	public @ResponseBody String getGroupImportDetails(HttpServletRequest request){
		logger.debug("Getting group import details..."); 
		String body = null;
		JSONObject messages = new JSONObject();
		Long groupId = null;
		try {
			body = HunterUtility.getRequestBodyAsString(request);
			JSONObject json = new JSONObject(body);
			groupId = json.getLong("groupId");
		} catch (IOException e) {
			e.printStackTrace();
			messages.put(HunterConstants.MESSAGE_STRING, e.getMessage());
			messages.put(HunterConstants.STATUS_STRING, HunterConstants.STATUS_FAILED);
			return messages.toString();
		}
		String records = receiverGroupDao.getGroupImportBeansDetails(groupId);
		logger.debug("Returning recrods : " + records);
		return records;
		
	}
	
	
	@RequestMapping(value="/action/group/import/receiverGroupReceivers/{groupId}", method=RequestMethod.POST) 
	@ResponseBody public String importReceiverRegions(MultipartHttpServletRequest request, HttpServletResponse response, @PathVariable("groupId")Long groupId){
		
		logger.debug("Beginning receiver group receivers import process..."); 
		Object[] wbkExtracts = HunterUtility.getWorkbookFromMultiPartRequest(request);
		Workbook workbook = (Workbook)wbkExtracts[0];
		String fileName = (String)wbkExtracts[1];
		String userName = getUserName();
		AuditInfo auditInfo = HunterUtility.getAuditInfoFromRequestForNow(request, userName);
		GroupReceiversExtractor receiverExtractor = new GroupReceiversExtractor(workbook,auditInfo, fileName, groupId);
		
		@SuppressWarnings("unused")
		Map<String, Object> bundle = receiverExtractor.execute();
		
		return "Error";
		
		
	}
	

	@RequestMapping(value="/action/taskReceivers/getAllCounts/{taskId}", method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody public String getDistinctReceiversFroTaskId(HttpServletRequest request, HttpServletResponse response, @PathVariable("taskId")Long taskId){
		logger.debug("Fetching all receiver counts for task id : " + taskId); 
		JSONObject result = new JSONObject();
		try {
			Object[] regionsData = regionService.getTrueHntrMsgRcvrCntFrTaskRgns(taskId);
			int regionCount = (int)regionsData[0];
			@SuppressWarnings("unchecked")
			int regionsNumber = ((List<? extends ReceiverRegionJson>)regionsData[1]).size();
			int groupCount = taskManager.getTotalTaskGroupsReceivers(taskId);
			int groupNumber = taskManager.getTaskGroupTotalNumber(taskId);
			result.put("regionCount", regionCount);
			result.put("groupCount", groupCount);
			result.put("totalCount", regionCount + groupCount);
			result.put("regionsNumber", regionsNumber);
			result.put("groupNumber", groupNumber);
			logger.debug("Successfully fetched receiver count for task ( " + taskId + " ) : "  + result) ;
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return HunterUtility.setJSONObjectForFailure(result, e.getMessage()).toString();
		}
	}
	
	
	@RequestMapping(value="/action/group/downloadGroupImportBean/{importId}", method=RequestMethod.GET) 
	@ResponseBody public byte[] downloadGroupImportFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("importId")Long importId){
		
		logger.debug("Getting import file from db..."); 
		HunterImportBean hunterImportBean = hunterImportBeanDao.getHunterImportBeanById(importId);
		Blob blob = hunterImportBean.getExcelBlob();
		Workbook newWorkbook = null;
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		byte[] bytes = null;
		
		try {
			newWorkbook = new XSSFWorkbook(blob.getBinaryStream());
			newWorkbook.write(outByteStream);
			bytes = outByteStream.toByteArray();
			response.setContentType("application/ms-excel");
			response.setContentLength(bytes.length);
			response.setHeader("Expires:", "0"); // eliminates browser caching
			response.setHeader("Content-Disposition", "attachment; filename="+ hunterImportBean.getOriginalFileName()); 
			OutputStream outStream = response.getOutputStream();
			outStream.write(bytes);
			outStream.flush();
			logger.debug("Successfully prepared the output stream for excel file!!"); 
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		
		logger.debug("Successfully wrote the workbook to response!!"); 
		
		return bytes;
		
	}
	
	
	
	
	@RequestMapping(value="/action/group/downloadAllReceivers/{groupId}", method=RequestMethod.GET) 
	@ResponseBody public byte[] downloadAllGroupReceivers(HttpServletRequest request, HttpServletResponse response, @PathVariable("groupId")Long groupId){

		ReceiverGroup receiverGroup = receiverGroupDao.getReceiverGroupById(groupId);
		System.out.println(receiverGroup); 
		String templateLoc = HunterURLConstants.RESOURCE_TEMPL_FOLDER + "\\ReceiverGroupReceivers.xlsx";
		FileInputStream fis = null;
		Workbook newWorkbook = null;
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		byte[] bytes = null;
		
		try {
			fis = new FileInputStream(templateLoc);
			newWorkbook = new XSSFWorkbook(fis);
			logger.debug("Successfully retrieved template workbook");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		
		
		try {
			writeAllReceiversToWorkbook(receiverGroup, newWorkbook); 
			newWorkbook.write(outByteStream);
			bytes = outByteStream.toByteArray();
			response.setContentType("application/ms-excel");
			response.setContentLength(bytes.length);
			response.setHeader("Expires:", "0"); // eliminates browser caching
			response.setHeader("Content-Disposition", "attachment; filename="+ "all_group_receivers_"+ groupId + ".xlsx"); 
			OutputStream outStream = response.getOutputStream();
			outStream.write(bytes);
			outStream.flush();
			logger.debug("Successfully prepared the output stream for excel file!!"); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.debug("Successfully wrote the workbook to response!!"); 
		
		return bytes;
		
	}
	
	private void writeAllReceiversToWorkbook(ReceiverGroup receiverGroup, Workbook workbook){
		logger.debug("Writing receivers to workbook template..."); 
		Set<ReceiverGroupReceiver> receiverGroupReceivers = receiverGroup.getReceiverGroupReceivers();
		if(receiverGroupReceivers.isEmpty()) return;
		int indx = 1;
		for(ReceiverGroupReceiver receiver : receiverGroupReceivers){
			Sheet sheet = workbook.getSheet("RECEIVERS");
			Row row = sheet.createRow(indx);
			indx++;
			Cell cell = row.createCell(0);
			Cell cell2 = row.createCell(1);
			Cell cell3 = row.createCell(2);
			Cell cell4 = row.createCell(3);
			cell.setCellValue(receiver.getReceiverContact());
			cell2.setCellValue(receiver.getReceiverType());
			cell3.setCellValue(HunterUtility.getYNForBoolean(receiver.isActive())); 
			cell4.setCellValue(HunterUtility.getYNForBoolean(receiver.isApproved())); 
		}
		logger.debug("Finished writing receivers to workbook!!"); 
	}
	
	
}
