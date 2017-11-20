package com.techmaster.hunter.region;

import java.util.List;

import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.Constituency;
import com.techmaster.hunter.obj.beans.ConstituencyWard;
import com.techmaster.hunter.obj.beans.Country;
import com.techmaster.hunter.obj.beans.County;
import com.techmaster.hunter.obj.beans.RegionHierarchy;
import com.techmaster.hunter.obj.beans.State;

public interface RegionAdapterService {
	
	public Country createCountry(RegionHierarchy regionHierarchy);
	public State createState(RegionHierarchy regionHierarchy);
	public County createCounty(RegionHierarchy regionHierarchy);
	public Constituency createConstituency(RegionHierarchy regionHierarchy);
	public ConstituencyWard createConstituencyWard(RegionHierarchy regionHierarchy);
	
	public AuditInfo getAuditInfoFromHierarchy(RegionHierarchy regionHierarchy);
	public List<Country> adapt(List<RegionHierarchy> regionHierarchies);
	
}
