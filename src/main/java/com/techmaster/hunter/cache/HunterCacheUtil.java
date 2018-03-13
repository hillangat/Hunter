package com.techmaster.hunter.cache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.constants.HunterURLConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.ReceiverRegionDao;
import com.techmaster.hunter.gateway.beans.GateWayClientHelper;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.Constituency;
import com.techmaster.hunter.obj.beans.ConstituencyWard;
import com.techmaster.hunter.obj.beans.Country;
import com.techmaster.hunter.obj.beans.County;
import com.techmaster.hunter.obj.beans.EmailMessage;
import com.techmaster.hunter.obj.beans.HunterEmailTemplateBean;
import com.techmaster.hunter.obj.beans.HunterMessageReceiver;
import com.techmaster.hunter.obj.beans.Message;
import com.techmaster.hunter.obj.beans.MessageAttachmentMetadata;
import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.obj.beans.TaskClientConfigBean;
import com.techmaster.hunter.util.EmailTemplateUtil;
import com.techmaster.hunter.util.HunterUtility;
import com.techmaster.hunter.xml.XMLService;


public class HunterCacheUtil {

	private static HunterCacheUtil instance;
	private static Logger logger = Logger.getLogger(HunterCacheUtil.class);
	private HunterCacheUtil (){};
	
	static{
		if(instance == null){
			synchronized (HunterCache.class) {
				instance = new HunterCacheUtil();
			}
		}
	}
	
	public static HunterCacheUtil getInstance(){
		return instance;
	}

	public void refreshAllXMLServices(){
		String path = HunterURLConstants.CACHE_REFRESH_JSONS;
		File cacheRefresh = new File( path );
		String cacheContents = HunterUtility.getStringOfFile(cacheRefresh);
		if ( HunterUtility.notNullNotEmpty(cacheContents) ) {
			try {
				JSONArray cacheRefreshes = new JSONArray( cacheContents );
				for( int i = 0; i < cacheRefreshes.length(); i++ ) {
					JSONObject cache = cacheRefreshes.getJSONObject(i);
					String key = HunterUtility.getStringOrNulFromJSONObj(cache, "key");
					if ( HunterUtility.notNullNotEmpty(key) && !key.equals("allXMLService") ) {
						this.refreshCacheService( key );
					}
				}
			} catch( Exception e ) {
				e.printStackTrace();
			}
		}
	}
	
	public void refreshCacheService(String xmlName){
		switch (xmlName) {
		case "allXMLService":
			refreshAllXMLServices();
			break;
		case "queryXML":
			logger.debug("Caching xmlQuery..."); 
			XMLService queryService = HunterUtility.getXMLServiceForFileLocation(HunterURLConstants.QRY_XML_FL_LOC_PATH);
			HunterCache.getInstance().put(HunterConstants.QUERY_XML_CACHED_SERVICE, queryService);
			logger.debug("Done caching xmlQuery!!");
			break;
		case "uiMsgXML":
			logger.debug("Caching ui message xml..."); 
			XMLService uiMsgService = HunterUtility.getXMLServiceForFileLocation(HunterURLConstants.UI_MSG_XML_FL_LOC_PATH);
			HunterCache.getInstance().put(HunterConstants.UI_MSG_CACHED_SERVICE, uiMsgService);
			logger.debug("Done caching ui message xml!!");
			break;
		case "clientConfigs":
			logger.debug("Caching ui clieConfigService xml..."); 
			XMLService clieConfigService = HunterUtility.getXMLServiceForFileLocation(HunterURLConstants.CLIENT_CONFIG_LOC_PATH); 
			HunterCache.getInstance().put(HunterConstants.CLIENT_CONFIG_XML_CACHED_SERVICE, clieConfigService);
			logger.debug("Done caching clieConfigService xml!!");
			break;
		case "responseConfigs":
			logger.debug("Caching ui responseConfigXml xml..."); 
			XMLService responseConfigXml = HunterUtility.getXMLServiceForFileLocation(HunterURLConstants.RESPONSE_CONFIG_LOC_PATH);
			HunterCache.getInstance().put(HunterConstants.RESPONSE_CONFIG_CACHED_SERVICE, responseConfigXml);
			logger.debug("Done caching responseConfigXml xml...");
			break;
		case "emailTemplates":
			logger.debug("Caching emailTemplates xml...");
			XMLService emailTemplates = EmailTemplateUtil.getInstance().getAllTemplateXMLService(); 
		    //HunterUtility.getXMLServiceForFileLocation(HunterURLConstants.EMAIL_TEMPLATES_LOCL_PATH);
			HunterCache.getInstance().put(HunterConstants.EMAIL_TEMPLATES_CACHED_SERVICE, emailTemplates);
			logger.debug("Done caching emailTemplates xml...");
			break;
		case "emailConfigs":
			logger.debug("Cachingg emailConfigs xml...");
			XMLService emailConfigs = HunterUtility.getXMLServiceForFileLocation(HunterURLConstants.EMAIL_CONFIGS__LOCL_PATH);
			HunterCache.getInstance().put(HunterConstants.EMAIL_CONFIG_CACHED_SERVICE, emailConfigs);
			logger.debug("Done caching emailConfigs xml!!");
			break;
		case "taskProcessTemplates":
			logger.debug("Cachingg Task process templates...");
			loadEmailTemplateBeans();
			logger.debug("Done caching Task process templates!!");
			break;
		case "existingTemplateNames" :
			logger.debug("Caching existing email template names..."); 
			HunterCacheUtil.getInstance().loadExistentEmailTemplatesNames();
			logger.debug("Done caching existing email template names!!"); 
			break;
		case "queryToBeanMapper" :
			XMLService queryToBeanMapper = HunterUtility.getXMLServiceForFileLocation(HunterURLConstants.QUERY_TO_BEAN_MAPPER);
			HunterCache.getInstance().put(HunterConstants.QUERY_TO_BEAN_MAPPER, queryToBeanMapper);
			logger.debug("Done cachingg login data seed xml!!");
			break;
		case "gridHeaders" :
			XMLService gridHeadersService = HunterUtility.getXMLServiceForFileLocation(HunterURLConstants.HUNTER_ANGULAR_GRID_HEADERS_PATH);
			HunterCache.getInstance().put(HunterConstants.ANGULAR_HEADERS_CONFIG_CACHED_SERVICE, gridHeadersService);
			logger.debug("Done caching Grid Headers!!");
			break;
		case "gridQueryFieldMapping" :
			XMLService queryGridFieldMapper = HunterUtility.getXMLServiceForFileLocation(HunterURLConstants.HUNTER_GRID_QUERY_FIELD_MAPPING);
			HunterCache.getInstance().put(HunterConstants.QUERY_GRID_FIELD_MAPPER, queryGridFieldMapper);
			logger.debug("Done cachingg query gid field mapper xml!!");
			break;
		default:
			break;
		}
	}
	
	public XMLService getXMLService(String xmlName) {
		XMLService service = (XMLService)HunterCache.getInstance().get(xmlName);
		if(service == null) logger.debug("Service ( " + xmlName + " ) cannot be found!!") ; 
		return service;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getExistentEmailTemplateNames(){
		List<String> existentTemplates = (List<String>)HunterCache.getInstance().get(HunterConstants.EXISTENT_EMAIL_TEMPLATES);
		if(existentTemplates == null || existentTemplates.isEmpty()){
			return loadExistentEmailTemplatesNames();
		}
		return existentTemplates;
	}
	
	public List<String> loadExistentEmailTemplatesNames(){
		logger.debug("Loading existent templates...");
		String xPath = "//template/@*";
		List<String> existentTemplates = new ArrayList<>();
		NodeList nodeList = getXMLService(HunterConstants.EMAIL_TEMPLATES_CACHED_SERVICE).getNodeListForPathUsingJavax(xPath); 
		int length = nodeList.getLength();
		logger.debug("Number of templates configured : " + length);
		for( int i=0; i<length; i++) {
		    Attr attr = (Attr) nodeList.item(i);
		    //String name = attr.getName();
		    String value = attr.getValue();
		    existentTemplates.add(value);
		}
		existentTemplates.addAll(existentTemplates);
		HunterCache.getInstance().put(HunterConstants.EXISTENT_EMAIL_TEMPLATES, existentTemplates);
		logger.debug("Done loading existent templates!"); 
		return existentTemplates;
	}
	
	public Map<String, HunterEmailTemplateBean> loadEmailTemplateBeans(){
		logger.debug("Loading email template beans...");
		Map<String, HunterEmailTemplateBean> emailTemplateBeans = new HashMap<>();
		List<String> existentTemplates = getExistentEmailTemplateNames();
		for(String mailTypeName : existentTemplates){
			HunterEmailTemplateBean  emailTemplateBean = getInstance().createEmailTemplateBean(mailTypeName);
			emailTemplateBeans.put(mailTypeName, emailTemplateBean);
		}
		logger.debug("Done loading email template beans!"); 
		HunterCache.getInstance().put(HunterConstants.EMAIL_TEMPLATE_BEANS, emailTemplateBeans);
		return emailTemplateBeans;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, HunterEmailTemplateBean> getAllEmailTemplateBeanMap(){
		Map<String,HunterEmailTemplateBean> emailTemplateBeanMap = (Map<String,HunterEmailTemplateBean> )HunterCache.getInstance().get(HunterConstants.EMAIL_TEMPLATE_BEANS);
		return emailTemplateBeanMap;
	}
	
	public HunterEmailTemplateBean getEmailTemplateBean(String templateName){
		Map<String,HunterEmailTemplateBean> emailTemplateBeanMap = getAllEmailTemplateBeanMap();
		HunterEmailTemplateBean emailTemplateBean = null;
		if(emailTemplateBeanMap != null && !emailTemplateBeanMap.isEmpty()){
			emailTemplateBean = emailTemplateBeanMap.get(templateName);
		}
		return emailTemplateBean;
	}
	
	public String getReadyEmailBodyForEmailTask(Task task,String prevLoc, String staticPrevLoc){
		
		File prevFolder = new File(prevLoc);
		
		if( !prevFolder.exists() )
			prevFolder.mkdir();
		
		File[] previews = prevFolder.listFiles();
		
		//clear existing previews first.
		if( previews.length > 0 ){
			for( File preview : previews ){
				preview.delete();
			}
		}
		
		Message message = task.getTaskMessage();
		if(message == null || !HunterUtility.notNullNotEmpty(message.getMsgText())){ 
			return "<h2 style='color:red;margin-top:40%;text-align:center;' >Task Message text doesn't exist. Please create message first.</h2>";
		}
		
		String templateName = ((EmailMessage)task.getTaskMessage()).getEmailTemplateName();
		logger.debug("Getting template for template name : " + templateName); 
		HunterEmailTemplateBean bean = HunterCacheUtil.getInstance().getEmailTemplateBean(templateName);
		if(bean == null || !HunterUtility.notNullNotEmpty(bean.getTemplate())){ 
			return "<h3 style='color:red;margin-top:40%;text-align:center;' >Template ( "+ templateName +" ) does not exist. Please Create template first.</h3>";
		}
		String emailContent = bean.getTemplate().replaceAll("#TEMPLATE_EMAIL_CONTENTS#", task.getTaskMessage().getMsgText());
		
		if(!HunterUtility.notNullNotEmpty(((EmailMessage)task.getTaskMessage()).getMessageAttachments())){
			logger.debug("Template has no message attachments.Returning just the contents!"); 
			return emailContent;
		}
		
		logger.debug("Creating attachments for email message..."); 
		List<MessageAttachmentMetadata> messageAttachmentMetadata = GateWayClientHelper.getInstance().createMessageAttachmentMetadata(task);
		for(MessageAttachmentMetadata attachmentMetadata : messageAttachmentMetadata){
			String cidKey = "cid:HUNTER_CID_REF=" + attachmentMetadata.getKey();
			if( emailContent.contains(cidKey) ){
				logger.debug( attachmentMetadata.getKey() + " is an embedded attachment : cid = " + attachmentMetadata.getMsgCid() );
				String url = attachmentMetadata.getUrl();
				try {
					File sourceFile = new File(url);
					FileUtils.copyFile(sourceFile, new File(prevLoc + File.separator + attachmentMetadata.getOriginalFileName()));
					sourceFile.delete();
				} catch (IOException e) {
					e.printStackTrace();
				} 
				emailContent = emailContent.replaceAll(cidKey, staticPrevLoc + "/" + attachmentMetadata.getOriginalFileName());
			}
		}
		
		return emailContent;
	}
	
	public HunterEmailTemplateBean createEmailTemplateBean(String templateName){
		
		StringBuilder templateCheckPath = new StringBuilder().append("templates/template[@name=\"").append(templateName).append("\"]");
		XMLService xmlService = getXMLService(HunterConstants.EMAIL_TEMPLATES_CACHED_SERVICE);
		
		try {
			NodeList check  = xmlService.getAllElementsUnderTag(templateCheckPath.toString()); 
			if(check == null){
				logger.debug("Cannot find the template with the path : " + templateCheckPath.toString());
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		StringBuilder templateDescPath = new StringBuilder().append("templates/template[@name=\"").append(templateName).append("\"]/description");
		StringBuilder templateContentPath = new StringBuilder().append("templates/template[@name=\"").append(templateName).append("\"]/content/text()");
		StringBuilder templatePath = new StringBuilder().append("templates/template[@name=\"").append(templateName).append("\"]/template/text()");
		StringBuilder contextPath = new StringBuilder().append("templates/template[@name=\"").append(templateName).append("\"]/context");
		StringBuilder contextFromPath = new StringBuilder().append(contextPath.toString()).append("/from");
		StringBuilder contextCCListPath = new StringBuilder().append(contextPath.toString()).append("/ccList");
		StringBuilder contextToListPath = new StringBuilder().append(contextPath.toString()).append("/toList");
		StringBuilder contextSubjectPath = new StringBuilder().append(contextPath.toString()).append("/subject");
		StringBuilder contextContentTypePath = new StringBuilder().append(contextPath.toString()).append("/contentType");

		String templateDesc = xmlService.getTextValue(templateDescPath.toString()); 
		String templateContent = xmlService.getTextValue(templateContentPath.toString());
		String contextFrom = xmlService.getTextValue(contextFromPath.toString());
		String contextCCList = xmlService.getTextValue(contextCCListPath.toString());
		String contextToList = xmlService.getTextValue(contextToListPath.toString());
		String contextSubject = xmlService.getTextValue(contextSubjectPath.toString());
		String contextContentType = xmlService.getTextValue(contextContentTypePath.toString());
		String template = xmlService.getTextValue(templatePath.toString());
		
		Map<String, String> miscelaneousMap = new HashMap<String, String>();
		
		NodeList nodeList = xmlService.getNodeListForPathUsingJavax("//template[@name='"+ templateName +"']/context/miscelaneous/*");
		if(nodeList != null && nodeList.getLength() >= 1){
			for(int i=0; i<nodeList.getLength(); i++){
				Node node = nodeList.item(i);
				if(node.getNodeName().equals("config")){
					NodeList configs = node.getChildNodes();
					String key = configs.item(1).getTextContent();
					String value = configs.item(3).getTextContent();
					miscelaneousMap.put(key, value);
				}
			}
		}
		logger.debug("Miscelaneous values for template bean : \n" + HunterUtility.stringifyMap(miscelaneousMap));  

		NodeList attachments = xmlService.getNodeListForPathUsingJavax("//template[@name='"+ templateName +"']/attachments/attachment");
		Map<String, String> attachmentsMap = new HashMap<String, String>();
		List<String> embeddedAttachments  = new ArrayList<>();
		
		if(attachments != null && attachments.getLength() >= 1){
			for(int i=0; i<attachments.getLength(); i++){
				Node attachment = attachments.item(i);
				String key = attachment.getAttributes().getNamedItem("key").getTextContent();
				String type = attachment.getAttributes().getNamedItem("type").getTextContent();
				if( type != null && "embedded".equalsIgnoreCase(type) ){
					embeddedAttachments.add(key);
				}
				String text = attachment.getTextContent();
				attachmentsMap.put(key, text);
			}
		}
		
		logger.debug("Embedded attachments : " + HunterUtility.stringifyList(embeddedAttachments));  
		logger.debug("Attachment values for template bean : \n" + HunterUtility.stringifyMap(attachmentsMap));  
		
		HunterEmailTemplateBean templateBean = new HunterEmailTemplateBean();
		templateBean.setCcList(contextCCList); 
		templateBean.setFromList(contextFrom);
		templateBean.setMiscelaneous(miscelaneousMap);
		templateBean.setSubject(contextSubject);
		templateBean.setTemplateContent(templateContent);
		templateBean.setTemplateDesc(templateDesc);
		templateBean.setTemplateName(templateName);
		templateBean.setToList(contextToList);
		templateBean.setContentType(contextContentType);
		templateBean.setTemplate(template); 
		templateBean.setAttachments(attachmentsMap);
		templateBean.setEmbeddedAttachments(embeddedAttachments);
		
		logger.debug("Successfully created email template bean for template name ( " + templateName + " )");
		
		return templateBean;
		
	}
	
	public void populateUIMessages(){
		Map<String,String> uiMessages = new HashMap<>();
		logger.debug("populating ui messages..." ); 
		XMLService service = getXMLService(HunterConstants.UI_MSG_CACHED_SERVICE); 
		NodeList messages = service.getNodeListForXPath("//message");
		for(int i=0; i<messages.getLength(); i++){
			Node message = messages.item(i);
			if(message.getNodeName().equals("message")){
				String id = message.getAttributes().getNamedItem("id").getTextContent(); 
				NodeList metadata = message.getChildNodes();
				String desc = null;
				String text = null;
				for(int j=0; j<metadata.getLength(); j++){
					Node datum = metadata.item(j);
					if(datum.getNodeName().equals("desc"))
						desc = datum.getTextContent();
					else if(datum.getNodeName().equals("text")){
						text = datum.getTextContent();
					}
				}
				uiMessages.put(id+"_DESC", desc);
				uiMessages.put(id+"_TEXT", text);
			}
		}
		HunterCache.getInstance().put(HunterConstants.UI_MSG_CACHED_BEANS, uiMessages);
		logger.debug("Succesfully populated ui messages!!" );
	}
	
	public String getUIMsgTxtForMsgId(String msgId){
		return getUIMsgMap().get(msgId+"_TEXT");
	}
	
	public String getUIMsgDescForMsgId(String msgId){
		return getUIMsgMap().get(msgId+"_DESC");
	}
	
	public String getUIMsgTxtAndReplace( String msgId, Map<String, String> params ){
		String msg = getUIMsgMap().get(msgId+"_TEXT");
		if ( msg != null && HunterUtility.isMapNotEmpty( params ) ) {
			for ( Map.Entry<String, String> entry : params.entrySet() ) {
				msg = msg.replaceAll(entry.getKey(), entry.getValue());
			}
		}
		return msg;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, String> getUIMsgMap() {
		return (Map<String, String>)HunterCache.getInstance().get(HunterConstants.UI_MSG_CACHED_BEANS);		
	}
	
	public List<Country> loadCountries(){
		List<Country> countries = HunterDaoFactory.getObject(ReceiverRegionDao.class).getAllCountries(); 
		HunterCache.getInstance().put(HunterConstants.COUNTRIES, countries);
		return countries;
	}
	
	public void loadCountriesViaThread() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				loadCountries();
			}
		};
		new Thread( runnable ).start();
	}
	
	public boolean isCountriesLoaded(){
		@SuppressWarnings("unchecked")
		List<Country> countries = (List<Country>)HunterCache.getInstance().get(HunterConstants.COUNTRIES);
		return countries != null && !countries.isEmpty();
	}
	
	public List<Country> getAllCountries(){
		if( !isCountriesLoaded() ){
			return loadCountries();
		}
		@SuppressWarnings("unchecked")
		List<Country> countries = (List<Country> )HunterCache.getInstance().get(HunterConstants.COUNTRIES);
		return countries;
		
	}
	
	public void loadReceivers(){
		
		logger.debug("Loading hunter message receivers into cache..."); 
		
		List<HunterMessageReceiver> hunterMessageReceivers_ = new ArrayList<>();
		
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
		String query = hunterJDBCExecutor.getQueryForSqlId("getAllHunterMessagesForCache");
		List<Map<String, Object>> rowMapList = hunterJDBCExecutor.executeQueryRowMap(query, null);
		
		if( HunterUtility.isCollectionNotEmpty(rowMapList) ){
			for(Map<String,Object> rowMap : rowMapList){
				
				boolean 
				isBlocked = HunterUtility.getBooleanForYN(rowMap.get("RCVR_BLCKD")+""),
				active 	  = HunterUtility.getBooleanForYN(rowMap.get("ACTV")+"");
				
				int
				successDelTimes = Integer.valueOf( rowMap.get("SCCSS_DEL_TMS") == null ? "0" : rowMap.get("SCCSS_DEL_TMS").toString() ),
				failedDelTimes = Integer.valueOf( rowMap.get("SCCSS_DEL_TMS") == null ? "0" : rowMap.get("SCCSS_DEL_TMS").toString() );
				
				Long 
				receiverId = HunterUtility.getLongFromObject(rowMap.get("RCVR_ID")); 
				
				String 
				type 		= HunterUtility.getStringOrNullOfObj(rowMap.get("RCVR_TYP")),
				regonLevel 	= HunterUtility.getStringOrNullOfObj(rowMap.get("RCVR_RGN_LVL")),
				countryName = HunterUtility.getStringOrNullOfObj(rowMap.get("CNTRY_NAM")),
				countyName 	= HunterUtility.getStringOrNullOfObj(rowMap.get("CNTY_NAM")),
				consName 	= HunterUtility.getStringOrNullOfObj(rowMap.get("CONS_NAM")),
				wardName 	= HunterUtility.getStringOrNullOfObj(rowMap.get("WRD_NAM")),
				stateName 	= HunterUtility.getStringOrNullOfObj(rowMap.get("STATE_NAM")),
				
				receiverContact = HunterUtility.getStringOrNullOfObj(rowMap.get("RCVR_CNTCT")),
				regionLevelName = HunterUtility.getStringOrNullOfObj(rowMap.get("RCVR_RGN_LVL_NAM")),
				cretDate 		= HunterUtility.getStringOrNullOfObj(rowMap.get("CRET_DATE")),
				lastUpdatedOn 	= HunterUtility.getStringOrNullOfObj(rowMap.get("LST_UPDT_DATE")),
				createdBy 		= HunterUtility.getStringOrNullOfObj(rowMap.get("CRTD_BY")),
				lastUpdatedBy 	= HunterUtility.getStringOrNullOfObj(rowMap.get("LST_UPDTD_BY")),
				firstName 		= HunterUtility.getStringOrNullOfObj(rowMap.get("FRST_NAM")),
				lastName 		= HunterUtility.getStringOrNullOfObj(rowMap.get("LST_NAM")),
				givenByUserName = HunterUtility.getStringOrNullOfObj(rowMap.get("GVN_BY_USR_NAM"));
				
				AuditInfo auditInfo = new AuditInfo();
				auditInfo.setCreatedBy(createdBy);
				auditInfo.setLastUpdatedBy(lastUpdatedBy);
				auditInfo.setCretDate(HunterUtility.parseDate(cretDate, HunterConstants.DATE_FORMAT_STRING));
				auditInfo.setLastUpdate(HunterUtility.parseDate(lastUpdatedOn, HunterConstants.DATE_FORMAT_STRING));
				
				
				HunterMessageReceiver messageReceiver = new HunterMessageReceiver();
				messageReceiver.setActive(active);
				messageReceiver.setAuditInfo(auditInfo);;
				messageReceiver.setBlocked(isBlocked);
				messageReceiver.setConsName(consName);
				messageReceiver.setCountryName(countryName);
				messageReceiver.setCountyName(countyName);
				messageReceiver.setFailDeliveryTimes(failedDelTimes);
				messageReceiver.setFirstName(firstName);
				messageReceiver.setGivenByUserName(givenByUserName);
				messageReceiver.setLastName(lastName);
				messageReceiver.setReceiverContact(receiverContact);
				messageReceiver.setReceiverId(receiverId);
				messageReceiver.setReceiverRegionLevel(regonLevel);
				messageReceiver.setReceiverRegionLevelName(regionLevelName);
				messageReceiver.setReceiverType(type);
				messageReceiver.setStateName(stateName);
				messageReceiver.setSuccessDeliveryTimes(successDelTimes);
				messageReceiver.setConsWardName(wardName); 
				
				hunterMessageReceivers_.add(messageReceiver);
			}
		}else{
			logger.warn("No hunter message receivers found!!"); 
		}
		logger.debug("Finished loading hunter message receivers. Size ( " + hunterMessageReceivers_.size() +" )" );
		HunterCache.getInstance().put(HunterConstants.RECEIVERS, hunterMessageReceivers_);
	}
	
	public List<HunterMessageReceiver> getAllReceivers(){
		@SuppressWarnings("unchecked")
		List<HunterMessageReceiver> hunterMessageReceivers_ = (List<HunterMessageReceiver>)HunterCache.getInstance().get(HunterConstants.RECEIVERS);
		return hunterMessageReceivers_;
	}
	
	public TaskClientConfigBean getTaskClientConfigBean (String key){
		Object obj = HunterCache.getInstance().get(key);
		TaskClientConfigBean configBean = null;
		if(obj != null && obj instanceof TaskClientConfigBean){
			configBean = (TaskClientConfigBean)obj;
		}
		return configBean;
	}
	
	public  Set<County> getCountiesBeans(String countryName){
		 List<Country> cList = getAllCountries();
		 for(Country c : cList){
			 if(c.getCountryName().equals(countryName)){
				 Set<County> counties = c.getCounties();
				 return counties;
			 }
		 }
		return new HashSet<>();
	}
	
	public  Set<Constituency> getConsBeans(String countryName, String countyName){
		 List<Country> cList = getAllCountries();
		 for(Country c : cList){
			 if(c.getCountryName().equals(countryName)){
				 Set<County> counties = c.getCounties();
				 if(counties != null && !counties.isEmpty()){
					 for(County county : counties){
						if(county.getCountyName().equals(countyName)){
							return county.getConstituencies();
						}
					 }
				 }
			 }
		 }
		return new HashSet<>();
	}
	
	public  Set<ConstituencyWard> getConsWardBeans(String countryName, String countyName, String consName){
		 List<Country> cList = getAllCountries();
		 for(Country c : cList){
			 if(c.getCountryName().equals(countryName)){
				 Set<County> counties = c.getCounties();
				 if(counties != null && !counties.isEmpty()){
					 for(County county : counties){
						if(county.getCountyName().equals(countyName)){
							Set<Constituency> constituencies = county.getConstituencies();
							if(constituencies != null && !constituencies.isEmpty()){
								for(Constituency cons : constituencies){
									if(cons.getCnsttncyName().equals(consName)){
										return cons.getConstituencyWards();
									}
								}
							}
						}
					 }
				 }
			 }
		 }
		return new HashSet<>();
	}
	
	public Map<Long,String> getCountiesMapForCountry(String countryName){
		Map<Long,String> country = new HashMap<>();
		 List<Country> cList = getAllCountries();
		 for(Country c : cList){
			 if(c.getCountryName().equals(countryName)){
				 Set<County> counties = c.getCounties();
				 if(counties != null && !counties.isEmpty()){
					 for(County county : counties){
						 country.put(county.getCountyId(), county.getCountyName());
					 }
				 }
				 break;
			 }
		 }
		return country;
	}
	
	public Map<Long,String> getConstituenciesMapForCounty(String countryName, String countyName){
		Set<County> counties = getCountiesBeans(countryName);
		Map<Long,String> cons = new HashMap<>();
		if(counties != null && !counties.isEmpty()){
			for(County county : counties){
				if(county.getCountyName().equals(countyName)){
					for(Constituency constituency : county.getConstituencies()){
						cons.put(constituency.getCnsttncyId(), constituency.getCnsttncyName());
					}
					return cons;
				}
			}
		}
		return new HashMap<>();
	}
	
	public Map<Long,String> getConsWardsMapForCounty(String countryName, String countyName, String consName){
		Set<Constituency> constituencies = getConsBeans(countryName, countyName);
		Map<Long,String> wards = new HashMap<>();
		if(constituencies != null && !constituencies.isEmpty()){
			for(Constituency constituency : constituencies){
				if(constituency.getCnsttncyName().equals(consName)){
					for(ConstituencyWard ward : constituency.getConstituencyWards()){
						wards.put(ward.getWardId(), ward.getWardName());
					}
					return wards;
				}
			}
		}
		return wards;
	}
	
	public Map<Long,String> getNameIdForId(String type, Map<String, Long> regionIds){
		
		Map<Long,String> nameIds = new HashMap<>();
		Map<String,Long> values = new HashMap<>();
		
		if(HunterConstants.RECEIVER_LEVEL_COUNTRY.equals(type)){
			List<Country> countries = getAllCountries();
			for(Country country : countries){
				if(country.getCountryId().equals(regionIds.get(HunterConstants.RECEIVER_LEVEL_COUNTRY))){
					nameIds.put(country.getCountryId(), country.getCountryName());
					logger.debug(HunterUtility.stringifyMap(nameIds)); 
					return nameIds;
				}
			}
			
		}else if(HunterConstants.RECEIVER_LEVEL_COUNTY.equals(type)){
			
			values.clear();
			values.put(HunterConstants.RECEIVER_LEVEL_COUNTRY, regionIds.get(HunterConstants.RECEIVER_LEVEL_COUNTRY) ); 
			Map<Long,String> country = getNameIdForId(HunterConstants.RECEIVER_LEVEL_COUNTRY, values);
			logger.debug(HunterUtility.stringifyMap(country)); 
			
			String countryName = country.get(Long.parseLong(regionIds.get(HunterConstants.RECEIVER_LEVEL_COUNTRY)+""));
			Map<Long,String> counties = getCountiesMapForCountry(countryName);  
			nameIds.clear();
			
			for(Map.Entry<Long, String> entry : counties.entrySet()){
				if(entry.getKey().equals(regionIds.get(HunterConstants.RECEIVER_LEVEL_COUNTY))){
					nameIds.put(entry.getKey(), entry.getValue());
					logger.debug(HunterUtility.stringifyMap(nameIds)); 
					return nameIds;
				}
			}
			  
		}else if(HunterConstants.RECEIVER_LEVEL_CONSITUENCY.equals(type)){
			
			values.clear();
			values.put(HunterConstants.RECEIVER_LEVEL_COUNTRY, regionIds.get(HunterConstants.RECEIVER_LEVEL_COUNTRY)); 
			Map<Long,String> country = getNameIdForId(HunterConstants.RECEIVER_LEVEL_COUNTRY, values);
			logger.debug(HunterUtility.stringifyMap(country)); 
			
			values.put(HunterConstants.RECEIVER_LEVEL_COUNTY, regionIds.get(HunterConstants.RECEIVER_LEVEL_COUNTY)); 
			Map<Long,String> county = getNameIdForId(HunterConstants.RECEIVER_LEVEL_COUNTY, values);
			logger.debug(HunterUtility.stringifyMap(county));
			
			String countryName = country.get(Long.parseLong(regionIds.get(HunterConstants.RECEIVER_LEVEL_COUNTRY)+""));
			String countyName = county.get(Long.parseLong(regionIds.get(HunterConstants.RECEIVER_LEVEL_COUNTY)+""));
			
			Map<Long,String> consituencies = getConstituenciesMapForCounty(countryName, countyName);  
			nameIds.clear();
			
			for(Map.Entry<Long, String> entry : consituencies.entrySet()){
				if(entry.getKey().equals(regionIds.get(HunterConstants.RECEIVER_LEVEL_CONSITUENCY))){
					nameIds.put(entry.getKey(), entry.getValue());
					logger.debug(HunterUtility.stringifyMap(nameIds)); 
					return nameIds;
				}
			}
			  
		}else if(HunterConstants.RECEIVER_LEVEL_WARD.equals(type)){
			
			values.clear();
			values.put(HunterConstants.RECEIVER_LEVEL_COUNTRY, regionIds.get(HunterConstants.RECEIVER_LEVEL_COUNTRY)); 
			Map<Long,String> country = getNameIdForId(HunterConstants.RECEIVER_LEVEL_COUNTRY, values);
			logger.debug(HunterUtility.stringifyMap(country)); 
			
			values.put(HunterConstants.RECEIVER_LEVEL_COUNTY, regionIds.get(HunterConstants.RECEIVER_LEVEL_COUNTY)); 
			Map<Long,String> county = getNameIdForId(HunterConstants.RECEIVER_LEVEL_COUNTY, values);
			logger.debug(HunterUtility.stringifyMap(county));
			
			values.put(HunterConstants.RECEIVER_LEVEL_CONSITUENCY, regionIds.get(HunterConstants.RECEIVER_LEVEL_CONSITUENCY)); 
			Map<Long,String> constituency = getNameIdForId(HunterConstants.RECEIVER_LEVEL_CONSITUENCY, values);
			logger.debug(HunterUtility.stringifyMap(constituency));
			

			String countryName = country.get(Long.parseLong(regionIds.get(HunterConstants.RECEIVER_LEVEL_COUNTRY)+""));
			String countyName = county.get(Long.parseLong(regionIds.get(HunterConstants.RECEIVER_LEVEL_COUNTY)+""));
			String consName = constituency.get(Long.parseLong(regionIds.get(HunterConstants.RECEIVER_LEVEL_CONSITUENCY)+""));  
			
			Map<Long,String> wards = getConsWardsMapForCounty(countryName,countyName,consName);  
			nameIds.clear();
			
			for(Map.Entry<Long, String> entry : wards.entrySet()){
				if(entry.getKey().equals(regionIds.get(HunterConstants.RECEIVER_LEVEL_WARD))){
					nameIds.put(entry.getKey(), entry.getValue());
					logger.debug(HunterUtility.stringifyMap(nameIds)); 
					return nameIds;
				}
			}
			  
		}
		
		return nameIds;
	}
	
	public String getDefaultEmailTemplate(){
		
		logger.debug("Fetching default XML..."); 
		
		if( HunterCache.getInstance().containsKey(HunterConstants.DEFAULT_EMAIL_TEMPLATE) ){ 
			return HunterUtility.getStringOrNullOfObj( HunterCache.getInstance().get(HunterConstants.DEFAULT_EMAIL_TEMPLATE) ); 
		}
		
		String defTempLoc = HunterURLConstants.RESOURCE_BASE_XML_PATH + "emailTemplateMetadataSample.xml";
		XMLService clieConfigService = HunterUtility.getXMLServiceForFileLocation( defTempLoc );
		String xmlStr = clieConfigService == null ? null : clieConfigService.toString();
		
		logger.debug( xmlStr ); 
		
		return xmlStr;
		
	}
	
	
	
	
	
}
