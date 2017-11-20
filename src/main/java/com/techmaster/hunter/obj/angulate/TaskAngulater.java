package com.techmaster.hunter.obj.angulate;

import java.util.ArrayList;
import java.util.List;

import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.TaskDao;
import com.techmaster.hunter.json.TaskAngular;
import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.util.HunterHibernateHelper;

public class TaskAngulater {
	
	public List<TaskAngular> getAllAngularTasks(){
		List<TaskAngular> angularTasks = new ArrayList<>();		
		List<Task> tasks = HunterDaoFactory.getObject(HunterHibernateHelper.class).getAllEntities(Task.class); 
		for( Task task : tasks ){
			TaskAngular angularTask = new TaskAngular();
			angularTask.setTaskId(task.getTaskId());
			angularTask.setTaskName(task.getTaskName());
			angularTask.setDescription(task.getDescription()); 
			angularTask.setClientId(task.getClientId()); 
			angularTask.setTaskType(task.getTaskType()); 
			angularTask.setTaskObjective(task.getTaskObjective()); 
			angularTask.setDescription(task.getDescription());
			angularTask.setTskAgrmntLoc(task.getTskAgrmntLoc());
			angularTask.setTskMsgType(task.getTskMsgType());
			angularTask.setTaskBudget(task.getTaskBudget());
			angularTask.setTaskCost(task.getTaskCost());
			angularTask.setRecurrentTask(task.isRecurrentTask());
			
			
		}
		return angularTasks;
	}

}
