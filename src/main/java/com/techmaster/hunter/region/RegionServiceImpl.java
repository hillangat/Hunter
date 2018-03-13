package com.techmaster.hunter.region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.HunterMessageReceiverDao;
import com.techmaster.hunter.dao.types.HunterUserDao;
import com.techmaster.hunter.dao.types.ReceiverRegionDao;
import com.techmaster.hunter.dao.types.UserRoleDao;
import com.techmaster.hunter.enums.HunterUserRolesEnums;
import com.techmaster.hunter.json.PagedHunterMessageReceiverJson;
import com.techmaster.hunter.json.ReceiverRegionJson;
import com.techmaster.hunter.obj.beans.Constituency;
import com.techmaster.hunter.obj.beans.ConstituencyWard;
import com.techmaster.hunter.obj.beans.Country;
import com.techmaster.hunter.obj.beans.County;
import com.techmaster.hunter.obj.beans.HunterJacksonMapper;
import com.techmaster.hunter.obj.beans.HunterMessageReceiver;
import com.techmaster.hunter.obj.beans.HunterUser;
import com.techmaster.hunter.obj.beans.ReceiverGroupReceiver;
import com.techmaster.hunter.obj.beans.ReceiverRegion;
import com.techmaster.hunter.obj.beans.State;
import com.techmaster.hunter.obj.beans.UserRole;
import com.techmaster.hunter.task.TaskManager;
import com.techmaster.hunter.util.HunterLogFactory;
import com.techmaster.hunter.util.HunterUtility;

public class RegionServiceImpl extends AbstractRegionService {
	
	@Autowired private ReceiverRegionDao receiverRegionDao;
	@Autowired private HunterJacksonMapper hunterJacksonMapper;
	@Autowired private HunterMessageReceiverDao hunterMessageReceiverDao;
	@Autowired private HunterJDBCExecutor hunterJDBCExecutor;
	
	private static final Logger logger = HunterLogFactory.getLog(RegionServiceImpl.class);

	@Override
	public void populateRandomReceiversToCountry(Long countryId, int countryCount) {
		
		Country country = receiverRegionDao.getCountryById(countryId);
		
		if(country == null){
			logger.error("Could not populate country. No country found for countryId >> " + countryId);
			return;
		}
		
		logger.debug("Populating random receivers for country >> " + country);
		
		hunterMessageReceiverDao = hunterMessageReceiverDao == null ? hunterMessageReceiverDao = HunterDaoFactory.getDaoObject(HunterMessageReceiverDao.class) : hunterMessageReceiverDao;
		
		Long maxPhoneNum = hunterMessageReceiverDao.getMaxPhoneNumberForRnadomReceiverForCountry(country.getCountryName());
		String code = country.getCountryCode();
		
		if(maxPhoneNum == null){
			maxPhoneNum = HunterUtility.getLongFromObject(code + "700000001");
			logger.debug("No phone number seed found for the country. Generated a seed >> " + maxPhoneNum); 
		}else{
			maxPhoneNum++;
		}
		
		List<HunterMessageReceiver> hunterMessageReceivers = new ArrayList<HunterMessageReceiver>(countryCount);
		for(int i=0; i< countryCount; i++){
			HunterMessageReceiver hunterMessageReceiver = new HunterMessageReceiver();
			hunterMessageReceiver.setActive(true);
			hunterMessageReceiver.setAuditInfo(HunterUtility.getAuditInforForNow(new Date(), "hlangat01", new Date(), "hlangat01"));
			hunterMessageReceiver.setBlocked(false);
			hunterMessageReceiver.setCountryName(country.getCountryName()); 
			hunterMessageReceiver.setReceiverRegionLevelName(country.getCountryName()); 
			hunterMessageReceiver.setFailDeliveryTimes(0);
			hunterMessageReceiver.setReceiverContact(Long.toString(maxPhoneNum)); 
			maxPhoneNum++;
			hunterMessageReceiver.setReceiverRegionLevel(HunterConstants.RECEIVER_LEVEL_COUNTY); 
			hunterMessageReceiver.setReceiverType(HunterConstants.RECEIVER_TYPE_TEXT);
			hunterMessageReceivers.add(hunterMessageReceiver);
		}
		
		logger.debug("Successfully finished generating random receivers for country ( " + country.getCountryName() + " ) \n " + HunterUtility.stringifyList(hunterMessageReceivers));
		hunterMessageReceiverDao.insertHunterMessageReceivers(hunterMessageReceivers); 
		
	}

	@Override
	public void populateRandomReceiversToState(Long stateId, int stateCount) {
		
		State state = receiverRegionDao.getStateById(stateId);
		
		if(state == null){
			logger.error("Could not populate state. No state found for stateId >> " + stateId);
			return;
		}
		
		Country country = receiverRegionDao.getCountryById(state.getCountryId());
		logger.debug("State belongs to country >> " + country.getCountryName()); 
		
		logger.debug("Populating random receivers for state >> " + state);
		Long maxPhoneNum = hunterMessageReceiverDao.getMaxPhoneNumberForRnadomReceiverForCountry(state.getStateName());
		String code = country.getCountryCode();
		
		if(maxPhoneNum == null){
			maxPhoneNum = HunterUtility.getLongFromObject(code + "700000001");
			logger.debug("No phone number seed found for the country. Generated a seed >> " + maxPhoneNum); 
		}else{
			maxPhoneNum++;
		}
		
		List<HunterMessageReceiver> hunterMessageReceivers = new ArrayList<HunterMessageReceiver>(stateCount);
		
		for(int i=0; i< stateCount; i++){
			HunterMessageReceiver hunterMessageReceiver = new HunterMessageReceiver();
			hunterMessageReceiver.setActive(true);
			hunterMessageReceiver.setAuditInfo(HunterUtility.getAuditInforForNow(new Date(), "hlangat01", new Date(), "hlangat01"));
			hunterMessageReceiver.setBlocked(false);
			hunterMessageReceiver.setCountryName(country.getCountryName());
			hunterMessageReceiver.setFailDeliveryTimes(0);
			hunterMessageReceiver.setReceiverContact(Long.toString(maxPhoneNum));
			hunterMessageReceiver.setReceiverRegionLevelName(state.getStateName());
			maxPhoneNum++;
			hunterMessageReceiver.setReceiverRegionLevel(HunterConstants.RECEIVER_LEVEL_STATE); 
			hunterMessageReceiver.setReceiverType(HunterConstants.RECEIVER_TYPE_TEXT); 
			hunterMessageReceivers.add(hunterMessageReceiver);
		}
		
		logger.debug("Successfully finished generating random receivers for state ( " + state.getStateName() + " ) \n " + HunterUtility.stringifyList(hunterMessageReceivers));
	}

	@Override
	public void populateRandomReceiversToCounty(Long countyId, int countyCount) {
		
		String getCntryCntyCodeNameId = hunterJDBCExecutor.getQueryForSqlId("getCntryCntyCodeNameId");
		List<Object> values = new ArrayList<Object>();
		values.add(countyId);
		List<Map<String, Object>> cnsValues = hunterJDBCExecutor.executeQueryRowMap(getCntryCntyCodeNameId, values);
		Map<String, Object> rowMap = cnsValues.get(0);
		logger.debug(HunterUtility.stringifyMap(rowMap));
		
		if(rowMap == null || rowMap.isEmpty()){
			logger.error("Could not populate county. No state found for countyId >> " + countyId);
			return;
		}
		
		logger.debug("Populating random receivers for county >> " + rowMap.get("CNTY_NAM")); 
		Long maxPhoneNum = hunterMessageReceiverDao.getMaxPhoneNumberForRnadomReceiverForCountry(rowMap.get("COUNTRYNAME").toString());
		
		String code = rowMap.get("COUNTYCODE") != null ? rowMap.get("COUNTYCODE").toString() : null;
		
		if(code == null){
			code = rowMap.get("COUNTRYCODE") != null ? rowMap.get("COUNTRYCODE").toString() : null;
			logger.warn("County code is not set for county. Using country code to generate phone numbers >> country code ( " + code + " )");
		}
		
		if(maxPhoneNum == null || maxPhoneNum == 0){
			maxPhoneNum = HunterUtility.getLongFromObject(code + "700000001");
			logger.debug("No phone number seed found for the country. Generated a seed >> " + maxPhoneNum); 
		}else{
			maxPhoneNum++;
		}
		
		List<HunterMessageReceiver> hunterMessageReceivers = new ArrayList<HunterMessageReceiver>(countyCount);
		
		for(int i=0; i< countyCount; i++){
			HunterMessageReceiver hunterMessageReceiver = new HunterMessageReceiver();
			hunterMessageReceiver.setActive(true);
			hunterMessageReceiver.setAuditInfo(HunterUtility.getAuditInforForNow(new Date(), "admin", new Date(), "admin"));
			hunterMessageReceiver.setBlocked(false);
			hunterMessageReceiver.setCountryName(rowMap.get("COUNTRYNAME").toString());
			hunterMessageReceiver.setCountyName(rowMap.get("COUNTYNAME").toString());
			hunterMessageReceiver.setFailDeliveryTimes(0);
			hunterMessageReceiver.setReceiverContact(Long.toString(maxPhoneNum)); 
			hunterMessageReceiver.setReceiverRegionLevelName(rowMap.get("COUNTYNAME").toString()); 
			maxPhoneNum++;
			hunterMessageReceiver.setReceiverRegionLevel(HunterConstants.RECEIVER_LEVEL_COUNTY); 
			hunterMessageReceiver.setReceiverType(HunterConstants.RECEIVER_TYPE_TEXT); 
			hunterMessageReceivers.add(hunterMessageReceiver);
		}
		
		hunterMessageReceiverDao.insertHunterMessageReceivers(hunterMessageReceivers); 
		
		logger.debug("Successfully finished generating random receivers for county ( " + rowMap.get("CNTY_NAM") + " ) \n " + HunterUtility.stringifyList(hunterMessageReceivers));
		
	}

	@Override
	public void populateRandomReceiversToConstituency(Long constituencyId,int constituencyCount) {
		
		String getCntryNmIdAndCdeCntyIdNmAndCdeFrCnsttncyId = hunterJDBCExecutor.getQueryForSqlId("getCntryNmIdAndCdeCntyIdNmAndCdeFrCnsttncyId");
		List<Object> values = new ArrayList<Object>();
		values.add(constituencyId);
		List<Map<String, Object>> cnsValues = hunterJDBCExecutor.executeQueryRowMap(getCntryNmIdAndCdeCntyIdNmAndCdeFrCnsttncyId, values);
		Map<String, Object> rowMap = cnsValues.get(0);
		logger.debug(HunterUtility.stringifyMap(rowMap)); 
		
		Constituency constituency = receiverRegionDao.getConstituencyById(constituencyId);
		
		if(constituency == null){
			logger.error("Could not populate constituency. No state found for constituencyId >> " + constituencyId);
			return;
		}
		
		County county = receiverRegionDao.getCountyById(constituency.getCountyId()); 
		logger.debug("State belongs to county >> " + county.getCountyName()); 
		
		logger.debug("Populating random receivers for state >> " + county);
		Long maxPhoneNum = hunterMessageReceiverDao.getMaxPhoneNumberForRnadomReceiverForCountry(rowMap.get("CNTRY_NAM").toString());
		
		if(maxPhoneNum == null){
			maxPhoneNum = HunterUtility.getLongFromObject(rowMap.get("CNTRY_CODE") + "700000001"); 
			logger.debug("No phone number seed found for the country. Generated a seed >> " + maxPhoneNum); 
		}else{
			maxPhoneNum++;
		}
		
		List<HunterMessageReceiver> hunterMessageReceivers = new ArrayList<HunterMessageReceiver>(constituencyCount);
		
		for(int i=0; i< constituencyCount; i++){
			HunterMessageReceiver hunterMessageReceiver = new HunterMessageReceiver();
			hunterMessageReceiver.setActive(true);
			hunterMessageReceiver.setAuditInfo(HunterUtility.getAuditInforForNow(new Date(), "hlangat01", new Date(), "hlangat01"));
			hunterMessageReceiver.setBlocked(false);
			hunterMessageReceiver.setCountryName(rowMap.get("CNTRY_NAM").toString());
			hunterMessageReceiver.setCountyName(rowMap.get("CNTY_NAM").toString());
			hunterMessageReceiver.setConsName(rowMap.get("CNSTTNCY_NAM").toString());
			hunterMessageReceiver.setFailDeliveryTimes(0);
			hunterMessageReceiver.setReceiverContact(Long.toString(maxPhoneNum)); 
			hunterMessageReceiver.setReceiverRegionLevelName(rowMap.get("CNSTTNCY_NAM").toString()); 
			maxPhoneNum++;
			hunterMessageReceiver.setReceiverRegionLevel(HunterConstants.RECEIVER_LEVEL_STATE); 
			hunterMessageReceiver.setReceiverType(HunterConstants.RECEIVER_TYPE_TEXT); 
			hunterMessageReceivers.add(hunterMessageReceiver);
		}
		
		logger.debug("Successfully finished generating random receivers for country ( " + rowMap.get("CNTRY_NAM").toString() + " ) \n " + HunterUtility.stringifyList(hunterMessageReceivers));
		
	}

	@Override
	public void populateRandomReceiversToConstituencyWard(Long constituencyWardId, int constituencyWardCount) {
		
		String getCntryCntyConstyConswardIdCodeNameId = hunterJDBCExecutor.getQueryForSqlId("getCntryCntyConstyConswardIdCodeNameId");
		List<Object> values = new ArrayList<Object>();
		values.add(constituencyWardId);
		List<Map<String, Object>> cnsValues = hunterJDBCExecutor.executeQueryRowMap(getCntryCntyConstyConswardIdCodeNameId, values);
		Map<String, Object> rowMap = cnsValues.get(0);
		logger.debug(HunterUtility.stringifyMap(rowMap)); 
		
		
		if(rowMap == null || rowMap.isEmpty()){
			logger.error("Could not populate constituency. No state found for constituencyId >> " + constituencyWardId);
			return;
		}
		
		logger.debug("State belongs to constituency >> " + rowMap.get("CNSTTNCY_NAM")); 
		
		logger.debug("Populating random receivers for constituency ward >> " + rowMap.get("WRD_NAM"));
		Long maxPhoneNum = hunterMessageReceiverDao.getMaxPhoneNumberForRnadomReceiverForCountry(rowMap.get("CNTRY_NAM").toString());
		
		if(maxPhoneNum == null){
			maxPhoneNum = HunterUtility.getLongFromObject(rowMap.get("CNTRY_CODE") + "700000001"); 
			logger.debug("No phone number seed found for the country. Generated a seed >> " + maxPhoneNum); 
		}else{
			maxPhoneNum++;
		}
		
		List<HunterMessageReceiver> hunterMessageReceivers = new ArrayList<HunterMessageReceiver>(constituencyWardCount);
		
		for(int i=0; i< constituencyWardCount; i++){
			HunterMessageReceiver hunterMessageReceiver = new HunterMessageReceiver();
			hunterMessageReceiver.setActive(true);
			hunterMessageReceiver.setAuditInfo(HunterUtility.getAuditInforForNow(new Date(), "hlangat01", new Date(), "hlangat01"));
			hunterMessageReceiver.setBlocked(false);
			hunterMessageReceiver.setCountryName(rowMap.get("CNTRY_NAM").toString());
			hunterMessageReceiver.setCountyName(rowMap.get("CNTY_NAM").toString());
			hunterMessageReceiver.setConsName(rowMap.get("CNSTTNCY_NAM").toString());
			hunterMessageReceiver.setConsWardName(rowMap.get("WRD_NAM").toString());
			hunterMessageReceiver.setFailDeliveryTimes(0);
			hunterMessageReceiver.setReceiverContact(Long.toString(maxPhoneNum)); 
			maxPhoneNum++;
			hunterMessageReceiver.setReceiverRegionLevel(HunterConstants.RECEIVER_LEVEL_WARD); 
			hunterMessageReceiver.setReceiverRegionLevelName(rowMap.get("WRD_NAM").toString()); 
			hunterMessageReceiver.setReceiverType(HunterConstants.RECEIVER_TYPE_TEXT); 
			hunterMessageReceivers.add(hunterMessageReceiver);
		}
		
		logger.debug("Successfully finished generating random receivers for constituency ward ( " + rowMap.get("WRD_NAM") + " ) \n " + HunterUtility.stringifyList(hunterMessageReceivers));
		hunterMessageReceiverDao.insertHunterMessageReceivers(hunterMessageReceivers); 
		
	}

	@Override
	public Map<String, Object> getNamesAndIdsForRegionsNamesForWard( String countryName, String countyName, String constituencyName, String constituencyWardName) {
		logger.debug("Getting names and id for regions names for ward..."); 
		String query = hunterJDBCExecutor.getQueryForSqlId("getRegionIdsForNamesToWard");
		List<Object> values = new ArrayList<Object>();
		values.add(countryName);
		values.add(countyName);
		values.add(constituencyName);
		values.add(constituencyWardName);
		logger.debug("Executing query >> " + query); 
		logger.debug("With the values >> " + HunterUtility.stringifyList(values)); 
		List<Map<String, Object>> rowMaps = hunterJDBCExecutor.executeQueryRowMap(query, values);
		if(rowMaps.size() > 1){
			logger.error("Data expected to be only one row. Found multiple rows!! Please check the data!!");
		}
		logger.debug("Obtained the values : "); 
		for(Map<String, Object> rowMap : rowMaps){
			logger.debug(HunterUtility.stringifyMap(rowMap));  
		}
		return rowMaps.get(0);
	}

	@Override
	public Map<String, Object> getNamesAndIdsForRegionsNamesForConstituency(String countryName, String countyName, String constituencyName) {
		logger.debug("Getting names and id for regions names for constituency..."); 
		String query = hunterJDBCExecutor.getQueryForSqlId("getRegionIdsForNamesToCons");
		List<Object> values = new ArrayList<Object>();
		values.add(countryName);
		values.add(countyName);
		values.add(constituencyName);
		logger.debug("Executing query >> " + query); 
		logger.debug("With the values >> " + HunterUtility.stringifyList(values)); 
		List<Map<String, Object>> rowMaps = hunterJDBCExecutor.executeQueryRowMap(query, values);
		if(rowMaps.size() > 1){
			logger.error("Data expected to be only one row. Found multiple rows!! Please check the data!!");
		}
		logger.debug("Obtained the values : "); 
		for(Map<String, Object> rowMap : rowMaps){
			logger.debug(HunterUtility.stringifyMap(rowMap));  
		}
		return rowMaps.get(0);
	}

	@Override
	public Map<String, Object> getNamesAndIdsForRegionsNamesForCounty(String countryName, String countyName) {
		logger.debug("Getting names and id for regions names for county..."); 
		String query = hunterJDBCExecutor.getQueryForSqlId("getRegionIdsForNamesToCounty");
		List<Object> values = new ArrayList<Object>();
		values.add(countryName);
		values.add(countyName);
		logger.debug("Executing query >> " + query); 
		logger.debug("With the values >> " + HunterUtility.stringifyList(values)); 
		List<Map<String, Object>> rowMaps = hunterJDBCExecutor.executeQueryRowMap(query, values);
		if(rowMaps.size() > 1){
			logger.error("Data expected to be only one row. Found multiple rows!! Please check the data!!");
		}
		logger.debug("Obtained the values : "); 
		for(Map<String, Object> rowMap : rowMaps){
			logger.debug(HunterUtility.stringifyMap(rowMap));  
		}
		return rowMaps.get(0);
	}

	@Override
	public List<Map<String, Object>> getNmsAndIdsFrRgnNmsFrCnsttncs(String countryName, String countyName,List<String> constituencies) {
		
		logger.debug("Getting names and Id for regions names under given constituencies.."); 
		String quotedStr = HunterUtility.getCommaSeparatedSingleQuoteStrForList(constituencies);
		logger.debug("Converted constituencies to string >> " + quotedStr);
		String query = hunterJDBCExecutor.getQueryForSqlId("getRgnLvlIdsAndNamesForConstituencies");
		logger.debug("Using query : " + query);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(":countryName", HunterUtility.singleQuote(countyName)); 
		params.put(":constituencyNames", quotedStr);
		params.put(":coutryName", HunterUtility.singleQuote(countryName)); 
		
		List<Map<String, Object>> rowMaps = hunterJDBCExecutor.replaceAndExecuteQuery(query, params);
		return rowMaps;
	}

	@Override
	public ReceiverRegionJson creatRcvrRgnJsnFrmRcvrRgn(ReceiverRegion receiverRegion) {
		ReceiverRegionJson regionJson = hunterJacksonMapper.convertValue(receiverRegion, ReceiverRegionJson.class);
		logger.debug(regionJson);
		return regionJson;
	}
	
	private static String[] getChildren(String levelStr, Set<String> levels,String type ){
		String[] realChildren = null;
		for(String level : levels){
			if(level.startsWith(levelStr)){
				String[] children = level.split("::");
				int len = children.length;
				logger.debug("Children NO : " + len);
				if(type.equals(HunterConstants.RECEIVER_LEVEL_COUNTRY) && len > 1){
					realChildren = HunterUtility.initArrayAndInsert(realChildren, level);
				}else if(type.equals(HunterConstants.RECEIVER_LEVEL_COUNTY) && len > 2 ){
					realChildren = HunterUtility.initArrayAndInsert(realChildren, level);
				}else if(type.equals(HunterConstants.RECEIVER_LEVEL_CONSITUENCY) && len > 3 ){
					return children;
				}else if(type.equals(HunterConstants.RECEIVER_LEVEL_WARD) && len > 4 ){
					realChildren = HunterUtility.initArrayAndInsert(realChildren, level);
				}
			}
		}
		logger.debug("Found the children : " + HunterUtility.getCommaDelimitedStrings(realChildren)); 
		return realChildren;
	}
	
	private static void removeChildren(String[] children, Set<String> levels){
		for(String child : children){
			boolean removeChild = false;
			String chldRemov = null;
			for(String level : levels){
				if(child.equals(level)){
					removeChild = true;
					chldRemov = level;
					break;
				}
			}
			if(removeChild && chldRemov != null){
				levels.remove(chldRemov);
			}
		}
	}

	@Override
	public Object[] getTrueHntrMsgRcvrCntFrTaskRgns(Long taskId) {
		
		List<ReceiverRegionJson> receiverJsons = receiverRegionDao.getReceiverRegionsJsonByTaskId(taskId);
		Set<String> levels = new HashSet<>();

		for(ReceiverRegionJson json : receiverJsons){
			
			String country = json.getCountry();
			String county = json.getCounty();
			String cons = json.getConstituency();
			String ward = json.getWard();

			String cntryLevel = country;
			String cntyLevel = country + "::" + county;
			String consLevel = cntyLevel + "::" + cons;
			String wardLevel = consLevel + "::" + ward;
			
			boolean isCntryLevel = country != null && county == null && cons == null && ward == null;
			if(isCntryLevel && !levels.contains(cntryLevel)){ 
				levels.add(cntryLevel);
				json.setCurrentLevel(HunterConstants.RECEIVER_LEVEL_COUNTRY);
			}
			
			boolean isCntyLevel = county != null && cons == null && ward == null;
			if(isCntyLevel && !levels.contains(cntyLevel)){ 
				levels.add(cntyLevel);
				json.setCurrentLevel(HunterConstants.RECEIVER_LEVEL_COUNTY);
			}
			
			boolean isConsLevel = cons != null && ward == null;
			if(isConsLevel && !levels.contains(consLevel)){ 
				levels.add(consLevel);
				json.setCurrentLevel(HunterConstants.RECEIVER_LEVEL_CONSITUENCY);
			}
			
			boolean isWardLevel = ward != null;
			if(isWardLevel && !levels.contains(wardLevel)){ 
				levels.add(wardLevel);
				json.setCurrentLevel(HunterConstants.RECEIVER_LEVEL_WARD);
			}
			
			// If a region parent is there already, remove the children.
			
			boolean remove = false;
			String removeStr = null;
			
			if(isCntryLevel){
				
				Iterator<String> levelsItr = levels.iterator();
				String[] cntyChildren = null;
				
				while (levelsItr.hasNext()) {
				    String curLevel = levelsItr.next();
				    if( curLevel.startsWith(cntryLevel) && !curLevel.equals(cntryLevel) ){ 
				    	remove = true;
				    	removeStr = cntryLevel;
				    }else if(curLevel.startsWith(cntryLevel) && curLevel.equals(cntryLevel)){
				    	cntyChildren = getChildren(curLevel, levels, HunterConstants.RECEIVER_LEVEL_COUNTRY);
				    }
				}
				
				if(cntyChildren != null && cntyChildren.length > 0){
					removeChildren(cntyChildren, levels); 
				}
				
				if(remove){
					levels.remove(removeStr);
					remove = false;
				}
			}else if(isCntyLevel){
				
				Iterator<String> levelsItr = levels.iterator();
				String[] cntyChildren = null;
				
				while (levelsItr.hasNext()) {
				    String curLevel = levelsItr.next();
				    if( curLevel.startsWith(cntyLevel) && !curLevel.equals(cntyLevel) ){
				    	if(levels.contains(cntryLevel)){ 
				    		remove = true;
					    	removeStr = cntyLevel;
				    	}
				    }else if(curLevel.startsWith(cntyLevel) && curLevel.equals(cntyLevel)){
				    	cntyChildren = getChildren(curLevel, levels, HunterConstants.RECEIVER_LEVEL_COUNTY);
				    }
				}
				
				if(cntyChildren != null && cntyChildren.length > 0){
					removeChildren(cntyChildren, levels); 
				}
				
				if(remove){
					levels.remove(removeStr);
					remove = false;
				}
				
			}else if(isConsLevel){
				
				Iterator<String> levelsItr = levels.iterator();
				String[] cntyChildren = null;
				
				while (levelsItr.hasNext()) {
				    String curLevel = levelsItr.next();
				    if( curLevel.startsWith(consLevel) && !curLevel.equals(consLevel) ){ 
				    	if(levels.contains(cntyLevel)){
				    		remove = true;
					    	removeStr = consLevel;
				    	}
				    }else if(curLevel.startsWith(consLevel) && curLevel.equals(cntyLevel)){
				    	cntyChildren = getChildren(curLevel, levels, HunterConstants.RECEIVER_LEVEL_CONSITUENCY);
				    }
				}
				
				if(cntyChildren != null && cntyChildren.length > 0){
					removeChildren(cntyChildren, levels); 
				}
				
				if(remove){
					levels.remove(removeStr);
					remove = false;
				}
			}else if(isWardLevel){
				Iterator<String> levelsItr = levels.iterator();
				while (levelsItr.hasNext()) {
				    String curLevel = levelsItr.next();
				    if( curLevel.startsWith(consLevel) && !curLevel.equals(wardLevel) ){ 
				    	if(levels.contains(consLevel) || levels.contains(cntyLevel) || levels.contains(cntryLevel)){
				    		remove = true;
					    	removeStr = wardLevel;
				    	}
				    }
				}
				if(remove){
					logger.debug("Removing level > " + removeStr); 
					levels.remove(removeStr);
					remove = false;
				}
			}
			
		}
		
		logger.debug("Final count of unique regions : " + levels.size());
		int uniqueCount = 0;
		Set<String> dstnctPrcsdLvls = new HashSet<>();
		List<ReceiverRegionJson> countedRegionsJs = new ArrayList<>();
		
		for(ReceiverRegionJson receiverJson : receiverJsons){
			
			String jsonLevel = null;
			
			if(receiverJson.getCurrentLevel().equals(HunterConstants.RECEIVER_LEVEL_COUNTRY)){
				jsonLevel = receiverJson.getCountry();
			}else if(receiverJson.getCurrentLevel().equals(HunterConstants.RECEIVER_LEVEL_COUNTY)){
				jsonLevel = receiverJson.getCountry() + "::" + receiverJson.getCounty();
			}else if(receiverJson.getCurrentLevel().equals(HunterConstants.RECEIVER_LEVEL_CONSITUENCY)){
				jsonLevel = receiverJson.getCountry() + "::" + receiverJson.getCounty() + "::" + receiverJson.getConstituency();
			}else if(receiverJson.getCurrentLevel().equals(HunterConstants.RECEIVER_LEVEL_WARD)){
				jsonLevel = receiverJson.getCountry() + "::" + receiverJson.getCounty() + "::" + receiverJson.getConstituency() + "::" + receiverJson.getWard();
			}
			
			for(String level : levels){
				if(jsonLevel.equals(level)){
					if(!dstnctPrcsdLvls.contains(level)){
						uniqueCount += receiverJson.getReceiverCount();
						dstnctPrcsdLvls.add(level);
						countedRegionsJs.add(receiverJson);
					}
					break;
				}
			}
		}
		
		logger.debug("returnign totals count > " + uniqueCount);
		
		return new Object[]{uniqueCount, countedRegionsJs};
	}

	@Override
	public String addRegionToTask(Long taskId, String country, String county, String constituency, String ward) {
		
		String query = null;
		List<Object> values = new ArrayList<>();
		
		if(country != null && county == null && constituency == null && ward == null){
			query = "SELECT rgn.RGN_ID  FROM RCVR_RGN rgn WHERE rgn.CNTRY = ? and rgn.CNTY IS NULL and rgn.CNSTTNCY IS NULL and rgn.WRD IS NULL";
			values.add(country);
		}else if(country != null && county != null && constituency == null && ward == null){
			query = "SELECT rgn.RGN_ID  FROM RCVR_RGN rgn WHERE rgn.CNTRY = ? and rgn.CNTY = ? and rgn.CNSTTNCY IS NULL and rgn.WRD IS NULL";
			values.add(country);
			values.add(county);
		}else if(country != null && county != null && constituency != null && ward == null){
			query = "SELECT rgn.RGN_ID  FROM RCVR_RGN rgn WHERE rgn.CNTRY = ? and rgn.CNTY = ? and rgn.CNSTTNCY = ? and rgn.WRD IS NULL";
			values.add(country);
			values.add(county);
			values.add(constituency);
		}else if(country != null && county != null && constituency != null && ward != null){
			query = "SELECT rgn.RGN_ID  FROM RCVR_RGN rgn WHERE rgn.CNTRY = ? and rgn.CNTY = ? and rgn.CNSTTNCY = ? and rgn.WRD = ?";
			values.add(country);
			values.add(county);
			values.add(constituency);
			values.add(ward);
		}
		
		logger.debug("Executing query : " + query); 
		
		Map<Integer, List<Object>>  rowMapList = hunterJDBCExecutor.executeQueryRowList(query, values);
		if(rowMapList.size() > 1){
			logger.warn("Query returns more than one row!!!!! Using the first row..!");
		}else if(rowMapList.isEmpty()){
			logger.debug("No such region found!!! Returning...");
			return "No such region is found!" ;
		}
		
		Long regionId = null;
		
		for(Map.Entry<Integer, List<Object>> entry : rowMapList.entrySet()){
			List<Object> idList = entry.getValue();
			regionId = HunterUtility.getLongFromObject(idList.get(0)); 
			break;
		}
		
		if(isRegionAlreadyAddedToTask(regionId, taskId)){
			logger.debug("Region is already added to task!! Will not attemp to add again! Returning...");
			return "Region is already added!";
		}
		
		String insert = "INSERT INTO TSK_RGNS (TSK_ID,RGN_ID) VALUES (:TSK_ID,:RGN_ID)";
		
		Map<String, Object> params = new HashMap<>();
		params.put(":TSK_ID", taskId);
		params.put(":RGN_ID", regionId);
		
		// there should be a method in the executor for replacing and inserting.
		hunterJDBCExecutor.replaceAndExecuteUpdate(insert, params); 
		return null;
		
	}
	
	

	@Override
	public boolean isRegionAlreadyAddedToTask(Long regionId, Long taskId) {
		
		String checkQuery = "SELECT COUNT(*) as CNT FROM TSK_RGNS tr WHERE tr.TSK_ID = ? AND tr.RGN_ID = ?";
		List<Object> values = new ArrayList<>();
		values.clear();
		values.add(taskId);
		values.add(regionId);
		
		Map<Integer, List<Object>>  cRowMapList = hunterJDBCExecutor.executeQueryRowList(checkQuery, values);
		int count = new Integer(cRowMapList.get(1).get(0) == null ? 0 : Integer.parseInt(cRowMapList.get(1).get(0)+""));  
		
		if(count >= 1){
			logger.debug("Region is already added to task!! Will not attemp to add again! Returning...");  
		}
	
		return count >= 1;
	}

	@Override
	public String removeTaskRegion(Long taskId, Long taskRegionId) {
		
		try{
		
			String existQuery = hunterJDBCExecutor.getQueryForSqlId("checkIfRegionAndTaskExist");
			Map<Integer, List<Object>> rowMapList = hunterJDBCExecutor.executeQueryRowList(existQuery, hunterJDBCExecutor.getValuesList(new Object[]{taskId, taskRegionId, taskId, taskRegionId}));
			List<Object> rowList = rowMapList.get(1);
			StringBuilder builder = new StringBuilder();
			
			for(int i=0; i<rowList.size(); i++){
				Object obj = rowList.get(i);
				if(i == 0){
					int tskCount = obj != null ? Integer.parseInt(obj+"") : 0;
					if(tskCount == 0){
						builder.append("No task of id( " + taskId + " ) found!");
					}
				}else if(i==1){
					int regionCount = obj != null ? Integer.parseInt(obj+"") : 0;
					if(regionCount == 0){
						builder.append(", No region of id( " + taskRegionId + " ) found!");
					}
				}else if(i==2){
					int taskRgnCount = obj != null ? Integer.parseInt(obj+"") : 0;
					if(taskRgnCount == 0){
						builder.append(" , No task region comvination of task id( " + taskRegionId + " ) and region id ("+ taskRegionId + " ) found!");
					}
				}
				
			}
			
			String errMsg = builder.toString();
			errMsg = errMsg.startsWith(",") ? errMsg.substring(0, errMsg.length() - 1) : errMsg;
			
			if(errMsg.length() > 0){
				logger.debug("Error while removing task region from task : " + errMsg); 
				return errMsg;
			}
			
			String query = "DELETE FROM TSK_RGNS tr WHERE tr.TSK_ID = :TSK_ID AND tr.RGN_ID = :RGN_ID";
			Map<String, Object> params = new HashMap<>();
			params.put(":TSK_ID", taskId);
			params.put(":RGN_ID", taskRegionId);
			hunterJDBCExecutor.replaceAndExecuteUpdate(query, params);
			logger.debug("Successfully removed region from taskregions!!"); 
		
		}catch(Exception e){
			return e.getMessage();
		}
		
		return null;
	}

	
	@Override
	public void removeAllRegionsForTask(Long taskId) {
		String query = "DELETE FROM TSK_RGNS tr WHERE tr.TSK_ID = :TSK_ID ";
		Map<String, Object> params = new HashMap<>();
		params.put(":TSK_ID", taskId);
		hunterJDBCExecutor.replaceAndExecuteUpdate(query, params);
		logger.debug("Successfully removed region from taskregions!!");
	}

	@Override
	public List<Long> getRegionsIdForNames(String country, String county, String constituency, String ward) {
		
		 List<Long> regionIds = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		String query = null;
		
		// I am doing this because the hunter jdbc preparepared statement is not working well for null values!!
		
		if(ward != null){
			values.add(country);
			values.add(county);
			values.add(constituency);
			values.add(ward);
			query = "SELECT rgn.RGN_ID  FROM RCVR_RGN rgn WHERE rgn.CNTRY = ? and rgn.CNTY = ? and rgn.CNSTTNCY = ? and rgn.WRD = ?";
		}else if(constituency != null && ward == null){
			values.add(country);
			values.add(county);
			values.add(constituency);
			query = "SELECT rgn.RGN_ID  FROM RCVR_RGN rgn WHERE rgn.CNTRY = ? and rgn.CNTY = ? and rgn.CNSTTNCY = ? and rgn.WRD IS NULL";
		}else if(county != null && constituency == null && ward == null){
			values.add(country);
			values.add(county);
			query = "SELECT rgn.RGN_ID  FROM RCVR_RGN rgn WHERE rgn.CNTRY = ? and rgn.CNTY = ? and rgn.CNSTTNCY IS NULL  and rgn.WRD IS NULL";
		}else if(country != null && county != null && constituency == null && ward == null){
			values.add(country);
			query = "SELECT rgn.RGN_ID  FROM RCVR_RGN rgn WHERE rgn.CNTRY = ? and rgn.CNTY IS NULL and rgn.CNSTTNCY IS NULL and rgn.WRD IS NULL";
		}
		
		logger.debug("Executing query : " + query); 
		Map<Integer, List<Object>>  rowMapList = hunterJDBCExecutor.executeQueryRowList(query, values);
		
		if(rowMapList.isEmpty()){
			logger.debug("No such region found!!! Returning empty list...");
			return regionIds;
		}
		
		for(Map.Entry<Integer, List<Object>> entry : rowMapList.entrySet()){
			List<Object> idList = entry.getValue();
			Long regionId = HunterUtility.getLongFromObject(idList.get(0)); 
			regionIds.add(regionId);
		}
		
		return regionIds;
	}

	@Override
	public List<String> editReceiverRegion(Map<String, Object> params, String userName) {
		
		String type = HunterUtility.getStringOrNullOfObj(params.get("levelType"));
		logger.debug("Upadting "+ type +" with params ( " + HunterUtility.stringifyMap(params) + " )");
		Long beanId = Long.parseLong(params.get("beanId")+""); 
		int population = Integer.parseInt(params.get("population")+"");
		int hunterPopulation = Integer.parseInt(params.get("hunterPopuplation")+"");
		String regionCode = HunterUtility.getStringOrNullOfObj(params.get("regionCode")); 
		String regionName = HunterUtility.getStringOrNullOfObj(params.get("regionName")); 
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
		List<Object> values = new ArrayList<>();
		
		List<String> errors = new ArrayList<>();
		errors.add("You do not admin role required for this action.");
		TaskManager taskManager = HunterDaoFactory.getDaoObject(TaskManager.class);
		
		if ( !taskManager.userHasRole(HunterUserRolesEnums.ROLE_ADMIN.getName(), userName) ) {
			return errors;
		}
		
		errors.clear();
		
		if(HunterConstants.RECEIVER_LEVEL_COUNTRY.equals(type)){ 
			
			Country country = receiverRegionDao.getCountryById(beanId);
			
			String query = "UPDATE RCVR_RGN SET CNTRY = ? WHERE CNTRY = ?"; 
			logger.debug("Executing query : " + query);
			values.add(regionName);
			values.add(country.getCountryName());
			hunterJDBCExecutor.executeUpdate(query, values);
			
			country.setCountryName(regionName);
			country.setCountryPopulation(population);
			country.setHunterPopulation(hunterPopulation);
			country.setCountryCode(regionCode);
			receiverRegionDao.updateCountry(country);
			
		}else if(HunterConstants.RECEIVER_LEVEL_COUNTY.equals(type)){
			
			County county = receiverRegionDao.getCountyById(beanId);
			Country country = receiverRegionDao.getCountryById(county.getCountryId());
			
			String query = "UPDATE RCVR_RGN SET CNTY = ? WHERE CNTY = ? AND CNTRY = ?";
			values.add(regionName);
			values.add(county.getCountyName());
			values.add(country.getCountryName());
			logger.debug("Executing query : " + query);
			hunterJDBCExecutor.executeUpdate(query, values); 
			
			county.setCountyName(regionName);
			county.setCountyPopulation(population);
			county.setHunterPopulation(hunterPopulation);
			county.setCountyCode(regionCode);
			receiverRegionDao.updateCounty(county);
			
		}else if(HunterConstants.RECEIVER_LEVEL_CONSITUENCY.equals(type)){
			
			Constituency constituency = receiverRegionDao.getConstituencyById(beanId);
			County county = receiverRegionDao.getCountyById(constituency.getCountyId());
			Country country = receiverRegionDao.getCountryById(county.getCountryId());
			
			String query = "UPDATE RCVR_RGN SET CNSTTNCY = ? WHERE CNSTTNCY = ? AND CNTY = ? AND CNTRY = ?";
			values.add(regionName);
			values.add(constituency.getCnsttncyName());
			values.add(county.getCountyName());
			values.add(country.getCountryName());
			logger.debug("Executing query : " + query);
			hunterJDBCExecutor.executeUpdate(query, values); 
			
			constituency.setCnsttncyName(regionName);
			constituency.setCnsttncyPopulation(population); 
			constituency.setHunterPopulation(hunterPopulation);
			constituency.setConstituencyCode(regionCode);
			receiverRegionDao.updateConstituency(constituency);
			
		}else if(HunterConstants.RECEIVER_LEVEL_WARD.equals(type)){

			ConstituencyWard ward = receiverRegionDao.getConstituencyWardById(beanId);
			Constituency constituency = receiverRegionDao.getConstituencyById(ward.getConstituencyId());
			County county = receiverRegionDao.getCountyById(constituency.getCountyId());
			Country country = receiverRegionDao.getCountryById(county.getCountryId());

			String query = "UPDATE RCVR_RGN SET WRD = ? WHERE WRD = ? AND CNSTTNCY = ? AND CNTY = ? AND CNTRY = ?";
			values.add(regionName);
			values.add(ward.getWardName());
			values.add(constituency.getCnsttncyName());
			values.add(county.getCountyName());
			values.add(country.getCountryName());
			
			logger.debug("Executing query : " + query);
			hunterJDBCExecutor.executeUpdate(query, values);
			
			ward.setWardName(regionName);
			ward.setWardPopulation(population); 
			ward.setHunterPopulation(hunterPopulation);
			ward.setConstituencyWardCode(regionCode);
			receiverRegionDao.updateConstituencyWard(ward); 
			
		}
		logger.debug("Completed updating " + type + ". Refreshing cache..."); 
		HunterCacheUtil.getInstance().loadCountries();
		return Collections.emptyList();
	}

	@Override
	public JSONArray getCountriesNameAndIds(String countryName) {
		JSONArray ja = new JSONArray();
		List<Country> countries = HunterCacheUtil.getInstance().getAllCountries();
		for(Country country : countries){
			JSONObject jo = new JSONObject();
			jo.put("countryId", country.getCountryId());
			jo.put("countryName", country.getCountryName());
			ja.put(jo);
		}
		System.out.println(ja); 
		return ja;
		
	}

	@Override
	public JSONArray getCountiesNameAndIds(String countryName) {
		JSONArray ja = new JSONArray();
		Map<Long,String> counties = HunterCacheUtil.getInstance().getCountiesMapForCountry(countryName);
		for(Map.Entry<Long, String> entry : counties.entrySet()){
			JSONObject jo = new JSONObject();
			jo.put("countyId", entry.getKey());
			jo.put("countyName", entry.getValue());
			ja.put(jo);
		}
		return ja;
	}

	@Override
	public JSONArray getConsNameAndIds(String countryName, String countyName) {
		JSONArray ja = new JSONArray();
		Map<Long,String> counties = HunterCacheUtil.getInstance().getConstituenciesMapForCounty(countryName, countyName);
		for(Map.Entry<Long, String> entry : counties.entrySet()){
			JSONObject jo = new JSONObject();
			jo.put("consId", entry.getKey());
			jo.put("consName", entry.getValue());
			ja.put(jo);
		}
		return ja;
	}

	@Override
	public JSONArray getConsWardNameAndIds(String countryName,String countyName, String consName) {
		JSONArray ja = new JSONArray();
		Map<Long,String> counties = HunterCacheUtil.getInstance().getConsWardsMapForCounty(countryName, countyName, consName);
		for(Map.Entry<Long, String> entry : counties.entrySet()){
			JSONObject jo = new JSONObject();
			jo.put("consWardId", entry.getKey());
			jo.put("consWardame", entry.getValue());
			ja.put(jo);
		}
		return ja;
	}

	@Override
	public List<ReceiverGroupReceiver> getReceiversForGroup(Long groupId,String taskMsgType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PagedHunterMessageReceiverJson> getMessageReceiversForRegion(String cntryNam, String cntyNam, String consName, String wardName, int pageNumber, int pageCount,String rcvrTyp) {
		
		pageCount = pageCount == 0 ? 100 : pageCount;
		
		List<PagedHunterMessageReceiverJson> pageHunterMessageReceiverJsons = new ArrayList<>();
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
		String query = hunterJDBCExecutor.getQueryForSqlId("getPagedHunterMsgReceiversForRegions");
		
		int index1 = query.indexOf("|:");
	    int index2 = query.indexOf(":|");
	    String key = query.substring( index1+2, index2  );
	    String replaced = "|:" + key + ":|";
	    
	    Map<String,Object> params = new HashMap<>();
		params.put(":page_num", pageNumber);
		params.put(":page_count", pageCount);
		params.put("=:rcvrTyp", rcvrTyp == null ? " IS NOT NULL " : "=" + HunterUtility.singleQuote( rcvrTyp )); 
		String whrCls = "";
	    
	    if( wardName != null && consName != null && cntyNam != null && cntryNam != null ){
	    	whrCls = key + "." + "CNTRY_NAM =:CNTRY_NAM AND " + key + "." + "CNTY_NAM =:CNTY_NAM AND " + key + "." + "CONS_NAM =:CONS_NAM AND " + key + "." + "WRD_NAM =:WRD_NAM";
	    	query = query.replace( replaced, whrCls );
	    	params.put(":CNTRY_NAM", HunterUtility.singleQuote(cntryNam));
			params.put(":CNTY_NAM", HunterUtility.singleQuote(cntyNam));
			params.put(":CONS_NAM", HunterUtility.singleQuote(consName));
			params.put(":WRD_NAM", HunterUtility.singleQuote(wardName));
	    }else if( wardName == null && consName != null && cntyNam != null && cntryNam != null ){
	    	whrCls = key + "." + "CNTRY_NAM =:CNTRY_NAM AND " + key + "." + "CNTY_NAM =:CNTY_NAM AND " + key + "." + "CONS_NAM =:CONS_NAM";
	    	query = query.replace( replaced, whrCls );
	    	params.put(":CNTRY_NAM", HunterUtility.singleQuote(cntryNam));
			params.put(":CNTY_NAM", HunterUtility.singleQuote(cntyNam));
			params.put(":CONS_NAM", HunterUtility.singleQuote(consName));
	    }else if( wardName == null && consName == null && cntyNam != null && cntryNam != null ){
	    	whrCls = key + "." + "CNTRY_NAM =:CNTRY_NAM AND " + key + "." + "CNTY_NAM =:CNTY_NAM";
	    	query = query.replace( replaced, whrCls );
	    	params.put(":CNTRY_NAM", HunterUtility.singleQuote(cntryNam));
			params.put(":CNTY_NAM", HunterUtility.singleQuote(cntyNam));
	    }else if( wardName == null && consName == null && cntyNam == null && cntryNam != null ){
	    	whrCls = key + "." + "CNTRY_NAM =:CNTRY_NAM";
	    	query = query.replace( replaced, whrCls );
	    	params.put(":CNTRY_NAM", HunterUtility.singleQuote(cntryNam));
	    }
	    
	    logger.debug( whrCls );
	    
	    whrCls = whrCls.replaceAll(key, "r2");
	    logger.debug( whrCls ); 
	    
	    replaced = "|:r2:|";
	    query = query.replace( replaced, whrCls );
	    
	    logger.debug("Processed query : " + query); 
		
		query = hunterJDBCExecutor.replaceAllColonedParams(query, params);
		List<Map<String, Object>>  rowMapList = hunterJDBCExecutor.executeQueryRowMap(query, null);
		
		for( Map<String, Object> rowMap: rowMapList ){
			PagedHunterMessageReceiverJson receiverJson = new PagedHunterMessageReceiverJson();
			receiverJson.setContact( HunterUtility.getStringOrNullOfObj( rowMap.get("RCVR_CNTCT") ) ); 
			receiverJson.setIndex( Integer.valueOf( HunterUtility.getStringOrNullOfObj( rowMap.get("ROW_NUM") ) ) );
			receiverJson.setCount( Integer.valueOf( HunterUtility.getStringOrNullOfObj( rowMap.get("CNT") ) ) );
			pageHunterMessageReceiverJsons.add(receiverJson); 
		}
		
		logger.debug( HunterUtility.stringifyList( pageHunterMessageReceiverJsons ) );  
		
		return pageHunterMessageReceiverJsons;
	}
	
	
	
	
}
