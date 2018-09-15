package com.techmaster.hunter.controllers;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.techmaster.hunter.angular.data.HunterAngularDataHelper;
import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.MessageAttachmentBeanDao;
import com.techmaster.hunter.dao.types.MessageDao;
import com.techmaster.hunter.dao.types.ServiceProviderDao;
import com.techmaster.hunter.dao.types.TaskDao;
import com.techmaster.hunter.dao.types.TaskHistoryDao;
import com.techmaster.hunter.email.HunterEmailTemplateHandler;
import com.techmaster.hunter.enums.TaskHistoryEventEnum;
import com.techmaster.hunter.exception.HunterRunTimeException;
import com.techmaster.hunter.gateway.beans.GateWayClientHelper;
import com.techmaster.hunter.json.AngularTaskMessage;
import com.techmaster.hunter.json.HunterSocialAppJson;
import com.techmaster.hunter.json.MessageAttachmentBeanJson;
import com.techmaster.hunter.json.SocialMessageJson;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.EmailMessage;
import com.techmaster.hunter.obj.beans.HunterEmailTemplateBean;
import com.techmaster.hunter.obj.beans.HunterSocialApp;
import com.techmaster.hunter.obj.beans.HunterSocialGroup;
import com.techmaster.hunter.obj.beans.HunterSocialMedia;
import com.techmaster.hunter.obj.beans.Message;
import com.techmaster.hunter.obj.beans.ServiceProvider;
import com.techmaster.hunter.obj.beans.SocialMessage;
import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.obj.beans.TaskHistory;
import com.techmaster.hunter.obj.beans.TextMessage;
import com.techmaster.hunter.social.HunterSocialHelper;
import com.techmaster.hunter.task.TaskManager;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterUtility;

@Controller
@RequestMapping(value="/message")
public class MessageConstroller extends HunterBaseController implements ServletContextAware{
	
	@Autowired private ServiceProviderDao serviceProviderDao;
	@Autowired private TaskManager taskManager;
	@Autowired private TaskDao taskDao;
	@Autowired private MessageDao messageDao;
	@Autowired private TaskHistoryDao taskHistoryDao;
	@Autowired private HunterHibernateHelper hunterHibernateHelper;
	
	private ServletContext servletContext;
	private Logger logger = Logger.getLogger(MessageConstroller.class);
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@RequestMapping(value="/action/mainPage", method = RequestMethod.GET )
	public String mainPage(HttpServletRequest request, HttpServletResponse response){
		return "views/messageMainPage";
	}
	
	@RequestMapping(value="/action/read/all", method = RequestMethod.POST )
	public String readAll(HttpServletRequest request, HttpServletResponse response){
		return "views/messageMainPage";
	}
	
	@RequestMapping(value="/action/providers/read", method = RequestMethod.POST )
	public List<ServiceProvider> readAllProviders(HttpServletRequest request, HttpServletResponse response){
		List<ServiceProvider> providers = serviceProviderDao.getAllServiceProviders();
		return providers;
	}
	
	@RequestMapping(value="/action/tskMsg/getAngularMsg/{taskId}", method = RequestMethod.GET )
	@Produces("application/json")
	public @ResponseBody Object getAngularMsg( @PathVariable("taskId") Long taskId ){
		try {
			AngularTaskMessage taskMessage = new AngularTaskMessage();
			Message message = HunterDaoFactory.getObject(MessageDao.class).getMessageById(taskId);
			if ( null != message ) {
				BeanUtils.copyProperties(message, taskMessage);
				taskMessage.setProvider(message.getProvider());
				if ( HunterConstants.MESSAGE_TYPE_TEXT.equalsIgnoreCase(taskMessage.getMsgTaskType()) ) {
					taskMessage.setPageable( ( Boolean.toString(((TextMessage)message).isPageable())));
				}
			}
			List<AngularTaskMessage> messageList = Arrays.asList( new AngularTaskMessage[] { taskMessage } );
			return HunterAngularDataHelper.getIntance().getDataBean(messageList, null);
		} catch (Exception e) {
			e.printStackTrace();
			return HunterAngularDataHelper.getIntance().getBeanForMsgAndSts("No data found for task id: " + taskId , HunterConstants.STATUS_FAILED, null);
		}		
	}
	
	@RequestMapping(value="/action/tskMsg/create/{taskId}", method = RequestMethod.POST )
	@Produces("application/json")
	@Consumes("application/json") 
	public @ResponseBody Object createTaskMessage(@PathVariable("taskId") Long taskId, HttpServletRequest request){
		
		logger.debug("Creating text message from json...");
		String requestBody = null;
		
		TaskHistory taskHistory = taskManager.getNewTaskHistoryForEventName(taskId, TaskHistoryEventEnum.ADD_MESSAGE.getEventName(), getUserName());
		
		try {
			requestBody = HunterUtility.getRequestBodyAsString(request);
		} catch (IOException e) {
			e.printStackTrace();
			taskManager.saveTaskHitory(taskHistory, e.getMessage(), HunterConstants.STATUS_FAILED);
		}
		
		String tskMsgType = taskDao.getTaskMsgType(taskId);
		logger.debug("Message type for task : " + tskMsgType); 
		
		Message message = null;
		
		if(tskMsgType != null && tskMsgType.equals(HunterConstants.MESSAGE_TYPE_TEXT)){  
			message = taskManager.convertTextMessage(requestBody);
			message.setMsgTaskType(tskMsgType);
		}else {
			String error = "Task message type not supported at the moment : " + tskMsgType + " and message";
			taskManager.saveTaskHitory(taskHistory, error, HunterConstants.STATUS_FAILED);
			throw new HunterRunTimeException(error);
		}
		
		logger.debug("Task Message obtained >> " + message);
		Task task = taskDao.getTaskById(taskId);
		
		boolean hasMessage = task.getTaskMessage() != null;
		
		if(hasMessage){
			message.setMsgId(taskId); 
			messageDao.updateMessage(message); 
		}else{
			message.setMsgId(taskId); 
			task.setTaskMessage(message);
			taskDao.update(task);
		}
		
		List<Message> messages = new ArrayList<>();
		messages.add(message);
		taskManager.saveTaskHitory(taskHistory, "Successfully " + ( !hasMessage ? "added" : "updated" ) + " message to task.", HunterConstants.STATUS_SUCCESS);
		return HunterAngularDataHelper.getIntance().getDataBean(messages, null);
	}
	
	@RequestMapping(value="/action/tskMsg/email/deleteEmail/{taskId}", method = RequestMethod.POST )
	@Produces("application/json") 
	public @ResponseBody String deleteTaskEmailMsg(HttpServletRequest request, HttpServletResponse response, @PathVariable("taskId") Long taskId){
		
		JSONObject results = new JSONObject();
		List<Object>  errors = taskManager.validateMessageDelete(taskId);
		
		String userName = getUserName();
		TaskHistory taskHistory = taskManager.getNewTaskHistoryForEventName(taskId, TaskHistoryEventEnum.DELETE_MESSAGE.getEventName(), userName);
		
		if(errors != null && !errors.isEmpty()){
			String	lifeStsMsg =  errors.get(0) + "";
			String	delStsMsg =  errors.get(1) + "";
			String message = "";
			if(!lifeStsMsg.equals("Delete")){
				message = lifeStsMsg;
			}
			if(!delStsMsg.equals("Delete")){
				message = message == null ? "" : message;
				message += "<br/>" + delStsMsg;
			}
			if((!delStsMsg.equals("Delete") || !lifeStsMsg.equals("Delete"))){  
				logger.debug("Cannot delete message because : " + message);
				taskManager.setTaskHistoryStatusAndMessage(taskHistory, HunterConstants.STATUS_FAILED, "Cannot delete message because : " + message); 
				taskHistoryDao.insertTaskHistory(taskHistory); 
				return HunterUtility.setJSONObjectForFailure(results, message ).toString();
			}else{
				Task task = taskDao.getTaskById(taskId);
				Message msg = task.getTaskMessage();
				messageDao.deleteMessage(msg);
				task.setTaskMessage(null); 
				taskDao.update(task); 
				logger.debug("Successfully deleted message for task id : " + taskId); 
				taskManager.setTaskHistoryStatusAndMessage(taskHistory, HunterConstants.STATUS_FAILED, "Successfully deleted message for task id : " + taskId); 
				taskHistoryDao.insertTaskHistory(taskHistory); 
			}
		}
		return HunterUtility.setJSONObjectForSuccess(results, "Successfully deleted message" ).toString();
	}
	
	@RequestMapping(value="/action/tskMsg/email/createOrUpdate", method = RequestMethod.POST )
	@Produces("application/json")
	@Consumes("application/json")
	public @ResponseBody String createOrUpdateEmailMsg(HttpServletRequest request, HttpServletResponse response){
		
		JSONObject results = new JSONObject();
		JSONObject input =  null;
		
		logger.debug("Creating text message from json...");
		String requestBody = null;
		Long taskId = null;
		String emailTemplateName = null;
		boolean hasAttachment = false;
		String tskEmailMsgStatus = null;
		String taskEmailMsgSendDateStr = null;
		Date msgSendDate = null;
		String taskEmailHtml = null;
		String subject = null;
		
		try {
			requestBody = HunterUtility.getRequestBodyAsString(request);
			input = new JSONObject(requestBody); 
			String taskIdStr = HunterUtility.getStringOrNullFromJSONObj(input, "tskId");
			taskId = HunterUtility.getLongFromObject(taskIdStr);
			emailTemplateName = HunterUtility.getStringOrNullFromJSONObj(input, "emailTemplateName");
			hasAttachment = HunterUtility.getStringOrNullFromJSONObj(input, "hasAttachment").equals("true") ? true : false;
			tskEmailMsgStatus = HunterUtility.getStringOrNullFromJSONObj(input, "tskEmailMsgStatus");
			taskEmailMsgSendDateStr = HunterUtility.getStringOrNullFromJSONObj(input, "taskEmailMsgSendDate");
			taskEmailMsgSendDateStr = taskEmailMsgSendDateStr.replaceAll("T", " ");
			taskEmailMsgSendDateStr = taskEmailMsgSendDateStr.replaceAll("Z", "");
			msgSendDate = HunterUtility.parseDate(taskEmailMsgSendDateStr+":00", HunterConstants.DATE_FORMAT_STRING);
			taskEmailHtml = HunterUtility.getStringOrNullFromJSONObj(input, "taskEmailHtml");
			subject = HunterUtility.getStringOrNullFromJSONObj(input, "emailSubject");
		} catch (IOException e) {
			e.printStackTrace();
			return HunterUtility.setJSONObjectForFailure(results, e.getMessage()).toString();
		}
		
		Task task = taskDao.getTaskById(taskId);
		EmailMessage message = (EmailMessage)task.getTaskMessage();
		String userName = getUserName();
		TaskHistory taskHistory = taskManager.getNewTaskHistoryForEventName(taskId, TaskHistoryEventEnum.ADD_MESSAGE.getEventName(), userName);
		
		/* If attachment template name changes, 
		 * then wipe out the existing one attachment configuration
		 */
		String originalTemplateName = message == null ? null : message.getEmailTemplateName();
		if( message != null && !emailTemplateName.equalsIgnoreCase(originalTemplateName) ){
			message.setEmailTemplateName(emailTemplateName);
			message.setMessageAttachments(null); 
		}
		
		if(message == null){
			logger.debug("No email message configured for task. Creating email message...");  
			message = new EmailMessage();
			message.setMsgId(task.getTaskId());
			message.setActualReceivers(0); 
			message.setConfirmedReceivers(0);
			message.setCreatedBy(userName);
			message.setCretDate(new Date()); 
			message.setDesiredReceivers(0); 
			message.seteSubject(subject);
			message.setLastUpdate(new Date());
			message.setLastUpdatedBy(userName);
			message.setMsgDeliveryStatus(HunterConstants.STATUS_CONCEPTUAL); 
			message.setMsgLifeStatus(HunterConstants.STATUS_DRAFT);
			message.setMsgOwner(taskDao.getUserNameForTaskOwnerId(taskId)); 
			message.setMsgSendDate(msgSendDate);
			message.setMsgTaskType(HunterConstants.MESSAGE_TYPE_EMAIL); 
			message.setMsgText(taskEmailHtml);
			ServiceProvider serviceProvider = serviceProviderDao.getServiceProviderByName("Hunter Email Provider");
			message.setProvider(serviceProvider);
			message.setEmailTemplateName(emailTemplateName);
			message.setHasAttachment(hasAttachment);
			messageDao.insertMessage(message); 
			logger.debug("Finished creating email message... : \n " + message);
		}else{
			message.setLastUpdate(new Date());
			message.setLastUpdatedBy(userName);
			message.setMsgLifeStatus(HunterConstants.STATUS_DRAFT);
			message.setMsgSendDate(msgSendDate);
			message.setMsgText(taskEmailHtml);
			message.setMsgLifeStatus(tskEmailMsgStatus); 
			message.setEmailTemplateName(emailTemplateName);
			message.setHasAttachment(hasAttachment);
			message.seteSubject(subject); 
		}
		task.setTaskMessage(message);
		taskDao.update(task);
		taskManager.setTaskHistoryStatusAndMessage(taskHistory, HunterConstants.STATUS_FAILED, "Successfully added message to task with task id : " + taskId); 
		taskHistoryDao.insertTaskHistory(taskHistory);
		logger.debug("Finished updating email message... : \n " + message);
		
		return HunterUtility.setJSONObjectForSuccess(results, "Successfully saved message" ).toString();
	}
	
	@RequestMapping(value="/action/tskMsg/email/getEmailTemplateForName/{templateName}", method = RequestMethod.GET )
	@Produces("application/html")
	public @ResponseBody String getEmailTemplateForName(HttpServletRequest request, HttpServletResponse response, @PathVariable("templateName") String templateName){
		new URLDecoder();
		String templateNameDecoded;
		String html = null;
		try {
			templateNameDecoded = URLDecoder.decode(templateName, "UTF-8");
			html = HunterEmailTemplateHandler.getInstance().getTemplateForName(templateNameDecoded); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "<span style='color:red;font-weight:bolder;font-size:13px;'> Error occurred!! <br/> ( "+ e.getMessage() +" ) </span>";
		}
		return html;
	}
	
	@RequestMapping(value="/action/tskMsg/email/getAllTemplateNames", method = RequestMethod.POST )
	@Produces("application/json")
	public @ResponseBody List<String> getAllExistingTemplateNames(HttpServletRequest request, HttpServletResponse response){
		List<String> existingNames = HunterEmailTemplateHandler.getInstance().getAllExistingTemplatesNames();
		return existingNames;
	}
	
	@RequestMapping(value="/action/email/getRefreshValues/{msgId}", method = RequestMethod.POST )
	@Produces("application/json") 
	public @ResponseBody Map<String,Object> getEmailMsgRefreshVals(HttpServletRequest request, HttpServletResponse response, @PathVariable("msgId") Long msgId){
		Map<String,Object> data = messageDao.getEmailMsgRefreshData(msgId);
		return data;
	}
	
	@RequestMapping(value="/action/tskMsg/getPrcssTxtMssgeDtls/{msgId}", method = RequestMethod.POST )
	@Consumes("application/json") 
	@Produces("application/json") 
	public @ResponseBody Map<String,Object> getPrcssTxtMssgeDtls(HttpServletRequest request, HttpServletResponse response, @PathVariable("msgId") Long msgId){
		Map<String,Object> data = messageDao.getPrcssTxtMssgeDtls(msgId);
		HunterUtility.threadSleepFor(500);
		return data;
	}
	
	@RequestMapping(value="/action/tskMsg/updateTxtMsg", method = RequestMethod.POST )
	@Consumes("application/json") 
	@Produces("application/json") 
	@ResponseBody
	public String updateTextMessage(@RequestBody Map<String,String> params){
		
		logger.debug(HunterUtility.stringifyMap(params)); 
		HunterUtility.threadSleepFor(400); 
		JSONObject results = new JSONObject();
		
		try{
			
			String userName = getUserName();
			Long taskId = HunterUtility.getLongFromObject(params.get("taskId"));
			String owner = HunterUtility.getStringOrNullOfObj(params.get("msgOwner"));
			Long providerId = HunterUtility.getLongFromObject(params.get("providerId"));
			String msgSts = HunterUtility.getStringOrNullOfObj(params.get("msgSts"));
			String msgText = HunterUtility.getStringOrNullOfObj(params.get("msgText"));
			
			msgText = msgText.replaceAll("&apos;", "'");
			msgText = msgText.replaceAll("&quot;", "\"");
			
			Task task = taskDao.getTaskById(taskId);
			
			if(task != null && HunterConstants.MESSAGE_TYPE_TEXT.equals(task.getTskMsgType())){
				ServiceProvider provider = serviceProviderDao.getServiceProviderById(providerId);
				TextMessage txtMsg = (TextMessage)task.getTaskMessage();
				boolean insert = false;
				if(txtMsg == null){
					txtMsg = taskManager.getDefaultTextMessage(task, getAuditInfo());
					insert = true;
				}
				txtMsg.setMsgOwner(owner);
				txtMsg.setLastUpdatedBy(userName);
				txtMsg.setLastUpdate(new Date()); 
				txtMsg.setMsgDeliveryStatus(HunterConstants.STATUS_CONCEPTUAL);
				txtMsg.setMsgLifeStatus(msgSts);
				txtMsg.setProvider(provider); 
				txtMsg.setMsgText(msgText); 
				txtMsg.setMsgTaskType(HunterConstants.MESSAGE_TYPE_TEXT);
				if(insert){
					messageDao.insertMessage(txtMsg); 
				}else{
					messageDao.updateMessage(txtMsg);
				}
				taskDao.update(task); 
				HunterUtility.setJSONObjectForSuccess(results, "Successfully updated text message");
			}else{
				HunterUtility.setJSONObjectForFailure(results, "No text task found for task id : " + taskId);
			}
			
			return results.toString();
			
		}catch(Exception e){
			e.printStackTrace(); 
			HunterUtility.setJSONObjectForFailure(results, e.getMessage());
		}
		return results.toString();
	}
	
	
	@RequestMapping(value="/action/tskMsg/getReadyEmailBody/{taskId}", method = RequestMethod.GET )
	public void getReadyEmailBody(@PathVariable("taskId") Long taskId, HttpServletResponse response, HttpServletRequest request){
		
		
		String prevLoc = servletContext.getRealPath(File.separator) + "resources"+ File.separator + "tempPrevs";
		String staticPrevLoc = HunterUtility.getRequestBaseURL(request) + "/Hunter/static/resources/tempPrevs";
		
		Task task = taskDao.getTaskById(taskId);
		String body = "";
		if( task != null && HunterConstants.MESSAGE_TYPE_EMAIL.equals(task.getTskMsgType()) ){ 
			body = HunterCacheUtil.getInstance().getReadyEmailBodyForEmailTask(task, prevLoc, staticPrevLoc);
		}else{
			if(task == null)
				body = "<h2 style='color:red;'> Task with of task id : "+ taskId +", is cannot be found!  </h2>";
			else 
				body = "<h2 style='color:red;'>Task is not of email type</h2>";
			logger.debug(body);
		}
		response.setContentType("text/html"); 
		try {
			response.getWriter().println(body);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@RequestMapping(value="/action/tskMsg/attachments/{templateName}", method = RequestMethod.POST )
	@Consumes("application/json") 
	@Produces("application/json") 
	@ResponseBody
	public Map<String,String> getAttachmentsForMessageTemplate(@PathVariable("templateName") String templateName){
		HunterEmailTemplateBean templateBean = HunterCacheUtil.getInstance().getEmailTemplateBean(templateName);
		return templateBean.getAttachments();
	}
	
	@RequestMapping(value="/action/tskMsg/attachmentsRecords", method = RequestMethod.POST )
	@Consumes("application/json") 
	@Produces("application/json") 
	@ResponseBody
	public List<MessageAttachmentBeanJson> getMessageAttachmentsRecords(){
		HunterUtility.threadSleepFor(1000); 
		MessageAttachmentBeanDao messageAttachmentBeanDao = HunterDaoFactory.getObject(MessageAttachmentBeanDao.class);
		return messageAttachmentBeanDao.getAllAttachmentBeansJson();
	}
	
	@RequestMapping(value="/action/tskMsg/getMessageAttachmentsNamesString", method = RequestMethod.POST )
	@Consumes("application/json") 
	@Produces("application/json") 
	@ResponseBody
	public String getMessageAttachmentsNamesString(@RequestBody Map<String,String> params){
		String data = null;
		Long msdId = HunterUtility.getLongFromObject(params.get("msgId"));
		if( HunterUtility.notNullNotEmpty(msdId) ){  
			EmailMessage emailMessage = (EmailMessage)messageDao.getMessageById(msdId);
			try{
				data = GateWayClientHelper.getInstance().rplcMsgAttchmntKysWthNms(emailMessage);
			}catch (NullPointerException e) {
				data = null;
			}
		}
		data = data == null ? "NO DATA" : data;
		return data;
	}
	
	@RequestMapping(value="/action/tskMsg/setAttachmentToMsgAttchment", method = RequestMethod.POST )
	@Consumes("application/json") 
	@Produces("application/json") 
	@ResponseBody
	public String setAttachmentToMsgAttchment(@RequestBody Map<String,Object> params){
		Long msgId = HunterUtility.getLongFromObject(params.get("taskId"));
		Long attchmentId = HunterUtility.getLongFromObject(params.get("attchmentId")); 
		String templateAttachmentName = HunterUtility.getStringOrNullOfObj(params.get("templateAttachmentName"));
		EmailMessage message = (EmailMessage)messageDao.getMessageById(msgId);
		GateWayClientHelper clientHelper = GateWayClientHelper.getInstance();
		Map<String,String> results = clientHelper.setAndGetMessageAttachmentsStringForMsg(message, attchmentId, templateAttachmentName );
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("uiMessage", results.get("UI_NAME"));
		jsonObject.put("msgAttachmentStr", results.get("MSG_ATTCHMNT_STR"));
		jsonObject = HunterUtility.setJSONObjectForSuccess(new JSONObject(), "Successfully updated message attachment!");
		logger.debug("Returning : " + jsonObject); 
		return jsonObject.toString();
	}
	
	@RequestMapping(value="/action/tskMsg/social/createOrUpdateSocialMsg", method = RequestMethod.POST )
	@Produces("application/html")
	@Consumes("application/json") 
	public @ResponseBody String createOrUpdateSocialMessage(@RequestBody SocialMessageJson socialMessageJson, HttpServletRequest request){
		
		SocialMessage socialMessage = hunterHibernateHelper.getEntityById(socialMessageJson.getSocialMsgId(), SocialMessage.class);
		AuditInfo auditInfo 		= getAuditInfo();
		boolean update 				= false;
		
		if( socialMessage != null ){
			update = true;
		}else{
			socialMessage = new SocialMessage();
			socialMessage.setCretDate(auditInfo.getCretDate());
			socialMessage.setCreatedBy(auditInfo.getCreatedBy()); 
		}
		
		socialMessage.setLastUpdate(auditInfo.getLastUpdate()); 
		socialMessage.setLastUpdatedBy(auditInfo.getLastUpdatedBy()); 
		
		socialMessage.setSocialMsgId(socialMessageJson.getSocialMsgId());
		socialMessage.setMsgId(socialMessageJson.getSocialMsgId()); 
		socialMessage.getLastUpdatedBy();
		
		socialMessage.setExternalId(socialMessageJson.getExternalId());
		socialMessage.setMediaType(socialMessageJson.getMediaType());
		socialMessage.setDescription(socialMessageJson.getDescription());
		socialMessage.setSocialPost(socialMessageJson.getSocialPost());
		socialMessage.setSocialPostType(socialMessageJson.getSocialPostType());
		socialMessage.setSocialPostAction(socialMessageJson.getSocialPostAction()); 
		
		
		Long defaultSclAppId = socialMessageJson.getDefaultSocialAppId();
		HunterSocialApp socialApp = defaultSclAppId == null ? null : hunterHibernateHelper.getEntityById(defaultSclAppId, HunterSocialApp.class);
		socialMessage.setDefaultSocialApp(socialApp);
		
		Long [] socialGroupIds = socialMessageJson.getHunterSocialGroupsIds();
		socialMessage.getHunterSocialGroups().clear();
		
		for(Long groupId : socialGroupIds){
			HunterSocialGroup hunterSocialGroup = hunterHibernateHelper.getEntityById(groupId, HunterSocialGroup.class);
			socialMessage.getHunterSocialGroups().add(hunterSocialGroup);
		}
		
		HttpSession session = HunterUtility.getSessionForRequest(request);
		HunterSocialMedia socialMedia = (HunterSocialMedia)session.getAttribute(HunterConstants.SOCIAL_MEDIA_IN_USER_SESSION);
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class); 
		
		if( socialMedia != null && !socialMessageJson.isUseRemoteMedia() ){
			
			String delete = "DELETE FROM HNTR_SCL_MDA WHERE MDA_ID = ?";
			List<Object> values = new ArrayList<>();
			values.add(socialMessage.getMsgId());
			hunterJDBCExecutor.executeUpdate(delete, values);
			
			socialMessage.setSocialMedia(socialMedia);
			socialMedia.setMediaId(socialMessage.getMsgId()); 
			socialMedia.setAuditInfo(HunterUtility.getAuditInfoForSclMsg(socialMessage)); 
			
			String cdetails = hunterJDBCExecutor.getQueryForSqlId("getTaskHunterClientDetails");
			List<Map<String, Object>> rowMapList = hunterJDBCExecutor.executeQueryRowMap(cdetails, values);
			String clientName = HunterUtility.getStringOrNullOfObj(HunterUtility.isCollNotEmpty(rowMapList) ? rowMapList.get(0).get("USR_NAM") : null); 
			socialMedia.setClientName(clientName);
			
			socialMessage.setUseRemoteMedia( false );
			
		}else if( socialMessageJson.isUseRemoteMedia() && HunterUtility.notNullNotEmpty(socialMessageJson.getRemoteURL()) ){  
			socialMessage.setUseRemoteMedia( socialMessageJson.isUseRemoteMedia() );
			HunterSocialMedia defltSclMda = HunterSocialHelper.getInstance().createDfltRmtSclMda(socialMessageJson.getRemoteURL(), auditInfo, socialMessage.getMsgId());
			socialMessage.setSocialMedia(defltSclMda);;
		}
		
		if( update ){
			messageDao.updateMessage(socialMessage);
		}else{
			messageDao.insertMessage(socialMessage);
		}
		return HunterUtility.setJSONObjectForSuccess(null, "Successfully saved changes!").toString();
	}
	
	
	@RequestMapping(value="/action/social/apps/dropdown", method = RequestMethod.POST )
	@Produces("application/json") 
	@ResponseBody
	public List<HunterSocialAppJson> getSocialAppDropdownVals(){
		return HunterSocialHelper.getInstance().getAllSocialAppsJsons();
	}
	

	
}
