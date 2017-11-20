package com.techmaster.hunter.email;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.constants.UIMessageConstants;
import com.techmaster.hunter.obj.beans.HunterEmailTemplateBean;
import com.techmaster.hunter.util.HunterUtility;

public class HunterBusinessEmailService {
	
	private static HunterBusinessEmailService instance;
	private static JavaMailSender javaMailSender;
	private static Logger logger = Logger.getLogger(HunterBusinessEmailService.class);
	private HunterBusinessEmailService(){};
	
	static {
		if(instance == null){
			instance = new HunterBusinessEmailService();
		}
	}
	
	public static void injectStaticBeans(JavaMailSender javaMailSender){
		if(javaMailSender == null){
			throw new IllegalArgumentException("Java mail bean can't ben null!!");
		}
		HunterBusinessEmailService.javaMailSender = javaMailSender;
		logger.debug("Successfully wired static java mail sender bean!!"); 
	}
	public static HunterBusinessEmailService getInstance(){
		return instance;
	}
	
	private boolean validateTemplateName(String templateName){
		if(templateName == null) return false;
		for(String inTemplateName : HunterCacheUtil.getInstance().getExistentEmailTemplateNames()){
			if(inTemplateName.equals(templateName))
				return true;
		}
		return false;
	}
	
	private boolean validateMailType(String mailType){
		if(mailType == null) return false;
		for(String inMailType : HunterConstants.MAIL_TYPES_NAMES){
			if(inMailType.equals(mailType))
				return true;
		}
		return false;
	}
	
	public String send(String mailType, Map<String, Object> cntntParams, String templateName){
		
		if(mailType == null || cntntParams == null || templateName == null){
			String message = "Task and mail type cannot be null!!";
			logger.debug(message); 
			return message; 
		}
		
		if(!validateTemplateName(templateName)){
			String message =  HunterCacheUtil.getInstance().getUIMsgTxtForMsgId(UIMessageConstants.MSG_REGION_001);
			String messageDesc =  HunterCacheUtil.getInstance().getUIMsgDescForMsgId(UIMessageConstants.MSG_REGION_001);
			logger.debug(messageDesc);
			logger.debug(message);
			return message;
		}else if(!validateMailType(mailType)){ 
			String message =  HunterCacheUtil.getInstance().getUIMsgTxtForMsgId(UIMessageConstants.EMAIL_MSG_002);
			String messageDesc =  HunterCacheUtil.getInstance().getUIMsgDescForMsgId(UIMessageConstants.EMAIL_MSG_002);
			logger.debug(messageDesc);
			logger.debug(message);
			return message;
		}
		logger.debug("Preparing to send email with the template : " + templateName); 
		HunterEmailTemplateBean hunterEmailTemplateBean = HunterCacheUtil.getInstance().getEmailTemplateBean(templateName);
		
		if(hunterEmailTemplateBean == null){
			String message = "Cannot find templatebean with the name ( " + templateName + " ) in the cache.";
			logger.debug(message);
			return message;
		}
		
		try{
			MimeMessageHelper messageHelper = getMimeMsgHelperForMailType(mailType, hunterEmailTemplateBean, cntntParams);
			MimeMessage mimeMessage = messageHelper.getMimeMessage();
			javaMailSender.send(mimeMessage);
			return HunterConstants.STATUS_SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public void setReceivers(MimeMessageHelper mimeMessageHelper, String receiversStr, String delimiter, String receiverType) throws MessagingException{
		boolean allNotEmpty = HunterUtility.notNullNotEmpty(receiversStr) && HunterUtility.notNullNotEmpty(delimiter);
		if( mimeMessageHelper != null && allNotEmpty){
			String[] receivers = receiversStr.split(delimiter);
			if(receivers != null && receivers.length > 0){
				for(String receiver : receivers){
					if(receiverType.equals(HunterConstants.TO_LIST)){
						mimeMessageHelper.addTo(receiver);
					}else if(receiverType.equals(HunterConstants.TO_LIST)){
						mimeMessageHelper.addCc(receiver); 
					}else if(receiverType.equals(HunterConstants.FROM_EMAIL)){
						mimeMessageHelper.setFrom(receiver); 
					}
					
				}
			}
		}else{
			logger.debug("Either mieMessageHelper, to String or delimiter is null!!");
		}
	}
	
	public String getReceiversStringForType(String type, HunterEmailTemplateBean hunterEmailTemplateBean){
		String receiversStr = null;
		if(type != null && hunterEmailTemplateBean != null){
			if(type.equals(HunterConstants.TO_LIST)){
				receiversStr = hunterEmailTemplateBean.getToList();
			}else if(type.equals(HunterConstants.CC_LIST)){
				receiversStr = hunterEmailTemplateBean.getCcList();
			}else if(type.equals(HunterConstants.FROM_EMAIL)){
				receiversStr = hunterEmailTemplateBean.getFromList();
			}
		}
		logger.debug("Receivers string : " + receiversStr); 
		return receiversStr;
		
	}
	
	public void setReceivers(MimeMessageHelper mimeMessageHelper, HunterEmailTemplateBean hunterEmailTemplateBean, String delimiter, String receiverType) throws MessagingException{
		String receiverStr = getReceiversStringForType(receiverType, hunterEmailTemplateBean);
		logger.debug("Receiver string : " + receiverStr); 
		setReceivers(mimeMessageHelper, receiverStr, delimiter, receiverType); 
	}
	
	public MimeMessageHelper getMimeMsgHelperForMailType(String mailType, HunterEmailTemplateBean hunterEmailTemplateBean, Map<String, Object> cntntParams ) throws MessagingException{
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
		
		setReceivers(messageHelper, hunterEmailTemplateBean, ",",HunterConstants.TO_LIST);
		setReceivers(messageHelper, hunterEmailTemplateBean, ",",HunterConstants.CC_LIST);
		setReceivers(messageHelper, hunterEmailTemplateBean, ",",HunterConstants.FROM_EMAIL);
		setReceivers(messageHelper, cntntParams.get(HunterConstants.TO_LIST) == null ? null : cntntParams.get(HunterConstants.TO_LIST).toString(), ",",HunterConstants.TO_LIST);  
		setReceivers(messageHelper, cntntParams.get(HunterConstants.CC_LIST) == null ? null : cntntParams.get(HunterConstants.CC_LIST).toString(), ",",HunterConstants.TO_LIST);
		
		messageHelper.setSubject(hunterEmailTemplateBean.getSubject()); 
		
		String replacedContent = getReadyEmailContents(hunterEmailTemplateBean, cntntParams);
		if(replacedContent == null || replacedContent.equals("")){
			throw new IllegalArgumentException("Replaced email content is null or empty!!"); 
		}
		mimeMessage.setContent(replacedContent, hunterEmailTemplateBean.getContentType());
		return messageHelper;
	}
	
	/**
	 * 
	 * @param mailType
	 * @param hunterEmailTemplateBean
	 * @param cntntParams : The keys must be the keys in the template!!
	 */
	public String getReadyEmailContents(HunterEmailTemplateBean hunterEmailTemplateBean, Map<String, Object> cntntParams){
		String content = hunterEmailTemplateBean.getTemplateContent();
		if(cntntParams != null && !cntntParams.isEmpty()){
			for(Map.Entry<String, Object> entry : cntntParams.entrySet()){
				String key = entry.getKey();
				String value = entry.getValue()+"";
				content = content.replaceAll(key, value);
			}
		}
		return content;
	}
	
	
	
}
