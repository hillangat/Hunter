package com.techmaster.hunter.test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.techmaster.hunter.dao.impl.ReceiverRegionDaoImpl;
import com.techmaster.hunter.dao.types.ReceiverRegionDao;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.Country;
import com.techmaster.hunter.obj.beans.County;
import com.techmaster.hunter.util.HunterLogFactory;

public class RegionTest {
	
	private static ReceiverRegionDao regionDao = new ReceiverRegionDaoImpl();
	private static Logger logger = HunterLogFactory.getLog(RegionTest.class);
	
	public static AuditInfo getDefaultAuditInfo(){	
		AuditInfo auditInfo = new AuditInfo();
		auditInfo.setCretDate(new Date());
		auditInfo.setCreatedBy("hlangat01");
		auditInfo.setLastUpdate(new Date()); 
		auditInfo.setLastUpdatedBy("hlangat01");
		return auditInfo;
	}
	
	public static void createCountry(){
		
		AuditInfo auditInfo = new AuditInfo();
		auditInfo.setCretDate(new Date());
		auditInfo.setCreatedBy("hlangat01");
		auditInfo.setLastUpdate(new Date()); 
		auditInfo.setLastUpdatedBy("hlangat01");
		
		Country country = new Country();
		country.setAuditInfo(auditInfo);
		country.setCapital("Nairobi");
		country.setCountryName("Kenya");
		country.setCountryPopulation(48000000);
		country.setHunterPopulation(2000000); 
		
		regionDao.insertCountry(country); 
	}
	
	public static Country getCountryForId(Long id){
		Country country =  regionDao.getCountryById(1L);
		logger.debug(country); 
		return country;
	}
	
	public static void addCountiesToCountry(Country country){
		
		County county1 = new County();
		county1.setAuditInfo(getDefaultAuditInfo());
		county1.setCountyName("Kerich"); 
		county1.setCountyPopulation(2000000); 
		county1.setHunterPopulation(1000000); 
		county1.setMapDots(null); 
		
		County county2 = new County();
		county2.setAuditInfo(getDefaultAuditInfo());
		county2.setCountyName("Bomet"); 
		county2.setCountyPopulation(2000000); 
		county2.setHunterPopulation(1000000); 
		county2.setMapDots(null); 
		
		County county3 = new County();
		county3.setAuditInfo(getDefaultAuditInfo());
		county3.setCountyName("Bomet"); 
		county3.setCountyPopulation(2000000); 
		county3.setHunterPopulation(1000000); 
		county3.setMapDots(null);
		
		County county4 = new County();
		county4.setAuditInfo(getDefaultAuditInfo());
		county4.setCountyName("Kisii"); 
		county4.setCountyPopulation(2000000); 
		county4.setHunterPopulation(1000000); 
		county4.setMapDots(null);
		
		County county5 = new County();
		county5.setAuditInfo(getDefaultAuditInfo());
		county5.setCountyName("Nakuru"); 
		county5.setCountyPopulation(2000000); 
		county5.setHunterPopulation(1000000); 
		county5.setMapDots(null);
		
		County county6 = new County();
		county6.setAuditInfo(getDefaultAuditInfo());
		county6.setCountyName("Narok"); 
		county6.setCountyPopulation(2000000); 
		county6.setHunterPopulation(1000000); 
		county6.setMapDots(null);
		
		Set<County> counties = new HashSet<>();
		counties.add(county6);
		counties.add(county5);
		counties.add(county4);
		counties.add(county3);
		counties.add(county2);
		counties.add(county1);
		
		for(County county : counties){
			county.setCountryId(country.getCountryId()); 
			regionDao.insertCounty(county);
		}
		
		country.setCounties(counties);
		regionDao.updateCountry(country); 
		
		logger.debug(country); 
		
	}
	
	public static void testPopulateRandomReceiversToConstituencyWard(){
		/*Map<String, Object> rowMap = regionService.getNamesAndIdsForRegionsNamesForWard("Kenya", "Bomet", "Bomet Central", "Chesoen");
		Long constituencyWardId = HunterUtility.getLongFromObject(rowMap.get("WRD_ID")); 
		logger.debug("Obtained ward id >> " + constituencyWardId); 
		regionService.populateRandomReceiversToConstituencyWard(constituencyWardId, 3000);*/
	}
	
	public static void main(String[] args) {
		
		//createCountry();
		//getCountryForId(1L);
		
		addCountiesToCountry(getCountryForId(1L));
		logger.debug(getCountryForId(1L)); 
		
	}

}
