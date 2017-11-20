package com.techmaster.hunter.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.dao.proc.ProcedureHandler;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.MessageAttachmentBeanDao;
import com.techmaster.hunter.json.MessageAttachmentBeanJson;
import com.techmaster.hunter.obj.beans.MessageAttachmentBean;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterUtility;

public class MessageAttachmentBeanDaoImpl implements MessageAttachmentBeanDao{
	
	private Logger logger = Logger.getLogger(getClass());
	@Autowired private ProcedureHandler get_generated_cid;
	@Autowired private HunterHibernateHelper hunterHibernateHelper;

	@Override
	public void createMessageAttachmentBean(MessageAttachmentBean attachmentBean) {
		logger.debug("Creating message attachment..."); 
		hunterHibernateHelper.saveEntity(attachmentBean);
		Map<String,Object> executeParams = new HashMap<String, Object>();
		executeParams.put("bean_id", attachmentBean.getBeanId());
		Map<String, Object> execute = get_generated_cid.execute(executeParams);
		attachmentBean.setCid(HunterUtility.getStringOrNullOfObj(execute.get("p_bean_cid")));
		hunterHibernateHelper.updateEntity(attachmentBean);
		logger.debug(HunterUtility.stringifyMap(execute));
		logger.debug("Done creating message attachment!!");
	}

	@Override
	public MessageAttachmentBean getAttachmentBeanById(Long beanId) {
		logger.debug("Creating message attachment..."); 
		MessageAttachmentBean messageAttachmentBean = hunterHibernateHelper.getEntityById(beanId, MessageAttachmentBean.class);
		logger.debug("Done creating message attachment!!");
		return messageAttachmentBean;
	}

	@Override
	public List<MessageAttachmentBean> getAllAttachmentBeans() {
		logger.debug("Getting all attchment beans..."); 
		List<MessageAttachmentBean> messageAttachmentBeans = hunterHibernateHelper.getAllEntities(MessageAttachmentBean.class);
		logger.debug("Don getting all attachment beans. Size("+ messageAttachmentBeans.size() +")");
		return messageAttachmentBeans;
	}
	
	private MessageAttachmentBeanJson createMsgAttchmentJsnForMap(Map<String, Object> map){
		MessageAttachmentBeanJson attachmentBeanJson = new MessageAttachmentBeanJson();
		 attachmentBeanJson.setBeanId(HunterUtility.getLongFromObject(map.get("BEAN_ID"))); 
		 attachmentBeanJson.setBeanDesc(HunterUtility.getStringOrNullOfObj(map.get("BEAN_DESC"))); 
		 attachmentBeanJson.setBeanName(HunterUtility.getStringOrNullOfObj(map.get("BEAN_NAME"))); 
		 attachmentBeanJson.setCreatedBy(HunterUtility.getStringOrNullOfObj(map.get("CRTD_BY"))); 
		 attachmentBeanJson.setCretDate(HunterUtility.getStringOrNullOfObj(map.get("CRET_DATE"))); 
		 attachmentBeanJson.setFileFormat(HunterUtility.getStringOrNullOfObj(map.get("FLE_FRMT"))); 
		 attachmentBeanJson.setFileHeight(Integer.parseInt(HunterUtility.getStringOrNullOfObj(map.get("FLE_HGHT"))));
		 attachmentBeanJson.setFileWidth(Integer.parseInt(HunterUtility.getStringOrNullOfObj(map.get("FLE_WDTH")))); 
		 attachmentBeanJson.setFileSize(Integer.parseInt(HunterUtility.getStringOrNullOfObj(map.get("FLE_SZE")))); 
		 attachmentBeanJson.setLastUpdate(HunterUtility.getStringOrNullOfObj(map.get("LST_UPDT_DATE"))); 
		 attachmentBeanJson.setLastUpdatedBy(HunterUtility.getStringOrNullOfObj(map.get("LST_UPDTD_BY"))); 
		 attachmentBeanJson.setOriginalFileName(HunterUtility.getStringOrNullOfObj(map.get("ORGNL_FL_NAM")));
		 attachmentBeanJson.setCid(HunterUtility.getStringOrNullOfObj(map.get("CID")));
		 return attachmentBeanJson;
	}

	@Override
	public List<MessageAttachmentBeanJson> getAllAttachmentBeansJson() {
		 logger.debug("Getting all attachment beans jsons..."); 
		 List<MessageAttachmentBeanJson> messageAttachmentBeanJsons = new ArrayList<MessageAttachmentBeanJson>();
		 HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
		 String query = hunterJDBCExecutor.getQueryForSqlId("getAllMessageAttachmentJson");
		 List<Map<String, Object>> rowMapList = hunterJDBCExecutor.executeQueryRowMap(query, null);
		 if( rowMapList != null && !rowMapList.isEmpty() ){
			 for(Map<String,Object> map : rowMapList){
				 MessageAttachmentBeanJson attachmentBeanJson = createMsgAttchmentJsnForMap(map);
				 messageAttachmentBeanJsons.add(attachmentBeanJson);
			 }
		 }
		 logger.debug("Finished getting all attachment beans jsons ( " + messageAttachmentBeanJsons.size() +" )"); 
		 return messageAttachmentBeanJsons;
	}
	
	@Override
	public MessageAttachmentBeanJson getAttachmentBeanJsonById(Long attachmentId) {
		logger.debug("Getting all attachment bean json for id : " + attachmentId); 
		 HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
		 String query = hunterJDBCExecutor.getQueryForSqlId("getMessageAttachmentJsonForId");
		 List<Object> values = new ArrayList<>();
		 values.add(attachmentId);
		 List<Map<String, Object>> rowMapList = hunterJDBCExecutor.executeQueryRowMap(query, values);
		 MessageAttachmentBeanJson attachmentBeanJson = null;
		 if( rowMapList != null && !rowMapList.isEmpty() ){
			 for(Map<String,Object> map : rowMapList){
				 attachmentBeanJson = createMsgAttchmentJsnForMap(map);
				 break;
			 }
		 }
		 logger.debug("Finished getting attachment bean json ( " + attachmentBeanJson +" )"); 
		 return attachmentBeanJson;
	}

	@Override
	public void deleteMessageAttachmentBeanById(Long beanId) {
		logger.debug("Deleting message attachment bean id!"); 
		MessageAttachmentBean messageAttachmentBean = hunterHibernateHelper.getEntityById(beanId, MessageAttachmentBean.class);
		hunterHibernateHelper.deleteEntity(messageAttachmentBean);
		logger.debug("Successfully deleted message attachment for bean id : " + beanId); 
	}

	@Override
	public void deleteMessageAttachmentBean(MessageAttachmentBean attachmentBean) {
		logger.debug("Deleting message attachment"); 
		hunterHibernateHelper.deleteEntity(attachmentBean);
		logger.debug("Successfully deleted message attachment");
	}

	@Override
	public List<MessageAttachmentBean> getAttachmentBeanByIds(List<Long> attachmentIds) {
		String commaSeprtd = HunterUtility.getCommaDelimitedStrings(attachmentIds);
		String readyQuery = "FROM MessageAttachmentBean m WHERE m.beanId IN ( " + commaSeprtd + " )";
		logger.debug("Executing query : " + readyQuery); 
		List<MessageAttachmentBean> attachmentBeans = hunterHibernateHelper.executeQueryForObjList(MessageAttachmentBean.class, readyQuery);
		logger.debug("Loaded attachment beans, size = " + attachmentBeans.size());
		return attachmentBeans;
	}
	
	

}
