package com.techmaster.hunter.region;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.obj.beans.Country;
import com.techmaster.hunter.obj.beans.County;
import com.techmaster.hunter.obj.beans.HunterMessageReceiver;
import com.techmaster.hunter.obj.beans.ReceiverRegion;
import com.techmaster.hunter.util.HunterUtility;

public class RegionCache {
	
	private static RegionCache instance;
	
	static{
		if(instance == null){
			synchronized (RegionCache.class) {
				instance = new RegionCache();
			}
		}
	}
	
	public static RegionCache getInstance(){
		return instance;
	}

	public Set<County> getCountiesForCountryName(String countryName){
		Set<County> counties_ = new HashSet<>();
		Set<County> counties = new HashSet<>();;
		for(Country country : HunterCacheUtil.getInstance().getAllCountries()){
			if(country.getCountryName().equals(countryName)){
				counties = country.getCounties();
				break;
			}
		}
		counties_.addAll(counties);
		return counties_;
	}
	
	public List<HunterMessageReceiver> getReceiversForWard(String countryName, String stateName,String countyName, String consName, String consWardName){
		List<HunterMessageReceiver> wardReceivers = new ArrayList<>();
		for(HunterMessageReceiver hunterMessageReceiver : HunterCacheUtil.getInstance().getAllReceivers()){
			boolean isCountryNotNullAndEqual = HunterUtility.notNullNotEmptyAndEquals(hunterMessageReceiver.getCountryName(), countryName);
			boolean isCountyNotNullAndEqual = HunterUtility.notNullNotEmptyAndEquals(hunterMessageReceiver.getCountyName(), countyName);
			boolean isConsNotNullAndEqual = HunterUtility.notNullNotEmptyAndEquals(hunterMessageReceiver.getConsName(), consName);
			boolean isConsWardNotNullAndEqual = HunterUtility.notNullNotEmptyAndEquals(hunterMessageReceiver.getConsWardName(), consWardName);
			if(isCountryNotNullAndEqual && isCountyNotNullAndEqual && isConsNotNullAndEqual && isConsWardNotNullAndEqual){
				wardReceivers.add(hunterMessageReceiver);
			}
		}
		return wardReceivers;
	}
	
	public List<HunterMessageReceiver> getReceiversForConstituency(String countryName, String stateName,String countyName, String consName){
		List<HunterMessageReceiver> wardReceivers = new ArrayList<>();
		for(HunterMessageReceiver hunterMessageReceiver : HunterCacheUtil.getInstance().getAllReceivers()){
			boolean isCountryNotNullAndEqual = HunterUtility.notNullNotEmptyAndEquals(hunterMessageReceiver.getCountryName(), countryName);
			boolean isCountyNotNullAndEqual = HunterUtility.notNullNotEmptyAndEquals(hunterMessageReceiver.getCountyName(), countyName);
			boolean isConsNotNullAndEqual = HunterUtility.notNullNotEmptyAndEquals(hunterMessageReceiver.getConsName(), consName);
			if(isCountryNotNullAndEqual && isCountyNotNullAndEqual && isConsNotNullAndEqual){
				wardReceivers.add(hunterMessageReceiver);
			}
		}
		return wardReceivers;
	}
	
	public List<HunterMessageReceiver> getReceiversForCounty(String countryName, String stateName,String countyName){
		List<HunterMessageReceiver> wardReceivers = new ArrayList<>();
		for(HunterMessageReceiver hunterMessageReceiver : HunterCacheUtil.getInstance().getAllReceivers()){
			boolean isCountryNotNullAndEqual = HunterUtility.notNullNotEmptyAndEquals(hunterMessageReceiver.getCountryName(), countryName);
			boolean isCountyNotNullAndEqual = HunterUtility.notNullNotEmptyAndEquals(hunterMessageReceiver.getCountyName(), countyName);
			if(isCountryNotNullAndEqual && isCountyNotNullAndEqual){
				wardReceivers.add(hunterMessageReceiver);
			}
		}
		return wardReceivers;
	}
	
	public List<HunterMessageReceiver> getReceiversForCountry(String countryNam){
		List<HunterMessageReceiver> wardReceivers = new ArrayList<>();
		for(HunterMessageReceiver hunterMessageReceiver : HunterCacheUtil.getInstance().getAllReceivers()){
			boolean isCountryNotNullAndEqual = HunterUtility.notNullNotEmptyAndEquals(hunterMessageReceiver.getCountryName(), countryNam);
			if(isCountryNotNullAndEqual){
				wardReceivers.add(hunterMessageReceiver);
			}
		}
		return wardReceivers;
	}
	
	public int getReceiverCountForRegion(ReceiverRegion receiverRegion){
		List<HunterMessageReceiver> receivers = getReceiversForReceiverRegion(receiverRegion);
		return receivers.size();
	}
	
	public List<HunterMessageReceiver> getReceiversForReceiverRegion(ReceiverRegion receiverRegion){
		List<HunterMessageReceiver> receivers = new ArrayList<>();
		if(receiverRegion.getCountry() != null && receiverRegion.getCounty() != null && receiverRegion.getConstituency() != null && receiverRegion.getWard() != null){
			receivers = getReceiversForWard(receiverRegion.getCountry(), receiverRegion.getState() ,receiverRegion.getCounty(), receiverRegion.getConstituency(), receiverRegion.getWard());
			return receivers;
		}else if(receiverRegion.getCountry() != null && receiverRegion.getCounty() != null && receiverRegion.getConstituency() != null && receiverRegion.getWard() == null){
			receivers = getReceiversForConstituency(receiverRegion.getCountry(), receiverRegion.getState() ,receiverRegion.getCounty(), receiverRegion.getConstituency());
			return receivers;
		}else if(receiverRegion.getCountry() != null && receiverRegion.getCounty() != null && receiverRegion.getConstituency() == null && receiverRegion.getWard() == null){
			receivers = getReceiversForCounty(receiverRegion.getCountry(), receiverRegion.getState() ,receiverRegion.getCounty());
			return receivers;
		}else if(receiverRegion.getCountry() != null && receiverRegion.getCounty() == null && receiverRegion.getConstituency() == null && receiverRegion.getWard() == null){
			receivers = getReceiversForCountry(receiverRegion.getCountry());
			return receivers;
		}
		return receivers;
	}
	
	public Set<County> getCountiesForCountryId(Long countryId){
		Set<County> counties_ = new HashSet<>();
		Set<County> counties = new HashSet<>();;
		for(Country country : HunterCacheUtil.getInstance().getAllCountries()){
			if(country.getCountryId().equals(countryId)){
				counties = country.getCounties();
				break;
			}
		}
		counties_.addAll(counties);
		return counties_;
	}

	public List<Country> getCountries(){
		List<Country> copy = new ArrayList<>();
		copy.addAll(HunterCacheUtil.getInstance().getAllCountries());
		return copy;
	}
	
}
