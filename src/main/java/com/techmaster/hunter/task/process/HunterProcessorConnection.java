package com.techmaster.hunter.task.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.constants.TaskProcessConstants;
import com.techmaster.hunter.obj.beans.TaskClientConfigBean;
import com.techmaster.hunter.util.HunterUtility;

public class HunterProcessorConnection {
	
	private static Logger logger = Logger.getLogger(HunterProcessorConnection.class);
	

	private String method;
	private TaskClientConfigBean configBean;
	private String requestBody;
	
	public HunterProcessorConnection(String method,TaskClientConfigBean configBean,String requestBody) {
		super();
		this.method = method;
		this.configBean = configBean;
		this.requestBody = requestBody;
	}
	
	public Map<String, Object> getResponse(){
		if(HunterConstants.METHOD_GET.equals(method)){ 
			return getGetResponse();
		}else{
			return getPostReponse();
		}
	}
	
	private Map<String, Object> getPostReponse(){
		logger.debug("Making a request for post response..."); 
		Map<String, Object> results = new HashMap<>();
		try {
            URL url = new URL(configBean.getMethodURL()); 
            Long time = System.currentTimeMillis();
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(requestBody);
            wr.flush();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String response = "";
            while ((line = rd.readLine()) != null) {
                response += line;
            }
            results.put(TaskProcessConstants.RESPONSE_CODE, response); 
            int responseCode = conn.getResponseCode();
            logger.debug("Response code : " + Integer.toString(responseCode));  
            results.put(TaskProcessConstants.RESPONSE_CODE, Integer.toString(responseCode)); 
            results.put(TaskProcessConstants.CONN_STATUS, HunterConstants.STATUS_SUCCESS);
        	results.put(TaskProcessConstants.RESPONSE_ERROR, null);
        	results.put(TaskProcessConstants.RESPONSE_DURATION, (System.currentTimeMillis() - time)+"");
        	results.put(TaskProcessConstants.RESPONSE_ERROR_MSG, response);
        	results.put(TaskProcessConstants.RESPONSE_TEXT, response);
        	results.put(TaskProcessConstants.ERROR_TYPE, TaskProcessConstants.ERROR_TYPE_RESPONSE);
            wr.close();
            rd.close();
            return results;
        } catch (IOException ex) {
        	ex.printStackTrace();
        	logger.warn("Encountered exception while processing GateWayMessage "); 
        	results.put(TaskProcessConstants.CONN_STATUS, HunterConstants.STATUS_FAILED);
        	results.put(TaskProcessConstants.RESPONSE_ERROR, HunterUtility.getApplicationErrorMessage());
        	results.put(TaskProcessConstants.RESPONSE_DURATION, 0L);
        	results.put(TaskProcessConstants.RESPONSE_ERROR_MSG, ex.getMessage());
        	results.put(TaskProcessConstants.RESPONSE_TEXT, null);
        	results.put(TaskProcessConstants.ERROR_TYPE, TaskProcessConstants.ERROR_TYPE_EXCEPTION);
            return results; 
        }
		
	}
	
	private void setConnRequestProps(URLConnection conn){
		Map<String,String> connParams = configBean.getConnConfigs();
		if(connParams != null && !connParams.isEmpty()){
			for(Map.Entry<String, String> entry : connParams.entrySet()){
				String key = entry.getKey();
				String value = entry.getValue()+"";
				conn.setRequestProperty(key, value);
			}
		}
	}
	
	private Map<String, Object> getGetResponse(){
		
		Long time = System.currentTimeMillis();
		Map<String, Object> results = new HashMap<String, Object>();
		String URL = configBean.getMethodURL();
		
		try {
            
			URL url = new URL(URL); 
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod(HunterConstants.METHOD_GET);
            conn.setDoOutput(true);
            setConnRequestProps(conn);
            
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(requestBody);
            wr.flush();
            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String response = "";
            while ((line = rd.readLine()) != null) {
                response += line;
            }
            
            results.put(TaskProcessConstants.RESPONSE_TEXT, response);
            results.put(TaskProcessConstants.CONN_STATUS, HunterConstants.STATUS_SUCCESS);
            
            int responseCode = conn.getResponseCode();
            logger.debug("Response code : " + responseCode); 
            results.put(TaskProcessConstants.RESPONSE_CODE, responseCode);
            results.put(TaskProcessConstants.ERROR_TYPE, TaskProcessConstants.ERROR_TYPE_RESPONSE);
            
            wr.close();
            rd.close();
            
            results.put(TaskProcessConstants.RESPONSE_DURATION, System.currentTimeMillis() - time);
            conn.disconnect();
            return results;
        } catch (IOException ex) {
        	ex.printStackTrace();
        	results.put(TaskProcessConstants.CONN_STATUS, HunterConstants.STATUS_FAILED);
        	results.put(TaskProcessConstants.RESPONSE_ERROR, HunterUtility.getApplicationErrorMessage());
        	results.put(TaskProcessConstants.RESPONSE_DURATION, 0L);
        	results.put(TaskProcessConstants.RESPONSE_ERROR_MSG, ex.getMessage());
        	results.put(TaskProcessConstants.ERROR_TYPE, TaskProcessConstants.ERROR_TYPE_EXCEPTION);
        	results.put(TaskProcessConstants.RESPONSE_TEXT, null);
            return results;
        }
		
		
	}

	
	
	
	
	
	
}
