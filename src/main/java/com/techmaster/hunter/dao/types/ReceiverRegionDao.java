package com.techmaster.hunter.dao.types;

import java.util.List;

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

public interface ReceiverRegionDao {
	
	public void insertReceiverRegion(ReceiverRegion receiverRegion);
	public void insertReceicerRegions(List<ReceiverRegion> receiverRegions); 
	public ReceiverRegion getReceiverRegionById(Long receiverId);
	public List<ReceiverRegionJson> getReceiverRegionsJsonByTaskId(Long taskId);
	public List<ReceiverRegion> getAllReceiverRegions();
	public void updateReceiverRegion(ReceiverRegion receiverRegion);
	public void deleteReceiverById(Long receiverId);
	public void deleteReceiverRegion(ReceiverRegion receiverRegion); 
	
	public void insertCountry(Country country);
	public void deleteCountry (Country country);
	public Country getCountryByName(String name);
	public Country getCountryById(Long countryId);
	public List<Country> getAllCountries();
	public List<CountryJson> getCountryJsonsForAllCountries();
	public void updateCountry(Country country);
	
	public void insertCounty(County county);
	public void deleteCounty (County county);
	public County getCountyById(Long countryId);
	public List<County> getCountyByNameAndCountryId(String countyName, Long countryId);
	public List<County> getAllCounties();
	public List<CountyJson> getCountyJsonsForSelCountry(String selCountry);
	public List<County> getAllCountiesForCountryId(String countryId);
	public void updateCounty(County county);
	
	public void insertState(State state);
	public void deleteState (State state);
	public State getStateById(Long countryId);
	public List<State> getAllStates();
	public void updateState(State state);
	
	
	public void insertConstituency(Constituency constituency);
	public void deleteConstituency (Constituency constituency);
	public Constituency getConstituencyById(Long id);
	public List<Constituency> getAllConstituencies();
	public List<ConstituencyJson> getConstituencyJsonsForSelCounty(String selCounty);
	public List<Constituency> getAllConstituenciesForCountyId(String countyId);
	public void updateConstituency(Constituency constituency);
	
	
	public void insertConstituencyWard(ConstituencyWard constituencyWard);
	public void deleteConstituencyWard (ConstituencyWard constituencyWard);
	public ConstituencyWard getConstituencyWardById(Long constituencyWardId);
	public List<ConstituencyWard> getAllConstituencyWards();
	public List<ConstituencyWardJson> getAllconsConstituencyWardJsonsForSelCons(String selCons);
	public List<ConstituencyWard> getAllConstituencyWardsForConstituencyId(String constituencyId);
	public void updateConstituencyWard(ConstituencyWard constituencyWard);
	
	
	public Long getNextReceiverRegionId();
	public Long getNextCountryId();
	public Long getNextCountyId();
	public Long getNextConsituencyId();
	public Long getNextConsituencyWardId();
	
	
	

}
