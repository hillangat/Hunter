package com.techmaster.hunter.angular.imports._2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.obj.beans.Constituency;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterUtility;

public class VoterCountImport2017 implements ImportHelper {
	
	private static final String FILE_NAME = "import\\voter_count_2017\\cons_voter_count.json";
	private static final String QUERY = "SELECT cn.CNSTTNCY_ID, c.CNTY_NAM, cn.CNSTTNCY_NAM FROM CNTY c, CNSTTNCY cn WHERE cn.CNTY_ID = c.CNTY_ID AND c.CNTY_NAM || '_' ||  cn.CNSTTNCY_NAM IN ( :REGION_NAMES )";
	private static final String REGION_CODES = "regionCodes";
	private static final String VOTER_COUNT = "voterCounts";
	private static final String REGION_NAMES = "regionNames";
	private static final String CONS_IDS = "consIds";

	@Override
	public Object read(String fileName) {
		return HunterUtility.getClassPathFileJsonArray(fileName);
	}

	@Override
	public Object process(Object obj) {
		JSONArray jsonArray = (JSONArray)obj;
		List<String> regionNames = new ArrayList<>();
		Map<String, String> regionCodes = new HashMap<>();
		Map<String, Integer> voterCounts = new HashMap<>();
		for( int i = 1; i < jsonArray.length() - 1; i++ ) {
			JSONObject row = jsonArray.getJSONObject(i);
			String regionCode = HunterUtility.getStringOrNullFromJSONObj(row, "FIELD1");
			String countyName = normalizeName(HunterUtility.getStringOrNullFromJSONObj(row, "FIELD2"));
			String consName = normalizeName(HunterUtility.getStringOrNullFromJSONObj(row, "FIELD3"));
			String voterCountStr = HunterUtility.getStringOrNullFromJSONObj(row, "FIELD4").replaceAll(",", "");
			String names = countyName + "_" + consName;
			regionCodes.put(names, regionCode);
			voterCounts.put(names, Integer.parseInt(voterCountStr));
			regionNames.add( names );
		}
		Map<Long, String> consIds = getConsIdsForNames(regionNames);
		Map<String, Object> valMap = new HashMap<>();
		valMap.put(REGION_CODES, regionCodes);
		valMap.put(VOTER_COUNT, voterCounts);
		valMap.put(REGION_NAMES, regionNames);
		valMap.put(CONS_IDS, consIds);
		return valMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object save(Object obj) {
		List<Constituency> constituencies = (List<Constituency>)obj;
		HunterDaoFactory.getDaoObject(HunterHibernateHelper.class).saveOrUpdateEntities(constituencies);
		return constituencies;
	}

	@SuppressWarnings({ "unchecked", "unlikely-arg-type", "unused" })
	@Override
	public Object execute(Object obj) {
		JSONArray jsonArray = (JSONArray)read(FILE_NAME);
		Map<Long, Object> processMap = (Map<Long, Object>)process(jsonArray);
		List<String> regionNames = (List<String>)(processMap.get(REGION_NAMES));
		Map<String, String> regionCodes = (Map<String, String>)(processMap.get(REGION_CODES));
		Map<String, Integer> voterCounts = (Map<String, Integer>)(processMap.get(VOTER_COUNT));
		Map<Long, String> consIds = (Map<Long, String>)(processMap.get(CONS_IDS));
		
		List<Long> consIdList = new ArrayList<>();
		consIds.forEach( (k, v) -> consIdList.add(k) );
		
		List<Constituency> constituencies = getConsForIds(consIdList);
		constituencies.stream().forEach(c -> System.out.println( "Before >> " + c.getCnsttncyName() + ": " + c.getCnsttncyPopulation() + ", code = " + c.getConstituencyCode() ));
		constituencies.forEach( c -> c.setHunterPopulation( 0 ) );
		constituencies.forEach( c -> c.setCnsttncyPopulation( voterCounts.get( consIds.get(c.getCnsttncyId()))));
		constituencies.forEach( c -> c.setConstituencyCode( regionCodes.get( consIds.get(c.getCnsttncyId()))));
		constituencies.stream().forEach(c -> System.out.println( "After >> " + c.getCnsttncyName() + ": " + c.getCnsttncyPopulation() + ", code = " + c.getConstituencyCode() ));
		
		return save( constituencies );
	}
	
	private Map<Long, String> getConsIdsForNames( List<String> regionsNames ) {
		Map<Long, String> consIds = new HashMap<>();
		HunterJDBCExecutor executor = HunterDaoFactory.getDaoObject(HunterJDBCExecutor.class);
		List<String> singleQuotedList = regionsNames.stream().map( name -> HunterUtility.singleQuote(name )).collect(Collectors.toList());
		Map<String, Object> params = new HashMap<>();
		params.put(":REGION_NAMES", HunterUtility.getCommaDelimitedStrings(singleQuotedList));
		String finalQuery = executor.replaceAllColonedParams(QUERY, params);
		Map<Integer, List<Object>> listMap = executor.executeQueryRowList(finalQuery, null );
		listMap.forEach( (k, val) -> consIds.put(HunterUtility.getLongFromObject(val.get(0)), val.get(1) + "_" + val.get(2) ) );
		return consIds;
	}
	
	private List<Constituency> getConsForIds( List<Long> consIds ) {
		String query = "FROM Constituency c WHERE c.cnsttncyId IN ( " + HunterUtility.getCommaDelimitedStrings(consIds) + " )";
		List<Constituency> cons = HunterDaoFactory.getDaoObject(HunterHibernateHelper.class).executeQueryForObjList(Constituency.class, query);
		return cons;
	}
	
	private String normalizeName( String name ) {
		name = name.toLowerCase().trim().replaceAll("'", "");
		name = String.valueOf(name.charAt(0)).toUpperCase() + (name.substring(1, name.length()));
		// name = removeCharAndCap(name, "-");
		name = removeCharAndAddCap(name, " ");
		return name;
	}
	
	private String removeCharAndAddCap( String victim, String chr ) {
		String finalized = victim;
		if ( victim.contains(chr) ) {
			String parts[] = victim.split(chr);
			StringBuilder builder = new StringBuilder();
			Arrays.stream(parts).forEach(p -> builder.append(HunterUtility.getFirstUpperCase(p)).append(chr));
			String finalStr = builder.toString();
			finalStr = finalStr.substring(0, finalStr.length()-1);
			finalized = finalStr;
		}
		return finalized;
		
	}

}
