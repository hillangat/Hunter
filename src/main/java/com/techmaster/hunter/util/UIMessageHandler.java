package com.techmaster.hunter.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.techmaster.hunter.constants.HunterURLConstants;
import com.techmaster.hunter.constants.UIMessageConstants;
import com.techmaster.hunter.xml.XMLService;

public class UIMessageHandler {
	
	private static Map<String, String> uiMessages = new HashMap<String, String>();
	private static UIMessageHandler instance;
	private static final Logger logger = HunterLogFactory.getLog(UIMessageHandler.class); 
	
	static{
		
		if(instance == null){
			synchronized (UIMessageHandler.class) { 
				instance = new UIMessageHandler();
			}
		}
		
		populateUIMessages();
	}
	
	public static UIMessageHandler getInstance(){
		return instance;
	}
	
	private static void populateUIMessages(){
		logger.debug("populating ui messages..." ); 
		XMLService service = HunterUtility.getXMLServiceForFileLocation(HunterURLConstants.UI_MSG_XML_FL_LOC_PATH);
		NodeList messages = service.getNodeListForXPath("//message");
		for(int i=0; i<messages.getLength(); i++){
			Node message = messages.item(i);
			if(message.getNodeName().equals("message")){
				String id = HunterUtility.getNodeAttr(message, "id", String.class); 
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
		logger.debug("Succesfully populated ui messages!!" );
	}
	
	private static void updateDOMFromDBAndPopulateUIMessages(){
		logger.info("updateDOMFromDBAndPopulateUIMessages() needs to get ui messages document from database, update uiMessages xml and then populate the map");
	}
	
	public String getMsgTxtForMsgId(String msgId){
		return uiMessages.get(msgId+"_TEXT");
	}
	
	public String getMsgDescForMsgId(String msgId){
		return uiMessages.get(msgId+"_DESC");
	}
	
	
	public static void refreshMessages(){
		updateDOMFromDBAndPopulateUIMessages();
	}
	
	
	public static void main(String[] args) {
		
		String text = UIMessageHandler.getInstance().getMsgTxtForMsgId(UIMessageConstants.MSG_TASK_001); 
		logger.debug("Text >> " + text);
		String desc = UIMessageHandler.getInstance().getMsgDescForMsgId(UIMessageConstants.MSG_TASK_001);
		logger.debug("Desc >> " + desc);
		
	}
	

}
