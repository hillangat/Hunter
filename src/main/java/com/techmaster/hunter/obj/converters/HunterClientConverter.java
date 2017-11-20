package com.techmaster.hunter.obj.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.techmaster.hunter.constants.HunterDaoConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.json.HunterClientJson;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.util.HunterUtility;

public class HunterClientConverter {
	
	private Logger logger = Logger.getLogger(getClass());
	private HunterClientConverter(){};
	private static HunterClientConverter instance = null;
	
	static{
		if( instance == null ){
			synchronized (HunterClientConverter.class) {
				instance = new HunterClientConverter();
			}
		}
	}
	
	public static HunterClientConverter getInstance(){
		return instance;
	}
	
	public void updateHunterClient(HunterClientJson hunterClientJson, AuditInfo auditInfo){
		
		logger.debug("Updating client with client id : " + hunterClientJson.getClientId()); 
		
		String isReceiver = HunterUtility.getYNForBoolean(hunterClientJson.isReceiver()) ; 
		float budget = hunterClientJson.getBudget();
		long clientId = hunterClientJson.getClientId();
		
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
		String query = hunterJDBCExecutor.getQueryForSqlId("updateHunterClient");
		
		List<Object> values = new ArrayList<>();
		values.add(budget);
		values.add(isReceiver);
		values.add(auditInfo.getLastUpdatedBy());
		values.add(clientId);
		
		hunterJDBCExecutor.executeUpdate(query, values);
		logger.debug("Successfully updated client with client id : " + hunterClientJson.getClientId());
		
	}
	
	public List<HunterClientJson> getAllHunterCientJsons(){
		
		logger.debug("Retrieving all hunter client jsons..."); 
		List<HunterClientJson> hunterClientJsons = new ArrayList<HunterClientJson>();
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
		String query = hunterJDBCExecutor.getQueryForSqlId("getAllClientsJsonData");
		List<Map<String, Object>>  rowMapList = hunterJDBCExecutor.executeQueryRowMap(query, null);
		
		if(rowMapList != null && !rowMapList.isEmpty()){
			for(Map<String,Object> rowMap : rowMapList){
				
				HunterClientJson clientJson = new HunterClientJson();
				Long clientId = HunterUtility.getLongFromObject(rowMap.get("HNTR_CLNT_ID")); 
				String firstName = HunterUtility.getStringOrNullOfObj(rowMap.get("FRST_NAM"));
				String isReceiver = HunterUtility.getStringOrNullOfObj(rowMap.get("IS_RCVR")); 
				String lastName = HunterUtility.getStringOrNullOfObj(rowMap.get("LST_NAM"));
				String email = HunterUtility.getStringOrNullOfObj(rowMap.get("EMAIL"));
				String userName = HunterUtility.getStringOrNullOfObj(rowMap.get("USR_NAM"));
				float clientBudget = Float.parseFloat(HunterUtility.getStringOrNullOfObj(rowMap.get("CLNT_TL_BDGT")));
				String createdOn = HunterUtility.getStringOrNullOfObj(rowMap.get("CRET_DATE"));
				String createdBy = HunterUtility.getStringOrNullOfObj(rowMap.get("CRTD_BY"));
				String lastUpdatedOn = HunterUtility.getStringOrNullOfObj(rowMap.get("LST_UPDATE"));
				String lastUpdatedBy = HunterUtility.getStringOrNullOfObj(rowMap.get("LST_UPDTD_BY"));
				
				clientJson.setBudget(clientBudget);
				clientJson.setClientId(clientId);
				clientJson.setCreatedBy(createdBy);
				clientJson.setCreatedDate(createdOn);
				clientJson.setEmail(email);
				clientJson.setFirstName(firstName);
				clientJson.setLastName(lastName);
				clientJson.setReceiver(HunterUtility.getBooleanForYN(isReceiver));
				clientJson.setUpdatedBy(lastUpdatedBy);
				clientJson.setUpdatedOn(lastUpdatedOn);
				clientJson.setUserName(userName); 
				
				hunterClientJsons.add(clientJson);
				
			}
		}
		
		logger.debug("Finished retrieving hunter clients : Size ( " + hunterClientJsons.size() + " )");
		
		return hunterClientJsons;
	}
	
	private HunterClientJson createHntrJsnFrMap( Map<String, Object> rowMap ) {
		
		HunterClientJson clientJson = new HunterClientJson();
		
		Long clientId = HunterUtility.getLongFromObject(rowMap.get("HNTR_CLNT_ID")); 
		String firstName = HunterUtility.getStringOrNullOfObj(rowMap.get("FRST_NAM"));
		String isReceiver = HunterUtility.getStringOrNullOfObj(rowMap.get("IS_RCVR")); 
		String lastName = HunterUtility.getStringOrNullOfObj(rowMap.get("LST_NAM"));
		String email = HunterUtility.getStringOrNullOfObj(rowMap.get("EMAIL"));
		String userName = HunterUtility.getStringOrNullOfObj(rowMap.get("USR_NAM"));
		float clientBudget = Float.parseFloat(HunterUtility.getStringOrNullOfObj(rowMap.get("CLNT_TL_BDGT")));
		String createdOn = HunterUtility.getStringOrNullOfObj(rowMap.get("CRET_DATE"));
		String createdBy = HunterUtility.getStringOrNullOfObj(rowMap.get("CRTD_BY"));
		String lastUpdatedOn = HunterUtility.getStringOrNullOfObj(rowMap.get("LST_UPDATE"));
		String lastUpdatedBy = HunterUtility.getStringOrNullOfObj(rowMap.get("LST_UPDTD_BY"));
		
		clientJson.setBudget(clientBudget);
		clientJson.setClientId(clientId);
		clientJson.setCreatedBy(createdBy);
		clientJson.setCreatedDate(createdOn);
		clientJson.setEmail(email);
		clientJson.setFirstName(firstName);
		clientJson.setLastName(lastName);
		clientJson.setReceiver(HunterUtility.getBooleanForYN(isReceiver));
		clientJson.setUpdatedBy(lastUpdatedBy);
		clientJson.setUpdatedOn(lastUpdatedOn);
		clientJson.setUserName(userName); 
		
		return clientJson;
	}

	public HunterClientJson getClientForUserId(Long userId) {
		HunterJDBCExecutor executor = HunterDaoFactory.getObject( HunterJDBCExecutor.class );
		String query = executor.getQueryForSqlId(HunterDaoConstants.GET_CLIENTS_FOR_ANGULAR_QUERY );
		Map<String, Object> rowMap = executor.executeQueryFirstRowMap(query, Arrays.asList( Long.toString(userId) ));
		return createHntrJsnFrMap(rowMap);
	}

}
