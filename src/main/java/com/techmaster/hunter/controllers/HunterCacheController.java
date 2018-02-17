package com.techmaster.hunter.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterURLConstants;
import com.techmaster.hunter.util.HunterUtility;

@Controller
@RequestMapping("/cache")
public class HunterCacheController extends HunterBaseController {
	
	// private Logger logger = Logger.getLogger(this.getClass());
	
	@Produces("application/json")
	@RequestMapping(value="/action/read", method=RequestMethod.GET)
	@ResponseBody
	public String refreshCache() {
		HunterUtility.threadSleepFor(1500); 
		try {
			return new JSONArray( HunterUtility.convertFileToString(HunterURLConstants.CACHE_REFRESH_JSONS) ).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return HunterUtility.setJSONObjectForFailure(null, "Application error occurred while loading caches").toString();
		}
	}
	
	@Produces("application/json")
	@Consumes("application/json") 
	@RequestMapping(value="/action/refreshCaches", method=RequestMethod.POST)
	@ResponseBody
	public String refreshCache( HttpServletRequest request ){
		HunterUtility.threadSleepFor(500); 
		try{
			JSONArray jsonArray = new JSONArray( HunterUtility.getRequestBodyAsStringSafely(request) );
			if( jsonArray != null && jsonArray.length() > 0 ){
				List<String> keys = new ArrayList<String>();
				for( int i = 0; i < jsonArray.length(); i++ ) {
					JSONObject cache = jsonArray.getJSONObject(i);
					String key = HunterUtility.getStringOrNulFromJSONObj(cache, "key");
					if ( HunterUtility.notNullNotEmpty(key) ) {
						if ( key.equals("allXMLService") ) {
							keys.clear();
							keys.add(key);
							break;
						}
						keys.add(key);
					}
				}
				keys.forEach( k -> HunterCacheUtil.getInstance().refreshCacheService(k) );
				return HunterUtility.setJSONObjectForSuccess(null, "Successfully refreshed cache!").toString();
			}else{
				return HunterUtility.setJSONObjectForFailure(null, "No cache service specified in request.").toString();
			}
		}catch(Exception e){
			return HunterUtility.setJSONObjectForFailure(null, "Applicaiton error : " + e.getMessage()).toString();
		}
	}
	
}
