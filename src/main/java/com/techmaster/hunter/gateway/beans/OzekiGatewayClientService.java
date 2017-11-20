package com.techmaster.hunter.gateway.beans;

import com.techmaster.hunter.obj.beans.Task;

public interface OzekiGatewayClientService {

	public String preSendMsgs(Task task);
	public String sendMsgs(Task task);
	public void handleMsgResponse(String response);
	public void getAndProcessSentMsgs(Task task);
	
	
	
}
