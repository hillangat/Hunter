package com.techmaster.hunter.rawreceivers;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.techmaster.hunter.json.HunterRawReceiverJson;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.HunterMessageReceiver;
import com.techmaster.hunter.obj.beans.HunterRawReceiver;
import com.techmaster.hunter.obj.beans.HunterRawReceiverUser;

public interface RawReceiverService {
	
	public HunterRawReceiverUser registerRawReceiverUser(HunterRawReceiverUser rawReceiverUser);
	public List<HunterRawReceiver> getPayableRawReceivers(HunterRawReceiverUser hunterRawReceiverUser);
	public List<HunterRawReceiver> getAllRawReceiversForUser(HunterRawReceiverUser hunterRawReceiverUser);
	public int getRawReceiverVersion (String givenByUserName);
	public List<HunterRawReceiver> getDefaultRawReceivers();
	public Map<String, String> validateRawReceiver(HunterRawReceiver hunterRawReceiver);
	public Map<String,String> payRawReceiverUser(HunterRawReceiverUser rawReceiverUser);
	public HunterRawReceiver createRawReceiver(Map<String,String> params, AuditInfo auditInfo);
	public HunterRawReceiver updateRawReceiver(Map<String,String> params, AuditInfo auditInfo);
	public void setRegionIdsForReceiver(HunterRawReceiver hunterRawReceiver);
	public JSONObject createOrUpdateRawReceiver(Map<String,String> inParams, AuditInfo auditInfo);
	public void updateRawReceiverCountsForUser(String userName);
	public String getBase64PhotoForUser(String userName);
	public List<String> getDistinctContactsForUser(String userName);
	public List<HunterRawReceiverJson> getRawReceiverJsonForDbMap(List<Map<String,Object>> rowMapList);
	public List<HunterMessageReceiver> createHntrMsgReceiversForRawReceivers( String rawReceiverIds, AuditInfo auditInfo );
	
	
}
