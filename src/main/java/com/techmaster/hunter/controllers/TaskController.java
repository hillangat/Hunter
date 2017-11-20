package com.techmaster.hunter.controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.constants.UIMessageConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.ReceiverGroupDao;
import com.techmaster.hunter.dao.types.ServiceProviderDao;
import com.techmaster.hunter.dao.types.TaskDao;
import com.techmaster.hunter.dao.types.TaskHistoryDao;
import com.techmaster.hunter.enums.HunterUserRolesEnums;
import com.techmaster.hunter.enums.TaskHistoryEventEnum;
import com.techmaster.hunter.gateway.beans.GateWayClientService;
import com.techmaster.hunter.json.ReceiverGroupJson;
import com.techmaster.hunter.json.TaskProcessJobJson;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.HunterJacksonMapper;
import com.techmaster.hunter.obj.beans.HunterSocialMedia;
import com.techmaster.hunter.obj.beans.Message;
import com.techmaster.hunter.obj.beans.ServiceProvider;
import com.techmaster.hunter.obj.beans.SocialMessage;
import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.obj.beans.TaskHistory;
import com.techmaster.hunter.obj.beans.TextMessage;
import com.techmaster.hunter.obj.converters.TaskConverter;
import com.techmaster.hunter.obj.converters.TaskProcessJobConverter;
import com.techmaster.hunter.task.TaskManager;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterLogFactory;
import com.techmaster.hunter.util.HunterUtility;

@Controller
@RequestMapping(value = "/task")
public class TaskController extends HunterBaseController{

	@Autowired private TaskDao taskDao;
	@Autowired private HunterJacksonMapper hunterJacksonMapper;
	@Autowired private TaskManager taskManager;
	@Autowired private TaskHistoryDao taskHistoryDao;
	@Autowired private ReceiverGroupDao receiverGroupDao;
	
	private static final Logger logger = HunterLogFactory.getLog(TaskController.class);

	@RequestMapping(value = "/action/read/getTasksForClientId/{clientId}")
	@Produces("application/json")
	@Consumes("application/json")
	@ResponseBody
	public List<Task> getTaskForClientId(@PathVariable Long clientId) {
		
		String baseDir = System.getProperty("catalina.base");
		logger.debug(baseDir); 
		List<Task> tasks = HunterDaoFactory.getObject(HunterHibernateHelper.class).getAllEntities(Task.class);
		logger.debug("Returning Tasks for client >> " + tasks);
		return tasks;
	}
	
	@RequestMapping(value = "/action/task/clone")
	@Produces("application/json")
	@Consumes("application/json")
	public @ResponseBody String cloneTask(HttpServletRequest request) {
		
		JSONObject messages = new JSONObject();
		
		try {
			
			String requestBoby = HunterUtility.getRequestBodyAsString(request);
			JSONObject requestBodyJs = new JSONObject(requestBoby);

			Long taskId = requestBodyJs.getLong("taskId");
			String newUserName = HunterUtility.getStringOrNullFromJSONObj(requestBodyJs, "newOwner");
			String taskName = HunterUtility.getStringOrNullFromJSONObj(requestBodyJs, "taskName");; 
			String taskDescription = HunterUtility.getStringOrNullFromJSONObj(requestBodyJs, "taskDescription");
			logger.debug("Cloning task with id ( " + requestBodyJs.getLong("taskId") + " )");   
			TaskHistory taskHistory = taskManager.getNewTaskHistoryForEventName(taskId, TaskHistoryEventEnum.CLONE.getEventName(), getUserName());
			
			// check if that name is already used.
			String taskNamesStr = HunterDaoFactory.getObject(TaskDao.class).getCmmSprtdTskNamsFrUsrNam(newUserName);  
			if(taskNamesStr != null){
				String[] taskNames = taskNamesStr.split(","); 
				for(String tskName : taskNames){
					if(tskName.equals(taskName) || tskName.equals(taskName  + "_" + taskId)){
						logger.debug("Client already has task with this task name."); 
						String errorMsg = "Client already has a task with this task name.";
						messages = HunterUtility.setJSONObjectForFailure(messages, errorMsg);
						messages.put("duplicate", "true");
						taskManager.setTaskHistoryStatusAndMessage(taskHistory,HunterConstants.STATUS_FAILED,"Failed to clone task." + errorMsg);  
						taskHistoryDao.insertTaskHistory(taskHistory); 
						return messages.toString();
					}
				}
			}
			
			Task task = taskDao.getTaskById(taskId);
			AuditInfo auditInfo = HunterUtility.getAuditInfoFromRequestForNow(null, getUserName()); 
			
			Task copy = taskManager.cloneTask(task, newUserName, taskName, taskDescription, auditInfo);
			
			Message message = copy.getTaskMessage();
			SocialMessage socialMessage = null;
			HunterSocialMedia socialMedia = null;
			
			if( message instanceof SocialMessage ){
				socialMessage = ((SocialMessage)copy.getTaskMessage());
				socialMedia = socialMessage.getSocialMedia();
				socialMessage.setSocialMedia(null); 
			}
			
			logger.debug(copy); 
			taskDao.insertTask(copy);
			
			if( copy.getTskMsgType().equals(HunterConstants.TASK_TYPE_SOCIAL) ){
				logger.debug("Resetting social media id for social message..."); 
				socialMedia.setMediaId(copy.getTaskId()); 
				socialMessage.setSocialMedia(socialMedia); 
				task.setTaskMessage(socialMessage);
				taskDao.update(copy); 
			}
			
			taskManager.setTaskHistoryStatusAndMessage(taskHistory,HunterConstants.STATUS_SUCCESS,"Successfully cloned task. New task ( " + copy.getTaskId() + " ) for new user ( " + newUserName + " )");  
			taskHistoryDao.insertTaskHistory(taskHistory);
			
			logger.debug("Finished cloning task!!"); 
			return HunterUtility.setJSONObjectForSuccess(messages, "Successfully cloned task!").toString();
			
		} catch (JSONException e) {
			e.printStackTrace();
			return HunterUtility.setJSONObjectForFailure(messages, "Application error occurred!").toString();
		} catch (IOException e) {
			e.printStackTrace();
			return HunterUtility.setJSONObjectForFailure(messages, "Application error occurred!").toString();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return HunterUtility.setJSONObjectForFailure(messages, "Application error occurred!").toString();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return HunterUtility.setJSONObjectForFailure(messages, "Application error occurred!").toString();
		}
	}

	@RequestMapping(value = "/action/task/changeStatus")
	@Produces("application/json")
	@Consumes("application/json")
	@ResponseBody
	public String changeTaskStatus(HttpServletRequest request) {
		
		String requeBody = null;
		JSONObject results = new JSONObject();
		Long taskId = null;
		String toStatus = null;
		

		try {
			requeBody = HunterUtility.getRequestBodyAsString(request);
			JSONObject requestData = new JSONObject(requeBody);
			String taskIdStr = HunterUtility.getStringOrNullFromJSONObj(requestData, "taskId");
			taskId = taskIdStr == null ? null : HunterUtility.getLongFromObject(taskIdStr);
			toStatus = HunterUtility.getStringOrNullFromJSONObj(requestData,"toStatus");
		} catch (IOException e) {
			e.printStackTrace();
			results.put(HunterConstants.STATUS_STRING,HunterConstants.STATUS_FAILED);
			results.put(HunterConstants.MESSAGE_STRING, e.getMessage());
			return results.toString();
		}

		List<String> validationErrors = taskManager.validateStatusChange(taskId, toStatus,getUserName());
		String userName = getUserName();
		logger.debug("Logged in user : " + userName);
		if (userName == null) {
			validationErrors.add("Login is needed to change task status!");
		}
		
		TaskHistory taskHistory = taskManager.getNewTaskHistoryForEventName(taskId, TaskHistoryEventEnum.STATUS_CHANGE.getEventName(), userName);

		if (validationErrors.isEmpty()) {
			taskDao.updateTaskStatus(taskId, toStatus, userName);
			taskManager.setTaskHistoryStatusAndMessage(taskHistory,HunterConstants.STATUS_SUCCESS,"Successfully changed task status to : " + toStatus);
			taskHistoryDao.insertTaskHistory(taskHistory); 
			results.put(HunterConstants.STATUS_STRING,HunterConstants.STATUS_SUCCESS);
			results.put(HunterConstants.MESSAGE_STRING,"Task status approved successfully");
			return results.toString();
		}else{
			taskManager.setTaskHistoryStatusAndMessage(taskHistory,HunterConstants.STATUS_FAILED,"Failed to change task status to : " + toStatus + ". " + HunterUtility.stringifyList(validationErrors)); 
			taskHistoryDao.insertTaskHistory(taskHistory); 
		}

		StringBuilder errBuilder = new StringBuilder();
		for (String error : validationErrors) {
			errBuilder.append(error).append(",");
		}
		String errors = errBuilder.toString();
		errors = errors.substring(0, errors.length() - 1);
		logger.warn("Task with taskId ( " + taskId+ " ) cannot be approved >> " + errors);
		results.put(HunterConstants.STATUS_STRING,HunterConstants.STATUS_FAILED);
		results.put(HunterConstants.MESSAGE_STRING, errors);
		logger.debug("returned js >> " + results);

		return results.toString();
	}

	@RequestMapping(value = "/action/create/createTaskForClientId", method = RequestMethod.POST)
	@Produces("application/json")
	@Consumes("application/json")
	@ResponseBody
	public Task createTaskForCilentId(HttpServletRequest request) {

		String bodyString = null;
		Task task = new Task();
		
		String userName = getUserName();
		TaskHistory taskHistory = taskManager.getNewTaskHistoryForEventName(null, TaskHistoryEventEnum.CREATE.getEventName(), userName);

		try {

			bodyString = HunterUtility.getRequestBodyAsString(request);
			task = new TaskConverter(bodyString).convertBasic();
			task.setLastUpdate(new Date()); 
			task.setUpdatedBy( getUserName() );
			task.setTaskDateline(new Date());

		} catch (IOException e) {
			e.printStackTrace();
		}
		String parameters = HunterUtility.getParamNamesAsStringsFrmRqst(request);
		logger.debug("Request parameters >> " + parameters);
		taskDao.insertTask(task);
		logger.debug("Returning new task >> " + task);
		
		taskHistory.setTaskId(task.getTaskId()); 
		taskManager.setTaskHistoryStatusAndMessage(taskHistory, HunterConstants.STATUS_SUCCESS, "Successfully created task."); 
		taskHistoryDao.insertTaskHistory(taskHistory); 
		
		return task;
	}
	
	@RequestMapping(value = "/action/create/createTaskForCilentIdNew", method = RequestMethod.POST)
	@Produces("application/json")
	@Consumes("application/json")
	@ResponseBody
	public String createTaskForCilentIdNew(HttpServletRequest request) {

		String bodyString = null;
		Task task = new Task();
		
		String userName = getUserName();
		TaskHistory taskHistory = taskManager.getNewTaskHistoryForEventName(null, TaskHistoryEventEnum.CREATE.getEventName(), userName);

		try {

			bodyString = HunterUtility.getRequestBodyAsString(request);
			task = new TaskConverter(bodyString).convertBasic();
			task.setLastUpdate(new Date()); 
			task.setCreatedBy( task.getCreatedBy() != null ? task.getCreatedBy() : getUserName() );
			task.setUpdatedBy( getUserName() );
			task.setTaskDateline(new Date());
			
			if( task.getTaskId() != 0 ){
				Task persitentTask = taskDao.getTaskById(task.getTaskId());
				persitentTask.setTaskName(task.getTaskName());
				persitentTask.setDescription(task.getDescription());
				persitentTask.setTaskType(task.getTaskType());
				persitentTask.setTskMsgType(task.getTskMsgType()); 
				persitentTask.setGateWayClient(task.getGateWayClient());
				persitentTask.setTaskObjective(task.getTaskObjective()); 
				persitentTask.setRecurrentTask(task.isRecurrentTask()); 
				persitentTask.setTaskBudget(task.getTaskBudget()); 
				persitentTask.setDesiredReceiverCount(task.getDesiredReceiverCount()); 
				taskDao.update(persitentTask); 
				return HunterUtility.setJSONObjectForSuccess(new JSONObject(), "Successfully created task").toString();
			}
			

		} catch (IOException e) {
			e.printStackTrace();
			return HunterUtility.setJSONObjectForFailure(null, e.getMessage()).toString();
		}
		String parameters = HunterUtility.getParamNamesAsStringsFrmRqst(request);
		logger.debug("Request parameters >> " + parameters);
		taskDao.insertTask(task);
		logger.debug("Returning new task >> " + task);
		
		taskHistory.setTaskId(task.getTaskId()); 
		taskManager.setTaskHistoryStatusAndMessage(taskHistory, HunterConstants.STATUS_SUCCESS, "Successfully created task."); 
		taskHistoryDao.insertTaskHistory(taskHistory); 
		
		return HunterUtility.setJSONObjectForSuccess(new JSONObject(), "Successfully created task").toString();
	}

	@RequestMapping(value = "action/update/updateTaskForClientId", method = RequestMethod.POST)
	@Produces("application/json")
	@Consumes("application/json")
	@ResponseBody
	public Task updateTaskForClient(HttpServletRequest request) {
		String requestBody = null;
		try {
			requestBody = HunterUtility.getRequestBodyAsString(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Task task = new TaskConverter(requestBody).convert();
		String status = task.getTaskLifeStatus();
		logger.debug("Upadting task. Status of the task : " + status);
		if (status != null && !status.equals(HunterConstants.STATUS_DRAFT)&& !status.equals(HunterConstants.STATUS_REVIEW)) {
			task.setTaskApproved(true);
		} else {
			task.setTaskApproved(false);
		}
		String userName = getUserName();
		logger.debug("Logged in user : " + userName);
		if (userName != null) {
			task.setTaskApprover(userName);
		}
		
		taskDao.update(task);
		logger.debug("Returning updated task >> " + task);
		
		TaskHistory taskHistory = taskManager.getNewTaskHistoryForEventName(task.getTaskId(), TaskHistoryEventEnum.UPDATE.getEventName(), userName);
		taskManager.setTaskHistoryStatusAndMessage(taskHistory, HunterConstants.STATUS_SUCCESS, "Successfully updated task."); 
		taskHistoryDao.insertTaskHistory(taskHistory);
		
		return task;
	}

	@RequestMapping(value = "/action/destroy/destroyTaskForClientId", method = RequestMethod.POST)
	@Produces("application/json")
	@Consumes("application/json")
	@ResponseBody
	public Task destroyTaskForClient(HttpServletRequest request) {
		String requestBody = null;
		try {
			requestBody = HunterUtility.getRequestBodyAsString(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Long taskId = new JSONObject(requestBody).getLong("taskId");
		Task task = taskDao.getTaskById(taskId);
		taskDao.deleteTask(task);
		
		String userName = getUserName();
		TaskHistory taskHistory = taskManager.getNewTaskHistoryForEventName(task.getTaskId(), TaskHistoryEventEnum.DELETE.getEventName(), userName);
		taskManager.setTaskHistoryStatusAndMessage(taskHistory, HunterConstants.STATUS_SUCCESS, "Successfully deleted task."); 
		taskHistoryDao.insertTaskHistory(taskHistory);
		
		return task;
	}

	@RequestMapping(value = "/action/processTask/{taskId}", method = RequestMethod.POST)
	@Produces("application/json")
	@Consumes("application/json")
	@ResponseBody
	public String processTask(HttpServletRequest request, @PathVariable("taskId") Long taskId) {

		JSONObject errorsJs = new JSONObject();
		Task task = taskDao.getTaskById(taskId);
		String userName = getUserName();
		task.setLastUpdate(new Date());
		task.setUpdatedBy(userName); 
		
		AuditInfo auditInfo = HunterUtility.getAuditInfoFromRequestForNow(request, userName);
		TaskHistory taskHistory = taskManager.getNewTaskHistoryForEventName(task.getTaskId(), TaskHistoryEventEnum.PROCESS.getEventName(), userName);
		
		Map<String, Object> result = taskManager.processTask(task,auditInfo);
		
		@SuppressWarnings("unchecked")
		List<String> validationErrors = (List<String>) result.get(GateWayClientService.TASK_VALIDATION_ERRORS);
		if (validationErrors != null && !validationErrors.isEmpty()) {
			StringBuilder errBuilder = new StringBuilder();
			for (String error : validationErrors) {
				errBuilder.append(error).append(",");
			}
			String errors = errBuilder.toString();
			errors = errors.substring(0, errors.length() - 1);
			logger.warn("Task( " + task.getTaskId()+ " ) processing returned errors >> " + errors);
			errorsJs.put("errors", errors);
			errorsJs.put("status", HunterConstants.STATUS_FAILED);
			logger.debug("returned js >> " + errorsJs);
			
			taskManager.setTaskHistoryStatusAndMessage(taskHistory, HunterConstants.STATUS_FAILED, "Failed to process task. " + HunterUtility.stringifyList(validationErrors));  
			taskHistoryDao.insertTaskHistory(taskHistory);
			
		}else{
			taskManager.setTaskHistoryStatusAndMessage(taskHistory, HunterConstants.STATUS_SUCCESS, "Successfully processed task."); 
			taskHistoryDao.insertTaskHistory(taskHistory);
		}
		
		return errorsJs.toString();
	}

	@RequestMapping(value = "/action/tskMsg/getDefault/{taskId}", method = RequestMethod.POST)
	@ResponseBody
	public String getDefaultTaskMsg(@PathVariable("taskId") Long taskId, HttpServletRequest request) {

		HunterUtility.threadSleepFor(500); 
		String tskMsgType = taskDao.getTaskMsgType(taskId);
		logger.debug("Message type for task : " + tskMsgType);
		
		AuditInfo auditInfo = HunterUtility.getAuditInfoFromRequestForNow(request, HunterConstants.HUNTER_ADMIN_USER_NAME);
		Message msg = taskManager.getTaskDefaultMessage(taskId,HunterConstants.MESSAGE_TYPE_TEXT);
		msg.setCreatedBy(auditInfo.getCreatedBy());
		msg.setCretDate(auditInfo.getCretDate());
		msg.setLastUpdate(auditInfo.getLastUpdate());
		msg.setLastUpdatedBy(auditInfo.getCreatedBy());
		msg.setMsgTaskType(tskMsgType);
		String taskOwner = taskDao.getUserNameForTaskOwnerId(taskId);
		msg.setMsgOwner(taskOwner);
		
		//Default service provider is safaricom
		ServiceProvider provider = HunterDaoFactory.getObject(ServiceProviderDao.class).getServiceProviderByName("Safaricom");  
		msg.setProvider(provider); 
		Task task = taskDao.getTaskById(taskId);
		task.setTaskMessage(msg); 
		taskDao.update(task); 

		String msgStr = null;

		try {
			msgStr = hunterJacksonMapper.writeValueAsString((TextMessage) msg);
			logger.debug("Message string >> " + msgStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msgStr;
	}

	@RequestMapping(value = "/action/tskGrp/read/{taskId}", method = RequestMethod.POST)
	@ResponseBody
	public List<ReceiverGroupJson> getTaskGroups(@PathVariable("taskId") Long taskId) {
		logger.debug("Reading task groups for task Id : " + taskId);
		List<ReceiverGroupJson> taskGroups = new ArrayList<ReceiverGroupJson>();
		if (taskId != 0) {
			taskGroups.addAll(taskDao.getTaskReceiverGroups(taskId));
		}
		logger.debug("Done reading task groups!");
		return taskGroups;
	}

	@RequestMapping(value = "/action/tskGrp/create", method = RequestMethod.POST)
	@Consumes("application/json")
	@Produces("application/json")
	@ResponseBody
	public String addGroupToTask(HttpServletRequest request) {

		JSONObject requestBodyJson = null;
		JSONObject json = new JSONObject();
		
		String userName = getUserName();
		TaskHistory taskHistory = taskManager.getNewTaskHistoryForEventName(null, TaskHistoryEventEnum.ADD_GROUP.getEventName(), userName);

		try {
			String requestBody = HunterUtility.getRequestBodyAsString(request);
			if (requestBody.startsWith("[") && requestBody.endsWith("]")) {
				requestBody = requestBody.substring(1, requestBody.length() - 1);
			}
			requestBodyJson = new JSONObject(requestBody);
		} catch (JSONException e) {
			e.printStackTrace();
			json.put(HunterConstants.STATUS_STRING,HunterConstants.STATUS_FAILED);
			json.put("Message", e.getMessage());
			return json.toString();
		} catch (IOException e) {
			e.printStackTrace();
			json.put(HunterConstants.STATUS_STRING,HunterConstants.STATUS_FAILED);
			json.put("Message", e.getMessage());
			return json.toString();
		}

		String taskIdStr = HunterUtility.getStringOrNullFromJSONObj(requestBodyJson, "taskId");
		String groupIdStr = HunterUtility.getStringOrNullFromJSONObj(requestBodyJson, "groupId");

		if (!HunterUtility.notNullNotEmpty(taskIdStr) || !HunterUtility.notNullNotEmpty(groupIdStr) || groupIdStr.equalsIgnoreCase("null") || taskIdStr.equalsIgnoreCase("null") ) {
			json.put(HunterConstants.STATUS_STRING, HunterConstants.STATUS_FAILED);
			json.put("Message", "Task or group id is invalid!");
			return json.toString();
		}
		
		Long taskId = HunterUtility.getLongOrNulFromJSONObj(requestBodyJson, "taskId");
		Long groupId = HunterUtility.getLongOrNulFromJSONObj(requestBodyJson, "groupId");
		
		if( taskId == null || groupId == null ){
			json.put(HunterConstants.STATUS_STRING, HunterConstants.STATUS_FAILED);
			json.put("Message", "Task or group id is invalid!");
			return json.toString();
		}
		
		String groupName = receiverGroupDao.getGroupNameById(groupId);
		String results = taskManager.addGroupToTask(groupId, taskId);
		
		taskHistory.setTaskId(taskId); 

		if (results != null) {
			json.put(HunterConstants.STATUS_STRING,HunterConstants.STATUS_FAILED);
			json.put("Message", results);
			taskManager.setTaskHistoryStatusAndMessage(taskHistory, HunterConstants.STATUS_FAILED, "Failed to add group( " + groupIdStr +" - " + groupName + " ) to task. " + results);  
			taskHistoryDao.insertTaskHistory(taskHistory);
			return json.toString();
		}

		int groupReceiverCount = taskManager.getTotalTaskGroupsReceivers(taskId);

		json.put(HunterConstants.STATUS_STRING, HunterConstants.STATUS_SUCCESS);
		json.put("Message", "Successfully added group to task!");
		json.put("groupReceiverCount", groupReceiverCount);
		
		taskManager.setTaskHistoryStatusAndMessage(taskHistory, HunterConstants.STATUS_SUCCESS, "Successfully added group ( " + groupIdStr +" - " + groupName + " )");   
		taskHistoryDao.insertTaskHistory(taskHistory);

		return json.toString();
	}

	@RequestMapping(value = "/action/tskGrp/destroy", method = RequestMethod.POST)
	@ResponseBody
	public String revoveGroupFromTask(HttpServletRequest request) {

		logger.debug("Removing group from task");

		JSONObject requestBodyJson = null;
		JSONObject json = new JSONObject();
		
		String userName = getUserName();
		TaskHistory taskHistory = taskManager.getNewTaskHistoryForEventName(null, TaskHistoryEventEnum.REMOVE_GROUP.getEventName(), userName);

		try {
			String requestBody = HunterUtility.getRequestBodyAsString(request);
			if (requestBody.startsWith("[") && requestBody.endsWith("]")) {
				requestBody = requestBody.substring(1, requestBody.length() - 1);
			}
			requestBodyJson = new JSONObject(requestBody);
		} catch (JSONException e) {
			e.printStackTrace();
			json.put(HunterConstants.STATUS_STRING,HunterConstants.STATUS_FAILED);
			json.put("Message", e.getMessage());
			return json.toString();
		} catch (IOException e) {
			e.printStackTrace();
			json.put(HunterConstants.STATUS_STRING,HunterConstants.STATUS_FAILED);
			json.put("Message", e.getMessage());
			return json.toString();
		}

		Long taskId = HunterUtility.getLongFromObject(requestBodyJson.getLong("taskId"));
		Long groupId = HunterUtility.getLongFromObject(requestBodyJson.getLong("groupId"));
		
		taskHistory.setTaskId(taskId);

		try {
			taskManager.removeGroupFromTask(groupId, taskId);
			taskManager.setTaskHistoryStatusAndMessage(taskHistory, HunterConstants.STATUS_SUCCESS, "Successfully removed group( " + groupId + " ) from task. ");  
			taskHistoryDao.insertTaskHistory(taskHistory);
			logger.debug("Successfully removed group from task");
		} catch (Exception e) {
			e.printStackTrace();
			json.put(HunterConstants.STATUS_STRING,HunterConstants.STATUS_FAILED);
			json.put("Message", e.getMessage());
			return json.toString();
		}

		int groupReceiverCount = taskManager.getTotalTaskGroupsReceivers(taskId);

		json.put(HunterConstants.STATUS_STRING, HunterConstants.STATUS_SUCCESS);
		json.put("Message", "Successfully removed group from task!");
		json.put("groupReceiverCount", groupReceiverCount);

		return json.toString();
	}
	
	@Produces("application/json")
	@RequestMapping(value = "/action/task/history/getForTask/{taskId}", method = RequestMethod.POST)
	public @ResponseBody List<TaskHistory> getTaskHistoriesForTask(@PathVariable("taskId")Long taskId) {
		List<TaskHistory> taskHistories = taskHistoryDao.getTaskHistoriesByTaskId(taskId);
		return taskHistories;
	}
	
	@Produces("application/json")
	@Consumes("application/json")
	@RequestMapping(value = "/action/task/process/validate", method = RequestMethod.POST)
	public @ResponseBody String validateTaskForProcessing(HttpServletRequest request) {
		
		HunterUtility.threadSleepFor(1000);
		JSONObject messages = new JSONObject();
		List<String> results;
		
		try {
			JSONObject jsonObject = null;
			jsonObject = new JSONObject(HunterUtility.getRequestBodyAsString(request));
			Long selTaskId = jsonObject.getLong("selTaskId");
			Task task = taskDao.getTaskById(selTaskId); 
			results = taskManager.validateTask(task);
			if(!taskManager.userHasRole(HunterUserRolesEnums.ROLE_TASK_PROCESSOR.getName(), getUserName())){ 
				String message = HunterCacheUtil.getInstance().getUIMsgTxtForMsgId(UIMessageConstants.MSG_TASK_010 );
				results = results == null ? new ArrayList<String>() : results;
				results.add(message);
				logger.debug(message); 
			}
			String resulString = results != null && !results.isEmpty() ? HunterUtility.getCommaDelimitedStrings(results) : null;
			logger.debug("Finished validating task : " + resulString);
			if(resulString != null){
				TaskHistory taskHistory = taskManager.getNewTaskHistoryForEventName(selTaskId, TaskHistoryEventEnum.VALIDATE_PROCESS.getEventName(), getUserName());
				taskManager.setTaskHistoryStatusAndMessage(taskHistory, HunterConstants.STATUS_FAILED, "Failed to process task. " + resulString);  
				taskHistoryDao.insertTaskHistory(taskHistory);
				messages = HunterUtility.setJSONObjectForFailure(messages, resulString);
			}else{
				messages = HunterUtility.setJSONObjectForSuccess(messages, resulString);
			}
			return messages.toString();
		} catch (Exception e) {
			e.printStackTrace();
			messages = HunterUtility.setJSONObjectForFailure(messages, "Application error please contact production support!");
			return messages.toString();
		}
		
	}
	
	@Produces("application/json")
	@Consumes("application/json")
	@RequestMapping(value = "/action/task/process/process", method = RequestMethod.POST)
	public @ResponseBody String processTask(HttpServletRequest request) {
		
		HunterUtility.threadSleepFor(1000);
		JSONObject messages = new JSONObject();
		List<String> results;
		
		try {
			JSONObject jsonObject = null;
			jsonObject = new JSONObject(HunterUtility.getRequestBodyAsString(request));
			Long selTaskId = jsonObject.getLong("selTaskId");
			Task task = taskDao.getTaskById(selTaskId); 
			results = taskManager.validateTask(task);
			if(!taskManager.userHasRole(HunterUserRolesEnums.ROLE_TASK_PROCESSOR.getName(), getUserName())){ 
				String message = HunterCacheUtil.getInstance().getUIMsgTxtForMsgId(UIMessageConstants.MSG_TASK_010 );
				results = results == null ? new ArrayList<String>() : results;
				results.add(message);
				logger.debug(message); 
			}
			String resulString = results != null && !results.isEmpty() ? HunterUtility.getCommaDelimitedStrings(results) : null;
			logger.debug("Finished validating task : " + resulString);
			TaskHistory taskHistory = taskManager.getNewTaskHistoryForEventName(selTaskId, TaskHistoryEventEnum.PROCESS.getEventName(), getUserName());
			if(resulString != null){
				taskManager.setTaskHistoryStatusAndMessage(taskHistory, HunterConstants.STATUS_FAILED, "Failed to process task. " + resulString);  
				taskHistoryDao.insertTaskHistory(taskHistory);
				messages = HunterUtility.setJSONObjectForFailure(messages, resulString);
			}else{
				logger.debug("Submitting task for processing..."); 
				taskManager.processTask(task, HunterUtility.getAuditInfoFromRequestForNow(request, getUserName()));
				taskManager.setTaskHistoryStatusAndMessage(taskHistory, HunterConstants.STATUS_SUCCESS, "Successfully processed task!");  
				taskHistoryDao.insertTaskHistory(taskHistory);
				messages = HunterUtility.setJSONObjectForSuccess(messages, "Successfully processed Task ( " + task.getTaskName() + " )"  ); 
			}
			return messages.toString();
		} catch (Exception e) {
			e.printStackTrace();
			messages = HunterUtility.setJSONObjectForFailure(messages, "Application error please contact production support!");
			return messages.toString();
		}
		
	}
	
	
	@Produces("application/json")
	@RequestMapping(value = "/action/task/process/results/{taskId}", method = RequestMethod.POST)
	public @ResponseBody  List<TaskProcessJobJson> getTaskProcessJobResults(@PathVariable("taskId")Long taskId) {
		logger.debug("Fetching task process job results for task id : " + taskId); 
		List<TaskProcessJobJson> list = TaskProcessJobConverter.getInstance().getProcessJobJsonsForTask(taskId);
		return list;
	}
	
	
	
	
}

