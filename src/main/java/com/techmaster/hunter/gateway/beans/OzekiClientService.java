package com.techmaster.hunter.gateway.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.GateWayMessage;
import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.obj.beans.TaskClientConfigBean;
import com.techmaster.hunter.task.process.TaskProcessor;

public class OzekiClientService extends AbstractGateWayClientService {
	
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public Map<String, Object> execute(Map<String, Object> executeParams) {
		
		Map<String, Object> results = new HashMap<String, Object>();
		List<String> procErrors = new ArrayList<>();
		String procStatus = HunterConstants.STATUS_FAILED;
		Map<String,Object> processResults = new HashMap<>();
		
		try{
			
			logger.debug("Starting to process ozeki client task.");
			Task task = (Task)executeParams.get(TASK_BEAN);
			TaskClientConfigBean configBean = readConfigurations(HunterConstants.CLIENT_OZEKI);
			AuditInfo auditInfo = (AuditInfo)executeParams.get(PARAM_AUDIT_INFO);
			Set<GateWayMessage> allMsgs = createGtwayMsgsFrAllTskGtwyMssgs(task, auditInfo);
			
			logger.debug("Total gateway messages : " + allMsgs.size());
			processResults = TaskProcessor.getInstance().process(task, configBean, allMsgs, auditInfo);
			putStatusAndErrors(procErrors, procStatus=HunterConstants.STATUS_FAILED, results);
			logger.debug("Successfully submitted ozeki client task for processing!"); 
			return processResults;
			
		}catch(Exception e){
			e.printStackTrace();
			procErrors.add("Application error ocurred while submitting task for processing");
			procErrors.add(e.getMessage());
			procStatus = HunterConstants.STATUS_FAILED;
			putStatusAndErrors(procErrors, procStatus, results);
		}
		return processResults;
	}

	
}
