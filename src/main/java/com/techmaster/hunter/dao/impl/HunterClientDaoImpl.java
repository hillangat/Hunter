package com.techmaster.hunter.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.dao.types.HunterClientDao;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.json.HunterClientsDetailsJson;
import com.techmaster.hunter.obj.beans.HunterClient;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterUtility;

public class HunterClientDaoImpl implements HunterClientDao{
	
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired private HunterJDBCExecutor hunterJDBCExecutor;
	@Autowired private HunterHibernateHelper hunterHibernateHelper;

	@Override
	public void insertHunterClient(HunterClient client) {
		logger.debug("Saving hunter client...");
		hunterHibernateHelper.saveEntity(client);
		logger.debug("Done saving hunter client!!");
	}

	@Override
	public void insertHunterClients(List<HunterClient> hunterClients) {
		logger.debug("Saving hunter clients..."); 
		hunterHibernateHelper.saveEntities(hunterClients);
		logger.debug("Done saving hunter clients!!");
		
	}

	@Override
	public void deleteHunterClientById(Long clientId) {
		logger.debug("Deleting hunter client...");
		HunterClient client = getHunterClientById(clientId);
		hunterHibernateHelper.deleteEntity(client);
		logger.debug("Done deleting hunter client!!"); 
	}

	@Override
	public void deleteHunterClient(HunterClient client) {
		logger.debug("Deleting hunter client...");
		hunterHibernateHelper.deleteEntity(client);
		logger.debug("Done deleting hunter client!!"); 
	}

	@Override
	public HunterClient getHunterClientById(Long id) {
		logger.debug("Getting hunter client by id >> " + id);
		HunterClient client = hunterHibernateHelper.getEntityById(id, HunterClient.class);
		logger.debug("Loaded client >> " + client); 
		logger.debug("Finished loading hunter client by Id!!");
		return client;
	}

	@Override
	public List<HunterClient> getAllclients() {
		logger.debug("Fetching all clients...");
		List<HunterClient> clients = hunterHibernateHelper.getAllEntities(HunterClient.class);
		logger.debug("Successfully fetched clients. Size( " + clients.size() + " )");  
		return clients;
	}

	@Override
	public void updateClient(HunterClient update) {
		logger.debug("Updating hunter client...");
		hunterHibernateHelper.updateEntity(update); 
		logger.debug("Done updating hunter client!!");
	}

	@Override
	public Long nextClientId() {
		logger.debug("Getting next hunter client id...");
		Long maxClientId = hunterHibernateHelper.getMaxEntityIdAsNumber(HunterClient.class, Long.class, "clientId");
		if(maxClientId == null)
			maxClientId = 0L;
		maxClientId++;
		logger.debug("Successfully obtained next hunter client Id ( " + maxClientId + " )");  
		return maxClientId;
	}
	
	
	public static void main(String[] args) {
		HunterClient client = new HunterClientDaoImpl().getHunterClientForUserId(1L); 
		System.out.println(client);   
	}

	@Override
	public HunterClient getHunterClientForUserId(Long userId) {
		String query = "FROM HunterClient c WHERE c.clientId = " + userId;
		List<HunterClient> clients = hunterHibernateHelper.executeQueryForObjList(HunterClient.class, query);
		HunterClient client = null;
		if(clients.size() > 1){
			logger.warn("Returned more than one client!!! Will return the first one!!");
		}
		client = clients != null && !clients.isEmpty() ? clients.get(0) : client;
		logger.debug("Successfully fetched client : " + client); 
		return client;
		
	}

	@Override
	public HunterClient editReceiverAndBudget(Long clientId, float budget, boolean isReceiver) {
		logger.debug("Updating client id..."); 
		HunterClient client = hunterHibernateHelper.getEntityById(clientId, HunterClient.class);
		if(client != null){
			client.setClientTotalBudget(budget);
			client.setReceiver(isReceiver);
			updateClient(client); 
			logger.debug("Successfully updated client!!"); 
		}else{
			logger.debug("No client found for client id ( " + clientId  + " )");  
		}
		return client;
	}

	@Override
	public List<HunterClientsDetailsJson> getAllHunterClientDetailsJson() {
		
		logger.debug("Fetching all client details...");
		String query = hunterJDBCExecutor.getQueryForSqlId("getClientDetailsData");
		List<Map<String, Object>> rowMapList = hunterJDBCExecutor.executeQueryRowMap(query, null);
		List<HunterClientsDetailsJson> detailsJson = new ArrayList<HunterClientsDetailsJson>();
		
		for(Map<String, Object> rowMap : rowMapList){
			
			String firstName = rowMap.get("FRST_NAM").toString();
			String lastName = rowMap.get("LST_NAM").toString();
			String userName = rowMap.get("USR_NAM").toString();
			String clientIdStr = rowMap.get("HNTR_CLNT_ID").toString();
			Long clientId = HunterUtility.getLongFromObject(clientIdStr);
			String clientText = lastName + "(" + userName + ")";
			
			HunterClientsDetailsJson json = new HunterClientsDetailsJson();
			json.setClientId(clientId);
			json.setFirstName(firstName);
			json.setLastName(lastName);
			json.setUserName(userName);
			json.setClientText(clientText); 
			
			detailsJson.add(json);
		}
		logger.debug("Successfully fetched all client details. Size ( " + detailsJson.size() + " )"); 
		return detailsJson;
	}

	@Override
	public String validateClientDelete(Long clientId) {
		return null;
	}
	
	

}
