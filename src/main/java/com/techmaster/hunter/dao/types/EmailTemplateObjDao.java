package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.json.EmailTemplateObjJson;
import com.techmaster.hunter.obj.beans.EmailTemplateObj;

public interface EmailTemplateObjDao {
	
	public void insertTemplateObj(EmailTemplateObj templateObj);
	public EmailTemplateObj getTemplateObjById(Long templateId);
	public List<EmailTemplateObj> getAllTemplateObjs();
	public List<EmailTemplateObj> getApprovedTemplateObjs();
	public String deleteEmailTemplateObj(EmailTemplateObj emailTemplateObj);
	public String deleteEmailTemplateObjById(Long templateId);
	public List<EmailTemplateObjJson> getAllEmailTemplateObjJsons();
	public void updateEmailTemplateObj(EmailTemplateObj emailTemplateObj);
	public EmailTemplateObjJson getEmailTemplateJsonById(Long templateId);

}
