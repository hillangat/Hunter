package com.techmaster.hunter.region;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.ReceiverRegionDao;
import com.techmaster.hunter.obj.beans.County;
import com.techmaster.hunter.obj.beans.RegionHierarchy;
import com.techmaster.hunter.util.HunterUtility;

public class RegionHierarchyNavBean {
	
	@Autowired ReceiverRegionDao receiverRegionDao;
	@Autowired HunterJDBCExecutor hunterJDBCExecutor;
	
	public static final String NAV_SESSION_BEAN = "RegionHierarchyNavBean";
	
	public static boolean refresh = false;
	public static List<String> orderedCountyNames = new ArrayList<>();
	private static final Logger logger = Logger.getLogger(RegionHierarchyNavBean.class);
	private static List<RegionHierarchy> allRegionHierarchies = new ArrayList<>();
	
	public static final String BACKWARD_NAV = "BACKWARD_NAV";
	public static final String FORWARD_NAV = "FORWARD_NAV";
	
	private String sessionId = null;
	private int currRange = 1;
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	static{
		refreshHierarchies();
	}
	
	public void setReceiverRegionDao(ReceiverRegionDao receiverRegionDao) {
		this.receiverRegionDao = receiverRegionDao;
	}

	public void setHunterJDBCExecutor(HunterJDBCExecutor hunterJDBCExecutor) {
		this.hunterJDBCExecutor = hunterJDBCExecutor;
	}

	public RegionHierarchyNavBean(ReceiverRegionDao receiverRegionDao, HunterJDBCExecutor hunterJDBCExecutor) {
		
		super();
		
		this.receiverRegionDao = receiverRegionDao;
		this.hunterJDBCExecutor = hunterJDBCExecutor;
		
		// Populate county names
		if(orderedCountyNames.isEmpty()){
			String query = "select distinct CNTY_NAM from CNTY order by CNTY_NAM asc";
			logger.debug("orderedCountyNames county names is empty. Loading...");
			orderedCountyNames = new ArrayList<>();
			Map<Integer, List<Object>> rowLists = hunterJDBCExecutor.executeQueryRowList(query, null);
			for(Map.Entry<Integer, List<Object>> entry : rowLists.entrySet()){
				String countyName = entry.getValue().get(0) + ""; 
				orderedCountyNames.add(countyName);
			}
			logger.debug("Successfully loaded orderedCountyNames!!");
			logger.debug(HunterUtility.stringifyList(orderedCountyNames));  
		}else{
			logger.debug("orderedCountyNames county names is not empty. Doing nothing!");
		}
		
		if(allRegionHierarchies.isEmpty()){
			refreshHierarchies();
		}
		
	}

	public static void refreshHierarchies(){
		logger.debug("Region Hierarchy Navigation Bean refreshing hierarchies..."); 
		allRegionHierarchies = RegionHierarchyAdapter.getInstance().adapt(false);
	}
	
	public int getNextRange(String dir){
		
		// Take care of the edges.
		if(currRange == 5 && dir.equals(FORWARD_NAV)){ 
			currRange = 1;
			return currRange;
		}else if(currRange == 1 && dir.equals(BACKWARD_NAV)){
			currRange = 5;
			return currRange;
		}
		
		
		// Now task care of normal values.
		if(dir.equals(FORWARD_NAV)){
			currRange++;
			return currRange;
		}else if(dir.equals(BACKWARD_NAV)){
			currRange--;
			return currRange;
		}
		
		return this.currRange;
	}
	
	public List<RegionHierarchy> goForward(String countryName){
		int start = getNextRange(FORWARD_NAV);
		int end = start + 5;
		return getListForRange(start, end, countryName);
	}
	
	public List<RegionHierarchy> goBackward(String countryName){
		int start = getNextRange(BACKWARD_NAV);
		int end = start + 5;
		return getListForRange(start, end, countryName);
	}
	
	private List<RegionHierarchy> getListForRange(int start, int end, String countryName){
		
		if(start < 0 || start >= orderedCountyNames.size()){
			throw new IllegalArgumentException("Invalid starting point");
		}
		if(end < 0 || end >= orderedCountyNames.size()){
			throw new IllegalArgumentException("Invalid ending point");
		}
		
		List<RegionHierarchy> rgnHrrchs = new ArrayList<>();
		String[] rngCntyNms = null;
		
		for(int i=start; i<= end; i++){
			String name = orderedCountyNames.get(i);
			rngCntyNms = HunterUtility.initArrayAndInsert(rngCntyNms, name);
		}
		
		String query = "SELECT c.CNTRY_NAM, c.CNTRY_ID FROM CNTRY c where c.CNTRY_NAM = ?";
		List<Object> values = new ArrayList<>();
		values.add(countryName);
		Map<Integer, List<Object>> rowMaps = hunterJDBCExecutor.executeQueryRowList(query, values);
		
		Long countryId = HunterUtility.getLongFromObject(rowMaps.get(1).get(1));
		StringBuilder countyNameList = new StringBuilder();
		
		for(String name : rngCntyNms){
			String quoted = HunterUtility.singleQuote(name);
			countyNameList.append(quoted).append(",");
		}
		
		String quotedStr = countyNameList.toString();
		quotedStr = quotedStr == null ? null : quotedStr.substring(0, quotedStr.length() - 1);
		logger.debug(quotedStr); 
		Set<County> counties = RegionCache.getInstance().getCountiesForCountryId(countryId);
		
		for(County county : counties){
			 List<RegionHierarchy> cntyRgnHrrchs = RegionHierarchyAdapter.getInstance().getRgnHrrchsUndrCnty(county, true, true);
			 rgnHrrchs.addAll(cntyRgnHrrchs);
		 }
		
		logger.debug("Size of region hierarchies found >> " + rgnHrrchs.size()); 
		logger.debug("Names for range ( "+ start + " - " + end + ") : " + Arrays.toString(rngCntyNms));  
		
		return rgnHrrchs;
	}
	
	public List<RegionHierarchy> getDefault(String countryName) {
		this.currRange = 1;
		List<RegionHierarchy> defaultList = getListForRange(0, 46, countryName);
		defaultList = genIdAndUpdateGenParents(defaultList);
		return defaultList;
	}
	
	public List<RegionHierarchy> genIdAndUpdateGenParents(List<RegionHierarchy> inputRgnHrrchs){
		
		Long genId = 1L;
		
		for(RegionHierarchy regionHierarchy : inputRgnHrrchs){
			regionHierarchy.setId(genId); 
			genId++;
		}
		
		for(RegionHierarchy cntyRegionHierarchy : inputRgnHrrchs){
			if(cntyRegionHierarchy.getLevelType().equals(HunterConstants.RECEIVER_LEVEL_COUNTY)){
				for(RegionHierarchy consRegionHierarchy : inputRgnHrrchs){
					if(consRegionHierarchy.getLevelType().equals(HunterConstants.RECEIVER_LEVEL_CONSITUENCY) && consRegionHierarchy.getParent().equals(cntyRegionHierarchy.getBeanId())){
						consRegionHierarchy.setGenParent(cntyRegionHierarchy.getId());
						for(RegionHierarchy consWardRegionHierarchy : inputRgnHrrchs){
							if(consWardRegionHierarchy.getLevelType().equals(HunterConstants.RECEIVER_LEVEL_WARD) && consWardRegionHierarchy.getParent().equals(consRegionHierarchy.getBeanId()) ){
								consWardRegionHierarchy.setGenParent(consRegionHierarchy.getId());  
							}
						}
					}
				}
			}
		}
		
		
		return inputRgnHrrchs;
	}


}
