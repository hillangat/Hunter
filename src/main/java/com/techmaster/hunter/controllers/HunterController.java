package com.techmaster.hunter.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterRawReceiverUserDao;
import com.techmaster.hunter.enums.HunterUserRolesEnums;
import com.techmaster.hunter.obj.beans.HunterRawReceiverUser;
import com.techmaster.hunter.util.HunterUtility;

@Controller
@RequestMapping("/hunter")
public class HunterController extends HunterBaseController{
	
	private Logger logger = Logger.getLogger(getClass());
	
	@RequestMapping(value="/login/page", method=RequestMethod.GET)
	public String loginHome(){
		return "access/login";
	}
	
	@RequestMapping(value="/testing/socialMessageView", method=RequestMethod.GET)
	public String socialMessageView(){
		return "views/socialMessageView";
	}
	

	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String home(){
		
		Collection<? extends GrantedAuthority> grantedAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		List<GrantedAuthority> authList = new ArrayList<>();
		authList.addAll(grantedAuthorities);
		
		String homePage = "views/hunterHome";
		
		for( GrantedAuthority grantedAuthority : grantedAuthorities ){
			String userRole = grantedAuthority.getAuthority();
			if(userRole.equals(HunterUserRolesEnums.HNTR_RW_MSG_RCVR_USR.getName())){
				for(GrantedAuthority authority : grantedAuthorities){
					if(HunterUserRolesEnums.ROLE_ADMIN.getName().equals(authority.getAuthority())){
						return homePage;
					}
				}
				
				//if the user has not been registered as row user and is raw user, turn them away
				HunterRawReceiverUserDao rawReceiverUserDao = HunterDaoFactory.getObject(HunterRawReceiverUserDao.class);
				HunterRawReceiverUser rawReceiver = rawReceiverUserDao.getRawUserByUserName(getUserName()); 
				if(rawReceiver == null){
					return "views/fieldProfileNotFound";
				}
				
				// refresh regions so it the page is fast enough for users.
				if(!HunterCacheUtil.getInstance().isCountriesLoaded()){
					HunterCacheUtil.getInstance().loadCountries();
				}
				return "views/fieldProfile";
			}
		}
		
		return homePage;
		
	}
	
	@RequestMapping(value="/login/params", method=RequestMethod.POST)
	public String performLogin(){
		return "access/login";
	}
	
	
	@RequestMapping(value="/login/after", method=RequestMethod.GET)
	public String postLogin(){
		
		Collection<? extends GrantedAuthority> grantedAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		List<GrantedAuthority> authList = new ArrayList<>();
		authList.addAll(grantedAuthorities);
		
		String homePage = "views/hunterHome";
		
		for( GrantedAuthority grantedAuthority : grantedAuthorities ){
			String userRole = grantedAuthority.getAuthority();
			if(userRole.equals(HunterUserRolesEnums.HNTR_RW_MSG_RCVR_USR.getName())){
				for(GrantedAuthority authority : grantedAuthorities){
					if(HunterUserRolesEnums.ROLE_ADMIN.getName().equals(authority.getAuthority())){
						return homePage;
					}
				}
				
				//if the user has not been registered as row user and is raw user, turn them away
				HunterRawReceiverUserDao rawReceiverUserDao = HunterDaoFactory.getObject(HunterRawReceiverUserDao.class);
				HunterRawReceiverUser rawReceiver = rawReceiverUserDao.getRawUserByUserName(getUserName()); 
				if(rawReceiver == null){
					return "views/fieldProfileNotFound";
				}
				
				// refresh regions so it the page is fast enough for users.
				if(!HunterCacheUtil.getInstance().isCountriesLoaded()){
					HunterCacheUtil.getInstance().loadCountries();
				}
				return "views/fieldProfile";
			}
		}
		
		
		return homePage;
	}
	
	@RequestMapping(value="/login/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null){
			logger.debug("User was authenticated before. Logging out..."); 
			new SecurityContextLogoutHandler().logout(request, response, authentication);
			logger.debug("Done Logging out..."); 
		}else{
			logger.debug("User was NOT authenticated before. Returning...");
		}
		SecurityContextHolder.getContext().setAuthentication(null);
		HunterUtility.getSessionForRequest(request).invalidate();
	     return "access/logout";
	}
	
	@RequestMapping(value="/tasks/home", method=RequestMethod.GET)
	public String goToHunterTasksHome(HttpServletRequest request, HttpServletResponse response){
		return "access/postLogin";
	}
	
	@RequestMapping(value="/fancy", method=RequestMethod.GET)
	public String goToFancyPage(HttpServletRequest request, HttpServletResponse response){
		return "views/fancyAdmin";
	}
	
	@RequestMapping(value="/jqui", method=RequestMethod.GET)
	public String goTojQueryUI(HttpServletRequest request, HttpServletResponse response){
		return "views/fancyJqueryUIHeader";
	}
	
	
	
	
	

}
