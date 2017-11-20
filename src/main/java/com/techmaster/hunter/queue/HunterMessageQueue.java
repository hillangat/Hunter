package com.techmaster.hunter.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.techmaster.hunter.obj.beans.Message;

public class HunterMessageQueue implements HunterQueueService<Message> {

	private static Queue<Message> hunterQueue = new LinkedList<>();

	@Override
	public boolean add(Message q) {
		return hunterQueue.add(q); 
	}

	@Override
	public boolean offer(Message q) {
		return hunterQueue.offer(q); 
	}

	@Override
	public Message remove() {
		return hunterQueue.remove();
	}

	@Override
	public Message poll() {
		return hunterQueue.poll();
	}

	@Override
	public Message element() {
		return hunterQueue.element();
	}

	@Override
	public Message peek() {
		return hunterQueue.peek();
	}

	@Override
	public int size() {
		return hunterQueue.size();
	}

	@Override
	public boolean addAll(Collection<Message> messages) {
		return hunterQueue.addAll(messages); 
	}

	@Override
	public void clear() {
		hunterQueue.clear();;
	}

	@Override
	public boolean contains(Message q) {
		return hunterQueue.contains(q); 
	}

	@Override
	public boolean containsAll(Collection<Message> messages) {
		return hunterQueue.containsAll(messages); 
	}

	@Override
	public boolean isEmpty() {
		return hunterQueue.isEmpty();
	}

	@Override
	public Iterator<Message> iterator() {
		return hunterQueue.iterator();
	}

	@Override
	public Message remove(Message q) {
		return hunterQueue.remove();
	}

	@Override
	public boolean removeAll(Collection<Message> messages) {
		return removeAll(messages);
	}

	@Override
	public void retainAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Message[] toArray() {
		return (Message[]) hunterQueue.toArray();
	}

	@Override
	public Message[] toArray(Message[] q) {
		return hunterQueue.toArray(q);
	}
	
	
	
	
	
}
