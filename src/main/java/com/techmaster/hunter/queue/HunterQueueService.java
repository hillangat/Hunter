package com.techmaster.hunter.queue;

import java.util.Collection;
import java.util.Iterator;


public interface HunterQueueService<Q> {
	
	/*
	 * Inserts the specified element into this queue 
	 * if it is possible to do so immediately without violating capacity restrictions, 
	 * returning true upon success and throwing an IllegalStateException if no space is currently available.
	 */
	public boolean add(Q q);
	
	/*
	 * Inserts the specified element into this queue if it is possible to do so immediately without violating capacity restrictions.
	 */
	public boolean offer(Q q);
	
	/*
	 * Retrieves and removes the head of this queue.
	 */
	public Q remove();
	
	/*
	 * Retrieves and removes the head of this queue, or returns null if this queue is empty.
	 */
	public Q poll();
	
	/*
	 * Retrieves, but does not remove, the head of this queue.
	 */
	public Q element();
	
	/*
	 * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
	 */
	public Q peek();
	
	/*
	 * Methods inherited from Collection
	 */
	public int size();
	public boolean addAll(Collection<Q> others);
	public void clear(); 
	public boolean contains(Q q); 
	public boolean containsAll(Collection<Q> collection); 
	public boolean equals(Object obj); 
	public int hashCode(); 
	public boolean isEmpty(); 
	public Iterator <Q> iterator(); 
	public Q remove(Q q);
	public boolean removeAll(Collection<Q> collection); 
	public void retainAll(); 
	public Q[] toArray();
	public Q[] toArray(Q[] q);
	
	

}
