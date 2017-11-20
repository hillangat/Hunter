package com.techmaster.hunter.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.proc.ProcedureHandler;
import com.techmaster.hunter.dao.types.EmailTemplateObjDao;
import com.techmaster.hunter.dao.types.HunterAddressDao;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.HunterRawReceiverUserDao;
import com.techmaster.hunter.dao.types.HunterUserDao;
import com.techmaster.hunter.dao.types.MessageAttachmentBeanDao;
import com.techmaster.hunter.dao.types.TaskHistoryDao;
import com.techmaster.hunter.dao.types.UserRoleDao;
import com.techmaster.hunter.enums.TaskHistoryEventEnum;
import com.techmaster.hunter.exception.HunterRunTimeException;
import com.techmaster.hunter.json.EmailTemplateObjJson;
import com.techmaster.hunter.json.HunterClientJson;
import com.techmaster.hunter.json.HunterUserJson;
import com.techmaster.hunter.json.MessageAttachmentBeanJson;
import com.techmaster.hunter.json.RawUsersDropdownJson;
import com.techmaster.hunter.json.RegionJsonForDropdowns;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.EmailTemplateObj;
import com.techmaster.hunter.obj.beans.HunterAddress;
import com.techmaster.hunter.obj.beans.HunterRawReceiverUser;
import com.techmaster.hunter.obj.beans.HunterUser;
import com.techmaster.hunter.obj.beans.MessageAttachmentBean;
import com.techmaster.hunter.obj.beans.TaskHistory;
import com.techmaster.hunter.obj.beans.UserRole;
import com.techmaster.hunter.obj.converters.HunterClientConverter;
import com.techmaster.hunter.obj.converters.HunterUserConverter;
import com.techmaster.hunter.task.TaskManager;
import com.techmaster.hunter.util.EmailTemplateUtil;
import com.techmaster.hunter.util.HunterUtility;

@Controller
@RequestMapping("/admin") 
public class HunterAdminController extends HunterBaseController{
	
	@Autowired private HunterUserDao hunterUserDao;
	@Autowired private HunterAddressDao hunterAddressDao;
	@Autowired private UserRoleDao userRoleDao;
	@Autowired private ProcedureHandler get_msg_ids_usng_attchmnt_id;
	
	private Logger logger = Logger.getLogger(getClass());

	@RequestMapping(value="/action/home", method=RequestMethod.GET)
	public String goAdminHome(){
		return "views/adminHome";
	}
	
	@RequestMapping(value="/action/social/fb/home", method=RequestMethod.GET)
	public String goToFbHome(){
		return "views/testFacebokConnection";
	}
	
	
	@RequestMapping(value="/action/emailTemplateObj/loadTemplateMetaData/{templateId}", method = RequestMethod.GET )
	public void loadTemplateMetaData( @PathVariable("templateId") Long templateId, HttpServletResponse response ){
		String html = loadEmailTemplateObjTemplates_(templateId);
		response.setContentType("text/html"); 
		try {
			response.getWriter().println(html);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private String loadEmailTemplateObjTemplates_(Long templateId){
		EmailTemplateObjDao emailTemplateObjDao = HunterDaoFactory.getObject(EmailTemplateObjDao.class);
		EmailTemplateObj emailTemplateObj = emailTemplateObjDao.getTemplateObjById(templateId);
		String metadata = HunterUtility.getBlobStr( emailTemplateObj.getDocumentMetadata() );
		metadata = metadata == null || metadata.trim().equalsIgnoreCase("") ? HunterCacheUtil.getInstance().getDefaultEmailTemplate() : metadata; 
		return metadata;
	}
	
	@Produces("application/json")
	@RequestMapping(value="/action/emailTemplateObj/create", method=RequestMethod.POST)
	public @ResponseBody EmailTemplateObjJson createEmailTemplateObj(@RequestBody EmailTemplateObjJson templateObjJson){
		EmailTemplateObj templateObj = EmailTemplateUtil.getInstance().createOrUpdateTemplateFromJson(templateObjJson, getAuditInfo());
		logger.debug("Successfully created object : " + templateObj); 
		return templateObjJson;
	}
	
	@Produces("application/json")
	@RequestMapping(value="/action/emailTemplateObj/changeStatus", method=RequestMethod.POST)
	public @ResponseBody String changeEmailTemplateObjStatus(HttpServletRequest request, @RequestBody Map<String,Object> params){
		String toStatus = HunterUtility.getStringOrNullOfObj( params.get("toStatus") ); 
		Long templateId = HunterUtility.getLongFromObject( params.get("templateId") );
		EmailTemplateObjDao templateObjDao = HunterDaoFactory.getObject(EmailTemplateObjDao.class);
		EmailTemplateObj emailTemplateObj = templateObjDao.getTemplateObjById(templateId);
		emailTemplateObj.setStatus( toStatus ); 
		templateObjDao.updateEmailTemplateObj(emailTemplateObj); 
		JSONObject json = HunterUtility.setJSONObjectForSuccess(null, "Successfully changed status to : " + toStatus);
		return json.toString();
	}
	
	@Produces("application/json")
	@Consumes("application/json") 
	@RequestMapping(value="/action/emailTemplateObj/saveTemplateFile/{templateId}", method=RequestMethod.POST)
	public @ResponseBody String saveEmailTemplateObjFile(@PathVariable("templateId") Long templateId, @RequestBody Map<String,Object> requestParams){
		EmailTemplateObjDao emailTemplateObjDao = HunterDaoFactory.getObject(EmailTemplateObjDao.class);
		EmailTemplateObj emailTemplateObj = emailTemplateObjDao.getTemplateObjById(templateId);
		emailTemplateObj.setXmlTemplates( HunterUtility.getStringBlob( HunterUtility.getStringOrNullOfObj( requestParams.get("content") ) ) );
		emailTemplateObjDao.updateEmailTemplateObj(emailTemplateObj); 
		return HunterUtility.setJSONObjectForSuccess(null, "Successfully save template file").toString();
	}
	
	@Produces("application/json")
	@Consumes("application/json") 
	@RequestMapping(value="/action/emailTemplateObj/saveTemplateMetaDataFile/{templateId}", method=RequestMethod.POST)
	public @ResponseBody String saveEmailTemplateMetaDataFile(@PathVariable("templateId") Long templateId, @RequestBody Map<String,Object> requestParams){
		EmailTemplateObjDao emailTemplateObjDao = HunterDaoFactory.getObject(EmailTemplateObjDao.class);
		EmailTemplateObj emailTemplateObj = emailTemplateObjDao.getTemplateObjById(templateId);
		emailTemplateObj.setDocumentMetadata( HunterUtility.getStringBlob( HunterUtility.getStringOrNullOfObj( requestParams.get("content") )));
		emailTemplateObjDao.updateEmailTemplateObj(emailTemplateObj); 
		return HunterUtility.setJSONObjectForSuccess(null, "Successfully save template file").toString();
	}
	
	
	private String getEmailTemplateHtmlForId(Long templateId){
		EmailTemplateObjDao emailTemplateObjDao = HunterDaoFactory.getObject(EmailTemplateObjDao.class);
		EmailTemplateObj emailTemplateObj = emailTemplateObjDao.getTemplateObjById(templateId);
		String html = HunterUtility.getBlobStr( emailTemplateObj.getXmlTemplates() );
		html = html == null ? "<div>Default Content</div>" : html;
		return html;
	}
	
	@RequestMapping(value="/action/emailTemplateObj/loadTemplates/{templateId}", method = RequestMethod.GET )
	public void loadEmailTemplateObjTemplates( @PathVariable("templateId") Long templateId, HttpServletResponse response ){
		String html = getEmailTemplateHtmlForId(templateId);
		response.setContentType("text/html"); 
		try {
			response.getWriter().println(html);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@RequestMapping(value="/action/emailTemplateObj/getTemplatePreview/{templateId}", method = RequestMethod.GET )
	public void getEmailTemplateObjPreview( @PathVariable("templateId") Long templateId, HttpServletResponse response ){
		String html = getEmailTemplateHtmlForId(templateId);
		response.setContentType("text/html"); 
		try {
			response.getWriter().println(html);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	@Produces("application/json")
	@RequestMapping(value="/action/emailTemplateObj/update", method=RequestMethod.POST)
	public @ResponseBody EmailTemplateObjJson updateEmailTemplateObj(@RequestBody EmailTemplateObjJson templateObjJson){
		EmailTemplateObj templateObj = EmailTemplateUtil.getInstance().createOrUpdateTemplateFromJson(templateObjJson, getAuditInfo());
		logger.debug("Successfully updated object : " + templateObj); 
		return templateObjJson;
	}
	
	@Produces("application/json")
	@RequestMapping(value="/action/emailTemplateObj/read", method=RequestMethod.POST)
	public @ResponseBody List<EmailTemplateObjJson> getAllEmailTemplateObj(){
		EmailTemplateObjDao templateObjDao = HunterDaoFactory.getObject(EmailTemplateObjDao.class);
		List<EmailTemplateObjJson> emailTemplateObjJsons = templateObjDao.getAllEmailTemplateObjJsons();
		return emailTemplateObjJsons;
	}	
	
	@Consumes("application/json") 
	@Produces("application/json")
	@RequestMapping(value="/action/emailTemplateObj/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteEmailTemplateObj(@RequestBody Map<String,Object> map){
		Long templateId = HunterUtility.getLongFromObject(map.get("templateId")); 
		EmailTemplateObjDao templateObjDao = HunterDaoFactory.getObject(EmailTemplateObjDao.class);
		EmailTemplateObj templateObj = templateObjDao.getTemplateObjById(templateId);
		String results = templateObjDao.deleteEmailTemplateObj(templateObj);
		results = results == null ? HunterUtility.setJSONObjectForSuccess(null, "Template successfully deleted").toString() : HunterUtility.setJSONObjectForFailure(null, results).toString();
		logger.debug(results); 
		return results;
	}	
	
	@Produces("application/json")
	@RequestMapping(value="/action/user/create", method=RequestMethod.POST)
	public @ResponseBody HunterUserJson createHunterUser(@RequestBody HunterUserJson hunterUserJson){
		String userName = getUserName();
		AuditInfo auditInfo = HunterUtility.getAuditInfoFromRequestForNow(null, userName);
		HunterUser hunterUser = HunterUserConverter.getInstance().getNewHunterUserFromJson(hunterUserJson, auditInfo);
		hunterUserDao.insertHunterUser(hunterUser);
		hunterUserJson = HunterUserConverter.getInstance().createHunterUserJsonForUser(hunterUser);
		return hunterUserJson;
	}
	
	@Produces("application/json")
	@RequestMapping(value="/action/user/update", method=RequestMethod.POST)
	public @ResponseBody HunterUserJson updateHunterUsers(@RequestBody HunterUserJson hunterUserJson){
		String userName = getUserName();
		AuditInfo auditInfo = HunterUtility.getAuditInfoFromRequestForNow(null, userName);
		HunterUser hunterUser = HunterUserConverter.getInstance().getNewHunterUserFromJson(hunterUserJson, auditInfo);
		hunterUserDao.updateUser(hunterUser);
		hunterUserJson = HunterUserConverter.getInstance().createHunterUserJsonForUser(hunterUser);
		return hunterUserJson;
	}
	
	@Produces("application/json")
	@Consumes("application/json")
	@RequestMapping(value="/action/user/destroy", method=RequestMethod.POST)
	public @ResponseBody String destroyHunterUsers(HttpServletRequest request){
		String requestBody = null;
		JSONObject body = null;
		JSONObject result = new JSONObject();
		try {
			requestBody = HunterUtility.getRequestBodyAsString(request);
			body = new JSONObject(requestBody);
			String userIdStr = HunterUtility.getStringOrNullFromJSONObj(body, "userId"); 
			if(userIdStr != null && HunterUtility.isNumeric(userIdStr)){ 
				String deleteErrors = hunterUserDao.validateAndDeleteById(HunterUtility.getLongFromObject(userIdStr));
				if(deleteErrors != null){
					result = HunterUtility.setJSONObjectForFailure(result, deleteErrors);
				}else{
					result = HunterUtility.setJSONObjectForSuccess(result, "User deleted successfully");
				}
			}else{
				result = HunterUtility.setJSONObjectForFailure(result, "Invalid user id ( " + userIdStr + " )"); 
			}
		} catch (IOException e) {
			e.printStackTrace();
			result = HunterUtility.setJSONObjectForFailure(result, "Appplicatiation error occured!");
		}
		return result.toString();
	}
	
	
	@Produces("application/json")
	@ResponseBody 
	@RequestMapping(value="/action/user/read", method=RequestMethod.POST)
	public List<HunterUserJson> getAllHunterUsers(){
		List<HunterUserJson> users = hunterUserDao.getAllUserJson();
		return users;
	}
	
	@RequestMapping(value="/action/templates/userDetailsTab", method=RequestMethod.GET)
	public String getHunterUserDetailsTabsrip(){
		return "templates/taskUserDetails";
	}
	
	@RequestMapping(value="/action/templates/{templateName}", method=RequestMethod.GET)
	public String getTemplate(@PathVariable String templateName){
		if(templateName != null && HunterUtility.notNullNotEmpty(templateName)){
			return "templates/" + templateName;
		}else{
			throw new HunterRunTimeException("Template name cannot be found!!"); 
		}
	}
	
	@RequestMapping(value="/action/templates/userAddressesTab", method=RequestMethod.GET)
	public String getHunterUserAddressesTabsrip(){
		return "templates/taskUserAddresses";
	}
	
	@RequestMapping(value="/action/templates/userRolesTab", method=RequestMethod.GET)
	public String getHunterUserRolesTabsrip(){
		return "templates/taskUserRoles";
	}
	
	@Produces("application/json")
	@ResponseBody 
	@RequestMapping(value="/action/user/addresses/read/{userId}", method=RequestMethod.POST)
	public List<HunterAddress> getUserAddresses(@PathVariable("userId")Long userId){
		List<HunterAddress> usersAddresses = hunterAddressDao.getAddressesByUserId(userId);
		return usersAddresses;
	}
	
	@Produces("application/json")
	@ResponseBody 
	@RequestMapping(value="/action/user/addresses/create/{userId}", method=RequestMethod.POST)
	public HunterAddress createTaskUserAddress(@PathVariable("userId")Long userId, @RequestBody HunterAddress hunterAddress ){
		return hunterAddress;
	}
	
	@Produces("application/json")
	@ResponseBody 
	@RequestMapping(value="/action/user/addresses/update/{userId}", method=RequestMethod.POST)
	public HunterAddress updateTaskUserAddress(@PathVariable("userId")Long userId, @RequestBody HunterAddress hunterAddress ){
		return hunterAddress;
	}
	
	@Produces("application/json")
	@Consumes("application/json") 
	@RequestMapping(value="/action/user/roles/getAll", method=RequestMethod.POST)
	public @ResponseBody List<UserRole> getAllUserRoles(HttpServletRequest request){
		List<UserRole> userRoles = userRoleDao.getAllUserRole();
		return userRoles;
	}
	
	@Produces("application/json")
	@Consumes("application/json") 
	@RequestMapping(value="/action/user/roles/remove", method=RequestMethod.POST)
	public @ResponseBody String removeRoleFromUser(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		JSONObject requestBodyJ = null;
		try {
			String requestBody = HunterUtility.getRequestBodyAsString(request);
			requestBodyJ = new JSONObject(requestBody);
			Long userId = HunterUtility.getLongFromObject(HunterUtility.getNullOrValFromJSONObj(requestBodyJ, "userId"));
			Long userRoleId = HunterUtility.getLongFromObject(HunterUtility.getNullOrValFromJSONObj(requestBodyJ, "userRoleId"));
			String message = userRoleDao.removeRoleFromUser(userId, userRoleId);
			if(message == null){
				return HunterUtility.setJSONObjectForSuccess(jsonObject, "Successfully removed role.").toString();
			}else{
				logger.debug(message); 
				return HunterUtility.setJSONObjectForFailure(jsonObject, message).toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return HunterUtility.setJSONObjectForFailure(jsonObject, "Application error occurred!").toString();
		} catch (IOException e) {
			e.printStackTrace();
			return HunterUtility.setJSONObjectForFailure(jsonObject, "Application error occurred!").toString();
		}
	}
	
	@Produces("application/json")
	@Consumes("application/json") 
	@RequestMapping(value="/action/user/roles/add", method=RequestMethod.POST)
	public @ResponseBody String addRoleToUser(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		JSONObject requestBodyJ = null;
		try {
			String requestBody = HunterUtility.getRequestBodyAsString(request);
			requestBodyJ = new JSONObject(requestBody);
			Long userId = HunterUtility.getLongFromObject(HunterUtility.getNullOrValFromJSONObj(requestBodyJ, "userId"));
			Long userRoleId = HunterUtility.getLongFromObject(HunterUtility.getNullOrValFromJSONObj(requestBodyJ, "userRoleId"));
			String message = userRoleDao.addRoleToUser(userId, userRoleId);
			if(message == null){
				return HunterUtility.setJSONObjectForSuccess(jsonObject, "Successfully added role.").toString();
			}else{
				logger.debug(message); 
				return HunterUtility.setJSONObjectForFailure(jsonObject, message).toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return HunterUtility.setJSONObjectForFailure(jsonObject, "Application error occurred!").toString();
		} catch (IOException e) {
			e.printStackTrace();
			return HunterUtility.setJSONObjectForFailure(jsonObject, "Application error occurred!").toString();
		}
	}
	
	@Produces("application/json")
	@Consumes("application/json") 
	@RequestMapping(value="/action/user/roles/get/{userId}", method=RequestMethod.POST)
	public @ResponseBody List<UserRole> getRolesForUser(@PathVariable("userId") Long userId){
		List<UserRole> userRoles = userRoleDao.getUserRolesForUserId(userId);
		return userRoles;
	}
	
	@RequestMapping(value="/action/fieldProfile", method=RequestMethod.GET)
	public String goToFieldProfile(HttpServletResponse response){
		HunterRawReceiverUserDao userDao = HunterDaoFactory.getObject(HunterRawReceiverUserDao.class);
		HunterRawReceiverUser rawReceiverUser = userDao.getRawUserByUserName(getUserName());
		if(rawReceiverUser == null){
			return "views/fieldProfileNotFound";
		}
		return "views/fieldProfile";
	}
	
	@RequestMapping(value="/action/fieldSample", method=RequestMethod.GET)
	public String goToSample(){
		return "views/fieldSample";
	}
	
	@RequestMapping(value="/action/raw/pagination", method=RequestMethod.GET)
	public String toToPagination(){
		return "views/pagination";
	}
	
	@RequestMapping(value="/action/admin/main", method=RequestMethod.GET)
	public String toAdminMain(){
		return "views/adminMain";
	}
	
	@RequestMapping(value="/action/roles/create", method=RequestMethod.POST)
	@ResponseBody
	@Produces("application/json") 
	public UserRole createUserRole(@RequestBody UserRole userRole){
		 userRoleDao.createUserRole(userRole);
		 return userRole;
	}
	
	@RequestMapping(value="/action/roles/read", method=RequestMethod.POST)
	@ResponseBody
	@Produces("application/json") 
	public List<UserRole> getAllUserRoles(){
		 List<UserRole> userRoles = userRoleDao.getAllUserRole();
		return userRoles;
	}
	
	@RequestMapping(value="/action/roles/edit", method=RequestMethod.POST)
	@ResponseBody
	@Produces("application/json")
	@Consumes("application/json") 
	public UserRole editUserRole(@RequestBody UserRole userRole){
		 userRoleDao.editUserRole(userRole);
		return userRole;
	}
	
	@RequestMapping(value="/action/roles/delete", method=RequestMethod.POST)
	@ResponseBody
	@Produces("application/json")
	@Consumes("application/json") 
	public String deleteUserRole(@RequestBody Map<String,String> params){
		 Long userRoleId = HunterUtility.getLongFromObject(params.get("userRoleId")); 
		 UserRole userRole = userRoleDao.getUserRoleById(userRoleId);
		 JSONObject json = new JSONObject();
		 String message = userRoleDao.deleteUserRole(userRole);
		 if( HunterUtility.notNullNotEmpty(message) ){ 
			 HunterUtility.setJSONObjectForFailure(json, message);
		 }else{
			 HunterUtility.setJSONObjectForSuccess(json, "Successfully deleted user role ( " + userRole.getRoleShortName() + " )"); 
		 }
		return json.toString();
	}
	
	@RequestMapping(value="/action/clients/delete", method=RequestMethod.POST)
	@ResponseBody
	@Produces("application/json")
	@Consumes("application/json") 
	public String deleteHunterClient(@RequestBody Map<String,String> params){
		 @SuppressWarnings("unused")
		Long hunterClientId = HunterUtility.getLongFromObject(params.get("hunterClientId")); 
		 JSONObject resultsObj = new JSONObject();
		 return resultsObj.toString();
	}
	
	@RequestMapping(value="/action/clients/read", method=RequestMethod.POST)
	@ResponseBody
	@Produces("application/json")
	@Consumes("application/json") 
	public List<HunterClientJson> getAllHunterClients(){
		List<HunterClientJson> hunterClientJsons = HunterClientConverter.getInstance().getAllHunterCientJsons();
		return hunterClientJsons;
	}
	
	@RequestMapping(value="/action/clients/update", method=RequestMethod.POST)
	@ResponseBody
	@Produces("application/json")
	@Consumes("application/json") 
	public HunterClientJson updateHunterClient(@RequestBody HunterClientJson hunterClientJson){
		HunterClientConverter.getInstance().updateHunterClient(hunterClientJson, getAuditInfo());
		return hunterClientJson;
	}
	
	@RequestMapping(value="/action/messageAttachments/read", method=RequestMethod.POST)
	@ResponseBody
	@Produces("application/json")
	@Consumes("application/json") 
	public List<MessageAttachmentBeanJson> getMessageAttachments(){
		MessageAttachmentBeanDao messageAttachmentBeanDao = HunterDaoFactory.getObject(MessageAttachmentBeanDao.class);
		return messageAttachmentBeanDao.getAllAttachmentBeansJson();
	}
	
	@RequestMapping(value="/action/messageAttachments/create", method=RequestMethod.POST)
	public String createNewMessageAttachment(MultipartHttpServletRequest request){
		
		boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
		
		if(!isMultiPart){
			logger.warn("Request is not mult-part. Returning null workbook!!"); 
			return null;
		}

		MessageAttachmentBean messageAttachmentBean = new MessageAttachmentBean();
		BufferedImage image = null;
		InputStream inputStream = null;
		
		try {
			
			MultipartFile multipartFile = request.getFile("messageAttachmentFiles");
			inputStream = multipartFile.getInputStream();
			
			String attachmentName = request.getParameter("attachmentName");
			String attachmentDesc = request.getParameter("attachmentDesc");
			String originalFileName = multipartFile.getOriginalFilename();
			String format = originalFileName.substring(originalFileName.lastIndexOf(".")+1, originalFileName.length());
			
			messageAttachmentBean.setBeanDesc(attachmentDesc);
			messageAttachmentBean.setBeanName(attachmentName);
			messageAttachmentBean.setOriginalFileName(originalFileName);
			messageAttachmentBean.setFileFormat(format); 
			messageAttachmentBean.setAuditInfo(getAuditInfo()); 
			
			if(inputStream != null){
				
				image = ImageIO.read(inputStream);
				messageAttachmentBean.setFileHeight(image.getHeight());
				messageAttachmentBean.setFileWidth(image.getWidth());

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write( image, messageAttachmentBean.getFileFormat(), baos );
				baos.flush();
				byte[] bytes = baos.toByteArray();
				baos.close();
				
				Blob blob = new SerialBlob(bytes);
				messageAttachmentBean.setFileBlob(blob);
				
				messageAttachmentBean.setFileSize(bytes.length); 
				
			}
			
			HunterDaoFactory.getObject(MessageAttachmentBeanDao.class).createMessageAttachmentBean(messageAttachmentBean);
			
			logger.debug("Finished extracting image from request!");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		logger.warn("Image extraction from request failed. Returning empty array!!"); 
		request.setAttribute("msgAttchmentImprtStatus", "Success");
		return "redirect:/admin/action/admin/main";
	}
	
	@RequestMapping(value="/action/messageAttachments/delete", method=RequestMethod.POST)
	@Consumes("application/json")
	@Produces("application/json")
	public @ResponseBody String deleteNewMessageAttachment(@RequestBody Map<String,Object> params){
		
		JSONObject json = new JSONObject();
		Long msgAttachmentId = HunterUtility.getLongFromObject(params.get("attachmentId"));
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("p_attachment_id", HunterUtility.getLongFromObject(params.get("attachmentId"))); 
		Map<String, Object> msgIdsMap = get_msg_ids_usng_attchmnt_id.execute_(inParams);
		
		String[] msgIds = null;
		Object[] msgIdsHolder = null;
		Set<String> sets = new HashSet<>();
		
		if( msgIdsMap != null && msgIdsMap.get("msg_ids") != null && !msgIdsMap.get("msg_ids").toString().equalsIgnoreCase("null") ){
			logger.debug("Attachment is being used by messages : " + HunterUtility.stringifyMap(msgIdsMap));   
			msgIds = String.valueOf(msgIdsMap.get("msg_ids")).split(",");
			sets.addAll(Arrays.asList(msgIds));
			msgIdsHolder = sets.toArray();
			String message = "Attachment cannot be deleted because it is </br> being used by messages ( " + HunterUtility.getCommaDelimitedStrings(msgIdsHolder) + " )";
			json = HunterUtility.setJSONObjectForFailure(json, message);
		}else{
			logger.debug("No message ");
			MessageAttachmentBeanDao msgAttmntBnDao = HunterDaoFactory.getObject(MessageAttachmentBeanDao.class);
			msgAttmntBnDao.deleteMessageAttachmentBeanById(msgAttachmentId);
			json = HunterUtility.setJSONObjectForSuccess(json, "Message attachment deleted successfully");
		}
		
		return json.toString();
	}
	
	@RequestMapping(value="/action/task/uncomplete/{taskIds}", method=RequestMethod.GET)
	@Consumes("application/json")
	@Produces("application/json")
	public @ResponseBody String uncompleteTaskIds(@PathVariable("taskIds") String taskIds){
		
		JSONObject jsonObject = new JSONObject();
		String[] taskIdsArray = null;
		TaskManager taskManager = HunterDaoFactory.getObject(TaskManager.class); 
		TaskHistoryDao taskHistoryDao = HunterDaoFactory.getObject(TaskHistoryDao.class);
		
		try{
			
			taskIdsArray = taskIds != null ? taskIds.split(",") : new String[0];
			if( taskIdsArray.length == 0 ){
				jsonObject = HunterUtility.setJSONObjectForFailure(jsonObject, "Task ids are required. ( "+ taskIds +" )!"); 
				return jsonObject.toString();
			}
			
			for(String taskId : taskIdsArray){
				if(!HunterUtility.isNumeric(taskId)){
					String message =  "Error. Task ids provided are invalid ( " + HunterUtility.getCommaDelimitedStrings(taskIdsArray) + " )";  
					jsonObject = HunterUtility.setJSONObjectForFailure(jsonObject, message);
					return jsonObject.toString();
				}
			}
			
			HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
			String updateQuery = hunterJDBCExecutor.getQueryForSqlId("uncompleteTask");
			updateQuery = updateQuery.replaceAll("\\?", taskIds);
			hunterJDBCExecutor.executeUpdate(updateQuery, null);
			
			for(String taskId : taskIdsArray){
				TaskHistory taskHistory = taskManager.getNewTaskHistoryForEventName(HunterUtility.getLongFromObject(taskId), TaskHistoryEventEnum.UNCOMPLETE.getEventName(), getUserName()); 
				taskManager.setTaskHistoryStatusAndMessage(taskHistory,HunterConstants.STATUS_SUCCESS,"Successfully uncompleted task");
				taskHistoryDao.insertTaskHistory(taskHistory); 
			}
			
			logger.debug("Successfully updated tasks. Uncompleting messages.... "); 
			
			Map<String,Object> params = new HashMap<>();
			params.put(":tskIds", taskIds.trim());
			String tskDetailsQ = hunterJDBCExecutor.getReplacedAllColonedParamsQuery("getTskIdsAndMsgTypes", params);
			List<Map<String, Object>> rowMapList = hunterJDBCExecutor.executeQueryRowMap(tskDetailsQ, null);
			
			for(Map<String,Object> rowMap : rowMapList){
				Long tskId = HunterUtility.getLongFromObject(rowMap.get("TSK_ID"));
				String msgTable = HunterUtility.getStringOrNullOfObj(rowMap.get("TSK_MSG_TYP"));
				if(taskIds != null && msgTable != null){
					params.clear();
					params.put(":msgTable", msgTable);
					params.put(":lstUpdatedBy", HunterUtility.singleQuote(getUserName())); 
					params.put(":tskId", tskId+"");
					updateQuery = hunterJDBCExecutor.getReplacedAllColonedParamsQuery("rawQueryForUncompleteTaskMessage", params);
					hunterJDBCExecutor.executeUpdate(updateQuery, null);
				}
			}
			
			logger.debug("Successfully uncompleted task messages!!");  
			
			jsonObject = HunterUtility.setJSONObjectForSuccess(jsonObject, "Sucessfully uncompleted task(s) " + taskIds);
			
			return jsonObject.toString();
			
		}catch(Exception e){
			
			for(String taskId : taskIdsArray){
				TaskHistory taskHistory = taskManager.getNewTaskHistoryForEventName(HunterUtility.getLongFromObject(taskId), TaskHistoryEventEnum.UNCOMPLETE.getEventName(), getUserName()); 
				taskManager.setTaskHistoryStatusAndMessage(taskHistory,HunterConstants.STATUS_FAILED,"Failed to uncomplete task." + e.getMessage());
				taskHistoryDao.insertTaskHistory(taskHistory); 
			}
			
			String message = e.getMessage();
			jsonObject = HunterUtility.setJSONObjectForFailure(jsonObject, message);
			return jsonObject.toString();
		}
	}
	
	
	@RequestMapping(value="/action/raw/validateReceivers", method=RequestMethod.GET)
	@Consumes("application/json")
	@Produces("application/json")
	public String validateRawReceivers(HttpServletRequest request){
		return "views/validateRawReceivers";
	}
	
	@RequestMapping(value="/action/raw/validate/getAvailableRawReceiverUsers", method=RequestMethod.POST)
	@Consumes("application/json")
	@Produces("application/json")
	public @ResponseBody List<RawUsersDropdownJson> getAvailableRawReceiverUsers(HttpServletRequest request){
		
		List<RawUsersDropdownJson> usersDropdownJsons = new ArrayList<>();
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
		String query = hunterJDBCExecutor.getQueryForSqlId("getAvailableRawReceiverUsers");
		Map<Integer, List<Object>> rowListMap = hunterJDBCExecutor.executeQueryRowList(query, null);
		
		if( HunterUtility.isMapNotEmpty( rowListMap ) ) {
			for(Map.Entry<Integer, List<Object>> rowList : rowListMap.entrySet()){ 
				List<Object> row = rowList.getValue();
				String userFullName = HunterUtility.getStringOrNullOfObj( row.get(0) );
				Long userId = HunterUtility.getLongFromObject( row.get(1) );
				RawUsersDropdownJson rawUsersDropdownJson = new RawUsersDropdownJson();
				rawUsersDropdownJson.setUserFullName(userFullName);
				rawUsersDropdownJson.setUserId(userId);
				usersDropdownJsons.add(rawUsersDropdownJson);
			}
		}
		
		return usersDropdownJsons;		
	}
	
	
	@RequestMapping(value="/action/raw/validate/getRegionDataForDropdowns", method=RequestMethod.POST)
	@Consumes("application/json")
	@Produces("application/json")
	public @ResponseBody List<RegionJsonForDropdowns> getRegionDataForDropdowns(@RequestBody Map<String,Object> params){
		
		List<RegionJsonForDropdowns> regionJsons = new ArrayList<>();
		String regionLevel = HunterUtility.getStringOrNullOfObj( params.get("regionLevel") );
		Long regionId = HunterUtility.getLongFromObject( params.get("forRegionId") );
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject( HunterJDBCExecutor.class );
		
		/* currentMode is utilized only for social regions while editing it. 
		 * It just couldn't work on client side so we came here :( 
		 */
		String currentMode = HunterUtility.getStringOrNullOfObj(params.get("currentFetchType"));
		
		String query = null;
		List<Map<String, Object>> rowMapList = null;
		List<Object> values = new ArrayList<>();
		values.add(regionId);

		
		switch ( regionLevel ) {
		
		case HunterConstants.RECEIVER_LEVEL_COUNTRY: 
			
			query = hunterJDBCExecutor.getQueryForSqlId("getCountryNameAndId");
			rowMapList =  hunterJDBCExecutor.executeQueryRowMap(query, null);
			
			if( HunterUtility.isCollectionNotEmpty(rowMapList) ){ 
				for(Map<String,Object> rowMap : rowMapList){
					RegionJsonForDropdowns regionJson = new RegionJsonForDropdowns();
					regionJson.setRegionId( HunterUtility.getLongFromObject( rowMap.get("COUNTRYID") ) );
					regionJson.setRegionName( HunterUtility.getStringOrNullOfObj( rowMap.get("CNTRY_NAM") ) ); 
					regionJsons.add(regionJson); 
				}
				
				if( currentMode != null ){
					RegionJsonForDropdowns regionJson = new RegionJsonForDropdowns();
					regionJson.setRegionId(0L);
					regionJson.setRegionName(currentMode + "=" + HunterConstants.RECEIVER_LEVEL_COUNTRY);
					regionJsons.add(regionJson); 
				}
			}
			
			
			break;
			
		case HunterConstants.RECEIVER_LEVEL_COUNTY: 
			
			query = hunterJDBCExecutor.getQueryForSqlId("getCountiesNameAndIdForSelCountry");
			rowMapList =  hunterJDBCExecutor.executeQueryRowMap(query, values);
			
			if( HunterUtility.isCollectionNotEmpty(rowMapList) ){ 
				for(Map<String,Object> rowMap : rowMapList){
					RegionJsonForDropdowns regionJson = new RegionJsonForDropdowns();
					regionJson.setRegionId( HunterUtility.getLongFromObject( rowMap.get("COUNTYID") ) );
					regionJson.setRegionName( HunterUtility.getStringOrNullOfObj( rowMap.get("COUNTYNAME") ) ); 
					regionJsons.add(regionJson); 
				}
				
				if( currentMode != null ){
					RegionJsonForDropdowns regionJson = new RegionJsonForDropdowns();
					regionJson.setRegionId(0L);
					regionJson.setRegionName(currentMode + "=" + HunterConstants.RECEIVER_LEVEL_COUNTY); 
					regionJsons.add(regionJson); 
				}
			}
			
			break;
			
		case HunterConstants.RECEIVER_LEVEL_CONSITUENCY: 
			
			query = hunterJDBCExecutor.getQueryForSqlId("getConstituenciesNameAndIdForSelCounty");
			rowMapList =  hunterJDBCExecutor.executeQueryRowMap(query, values);
			
			if( HunterUtility.isCollectionNotEmpty(rowMapList) ){ 
				for(Map<String,Object> rowMap : rowMapList){
					RegionJsonForDropdowns regionJson = new RegionJsonForDropdowns();
					regionJson.setRegionId( HunterUtility.getLongFromObject( rowMap.get("CONSTITUENCY_ID") ) );
					regionJson.setRegionName( HunterUtility.getStringOrNullOfObj( rowMap.get("CONSTITUENCY_NAME") ) ); 
					regionJsons.add(regionJson); 
				}
				
				if( currentMode != null ){
					RegionJsonForDropdowns regionJson = new RegionJsonForDropdowns();
					regionJson.setRegionId(0L);
					regionJson.setRegionName(currentMode + "=" + HunterConstants.RECEIVER_LEVEL_CONSITUENCY); 
					regionJsons.add(regionJson); 
				}
			}
			
			break;
			
		case HunterConstants.RECEIVER_LEVEL_WARD: 
			
			query = hunterJDBCExecutor.getQueryForSqlId("getConstituencyWardsForSelCons");
			rowMapList =  hunterJDBCExecutor.executeQueryRowMap(query, values);
			
			if( HunterUtility.isCollectionNotEmpty(rowMapList) ){ 
				for(Map<String,Object> rowMap : rowMapList){
					RegionJsonForDropdowns regionJson = new RegionJsonForDropdowns();
					regionJson.setRegionId( HunterUtility.getLongFromObject( rowMap.get("WARD_ID") ) );
					regionJson.setRegionName( HunterUtility.getStringOrNullOfObj( rowMap.get("WRD_NAME") ) ); 
					regionJsons.add(regionJson); 
				}
				
				if( currentMode != null ){
					RegionJsonForDropdowns regionJson = new RegionJsonForDropdowns();
					regionJson.setRegionId(0L);
					regionJson.setRegionName(currentMode + "=" + HunterConstants.RECEIVER_LEVEL_WARD); 
					regionJsons.add(regionJson); 
				}
			}
			
			break;

		default:
			break;
		}
		
		
		return regionJsons;
	}
	
	
	
	
	
	
	
}
