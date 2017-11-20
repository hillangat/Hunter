package com.techmaster.hunter.task.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.constants.TaskProcessConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.TaskDao;
import com.techmaster.hunter.exception.HunterRunTimeException;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.TaskProcessJob;
import com.techmaster.hunter.util.HunterUtility;
import com.techmaster.hunter.xml.XMLService;

public class TaskScheduledUpdaterJob extends TimerTask {

	private String processJobKey;
	private Long taskId;
	private AuditInfo auditInfo;
	private Timer timer;

	private Logger logger = Logger.getLogger(getClass());

	public TaskScheduledUpdaterJob(String processJobKey, Long taskId, AuditInfo auditInfo, Timer timer) {
		super();
		this.processJobKey = processJobKey;
		this.taskId = taskId;
		this.auditInfo = auditInfo;
		this.timer = timer;
	}

	public void run() {
		int numberOfWorkers = getNumberOfWorkers();
		logger.debug("Configured number of workers : " + numberOfWorkers);
		int workerCount = getWorkerCount();
		logger.debug("Number of workers already processed: " + workerCount);
		/* If all workers have been reported */
		if (workerCount >= numberOfWorkers) {
			List<String> workerStatuses = getWorkerStatuses();
			String taskStatus = HunterConstants.STATUS_FAILED;
			int failedSts = 0;
			int successSts = 0;
			if (workerStatuses != null && !workerStatuses.isEmpty()) {
				for (String workerStatus : workerStatuses) {
					failedSts = workerStatus.equals(HunterConstants.STATUS_FAILED) ? (failedSts + 1) : failedSts;
					successSts = workerStatus.equals(HunterConstants.STATUS_SUCCESS) ? (successSts + 1) : successSts;
				}
			}
			if (failedSts > 0 && successSts > 0) {
				taskStatus = HunterConstants.STATUS_PARTIAL;
			} else if (failedSts == 0 && successSts > 0) {
				taskStatus = HunterConstants.STATUS_PROCESSED;
			} else if (failedSts > 0 && successSts == 0) {
				taskStatus = HunterConstants.STATUS_FAILED;
			}
			logger.debug("Updating task delivery status : " + taskStatus);
			TaskDao taskDao = HunterDaoFactory.getObject(TaskDao.class);
			taskDao.updateTaskStatus(taskId, HunterConstants.STATUS_PROCESSED, auditInfo.getLastUpdatedBy());
			taskDao.updateTaskDelStatus(taskId, taskStatus, auditInfo.getLastUpdatedBy());
			logger.debug("Canceling timer...");
			timer.cancel();
			logger.debug("Purge value :" + timer.purge());
			updateGeneralStatus(taskStatus);
			updateProcessJobGeneralDuration();
			commitProcessJobChanges();
			updateTaskMessage();
			logger.debug("Finished updating process Job.Removing task from task process handler map...");
			TaskProcessJobHandler.getInstance().removeProcessJob(processJobKey);
		} else {
			logger.debug("Processing not completed yet. numberOfWorkers(" + numberOfWorkers + "),workerCount(" + workerCount + "). Will keep checking...");
		}

	}

	public void updateTaskMessage() {
		logger.debug("Updating task message...");
		String query = "SELECT t.TSK_MSG_TYP FROM TASK t WHERE t.TSK_ID = ?";
		List<Object> values = new ArrayList<>();
		values.add(taskId);
		logger.debug("Executing query : " + query); 
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
		Map<Integer, List<Object>> rowMapList = hunterJDBCExecutor.executeQueryRowList(query, values);
		String msgType = null;
		if (rowMapList != null && !rowMapList.isEmpty()) {
			msgType = rowMapList.get(1) != null && !rowMapList.get(1).isEmpty() ? rowMapList.get(1).get(0).toString(): null;
		} else {
			logger.warn("Cannot find message type for task ( " + taskId + " )");
			return;
		}
		String tableName = null;
		msgType = HunterUtility.getFirstUpperCase(msgType.trim());
		switch (msgType) {
		case HunterConstants.MESSAGE_TYPE_TEXT:
			tableName = "TXT_MSG";
			break;
		case HunterConstants.MESSAGE_TYPE_EMAIL:
			tableName = "EMAIL_MSG";
			break;
		default:
			logger.debug("Message type not defined yet( "+ msgType +" ). Throwing exception...");
			throw new IllegalArgumentException( "Table name(" + tableName + ") could not be found!!" );
		}
		XMLService xmlService  = TaskProcessJobHandler.getInstance().getTaskProcessJobForKey(processJobKey).getXmlService();
		String sendDate = xmlService.getTextValue("//results/context/startDate");  
		String totalMsgs = xmlService.getTextValue("//results/context/totalMsgs");
		if(sendDate == null){
			sendDate = HunterUtility.formatDate(new Date(), HunterConstants.HUNTER_DATE_FORMAT_SECS);
		}
		query = "UPDATE " + tableName + " SET MSG_SEND_DATE = to_date(?,'yyyy-MM-dd HH24:MI:ss'), MSG_LIF_STS = ?, ACTL_RCVRS = ?  WHERE MSG_ID = ?";
		values.clear();
		values.add(sendDate);
		values.add(HunterConstants.STATUS_PROCESSED);
		values.add(totalMsgs == null ? 0 : totalMsgs);
		values.add(taskId);
		logger.debug("Executing query : " + query);
		int rows = hunterJDBCExecutor.executeUpdate(query, values); 
		logger.debug("Successfully updated message. Rows updated = "+ rows); 
	}

	public void commitProcessJobChanges() {
		logger.debug("Committing changes to process job handler...");
		TaskProcessJob processJob = TaskProcessJobHandler.getInstance().getTaskProcessJobForKey(processJobKey);
		TaskProcessJobHandler.getInstance().saveOrUpdateProcessJob(processJob);
	}

	public void updateGeneralStatus(String genStatus) {
		logger.debug("Updating general status to : " + genStatus + " ...");
		TaskProcessJob processJob = TaskProcessJobHandler.getInstance().getTaskProcessJobForKey(processJobKey);
		Node genStatusNode = processJob.getXmlService().getNodeListForPathUsingJavax("//context/genStatus").item(0);
		genStatusNode.setTextContent(genStatus);
	}

	public void updateProcessJobGeneralDuration() {
		logger.debug("updating process job general duration");
		TaskProcessJob processJob = TaskProcessJobHandler.getInstance().getTaskProcessJobForKey(processJobKey);
		if (processJob != null) {
			NodeList workers = processJob.getXmlService().getElementsByTagName("worker");
			Long lowestStartPoint = 0L;
			Long highestEndPoint = 0L;
			if (workers != null && workers.getLength() != 0) {
				for (int i = 0; i < workers.getLength(); i++) {
					Node worker = workers.item(i);
					NodeList workerConfigs = worker.getChildNodes();
					if (workerConfigs != null && workerConfigs.getLength() != 0) {
						for (int j = 0; j < workerConfigs.getLength(); j++) {
							Node config = workerConfigs.item(j);
							String nodeName = config.getNodeName();
							String value = config.getTextContent();
							if (TaskProcessConstants.START_POINT.equals(nodeName)) {
								Long longVal = Long.valueOf(value);
								if (lowestStartPoint == 0 || lowestStartPoint < longVal) {
									lowestStartPoint = longVal;
								}
							} else if (TaskProcessConstants.END_POINT.equals(nodeName)) {
								Long longVal = Long.valueOf(value);
								if (highestEndPoint == 0 || highestEndPoint > longVal) {
									highestEndPoint = longVal;
								}
							}
						}
					}
				}
				Long genDuration = lowestStartPoint - highestEndPoint;
				logger.debug("General duration found : " + genDuration + ". Updating general duration for process job.");
				Node node = processJob.getXmlService().getElementsByTagName(TaskProcessConstants.GEN_DURATION).item(0);
				node.setTextContent(Long.toString(genDuration));
			} else {
				logger.debug("No workers found for the process job : " + processJobKey);
			}
		} else {
			logger.debug("No process job found for the key : " + processJobKey);
		}
	}

	public int getNumberOfWorkers() {
		TaskProcessJob processJob = TaskProcessJobHandler.getInstance().getTaskProcessJobForKey(processJobKey);
		NodeList nodeList = processJob.getXmlService().getElementsByTagName("numberOfWorkers");
		if (nodeList == null || nodeList.getLength() == 0) {
			return 0;
		} else {
			String numberStr = nodeList.item(0).getTextContent();
			logger.debug("Number of workers :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: " + numberStr);
			if (HunterUtility.isNumeric(numberStr)) {
				return Integer.parseInt(numberStr);
			} else {
				throw new HunterRunTimeException("numberOfWorkers is not numeric ( " + numberStr + " )");
			}
		}
	}

	public int getWorkerCount() {
		TaskProcessJob processJob = TaskProcessJobHandler.getInstance().getTaskProcessJobForKey(processJobKey);
		NodeList nodeList = processJob.getXmlService().getElementsByTagName("worker");
		if (nodeList == null || nodeList.getLength() == 0)
			return 0;
		int workerCount = nodeList.getLength();
		logger.debug("Worker count :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: "+ workerCount);
		return workerCount;
	}

	public List<String> getWorkerStatuses() {
		TaskProcessJob processJob = TaskProcessJobHandler.getInstance().getTaskProcessJobForKey(processJobKey);
		List<String> statuses = new ArrayList<String>();
		NodeList nodeList = processJob.getXmlService().getElementsByTagName(TaskProcessConstants.WORKER_STATUS);
		if (nodeList != null && nodeList.getLength() != 0) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeName().equals(TaskProcessConstants.WORKER_STATUS)) {
					statuses.add(node.getTextContent());
				}
			}
		}
		logger.debug("List of worker statuses : "+ HunterUtility.getCommaDelimitedStrings(statuses)); 
		return statuses;
	}

}
