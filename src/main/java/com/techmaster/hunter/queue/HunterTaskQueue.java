package com.techmaster.hunter.queue;

import java.util.LinkedList;
import java.util.Queue;

import com.techmaster.hunter.obj.beans.Task;

public class HunterTaskQueue {

	private static HunterTaskQueue instance;
	private static Queue<Task> hunterTaskQueue = new LinkedList<>();
	
	static{
		if(instance == null){
			instance = new HunterTaskQueue();
		}
	}
	
	public static HunterTaskQueue getInstance(){
		return instance;
	}
	
	private HunterTaskQueue(){}; // private constructor.
	
	public boolean add(Task task){
		return hunterTaskQueue.add(task);
	}
	
	public boolean offer(Task task){
		return hunterTaskQueue.offer(task);
	}
	
	public Object remove(){
		return hunterTaskQueue.remove();
	}
	
	public Object poll(){
		return hunterTaskQueue.poll();
	}
	
	public Object element(){
		return hunterTaskQueue.element();
	}
	
	public Object peek(){
		return hunterTaskQueue.peek();
	}
	
}
