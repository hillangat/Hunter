package com.techmaster.hunter.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.dao.types.TaskMessageReceiverDao;
import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.obj.beans.TaskMessageReceiver;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterLogFactory;

public class TaskMessageReceiverDaoImpl implements TaskMessageReceiverDao {
	
	private static final Logger logger = HunterLogFactory.getLog(TaskMessageReceiverDaoImpl.class);
	
	
	@Autowired private HunterHibernateHelper hunterHibernateHelper;

	@Override
	public Long getNextHunterMessageReceiver() {
		logger.debug("Fetching next id for taskMessageReceiver..."); 
		Long id = hunterHibernateHelper.getMaxEntityIdAsNumber(TaskMessageReceiver.class, Long.class, "receiverId");
		logger.debug("Successfully obtained max id >> " + id); 
		id = id == null ? 1 : id;
		logger.debug("TaskMessageReceiver next id = " + id); 
		return id;
	}

	@Override
	public void insertTaskMessageReceiver( TaskMessageReceiver taskMessageReceiver) {
		logger.debug("Inserting taskMessageReceiver..."); 
		hunterHibernateHelper.saveEntity(taskMessageReceiver);
		logger.debug("Done inserting taskMessageReceiver");  
	}

	@Override
	public List<TaskMessageReceiver> getAllTaskMessageReceivers() {
		logger.debug("Fetching all taskMessageReceivers..."); 
		List<TaskMessageReceiver> taskMessageReceivers = hunterHibernateHelper.getAllEntities(TaskMessageReceiver.class);
		logger.debug("Done fetching all taskMessageReceivers!! Size (" + taskMessageReceivers.size() +")");
		return taskMessageReceivers;
	}

	@Override
	public List<TaskMessageReceiver> getTaskMessageReceiversForTask(Task task) {
		logger.debug("Fetching all taskMessageReceivers..."); 
		String readyQuery = "FROM Task t WHERE t.taskId = '" + task.getTaskId() + "'"; 
		logger.info("executing query >> " + readyQuery); 
		List<TaskMessageReceiver> taskMessageReceivers = hunterHibernateHelper.executeQueryForObjList(TaskMessageReceiver.class, readyQuery);
		logger.debug("Done fetching all taskMessageReceivers!! Size (" + taskMessageReceivers.size() +")");
		return taskMessageReceivers;
	}

	@Override
	public void updateTaskMessageReceiver( TaskMessageReceiver taskMessageReceiver) {
		logger.debug("Upadating taskMessageReceiver..."); 
		hunterHibernateHelper.updateEntity(taskMessageReceiver); 
		logger.debug("Done updating taskMessageReceiver");  		
	}

	@Override
	public void deleteTaskMessageReceiver( TaskMessageReceiver taskMessageReceiver) {
		logger.debug("Deleting taskMessageReceiver..."); 
		hunterHibernateHelper.deleteEntity(taskMessageReceiver);  
		logger.debug("Done deleting taskMessageReceiver");  
	}

	@Override
	public void delteTaskMessageReceiverById(Long id) {
		logger.debug("Deleting taskMessageReceiver for id >> " + id); 
		TaskMessageReceiver taskMessageReceiver = hunterHibernateHelper.getEntityById(id, TaskMessageReceiver.class); 
		hunterHibernateHelper.deleteEntity(taskMessageReceiver);
		logger.debug("Done deleting taskMessageReceiver");  
	}

	@Override
	public TaskMessageReceiver getTaskMessageReceiverById(Long id) {
		logger.debug("Fetching taskMessageReceiver by id >> " + id); 
		TaskMessageReceiver taskMessageReceiver = hunterHibernateHelper.getEntityById(id, TaskMessageReceiver.class); 
		logger.debug("Done fetching taskMessageReceiver");
		return taskMessageReceiver;
	}

}
