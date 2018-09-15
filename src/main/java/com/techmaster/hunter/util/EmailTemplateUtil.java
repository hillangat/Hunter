package com.techmaster.hunter.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.EmailTemplateObjDao;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.json.EmailTemplateObjJson;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.EmailTemplateObj;
import com.techmaster.hunter.xml.XMLService;

public class EmailTemplateUtil {
	
	private static EmailTemplateUtil instance = null;
	private Logger logger = Logger.getLogger(getClass());
	
	static {
		synchronized (EmailTemplateUtil.class) {
			 if(instance == null){
				 instance = new EmailTemplateUtil();
			 }
		}
	}

	public static EmailTemplateUtil getInstance(){
		return instance;
	}
	
	public EmailTemplateObj createOrUpdateTemplateFromJson(EmailTemplateObjJson templateObjJson, AuditInfo auditInfo){
		
		/* Update audit for UI */
		templateObjJson.setLastUpdatedBy(auditInfo.getLastUpdatedBy());  
		templateObjJson.setLastUpdate(HunterUtility.formatDate(auditInfo.getLastUpdate(), HunterConstants.DATE_FORMAT_STRING));
		
		logger.debug("Converting and email template object json to object: " + templateObjJson);
		EmailTemplateObjDao emailTemplateObjDao = HunterDaoFactory.getObject(EmailTemplateObjDao.class);
		EmailTemplateObj templateObj = new EmailTemplateObj();
		Long templateId = templateObjJson.getTemplateId();
		String status = templateObjJson.getStatus();
		
		if(  templateId != null && templateId != 0 ){
			
			/* Update audit for DB */
			auditInfo.setCreatedBy(templateObjJson.getCreatedBy());
			auditInfo.setCretDate(HunterUtility.parseDate(templateObjJson.getCretDate(), HunterConstants.DATE_FORMAT_STRING));
			
			logger.debug("Template object is existent. Loading it for updates..."); 
			templateObj = emailTemplateObjDao.getTemplateObjById(templateId);
			status = templateObj.getStatus();
		}else{
			status = HunterConstants.STATUS_DRAFT;
			templateObjJson.setCreatedBy(auditInfo.getCreatedBy());
			templateObjJson.setCretDate(HunterUtility.formatDate(auditInfo.getCretDate(), HunterConstants.DATE_FORMAT_STRING));  
		}
		
		templateObj.setStatus(status); 
		templateObj.setAuditInfo(auditInfo);
		templateObj.setTemplateDescription(templateObjJson.getTemplateDescription());
		templateObj.setTemplateId(templateId); 
		templateObj.setTemplateName(templateObjJson.getTemplateName());
		
		if( templateId != null && templateId != 0  ) 
			emailTemplateObjDao.updateEmailTemplateObj(templateObj);
		else 
			emailTemplateObjDao.insertTemplateObj(templateObj); 
		
		/* So when the json is returned to the client, it has an id. */
		templateObjJson.setTemplateId(templateObj.getTemplateId()); 
		
		return templateObj;
	}

	
	public String validateTemplateDeletion(EmailTemplateObj emailTemplateObj) {
		String message = "Email Template does not exist";
		if( emailTemplateObj != null ){
			HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
			String query = hunterJDBCExecutor.getQueryForSqlId("getEmailMsgsThatUseTemplate");
			List<Object> values = new ArrayList<>();
			values.add(emailTemplateObj.getTemplateName());
			List<Map<String, Object>> rowMapList = hunterJDBCExecutor.executeQueryRowMap(query, values);
			List<String> results = new ArrayList<>();
			if( HunterUtility.isCollNotEmpty(rowMapList) ){ 
				for(Map<String,Object> rowMap : rowMapList){
					results.add(HunterUtility.getStringOrNullOfObj(rowMap.get("TSK_INFO"))); 
				}
			}
			message = HunterUtility.getCommaDelimitedStrings(results);
		}
		logger.debug(message);
		return message;
	}
	
	
	public Map<String,XMLService> getAccumulatedEmailTemplates(){
		logger.debug("Getting accumulated email template xml services..."); 
		EmailTemplateObjDao emailTemplateObjDao = HunterDaoFactory.getObject(EmailTemplateObjDao.class);
		List<EmailTemplateObj> emailTemplateObjs = emailTemplateObjDao.getAllTemplateObjs();
		Map<String,XMLService> xmlServices = new HashMap<String, XMLService>(); 
		XMLService metaService = null;
		for(EmailTemplateObj emailTemplateObj :emailTemplateObjs){
			String templateName = emailTemplateObj.getTemplateName();
			logger.debug("Processing email template for template name : " + templateName); 
			String metaDataDoc = HunterUtility.getBlobStrFromDB("xmlTemplates", "templateId", Long.toString( emailTemplateObj.getTemplateId() ), EmailTemplateObj.class);
			if( HunterUtility.notNullNotEmpty(metaDataDoc) ){
				String htmlDoc = HunterUtility.getBlobStrFromDB("documentMetadata", "templateId", Long.toString( emailTemplateObj.getTemplateId() ), EmailTemplateObj.class);
				metaService = HunterUtility.getXMLServiceForStringContent(metaDataDoc);
				NodeList templatesList = metaService.getNodeListForPathUsingJavax("template[@name=\""+ templateName +"\"]");
				if( templatesList != null && templatesList.getLength() != 0 ){
					Node tempNode = templatesList.item(0);
					Element element = metaService.getXmlTree().getDoc().createElement("content");
					CDATASection cdata = metaService.getXmlTree().getDoc().createCDATASection( htmlDoc );
					element.appendChild( cdata );
					tempNode.appendChild(element);
				}
				xmlServices.put(templateName, metaService);
			}else{
				logger.warn("Warning warning!!!!!");
				logger.warn("No template blob set for template name : " + emailTemplateObj.getTemplateName() + ". Setting null value to the map. Please check and delete and refresh case or fix the template object."); 
				xmlServices.put(templateName, null);
			}
			logger.debug("Successfully processed email template for template name : " + templateName); 
		}
		logger.debug("Successfully obtained accumulated email template xml services!");
		return xmlServices; 
	}
	 
	public XMLService getMergedXMLServices( Map<String,XMLService> xmlServices ){
		logger.debug("Merging  email template xml services...."); 
		XMLService xmlService = HunterUtility.getXMLServiceForStringContent("<?xml version=\"1.0\" encoding=\"UTF-8\"?><templates></templates>");
		Node baseRootEle = xmlService.getXmlTree().getDoc().getDocumentElement();
		for(Map.Entry<String, XMLService> entry : xmlServices.entrySet()){
			XMLService templateService = entry.getValue();
			if( templateService != null ){
				Node node = templateService.getXmlTree().getDoc().getDocumentElement();
				Node node2 = xmlService.getXmlTree().getDoc().importNode(node, true);
				baseRootEle.appendChild(node2);
			}else{
				logger.warn("Warning warning!!!!!  No template service set for template name : " + entry.getKey()); 
			}
		}
		logger.debug(xmlService);
		logger.debug("Successfully merged email template xml services!");
		return xmlService;
	}
	
	public XMLService getAllTemplateXMLService(){
		logger.debug("getting all merged email template services..."); 
		return getMergedXMLServices(getAccumulatedEmailTemplates());
	}
	
	
}
