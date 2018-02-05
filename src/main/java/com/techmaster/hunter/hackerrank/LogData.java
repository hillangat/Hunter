package com.techmaster.hunter.hackerrank;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class LogData {
	
	private static Timer timer = new Timer();
	private static final String LOG_AND_CLEAR = "logAndClear";
	private static final String LOG_ONLY = "logOnly";
	private static Map<String, String> logDetails = Collections.synchronizedMap( new HashMap<String , String>() );
	private static TimerTask logTask = new TimerTask() {
		@Override
		public void run() {
			//synchronized(logDetails) {
				System.out.println( "Loggin data to db and clearing it : " + logDetails.size() );
				logDetails.clear();
			//}
		}
	};
	private static TimerTask clientTask = new TimerTask() {
		@Override
		public void run() {
			//synchronized(logDetails) {
				String key = Long.toString(System.currentTimeMillis());
				String date = new Date().toString();
				System.out.println(key + " successfully logged at : " + key);
				logDetails.put(key, date );
			//}
		}
	};
	
	public static void main(String[] args) {
		System.out.println( returnTyp() );
	}
	
	public static void test() {
		Timer timer = new Timer(true);
		SolutionTask task1 = new SolutionTask(LOG_ONLY);
		timer.schedule(task1, 1, 200);
		SolutionTask task2 = new SolutionTask(LOG_AND_CLEAR);
		timer.schedule(task2, 10, 10000);
	}
	
	@SuppressWarnings("finally")
	public static double returnTyp() {
		try {
			return 2.0;
		} catch (Exception e) {
			e.printStackTrace();
			return 2.1;
		}
	}
	
	static class SolutionTask extends TimerTask {
		private String type = null;
		public SolutionTask(String type) {
			super();
			this.type = type;
		}
		@Override
		public void run() {
			if ( type.equals(LOG_ONLY) ) {
				String key = Long.toString(System.currentTimeMillis());
				String date = new Date().toString();
				System.out.println(key + " successfully logged at : " + key);
				logDetails.put(key, date );
			} else {
				System.out.println( "Loggin data to db and clearing it : " + logDetails.size() );
				logDetails.clear();
			}
		}
	}

}
