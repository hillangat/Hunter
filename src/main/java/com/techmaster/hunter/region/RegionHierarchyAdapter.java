package com.techmaster.hunter.region;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.ReceiverRegionDaoImpl;
import com.techmaster.hunter.dao.types.ReceiverRegionDao;
import com.techmaster.hunter.obj.beans.Constituency;
import com.techmaster.hunter.obj.beans.ConstituencyWard;
import com.techmaster.hunter.obj.beans.Country;
import com.techmaster.hunter.obj.beans.County;
import com.techmaster.hunter.obj.beans.RegionHierarchy;
import com.techmaster.hunter.obj.beans.State;
import com.techmaster.hunter.util.HunterUtility;

public class RegionHierarchyAdapter {
	
	private ReceiverRegionDao receiverRegionDao = new ReceiverRegionDaoImpl();
	public static final Logger logger = Logger.getLogger(RegionHierarchy.class);
	private static RegionHierarchyAdapter regionAdapter;
	
	static{
		if(regionAdapter == null){
			synchronized (RegionHierarchyAdapter.class) {
				regionAdapter = new RegionHierarchyAdapter();
			}
		}
	}
	
	public static RegionHierarchyAdapter  getInstance(){
		return regionAdapter;
	}
	
	private RegionHierarchy createFromCountry(Country country){
		RegionHierarchy regionHierarchy = new RegionHierarchy();
		regionHierarchy.setId(country.getCountryId()); 
		regionHierarchy.setName(country.getCountryName()); 
		regionHierarchy.setLevelType(HunterConstants.RECEIVER_LEVEL_COUNTRY); 
		regionHierarchy.setBeanId(country.getCountryId());
		regionHierarchy.setRegionCode(country.getCountryCode()); 
		regionHierarchy.setPopulation(country.getCountryPopulation());
		regionHierarchy.setHunterPopuplation(country.getHunterPopulation()); 
		regionHierarchy.setCity(country.getCapital());
		regionHierarchy.setHasState(country.isHasState());
		regionHierarchy.setHunterPopuplation(country.getHunterPopulation());
		regionHierarchy.setCreatedBy(country.getAuditInfo().getCreatedBy()); 
		regionHierarchy.setCretDate(country.getAuditInfo().getCretDate());
		regionHierarchy.setLastUpdate(country.getAuditInfo().getLastUpdate());
		regionHierarchy.setLastUpdatedBy(country.getAuditInfo().getLastUpdatedBy());
		regionHierarchy.setParent(null); 
		return regionHierarchy;
	}
	private RegionHierarchy createFromCounty(County county){
		RegionHierarchy regionHierarchy = new RegionHierarchy();
		regionHierarchy.setId(county.getCountyId());
		regionHierarchy.setName(county.getCountyName());
		regionHierarchy.setMapDots(county.getMapDots()); 
		regionHierarchy.setLevelType(HunterConstants.RECEIVER_LEVEL_COUNTY); 
		regionHierarchy.setBeanId(county.getCountyId());
		regionHierarchy.setRegionCode(county.getCountyCode()); 
		regionHierarchy.setPopulation(county.getCountyPopulation());
		regionHierarchy.setHunterPopuplation(county.getHunterPopulation()); 
		regionHierarchy.setCity(null);
		regionHierarchy.setHasState(county.isHasState());
		regionHierarchy.setCreatedBy(county.getAuditInfo().getCreatedBy()); 
		regionHierarchy.setCretDate(county.getAuditInfo().getCretDate());
		regionHierarchy.setLastUpdate(county.getAuditInfo().getLastUpdate());
		regionHierarchy.setLastUpdatedBy(county.getAuditInfo().getLastUpdatedBy()); 
		regionHierarchy.setParent(county.getCountryId()); 
		return regionHierarchy;
	}
	private RegionHierarchy createFromConstituency(Constituency constituency){
		RegionHierarchy regionHierarchy = new RegionHierarchy();
		regionHierarchy.setId(constituency.getCnsttncyId());
		regionHierarchy.setName(constituency.getCnsttncyName());
		regionHierarchy.setMapDots(constituency.getMapDots()); 
		regionHierarchy.setLevelType(HunterConstants.RECEIVER_LEVEL_CONSITUENCY); 
		regionHierarchy.setBeanId(constituency.getCnsttncyId());
		regionHierarchy.setRegionCode(constituency.getConstituencyCode()); 
		regionHierarchy.setPopulation(constituency.getCnsttncyPopulation());
		regionHierarchy.setHunterPopuplation(constituency.getHunterPopulation()); 
		regionHierarchy.setCity(null);
		regionHierarchy.setHasState(false);
		regionHierarchy.setCreatedBy(constituency.getAuditInfo().getCreatedBy()); 
		regionHierarchy.setCretDate(constituency.getAuditInfo().getCretDate());
		regionHierarchy.setLastUpdate(constituency.getAuditInfo().getLastUpdate());
		regionHierarchy.setLastUpdatedBy(constituency.getAuditInfo().getLastUpdatedBy());
		regionHierarchy.setParent(constituency.getCountyId());
		return regionHierarchy;
	}
	private RegionHierarchy createFromConstituencyWard(ConstituencyWard constituencyWard){
		RegionHierarchy regionHierarchy = new RegionHierarchy();
		regionHierarchy.setId(constituencyWard.getWardId());
		regionHierarchy.setName(constituencyWard.getWardName());
		regionHierarchy.setMapDots(constituencyWard.getMapDots()); 
		regionHierarchy.setLevelType(HunterConstants.RECEIVER_LEVEL_WARD); 
		regionHierarchy.setBeanId(constituencyWard.getWardId());
		regionHierarchy.setRegionCode(constituencyWard.getConstituencyWardCode()); 
		regionHierarchy.setPopulation(constituencyWard.getWardPopulation());
		regionHierarchy.setHunterPopuplation(constituencyWard.getHunterPopulation()); 
		regionHierarchy.setCity(null);
		regionHierarchy.setHasState(false);
		regionHierarchy.setCreatedBy(constituencyWard.getAuditInfo().getCreatedBy()); 
		regionHierarchy.setCretDate(constituencyWard.getAuditInfo().getCretDate());
		regionHierarchy.setLastUpdate(constituencyWard.getAuditInfo().getLastUpdate());
		regionHierarchy.setLastUpdatedBy(constituencyWard.getAuditInfo().getLastUpdatedBy()); 
		regionHierarchy.setParent(constituencyWard.getConstituencyId());
		return regionHierarchy;
	}
	private RegionHierarchy createFromState(State state){
		RegionHierarchy regionHierarchy = new RegionHierarchy();
		regionHierarchy.setId(state.getStateId());
		regionHierarchy.setName(state.getStateName());
		regionHierarchy.setMapDots(state.getMapDots()); 
		regionHierarchy.setLevelType(HunterConstants.RECEIVER_LEVEL_STATE); 
		regionHierarchy.setBeanId(state.getStateId());
		regionHierarchy.setRegionCode(state.getStateCode()); 
		regionHierarchy.setPopulation(state.getStatePopulation());
		regionHierarchy.setHunterPopuplation(state.getHunterPopulation()); 
		regionHierarchy.setCity(null);
		regionHierarchy.setHasState(false);
		regionHierarchy.setCreatedBy(state.getAuditInfo().getCreatedBy()); 
		regionHierarchy.setCretDate(state.getAuditInfo().getCretDate());
		regionHierarchy.setLastUpdate(state.getAuditInfo().getLastUpdate());
		regionHierarchy.setLastUpdatedBy(state.getAuditInfo().getLastUpdatedBy()); 
		regionHierarchy.setParent(state.getCountryId()); 
		return regionHierarchy;
	}

	public List<RegionHierarchy> adapt(boolean hasCountry){
		
		List<RegionHierarchy> regionHierarchies = new ArrayList<RegionHierarchy>();
		List<Country> countries = receiverRegionDao.getAllCountries();
		
		for(Country country : countries){
			
			// create for country
			logger.debug("Creating region hierachies from country : " + country.getCountryName()); 
			if(hasCountry){
				RegionHierarchy regionHierarchy = createFromCountry(country);
				regionHierarchies.add(regionHierarchy);
			}
			
			// create from counties
			if(!country.isHasState()){
				Set<County> counties = country.getCounties();
				for(County county : counties){
					
					logger.debug("Creating region hierachies from county : " + county.getCountyName()); 
					RegionHierarchy cnRegionHierarchy = createFromCounty(county);
					if(!hasCountry){
						cnRegionHierarchy.setParent(null); 
					}
					regionHierarchies.add(cnRegionHierarchy);
					
					Set<Constituency> constituencies = county.getConstituencies();
					for(Constituency constituency : constituencies){
						
						// create from constituency
						logger.debug("Creating region hierachies from constituency : " + constituency.getCnsttncyName()); 
						RegionHierarchy consRegionHierarchy = createFromConstituency(constituency);
						regionHierarchies.add(consRegionHierarchy);
						
						Set<ConstituencyWard> constituencyWards = constituency.getConstituencyWards();
						for(ConstituencyWard constituencyWard : constituencyWards){
							RegionHierarchy consWardRegionHierarchy = createFromConstituencyWard(constituencyWard);
							regionHierarchies.add(consWardRegionHierarchy);
						}
					}
					
				}
				
			}else{
				
				logger.debug("Country has state, creating region hierarchies from state.."); 
				
				Set<State> states = country.getStates();
				for(State state : states){
					
					logger.debug("Creating region hierachies from county : " + state.getStateName()); 
					RegionHierarchy cnRegionHierarchy = createFromState(state);
					regionHierarchies.add(cnRegionHierarchy);
					
					Set<County> counties = state.getCounties();
					for(County county : counties){
						
						logger.debug("Creating region hierachies from county : " + county.getCountyName()); 
						RegionHierarchy cn2RegionHierarchy = createFromCounty(county);
						// if it has not ountry, then make the parent null;
						if(!hasCountry){
							cn2RegionHierarchy.setParent(null); 
						}
						regionHierarchies.add(cn2RegionHierarchy);
						
						Set<Constituency> constituencies = county.getConstituencies();
						for(Constituency constituency : constituencies){
							
							// create from constituency
							logger.debug("Creating region hierachies from constituency : " + constituency.getCnsttncyName()); 
							RegionHierarchy consRegionHierarchy = createFromConstituency(constituency);
							regionHierarchies.add(consRegionHierarchy);
							
							Set<ConstituencyWard> constituencyWards = constituency.getConstituencyWards();
							for(ConstituencyWard constituencyWard : constituencyWards){
								RegionHierarchy consWardRegionHierarchy = createFromConstituencyWard(constituencyWard);
								regionHierarchies.add(consWardRegionHierarchy);
							}
						}
					}
				}
			}
		}
		
		logger.debug("Finished creating hierarchies from regions!! \n" + HunterUtility.stringifyList(regionHierarchies)); 
		
		return regionHierarchies;
	}
	
	public ReceiverRegionDao getReceiverRegionDao() {
		return receiverRegionDao;
	}

	public List<RegionHierarchy> getRgnHrrchsUndrCnty(County county, boolean addCounty, boolean hasCountry){
		
		List<RegionHierarchy> resultRegionHierarchies = new ArrayList<>();
		Set<Constituency> constituencies = county.getConstituencies();
		
		if(addCounty){
			RegionHierarchy countyRegionHierarchy = createFromCounty(county);
			if(!hasCountry){
				countyRegionHierarchy.setParent(null); 
			}
			resultRegionHierarchies.add(countyRegionHierarchy);
		}
		
		for(Constituency constituency : constituencies){
			Set<ConstituencyWard> consConstituencyWards = constituency.getConstituencyWards();
			RegionHierarchy consRegionHierarchy = createFromConstituency(constituency);
			resultRegionHierarchies.add(consRegionHierarchy);
			for(ConstituencyWard constituencyWard : consConstituencyWards){
				RegionHierarchy wardRegionHierarchy = createFromConstituencyWard(constituencyWard);
				resultRegionHierarchies.add(wardRegionHierarchy);
			}
		}
		
		return resultRegionHierarchies;
	}
	
	public static void main(String[] args) {
		RegionHierarchyAdapter regionHierarchyAdapter = RegionHierarchyAdapter.getInstance();
		County Bomet = regionHierarchyAdapter.getReceiverRegionDao().getCountyById(4L);
		List<RegionHierarchy> regionHierarchies = regionHierarchyAdapter.getRgnHrrchsUndrCnty(Bomet, true, false);
		logger.debug(HunterUtility.stringifyList(regionHierarchies));  
	}
	
	
	
	
	
}

























