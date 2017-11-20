package com.techmaster.hunter.email;

import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.MimeMessageHelper;

import com.techmaster.hunter.obj.beans.HunterEmailTemplateBean;
import com.techmaster.hunter.obj.beans.Task;

public interface HunterEmailManager {

	public void send(Task task, String mailType) throws MessagingException;
	public MimeMessageHelper getMimeMsgHelperForMailType(String mailType, HunterEmailTemplateBean hunterEmailTemplateBean, Map<String, Object> cntntParams );
	 
}
