package com.techmaster.hunter.gateway.beans;

import java.util.Set;

import org.apache.log4j.Logger;

import com.techmaster.hunter.obj.beans.GateWayMessage;
import com.techmaster.hunter.util.HunterUtility;
import com.techmaster.hunter.xml.XMLService;

public class OzekiResponseHelper {
	
	private static OzekiResponseHelper instance;
	private static Logger logger = Logger.getLogger(OzekiResponseHelper.class);
	private OzekiResponseHelper(){};
	
	static{
		if(instance == null){
			synchronized (OzekiResponseHelper.class) {
				instance = new OzekiResponseHelper();
			}
		}
	}
	
	public static OzekiResponseHelper getInstance(){
		return instance;
	}
	
	public void execute(String response, Set<GateWayMessage> messages){
		logger.debug("Wiring up status configurations with response data...");
		XMLService xmlService = HunterUtility.getXMLServiceForStringContent(response);
		if(messages != null && !messages.isEmpty()){
			int idex = 0;
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
				if(idex > 1){
					break;
				}
			}
		}
		logger.debug("Done wiring up status configurations with response data!!");
	}
	

}
