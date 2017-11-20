package com.techmaster.hunter.task.process;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.constants.HunterURLConstants;
import com.techmaster.hunter.constants.TaskProcessConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.exception.HunterRunTimeException;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.TaskProcessJob;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterSessionFactory;
import com.techmaster.hunter.util.HunterUtility;
import com.techmaster.hunter.xml.XMLService;
import com.techmaster.hunter.xml.XMLServiceImpl;
import com.techmaster.hunter.xml.XMLTree;



public class TaskProcessJobHandler {
	
	private static TaskProcessJobHandler instance;
	private static Logger logger = Logger.getLogger(TaskProcessJobHandler.class);
	private static volatile Map<String, TaskProcessJob> synchProcessJobs = Collections.synchronizedMap(new HashMap<String, TaskProcessJob>());
	
	private TaskProcessJobHandler(){}
	
	static{
		if(instance == null){
			synchronized (TaskProcessJobHandler.class) {
				instance = new TaskProcessJobHandler();
			}
		}
	}
	
	public static TaskProcessJobHandler getInstance(){
		return instance;
	}
	
	public TaskProcessJob getTaskProcessJobForKey(String processJobKey){
		if(synchProcessJobs.containsKey(processJobKey)){
			return synchProcessJobs.get(processJobKey);
		}
		return null;
	}
	
	public Long getMaxSynchProcessJobId(){
		Long maxId = 0L;
		if(!synchProcessJobs.isEmpty()){
			for(Map.Entry<String, TaskProcessJob> entry : synchProcessJobs.entrySet()){
				Long jobId = entry.getValue().getJobId();
				if(maxId < jobId){
					maxId = jobId;
				}
			}
		}
		logger.debug("Next jobId : " + maxId); 
		return maxId;
	}
	
	public List<TaskProcessJob> getAllTaskProcessJobsForTaskId(Long taskId){
		logger.debug("Fetching task process jobs for task id : " + taskId); 
		String query = "FROM TaskProcessJob j WHERE j.taskId = " + taskId;
		List<TaskProcessJob> processJobs  =  HunterDaoFactory.getObject(HunterHibernateHelper.class).executeQueryForObjList(TaskProcessJob.class, query);
		if(processJobs != null && !processJobs.isEmpty()){
			for(TaskProcessJob processJob : processJobs){
				Blob docBlob = processJob.getDocBlob();
				if(docBlob != null){
					try {
						InputStream inputStream = docBlob.getBinaryStream();
						String xmlServiceStr = IOUtils.toString(inputStream, "UTF-8");
						XMLService xmlService = new XMLServiceImpl(new XMLTree(xmlServiceStr, true)); 
						processJob.setXmlService(xmlService); 
						logger.debug("Successfully set xml service to process job from clob value!"); 
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (HunterRunTimeException e) {
						e.printStackTrace();
					} catch (ParserConfigurationException e) {
						e.printStackTrace();
					}
				}
			}
		}
		logger.debug("Found task process jobs for size ( " + processJobs.size() + " )");  
		return processJobs;
	}
	
	public TaskProcessJob  saveOrUpdateProcessJob(TaskProcessJob processJob){
		Blob blob = null;
		Session session = null;
		Transaction trans = null;
		byte[] bytes = processJob.getXmlService().toString().getBytes();
		try {
			session = HunterDaoFactory.getDaoObject(HunterSessionFactory.class).getSessionFactory().openSession();
			trans = session.beginTransaction();
			
			if(bytes != null && bytes.length != 0 ){
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes); 
				blob = Hibernate.getLobCreator(session).createBlob(byteArrayInputStream, bytes.length);
				processJob.setDocBlob(blob); 
			}else{
				logger.debug("No bytes found for process jbo : " + processJob);
			}
			session.saveOrUpdate(processJob);
			trans.commit();
			logger.debug("Finished creating new task process job.");
			return processJob;
		} catch (HibernateException e) {
			e.printStackTrace();
			HunterHibernateHelper.rollBack(trans);
		}finally{
			HunterHibernateHelper.closeSession(session); 
		}
		return processJob;
	}
	
	/**
	 * 
	 * @param processJob
	 * @param jobContextParams : keys <b>MUST</b> be equal to <code><b>getNodeName()</b></code> of context elements.
	 */
	public void setProcessJobContext(TaskProcessJob processJob, Map<String, String> jobContextParams){
		logger.debug("Creating TaskProcessJob context elements : " + HunterUtility.stringifyMap(jobContextParams)); 
		XMLService xmlService = processJob.getXmlService();
		NodeList context = xmlService.getElementsByTagName("context"); 
		if(jobContextParams != null && !jobContextParams.isEmpty()){
			for(Map.Entry<String, String> entry : jobContextParams.entrySet()){
				String key = entry.getKey();
				String value = entry.getValue();
				NodeList contexts = context.item(0).getChildNodes();
				for(int i=0; i<contexts.getLength();i++){
					Node contextEle = contexts.item(i);
					String name = contextEle.getNodeName();
					if(name.equals(key)){
						logger.debug("Name : " + name + " , Value : " + value); 
						contextEle.setTextContent(value);
					}
				}
			}
			logger.debug("Finished setting context to Task Process Job"); 
		}
		processJob.setXmlService(xmlService); 
	}
	
	public TaskProcessJob createNewTaskProcessJob(Long taskId, String processJobKey, Map<String, String> jobContextParams,AuditInfo auditInfo){
		TaskProcessJob processJob = new TaskProcessJob();
		processJob.setTaskId(taskId);
		XMLService xmlService = HunterCacheUtil.getInstance().getXMLService(HunterConstants.TASK_PROCESS_JOBS_TEMPLATE);
		processJob.setXmlService(xmlService);
		setProcessJobContext(processJob,jobContextParams);
		processJob.setAuditInfo(auditInfo);
		processJob = saveOrUpdateProcessJob(processJob);
		if( processJobKey != null )
			synchProcessJobs.put(processJobKey, processJob);
		else{
			processJob.setDocBlob(HunterUtility.getStringBlob(processJob.getXmlService().toString()));
			saveOrUpdateProcessJob(processJob);
		}
		return processJob;
	}
	
	public void setWorkerXMLService (TaskProcessJob processJob, String workerName, Map<String, String> values){
		
		XMLService xmlService = processJob.getXmlService();
		
		Element worker = xmlService.createNewElemet("worker");
		worker.setAttribute("workerName", workerName);
		
		Element messageIds = xmlService.createNewElemet("messageIds");
		messageIds.setTextContent(values.get("messageIds")); 
		worker.appendChild(messageIds);
		
		Element response = xmlService.createNewElemet("response");
		
		Element responseCode = xmlService.createNewElemet("code");
		responseCode.setTextContent(values.get("responseCode")); 
		response.appendChild(responseCode);
		
		
		Element responseText = xmlService.createNewElemet("text");
		responseText.setTextContent(values.get("responseText")); 
		response.appendChild(responseText);
		
		worker.appendChild(response);
		
		Element errors = xmlService.createNewElemet("errors");
		
		Element errorsType = xmlService.createNewElemet("type");
		errorsType.setTextContent(values.get("errorsType")); 
		errors.appendChild(errorsType);
		
		Element errorsText = xmlService.createNewElemet("text");
		errorsText.setTextContent(values.get("responseText")); 
		errors.appendChild(errorsText);
		
		worker.appendChild(errors);

		Element duration = xmlService.createNewElemet("duration");
		duration.setTextContent(values.get(TaskProcessConstants.DURATION)); 
		worker.appendChild(duration);
		
		Element connStatus = xmlService.createNewElemet("connStatus");
		connStatus.setTextContent(values.get(TaskProcessConstants.CONN_STATUS)); 
		worker.appendChild(connStatus);
		
		Element startPoint = xmlService.createNewElemet("startPoint");
		startPoint.setTextContent(values.get(TaskProcessConstants.START_POINT)); 
		worker.appendChild(startPoint);
		
		Element endPoint = xmlService.createNewElemet("endPoint");
		endPoint.setTextContent(values.get(TaskProcessConstants.END_POINT)); 
		worker.appendChild(endPoint);
		
		Element workerStatus = xmlService.createNewElemet("workerStatus");
		workerStatus.setTextContent(values.get(TaskProcessConstants.WORKER_STATUS)); 
		worker.appendChild(workerStatus);
		
		Element workerMsgCount = xmlService.createNewElemet("workerMsgCount");
		workerMsgCount.setTextContent(values.get(TaskProcessConstants.WORKER_MSG_COUNT)); 
		worker.appendChild(workerMsgCount);
		
		xmlService.addElement("workers", worker, 0); 
		processJob.setXmlService(xmlService); 
		
	}
	
	
	public TaskProcessJob addWorkerToProcessJob(TaskProcessJob processJob, boolean saveOrUpdate, String workerName, Map<String, String> values){
		setWorkerXMLService(processJob, workerName, values); 
		if( saveOrUpdate )
			saveOrUpdateProcessJob(processJob);
		return processJob;
	}
	
	public synchronized void addTaskProcessWorker(String workerName, String processJobKey, Map<String, String> values){
		TaskProcessJob processJob = getTaskProcessJobForKey(processJobKey);
		if(processJob  == null){
			throw new HunterRunTimeException("Task process cannot be found with the key : " + processJobKey);
		}
		addWorkerToProcessJob(processJob, true, workerName, values);
	}
	
	public static void main(String[] args) {
		
		Map<String, String> values = new HashMap<>();
		values.put("workerName", "POOL-1-24");
		values.put("messageIds", "456,2569,25,2546,26,12545");
		values.put("responseCode", "200"); 
		values.put("responseText", "There was an exception while making a connection to the client server"); 
		values.put("errorsType", "Application Error");
		values.put("errorsText", "There was an exception while making a connection to the client server");
		
		XMLService xmlService = HunterUtility.getXMLServiceForFileLocation(HunterURLConstants.TASK_PROCESS_JOBS_TEMPLATE);
		
		Element worker = xmlService.createNewElemet("worker");
		worker.setAttribute("workerName", values.get("workerName"));
		
		Element messageIds = xmlService.createNewElemet("messageIds");
		messageIds.setTextContent(values.get("messageIds")); 
		
		worker.appendChild(messageIds);
		
		Element response = xmlService.createNewElemet("response");
		
		Element responseCode = xmlService.createNewElemet("code");
		responseCode.setTextContent(values.get("responseCode")); 
		response.appendChild(responseCode);
		
		
		Element responseText = xmlService.createNewElemet("text");
		responseText.setTextContent(values.get("responseText")); 
		response.appendChild(responseText);
		
		worker.appendChild(response);
		
		Element errors = xmlService.createNewElemet("errors");
		
		Element errorsType = xmlService.createNewElemet("type");
		errorsType.setTextContent(values.get("errorsType")); 
		errors.appendChild(errorsType);
		
		Element errorsText = xmlService.createNewElemet("text");
		errorsText.setTextContent(values.get("responseText")); 
		errors.appendChild(errorsText);
		
		worker.appendChild(errors);
		
		xmlService.addElement("workers", worker, 10); 
		logger.debug(xmlService);
	}

	public void removeProcessJob(String processJobKey) {
		if(synchProcessJobs.containsKey(processJobKey)){
			synchProcessJobs.remove(processJobKey);
		}else{
			logger.debug("synchProcessJobs does not have process job with key : " + processJobKey); 
		}
	}

}
