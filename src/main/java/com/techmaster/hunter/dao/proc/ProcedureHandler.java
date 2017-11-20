package com.techmaster.hunter.dao.proc;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.techmaster.hunter.util.HunterUtility;

public class ProcedureHandler extends StoredProcedure{
	
	private JdbcTemplate jdbcTemplate;
	private String procName;
	private boolean isFunction;
	
	private Map<String, Integer> inParams = new HashMap<>();
	private Map<String, Integer> outParams = new HashMap<>();
	
	private static final Logger logger = Logger.getLogger(ProcedureHandler.class); 

    public ProcedureHandler(JdbcTemplate jdbcTemplate, String procName, boolean isFunction, Map<String, Integer> inParams, Map<String, Integer> outParams ){
        
    	super(jdbcTemplate.getDataSource(),procName);
    	
    	this.jdbcTemplate = jdbcTemplate;
    	this.procName = procName;
    	this.inParams = inParams;
    	this.outParams = outParams;
        
    	declareInParams();
    	declareOutParams();
        setFunction(isFunction);
        
        compile();
        
    }
    
    private void declareInParams(){
    	logger.debug("Registering IN parameters for " + this.procName); 
    	
    	if(this.inParams.isEmpty()){
    		logger.debug("No IN parameters found. Returning..."); 
    		return;
    	}
    	
    	for(Map.Entry<String, Integer> entry : this.inParams.entrySet()){
    		
    		String key = entry.getKey();
    		Integer val = entry.getValue();
    		
    		logger.debug("Registering input param >> " + key); 
    		declareParameter(new SqlParameter(key,val));
    		
    	}
    	
    	logger.debug("Finished declaring IN params for >> " + this.procName); 
    }
    
    private void declareOutParams(){
    	
    	logger.debug("Registering OUT parameters for " + this.toString()); 
    	
    	if(this.inParams.isEmpty()){
    		logger.debug("No OUT parameters found. Returning..."); 
    		return;
    	}
    	
    	for(Map.Entry<String, Integer> entry : this.outParams.entrySet()){
    		
    		String key = entry.getKey();
    		Integer val = entry.getValue();
    		
    		logger.debug("Registering out param >> " + key); 
    		declareParameter(new SqlOutParameter(key,val));
    		
    	}
    	
    	logger.debug("Finished declaring OUT params for >> " + this.procName); 
    	
    }
    
	public Map<String, Object> execute_(Map<String, Object> inParams){
		logger.debug("Invoking stored procedure ( "+ procName +" ) with params : " + HunterUtility.stringifyMap(inParams)); 
        Map<String, Object> out = super.execute(inParams);
        for(Map.Entry<String, Object> entry : out.entrySet()){
        	logger.debug(entry.getKey() + " > " + entry.getValue()); 
        }
        return out;
    }

	public String getProcName() {
		return procName;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public Map<String, Integer> getInParams() {
		return inParams;
	}
	public Map<String, Integer> getOutParams() {
		return outParams;
	}
	public boolean isFunction() {
		return isFunction;
	}

	@Override
	public String toString() {
		return "ProcedureHandler [procName=" + procName + ", isFunction="
				+ isFunction + "]";
	}
    
}
