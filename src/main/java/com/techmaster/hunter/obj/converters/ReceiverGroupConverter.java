package com.techmaster.hunter.obj.converters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.impl.HunterUserDaoImpl;
import com.techmaster.hunter.dao.impl.ReceiverGroupDaoImpl;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.HunterUserDao;
import com.techmaster.hunter.dao.types.ReceiverGroupDao;
import com.techmaster.hunter.json.ReceiverGroupJson;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.ReceiverGroup;
import com.techmaster.hunter.util.HunterUtility;


public class ReceiverGroupConverter {
	
	private static ReceiverGroupConverter instance;
	private Logger logger = Logger.getLogger(getClass());
	private HunterUserDao hunterUserDao = new HunterUserDaoImpl();
	private ReceiverGroupDao receiverGroupDao = new ReceiverGroupDaoImpl();
	
	static {
		if(instance == null){
			synchronized (ReceiverGroupConverter.class) {
				instance = new ReceiverGroupConverter();
			}
		}
	}
	
	// private constructor.
	private ReceiverGroupConverter() {
		super();
	}

	public static ReceiverGroupConverter getInstance(){
		return instance;
	}
	
	private static List<String> getGroupsUserNamesList(List<ReceiverGroup> groups){
		if(groups == null || groups.isEmpty()) 
			return new ArrayList<String>();
		List<String> userNames = new ArrayList<>();
		Set<String> set = new HashSet<>();
		for(ReceiverGroup group : groups){
			String  userName = group.getOwnerUserName();
			set.add(userName);
		}
		userNames.addAll(set);
		return userNames;
	}
	
	public List<ReceiverGroupJson> getAllReceiverGroupJsons(){
		logger.debug("Retrieving all receiver group details..."); 
		List<ReceiverGroupJson> receiverGroups = new ArrayList<>();
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
		String query = hunterJDBCExecutor.getQueryForSqlId("getAllGroupsDetails");
		logger.debug("Executing query : " + query); 
		List<Map<String, Object>>  rowMapList = hunterJDBCExecutor.executeQueryRowMap(query, null);
		if(rowMapList == null || rowMapList.isEmpty()) 
			return receiverGroups;
		for(Map<String, Object> map : rowMapList){
			logger.debug(HunterUtility.stringifyMap(map));  
			ReceiverGroupJson group = new ReceiverGroupJson();
			for(Map.Entry<String, Object> entry : map.entrySet()){
				String stringVal = HunterUtility.getStringOrNullOfObj(entry.getValue());
				switch (entry.getKey()) {
				case "GRP_ID":
					group.setGroupId(HunterUtility.getLongFromObject(entry.getValue()));   
					break;
				case "OWNR_USR_NAM":
					group.setOwnerUserName(stringVal);    
					break;
				case "FRST_NAM":
					
					group.setFirstName(stringVal);      
					break;
				case "LST_NAM":
					group.setLastName(stringVal);      
					break;
				case "GRP_NAME":
					group.setGroupName(stringVal);      
					break;
				case "GRP_DESC":
					group.setGroupDesc(stringVal);      
					break;
				case "RCVR_CNT":
					int groupCount = 0;
					if(stringVal != null){
						groupCount = Integer.parseInt(stringVal);	
					}
					group.setReceiverCount(groupCount);        
					break;
				case "RCVR_TYP":
					group.setReceiverType(stringVal);      
					break;
				case "CRET_DATE":
					group.setCretDate(HunterUtility.parseDate(entry.getValue().toString(), HunterConstants.DATE_FORMAT_STRING));       
					break;
				case "CRTD_BY":
					group.setCreatedBy(HunterUtility.getStringOrNullOfObj(entry.getValue()));      
					break;
				case "LST_UPDT_DATE":
					group.setLastUpdate(HunterUtility.parseDate(entry.getValue().toString(), HunterConstants.DATE_FORMAT_STRING));        
					break;
				case "LST_UPDTD_BY":
					group.setLastUpdatedBy(HunterUtility.getStringOrNullOfObj(entry.getValue()));      
					break;
				default:
					break;
				}
			}
			receiverGroups.add(group);
		}
		return receiverGroups;
	}
	
	
	public List<ReceiverGroupJson> getGroupsJsons(List<ReceiverGroup> groups){
		
		logger.debug("Converting to jsons. Size to convert ( " + groups == null || groups.isEmpty() ? 0 : groups.size() + " ) "); 
		List<ReceiverGroupJson> jsons = new ArrayList<ReceiverGroupJson>();
		
		if(groups != null && !groups.isEmpty()){
			
			List<String> userNames = getGroupsUserNamesList(groups);
			Map<String, List<String>> fullNames = hunterUserDao.getFullNamesForUserNames(userNames);
			
			for(ReceiverGroup group : groups){
				
				ReceiverGroupJson json = new ReceiverGroupJson();
				
				AuditInfo auditInfo = group.getAuditInfo();
				json.setCreatedBy(auditInfo.getCreatedBy());
				json.setCretDate(auditInfo.getCretDate());
				json.setLastUpdate(auditInfo.getLastUpdate());
				json.setLastUpdatedBy(auditInfo.getLastUpdatedBy()); 
				
				List<String> fullNamesList = fullNames.get(group.getOwnerUserName());
				//this can happen is the user who owned the group was deleted.
				if(fullNamesList != null && !fullNamesList.isEmpty()){
					String firstName = fullNamesList.get(0);
					String lastName = fullNamesList.get(1);
					json.setFirstName(firstName);
					json.setLastName(lastName);
				}
				
				json.setGroupDesc(group.getGroupDesc());
				json.setGroupId(group.getGroupId());
				json.setGroupName(group.getGroupName()); 
				json.setOwnerUserName(group.getOwnerUserName()); 
				json.setReceiverCount(group.getReceiverCount()); 
				
				json.setReceiverType(group.getReceiverType());
				
				jsons.add(json);
				
			}
		}else{
			logger.debug("Groups passed is is either null or empty. Returning empty json list..."); 
			return jsons;
		}
		logger.debug("Finished cconverting groups to json! Size ( " + jsons.size() + " )");
		return jsons;
	}
	
	public ReceiverGroup setValuesFromJson(ReceiverGroup group, ReceiverGroupJson json){
		
		AuditInfo auditInfo = new AuditInfo();
		auditInfo.setCreatedBy(json.getCreatedBy());
		auditInfo.setCretDate(json.getCretDate());
		auditInfo.setLastUpdate(json.getLastUpdate());
		auditInfo.setLastUpdatedBy(json.getLastUpdatedBy()); 
		group.setAuditInfo(auditInfo);
		
		group.setGroupDesc(json.getGroupDesc());
		group.setGroupId(json.getGroupId()); 
		group.setGroupName(json.getGroupName()); 
		group.setOwnerUserName(json.getOwnerUserName()); 
		group.setReceiverCount(json.getReceiverCount());
		group.setReceiverType(json.getReceiverType()); 
		
		return group;
		
	}
	
	public ReceiverGroup getReceiverGroupForJson(ReceiverGroupJson receiverGroupJson){
		if(receiverGroupJson.getGroupId() != 0){
			logger.debug("Group is already existent. Updating it..."); 
			ReceiverGroup group = receiverGroupDao.getReceiverGroupById(receiverGroupJson.getGroupId());
			setValuesFromJson(group, receiverGroupJson);
			return group;
		}
		logger.debug("New group. Setting values..."); 
		ReceiverGroup group = new ReceiverGroup();
		setValuesFromJson(group, receiverGroupJson);
		return group;
	}
	
	
}
