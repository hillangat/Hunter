package com.techmaster.hunter.util;

public class HunterLogFactory {
	
	public static org.apache.log4j.Logger getLog(Class<?> clss){
		return org.apache.log4j.Logger.getLogger(clss);
	}

}
