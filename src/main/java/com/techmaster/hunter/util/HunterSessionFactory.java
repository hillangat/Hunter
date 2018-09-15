package com.techmaster.hunter.util;


import org.hibernate.SessionFactory;

import com.techmaster.hunter.dao.impl.HunterDaoFactory;

public class HunterSessionFactory {

	private static SessionFactory sessionFactory;
	private static HunterSessionFactory hunterSessionFactory;
	
	
	private HunterSessionFactory() {
		super();
	}
	

	public SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	public static HunterSessionFactory getInstance(){
		if( hunterSessionFactory == null ){			
			synchronized (HunterDaoFactory.class) {
				hunterSessionFactory = new HunterSessionFactory();
			}
		}
		return hunterSessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		HunterSessionFactory.sessionFactory = sessionFactory;
	}


	public static HunterSessionFactory getHunterSessionFactory() {
		return hunterSessionFactory;
	}


	public static void setHunterSessionFactory(HunterSessionFactory hunterSessionFactory) {
		HunterSessionFactory.hunterSessionFactory = hunterSessionFactory;
	}
	
	
	
	
	
	
}
