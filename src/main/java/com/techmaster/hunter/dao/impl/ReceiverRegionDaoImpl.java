package com.techmaster.hunter.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.ReceiverRegionDao;
import com.techmaster.hunter.json.ConstituencyJson;
import com.techmaster.hunter.json.ConstituencyWardJson;
import com.techmaster.hunter.json.CountryJson;
import com.techmaster.hunter.json.CountyJson;
import com.techmaster.hunter.json.ReceiverRegionJson;
import com.techmaster.hunter.obj.beans.Constituency;
import com.techmaster.hunter.obj.beans.ConstituencyWard;
import com.techmaster.hunter.obj.beans.Country;
import com.techmaster.hunter.obj.beans.County;
import com.techmaster.hunter.obj.beans.ReceiverRegion;
import com.techmaster.hunter.obj.beans.State;
import com.techmaster.hunter.region.RegionCache;
import com.techmaster.hunter.region.RegionService;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterLogFactory;
import com.techmaster.hunter.util.HunterUtility;

public class ReceiverRegionDaoImpl implements ReceiverRegionDao {
	
	private static final Logger logger = HunterLogFactory.getLog(ReceiverRegionDaoImpl.class);
	@Autowired private HunterJDBCExecutor hunterJDBCExecutor;
	@Autowired private RegionService regionService;
	@Autowired private HunterHibernateHelper hunterHibernateHelper;

	
	@Override
	public void insertReceiverRegion(ReceiverRegion receiverRegion) {
		
		logger.debug("Inserting receiver region...");
		Long maxId = getNextReceiverRegionId();
		receiverRegion.setRegionId(maxId);
		HunterDaoFactory.getObject(HunterHibernateHelper.class).saveEntity(receiverRegion);
		logger.debug("Finished inserting receiver region...");
		
	}

	@Override
	public void insertReceicerRegions(List<ReceiverRegion> receiverRegions) {
		
		logger.debug("Inserting receiver region...");
		Long maxId = getNextReceiverRegionId();
		for(ReceiverRegion region : receiverRegions){
			region.setRegionId(maxId);
			maxId++;
		}
		HunterDaoFactory.getObject(HunterHibernateHelper.class).saveEntities(receiverRegions); 
		logger.debug("Finished inserting receiver region...");
		
	}

	@Override
	public ReceiverRegion getReceiverRegionById(Long receiverId) {
		
		logger.debug("Getting getReceiverRegionById...");
		ReceiverRegion receiverRegion = HunterDaoFactory.getObject(HunterHibernateHelper.class).getEntityById(receiverId, ReceiverRegion.class);
		logger.debug("Finished getReceiverRegionById");
		return receiverRegion;
	}
	
	@Override
	public List<ReceiverRegionJson> getReceiverRegionsJsonByTaskId(Long taskId) {
		List<ReceiverRegionJson>  receiverRegions = new ArrayList<>();
		String query = "SELECT tr.TSK_ID,tr.RGN_ID FROM TSK_RGNS tr WHERE tr.TSK_ID = ?";
		logger.debug("Executing query > " + query); 
		List<Object> values = new ArrayList<>();
		values.add(taskId);
		Map<Integer, List<Object>> rowMapList = hunterJDBCExecutor.executeQueryRowList(query, values);
		for(Map.Entry<Integer, List<Object>> entry : rowMapList.entrySet()){
			List<Object> data = entry.getValue();
			ReceiverRegion receiverRegion = getReceiverRegionById(HunterUtility.getLongFromObject(data.get(1)));
			ReceiverRegionJson json = regionService.creatRcvrRgnJsnFrmRcvrRgn(receiverRegion);
			int receiverCount = RegionCache.getInstance().getReceiverCountForRegion(receiverRegion);
			json.setReceiverCount(receiverCount);
			receiverRegions.add(json);
		}
		logger.debug("Obtained receiver regions > " + HunterUtility.stringifyList(receiverRegions));  
		return receiverRegions;
	}

	@Override
	public List<ReceiverRegion> getAllReceiverRegions() {

		logger.debug("Getting all states");
		List<ReceiverRegion> receiverRegions = HunterDaoFactory.getObject(HunterHibernateHelper.class).getAllEntities(ReceiverRegion.class);
		logger.debug("Finished fetching all states size(" + receiverRegions != null ? receiverRegions.size() : null +  ")");
		return receiverRegions;

	}

	@Override
	public void updateReceiverRegion(ReceiverRegion receiverRegion) {

		logger.debug("updateReceiverRegion ...");
		HunterDaoFactory.getObject(HunterHibernateHelper.class).updateEntity(receiverRegion); 
		logger.debug("Finished updateReceiverRegion!");
		
		
	}

	@Override
	public void deleteReceiverById(Long receiverId) {
		
		logger.debug("deleting receiver region of id >> " + receiverId);
		ReceiverRegion receiverRegion = HunterDaoFactory.getObject(HunterHibernateHelper.class).getEntityById(receiverId, ReceiverRegion.class);
		HunterDaoFactory.getObject(HunterHibernateHelper.class).deleteEntity(receiverRegion); 
		logger.debug("Finished deleting receiver region !"); 
		
	}

	@Override
	public void deleteReceiverRegion(ReceiverRegion receiverRegion) {
		
		logger.debug("deleting receiver region....");
		HunterDaoFactory.getObject(HunterHibernateHelper.class).deleteEntity(receiverRegion); 
		logger.debug("Finished deleting receiver region !"); 
		
	}

	@Override
	public void insertCountry(Country country) {
		logger.debug("Inserting country...");
		HunterDaoFactory.getObject(HunterHibernateHelper.class).saveEntity(country);
		logger.debug("Finished inserting country"); 
		
	}

	@Override
	public void deleteCountry(Country country) {
		logger.debug("Deleting countries");
		HunterDaoFactory.getObject(HunterHibernateHelper.class).deleteEntity(country); 
		logger.debug("Finished deleting country");
		
	}

	@Override
	public Country getCountryById(Long countryId) {
		logger.debug("Getting country by Id");
		Country country = HunterDaoFactory.getObject(HunterHibernateHelper.class).getEntityById(countryId, Country.class);
		logger.debug("Finished getting country by id");
		return country;
	}

	@Override
	public Country getCountryByName(String name) {
		String query = "FROM Country c where c.countryName = '" + name + "'";
		logger.debug("Fetcing country by name. Query >> " + query); 
		List<Country> countries = HunterDaoFactory.getObject(HunterHibernateHelper.class).executeQueryForObjList(Country.class, "");
		Country country = countries.get(0);
		logger.debug("Finished getting country by name >> " + country); 
		return country;
	}
	
	@Override
	public List<County> getCountyByNameAndCountryId(String countyName, Long countryId) {
		String query = "From County c where c.countyName = '" + countyName + "' and c.countryId = '" + countryId + "'";
		logger.debug("Executing query : " + query); 
		List<County> counties = HunterDaoFactory.getObject(HunterHibernateHelper.class).executeQueryForObjList(County.class, query);
		logger.debug("Returned counties. Size( " + counties.size() + " )"); 
		return counties;
	}

	@Override
	public List<Country> getAllCountries() {
		logger.debug("Getting all countries...");
		List<Country> countries = HunterDaoFactory.getObject(HunterHibernateHelper.class).getAllEntities(Country.class); 
		logger.debug("Finished getting countries");
		return countries;
	}
	
	@Override
	public List<CountryJson> getCountryJsonsForAllCountries() {
		logger.debug("Getting all countries countryJsons...");
		List<CountryJson> countryJsons = new ArrayList<>();
		String query = hunterJDBCExecutor.getQueryForSqlId("getCountryNameAndId"); 
		logger.debug("Obtained query >> " + query);
		Map<Integer, List<Object>> objects = hunterJDBCExecutor.executeQueryRowList(query, new ArrayList<Object>());
		for(Map.Entry<Integer, List<Object>> entry : objects.entrySet()){
			List<Object> row = entry.getValue();
			CountryJson countryJson = new CountryJson();
			countryJson.setCountryId(HunterUtility.getLongFromObject(row.get(0)));  
			countryJson.setCountryName(row.get(1).toString());  
			countryJsons.add(countryJson);
		}
		logger.debug("Successfully executed the query!!"); 
		return countryJsons;
	}

	@Override
	public void updateCountry(Country country) {
		logger.debug("Updating country...");
		hunterHibernateHelper.updateEntity(country); 
		logger.debug("Finished updating country");
		
	}

	@Override
	public void insertCounty(County county) {
		logger.debug("Inserting county...");
		hunterHibernateHelper.saveEntity(county);
		logger.debug("Finished inserting county!");
		
	}

	@Override
	public void deleteCounty(County county) {
		logger.debug("Deleting county");
		hunterHibernateHelper.deleteEntity(county); 
		logger.debug("Finished deleting county");
		
	}

	@Override
	public County getCountyById(Long countryId) {
		logger.debug("Fetching county by id...");
		County county = hunterHibernateHelper.getEntityById(countryId, County.class);
		logger.debug("Finished fetching county");
		return county;
	}

	@Override
	public List<County> getAllCounties() {
		logger.debug("Fetching all counties...");
		List<County> counties = hunterHibernateHelper.getAllEntities(County.class);
		logger.debug("Finished fetching counties size(" + counties != null ? counties.size() : null +  ")");
		return counties;
	}
	
	@Override
	public List<CountyJson> getCountyJsonsForSelCountry(String selCounty) {
		
		logger.debug("Getting all counties countryJsons...");
		List<CountyJson> countyJsons = new ArrayList<>();
		
		String query = hunterJDBCExecutor.getQueryForSqlId("getCountiesNameAndIdForSelCountry"); 
		String queryForAll = hunterJDBCExecutor.getQueryForSqlId("getCountiesNameAndIdForAll");
		
		List<Object> values = new ArrayList<>();
		if(selCounty != null && HunterUtility.isNumeric(selCounty)){
			values.add(HunterUtility.getLongFromObject(selCounty));  
		}
		
		Map<Integer, List<Object>> objects = new HashMap<Integer, List<Object>>();
		
		if(values.isEmpty()){
			objects = hunterJDBCExecutor.executeQueryRowList(queryForAll, values);
			logger.debug("Executing query >> " + queryForAll); 
		}else{
			objects = hunterJDBCExecutor.executeQueryRowList(query, values);
			logger.debug("Executing query >> " + query); 
		}
		
		logger.debug("Values for query >> " + HunterUtility.stringifyList(values)); 
		
		for(Map.Entry<Integer, List<Object>> entry : objects.entrySet()){
			List<Object> row = entry.getValue();
			CountyJson countyJson = new CountyJson();
			countyJson.setCountryId(HunterUtility.getLongFromObject(row.get(0)));
			countyJson.setCountyId(HunterUtility.getLongFromObject(row.get(1))); 
			countyJson.setCountyName(row.get(2).toString()); 
			countyJsons.add(countyJson);
		}
		logger.debug("Successfully executed the query!!"); 
		return countyJsons;
	}

	@Override
	public List<County> getAllCountiesForCountryId(String countryId) {
		logger.debug("Fetching counties for given country id [" + countryId +"]");
		String query = "From Country c where c.countryId = '" + countryId + "'";
		List<Country> countries = hunterHibernateHelper.executeQueryForObjList(Country.class, query);
		List<County> counties = new ArrayList<County>();
		if(countries != null && countries.size() > 0 )
			counties.addAll(countries.get(0).getCounties());
		logger.debug("Finished teching counties by country id. counties >> " + HunterUtility.stringifyList(counties));
		return counties;
	}

	@Override
	public void updateCounty(County county) {
		logger.debug("Upadting county");
		hunterHibernateHelper.updateEntity(county); 
		logger.debug("Finished  updating county");
	}

	@Override
	public void insertState(State state) {
		logger.debug("Inserting state...");
		hunterHibernateHelper.saveEntity(state); 
		logger.debug("Finished inserting state");
	}

	@Override
	public void deleteState(State state) {
		logger.debug("Deleting state...");
		hunterHibernateHelper.deleteEntity(state);
		logger.debug("Finished deleting state");
		
	}

	@Override
	public State getStateById(Long countryId) {
		logger.debug("Getting state by id");
		State state = hunterHibernateHelper.getEntityById(countryId, State.class);
		logger.debug("Finished getting state by id"); 
		return state;
	}

	@Override
	public List<State> getAllStates() {
		logger.debug("Getting all states");
		List<State> states = hunterHibernateHelper.getAllEntities(State.class);
		logger.debug("Finished fetching all states size(" + states != null ? states.size() : null +  ")");
		return states;
	}

	@Override
	public void updateState(State state) {
		logger.debug("Updating state");
		hunterHibernateHelper.updateEntity(state);
		logger.debug("Finished updating state");
		
	}

	@Override
	public void insertConstituency(Constituency constituency) {
		logger.debug("Inserting constituency");
		hunterHibernateHelper.saveEntity(constituency); 
		logger.debug("Finished inserting constituency");
	}

	@Override
	public void deleteConstituency(Constituency constituency) {
		logger.debug("Deleting constituency...");
		hunterHibernateHelper.deleteEntity(constituency);	
		logger.debug("Finished deleting constituency...");
	}

	@Override
	public Constituency getConstituencyById(Long id) {
		logger.debug("loading Constituency...");
		Constituency constituency = hunterHibernateHelper.getEntityById(id, Constituency.class);
		logger.debug("Successfully loaded Constituency...");
		return constituency;
	}

	@Override
	public List<ConstituencyJson> getConstituencyJsonsForSelCounty(String selCounty) {
		
		logger.debug("getting constituencies for selected country >> " + selCounty); 
		
		String query = hunterJDBCExecutor.getQueryForSqlId("getConstituenciesNameAndIdForSelCounty");
		String queryForAll = hunterJDBCExecutor.getQueryForSqlId("getConstituenciesNameAndIdForAllCounties");
		List<ConstituencyJson> jsons = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		List<Map<String, Object>> rowMaps = new ArrayList<>();
		
		if(selCounty != null && HunterUtility.isNumeric(selCounty)){
			values.add(HunterUtility.getLongFromObject(selCounty));
			logger.debug("Executing query >> " + query);
			rowMaps = hunterJDBCExecutor.executeQueryRowMap(query, values);
		}else{
			logger.debug("Executing query >> " + queryForAll);
			rowMaps = hunterJDBCExecutor.executeQueryRowMap(queryForAll, null);
		}
		
		for(Map<String, Object> rowMap : rowMaps){
			ConstituencyJson constituencyJson = new ConstituencyJson();
			constituencyJson.setCountyId(HunterUtility.getLongFromObject(rowMap.get("COUNTY_ID")));
			constituencyJson.setConstituencyId(HunterUtility.getLongFromObject(rowMap.get("CONSTITUENCY_ID")));
			constituencyJson.setConstituencyName(rowMap.get("CONSTITUENCY_NAME").toString());
			jsons.add(constituencyJson);
		}
		
		logger.debug("Successfully finished executing queries!");
		return jsons;
	}

	@Override
	public List<Constituency> getAllConstituencies() {
		logger.debug("loading constituencies...");
		List<Constituency> constituencies = hunterHibernateHelper.getAllEntities(Constituency.class);
		logger.debug("Finished fetching constituencies size(" + constituencies != null ? constituencies.size() : null +  ")");
		return constituencies;
	}
	
	@Override
	public List<Constituency> getAllConstituenciesForCountyId(String countyId) {
		logger.debug("Fetching constituencies for given county id [" + countyId +"]");
		String query = "From County c where c.countyId = '" + countyId + "'";
		List<County> counties = hunterHibernateHelper.executeQueryForObjList(County.class, query);
		List<Constituency> constituencies = new ArrayList<Constituency>();
		if(counties != null && counties.size() > 0)
			constituencies.addAll(counties.get(0).getConstituencies());
		logger.debug("Finished teching constituencies by county id. constituencies >> " + HunterUtility.stringifyList(constituencies));
		return constituencies;
	}

	@Override
	public void updateConstituency(Constituency constituency) {
		logger.debug("Updating constituency...");
		hunterHibernateHelper.updateEntity(constituency);
		logger.debug("Finished updating constituency..."); 
	}

	@Override
	public void insertConstituencyWard(ConstituencyWard constituencyWard) {
		logger.debug("Inserting constituencyWard");
		hunterHibernateHelper.saveEntity(constituencyWard); 
		logger.debug("Finished inserting constituencyWard");		
	}

	@Override
	public void deleteConstituencyWard(ConstituencyWard constituencyWard) {
		logger.debug("Deleting ConstituencyWard...");
		hunterHibernateHelper.deleteEntity(constituencyWard);	
		logger.debug("Finished deleting ConstituencyWard...");		
	}

	@Override
	public ConstituencyWard getConstituencyWardById(Long id) {
		logger.debug("Fetching ConstituencyWard...");
		ConstituencyWard constituencyWard = hunterHibernateHelper.getEntityById(id, ConstituencyWard.class);
		logger.debug("Successfully loaded ConstituencyWard...");
		return constituencyWard;
	}

	@Override
	public List<ConstituencyWard> getAllConstituencyWards() {
		logger.debug("gettingAllConstituencyWards...");
		List<ConstituencyWard> constituencyWards = hunterHibernateHelper.getAllEntities(ConstituencyWard.class);
		logger.debug("Finished gettingAllConstituencyWards size(" + constituencyWards != null ? constituencyWards.size() : null +  ")");
		return constituencyWards;
	}
	
	@Override
	public List<ConstituencyWard> getAllConstituencyWardsForConstituencyId(String constituencyId) {
		logger.debug("Fetching constituencyWards for given constituency id [" + constituencyId +"]");
		String query = "From Constituency c where c.cnsttncyId = '" + constituencyId + "'";
		List<Constituency> constituencies = hunterHibernateHelper.executeQueryForObjList(Constituency.class, query);
		List<ConstituencyWard> constituencyWards = new ArrayList<ConstituencyWard>();
		if(constituencies != null && constituencies.size() > 0)
			constituencyWards.addAll(constituencies.get(0).getConstituencyWards());
		logger.debug("Finished fetching constituencyWards by constituency id. constituencyWards >> " + HunterUtility.stringifyList(constituencyWards));
		return constituencyWards;
	}

	@Override
	public List<ConstituencyWardJson> getAllconsConstituencyWardJsonsForSelCons(String selCons) {
		
		List<ConstituencyWardJson>  constituencyWardJsons = new ArrayList<>();
		logger.debug("getting constituencies for selected country >> " + selCons); 
		
		String query = hunterJDBCExecutor.getQueryForSqlId("getConstituencyWardsForSelCons");
		String queryForAll = hunterJDBCExecutor.getQueryForSqlId("getConstituencyWardsForAll");
		List<Object> values = new ArrayList<>();
		List<Map<String, Object>> rowMaps = new ArrayList<>();
		
		if(selCons != null && HunterUtility.isNumeric(selCons)){
			values.add(HunterUtility.getLongFromObject(selCons));
			logger.debug("Executing query >> " + query);
			rowMaps = hunterJDBCExecutor.executeQueryRowMap(query, values);
		}else{
			logger.debug("Executing query >> " + queryForAll);
			rowMaps = hunterJDBCExecutor.executeQueryRowMap(queryForAll, null);
		}
		
		for(Map<String, Object> rowMap : rowMaps){
			ConstituencyWardJson constituencyWardJson = new ConstituencyWardJson();
			constituencyWardJson.setConstituencyId(HunterUtility.getLongFromObject(rowMap.get("CNSTTNCY_ID")));
			constituencyWardJson.setConstituencyWardId(HunterUtility.getLongFromObject(rowMap.get("WARD_ID")));
			constituencyWardJson.setConstituencyWardName(rowMap.get("WRD_NAME").toString());
			constituencyWardJsons.add(constituencyWardJson);
		}
		
		logger.debug("Successfully finished executing queries!");
		return constituencyWardJsons;
	}

	@Override
	public void updateConstituencyWard(ConstituencyWard constituencyWard) {
		logger.debug("Updating constituencyWard...");
		hunterHibernateHelper.updateEntity(constituencyWard);
		logger.debug("Finished updating constituencyWard..."); 	
	}

	@Override
	public Long getNextCountryId() {
		logger.debug("Fetching next id for receiver country..."); 
		Long maxId = hunterHibernateHelper.getMaxEntityIdAsNumber(Country.class, Long.class, "countryId");
		maxId =  maxId == null ? 0 : (maxId++);
		logger.debug("Successfully obtained next country id >> " + maxId);
		return maxId;
	}

	@Override
	public Long getNextCountyId() {
		logger.debug("Fetching next id for country..."); 
		Long maxId = hunterHibernateHelper.getMaxEntityIdAsNumber(Country.class, Long.class, "countryId");
		maxId =  maxId == null ? 0 : (maxId++);
		logger.debug("Successfully obtained next country id >> " + maxId);
		return maxId;
	}

	@Override
	public Long getNextConsituencyId() {
		logger.debug("gettingNextConsituencyId..."); 
		Long maxId = hunterHibernateHelper.getMaxEntityIdAsNumber(Constituency.class, Long.class, "cnsttncyId");
		maxId =  maxId == null ? 0 : (maxId++);
		logger.debug("Successfully obtained next Constituency id >> " + maxId);
		return maxId;
	}

	@Override
	public Long getNextConsituencyWardId() {
		logger.debug("Fetching next id for ConsituencyWard..."); 
		Long maxId = hunterHibernateHelper.getMaxEntityIdAsNumber(ConstituencyWard.class, Long.class, "wardId");
		maxId =  maxId == null ? 0 : (maxId++);
		logger.debug("Successfully obtained next ConsituencyWard id >> " + maxId);
		return maxId;
	}
	

	@Override
	public Long getNextReceiverRegionId() {
		logger.debug("Fetching next id for receiver ReceiverRegion..."); 
		Long maxId = hunterHibernateHelper.getMaxEntityIdAsNumber(ReceiverRegion.class, Long.class, "regionId");
		maxId =  maxId == null ? 0 : (maxId+1);
		logger.debug("Successfully obtained next ReceiverRegion id >> " + maxId);
		return maxId;
	}


	
	
	
	
	
	
	

}
