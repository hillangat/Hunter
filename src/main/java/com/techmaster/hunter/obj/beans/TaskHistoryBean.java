package com.techmaster.hunter.obj.beans;

import java.util.HashSet;
import java.util.Set;

public class TaskHistoryBean {
	
	Set<TaskHistory> taskHistoriesSet = new HashSet<TaskHistory>();
	private Task task;

	public TaskHistoryBean(Task task) {
		super();
		this.task = task;
	}

	public Set<TaskHistory> getTaskHistoriesSet() {
		return taskHistoriesSet;
	}

	public void setTaskHistoriesSet(Set<TaskHistory> taskHistoriesSet) {
		this.taskHistoriesSet = taskHistoriesSet;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
}
