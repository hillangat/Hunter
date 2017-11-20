package com.techmaster.hunter.gateway.beans;

public class HunterEmailClientHelper {
	
	private static HunterEmailClientHelper instance;
	static{
		if(instance == null){
			synchronized (HunterEmailClientHelper.class) {
				instance = new HunterEmailClientHelper();
			}
		}
	}
	private HunterEmailClientHelper(){};
	public static HunterEmailClientHelper getInstance(){
		return instance;
	}

}
