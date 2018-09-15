package com.techmaster.hunter.obj.angulate;

import java.util.ArrayList;
import java.util.List;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.json.TaskAngular;
import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterUtility;

public class TaskAngulater {
	
	public List<TaskAngular> getAllAngularTasks(){
		List<TaskAngular> angularTasks = new ArrayList<>();		
		List<Task> tasks = HunterDaoFactory.getObject(HunterHibernateHelper.class).getAllEntities(Task.class); 
		for( Task task : tasks ){
			TaskAngular angularTask = new TaskAngular();
			angularTask.setTaskId(Long.toString(task.getTaskId()));
			angularTask.setTaskName(task.getTaskName());
			angularTask.setDescription(task.getDescription()); 
			angularTask.setClientId(Long.toString(task.getClientId()));
			angularTask.setTaskType(task.getTaskType()); 
			angularTask.setTaskObjective(task.getTaskObjective()); 
			angularTask.setDescription(task.getDescription());
			angularTask.setTskAgrmntLoc(task.getTskAgrmntLoc());
			angularTask.setTskMsgType(task.getTskMsgType());
			angularTask.setTaskBudget(Float.toString(task.getTaskBudget()));
			angularTask.setTaskCost(Float.toString(task.getTaskCost()));
			angularTask.setRecurrentTask(Boolean.toString(task.isRecurrentTask())); 
			angularTask.setTaskLifeStatus(task.getTaskLifeStatus());
			angularTask.setCretDate( HunterUtility.formatDate(task.getCretDate(), HunterConstants.DATE_FORMAT_STRING) );
			angularTask.setTaskDeliveryStatus(task.getTaskDeliveryStatus());
			angularTasks.add(angularTask);			
			
			
		}
		return angularTasks;
	}

}
