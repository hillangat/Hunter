package com.techmaster.hunter.region;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.techmaster.hunter.json.PagedHunterMessageReceiverJson;
import com.techmaster.hunter.json.ReceiverRegionJson;
import com.techmaster.hunter.obj.beans.ReceiverGroupReceiver;
import com.techmaster.hunter.obj.beans.ReceiverRegion;



public interface RegionService {

	public void populateRandomReceiversToCountry(Long countryId, int countryCount);
	public void populateRandomReceiversToState(Long stateId, int stateCount);
	public void populateRandomReceiversToCounty(Long countyId, int countyCount);
	public void populateRandomReceiversToConstituency(Long constituencyId, int constituencyCount);
	public void populateRandomReceiversToConstituencyWard(Long constituencyWardId, int constituencyWardCount);
	
	public Map<String, Object> getNamesAndIdsForRegionsNamesForWard(String countryName, String countyName, String constituencyName, String constituencyWardName);
	public Map<String, Object> getNamesAndIdsForRegionsNamesForConstituency(String countryName, String countyName, String constituencyName);
	public Map<String, Object> getNamesAndIdsForRegionsNamesForCounty(String countryName, String countyName);
	public List<Map<String, Object>> getNmsAndIdsFrRgnNmsFrCnsttncs(String countryName, String countyName, List<String> constituencies);
	
	public List<Long> getRegionsIdForNames(String country, String county, String constituency, String ward);
	public String addRegionToTask(Long taskId, String country, String county, String constituency, String ward);
	public String removeTaskRegion(Long taskId, Long taskRegionId);
	public void removeAllRegionsForTask(Long taskId);
	public boolean isRegionAlreadyAddedToTask(Long regionId, Long taskId);
	
	public ReceiverRegionJson creatRcvrRgnJsnFrmRcvrRgn(ReceiverRegion receiverRegion);
	public Object[] getTrueHntrMsgRcvrCntFrTaskRgns(Long taskId);
	
	public List<String> editReceiverRegion(Map<String,Object> params, String userName);
	
	public JSONArray getCountriesNameAndIds(String countryName);
	public JSONArray getCountiesNameAndIds(String countryName);
	public JSONArray getConsNameAndIds(String countryName,String countyName);
	public JSONArray getConsWardNameAndIds(String countryName,String countyName, String consName);
	
	public List<ReceiverGroupReceiver> getReceiversForGroup(Long groupId, String taskMsgType);
	public List<PagedHunterMessageReceiverJson> getMessageReceiversForRegion(String cntryNam,String cntyNam, String consName, String wardName, int pageNumber, int pageCount,String rcvrTyp);
	
	
	
}

