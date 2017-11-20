package com.techmaster.hunter.gateway.beans;

import java.util.Set;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.obj.beans.GateWayMessage;
import com.techmaster.hunter.util.HunterUtility;
import com.techmaster.hunter.xml.XMLService;

public class GateWayResponseHanlder {

	
	private XMLService respXMLService = HunterCacheUtil.getInstance().getXMLService(HunterConstants.RESPONSE_CONFIG_CACHED_SERVICE);
	private static GateWayResponseHanlder instance;
	private GateWayResponseHanlder(){};
	private Logger logger = Logger.getLogger(GateWayResponseHanlder.class);
	
	static{
		if(instance == null){
			synchronized (GateWayResponseHanlder.class) {
				instance = new GateWayResponseHanlder();
			}
		}
	}
	
	public static GateWayResponseHanlder getInstance(){
		return instance;
	}
	
	public void setAllStatuses(String status, Set<GateWayMessage> messages){
		if(messages != null && !messages.isEmpty()){
			for(GateWayMessage msg : messages){
				msg.setStatus(status); 
			}
		}
	}
	
	public String setStatusFromResponseText(String responseText, String clientName, Set<GateWayMessage> messages){
		if(responseText == null){
			logger.debug("Response text is null and will not process response for client : " + clientName);
			return null;
		}
		String status = null;
		if(clientName != null && HunterConstants.CLIENT_CM.equals(clientName)){
			status = HunterUtility.notNullNotEmpty(responseText) ? HunterConstants.STATUS_FAILED : HunterConstants.STATUS_SUCCESS;
			setAllStatuses(status, messages);
		}else if(clientName != null && HunterConstants.CLIENT_OZEKI.equals(clientName)){
			logger.debug("Wiring up status configurations with response data for ozeki client...");
			XMLService xmlService = HunterUtility.getXMLServiceForStringContent(responseText);
			if(messages != null && !messages.isEmpty()){
				for(GateWayMessage gateWayMessage : messages){
					String key = gateWayMessage.getClientTagKey();
					String mainPath = "response/data/acceptreport"+ key;
					String stsCodePath = mainPath +"/statuscode" + key;
					String stsMsgPath = mainPath +"/statusmessage" + key;
					//String msgIdPath = mainPath +"/messageid" + key;
					String stsCode = xmlService.getTextValue(stsCodePath);
					String stsMsg = xmlService.getTextValue(stsMsgPath); 
					//String msgId = xmlService.getTextValue(msgIdPath);
					gateWayMessage.setClntRspCode(stsCode);
					gateWayMessage.setClntRspText(stsMsg);
				}
			}
			logger.debug("Done wiring up status configurations with response data!!");
		}
		return status;
	}
	
	public void updateGateWayMessageForStatus(String clientName, String responseCode, GateWayMessage gateWayMessage, String statusType){
		
		if(clientName == null && statusType != null && responseCode != null){
			if(statusType.equals(HunterConstants.STATUS_TYPE_CLIENT)){
				gateWayMessage.setClntRspCode(responseCode);
				gateWayMessage.setClntRspText(responseCode); 
			}else if(statusType.equals(HunterConstants.STATUS_TYPE_DELIVERY)){
				gateWayMessage.setSubsRspnsCode(responseCode); 
				gateWayMessage.setSubsRspnsText(responseCode);
			}
			
			return;
		}
		
		String statusText = null;
		
		if (statusType == HunterConstants.STATUS_TYPE_DELIVERY)
			statusText = getClientTextForCode(clientName, responseCode, statusType);

		if(clientName != null && statusType == HunterConstants.STATUS_TYPE_CLIENT){
			gateWayMessage.setClntRspCode(responseCode);
			gateWayMessage.setClntRspText(responseCode); 
		}else if(clientName != null &&  statusType.equals(HunterConstants.STATUS_TYPE_DELIVERY)){
			gateWayMessage.setSubsRspnsCode(responseCode); 
			gateWayMessage.setSubsRspnsText(statusText);
		}
		
		
		
	}
	
	
	public String getClientTextForCode(String clientName, String responseCode, String statusType){
		
		String xPath = "//client[@clientName=\""+ clientName +"\"]/configs/config[@configType=\""+ statusType +"\"]/metadata[code=\""+ responseCode +"\"]";
		NodeList nodeList = respXMLService.getNodeListForXPath(xPath);
		
		Node node = nodeList.item(0); 
		NodeList metadata = node.getChildNodes();
		
		String code = null;
		String text = null;
		String desc = null;
		
		for(int i=0; i<metadata.getLength(); i++){
			Node datum = metadata.item(i);
			if(datum.getNodeName().equals("code"))
				code = datum.getTextContent();
			else if(datum.getNodeName().equals("text")){
				text = datum.getTextContent();
			}else if(datum.getNodeName().equals("desc")){
				desc = datum.getTextContent();
			}
		}
		
		logger.debug("Code  :  " + code);
		logger.debug("text  :  " + text);
		logger.debug("desc  :  " + desc);
		
		return text;
		
	}
	
	public static void main(String[] args) {
		
		String value = new GateWayResponseHanlder().getClientTextForCode("CM", "7", HunterConstants.STATUS_TYPE_CLIENT);
		System.out.println(value); 
		
	}
	
	
	
	
	
	
}
