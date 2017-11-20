package com.techmaster.hunter.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.UIMessageConstants;
import com.techmaster.hunter.util.HunterUtility;


public class HunterAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired private HunterUserAuthenticationService hunterUserAuthenticationService;
	private Logger logger = Logger.getLogger(getClass());
	
	public HunterUserAuthenticationService getHunterUserAuthenticationService() {
		return hunterUserAuthenticationService;
	}
	public void setHunterUserAuthenticationService( HunterUserAuthenticationService hunterUserAuthenticationService) {
		this.hunterUserAuthenticationService = hunterUserAuthenticationService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		/* 	Let the login spinner get to work :) 	*/
		HunterUtility.threadSleepFor(1200); 
		
		String name = authentication.getName();
        String password = authentication.getCredentials().toString(); 
        logger.debug("Starting authentication process for user ( " + name + " )"); 
        
        Map<String, Object> results = hunterUserAuthenticationService.authenthicate(name, password);
        String error = results != null && !results.isEmpty() ? results.get("ERROR") != null ? results.get("ERROR").toString() : null : null;
        
        if(error != null && error.equals(HunterCacheUtil.getInstance().getUIMsgTxtForMsgId(UIMessageConstants.NOT_RECOGNIZED_CREDENTIALS))){
        	logger.debug(error); 
        	throw new BadCredentialsException(error);
        }
        
        boolean blocked = results != null && !results.isEmpty() ? Boolean.parseBoolean(results.get("BLOCKED").toString()) : false;
        @SuppressWarnings("unchecked") List<String> refRoles =  (List<String>)results.get(HunterUserAuthenticationService.ROLES_KEY);
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        
        if(blocked){
        	String message = error + "</br>" + "Account BLOCKED!! Login failed attempts ( " + results.get("COUNT")  +" ) exeeded limit ( 5 )<br/>Please contact Hunter administrator.";
        	logger.debug(message);
        	throw new LockedException(message); 
        }else if(!blocked && results.get("REM_COUNT") != null && !refRoles.isEmpty()) {
        	int remCount = Integer.valueOf(results.get("REM_COUNT") + "");
        	String message = error;
        	if( remCount == 0 ){
        		message = message + "<br/>This is your last failed login attemp!</span> )";
        	}else{
        		message = message + "<br/>Remaining failed attempts ( <span style='font-size:16px;color:red;'  > " + results.get("REM_COUNT") + "</span> )";
        	}
        	logger.debug(message);
        	throw new BadCredentialsException(message);
        }
        
        if(error == null){
            if(refRoles != null && !refRoles.isEmpty()){
            	for(String role : refRoles){
            		grantedAuths.add(new SimpleGrantedAuthority (role));
            	}
            }else{
            	error = "No permissions granted! Please contact Hunter Security administrator.";
            }
        }
        
        if (error == null && !grantedAuths.isEmpty()) {
            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
            return auth;
        } else {
        	logger.debug("Access denied : " + error); 
        	throw new BadCredentialsException(error);
        }
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		 return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	
	
}
