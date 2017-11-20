package com.techmaster.hunter.controllers;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.util.HunterUtility;

@Controller
@RequestMapping("/cache")
public class HunterCacheController extends HunterBaseController {
	
	@Produces("application/json")
	@RequestMapping(value="/action/refresh/{cacheServiceName}", method=RequestMethod.GET)
	@ResponseBody
	public String refreshCache(@PathVariable("cacheServiceName") String cacheServiceName){
		switch (cacheServiceName) {
		case HunterConstants.ALL_XML_SERVICES:
			HunterCacheUtil.getInstance().refreshAllXMLServices();
			break;
		default:
			break;
		}
		return "Succes";
	}
	
	@Produces("application/json")
	@Consumes("application/json") 
	@RequestMapping(value="/action/refreshCaches", method=RequestMethod.POST)
	@ResponseBody
	public String refreshCache(@RequestBody Map<String,Object> params){
		JSONObject message = new JSONObject();
		try{
			if(params != null && !params.isEmpty()){
				for(Map.Entry<String, Object> entry : params.entrySet()){
					String key = entry.getKey();
					HunterCacheUtil.getInstance().refreshCacheService(key);
				}
				return HunterUtility.setJSONObjectForSuccess(message, "Successfully refreshed cache!").toString();
			}else{
				return HunterUtility.setJSONObjectForFailure(message, "No cache service specified in request.").toString();
			}
		}catch(Exception e){
			return HunterUtility.setJSONObjectForFailure(message, "Applicaiton error : " + e.getMessage()).toString();
		}
	}
	
}
