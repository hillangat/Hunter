package com.techmaster.hunter.obj.converters;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.json.TaskProcessJobJson;
import com.techmaster.hunter.json.TaskProcessWorkerJson;
import com.techmaster.hunter.obj.beans.TaskProcessJob;
import com.techmaster.hunter.task.process.TaskProcessJobHandler;
import com.techmaster.hunter.util.HunterUtility;
import com.techmaster.hunter.xml.XMLService;

public class TaskProcessJobConverter {
	
	private static TaskProcessJobConverter instance;
	private static Logger logger = Logger.getLogger(TaskProcessJobConverter.class);
	
	static{
		if(instance == null){
			synchronized (TaskProcessJobConverter.class) {
				instance = new TaskProcessJobConverter();
			}
		}
	}
	
	public static TaskProcessJobConverter getInstance(){
		return instance;
	}
	
	public void serializeTaskProcessJsons(List<TaskProcessJobJson> processJobJsons, Long taskId) throws IOException {
		
		logger.debug("Serializing task process job results for task id : " + taskId + "...");
		
		File file = File.createTempFile(HunterConstants.HUNTER_PRCESS_SERIAL_PREF_KEY + taskId, ".datum");
		if( !file.exists() )
			file.mkdir();
		
		String srlzdTskPrcssJbObjsFilLoc = file.getAbsolutePath();
		logger.debug("Location where serialized data is stored for this task ("+ taskId +") > " + srlzdTskPrcssJbObjsFilLoc); 
		String query = "UPDATE TASK t SET t.SRLZD_PRCSS_RSLTS_FL_LOC = ? WHERE t.TSK_ID = ?";
		List<Object> values = new ArrayList<>();
		values.add(srlzdTskPrcssJbObjsFilLoc);
		values.add(taskId);
		
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject( HunterJDBCExecutor.class );
		int rowsAffected  = hunterJDBCExecutor.executeUpdate(query, values);
		logger.debug("Updating task file location. Row affected = " + rowsAffected); 
		
        FileOutputStream fos = null;
		ObjectOutputStream objOutputStream = null;
		
		try {
			fos = new FileOutputStream( file );
			objOutputStream = new ObjectOutputStream(fos);
			for (Object processJobJson : processJobJsons) {
			    objOutputStream.writeObject(processJobJson);
			    objOutputStream.reset();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			objOutputStream.close();
	        fos.close(); 
		}
		
        logger.debug("Successfully serialized the objects. Number of objects = " + processJobJsons.size()); 
    }
	
	public List<TaskProcessJobJson> deSerializeTaskProcessJsons( Long taskId ) throws ClassNotFoundException, IOException {
		
		List<TaskProcessJobJson> taskProcessJobJsons = new ArrayList<TaskProcessJobJson> ();
		
		
		/* This whole process is disabled on purpose */
		if( taskProcessJobJsons.size() != 0 ){
			return taskProcessJobJsons;
		}
        
		String query = "SELECT t.SRLZD_PRCSS_RSLTS_FL_LOC FROM TASK t WHERE t.TSK_ID = ?";
		List<Object> values = new ArrayList<>();
		values.add(taskId);
		
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject( HunterJDBCExecutor.class );
		String srlzdTskPrcssJbObjsFilLoc = HunterUtility.getStringOrNullOfObj( hunterJDBCExecutor.executeQueryForOneReturn(query, values) );
		
		if( srlzdTskPrcssJbObjsFilLoc == null ){
			logger.debug("No task process file location set for task. Returning an empty arraylist..."); 
			return taskProcessJobJsons;
		}
		
        FileInputStream fis = new FileInputStream( srlzdTskPrcssJbObjsFilLoc );
        ObjectInputStream obj = new ObjectInputStream(fis);
        
        
        try {
            while ( fis.available() > 1 ) {
            	TaskProcessJobJson processJobJson = (TaskProcessJobJson) obj.readObject();
            	taskProcessJobJsons.add(processJobJson);
            }
            logger.debug("Successfully deserialized the file. Number of objects = " + taskProcessJobJsons.size()); 
        } catch (EOFException ex) {
            ex.printStackTrace();
        }finally{
        	obj.close();
            fis.close();
            obj = null;
            fis = null;
        }
        return taskProcessJobJsons;
    }
	
	
	public List<TaskProcessJobJson> getProcessJobJsonsForTask(Long taskId){
		
		logger.debug("Starting to extract proces job jsons for task id : " + taskId); 
		
		List<TaskProcessJob> processJobs = TaskProcessJobHandler.getInstance().getAllTaskProcessJobsForTaskId(taskId);
		List<TaskProcessJobJson> taskProcessJobJsons = getTaskProcessJobJsons(processJobs);
		return taskProcessJobJsons;
		
		/*
		
		try {
			List<TaskProcessJobJson> serializedProcessJobJsons = deSerializeTaskProcessJsons(taskId);
			
			if( !HunterUtility.isCollectionNullOrEmpty( serializedProcessJobJsons ) ){
				return serializedProcessJobJsons;
			}
			
			processJobs = TaskProcessJobHandler.getInstance().getAllTaskProcessJobsForTaskId(taskId);
			taskProcessJobJsons = getTaskProcessJobJsons(processJobs);
			
			logger.debug("Submitting proces job jsons for serialization..."); 
			serializeTaskProcessJsons(taskProcessJobJsons, taskId);
			
		} catch (ClassNotFoundException | IOException e) {
			logger.debug("Exception while submitting process job jsons for serialization. Message = " + e.getMessage());
			e.printStackTrace();
			processJobs = TaskProcessJobHandler.getInstance().getAllTaskProcessJobsForTaskId(taskId);
			taskProcessJobJsons = getTaskProcessJobJsons(processJobs);
		}
		
		return taskProcessJobJsons;
		
		*/
		
	}
	
	public List<TaskProcessJobJson> getTaskProcessJobJsons(List<TaskProcessJob> processJobs){
		List<TaskProcessJobJson> processJsonJsons = new ArrayList<>();
		if(processJobs != null && !processJobs.isEmpty()){
			for(TaskProcessJob processJob : processJobs){
				XMLService xmlService = processJob.getXmlService();
				TaskProcessJobJson processJobJson = new TaskProcessJobJson();
				processJobJson.setClientName(xmlService.getTextValue("//context/clientName"));
				processJobJson.setEndDate(xmlService.getTextValue("//context/endDate")); 
				processJobJson.setGenDuration(HunterUtility.getLongFromObject(xmlService.getTextValue("//context/genDuration")));  
				processJobJson.setNumberOfWorkers(xmlService.getTextValue("//context/numberOfWorkers")); 
				processJobJson.setProcessJobId(processJob.getJobId());  
				processJobJson.setStartDate(xmlService.getTextValue("//context/startDate"));
				String msgCnt = xmlService.getTextValue("//context/totalMsgs");
				processJobJson.setTotalMsgs(Integer.parseInt(msgCnt)); 
				List<TaskProcessWorkerJson> workers = getProcessJobWorkerJson(processJob);
				logger.debug("Number of workers found for this process job : " + workers.size()); 
				processJobJson.setWorkerJsons(workers);
				processJsonJsons.add(processJobJson);
			}
		}
		return processJsonJsons;
	}
	
	public int getWorkerCount(TaskProcessJob processJob){
		NodeList nodeList = processJob.getXmlService().getElementsByTagName("worker");
		if(nodeList == null || nodeList.getLength() == 0) return 0;
		int workerCount = nodeList.getLength();
		logger.debug("Number of workers for process job( "+ processJob.getJobId() +" )  = " + workerCount); 
		return workerCount;
	}
	
	public List<TaskProcessWorkerJson> getProcessJobWorkerJson(TaskProcessJob processJob){
		logger.debug("Retrieving task process job jsons..."); 
		List<TaskProcessWorkerJson> processJsons = new ArrayList<TaskProcessWorkerJson>();
		if(processJob != null ){
			int workerSize = getWorkerCount(processJob);
			for(int i=0; i<workerSize;i++){
				TaskProcessWorkerJson jobJson = new TaskProcessWorkerJson();
				String workerPath = "//worker["+ (1+i) +"]/";
				XMLService xmlService = processJob.getXmlService();
				jobJson.setProcessJobId(processJob.getJobId());
				jobJson.setCnnStatus(xmlService.getTextValue(workerPath + "connStatus"));
				jobJson.setDuration(xmlService.getTextValue(workerPath + "duration"));
				jobJson.setErrorType(xmlService.getTextValue(workerPath + "errors/type"));
				jobJson.setErrorText(xmlService.getTextValue(workerPath + "errors/text"));
				jobJson.setMsgCount(Integer.parseInt(xmlService.getTextValue(workerPath + "workerMsgCount"))); 
				jobJson.setWorkerName(xmlService.getTextValue(workerPath + "@workerName"));
				jobJson.setResponseCode(xmlService.getTextValue(workerPath + "response/code"));
				jobJson.setResponseText(xmlService.getTextValue(workerPath + "response/text"));
				jobJson.setWorkerStatus(xmlService.getTextValue(workerPath + "workerStatus"));
				jobJson.setMsgIds(xmlService.getTextValue(workerPath + "messageIds"));
				jobJson.setWorkerNo(i+1); 
				jobJson.setTaskId(processJob.getTaskId());
				processJsons.add(jobJson);
			}
		}
		logger.debug("Successfully retrieved ( " + processJsons.size() + " task process job jsons)");  
		return processJsons;
	}
	
	

}
