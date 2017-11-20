package com.techmaster.hunter.region;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.Constituency;
import com.techmaster.hunter.obj.beans.ConstituencyWard;
import com.techmaster.hunter.obj.beans.Country;
import com.techmaster.hunter.obj.beans.County;
import com.techmaster.hunter.obj.beans.RegionHierarchy;
import com.techmaster.hunter.obj.beans.State;
import com.techmaster.hunter.util.HunterUtility;

public class RegionAdapterServiceImpl implements RegionAdapterService {
	
	private static final Logger logger = Logger.getLogger(RegionAdapterServiceImpl.class);
	private static RegionAdapterService regionAdapterService;
	
	static{
		if(regionAdapterService == null){
			synchronized (RegionAdapterServiceImpl.class) {
				regionAdapterService = new RegionAdapterServiceImpl();
			}
		}
	}
	
	public static RegionAdapterService getInstance(){
		return regionAdapterService;
	}

	
	@Override
	public AuditInfo getAuditInfoFromHierarchy(RegionHierarchy regionHierarchy) {
		AuditInfo auditInfo = new AuditInfo();
		auditInfo.setCreatedBy(regionHierarchy.getCreatedBy());
		auditInfo.setCretDate(regionHierarchy.getCretDate());
		auditInfo.setLastUpdate(regionHierarchy.getLastUpdate());
		auditInfo.setLastUpdatedBy(regionHierarchy.getLastUpdatedBy()); 
		return auditInfo;
	}

	@Override
	public Country createCountry(RegionHierarchy regionHierarchy) {
		
		if(!regionHierarchy.getLevelType().equals(HunterConstants.RECEIVER_LEVEL_COUNTRY)){
			throw new IllegalArgumentException("Region hierarchy is not a country!!");
		}
		
		Country country = new Country();
		AuditInfo auditInfo = getAuditInfoFromHierarchy(regionHierarchy);
		country.setAuditInfo(auditInfo);
		country.setCapital(regionHierarchy.getCity()); 
		country.setCountryCode(regionHierarchy.getRegionCode());
		country.setCountryId(regionHierarchy.getBeanId());
		country.setCountryName(regionHierarchy.getName());
		country.setCountryPopulation(regionHierarchy.getPopulation());
		country.setHasState(regionHierarchy.isHasState());
		country.setHunterPopulation(regionHierarchy.getHunterPopuplation());
		
		return country;
	}

	@Override
	public State createState(RegionHierarchy regionHierarchy) {
		
		if(!regionHierarchy.getLevelType().equals(HunterConstants.RECEIVER_LEVEL_STATE)){
			throw new IllegalArgumentException("Region hierarchy is not a state!!");
		}
		
		State state = new State();
		AuditInfo auditInfo = getAuditInfoFromHierarchy(regionHierarchy);
		state.setAuditInfo(auditInfo);
		state.setCountryId(regionHierarchy.getParent());
		state.setHunterPopulation(regionHierarchy.getHunterPopuplation());
		state.setMapDots(regionHierarchy.getMapDots());
		state.setStateCode(regionHierarchy.getRegionCode());
		state.setStateId(regionHierarchy.getBeanId());
		state.setStateName(regionHierarchy.getName());
		state.setStatePopulation(regionHierarchy.getPopulation()); 
		
		return state;
	}

	@Override
	public County createCounty(RegionHierarchy regionHierarchy) {
		
		if(!regionHierarchy.getLevelType().equals(HunterConstants.RECEIVER_LEVEL_COUNTY)){
			throw new IllegalArgumentException("Region hierarchy is not a county!!");
		}
		
		County county = new County();
		AuditInfo auditInfo = getAuditInfoFromHierarchy(regionHierarchy);
		county.setAuditInfo(auditInfo);
		county.setCountyCode(regionHierarchy.getRegionCode());
		county.setCountyId(regionHierarchy.getBeanId());
		county.setCountyName(regionHierarchy.getName());
		county.setCountyPopulation(regionHierarchy.getPopulation());
		county.setHasState(regionHierarchy.isHasState());
		county.setHunterPopulation(regionHierarchy.getHunterPopuplation());
		county.setMapDots(regionHierarchy.getMapDots());
		
		if(!regionHierarchy.isHasState()){
			county.setCountryId(regionHierarchy.getParent());
		}else{
			county.setStateId(regionHierarchy.getParent()); 
		}
		
		return county;
	}

	@Override
	public Constituency createConstituency(RegionHierarchy regionHierarchy) {
		
		if(!regionHierarchy.getLevelType().equals(HunterConstants.RECEIVER_LEVEL_CONSITUENCY)){
			throw new IllegalArgumentException("Region hierarchy is not a constituency!!");
		}
		
		Constituency constituency = new Constituency();
		AuditInfo auditInfo = getAuditInfoFromHierarchy(regionHierarchy);
		constituency.setAuditInfo(auditInfo);
		constituency.setCnsttncyCity(regionHierarchy.getCity());
		constituency.setCnsttncyId(regionHierarchy.getBeanId());
		constituency.setCnsttncyName(regionHierarchy.getName());
		constituency.setCnsttncyPopulation(regionHierarchy.getPopulation());
		constituency.setConstituencyCode(regionHierarchy.getRegionCode());
		constituency.setCountyId(regionHierarchy.getParent());
		constituency.setHunterPopulation(regionHierarchy.getHunterPopuplation());
		constituency.setMapDots(regionHierarchy.getMapDots()); 
		
		return constituency;
	}

	@Override
	public ConstituencyWard createConstituencyWard(RegionHierarchy regionHierarchy) {
		
		if(!regionHierarchy.getLevelType().equals(HunterConstants.RECEIVER_LEVEL_WARD)){
			throw new IllegalArgumentException("Region hierarchy is not a country!!");
		}
		
		ConstituencyWard constituencyWard = new ConstituencyWard();
		AuditInfo auditInfo = getAuditInfoFromHierarchy(regionHierarchy);
		constituencyWard.setAuditInfo(auditInfo);
		constituencyWard.setConstituencyId(regionHierarchy.getParent());
		constituencyWard.setConstituencyWardCode(regionHierarchy.getRegionCode());
		constituencyWard.setHunterPopulation(regionHierarchy.getHunterPopuplation()); 
		constituencyWard.setMapDots(regionHierarchy.getMapDots()); 
		constituencyWard.setWardId(regionHierarchy.getBeanId());
		constituencyWard.setWardName(regionHierarchy.getName());
		constituencyWard.setWardPopulation(regionHierarchy.getPopulation()); 
		
		return constituencyWard;
	}

	@Override
	public List<Country> adapt(List<RegionHierarchy> regionHierarchies) {
		
		List<Country> countries = new ArrayList<Country>();  

		for(RegionHierarchy regionHierarchy : regionHierarchies){
			
			if(regionHierarchy.getLevelType().equals(HunterConstants.RECEIVER_LEVEL_COUNTRY)){
				
				Country country = createCountry(regionHierarchy);
				Set<State> states = new HashSet<>();
				Set<County> counties = new HashSet<>();
				
				for(RegionHierarchy steCntyHrrchy : regionHierarchies){
					if(steCntyHrrchy.getLevelType().equals(HunterConstants.RECEIVER_LEVEL_STATE) && steCntyHrrchy.getParent().equals(country.getCountryId())){ 
						
						State state = createState(steCntyHrrchy);
						states.add(state);
						
						Set<County> stateCounties = new HashSet<>();
						
						for(RegionHierarchy stateConsRegionHierarchy : regionHierarchies){
							if(stateConsRegionHierarchy.getLevelType().equals(HunterConstants.RECEIVER_LEVEL_COUNTY) && stateConsRegionHierarchy.getParent().equals(state.getStateId())){
								
								County stateCounty = createCounty(stateConsRegionHierarchy);
								stateCounties.add(stateCounty);
								
								Set<Constituency> countyConstituencies = new HashSet<>();
								
								for(RegionHierarchy countyRegionHierarchy : regionHierarchies){
									if(countyRegionHierarchy.getLevelType().equals(HunterConstants.RECEIVER_LEVEL_CONSITUENCY) && countyRegionHierarchy.getParent().equals(stateCounty.getCountyId())){
										
										Constituency constituency = createConstituency(countyRegionHierarchy);
										countyConstituencies.add(constituency);
										
										Set<ConstituencyWard> constituencyWards = new HashSet<>();
										
										for(RegionHierarchy consWardRegionHierarchy : regionHierarchies){
											if(consWardRegionHierarchy.getLevelType().equals(HunterConstants.RECEIVER_LEVEL_WARD) && consWardRegionHierarchy.getParent().equals(constituency.getCnsttncyId())){ 
												ConstituencyWard constituencyWard = createConstituencyWard(consWardRegionHierarchy);
												constituencyWards.add(constituencyWard);
											}
										}
										
									}
								}
								
							}
						}
						
					}else if(steCntyHrrchy.getLevelType().equals(HunterConstants.RECEIVER_LEVEL_COUNTY) && steCntyHrrchy.getParent().equals(country.getCountryId())){
						
						County county = createCounty(steCntyHrrchy);
						counties.add(county);
						
						Set<Constituency> countyConstituencies = new HashSet<>();
						
						for(RegionHierarchy countyRegionHierarchy : regionHierarchies){
							if(countyRegionHierarchy.getLevelType().equals(HunterConstants.RECEIVER_LEVEL_CONSITUENCY) && countyRegionHierarchy.getParent().equals(county.getCountyId())){
								
								Constituency constituency = createConstituency(countyRegionHierarchy);
								countyConstituencies.add(constituency);
								
								Set<ConstituencyWard> constituencyWards = new HashSet<>();
								
								for(RegionHierarchy consWardRegionHierarchy : regionHierarchies){
									if(consWardRegionHierarchy.getLevelType().equals(HunterConstants.RECEIVER_LEVEL_WARD) && consWardRegionHierarchy.getParent().equals(constituency.getCnsttncyId())){ 
										ConstituencyWard constituencyWard = createConstituencyWard(consWardRegionHierarchy);
										constituencyWards.add(constituencyWard);
									}
								}
								
								constituency.setConstituencyWards(constituencyWards); 
								
							}
						}
						
						county.setConstituencies(countyConstituencies); 
						
					}
				}
				country.setStates(states);
				country.setCounties(counties); 
				countries.add(country);
			}
		}
		
		return countries;
	}

	
	public static void main(String[] args) {
		
		List<RegionHierarchy> regionHierarchies = RegionHierarchyAdapter.getInstance().adapt(false);
		List<Country> countries = regionAdapterService.adapt(regionHierarchies);
		logger.debug(HunterUtility.stringifyList(countries));  
	}
	
	
	
	
	

}
