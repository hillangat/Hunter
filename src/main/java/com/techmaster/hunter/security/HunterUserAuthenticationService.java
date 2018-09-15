package com.techmaster.hunter.security;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.constants.UIMessageConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.HunterUserDao;
import com.techmaster.hunter.exception.HunterRunTimeException;
import com.techmaster.hunter.obj.beans.HunterUser;
import com.techmaster.hunter.obj.beans.UserLoginBean;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterSessionFactory;
import com.techmaster.hunter.util.HunterUtility;
import com.techmaster.hunter.xml.XMLService;
import com.techmaster.hunter.xml.XMLServiceImpl;
import com.techmaster.hunter.xml.XMLTree;


public class HunterUserAuthenticationService {
	
	@Autowired private HunterJDBCExecutor hunterJDBCExecutor;
	@Autowired private HunterUserDao hunterUserDao;
	
	public static final String ROLES_KEY = "ROLES_KEY";
	public static final int MAX_USER_LOGIN_COUNT = 5;
	private Logger logger = Logger.getLogger(getClass());
	
	
   /*
		AccountStatusException, 
		ActiveDirectoryAuthenticationException, 
		AuthenticationCancelledException, 
		AuthenticationCredentialsNotFoundException, 
		AuthenticationServiceException, 
		BadCredentialsException, 
		InsufficientAuthenticationException, 
		NonceExpiredException, 
		PreAuthenticatedCredentialsNotFoundException, 
		ProviderNotFoundException, 
		RememberMeAuthenticationException, 
		SessionAuthenticationException, 
		UsernameNotFoundException
    */
    
	public HunterJDBCExecutor getHunterJDBCExecutor() {
		return hunterJDBCExecutor;
	}
	public void setHunterJDBCExecutor(HunterJDBCExecutor hunterJDBCExecutor) {
		this.hunterJDBCExecutor = hunterJDBCExecutor;
	}
	
	public XMLService getLoginData(String userName){
		XMLService xmlService = null;
		List<Object> values = new ArrayList<>();
		values.add(userName);
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getDaoObject(HunterJDBCExecutor.class);
	    String query = hunterJDBCExecutor.getQueryForSqlId("callGetLoginDataFunc");
	    String result = HunterUtility.getStringOrNullOfObj(hunterJDBCExecutor.executeQueryForOneReturn(query, values));
	    if( HunterUtility.notNullNotEmpty(result) ){
	    	xmlService = HunterUtility.getXMLServiceForStringContent( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>" + result);
	    }
	    return xmlService;
	}
	
	public List<String> getUserRoles(String userName, XMLService loginData){
		List<String> roles = new ArrayList<>();
		if( loginData != null  ){
			NodeList rolesNodes = loginData.getNodeListForPathUsingJavax("loginData/roles/role");
			for(int i=0; i<rolesNodes.getLength();i++){
				Node role = rolesNodes.item(i);
				String roleName = role.getTextContent();
				roles.add(roleName);
			}
		}
		logger.debug("User ( " + userName + " ) has roles = " + HunterUtility.getCommaDelimitedStrings(roles.toArray()));  
		return roles;
	}
	
	public Map<String,String> getNewLoginBeanData(){
		Map<String,String> beanData = new HashMap<>();
		beanData.put("userId", null);
		beanData.put("lastLoginTime", HunterUtility.formatDate(new Date(), HunterConstants.DATE_FORMAT_STRING)); 
		beanData.put("blocked", Boolean.FALSE.toString());
		beanData.put("failedLoginCount", Integer.toString(0));
		return beanData;
	}
	
	public Map<String,String> getLoginBeanData(XMLService loginData){
		NodeList loginNodes = loginData.getNodeListForPathUsingJavax("loginData/login");
		Node login = loginNodes == null ? null : loginNodes.item(0);
		Map<String,String> loginBean = getNewLoginBeanData();
		if( login != null ){
			loginBean.put("userId", HunterUtility.getNodeAttr(login, "userId", String.class));
			loginBean.put("lastLoginTime", HunterUtility.getNodeAttr(login, "lastLoginTime", String.class));
			loginBean.put("blocked", HunterUtility.getNodeAttr(login, "blocked", String.class));
			loginBean.put("failedLoginCount", HunterUtility.getNodeAttr(login, "failedLoginCount", String.class));
			loginBean.put("loginExist", Boolean.toString(true));
		}else{
			loginBean.put("loginExist", Boolean.toString(false));
		}
		logger.debug("Login bean data = " + HunterUtility.stringifyMap(loginBean)); 
		return loginBean;
	}
	
	public Map<String, Object> getUserNamePasswordAndRole(String userName){
		
		String query = hunterJDBCExecutor.getQueryForSqlId("getUserRoleDetails");
		List<Object> values = new ArrayList<Object>();
		values.add(userName);
		values.add(userName);
		
		List<Map<String, Object>>  listMap = hunterJDBCExecutor.executeQueryRowMap(query, values);
		Map<String, Object> results = new HashMap<>();
		
		if(listMap != null && !listMap.isEmpty()){
			for(Map<String, Object> map : listMap){
				String password = map.get("PSSWRD") == null ? null : map.get("PSSWRD") + "";
				String userName_ = map.get("USR_NAM") == null ? null : map.get("USR_NAM") + "";
				if(password != null && userName_ != null){
					results.put("PSSWRD", password);
					results.put("USR_NAM", userName_);
					break;
				}else if(userName_ == null && password != null){
					results.put("PSSWRD", password);
				}
			}
		}else{
			return results;
		}
		
		List<String> roles = new ArrayList<>();
		
		for(Map<String, Object> map : listMap){
			String role = map.get("RL_NAM") != null ? map.get("RL_NAM").toString() : null;
			if(role != null){
				roles.add(role);
			}
		}
		
		results.put(ROLES_KEY, roles);
		
		return results;
	}
	
	public Map<String, Object> authenthicate(String userName, String password){
		
		XMLService xmlService = getLoginData(userName);
		Map<String, Object> params = new HashMap<String, Object>();
		
		//wrong user name.
		if(xmlService == null){
			params.put("ERROR", HunterCacheUtil.getInstance().getUIMsgTxtForMsgId(UIMessageConstants.NOT_RECOGNIZED_CREDENTIALS));
			return params;
		}
		
		List<String> userRoles = getUserRoles(userName, xmlService);
		Map<String,String> loginBeanData = getLoginBeanData(xmlService);
		
		params.put(HunterUserAuthenticationService.ROLES_KEY, userRoles);
		
		String IPAddress = getIPAddress(getHttpServletRequest());
		
		Node passwordNode = xmlService.getFirstNodeUsingAjaxByName("loginData");
		String refPassword =  HunterUtility.getNodeAttr(passwordNode, "passCode", String.class);
		UserLoginBean userLoginBean = null;
		
		if(refPassword == null || !refPassword.equals(password)){
			userLoginBean = getIncrementedFailureLoginCount(userName, loginBeanData);
			int newCount = userLoginBean.getFaildedLoginCount();
			updateLoginDataXMLForLogin(userLoginBean, HunterUtility.formatDate(new Date(), null), isLocked(newCount) ? "Blocked" : HunterConstants.STATUS_FAILED, IPAddress, userName, password, Integer.toString(newCount)); 
			putParamsForBlockedUnBlocked(params,newCount); 
		}else{
			String status = HunterConstants.STATUS_SUCCESS;
			userLoginBean = getIncrementedFailureLoginCount(userName, loginBeanData);
			int newCount = userLoginBean.getFaildedLoginCount();
			if(!isLocked(newCount)){ 
				logger.debug("Login successful, resetting failed login count"); 
				params.put("BLOCKED", false);
				userLoginBean.setFaildedLoginCount(0);
				userLoginBean.setBlocked(false); 
			}else{
				status = "Blocked";
				logger.debug("Account is blocked and cannot reset failed login counts."); 
				putParamsForBlockedUnBlocked(params,newCount);
			}
			updateLoginDataXMLForLogin(userLoginBean, HunterUtility.formatDate(new Date(), null), status, IPAddress, userName, password, Integer.toString(newCount));
			
		}
		return params;
	}
	
	public void putParamsForBlockedUnBlocked(Map<String, Object> params, int newCount){
		params.put("ERROR", HunterCacheUtil.getInstance().getUIMsgTxtForMsgId(UIMessageConstants.USER_PASSWORD_MISMATCH));
		params.put("BLOCKED", isLocked(newCount) + "" );  
		params.put("COUNT", newCount);
		params.put("REM_COUNT", isLocked(newCount) ? "0" : (MAX_USER_LOGIN_COUNT - newCount )+""); 
	}
	
	public UserLoginBean getIncrementedFailureLoginCount(String userName, Map<String, String>  userLoginDetails){
		int currCnt = Integer.parseInt(userLoginDetails.get("failedLoginCount").toString()) + 1;
		UserLoginBean userLoginBean = getUserLoginBeanByUserName(userName);
		userLoginBean.setLastLoginTime(new Date()); 
		if(userLoginDetails != null && !userLoginDetails.isEmpty()){
			logger.debug("Current failed login count : " + currCnt); 
			if(isLocked(currCnt)){ 
				userLoginBean.setFaildedLoginCount(currCnt);
				userLoginBean.setBlocked(true); 
				logger.debug("User has had more than five failed login. Account being blocked.count : " + currCnt); 
			}else{
				userLoginBean.setFaildedLoginCount(currCnt);
				userLoginBean.setBlocked(false); 
				logger.debug("Increasing failed login count for the user : " + userName); 
			}
		}else{
			logger.debug("Did not find user login for userName : " + userName);  
			userLoginBean.setFaildedLoginCount(currCnt);
			userLoginBean.setBlocked(false);
		}
		return userLoginBean;
	}
	
	public UserLoginBean getUserLoginBeanByUserName(String userName){
		
		SessionFactory sessionFactory = HunterDaoFactory.getDaoObject(HunterSessionFactory.class).getSessionFactory();
		Session session = null;
		Query query = null;
		List<?> userLoginBeans = null;
		
		
		try {
			session = sessionFactory.openSession();
			String hql = "FROM UserLoginBean u WHERE u.userId = ( SELECT h.userId FROM HunterUser h WHERE h.userName = :userName )";      
			query = session.createQuery(hql);
			query.setParameter("userName", userName);
			userLoginBeans = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			HunterHibernateHelper.closeSession(session); 
		}
        
		UserLoginBean userLoginBean = !HunterUtility.isCollNotEmpty( userLoginBeans ) ? createUserLoginForUser(userName) : (UserLoginBean) userLoginBeans.get(0);
		return userLoginBean;
	}
	
	public UserLoginBean createUserLoginForUser(String userName){
		logger.debug("Creating user login bean for the user..."); 
		HunterUser hunterUser = hunterUserDao.getUserByUserName(userName);
		UserLoginBean userLoginBean = new UserLoginBean();
		userLoginBean.setBlocked(false);
		userLoginBean.setLastLoginTime(new Date()); 
		userLoginBean.setFaildedLoginCount(0); 
		userLoginBean.setUserId(hunterUser.getUserId()); 
		hunterUser.setUserLoginBean(userLoginBean);
		hunterUserDao.updateUser(hunterUser); 
		logger.debug("Successfully created user login bean for the user!!"); 
		return userLoginBean;
	}
	
	public boolean isLocked(int loginCount){
		return loginCount > MAX_USER_LOGIN_COUNT;
	}
	
	public int getNextLoginNum(XMLService existingLoginData){
		NodeList nodeList = existingLoginData.getNodeListForPathUsingJavax("//login");
		int loginNum = 1;
		for(int i=0; i<nodeList.getLength();i++){
			Node node = nodeList.item(i);
			if( node.getNodeName().equalsIgnoreCase("login") ){
				int currLoginNum = HunterUtility.getNodeAttr(node, "loginNum", Integer.class);  
				if( currLoginNum > loginNum ){
					loginNum = currLoginNum;
				}
			}
		}
		return loginNum + 1;
	}
	
	public void updateLoginDataXMLForLogin(UserLoginBean userLoginBean, String loginDate,String status,String ipAddress,String userName, String loginPassword, String failedNo){
		
		if(userName == null){
			logger.debug("User login information does not exist for the user name : " + userName ); 
			return;
		}
			
 		
		XMLService loginDataXML = HunterCacheUtil.getInstance().getXMLService(HunterConstants.LOGIN_DATA_SEED_XML);
		
		String mainPath = "loginData/logins/login/";
		loginDataXML.getNodeListForPathUsingJavax( mainPath + "date" ).item(0).setTextContent(loginDate); 
		loginDataXML.getNodeListForPathUsingJavax( mainPath + "status" ).item(0).setTextContent(status); 
		loginDataXML.getNodeListForPathUsingJavax( mainPath + "ip" ).item(0).setTextContent(ipAddress); 
		loginDataXML.getNodeListForPathUsingJavax( mainPath + "userName" ).item(0).setTextContent(userName); 
		loginDataXML.getNodeListForPathUsingJavax( mainPath + "password" ).item(0).setTextContent( Base64.getEncoder().encodeToString(loginPassword.getBytes()) );  
		loginDataXML.getNodeListForPathUsingJavax( mainPath + "currFailedCount" ).item(0).setTextContent(failedNo); 
		
		Blob orinalDocBlob = userLoginBean.getLoginData();
		String loginData = HunterUtility.getBlobStrFromDB("appConfigs", "userId", Long.toString(userLoginBean.getUserId()), UserLoginBean.class);
		
		if( loginData == null ){
			logger.debug("Setting login data for the first time..."); 
			userLoginBean.setLoginData( HunterUtility.getStringBlob( loginDataXML.toString() ) ); 
			HunterDaoFactory.getDaoObject(HunterHibernateHelper.class).updateEntity( userLoginBean ); 
			return;
		}
		
		
		try {
			
			XMLService existingDocument = new XMLServiceImpl( new XMLTree( loginData , true));
			Node node = loginDataXML.getFirstNodeUsingAjaxByName("login");
			((org.w3c.dom.Element )(node)).setAttribute("loginNum", Integer.toString( getNextLoginNum(existingDocument) ));   
			Node node2 = existingDocument.getXmlTree().getDoc().importNode(node, true);
			existingDocument.getFirstNodeUsingAjaxByName("logins").appendChild(node2);
			userLoginBean.setLoginData( HunterUtility.getStringBlob( existingDocument.toString() ) ); 
			
		} catch (HunterRunTimeException | ParserConfigurationException e) {
			e.printStackTrace();
			userLoginBean.setLoginData( orinalDocBlob ); 
		}finally {
			HunterDaoFactory.getDaoObject(HunterHibernateHelper.class).updateEntity( userLoginBean );
		}
		
	}
	
	public HttpServletRequest getHttpServletRequest(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return request;
	}
	
	public String getIPAddress(HttpServletRequest request){
		 String ip = request.getHeader("X-Forwarded-For");  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("Proxy-Client-IP");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("WL-Proxy-Client-IP");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("HTTP_X_FORWARDED");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("HTTP_CLIENT_IP");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("HTTP_FORWARDED_FOR");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("HTTP_FORWARDED");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("HTTP_VIA");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("REMOTE_ADDR");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getRemoteAddr();  
		    }  
		    return ip;  
	}
	
	
}
