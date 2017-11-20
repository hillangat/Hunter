package com.techmaster.hunter.task.process;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.gateway.beans.GateWayClientHelper;
import com.techmaster.hunter.obj.beans.GateWayMessage;
import com.techmaster.hunter.obj.beans.TaskClientConfigBean;
import com.techmaster.hunter.util.HunterUtility;

public class ClientReqBodyHelper {
	
	private static ClientReqBodyHelper instance;
	private static Logger logger = Logger.getLogger(ClientReqBodyHelper.class);
	
	static{
		if(instance == null){
			synchronized (ClientReqBodyHelper.class) {
				instance = new ClientReqBodyHelper();
			}
		}
	}
	
	public static ClientReqBodyHelper getInstance(){
		return instance;
	}
	
	public void setRequestBody(Set<GateWayMessage> messages, TaskClientConfigBean configBean){
		String method = configBean.getActiveMethod();
		String clientName = configBean.getClientName();
		logger.debug("setting request body for client ( " + clientName + " ) for method ( " + method + " )");  
		if(HunterConstants.CLIENT_CM.equals(clientName)){
			setCMRequestBody(messages, configBean); 
		}else if(HunterConstants.CLIENT_OZEKI.equals(clientName)){
			setOzekiRequestBody(messages, configBean); 
		}else{
			logger.debug("No service available configured to set request bodies for client ( " + clientName + " ) and method ( " + method + " )");
		}
	}
	
	private void setCMRequestBody(Set<GateWayMessage> messages, TaskClientConfigBean configBean){
		String activeMethod = configBean.getActiveMethod();
		if(HunterUtility.notNullNotEmpty(activeMethod) && HunterConstants.METHOD_POST.equals(activeMethod)){
			Set<GateWayMessage> inMsgs = new HashSet<>();
			for(GateWayMessage message : messages){
				inMsgs.add(message);
				String body = GateWayClientHelper.getInstance().getCMPostRequestBody(configBean, inMsgs);
				inMsgs.clear();
				message.setRequestBody(body != null ? body.getBytes() : null); 
			}
		}else{
			
			Map<String, Object> params = new HashMap<>();
			String baseUrl = configBean.getMethodURL();
			String token = configBean.getConfigs().get(HunterConstants.CONFIG_UUID).toString();
			String from = configBean.getConfigs().get(HunterConstants.CONFIG_SENDER_KEY);
			
			params.put(HunterConstants.BASE_URL, baseUrl);
			params.put(HunterConstants.TOKEN, token);
			params.put(HunterConstants.FROM, from);
			
			for(GateWayMessage message : messages){
				
				String to = message.getContact();
				String reference = message.getClientTagKey(); 
				
				params.put(HunterConstants.BODY, message.getText());
				params.put(HunterConstants.REFERENCE, reference);
				params.put(HunterConstants.TO, to);
				
				String getString = GateWayClientHelper.getInstance().createGetStringFromParams(params);
				message.setRequestBody(getString != null ? getString.getBytes() : null);
			}
		}
	}
	
	private void setOzekiRequestBody(Set<GateWayMessage> messages, TaskClientConfigBean configBean){
		String activeMethod = configBean.getActiveMethod();
		if(HunterUtility.notNullNotEmpty(activeMethod) && HunterConstants.METHOD_POST.equals(activeMethod)){
			Set<GateWayMessage> inMsgs = new HashSet<>();
			for(GateWayMessage message : messages){
				inMsgs.add(message);
				String body = GateWayClientHelper.getInstance().getOzekiPostRequestBody(configBean, inMsgs);
				inMsgs.clear();
				message.setRequestBody(body != null ? body.getBytes() : null); 
			}
		}else if(HunterUtility.notNullNotEmpty(activeMethod) && HunterConstants.METHOD_GET.equals(activeMethod)){
			
			String baseUrl = configBean.getMethodURL();
			Map<String, Object> consParam = new HashMap<>();
			
			consParam.put(HunterConstants.BASE_URL, baseUrl);
			consParam.put("action", "sendmessage");
			consParam.put("messagecount", Integer.toString(messages.size()));
			consParam.put("username", configBean.getUserName());
			consParam.put("password", configBean.getPassword());
			
			Map<String, Object> params = new HashMap<>();
			int i=0;
			
			for(GateWayMessage message : messages){
				params.clear();
				params.putAll(consParam); 
				params.put("recipient"+i, message.getContact());
				params.put("messagetyp"+i, configBean.getConfigs().get("messagetype")); 
				params.put("messagedata"+i, message.getText());
				String requestBody = null;
				try {
					requestBody = HunterUtility.urlEncodeRequestMap(params, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				message.setRequestBody(requestBody != null ? requestBody.getBytes() : null);
				i++;
			}
		}
	}
	

}
