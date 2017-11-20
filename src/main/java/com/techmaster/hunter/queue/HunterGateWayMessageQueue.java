package com.techmaster.hunter.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.techmaster.hunter.obj.beans.GateWayMessage;

public class HunterGateWayMessageQueue implements HunterQueueService<GateWayMessage> {

	private static Queue<GateWayMessage> hunterQueue = new LinkedList<>();
	private static HunterGateWayMessageQueue instance = null;
	private HunterGateWayMessageQueue(){}
	
	static{
		if(instance == null){
			synchronized (HunterGateWayMessageQueue.class) {
				instance = new HunterGateWayMessageQueue();
			}
		}
	}
	
	public static HunterGateWayMessageQueue getInstance(){
		return instance;
	}

	@Override
	public boolean add(GateWayMessage q) {
		return hunterQueue.add(q); 
	}

	@Override
	public boolean offer(GateWayMessage q) {
		return hunterQueue.offer(q); 
	}

	@Override
	public GateWayMessage remove() {
		return hunterQueue.remove();
	}

	@Override
	public GateWayMessage poll() {
		return hunterQueue.poll();
	}

	@Override
	public GateWayMessage element() {
		return hunterQueue.element();
	}

	@Override
	public GateWayMessage peek() {
		return hunterQueue.peek();
	}

	@Override
	public int size() {
		return hunterQueue.size();
	}

	@Override
	public boolean addAll(Collection<GateWayMessage> messages) {
		return hunterQueue.addAll(messages); 
	}

	@Override
	public void clear() {
		hunterQueue.clear();;
	}

	@Override
	public boolean contains(GateWayMessage q) {
		return hunterQueue.contains(q); 
	}

	@Override
	public boolean containsAll(Collection<GateWayMessage> messages) {
		return hunterQueue.containsAll(messages); 
	}

	@Override
	public boolean isEmpty() {
		return hunterQueue.isEmpty();
	}

	@Override
	public Iterator<GateWayMessage> iterator() {
		return hunterQueue.iterator();
	}

	@Override
	public GateWayMessage remove(GateWayMessage q) {
		return hunterQueue.remove();
	}

	@Override
	public boolean removeAll(Collection<GateWayMessage> messages) {
		return removeAll(messages);
	}

	@Override
	public void retainAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GateWayMessage[] toArray() {
		return (GateWayMessage[]) hunterQueue.toArray();
	}

	@Override
	public GateWayMessage[] toArray(GateWayMessage[] q) {
		return hunterQueue.toArray(q);
	}
	
	
	
	
	
}
