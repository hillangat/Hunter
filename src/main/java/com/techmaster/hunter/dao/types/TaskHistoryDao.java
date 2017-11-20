package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.obj.beans.TaskHistory;

public interface TaskHistoryDao {
	
	public void insertTaskHistory(TaskHistory taskHistory);
	public void insertTaskHistories(List<TaskHistory> taskHistory); 
	public List<TaskHistory> getTaskHistoriesByTaskId(Long taskId);
	public void deleteTaskHistoriesByTaskId(Long taskId);
	

}
