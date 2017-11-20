package com.techmaster.hunter.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.dao.proc.ProcedureHandler;

public class TestProcedure {
	
	@Autowired private javax.sql.DataSource dataSource;
	@Autowired private ProcedureHandler get_region_codes;
	
	public void testGetRegionsCodesProcedure(){
		
    	Map<String, Object> inParams = new HashMap<String, Object>(5);
		inParams.put("country_name", "Kenya");
		inParams.put("county_name", "Bomet");
		inParams.put("state_name", null);
		inParams.put("constituency_name", "Bomet Central");
		inParams.put("constituency_ward_name", "Chesoen");
		
		get_region_codes.execute_(inParams);
		
		
	}

}
