package com.techmaster.hunter.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.dao.types.HunterImportBeanDao;
import com.techmaster.hunter.imports.beans.HunterImportBean;
import com.techmaster.hunter.imports.beans.ImportHelper;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterSessionFactory;

public class HunterImportBeanDaoImpl implements HunterImportBeanDao{
	
	private Logger logger = Logger.getLogger(HunterImportBean.class);
	private HunterHibernateHelper hunterHibernateHelper;
	private HunterSessionFactory hunterSessionFactory;

	@Override
	public void insertHunterImportBean(HunterImportBean hunterImportBean) {
		logger.debug("Inserting import bean : " + hunterImportBean.getOriginalFileName());
		hunterHibernateHelper.saveEntity(hunterImportBean);
		logger.debug("Finished saving import bean"); 
	}

	@Override
	public void insertHunterImportBeanUsngJDBC(HunterImportBean hunterImportBean) {
		logger.debug("Saving import bean using jdbc...");
		SessionFactory sessionFactory = hunterSessionFactory.getSessionFactory();
		Session session = sessionFactory.openSession();
		ImportHelper.createImportBeanBlobAndSave(hunterImportBean, session);
		logger.debug("Finished saving import bean using jdbc!"); 
	}

	@Override
	public HunterImportBean getHunterImportBeanById(Long importId) {
		logger.debug("Getting import bean by id : " + importId);
		HunterImportBean hunterImportBean = hunterHibernateHelper.getEntityById(importId, HunterImportBean.class);
		logger.debug("Successfully obtained import bean : " + hunterImportBean.toString());
		return hunterImportBean;
	}

	@Override
	public List<HunterImportBean> getImportBeanByBeanName(String beanName) {
		logger.debug("Getting import bean by bean name : " + beanName); 
		String query = "FROM HunterImportBean h WHERE h.beanName = '" + beanName + "'";
		List<HunterImportBean> hunterImportBeans = hunterHibernateHelper.executeQueryForObjList(HunterImportBean.class, query);
		logger.debug("Successfully obtained import beans size( " + hunterImportBeans.size() + " )");
		return hunterImportBeans;
		
	}

	@Override
	public List<HunterImportBean> getImportBeanByCrtdUserName(String userName) {
		logger.debug("Getting import bean by user name : " + userName); 
		String query = "FROM HunterImportBean h WHERE h.auditInfo.createdBy = '" + userName + "'";
		List<HunterImportBean> hunterImportBeans = hunterHibernateHelper.executeQueryForObjList(HunterImportBean.class, query);
		logger.debug("Successfully obtained import beans size( " + hunterImportBeans.size() + " )");
		return hunterImportBeans;
	}

	@Override
	public void updateHunterImportBean(HunterImportBean update) {
		logger.debug("Updating import bean : " + update); 
		hunterHibernateHelper.updateEntity(update);
		logger.debug("Finished updating import bean!"); 
	}

	@Override
	public void deleteHunterImportBean(HunterImportBean hunterImportBean) {
		logger.debug("Deleting import bean : " + hunterImportBean);
		hunterHibernateHelper.deleteEntity(hunterImportBean);
		logger.debug("Finished deleting import bean"); 
	}

	@Override
	public void deleteHunterImportById(Long importId) {
		logger.debug("Deleting import bean of id : " + importId);
		HunterImportBean hunterImportBean = hunterHibernateHelper.getEntityById(importId, HunterImportBean.class);
		hunterHibernateHelper.deleteEntity(hunterImportBean); 
		logger.debug("Finished deleting import bean : " + hunterImportBean); 
	}

}
