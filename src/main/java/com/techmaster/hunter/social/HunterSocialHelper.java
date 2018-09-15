package com.techmaster.hunter.social;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.constants.HunterURLConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.exception.HunterRunTimeException;
import com.techmaster.hunter.json.HunterSelectValue;
import com.techmaster.hunter.json.HunterSocialAppJson;
import com.techmaster.hunter.json.HunterSocialGroupJson;
import com.techmaster.hunter.json.HunterSocialRegionJson;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.HunterSocialApp;
import com.techmaster.hunter.obj.beans.HunterSocialGroup;
import com.techmaster.hunter.obj.beans.HunterSocialMedia;
import com.techmaster.hunter.obj.beans.HunterSocialRegion;
import com.techmaster.hunter.obj.beans.SocialMessage;
import com.techmaster.hunter.obj.beans.TaskProcessJob;
import com.techmaster.hunter.task.process.TaskProcessJobHandler;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterUtility;
import com.techmaster.hunter.xml.XMLService;
import com.techmaster.hunter.xml.XMLServiceImpl;
import com.techmaster.hunter.xml.XMLTree;

public class HunterSocialHelper {
	
	private static HunterSocialHelper instance;
	private static Logger logger = Logger.getLogger(HunterSocialHelper.class);
	private HunterSocialHelper(){}
	
	static{
		if( instance == null ){
			instance = new HunterSocialHelper();
		}
	}
	

	public static HunterSocialHelper getInstance(){
		return instance;
	}
	
	public Map<String,Object> getSclMsgRmtDetails(Long msgId){
		List<Object> values = new ArrayList<>();
		values.add(msgId);
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
		String query = hunterJDBCExecutor.getQueryForSqlId("getSocialMsgRemteDetails");
		List<Map<String,Object>> rowtMapList = hunterJDBCExecutor.executeQueryRowMap(query, values);
		return HunterUtility.isCollNotEmpty(rowtMapList) ?  rowtMapList.get(0) : new HashMap<String, Object>();
	}
	
	public XMLService getSocialAppConfig(HunterSocialApp socialApp){
		logger.debug("Getting social app configurations..."); 
		try {
			XMLService xmlService = new XMLServiceImpl(new XMLTree(HunterURLConstants.HUNTER_SOCIAL_APP_CONFIG_PATH, false));
			String blobStr = HunterUtility.getBlobStrFromDB("appConfigs", "appId", Long.toString(socialApp.getAppId()), HunterSocialApp.class);
			xmlService = socialApp.getAppConfigs() == null ? xmlService : new XMLServiceImpl(new XMLTree( blobStr, true));  
			logger.debug("Successfully retrieved social app configurations : " + xmlService); 
			return xmlService;
		} catch (HunterRunTimeException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}  
		return null;
	}
	
	public List<HunterSocialAppJson> getAllSocialAppsJsons(){
		logger.debug("Getting all social apps jsons...");
		List<HunterSocialApp>  socialApps =   HunterDaoFactory.getObject(HunterHibernateHelper.class).getAllEntities(HunterSocialApp.class);
		List<HunterSocialAppJson> socialAppJsons = new ArrayList<>();
		if( HunterUtility.isCollNotEmpty(socialApps) ){
			for(HunterSocialApp socialApp : socialApps){
				HunterSocialAppJson socialAppJson = new HunterSocialAppJson();
				socialAppJson.setAppDesc(socialApp.getAppDesc());
				socialAppJson.setAppId(socialApp.getAppId()); 
				socialAppJson.setAppName(socialApp.getAppName()); 
				socialAppJson.setCreatedBy(socialApp.getAuditInfo().getCreatedBy()); 
				socialAppJson.setCretDate(socialApp.getAuditInfo().getCretDate()); 
				socialAppJson.setExtrnalPassCode(socialApp.getExtrnalPassCode()); 
				socialAppJson.setExtrnlId(socialApp.getExtrnlId()); 
				socialAppJson.setLastUpdate(socialApp.getAuditInfo().getLastUpdate());
				socialAppJson.setLastUpdatedBy(socialApp.getAuditInfo().getLastUpdatedBy()); 
				socialAppJson.setSocialType(socialApp.getSocialType()); 
				socialAppJsons.add(socialAppJson);
			}
		}
		logger.debug("Finished getting social apps jsons. Size : " + socialAppJsons.size()); 
		return socialAppJsons;
	}
	
	public XMLService setNewSocialAppConfig(HunterSocialApp socialApp, boolean update){
		XMLService xmlService = HunterUtility.getXMLServiceForFileLocation(HunterURLConstants.HUNTER_SOCIAL_APP_CONFIG_PATH);
		socialApp.setAppConfigs(HunterUtility.getStringBlob(xmlService.toString()));
		if( update ){
			 HunterDaoFactory.getObject(HunterHibernateHelper.class).updateEntity(socialApp); 
		}
		return xmlService;
	}
	
	public HunterSocialApp createHunterSocialApp(HunterSocialApp socialApp){
		logger.debug("creating social app..." + socialApp); 
		 HunterDaoFactory.getObject(HunterHibernateHelper.class).saveEntity(socialApp);
		logger.debug("Successfully created social app " + socialApp); 
		return socialApp;
	}
	
	public HunterSocialAppJson createOrUpdateSocialAppFromJson (HunterSocialAppJson hunterSocialAppJson, AuditInfo auditInfo){
		
		logger.debug("Creating or updating social app ( " + hunterSocialAppJson +" )"); 
		boolean updateApp = hunterSocialAppJson.getAppId() != 0 && hunterSocialAppJson.getAppId() != null;
		HunterSocialApp socialApp = null;
		
		if( updateApp ){
			socialApp =  HunterDaoFactory.getObject(HunterHibernateHelper.class).getEntityById(hunterSocialAppJson.getAppId(), HunterSocialApp.class);
			auditInfo.setCreatedBy(socialApp.getAuditInfo().getCreatedBy());
			auditInfo.setCretDate(socialApp.getAuditInfo().getCretDate());
		}else{
			 socialApp = new HunterSocialApp();
		}
		
		socialApp.setAppConfigs(null);
		socialApp.setAppDesc(hunterSocialAppJson.getAppDesc()); 
		socialApp.setAppName(hunterSocialAppJson.getAppName());
		socialApp.setAuditInfo(auditInfo); 
		socialApp.setExtrnlId(hunterSocialAppJson.getExtrnlId()); 
		socialApp.setSocialType(hunterSocialAppJson.getSocialType()); 
		socialApp.setExtrnalPassCode(hunterSocialAppJson.getExtrnalPassCode()); 
		
		if( updateApp ){
			logger.debug("Updating ( " + socialApp +" )"); 
			 HunterDaoFactory.getObject(HunterHibernateHelper.class).updateEntity(socialApp);
		}else{
			logger.debug("Creating ( " + socialApp +" )"); 
			 HunterDaoFactory.getObject(HunterHibernateHelper.class).saveEntity(socialApp);
		}
		
		hunterSocialAppJson.setAppId(socialApp.getAppId());
		
		return hunterSocialAppJson;
	}
	
	public String validateAndDeleteSocialApp(Long appId){
		
		logger.debug("Trying to delete social app of app ID ( "+ appId +" )");
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
		String query = hunterJDBCExecutor.getQueryForSqlId("getSocialGroupsUsingSocialAppId"); 
		List<Object> values = new ArrayList<>();
		values.add(appId);
		List<Map<String, Object>> rowMapsList = hunterJDBCExecutor.executeQueryRowMap(query,values );
		StringBuilder builder = new StringBuilder();
		if( HunterUtility.isCollNotEmpty(rowMapsList) ){
			int counter = 1;
			for(Map<String, Object> rowMap : rowMapsList){
				String groupName = HunterUtility.getStringOrNullOfObj(rowMap.get("GRP_NAM"));
				builder.append("( ").append(counter).append(" ) ").append(groupName).append(",");
				counter++;
			}
			String bstr = builder.toString();
			if( bstr.contains(",") ){
				bstr = bstr.substring(0, bstr.length() - 1 );
			}
			logger.debug("Cannot delete social region since it is being used by social groups : " + bstr);
			return bstr;
		}else{
			 HunterDaoFactory.getObject(HunterHibernateHelper.class).deleteEntityByLongId(appId, HunterSocialApp.class); 
			return null;
		}
		
	}
	
	public HunterSocialApp updateHunterSocialApp(HunterSocialApp socialApp){
		logger.debug("Updating social app..." + socialApp); 
		 HunterDaoFactory.getObject(HunterHibernateHelper.class).updateEntity(socialApp); 
		logger.debug("Successfully updated social app!"); 
		return socialApp;
	}
	
	public void deleteHunterSocialApp(HunterSocialApp socialApp){
		logger.debug("Deleting social app : " + socialApp); 
		 HunterDaoFactory.getObject(HunterHibernateHelper.class).deleteEntity(socialApp);
		logger.debug("Successfully deleted social app!"); 
	}
	
	public String tryDeleteSocialGroup(Long groupId){
		logger.debug("Deleting social group : " + groupId);
		 HunterDaoFactory.getObject(HunterHibernateHelper.class).deleteEntityByLongId(groupId, HunterSocialGroup.class);
		logger.debug("Successfully deleted social app!"); 
		return null;
	}
	
	public void addSocialConfig(HunterSocialApp socialApp,String name, String value){
		XMLService appConfigs = getSocialAppConfig(socialApp);
		Document document = appConfigs.getXmlTree().getDoc();
		Element configs = document.getDocumentElement();
		Element config = document.createElement("config");
		config.setAttribute("name", name); 
		config.setTextContent(value);
		configs.appendChild(config);
	}
	
	public String getSocialConfig(HunterSocialApp socialApp,String name){
		XMLService appConfigs = getSocialAppConfig(socialApp);
		Document document = appConfigs.getXmlTree().getDoc();
		Element configs = document.getDocumentElement();
		NodeList configss = configs.getChildNodes();
		if( HunterUtility.isNodeListNotEmptpy(configss) ){
			for(int i=0; i<configss.getLength();i++){
				Node node = configss.item(i);
				if( node.getNodeName().equals("config") && HunterUtility.getNodeAttr(node, "name", String.class).equals(name) ){ 
					return node.getTextContent();
				}
			}
		}
		return null;
	}
	
	public HunterSocialGroupJson createOrUpdateSocialGroup(HunterSocialGroupJson socialGroupJson, AuditInfo auditInfo){
		
		logger.debug("Converting json to social group : " + socialGroupJson); 
		
		HunterSocialGroup socialGroup = new HunterSocialGroup();
		HunterSocialRegion socialRegion =  HunterDaoFactory.getObject(HunterHibernateHelper.class).getEntityById(socialGroupJson.getRegionId(), HunterSocialRegion.class);
		
		if( socialGroupJson.getGroupId() != null && socialGroupJson.getGroupId() != 0 ){
			socialGroup =  HunterDaoFactory.getObject(HunterHibernateHelper.class).getEntityById(socialGroupJson.getGroupId(), HunterSocialGroup.class);
			auditInfo.setCreatedBy(socialGroup.getAuditInfo().getCreatedBy()); 
			auditInfo.setCretDate(socialGroup.getAuditInfo().getCretDate()); 
		}
		
		HunterSocialApp defaultSocialApp =  HunterDaoFactory.getObject(HunterHibernateHelper.class).getEntityById(socialGroupJson.getSocialAppId(), HunterSocialApp.class);
		
		socialGroup.setSocialRegion(socialRegion);
		socialGroup.setAcquired(socialGroupJson.isAcquired());
		socialGroup.setAcquiredFromFullName(socialGroupJson.getAcquiredFromFullName());
		socialGroup.setActive(socialGroupJson.isActive());
		socialGroup.setAuditInfo(auditInfo);
		socialGroup.setClientUserName(socialGroupJson.getClientUserName()); 
		socialGroup.setExistent(socialGroupJson.isExistent()); 
		socialGroup.setExternalId(socialGroupJson.getExternalId()); 
		socialGroup.setGroupDescription(socialGroupJson.getGroupDescription());
		socialGroup.setGroupName(socialGroupJson.getGroupName()); 
		socialGroup.setHunterGroupAdmin(socialGroupJson.getHunterGroupAdmin());
		socialGroup.setHunterOwned(socialGroupJson.isHunterOwned());
		socialGroup.setReceiversCount(socialGroupJson.getReceiversCount());
		socialGroup.setSocialType(socialGroupJson.getSocialType()); 
		socialGroup.setSuspended(socialGroupJson.isSuspended()); 
		socialGroup.setSuspensionDescription(socialGroupJson.getSuspensionDescription()); 
		socialGroup.setVerifiedBy(socialGroupJson.getVerifiedBy());
		socialGroup.setDefaultSocialApp(defaultSocialApp); 
		
		String verifiedDate = socialGroupJson.getVerifiedDate();
		socialGroup.setVerifiedDate(verifiedDate == null ? null : HunterUtility.parseDate( verifiedDate, HunterConstants.DATE_FORMAT_STRING )); 
		
		logger.debug("Successfully converted the json. Resultant group : " + socialGroup); 
		
		if( socialGroup.getGroupId() != null &&  socialGroup.getGroupId() != 0){
			logger.debug("Updating the social group...");
			 HunterDaoFactory.getObject(HunterHibernateHelper.class).updateEntity(socialGroup); 
		}else{
			logger.debug("Creating the social group...");
			 HunterDaoFactory.getObject(HunterHibernateHelper.class).saveEntity(socialGroup); 
		}
		
		return socialGroupJson;
	}
	
	private Map<String, String> getSocialRegionNames(HunterSocialRegion socialRegion){
		
		logger.debug("Getting region names for region : " + socialRegion ); 
		
		Map<String,String> regionNames = new HashMap<>();
		
		if( socialRegion == null )
			return regionNames;
		
		Map<String,Long> regionsIds = new HashMap<>();
		regionsIds.put(HunterConstants.RECEIVER_LEVEL_COUNTRY, socialRegion.getCountryId());
		regionsIds.put(HunterConstants.RECEIVER_LEVEL_COUNTY, socialRegion.getCountyId());
		regionsIds.put(HunterConstants.RECEIVER_LEVEL_CONSITUENCY, socialRegion.getConsId());
		regionsIds.put(HunterConstants.RECEIVER_LEVEL_WARD, socialRegion.getWardId());
		
		Map<Long,String> country	  = socialRegion.getCountryId() == null ? new HashMap<Long,String>() : HunterCacheUtil.getInstance().getNameIdForId(HunterConstants.RECEIVER_LEVEL_COUNTRY, regionsIds);
		Map<Long,String> county 	  = socialRegion.getCountyId() 	== null ? new HashMap<Long,String>() : HunterCacheUtil.getInstance().getNameIdForId(HunterConstants.RECEIVER_LEVEL_COUNTY, regionsIds);
		Map<Long,String> constituency = socialRegion.getConsId() 	== null ? new HashMap<Long,String>() : HunterCacheUtil.getInstance().getNameIdForId(HunterConstants.RECEIVER_LEVEL_CONSITUENCY, regionsIds);
		Map<Long,String> ward 		  = socialRegion.getWardId() 	== null ? new HashMap<Long,String>() : HunterCacheUtil.getInstance().getNameIdForId(HunterConstants.RECEIVER_LEVEL_WARD, regionsIds);
		
		String countryName 		= country.get(regionsIds.get(HunterConstants.RECEIVER_LEVEL_COUNTRY)); 
		String countyName 		= county.get(regionsIds.get(HunterConstants.RECEIVER_LEVEL_COUNTY));
		String constituencyName = constituency.get(regionsIds.get(HunterConstants.RECEIVER_LEVEL_CONSITUENCY));
		String wardName 		= ward.get(regionsIds.get(HunterConstants.RECEIVER_LEVEL_WARD));
		
		regionNames.put(HunterConstants.RECEIVER_LEVEL_COUNTRY, countryName);
		regionNames.put(HunterConstants.RECEIVER_LEVEL_COUNTY, countyName);
		regionNames.put(HunterConstants.RECEIVER_LEVEL_CONSITUENCY, constituencyName);
		regionNames.put(HunterConstants.RECEIVER_LEVEL_WARD, wardName);
		
		logger.debug("Region names retrieved : " + HunterUtility.stringifyMap(regionNames) ); 
		
		return regionNames;
	}
	
	public List<HunterSocialGroupJson> convertSocialGroupToSocialGroupJson(List<HunterSocialGroup> socialGroups){
		List<HunterSocialGroupJson> socialGroupJsons = new ArrayList<>();
		if( HunterUtility.isCollNotEmpty(socialGroups) ){
			for(HunterSocialGroup socialGroup : socialGroups){
				
				HunterSocialGroupJson socialGroupJson = new HunterSocialGroupJson();
				
				socialGroupJson.setAcquired(socialGroup.isAcquired()); 
				socialGroupJson.setAcquiredFromFullName(socialGroup.getAcquiredFromFullName());
				socialGroupJson.setActive(socialGroup.isActive());
				socialGroupJson.setClientUserName(socialGroup.getClientUserName()); 
				socialGroupJson.setExistent(socialGroup.isExistent());
				socialGroupJson.setExternalId(socialGroup.getExternalId());
				socialGroupJson.setGroupDescription(socialGroup.getGroupDescription());
				socialGroupJson.setGroupId(socialGroup.getGroupId());
				socialGroupJson.setGroupName(socialGroup.getGroupName());
				socialGroupJson.setHunterGroupAdmin(socialGroup.getHunterGroupAdmin());
				socialGroupJson.setHunterOwned(socialGroup.isHunterOwned());
				socialGroupJson.setReceiversCount(socialGroup.getReceiversCount());
				socialGroupJson.setSocialType(socialGroup.getSocialType());
				socialGroupJson.setSuspended(socialGroup.isSuspended());
				socialGroupJson.setSuspensionDescription(socialGroup.getSuspensionDescription());
				socialGroupJson.setVerifiedBy(socialGroup.getVerifiedBy());
				socialGroupJson.setVerifiedDate(HunterUtility.formatDate(socialGroup.getVerifiedDate(), HunterConstants.DATE_FORMAT_STRING));  
				socialGroupJson.setStatus(socialGroup.getStatus());
				socialGroupJson.setSocialAppId(socialGroup.getDefaultSocialApp() != null ? socialGroup.getDefaultSocialApp().getAppId() : null);
				socialGroupJson.setSocialAppName(socialGroup.getDefaultSocialApp() != null ? socialGroup.getDefaultSocialApp().getAppName() : null); 
				
				socialGroupJson.setCreatedBy(socialGroup.getAuditInfo().getCreatedBy());
				socialGroupJson.setCretDate(HunterUtility.formatDate(socialGroup.getAuditInfo().getCretDate(), HunterConstants.DATE_FORMAT_STRING));
				socialGroupJson.setLastUpdate(HunterUtility.formatDate(socialGroup.getAuditInfo().getLastUpdate(), HunterConstants.DATE_FORMAT_STRING));
				socialGroupJson.setLastUpdatedBy(socialGroup.getAuditInfo().getLastUpdatedBy());
				
				
				
				Map<String,String> regionNames = getSocialRegionNames(socialGroup.getSocialRegion());
				
				String 
				countryName 		= regionNames.get(HunterConstants.RECEIVER_LEVEL_COUNTRY), 
				countyName 			= regionNames.get(HunterConstants.RECEIVER_LEVEL_COUNTY),
				constituencyName 	= regionNames.get(HunterConstants.RECEIVER_LEVEL_CONSITUENCY),
				wardName 			= regionNames.get(HunterConstants.RECEIVER_LEVEL_WARD);
				
				socialGroupJson.setRegionCountryName(countryName);
				socialGroupJson.setRegionCountyName(countyName);
				socialGroupJson.setRegionWardName(wardName);
				socialGroupJson.setRegionConsName(constituencyName);
				
				socialGroupJson.setReceiversCount(socialGroup.getSocialRegion().getPopulation());
				socialGroupJson.setRegionAssignedTo(socialGroup.getSocialRegion().getAssignedTo());
				String blobStr = HunterUtility.getBlobStrFromDB("coordinates", "regionId", Long.toString(socialGroup.getSocialRegion().getRegionId()), HunterSocialRegion.class);
				socialGroupJson.setRegionCoordinates( blobStr ); 
				socialGroupJson.setRegionDesc(socialGroup.getSocialRegion().getRegionDesc());
				socialGroupJson.setRegionId(socialGroup.getSocialRegion().getRegionId());
				socialGroupJson.setRegionName(socialGroup.getSocialRegion().getRegionName());
				socialGroupJson.setRegionPopulation(socialGroup.getSocialRegion().getPopulation());
				
				socialGroupJsons.add(socialGroupJson);
				
			}
		}
		return socialGroupJsons;
	}

	public List<HunterSocialGroupJson> getAllSocialGroupsJsons(){
		logger.debug("Getting all social group jsons..." ); 
		List<HunterSocialGroup> socialGroups =  HunterDaoFactory.getObject(HunterHibernateHelper.class).getAllEntities(HunterSocialGroup.class);
		List<HunterSocialGroupJson> socialGroupJsons = convertSocialGroupToSocialGroupJson(socialGroups);
		logger.debug("Returning groups. Size ( "+ socialGroupJsons.size() +" )" ); 
		return socialGroupJsons;
	}
	
	
	public HunterSocialRegionJson getSocialRegionJSONForRegion( HunterSocialRegion socialRegion ){
		
		Map<String,String> regionNames = getSocialRegionNames(socialRegion);
		HunterSocialRegionJson socialRegionJson = new HunterSocialRegionJson();
		
		
		String 
		countryName 		= regionNames.get(HunterConstants.RECEIVER_LEVEL_COUNTRY), 
		countyName 			= regionNames.get(HunterConstants.RECEIVER_LEVEL_COUNTY),
		constituencyName 	= regionNames.get(HunterConstants.RECEIVER_LEVEL_CONSITUENCY),
		wardName 			= regionNames.get(HunterConstants.RECEIVER_LEVEL_WARD);
		
		socialRegionJson.setCountryName(countryName);
		socialRegionJson.setCountyName(countyName);
		socialRegionJson.setWardName(wardName);
		socialRegionJson.setConsName(constituencyName);
		
		socialRegionJson.setCountryId(socialRegion.getCountryId());
		socialRegionJson.setCountyId(socialRegion.getCountyId()); 
		socialRegionJson.setConsId(socialRegion.getConsId()); 
		socialRegionJson.setWardId(socialRegion.getWardId()); 
		
		socialRegionJson.setPopulation(socialRegion.getPopulation());
		socialRegionJson.setAssignedTo(socialRegion.getAssignedTo());
		String blobStr = HunterUtility.getBlobStrFromDB("coordinates", "regionId", Long.toString(socialRegion.getRegionId()), HunterSocialRegion.class);
		socialRegionJson.setCoordinates( blobStr ); 
		socialRegionJson.setRegionDesc(socialRegion.getRegionDesc());
		socialRegionJson.setRegionId(socialRegion.getRegionId());
		socialRegionJson.setRegionName(socialRegion.getRegionName());
		socialRegionJson.setPopulation(socialRegion.getPopulation());
		
		socialRegionJson.setCreatedBy(socialRegion.getAuditInfo().getCreatedBy());
		socialRegionJson.setCretDate(HunterUtility.formatDate(socialRegion.getAuditInfo().getCretDate(), HunterConstants.DATE_FORMAT_STRING));
		socialRegionJson.setLastUpdate(HunterUtility.formatDate(socialRegion.getAuditInfo().getLastUpdate(), HunterConstants.DATE_FORMAT_STRING));
		socialRegionJson.setLastUpdatedBy(socialRegion.getAuditInfo().getLastUpdatedBy());
		
		return socialRegionJson;
	}
	
	
	public List<HunterSocialRegionJson> getAllSocialRegionsJsons(){
		List<HunterSocialRegion> socialRegions =  HunterDaoFactory.getObject(HunterHibernateHelper.class).getAllEntities(HunterSocialRegion.class);
		List<HunterSocialRegionJson> socialRegionJsons = new ArrayList<>();
		for(HunterSocialRegion socialRegion : socialRegions){
			HunterSocialRegionJson socialRegionJson = getSocialRegionJSONForRegion(socialRegion);
			socialRegionJsons.add(socialRegionJson);
		}
		return socialRegionJsons;
	}

	public HunterSocialRegion createOrUpdateSocialRegion( HunterSocialRegionJson socialRegionJson, AuditInfo auditInfo ) {
		
		HunterSocialRegion socialRegion = new HunterSocialRegion();
		
		if( socialRegionJson.getRegionId() != null && socialRegionJson.getRegionId() != 0 ){
			socialRegion =  HunterDaoFactory.getObject(HunterHibernateHelper.class).getEntityById( socialRegionJson.getRegionId(), HunterSocialRegion.class);
			auditInfo.setCreatedBy(socialRegionJson.getCreatedBy()); 
			auditInfo.setCretDate(HunterUtility.parseDate(socialRegionJson.getCretDate(), HunterConstants.DATE_FORMAT_STRING));  
		}
		
		socialRegion.setAssignedTo(socialRegionJson.getAssignedTo());
		socialRegion.setAuditInfo(auditInfo); 
		socialRegion.setConsId(socialRegionJson.getConsId()); 
		socialRegion.setCountryId(socialRegionJson.getCountryId()); 
		socialRegion.setCountyId(socialRegionJson.getCountyId()); 
		socialRegion.setPopulation(socialRegionJson.getPopulation()); 
		socialRegion.setRegionDesc(socialRegionJson.getRegionDesc());
		socialRegion.setRegionId(socialRegionJson.getRegionId());
		socialRegion.setRegionName(socialRegionJson.getRegionName());
		socialRegion.setCoordinates( HunterUtility.getStringBlob( socialRegionJson.getCoordinates() ) );  
		socialRegion.setWardId(socialRegionJson.getWardId()); 
		
		logger.debug("Successfully converted the json. Resultant social region : " + socialRegion); 
		
		if( socialRegionJson.getRegionId() != null &&  socialRegionJson.getRegionId() != 0){
			logger.debug("Updating the social region...");
			 HunterDaoFactory.getObject(HunterHibernateHelper.class).updateEntity(socialRegion);
		}else{
			logger.debug("Creating the social region...");
			 HunterDaoFactory.getObject(HunterHibernateHelper.class).saveEntity(socialRegion);
			socialRegionJson.setRegionId(socialRegion.getRegionId());
		}
		
		return socialRegion;
	}
	
	public String validateAndDeleteSocialRegion(Long regionId) {
		logger.debug("Trying to delete social region of region ID ( "+ regionId +" )");
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
		String query = hunterJDBCExecutor.getQueryForSqlId("getSocialGroupsUsingSocialRegionId"); 
		List<Object> values = new ArrayList<>();
		values.add(regionId);
		List<Map<String, Object>> rowMapsList = hunterJDBCExecutor.executeQueryRowMap(query,values );
		StringBuilder builder = new StringBuilder();
		if( HunterUtility.isCollNotEmpty(rowMapsList) ){
			int counter = 1;
			for(Map<String, Object> rowMap : rowMapsList){
				String groupName = HunterUtility.getStringOrNullOfObj(rowMap.get("GRP_NAM"));
				builder.append("( ").append(counter).append(" ) ").append(groupName).append(",");
				counter++;
			}
			String bstr = builder.toString();
			if( bstr.contains(",") ){
				bstr = bstr.substring(0, bstr.length() - 1 );
			}
			logger.debug("Cannot delete social region since it is being used by social groups : " + bstr);
			return bstr;
		}else{
			hunterJDBCExecutor.executeUpdate("DELETE FROM HNTR_SCL_RGN r WHERE r.RGN_ID = ?", values);
			return null;
		}
	}
	
	public HunterSocialMedia createDfltRmtSclMda(String remoteURL, AuditInfo  auditInfo, Long mId){
		
		HunterSocialMedia socialMedia = new HunterSocialMedia();
		socialMedia.setAuditInfo(auditInfo);
		socialMedia.setChannelType(null); 
		socialMedia.setByteSize(0); 
		socialMedia.setClientName("admin");  
		socialMedia.setDstrbtnDrctns(null);
		socialMedia.setDurationInSecs(0); 
		socialMedia.setFileFormat(null); 
		
		String 
		fileSlash 	= remoteURL.contains("\\") ? "\\" : "/",
		orgnlFlNam 	= remoteURL.substring( remoteURL.lastIndexOf(fileSlash) + 1, remoteURL.length()  ),
        suffix 		= orgnlFlNam.substring(orgnlFlNam.lastIndexOf(".")+1, orgnlFlNam.length());
        
        socialMedia.setMediaSuffix(suffix); 
        socialMedia.setHeight(0);
        socialMedia.setHunterOwned(true); 
        socialMedia.setLocalURL(remoteURL); 
        socialMedia.setMediaData(null);
        socialMedia.setMediaDescription(orgnlFlNam); 
        socialMedia.setMediaName(orgnlFlNam); 
        socialMedia.setMediaType(suffix); 
        socialMedia.setMimeType(suffix); 
        socialMedia.setOriginalFileName(orgnlFlNam);
        socialMedia.setRemoteURL(remoteURL); 
        socialMedia.setUserSpecs(" ");  
        socialMedia.setMediaId(mId); 
        socialMedia.setWidth(0); 
		
		return socialMedia;
	}
	
	private List<Long> getSelMsgSocialGroupIds(Long msgId){
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
		List<Long> groupIds = new ArrayList<>();
		String query = "SELECT g.GP_ID FROM SCL_MSG_SCL_GRPS g WHERE g.MSG_ID = ?";
		List<Object> values = new ArrayList<>();
		values.add(msgId);
		Map<Integer, List<Object>>  rowListMap = hunterJDBCExecutor.executeQueryRowList(query, values);
		for(Map.Entry<Integer, List<Object>> entry : rowListMap.entrySet()){
			List<Object> list = entry.getValue();
			Long groupId = HunterUtility.getLongFromObject(list.get(0));
			groupIds.add(groupId);
		}
		return groupIds;
	}

	public List<HunterSocialGroupJson> getAvailSocialMsgGroups(Long selMsgId) {
		List<Long> selGroupIds = getSelMsgSocialGroupIds(selMsgId);
		if( !HunterUtility.isCollNotEmpty(selGroupIds) ){
			return getAllSocialGroupsJsons();
		}
		String commaSepGrpIds = HunterUtility.getCommaDelimitedStrings(selGroupIds);
		String query = "FROM HunterSocialGroup g WHERE g.groupId NOT IN ("+ commaSepGrpIds +")";
		List<HunterSocialGroup> socialGroups =  HunterDaoFactory.getObject(HunterHibernateHelper.class).executeQueryForObjList(HunterSocialGroup.class, query);
		return convertSocialGroupToSocialGroupJson(socialGroups);
	}
	
	public List<HunterSocialGroupJson> getSelSocialMsgGroups(Long selMsgId) {
		SocialMessage socialMessage =  HunterDaoFactory.getObject(HunterHibernateHelper.class).getEntityById(selMsgId, SocialMessage.class);
		Set<HunterSocialGroup> socialGroups = socialMessage != null ? socialMessage.getHunterSocialGroups() : new HashSet<HunterSocialGroup>();
		List<HunterSocialGroup> socialGroup = new ArrayList<>();
		socialGroup.addAll(socialGroups);
		return convertSocialGroupToSocialGroupJson(socialGroup); 
	}
	
	public Long getNewProcessJobIdForSclProcess(Long taskId, AuditInfo auditInfo){
		String defltDate = HunterUtility.formatDate(new Date(), HunterConstants.DATE_FORMAT_STRING);
		Map<String, String> contextParams = new HashMap<>();
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class); 
		String query = hunterJDBCExecutor.getQueryForSqlId("getSclMsgCSVDiscintSocialType");
		List<Object> values = new ArrayList<>();
		values.add(taskId);
		Object clients = hunterJDBCExecutor.executeQueryForOneReturn(query, values);
		contextParams.put("genStatus", HunterConstants.STATUS_PARTIAL);
		contextParams.put("numberOfWorkers", Integer.toString(0)); 
		contextParams.put("totalMsgs", Integer.toString(0));
		contextParams.put("clientName", HunterUtility.getStringOrNullOfObj(clients)); 
		contextParams.put("genDuration", Integer.toString(0));
		contextParams.put("startDate", defltDate); 
		contextParams.put("endDate", defltDate);
		TaskProcessJob processJob = TaskProcessJobHandler.getInstance().createNewTaskProcessJob(taskId, null, contextParams, auditInfo);
		return processJob.getJobId();
	}
	
	public synchronized TaskProcessJob getSocialProcessedJob(Long jobId){
		String query = "FROM TaskProcessJob j WHERE j.jobId = " + jobId;
		List<TaskProcessJob> processJobs  =  HunterDaoFactory.getObject(HunterHibernateHelper.class).executeQueryForObjList(TaskProcessJob.class, query);
		if( HunterUtility.isCollNotEmpty(processJobs) ){
			TaskProcessJob processJob = processJobs.get(0); 
			try {
				String blobStr = HunterUtility.getBlobStrFromDB("docBlob", "jobId", Long.toString( processJob.getJobId() ), TaskProcessJob.class);
				XMLService xmlService = new XMLServiceImpl(new XMLTree( blobStr, true));
				processJob.setXmlService(xmlService);
				return processJob;
			} catch (HunterRunTimeException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public synchronized void updateProcessJob(TaskProcessJob processJob){
		TaskProcessJobHandler.getInstance().saveOrUpdateProcessJob(processJob);
	}
	
	public void addSocialProcessWorker(Long processJobId, boolean saveOrUpdate, String workerName, Map<String, String> values){
		logger.debug("Adding new worker to process job with id : " + processJobId);
		logger.debug("Worker values : " + HunterUtility.stringifyMap(values));  
		TaskProcessJob processJob = getSocialProcessedJob(processJobId);
		TaskProcessJobHandler.getInstance().addWorkerToProcessJob(processJob, saveOrUpdate, workerName, values);
	}
	
	
	
	public List<HunterSelectValue> getAssignableRawReceiverUsers(){
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getDaoObject(HunterJDBCExecutor.class);
		String query = hunterJDBCExecutor.getQueryForSqlId("getAssignableRawUsersForDropdowns");
		List<Map<String, Object>> rowMapList = hunterJDBCExecutor.executeQueryRowMap(query, null);
		List<HunterSelectValue>  selects = new ArrayList<>();
		if( HunterUtility.isCollNotEmpty(rowMapList) ){ 
			for( Map<String, Object> rowMap : rowMapList ){
				HunterSelectValue hunterSelectValue = new HunterSelectValue();
				hunterSelectValue.setText(rowMap.get("TEXT").toString());
				hunterSelectValue.setValue(rowMap.get("VALUE").toString());
				selects.add(hunterSelectValue);
			}
		}
		return selects;		
	}

	public HunterSocialRegionJson getSocialRegionJsonForId(Long regionId) {
		HunterSocialRegionJson regionJson = getSocialRegionJSONForRegion( HunterDaoFactory.getObject(HunterHibernateHelper.class).getEntityById(regionId, HunterSocialRegion.class));
		return regionJson;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}

