package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.obj.beans.HunterMessageReceiver;

public interface HunterMessageReceiverDao {
	
	public void insertHunterMessageReceiver(HunterMessageReceiver hunterMessageReceiver);
	public void insertHunterMessageReceivers(List<HunterMessageReceiver> hunterMessageReceiver);
	public HunterMessageReceiver getHunterMessageReceiverById(Long id);
	public List<HunterMessageReceiver> getAllHunterMessageReceivers();
	public void updateHunterMessageReceiver(HunterMessageReceiver hunterMessageReceiver);
	public Long getMaxPhoneNumberForRnadomReceiverForCountry(String countryName);
	
	public List<HunterMessageReceiver> getHunterMessageReceiversForCountry(String countryName);
	public List<HunterMessageReceiver> getHunterMessageReceiversForCounties(String countryName,List<String> countiesNames);
	public List<HunterMessageReceiver> getHunterMessageReceiversForConstituencies(String countryName, String countyName, List<String> constituenciesName);
	public List<HunterMessageReceiver> getHunterMessageReceiversForConstituencyWards(String countryName, String countyName, String constituencyName, List<String> constituencyWardsNames);
	

}
