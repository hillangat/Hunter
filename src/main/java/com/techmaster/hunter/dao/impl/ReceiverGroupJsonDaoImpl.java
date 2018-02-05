package com.techmaster.hunter.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.ReceiverGroupJsonDao;
import com.techmaster.hunter.json.ReceiverGroupJson;
import com.techmaster.hunter.util.HunterHibernateHelper;

public class ReceiverGroupJsonDaoImpl implements ReceiverGroupJsonDao {
	
	private Logger logger = Logger.getLogger(ReceiverRegionDaoImpl.class);
	@Autowired private HunterJDBCExecutor hunterJDBCExecutor;
	@Autowired private HunterHibernateHelper hunterHibernateHelper;

	@Override
	public void insertReceiverGroup(ReceiverGroupJson receiverGroup) {
		logger.debug("Inserting receiver group json: " + receiverGroup); 
		String query = hunterJDBCExecutor.getQueryForSqlId("getClientDetailsData");
		logger.debug("Executing query to set first and last name : \n" + query); 
		List<Map<String, Object>> rowMapList = hunterJDBCExecutor.executeQueryRowMap(query, null);
		if(rowMapList.isEmpty()){
			throw new IllegalArgumentException("No data fround for the owner user name : " + receiverGroup.getOwnerUserName()); 
		}
		for(Map<String, Object> rowMap : rowMapList){
			String firstName = rowMap.get("FRST_NAM").toString();
			String lastName = rowMap.get("LST_NAM").toString();
			receiverGroup.setFirstName(firstName);
			receiverGroup.setLastName(lastName);
			break;
		}
		hunterHibernateHelper.saveEntity(receiverGroup);
		logger.debug("Finished inserting receiver group!"); 
	}

	@Override
	public ReceiverGroupJson getReceiverGroupById(Long groupId) {
		logger.debug("Getting receiver group json by Id : " + groupId); 
		ReceiverGroupJson group = hunterHibernateHelper.getEntityById(groupId, ReceiverGroupJson.class);
		logger.debug("Finished getting receiver group json by id : " + group); 
		return group;
	}

	@Override
	public List<ReceiverGroupJson> getAllReceiverGroups() {
		logger.debug("Getting all receiver group jsons"); 
		List<ReceiverGroupJson> groups = hunterHibernateHelper.getAllEntities(ReceiverGroupJson.class);
		logger.debug("Finished getting all receiver group jsons ( " + groups.size() + " )");  
		return groups;
	}

	@Override
	public List<ReceiverGroupJson> getAllRcvrGrpForUsrNam(String userName) {
		String query = "FROM ReceiverGroupJson r WHERE r.ownerUserName = '" + userName + "'";
		logger.debug("Executing query : " + query); 
		List<ReceiverGroupJson> groups = hunterHibernateHelper.executeQueryForObjList(ReceiverGroupJson.class, query);
		logger.debug("Finished getting receiver group jsons by user name ( " + groups.size() + " )");
		return groups;
	}

	@Override
	public String deleteGroup(ReceiverGroupJson receiverGroup) {
		String isUsedQuery = "select ts.TSK_ID from TSK_GRPS ts where ts.GRP_ID = ?";
		Map<Integer, List<Object>> rowMapList = hunterJDBCExecutor.executeQueryRowList(isUsedQuery, hunterJDBCExecutor.getValuesList(new Object[]{receiverGroup.getGroupId()}));
		List<Object> rowList = new ArrayList<>();
		for(Entry<Integer, List<Object>> entry : rowMapList.entrySet()){
			List<Object> values = entry.getValue();
			rowList.addAll(values);
		}
		if(!rowList.isEmpty()){
			StringBuilder builder = new StringBuilder();
			for(Object obj : rowList){
				builder.append(obj);
				builder.append(",");
			}
			String idStr = builder.toString();
			idStr = idStr.endsWith(",") ? idStr.substring(0,idStr.length()-1) : idStr;
			String message = "Group is being used in Tasks ( " + idStr + " )";
			logger.debug("Cannot delete group since. " + message); 
			return message;
		}
		logger.debug("Deleting receiver group...");
		hunterHibernateHelper.deleteEntity(receiverGroup);
		logger.debug("Finished deleting group");
		return null;
	}

	@Override
	public void updateGroup(ReceiverGroupJson update) {
		logger.debug("Upadting receiver group : " + update);		 
		hunterHibernateHelper.updateEntity(update);
		logger.debug("Finished updating receiver group!"); 		
	}

	@Override
	public List<ReceiverGroupJson> getAllGrouspJson() {
		logger.debug("Fetching all groups..."); 
		List<ReceiverGroupJson> receiverGroups = hunterHibernateHelper.getAllEntities(ReceiverGroupJson.class);
		logger.debug("Done fetching grous. Size( " + receiverGroups.size() + " )");
		return receiverGroups;
	}

	@Override
	public String getGroupImportBeansDetails(Long groupId) {
		return null;
	}

}
