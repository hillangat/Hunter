package com.techmaster.hunter.util;


import com.techmaster.hunter.dao.impl.HunterDaoFactory;

public class HunterSessionFactory {

	private static org.hibernate.SessionFactory sessionFactory;
	private static HunterSessionFactory hunterSessionFactory;
	
	
	private HunterSessionFactory() {
		super();
	}
	

	public org.hibernate.SessionFactory getSessionFactory(){
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


	public void setSessionFactory(org.hibernate.SessionFactory sessionFactory) {
		HunterSessionFactory.sessionFactory = sessionFactory;
	}


	public static HunterSessionFactory getHunterSessionFactory() {
		return hunterSessionFactory;
	}


	public static void setHunterSessionFactory(HunterSessionFactory hunterSessionFactory) {
		HunterSessionFactory.hunterSessionFactory = hunterSessionFactory;
	}
	
	
	
	
	
	
}
