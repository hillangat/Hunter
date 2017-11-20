package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.json.MessageAttachmentBeanJson;
import com.techmaster.hunter.obj.beans.MessageAttachmentBean;

public interface MessageAttachmentBeanDao {
	
	public void createMessageAttachmentBean(MessageAttachmentBean attachmentBean);
	public MessageAttachmentBean getAttachmentBeanById(Long beanId);
	public List<MessageAttachmentBean> getAllAttachmentBeans();
	public List<MessageAttachmentBeanJson> getAllAttachmentBeansJson();
	public MessageAttachmentBeanJson getAttachmentBeanJsonById(Long attachmentId);
	public void deleteMessageAttachmentBeanById(Long beanId);
	public void deleteMessageAttachmentBean(MessageAttachmentBean attachmentBean);
	public List<MessageAttachmentBean> getAttachmentBeanByIds(List<Long> attachmentIds);

}
