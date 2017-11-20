package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.json.HunterClientsDetailsJson;
import com.techmaster.hunter.obj.beans.HunterClient;

public interface HunterClientDao {
	
	public void insertHunterClient(HunterClient client);
	public void insertHunterClients(List<HunterClient> HunterClients);
	public void deleteHunterClientById(Long clientId);
	public void deleteHunterClient(HunterClient client);
	public HunterClient getHunterClientById(Long id);
	public List<HunterClient> getAllclients();
	public void updateClient(HunterClient update);
	public Long nextClientId();
	public HunterClient getHunterClientForUserId(Long longFromObject);
	public HunterClient editReceiverAndBudget(Long clientId, float budget, boolean isReceiver);
	public List<HunterClientsDetailsJson> getAllHunterClientDetailsJson();
	public String validateClientDelete(Long clientId);

}
