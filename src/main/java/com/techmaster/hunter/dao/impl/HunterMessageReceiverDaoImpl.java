package com.techmaster.hunter.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.proc.ProcedureHandler;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.HunterMessageReceiverDao;
import com.techmaster.hunter.obj.beans.HunterMessageReceiver;
import com.techmaster.hunter.region.RegionService;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterSessionFactory;
import com.techmaster.hunter.util.HunterUtility;

public class HunterMessageReceiverDaoImpl implements HunterMessageReceiverDao{
	
	private static final Logger logger = Logger.getLogger(HunterMessageReceiverDaoImpl.class);
	@Autowired private HunterJDBCExecutor hunterJDBCExecutor;
	@Autowired private ProcedureHandler GET_RGN_LVL_NMS_FOR_CNTIES;
	@Autowired private RegionService regionService;
	@Autowired private HunterHibernateHelper hunterHibernateHelper;
	@Autowired private HunterSessionFactory hunterSessionFactory;

	@Override
	public void insertHunterMessageReceiver( HunterMessageReceiver hunterMessageReceiver) {
		logger.debug("Inserting receiver...");
		hunterHibernateHelper.saveEntity(hunterMessageReceiver);
		logger.debug("Finished inserting receiver!"); 
	}

	@Override
	public void insertHunterMessageReceivers(List<HunterMessageReceiver> hunterMessageReceivers) {
		logger.debug("Inserting list of receivers...");
		hunterHibernateHelper.saveEntities(hunterMessageReceivers);
		logger.debug("Finished inserting receivers!!"); 
	}

	@Override
	public HunterMessageReceiver getHunterMessageReceiverById(Long id) {
		logger.debug("Getting receiver by id ( " + id + " )"); 
		HunterMessageReceiver hunterMessageReceiver = hunterHibernateHelper.getEntityById(id, HunterMessageReceiver.class);
		logger.debug("Finished getting receiver by id >> " + hunterMessageReceiver);
		return hunterMessageReceiver;
		
	}

	@Override
	public List<HunterMessageReceiver> getAllHunterMessageReceivers() {
		logger.debug("Pulling up all message receivers...");
		List<HunterMessageReceiver> hunterMessageReceivers = hunterHibernateHelper.getAllEntities(HunterMessageReceiver.class);
		logger.debug("Finished getting all hunter message receivers size (" + hunterMessageReceivers.size() + " )");
		return hunterMessageReceivers;
	}

	@Override
	public void updateHunterMessageReceiver(HunterMessageReceiver hunterMessageReceiver) {
		logger.debug("Updating hunter message receiver >> " + hunterMessageReceiver);
		hunterHibernateHelper.updateEntity(hunterMessageReceiver);
		logger.debug("Finished updating hunterMessageReceiver!"); 
	}

	@Override
	public Long getMaxPhoneNumberForRnadomReceiverForCountry(String countryName) {
		logger.debug("getting Max Phone Number For Random Receiver..."); 
		String query = hunterJDBCExecutor.getQueryForSqlId("getMaxPhoneNumberForRandomReceiverInACountry");
		logger.debug("Executing query >> " + query); 
		List<Object> values = new ArrayList<Object>(1);
		values.add(countryName);
		List<Map<String, Object>> numbers = hunterJDBCExecutor.executeQueryRowMap(query, values);
		if(numbers != null && numbers.size() > 1){
			logger.warn("Max numbers returned is wrong. It should return only one row!!"); 
		}
		Long maxPhoneNum = 0L;
		if(!numbers.isEmpty()){
			Map<String, Object> rowMap = numbers.get(0);
			for(Map.Entry<String, Object> entry : rowMap.entrySet()){
				Long value = entry.getValue() == null ? 0 : HunterUtility.getLongFromObject(entry.getValue());
				logger.debug("Obtained the max phone number >> " + value); 
				maxPhoneNum = value;
			}
		}
		return maxPhoneNum;
	}

	@Override
	public List<HunterMessageReceiver> getHunterMessageReceiversForCountry(String countryName) {
		List<HunterMessageReceiver>  hunterMessageReceivers = new ArrayList<>();
		if(countryName == null || countryName.trim().equals("")){
			logger.warn("Country passed in is null!! Returning empty arrayList"); 
			return hunterMessageReceivers;
		}
		String query = "FROM HunterMessageReceiver h WHERE h.countryName = '" + countryName + "'";
		logger.debug("Executing query : " + query); 
		hunterMessageReceivers = hunterHibernateHelper.executeQueryForObjList(HunterMessageReceiver.class, query);
		logger.debug("Successfully obtained receivers for country : " + countryName); 
		return hunterMessageReceivers;
	}

	@Override
	public List<HunterMessageReceiver> getHunterMessageReceiversForCounties(String countryName, List<String> countiesNames) {
		
		List<HunterMessageReceiver>  hunterMessageReceivers = new ArrayList<>();
		
		if(countryName == null || countryName.trim().equals("")){
			logger.warn("Country passed in is null!! Returning empty arrayList"); 
			return hunterMessageReceivers;
		}
		logger.debug("Fetching hunterReceivers for counties : " + HunterUtility.stringifyList(countiesNames));
		
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<countiesNames.size();i++){
			String county = countiesNames.get(i);
			builder.append(county.trim());
			if(i <= countiesNames.size() - 2){
				builder.append(",");
			}
		}
		logger.debug("Counties names >> " + builder.toString()); 
		
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("COUNTRY_NAM", countryName);
		inParams.put("CMM_SPRTD_CNTIS", builder.toString());
		Map<String, Object> results = GET_RGN_LVL_NMS_FOR_CNTIES.execute_(inParams);
		String comsSprtddRgns = results.get("OUTPUT_REGION_NAME") != null ? results.get("OUTPUT_REGION_NAME").toString()  : null;
		logger.debug("Obtained the comma separated regions : " + comsSprtddRgns);
		logger.debug(HunterUtility.stringifyMap(results));
		
		List<String> regionsUnderCounties = Arrays.asList(comsSprtddRgns == null ? new String[]{} : comsSprtddRgns.split(",")); 
		String quoted = "";
		builder = new StringBuilder();
		for(int i=0; i<regionsUnderCounties.size();i++){
			String inParam = regionsUnderCounties.get(i);
			builder.append(HunterUtility.singleQuote(inParam));
			builder.append(",");
		}
		quoted = builder.substring(0, builder.length() - 1);
		logger.debug("Quoted values >> " + quoted); 
		String query = "FROM HunterMessageReceiver h WHERE h.countryName = '" + countryName + "' AND h.receiverRegionLevel != 'Country' AND h.receiverRegionLevelName IN (" + quoted + ")" ;
		logger.debug("Executing query : " + query); 
		
		SessionFactory sessionFactory = hunterSessionFactory.getSessionFactory();
		Session session = null;
		
		try {
			
			session = sessionFactory.openSession();
			Query q = session.createQuery(query);
		    List<?> list = q.list();
		    for(Object obj : list){
		    	HunterMessageReceiver hunterMessageReceiver = (HunterMessageReceiver)obj;
		    	hunterMessageReceivers.add(hunterMessageReceiver);
		    }
		    
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		logger.debug("Finally finished returning the values :  " + HunterUtility.stringifyList(hunterMessageReceivers) );
		
		return hunterMessageReceivers;
	}

	@Override
	public List<HunterMessageReceiver> getHunterMessageReceiversForConstituencies( String countryName, String countyName, List<String> constituenciesNames) {
		
		List<Map<String, Object>> rowMaps = regionService.getNmsAndIdsFrRgnNmsFrCnsttncs(countryName, countyName, constituenciesNames);
		StringBuilder builder = new StringBuilder();
		
		for(Map<String, Object> rowMap : rowMaps){
			String ward = rowMap.get("WRD_NAM").toString();
			ward = HunterUtility.singleQuote(ward);
			builder.append(ward).append(",");
		}
		
		String quotedWards = builder.toString();
		
		logger.debug("Returning receiver regions names under constituencies : " + quotedWards); 
		logger.debug("Quoted wards : " + quotedWards); 
		String quotedConsStr = HunterUtility.getCommaSeparatedSingleQuoteStrForList(constituenciesNames);
		quotedWards = quotedWards.concat(quotedConsStr);
		
		if(quotedWards.endsWith(",")){
			quotedWards = quotedWards.substring(0, quotedWards.length() - 1);
		}
		
		
		String hunterMessageQuery = "FROM HunterMessageReceiver h WHERE h.countryName = '" + countryName + "' AND h.receiverRegionLevel NOT IN ( 'Country', 'County' ) AND h.receiverRegionLevelName IN (" + quotedWards + ")" ;
		List<HunterMessageReceiver> hunterMessageReceivers = hunterHibernateHelper.executeQueryForObjList(HunterMessageReceiver.class, hunterMessageQuery);
		
		return hunterMessageReceivers;
		
	}

	@Override
	public List<HunterMessageReceiver> getHunterMessageReceiversForConstituencyWards(String countryName, String countyName, String constituencyName, List<String> constituencyWardsNames) {
		
		logger.debug("Pulling hunter message receivers for  countryName ( " + countryName + " ), countyName ( " + countyName + " ), constituencyName ( " + constituencyName + " ) and wards : " + HunterUtility.stringifyList(constituencyWardsNames)); 
		
		List<Map<String, Object>> rowMaps = regionService.getNmsAndIdsFrRgnNmsFrCnsttncs(countryName, countyName, Arrays.asList(new String[]{constituencyName}));
		List<String> validConstituencyWardsNames = new ArrayList<>();
		
		for(String wardName : constituencyWardsNames){
			boolean wardFound = false;
			for(Map<String, Object> rowMap : rowMaps){
				String ward = rowMap.get("WRD_NAM").toString();
				if(ward.equals(wardName)){
					wardFound = true;
					validConstituencyWardsNames.add(ward);
				}
			}
			if(!wardFound){
				logger.warn("Warning!! Ward provided ( " + wardName + " ) does not exist in countryName ( " + countryName + " ), countyName ( " + countyName + " ), constituencyName ( " + constituencyName + " ) ");
			}
		}
		
		String quotedWards = HunterUtility.getCommaSeparatedSingleQuoteStrForList(validConstituencyWardsNames);
		logger.debug("Quoted wards : " + quotedWards); 
		
		String query = hunterJDBCExecutor.getQueryForSqlId("getHntrMsgRcvrsFrConsWrdNames");
		Map<String, Object> values = new HashMap<>();
		values.put(":countryName", HunterUtility.singleQuote(countryName)); 
		values.put(":countyName", HunterUtility.singleQuote(countyName));
		values.put(":consName", HunterUtility.singleQuote(constituencyName));
		values.put(":consWardName", quotedWards);
		values.put(":receiverType", HunterUtility.singleQuote(HunterConstants.RECEIVER_TYPE_TEXT));
		values.put(":active", "true"); 
		
		List<HunterMessageReceiver>  hunterMessageReceivers = hunterHibernateHelper.replaceQueryAndExecuteForList(HunterMessageReceiver.class, query, values);
		
		
		return hunterMessageReceivers;
	}
	
	

}
