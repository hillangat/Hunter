package com.techmaster.hunter.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterClientDao;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.HunterUserDao;
import com.techmaster.hunter.json.HunterClientsDetailsJson;
import com.techmaster.hunter.json.HunterUserJson;
import com.techmaster.hunter.obj.beans.HunterUser;
import com.techmaster.hunter.util.HunterUtility;


@Controller
@RequestMapping(value="/hunteruser")
public class UserController {
	
	@Autowired private HunterUserDao userDao;
	@Autowired private HunterClientDao hunterClientDao;
	private Logger logger = Logger.getLogger(getClass());
	
	@RequestMapping(value="/action/create", method=RequestMethod.POST )
	@Produces("application/json")
	@Consumes("application/json")
	public @ResponseBody HunterUser createUser(@RequestBody Map<String,String> data){
		
		HunterUser hunterUser = new HunterUser();
		Long userId = userDao.getNextUserId();
		logger.debug("Assigned the id to the user >> " + userId); 
		hunterUser.setUserId(userId);
		hunterUser.setAddresses(null); 
		hunterUser.setCreatedBy("hlangat01"); 
		hunterUser.setCreditCards(null); 
		hunterUser.setCretDate(new Date()); 
		hunterUser.setEmail(data.get("email")); 
		hunterUser.setFirstName(data.get("firstName")); 
		hunterUser.setMiddleName(data.get("middleName")); 
		hunterUser.setLastName(data.get("lastName")); 
		hunterUser.setLastUpdate(new Date()); 
		hunterUser.setLastUpdatedBy("hlangat01"); 
		hunterUser.setPassword(data.get("password"));
		hunterUser.setPhoneNumber(data.get("phoneNumber"));
		hunterUser.setProfilePhoto(null);
		hunterUser.setUserName(data.get("userName"));
		hunterUser.setUserType(HunterConstants.HUNTER_CLIENT_USER);  
		logger.debug("created the following user >> " + hunterUser); 
		userDao.insertHunterUser(hunterUser); 
		
		return hunterUser;
	}
	
	@RequestMapping(value="/action/read/post", method=RequestMethod.POST )
	@Produces("application/json")
	public @ResponseBody List<HunterUserJson> readUsersPost(HttpServletResponse response){
		List<HunterUserJson> users = userDao.getAllUsersWhoAreClients();
		return users;
	}
	
	@RequestMapping(value="/action/update", method=RequestMethod.POST )
	@Produces("application/json")
	@Consumes("application/json")
	public @ResponseBody HunterUser updateUser(@RequestBody HunterUser hunterUser){
		logger.debug("Received user to update from kendo >> " + hunterUser); 
		userDao.updateUser(hunterUser); 
		return hunterUser;
	}
	
	@RequestMapping(value="/action/destroy", method=RequestMethod.POST )
	@Produces("application/json")
	@Consumes("application/json")
	public @ResponseBody HunterUser deleteUser(@RequestBody HunterUser hunterUser){
		logger.debug("Received user delete from kendo >> " + hunterUser); 
		userDao.deleteHunterUser(hunterUser); 
		return hunterUser;
	}
	
	
	@RequestMapping(value="/action/loginError", method=RequestMethod.GET )
	public String loginError(HttpServletResponse response){
		logger.debug("Redirected successfully!!"); 
		return "access/loginError";
	}
	
	@RequestMapping(value="/action/login", method=RequestMethod.GET )
	public String login(HttpServletResponse response){
		logger.debug("Redirected successfully!!"); 
		return "access/login";
	}
	
	@RequestMapping(value="/action/user/profile/home", method=RequestMethod.GET )
	public String goToMyHunterHome(HttpServletResponse response){
		logger.debug("Redirected successfully!!"); 
		return "views/myHunter";
	}
	
	@RequestMapping(value="/action/client/getAllClientsDetails", method=RequestMethod.POST )
	@Produces("application/json")
	@ResponseBody public List<HunterClientsDetailsJson> getClientDetailsData(){
		 List<HunterClientsDetailsJson> jsons = hunterClientDao.getAllHunterClientDetailsJson();
		 return jsons;
	}
	
	@RequestMapping(value="/action/user/unblock/{userName}", method=RequestMethod.GET )
	@Produces("application/json")
	@Consumes("application/json")
	@ResponseBody public String unBlockUser( @PathVariable("userName") String userName ){
		try{
			HunterJDBCExecutor jdbcExecutor = HunterDaoFactory.getDaoObject(HunterJDBCExecutor.class);
			 String query = jdbcExecutor.getQueryForSqlId("unblockUserByUserName");
			 List<Object> values = new ArrayList<>();
			 values.add(userName);
			 int rows = jdbcExecutor.executeUpdate(query, values);
			 if( rows == 0 ){
				 return HunterUtility.setJSONObjectForFailure(null, "No user found for update!").toString();
			 }else{
				 return HunterUtility.setJSONObjectForSuccess(null, "Successfully updated user").toString();
			 }
		}catch(Exception e){
			return HunterUtility.setJSONObjectForFailure(null, "Application error occurred while unblocking user").toString();
		}
	}
	
	
	

}
