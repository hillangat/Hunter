package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.obj.beans.TaskMessageReceiver;

public interface TaskMessageReceiverDao {

	public Long getNextHunterMessageReceiver();
	public  void insertTaskMessageReceiver(TaskMessageReceiver  taskMessageReceiver);
	public void updateTaskMessageReceiver(TaskMessageReceiver  taskMessageReceiver);
	public void deleteTaskMessageReceiver(TaskMessageReceiver  taskMessageReceiver);
	public void delteTaskMessageReceiverById(Long id);
	public TaskMessageReceiver getTaskMessageReceiverById(Long id);
	public List<TaskMessageReceiver> getAllTaskMessageReceivers();
	public List<TaskMessageReceiver> getTaskMessageReceiversForTask(Task task);
	
	
}
