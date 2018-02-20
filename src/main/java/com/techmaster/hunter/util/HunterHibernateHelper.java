package com.techmaster.hunter.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

public class HunterHibernateHelper {

	
private static final Logger logger = Logger.getLogger(HunterHibernateHelper.class);

	@Autowired
   	private HunterSessionFactory hunterSessionFactory;
	
	public Session getSession(){
		SessionFactory sessionFactory = hunterSessionFactory.getSessionFactory();
		return sessionFactory.openSession();
	}
	
	public static void rollBack(Transaction trans){
		if(trans != null && trans.isActive())
			trans.rollback();
	}
	
	public static void closeSession(Session session){
		if(session != null && session.isOpen())
			session.close();
	}
	
	public static void closeConnection(Connection conn){
		try {
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getMaxEntityIdAsNumber	(Class<?> clzz, Class<T> idType, String fieldName){
		
		T t = null;
		
		Session session = null;
		Transaction trans = null;
		
		logger.debug("Fetching max entity Id for Entity(" + getEntitySimpleName(clzz) + ")");
		
		try {
			
			session = hunterSessionFactory.getSessionFactory().openSession();
			trans = session.beginTransaction();
			Criteria criteria = session.createCriteria(clzz).setProjection(Projections.max(fieldName));
			t = (T)criteria.uniqueResult();
			closeSession(session);
			HunterLogFactory.getLog(HunterHibernateHelper.class);
			
		} catch (HibernateException e) {
			rollBack(trans); 
			e.printStackTrace();
		}finally{
			closeSession(session); 	
		}
		
		HunterLogFactory.getLog(HunterHibernateHelper.class).debug("Obtained max entity id >> " + t); 
		
		return t;
		
	}
	
	public void saveEntity(Object obj){
		
		SessionFactory sessionFactory = hunterSessionFactory.getSessionFactory();
		Session session = null;
		Transaction trans = null;
		
		logger.debug("Saving Entity(" + getEntitySimpleName(obj.getClass()) + ")");
		
		try {
			
			session = sessionFactory.openSession();
			trans = session.beginTransaction();
			session.saveOrUpdate(obj); 
			trans.commit();
			session.flush();
			closeSession(session);
			
			logger.debug("Successfully saved Entity(" + getEntitySimpleName(obj.getClass()) + ")");
			
		} catch (HibernateException e) {
			logger.debug("Exception saving Entity(" + getEntitySimpleName(obj.getClass()) + ")");
			rollBack(trans); 
			e.printStackTrace();
		}finally{
			closeSession(session); 	
		}
	}
	
	public void saveOrUpdateEntity(Object obj){
		
		SessionFactory sessionFactory = hunterSessionFactory.getSessionFactory();
		Session session = null;
		Transaction trans = null;
		
		logger.debug("Saving or Updating Entity(" + getEntitySimpleName(obj.getClass()) + ")");
		
		try {
			
			session = sessionFactory.openSession();
			trans = session.beginTransaction();
			session.saveOrUpdate(obj); 
			trans.commit();
			session.flush();
			closeSession(session);
			
			logger.debug("Successfully saved Entity(" + getEntitySimpleName(obj.getClass()) + ")");
			
		} catch (HibernateException e) {
			logger.debug("Exception saving Entity(" + getEntitySimpleName(obj.getClass()) + ")");
			rollBack(trans); 
			e.printStackTrace();
		}finally{
			closeSession(session); 	
		}
	}
	
	public void saveOrUpdateEntities(List<?> objects){
		
		SessionFactory sessionFactory = hunterSessionFactory.getSessionFactory();
		Session session = null;
		Transaction trans = null;
		
		Class<?> clzz = objects.get(0).getClass();
		logger.debug("Updating Entity(" + getEntitySimpleName(clzz));
		
		logger.debug("Saving or Updating Entity(" + getEntitySimpleName(clzz) + ")");
		
		try {
			
			session = sessionFactory.openSession();
			trans = session.beginTransaction();
			for(Object obj : objects){
				session.saveOrUpdate(obj); 
			}
			trans.commit();
			session.flush();
			closeSession(session);
			
			logger.debug("Successfully saved Entity(" + getEntitySimpleName(clzz) + ")");
			
		} catch (HibernateException e) {
			logger.debug("Exception saving Entity(" + getEntitySimpleName(clzz) + ")");
			rollBack(trans); 
			e.printStackTrace();
		}finally{
			closeSession(session); 	
		}
	}
	
	public void uppdateEntities(List<?> objs){
		
		SessionFactory sessionFactory = hunterSessionFactory.getSessionFactory();
		Session session = null;
		Transaction trans = null;
		
		logger.debug("Saving or Updating Entities(" + getEntitySimpleName(objs.get(0).getClass()) + ")"); 
		
		try {
			
			session = sessionFactory.openSession();
			trans = session.beginTransaction();
			for(Object obj : objs){
				session.update(obj); 
			}
			trans.commit();
			session.flush();
			closeSession(session);
			
			logger.debug("Successfully saved Entities(" + getEntitySimpleName(objs.get(0).getClass()) + ")");
			
		} catch (HibernateException e) {
			logger.debug("Exception saving Entities(" + getEntitySimpleName(objs.get(0).getClass()) + ")");
			rollBack(trans); 
			e.printStackTrace();
		}finally{
			closeSession(session); 	
		}
	}
	
	public void saveEntities(List<?> objs){
		
		SessionFactory sessionFactory = hunterSessionFactory.getSessionFactory();
		Session session = null;
		Transaction trans = null;
		
		Class<?> clss = null;
		
		if(objs != null && objs.size() > 0){
			clss = objs.get(0).getClass();
		}else{
			return;
		}
		
		logger.debug("Saving Entity(" + getEntitySimpleName(clss) + ")");
		
		try {
			
			session = sessionFactory.openSession();
			trans = session.beginTransaction();
			for(Object obj : objs){
				session.save(obj); 
			}
			trans.commit();
			session.flush();
			closeSession(session);
			
			logger.debug("Successfully saved entities for Entity(" + getEntitySimpleName(clss) + ")");
			
		} catch (HibernateException e) {
			logger.debug("Exception saving entities for Entity(" + getEntitySimpleName(clss) + ")");
			rollBack(trans); 
			e.printStackTrace();
		}finally{
			closeSession(session); 	
		}
	}
	
	
	
	public void updateEntity(Object obj){

		SessionFactory sessionFactory = hunterSessionFactory.getSessionFactory();
		Session session = null;
		Transaction trans = null;
		
		logger.debug("Updating Entity(" + getEntitySimpleName(obj.getClass()) + ")");
		
		try {
			
			session = sessionFactory.openSession();
			trans = session.beginTransaction();
			session.update(obj); 
			trans.commit();
			session.flush();
			closeSession(session);
			
			logger.debug("Finished updating Entity(" + getEntitySimpleName(obj.getClass()) + ")");
			
		} catch (HibernateException e) {
			logger.debug("Exception while updating Entity(" + getEntitySimpleName(obj.getClass()) + ")");
			rollBack(trans); 
			e.printStackTrace();
		}finally{
			closeSession(session); 	
		}
	}
	
	public void updateEntitities(List<?> objects){
		
		if(objects == null || objects.isEmpty()){
			logger.debug("Empty or null list. Returning..."); 
			return;
		}

		SessionFactory sessionFactory = hunterSessionFactory.getSessionFactory();
		Session session = null;
		Transaction trans = null;
		
		Class<?> clzz = objects.get(0).getClass();
		logger.debug("Updating Entity(" + getEntitySimpleName(clzz));
		
		try {
			
			session = sessionFactory.openSession();
			trans = session.beginTransaction();
			for(Object obj : objects){
				session.update(obj); 
			}
			trans.commit();
			closeSession(session);
			
			logger.debug("Finished updating Entity(" + getEntitySimpleName(clzz) + ")");
			
		} catch (HibernateException e) {
			logger.debug("Exception while updating Entity(" + getEntitySimpleName(clzz) + ")");
			rollBack(trans); 
			e.printStackTrace();
		}finally{
			closeSession(session); 	
		}
	}
	
	public void deleteEntityByLongId(Long id, Class<?> clazz){
		Object obj = getEntityById(id, clazz);
		if( obj != null ){
			logger.debug("Entity with id ( "+ id +" ) found. Deleting the entity...");
			deleteEntity(obj);
		}else{
			logger.warn("Cannot find the entity. Entity not deleted!!!!!!!"); 
		}
	}
	
	public void deleteEntity(Object obj){
		
		if( obj == null ) return;

		SessionFactory sessionFactory = hunterSessionFactory.getSessionFactory();
		Session session = null;
		Transaction trans = null;
		
		logger.debug("Deleting entity Entity(" + getEntitySimpleName(obj.getClass()) + ")");
		
		try {
			
			session = sessionFactory.openSession();
			trans = session.beginTransaction();
			session.delete(obj); 
			trans.commit();
			session.flush();
			closeSession(session);
			
			logger.debug("Finished deleting entity Entity(" + getEntitySimpleName(obj.getClass()) + ")");
			
		} catch (HibernateException e) {
			logger.debug("Exception deleting entity Entity(" + getEntitySimpleName(obj.getClass()) + ")");
			rollBack(trans); 
			e.printStackTrace();
		}finally{
			closeSession(session); 	
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getEntityById(Long id, Class<T> clazz){

		SessionFactory sessionFactory = hunterSessionFactory.getSessionFactory();
		Session session = null;
		T t = null;
		
		logger.debug("Fetching entity by id of Entity(" + getEntitySimpleName(clazz) + ")");
		
		try {
			
			session = sessionFactory.openSession();
			t = (T)session.get(clazz, id);
			// just make it a string so it can lazy load.
			if( t != null ){
				@SuppressWarnings("unused")
				String tStr = t.toString();
			}
			closeSession(session);
			
			logger.debug("Finished fetching entity by id of Entity(" + getEntitySimpleName(clazz) + ")");
			
		} catch (HibernateException e) {
			logger.debug("Exception fetching entity by id of Entity(" + getEntitySimpleName(clazz) + ")");
			e.printStackTrace();
		}finally{
			closeSession(session); 	
		}
		
		return t;
	}
	
	
	
	/**
	 * Only <b>hibernate queries</b> are acceptable to this method. <br/>
	 * This method accepts only the query that has parameters already replaced.
	 * @param clzz
	 * @param readyQuery
	 * @return
	 */
	public <T> List<T> executeQueryForObjList(Class<T> clzz, String readyQuery){
		
		
		SessionFactory sessionFactory = hunterSessionFactory.getSessionFactory();
		Session session = null;
		Transaction trans = null;
		
		List<T> entities = new ArrayList<>();
		
		logger.debug("Executing query for objects. Query>>> \n" + readyQuery);
		
		try {
			
			session = sessionFactory.openSession();
			trans = session.beginTransaction();
			
			Query query = session.createQuery(readyQuery);
			
			List<?> list = query.list();
			
			// this will force lazy initialization in case the bean is configured for lazy initialization.
			for(Object obj: list){
				@SuppressWarnings("unchecked") 
				T t = (T)obj;
				@SuppressWarnings("unused") 
				String tStr = t+"";
				entities.add(t);
			}
			
			closeSession(session);
			logger.debug("Successfully executed query for entity( " + getEntitySimpleName(clzz) +" ). Size ( " + entities.size() +")");
			
		} catch (HibernateException e) {
			rollBack(trans); 
			e.printStackTrace();
		}finally{
			closeSession(session); 	
		}
		
		return entities;
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getAllEntities(Class<T> clazz){

		SessionFactory sessionFactory = hunterSessionFactory.getSessionFactory();
		Session session = null;
		List<T> entities = new ArrayList<>();
		
		logger.debug("Fetching all entities for Entity(" + getEntitySimpleName(clazz) + ")");
		
		try {
			
			session = sessionFactory.openSession();
			String clsName = clazz.getSimpleName();
			String queryStr = "From " + clsName;
			Query query = session.createQuery(queryStr);
			List<?> list = query.list();
			for(Object obj : list){
				entities.add((T)obj);
				/*
				 * Stringifying this and doing nothing with the string so hibernate can pull up the children too
				 * This is in case the class has lazy loading.
				 */
				@SuppressWarnings("unused") String objStr = ((T)obj).toString();
			}
			closeSession(session);
			logger.debug("Successfully fetched all entities for Entity(" + getEntitySimpleName(clazz) + ")");
						
		} catch (HibernateException e) {
			logger.debug("Exception while fetching all entities for Entity(" + getEntitySimpleName(clazz) + ")");
			e.printStackTrace();
		}finally{
			closeSession(session); 	
		}
		
		return entities;
	}
	
	public <T> List<T> replaceQueryAndExecuteForList(Class<T> clazz, String query, Map<?, ?> values){
		logger.debug("Query before replacement >> \n" + query); 
		for(Entry<?, ?> entry : values.entrySet()){
			Object key = entry.getKey();
			Object value = entry.getValue();
			query = query.replaceAll(key+"", value+"");
		}
		logger.debug("Query after replacement >> \n" + query); 
		List<T> ts = executeQueryForObjList(clazz, query);
		return ts;
	}
	
	public void executeVoidTransctnlReadyQuery(String query){
		logger.debug("Executing hibernate query : " + query); 
		SessionFactory sessionFactory = hunterSessionFactory.getSessionFactory();
		Session session = null;
		Transaction trans = null;
		
		try {
			session = sessionFactory.openSession();
			trans = session.beginTransaction();
			Query query_ = session.createQuery(query);
			query_.executeUpdate();
			trans.commit();
		} catch (HibernateException e) {
			rollBack(trans); 
			e.printStackTrace();
		}finally{
			closeSession(session); 			
		}
		logger.debug("Finished executing query!"); 
	}
	
	public static String getEntitySimpleName(Class<?> clz){
		String simpleName =  clz.getSimpleName();
		return simpleName;
	}
	
	public List<?>  executeQueryForListWithParams(String queryStr, Map<String, Object> params) {
		logger.debug("Executing query : " + queryStr);
		logger.debug("With params : " + HunterUtility.stringifyMap(params)); 
		Session session = null;
		List<?> list = null;
		try {
			session = hunterSessionFactory.getSessionFactory().getCurrentSession();
			Query query = session.createQuery(queryStr);
			query.setProperties(params);
			list = query.list();
			session.close();
			logger.debug("Successfully retrieved the list. Size ( " + list.size() + " )");  
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			HunterHibernateHelper.closeSession(session);
		}
		return list;
	}
	
	
	public HunterSessionFactory getHunterSessionFactory() {
		return hunterSessionFactory;
	}

	public void setHunterSessionFactory(HunterSessionFactory hunterSessionFactory) {
		this.hunterSessionFactory = hunterSessionFactory;
	}

	public void main(String[] args) {
		
		/*String query = "FROM Task t WHERE t.taskId <= '16'"; 
		List<Task> tasks = executeQueryForObjList(Task.class, query); 
		logger.debug(HunterUtility.stringifyList(tasks));  */
		executeVoidTransctnlReadyQuery("delete TaskHistory where taskId = " + 13); 
		
	}
	
}
