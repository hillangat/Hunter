package com.techmaster.hunter.task.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.techmaster.hunter.obj.beans.GateWayMessage;
import com.techmaster.hunter.obj.beans.TaskClientConfigBean;

public class TaskProcessWorkerFactory {
	
	public static TaskProcessWorkerFactory factory;
	private static Long workerId = 0L;
	
	public static final String GET_ONE_REQ_PR_MSG = "GET_ONE_REQ_PR_MSG";
	public static final String GET_MUL_REQ_PR_MSG = "GET_MUL_REQ_PR_MSG";
	public static final String POST_ONE_REQ_PR_MSG = "GET_ONE_REQ_PR_MSG";
	public static final String POST_MUL_REQ_PR_MSG = "GET_MUL_REQ_PR_MSG";
	
	public static final String DEFAULT_POST_MUL_REQ_PR_MSG = "DEFAULT_POST_MUL_REQ_PR_MSG";
	public static final String DEFAULT_GET_MUL_REQ_PR_MSG = "DEFAULT_GET_MUL_REQ_PR_MSG";
	public static final String DEFAULT_EMAIL_MUL_REQ_PR_MSG = "DEFAULT_EMAIL_MUL_REQ_PR_MSG";
	
	static{
		if(factory == null){
			synchronized (TaskProcessWorkerFactory.class) {
				factory = new TaskProcessWorkerFactory();
			}
		}
	}
	
	public static TaskProcessWorkerFactory getInstance(){
		return factory;
	}

	public List<TaskProcessWorker> getTaskProcessWorkers(String workerType,TaskClientConfigBean configBean, List<Set<GateWayMessage>> messageSets, String processJobKey){ 
		
		List<TaskProcessWorker> processWorkers = new ArrayList<>();
		
		if(GET_ONE_REQ_PR_MSG.equals(workerType)){ 
			for(Set<GateWayMessage> messageSet : messageSets){
				Long workerId = getWorkerId();
				TaskProcessWorker processWorker = new HunterProcessWorker(configBean, messageSet, workerId, processJobKey);
				processWorkers.add(processWorker);
			}
			return processWorkers;
		}else if(GET_MUL_REQ_PR_MSG.equals(workerType)){ 
			for(Set<GateWayMessage> messageSet : messageSets){
				Long workerId = getWorkerId();
				TaskProcessWorker processWorker = new HunterProcessWorker(configBean, messageSet, workerId, processJobKey);
				processWorkers.add(processWorker);
			}
			return processWorkers;
		}else if(POST_MUL_REQ_PR_MSG.equals(workerType)){
			
		}else if(POST_ONE_REQ_PR_MSG.equals(workerType)){ 
			
		}else if(DEFAULT_POST_MUL_REQ_PR_MSG.equals(workerType)){ 
			
		}else if(DEFAULT_GET_MUL_REQ_PR_MSG.equals(workerType)){ 
			
		}else if(DEFAULT_EMAIL_MUL_REQ_PR_MSG.equals(workerType)){ 
			for(Set<GateWayMessage> messageSet : messageSets){
				Long workerId = getWorkerId();
				TaskProcessWorker processWorker = new HunterEmailProcessWorker(configBean, messageSet, workerId, processJobKey);
				processWorkers.add(processWorker);
			}
			return processWorkers;
		}
		
		return processWorkers;
	}
	
	
	
	private Long getWorkerId(){
		workerId++;
		return workerId;
	}
}
