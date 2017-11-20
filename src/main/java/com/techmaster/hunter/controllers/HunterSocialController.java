package com.techmaster.hunter.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.json.HunterSelectValue;
import com.techmaster.hunter.json.HunterSocialAppJson;
import com.techmaster.hunter.json.HunterSocialGroupJson;
import com.techmaster.hunter.json.HunterSocialRegionJson;
import com.techmaster.hunter.obj.beans.HunterSocialApp;
import com.techmaster.hunter.obj.beans.HunterSocialRegion;
import com.techmaster.hunter.social.HunterSocialHelper;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterUtility;

@Controller
@RequestMapping(value="/social")
public class HunterSocialController extends HunterBaseController{
	
	@Autowired private HunterHibernateHelper hunterHibernateHelper;
	

	@Produces("application/json") 
	@RequestMapping(value="/action/apps/dropdowns", method = RequestMethod.POST)
	@ResponseBody
	public List<HunterSelectValue> getAllAPPsKeyVals(){
		List<HunterSelectValue> pairs = new ArrayList<HunterSelectValue>();
		List<HunterSocialApp> socialApps = hunterHibernateHelper.getAllEntities(HunterSocialApp.class);
		for(int i=0; i<socialApps.size(); i++){
			HunterSelectValue pair = new HunterSelectValue();
			pair.setText(socialApps.get(i).getAppName());
			pair.setValue(HunterUtility.getStringOrNullOfObj(socialApps.get(i).getAppId()));
			pairs.add(pair);
		}
		return pairs;
	}
	
	@Produces("application/json")
	@Consumes("application/json")
	@RequestMapping(value="/action/apps/create", method = RequestMethod.POST)
	@ResponseBody
	public HunterSocialAppJson createSocialApp(@RequestBody HunterSocialAppJson socialAppJson){
		HunterSocialHelper.getInstance().createOrUpdateSocialAppFromJson(socialAppJson, getAuditInfo());
		return socialAppJson;
	}
	
	
	@Produces("application/json")
	@Consumes("application/json")
	@RequestMapping(value="/action/apps/read", method = RequestMethod.POST)
	@ResponseBody
	public List<HunterSocialAppJson> readSocialApps(){
		return HunterSocialHelper.getInstance().getAllSocialAppsJsons();
	}
	
	@Produces("application/json")
	@Consumes("application/json")
	@RequestMapping(value="/action/apps/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSocialApp( @RequestBody HunterSocialAppJson socialAppJson ){
		String message = HunterSocialHelper.getInstance().validateAndDeleteSocialApp(socialAppJson.getAppId());
		if( message == null ){
			return HunterUtility.setJSONObjectForSuccess(null, "Social app deleted successfully").toString();
		}else{
			return HunterUtility.setJSONObjectForSuccess(null, "Error:" + message).toString();
		}
	}
	

	@Produces("application/json")
	@Consumes("application/json")
	@RequestMapping(value="/action/groups/read", method = RequestMethod.POST)
	@ResponseBody
	public List<HunterSocialGroupJson> readSocialGroups(  ){
		return HunterSocialHelper.getInstance().getAllSocialGroupsJsons();
	}
	
	@Produces("application/json")
	@Consumes("application/json")
	@RequestMapping(value="/action/groups/update", method = RequestMethod.POST)
	@ResponseBody
	public HunterSocialGroupJson updateSocialGroups( @RequestBody HunterSocialGroupJson socialGroupJson ){
		return HunterSocialHelper.getInstance().createOrUpdateSocialGroup(socialGroupJson, getAuditInfo());
	}
	
	@Produces("application/json")
	@Consumes("application/json")
	@RequestMapping(value="/action/groups/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSocialGroups( @RequestBody HunterSocialGroupJson socialGroupJson ){
		String message = HunterSocialHelper.getInstance().tryDeleteSocialGroup(socialGroupJson.getGroupId());
		if( message == null ){
			return HunterUtility.setJSONObjectForSuccess(null, "Successfully deleted social group").toString();
		}else{
			return HunterUtility.setJSONObjectForFailure(null, "Error:" + message).toString();
		}
	}
	
	
	@Produces("application/json")
	@Consumes("application/json")
	@RequestMapping(value="/action/regions/read", method = RequestMethod.POST)
	@ResponseBody
	public List<HunterSocialRegionJson> readSocialRegions(  ){
		return HunterSocialHelper.getInstance().getAllSocialRegionsJsons();
	}
	
	
	@Produces("application/json")
	@Consumes("application/json")
	@RequestMapping(value="/action/regions/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSocialRegions( @RequestBody Map<String, String> socialRegionId ){
		Long regionId = HunterUtility.getLongFromObject(socialRegionId.get("regionId")); 
		if( regionId == null || regionId == 0 ){
			return HunterUtility.setJSONObjectForFailure(null, "Region id("+ regionId +") is invalid").toString();
		}
		String message = HunterSocialHelper.getInstance().validateAndDeleteSocialRegion(regionId);
		if( message == null){
			return HunterUtility.setJSONObjectForSuccess(null, "Successfully deleted social region").toString();
		}else{
			return HunterUtility.setJSONObjectForFailure(null, message).toString();
		}
	}
	
	@Produces("application/json")
	@Consumes("application/json")
	@RequestMapping(value="/action/regions/createOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public String updateSocialRegions( @RequestBody HunterSocialRegionJson hunterSocialRegionJson ){
		HunterSocialRegion socialRegion = HunterSocialHelper.getInstance().createOrUpdateSocialRegion(hunterSocialRegionJson, getAuditInfo());
		if( socialRegion != null && hunterSocialRegionJson.getRegionId() >0  ){
			return HunterUtility.setJSONObjectForSuccess(null, "Successfully updated social region").toString();
		}else{
			return HunterUtility.setJSONObjectForFailure(null, "Social region update failed!").toString(); 
		}
	}
	
	
	@Produces("application/json")
	@RequestMapping(value="/action/region/getAssignableHunterUsers", method = RequestMethod.POST)
	@ResponseBody
	public List<HunterSelectValue> getAssignableHunterUsers( ){
		return HunterSocialHelper.getInstance().getAssignableRawReceiverUsers();
	}
	
	@RequestMapping(value="/action/regions/home", method = RequestMethod.GET)
	public String goToSocialRegionsHOme(  ){
		return "views/socialRegions";
	}
	
	
	
	@RequestMapping(value="/action/groups/home", method=RequestMethod.GET)
	public String goToSocialGroups(){
		return "views/socialGroups";
	}
	
	@RequestMapping(value="/action/socialApp/home", method=RequestMethod.GET)
	public String goToSocialApps(){
		return "views/socialApp";
	}
	
	@Consumes("application/json")
	@Produces("application/json")
	@RequestMapping(value = "/action/regions/readSelVal", method = RequestMethod.POST)
	public @ResponseBody List<HunterSelectValue> readAllSocialRegions(){ 
		List<HunterSelectValue> selectValues = new ArrayList<HunterSelectValue>();
		List<HunterSocialRegion> socialRegions = hunterHibernateHelper.getAllEntities(HunterSocialRegion.class);
		if( HunterUtility.isCollectionNotEmpty(socialRegions)){
			for(HunterSocialRegion socialRegion : socialRegions){
				HunterSelectValue selectValue = new HunterSelectValue();
				selectValue.setText(socialRegion.getRegionName()); 
				selectValue.setValue(Long.toString(socialRegion.getRegionId())); 
				selectValues.add(selectValue);
			}
		}
		return selectValues;
	}
	
	@Consumes("application/json")
		@Produces("application/json")
		@RequestMapping(value = "/action/groups/changeStatus", method = RequestMethod.POST)
		public @ResponseBody String editSocialGroups(@RequestBody Map<String,Object> params){
			try{
				String update = "UPDATE HNTR_SCL_GRP SET STS = ? WHERE GP_ID = ?";
				List<Object> values = new ArrayList<>();
				values.add(params.get("toStatus"));
				values.add(params.get("groupId"));
				HunterDaoFactory.getDaoObject(HunterJDBCExecutor.class).executeUpdate(update, values);
				return HunterUtility.setJSONObjectForSuccess(null, "Successfully changed group status.").toString();
			}catch(Exception e){
				e.printStackTrace();
				return HunterUtility.setJSONObjectForFailure(null, e.getMessage()).toString();
			}
		}
		
	
	
	
}
