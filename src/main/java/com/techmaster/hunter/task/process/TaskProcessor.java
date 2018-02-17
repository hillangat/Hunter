package com.techmaster.hunter.task.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.constants.TaskProcessConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.gateway.beans.GateWayClientHelper;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.GateWayMessage;
import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.obj.beans.TaskClientConfigBean;
import com.techmaster.hunter.util.HunterUtility;


public class TaskProcessor {
	
	private Logger logger = Logger.getLogger(getClass());
	private static TaskProcessor instance;
	
	static{
		if(instance == null){
			synchronized (TaskProcessor.class) {
				instance = new TaskProcessor();
			}
		}
	}
	
	public static TaskProcessor getInstance(){
		return instance;
	}
	
	private List<Set<GateWayMessage>> getBatches(Set<GateWayMessage> messages, int batchSize){
		List<Set<GateWayMessage>> batches  = new ArrayList<>();
		List<GateWayMessage> listMsgs = new ArrayList<>();
		listMsgs.addAll(messages);
		Set<GateWayMessage> batch = new HashSet<>();
		for(int i=0; i<listMsgs.size();i++){
			GateWayMessage message = listMsgs.get(i);
			if(batch.size() <= batchSize){
				if(batch.size() == 0){
					batches.add(batch);
				}
				batch.add(message);
			}
			if(batch.size() == batchSize){
				batch = new HashSet<>();
			}
		}
		return batches;
	}
	
	public void sendTaskProcessBusinessEmail(String mailType, Long taskId){
		Map<String, Object> cntntParams = GateWayClientHelper.getInstance().getCntntParamsFrTskBsnsEmail(HunterConstants.MAIL_TYPE_TASK_PROCESS_NOTIFICATION, taskId);
		HunterBusinessEmailWorker worker = new HunterBusinessEmailWorker(mailType, HunterConstants.MAIL_TYPE_TASK_PROCESS_NOTIFICATION, cntntParams);
		worker.start();
	}
	
	private int determinePoolSize(Set<GateWayMessage> messages, TaskClientConfigBean configBean) {
		int batchMark = configBean.getBatchMark();
		int batchNo = configBean.getBatchNo();
		if(batchMark <= messages.size()){
			int batches = messages.size()%batchNo;
			//divisible exactly.
			if(batches == 0){
				batches = messages.size()/batchNo;
			
			// add additional batch to cater for lone messages.
			}else{
				batches = messages.size()/batchNo;
				batches++;
			}
			return batches;
		}else{
			return 1;
		}
	}
	
	private String getWorkerName(TaskClientConfigBean configBean){
		if(!configBean.isWorkerDefault()){
			return configBean.getWorkerName();
		}else if(configBean.getBatchType().equals("multiple") && configBean.getActiveMethod().equalsIgnoreCase("GET")){ 
			return TaskProcessWorkerFactory.GET_MUL_REQ_PR_MSG;
		}else if(configBean.getBatchType().equals("singular") && configBean.getActiveMethod().equalsIgnoreCase("GET")){ 
			return TaskProcessWorkerFactory.GET_ONE_REQ_PR_MSG;
		}else if(configBean.getBatchType().equals("multiple") && configBean.getActiveMethod().equalsIgnoreCase("POST")){ 
			return TaskProcessWorkerFactory.POST_MUL_REQ_PR_MSG;
		}else if(configBean.getBatchType().equals("singular") && configBean.getActiveMethod().equalsIgnoreCase("POST")){ 
			return TaskProcessWorkerFactory.POST_ONE_REQ_PR_MSG; 
		}
		if(configBean.getActiveMethod().equalsIgnoreCase("GET")){
			return TaskProcessWorkerFactory.DEFAULT_POST_MUL_REQ_PR_MSG;
		}else{
			return TaskProcessWorkerFactory.DEFAULT_POST_MUL_REQ_PR_MSG;
		}
	}


	private List<TaskProcessWorker> createTaskProcessWorkers(List<Set<GateWayMessage>> batches, TaskClientConfigBean configBean, String processJobKey) {
		String workerName = getWorkerName(configBean);
		List<TaskProcessWorker> workers = TaskProcessWorkerFactory.getInstance().getTaskProcessWorkers(workerName, configBean, batches, processJobKey);
		return workers;
	}
	
	public void updateTaskStatusAndHistory(Task task, String delStatus, String historyMsg){
		
	}
	
	public String getNextProcessJobKey(Long taskId){
		Long existentMaxJobId = TaskProcessJobHandler.getInstance().getMaxSynchProcessJobId();
		Long nextJobId = null;
		/* It returns 0 if there is nothing in the map */
		if(existentMaxJobId != 0){
			HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
			String query = "SELECT MAX(JB_ID) + 1  FROM TSK_PROCSS_JB";
			Map<Integer, List<Object>> rowListMap = hunterJDBCExecutor.executeQueryRowList(query, null);
			if(rowListMap != null && !rowListMap.isEmpty() && rowListMap.get(1) != null && rowListMap.get(1).isEmpty()){
				nextJobId = Long.parseLong(rowListMap.get(1).get(0) + "");
				nextJobId++;
			}else{
				nextJobId = 1L;
			}
		}else{
			nextJobId = 1L;
		}
		String key = "TASK_ID_" + taskId + "_JOB_ID_" + nextJobId + "_" + HunterUtility.getFormatedDate(new Date(), HunterConstants.DATE_FORMAT_STRING);
		logger.debug("next key : " + key); 
		return key;
	}
	
	public void setTaskForEmailProcessors(List<TaskProcessWorker> workers, Task task){
		if( HunterUtility.isCollectionNotEmpty( workers ) ){
			for(TaskProcessWorker taskProcessWorker : workers){
				if( taskProcessWorker instanceof HunterEmailProcessWorker ){
					( (HunterEmailProcessWorker)taskProcessWorker ).setTask(task);
				}else{
					break;
				}
			}
		}
	}
	
	public Map<String, Object> process(Task task,TaskClientConfigBean configBean, Set<GateWayMessage> messages, AuditInfo auditInfo) {
		
		// lock the task before doing anything else.
		task.setTaskDeliveryStatus(HunterConstants.STATUS_PENDING);
		GateWayClientHelper.getInstance().lockTask(task.getTaskId(), HunterConstants.STATUS_PENDING);
		
		logger.debug("Sending task process notification email..");
		sendTaskProcessBusinessEmail(HunterConstants.MAIL_TYPE_TASK_PROCESS_NOTIFICATION, task.getTaskId());
		
		Map<String, Object> results = new HashMap<String, Object>();
		List<String> errors = new ArrayList<>();
		
		try{
		
			int poolSize = determinePoolSize(messages, configBean);
			ExecutorService executor = Executors.newFixedThreadPool(poolSize);
			List<Set<GateWayMessage>> batches = getBatches(messages, configBean.getBatchNo());
			String processJobKey = getNextProcessJobKey(task.getTaskId()); 
			List<TaskProcessWorker> workers = createTaskProcessWorkers(batches, configBean, processJobKey);
			
			Map<String, String> jobContextParams = new HashMap<>();
			String date = HunterUtility.formatDate(new Date(), HunterConstants.DATE_FORMAT_STRING);
			jobContextParams.put(TaskProcessConstants.GEN_STATUS, HunterConstants.STATUS_SUCCESS);
			jobContextParams.put(TaskProcessConstants.TOTAL_MSGS, messages.size()+"");
			jobContextParams.put(TaskProcessConstants.CLIENT_NAME, configBean.getClientName());
			jobContextParams.put(TaskProcessConstants.GEN_DURATION, 0+"");
			jobContextParams.put(TaskProcessConstants.START_DATE, date); 
			jobContextParams.put(TaskProcessConstants.END_DATE, date);
			jobContextParams.put(TaskProcessConstants.NUMBER_OF_WORKERS, Integer.toString(batches.size())); 
			
			TaskProcessJobHandler.getInstance().createNewTaskProcessJob(task.getTaskId(), processJobKey, jobContextParams, auditInfo);
			setTaskForEmailProcessors(workers, task); 
			
			// the timer worker will keep running forever if it's submitted without workers in the map.
			if( HunterUtility.isCollectionNotEmpty(workers) ){
				for(TaskProcessWorker worker : workers){
					executor.submit(worker);
				}
				executor.shutdown();
				final Timer timer = new Timer();
				TaskScheduledUpdaterJob scheduledUpdaterJob = new TaskScheduledUpdaterJob(processJobKey, task.getTaskId(), auditInfo, timer);
				timer.scheduleAtFixedRate(scheduledUpdaterJob, 500, 200);
			}
			
			results.put("TASK_PROCESSOR_ERRORS", errors);
			results.put("TASK_PROCESSOR_STATUS", HunterConstants.STATUS_SUCCESS);
			return results;
			
		}catch(Exception e){
			e.printStackTrace();
			errors.add(e.getMessage());
			errors.add("Error occurred while submitting task for processing");
			results.put("TASK_PROCESSOR_ERRORS", errors);
			results.put("TASK_PROCESSOR_STATUS", HunterConstants.STATUS_FAILED);
			return results;
		}
		
	}
	
	
}
