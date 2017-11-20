package com.techmaster.hunter.gateway.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.constants.UIMessageConstants;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.GateWayMessage;
import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.obj.beans.TaskClientConfigBean;
import com.techmaster.hunter.task.process.TaskProcessor;

public class CMClientService extends AbstractGateWayClientService{
	
	private static Logger logger = Logger.getLogger(CMClientService.class);
	
	
	@Override
	public Map<String, Object> execute(Map<String, Object> executeParams) {
		
		Map<String, Object> results = new HashMap<String, Object>();
		List<String> procErrors = new ArrayList<>();
		String procStatus = HunterConstants.STATUS_FAILED;
		
		try{
			logger.debug("Starting to process cm client task.");
			Task task = getObjFromExecParams(Task.class, TASK_BEAN, executeParams);
			TaskClientConfigBean configBean = readConfigurations(HunterConstants.CLIENT_CM);
			AuditInfo auditInfo = (AuditInfo)executeParams.get(PARAM_AUDIT_INFO);
			Set<GateWayMessage> allMsgs = createGtwayMsgsFrAllTskGtwyMssgs(task, auditInfo);
			
			if(allMsgs == null || allMsgs.isEmpty()){
				procErrors.add(HunterCacheUtil.getInstance().getUIMsgTxtForMsgId(UIMessageConstants.MSG_TASK_011)); 
				putStatusAndErrors(procErrors, procStatus, results);
				return results;
			}
			
			logger.debug("Total gateway messages : " + allMsgs.size());
			Map<String,Object> processResults = TaskProcessor.getInstance().process(task, configBean, allMsgs, auditInfo);
			putStatusAndErrors(procErrors, procStatus, results);
			
			return processResults;
		}catch(Exception e){
			procErrors.add(e.getMessage());
			putStatusAndErrors(procErrors, procStatus, results);
			e.printStackTrace();
			return results;
		}
	}
}
