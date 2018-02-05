package com.techmaster.hunter.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techmaster.hunter.angular.data.AngularData;
import com.techmaster.hunter.angular.data.HunterAngularDataHelper;
import com.techmaster.hunter.angular.grid.GridDataQueryReq;
import com.techmaster.hunter.angular.grid.GridQueryHandler;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.constants.HunterDaoConstants;
import com.techmaster.hunter.constants.HunterURLConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterClientDao;
import com.techmaster.hunter.dao.types.HunterUserDao;
import com.techmaster.hunter.dao.types.TaskDao;
import com.techmaster.hunter.json.TaskAngular;
import com.techmaster.hunter.obj.beans.HunterClient;
import com.techmaster.hunter.util.HunterUtility;


@CrossOrigin( origins=HunterConstants.ALLOWED_CORS_ORIGINS, maxAge=3600 )
@Controller
@RequestMapping( value = "/restful/" )
public class RestfulServicesController {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Produces("application/json") 
	@Consumes("application/json")
	@ResponseBody
	@RequestMapping( value = "sample", method = RequestMethod.POST )
	public String getSampleService(){
		return HunterUtility.setJSONObjectForSuccess(null, "Successful sample").toString(); 
	}
	
	@RequestMapping(value="client/action/angular/read", method = RequestMethod.POST)
	@Produces("application/json") 
	@Consumes("application/json")
	public @ResponseBody String getClientsForAngularUI( @RequestBody Map<String, String> params, HttpServletResponse response ){
		try{
			this.logger.debug( HunterAngularDataHelper.getIntance().getJsonStr(HunterDaoConstants.TASK_GRID_HEADERS) );
			HunterUtility.threadSleepFor(500);
			HunterUserDao userDao = HunterDaoFactory.getDaoObject(HunterUserDao.class);	
			return userDao.getClientsForAngularUI();
		}catch (Exception e) {
			e.printStackTrace();
			return HunterUtility.getServerError("Error occurred while getting clients").toString(); 
		}
	}
	
	@RequestMapping(value="client/action/angular/update", method = RequestMethod.POST)
	@Produces("application/json") 
	@Consumes("application/json")
	public @ResponseBody String updateClientsForAngularUI( @RequestBody Map<String, String> params, HttpServletResponse response ){
		try{
			return HunterUtility.getServerSuccess("Successfully updated client").toString();
		}catch (Exception e) {
			e.printStackTrace();
			return HunterUtility.getServerError("Error occurred while getting clients").toString(); 
		}
	}
	
	@RequestMapping(value="client/action/angular/create", method = RequestMethod.POST)
	@Produces("application/json") 
	@Consumes("application/json")
	public @ResponseBody String createClientsForAngularUI( @RequestBody Map<String, String> params, HttpServletResponse response ){
		try{			
			HunterClientDao clientDao = HunterDaoFactory.getDaoObject(HunterClientDao.class);
			Long nextId = clientDao.nextClientId();
			HunterClient client = new HunterClient();			
			client.setClientId(nextId);
			client.setFirstName(params.get("firstName"));
			client.setLastName(params.get("firstName")); 
			client.setEmail(params.get("email"));
			client.setUserName(params.get("userName"));
			client.setClientTotalBudget(Float.parseFloat(params.get("clientTotalBudget")));
			client.setCreatedBy(params.get("createdBy"));
			client.setCretDate(HunterUtility.parseDate(params.get("cretDate"), HunterConstants.DATE_FORMAT_STRING));
			client.setLastUpdate(HunterUtility.parseDate(params.get("updatedOn"), HunterConstants.DATE_FORMAT_STRING)); 
			client.setLastUpdatedBy(params.get("lastUpdatedBy"));
			client.setReceiver(Boolean.parseBoolean(params.get("receiver")));			
			clientDao.insertHunterClient(client); 
			return HunterUtility.getServerSuccess("Successfully updated client").toString();
		}catch (Exception e) {
			e.printStackTrace();
			return HunterUtility.getServerError("Error occurred while getting clients").toString(); 
		}
	}
	
	
	@RequestMapping(value="workflow/tree/read", method = RequestMethod.POST)
	@Produces("application/json") 
	@Consumes("application/json")
	public @ResponseBody String getWorkflowTree( @RequestBody Map<String, String> params, HttpServletResponse response ){
		try{
			HunterUtility.threadSleepFor(500);
			String xmlLoc = HunterURLConstants.RESOURCE_BASE_PATH + "jsons\\workflow_trees.json";			
			JSONObject trees = new JSONObject(HunterUtility.convertFileToString(xmlLoc));
			JSONArray jsonArray = trees.getJSONArray("trees");
			return HunterUtility.getServerResponse(HunterConstants.STATUS_SUCCESS, HunterConstants.STATUS_SUCCESS, jsonArray).toString();
		}catch (Exception e) {
			e.printStackTrace();
			return HunterUtility.getServerError("Error occurred while getting workflow trees").toString();
		}
	}
	
	@RequestMapping(value="/tasks/read/{scope}", method = RequestMethod.POST)
	@Produces("application/json") 
	@Consumes("application/json")
	public @ResponseBody Object getAllAngularTasks( @PathVariable("scope") String scope, HttpServletRequest request ){
		HunterUtility.threadSleepFor(500);
		List<TaskAngular> angularTasks = GridQueryHandler.getInstance().executeQridQueryForRequest(TaskAngular.class, request);
		this.logger.debug(HunterUtility.stringifyList(angularTasks)); 
		if ( scope != null && scope.equals("all") ){
			List<TaskAngular> tasks = HunterDaoFactory.getDaoObject(TaskDao.class).getAllAngularTasks();
			AngularData angData = HunterAngularDataHelper.getIntance().getDataBean(tasks, HunterDaoConstants.TASK_GRID_HEADERS);
			return angData;
		} else if ( HunterUtility.isNumeric(scope) ) {
			List<TaskAngular> tasks = HunterDaoFactory.getDaoObject(TaskDao.class).getClientAngularTasks( HunterUtility.getLongFromObject(scope) );
			AngularData angData = HunterAngularDataHelper.getIntance().getDataBean(tasks, HunterDaoConstants.TASK_GRID_HEADERS);
			return angData;
		} else {
			return HunterUtility.getServerError("Invalid client id : " + scope ).toString();
		}
			
	}
	
	
	

}
