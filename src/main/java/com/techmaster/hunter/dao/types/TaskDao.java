package com.techmaster.hunter.dao.types;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.techmaster.hunter.json.ReceiverGroupJson;
import com.techmaster.hunter.json.TaskAngular;
import com.techmaster.hunter.obj.beans.Task;

public interface TaskDao {
	
	public Long getNextTaskId();
	public void insertTask(Task task);
	public void update(Task task);
	public void updateTaskStatus(Long taskId, String toStatus, String updatedBy);
	public void insertTasks(List<Task> tasks);
	public Task getTaskById(Long taskId);
	public List<Task> getTasksForClientUserName(String userName);
	public List<Task> getTaskForClientId(Long clientId);
	public String getTaskMsgType(Long taskId);
	public void deleteTask(Task task);
	public String getUserNameForTaskOwnerId(Long taskId);
	public Set<ReceiverGroupJson> getTaskReceiverGroups(Long taskId);
	public List<String> getCmmSprtdTskNamsFrUsrNam(String userName);
	public void updateTaskDelStatus(Long taskId, String toStatus, String updateBy);
	public Map<String,String> getTaskStatuses(Long taskId);
	public List<TaskAngular> getAllAngularTasks();
	public List<TaskAngular> getClientAngularTasks( Long clientId );
	public Map<String, Object> getTaskFurnishments( Long taskId );
	
	

}
