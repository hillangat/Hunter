package com.techmaster.hunter.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class HunterQueue implements HunterQueueService<Object>{ 
	
	private static HunterQueue instance;
	private static Queue<Object> hunterQueue = new LinkedList<>();
	
	static{
		if(instance == null){
			instance = new HunterQueue();
		}
	}
	
	public static HunterQueue getInstance(){
		return instance;
	}
	
	private HunterQueue(){} // private constructor.

	@Override
	public boolean add(Object q) {
		return hunterQueue.add(q);
	}

	@Override
	public boolean offer(Object q) {
		return hunterQueue.offer(q);
	}

	@Override
	public Object remove() {
		return hunterQueue.remove();
	}

	@Override
	public Object poll() {
		return hunterQueue.poll();
	}

	@Override
	public Object element() {
		return null;
	}

	@Override
	public Object peek() {
		return hunterQueue.peek();
	}

	@Override
	public int size() {
		return hunterQueue.size();
	}

	@Override
	public boolean addAll(Collection<Object> objects) {
		return false;
	}

	@Override
	public void clear() {
		hunterQueue.clear();
	}

	@Override
	public boolean contains(Object q) {
		return hunterQueue.contains(q); 
	}

	@Override
	public boolean containsAll(Collection<Object> messages) {
		return hunterQueue.containsAll(messages);
	}

	@Override
	public boolean isEmpty() {
		return hunterQueue.isEmpty();
	}

	@Override
	public Iterator<Object> iterator() {
		return hunterQueue.iterator();
	}

	@Override
	public Object remove(Object q) {
		return hunterQueue.remove();
	}

	@Override
	public boolean removeAll(Collection<Object> objects) {
		return hunterQueue.removeAll(objects);
	}

	@Override
	public void retainAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] toArray() {
		return hunterQueue.toArray();
	}

	@Override
	public Object[] toArray(Object [] q) {
		return hunterQueue.toArray(q); 
	}; 
	
	
	
	

}
