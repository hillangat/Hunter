package com.techmaster.hunter.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.techmaster.hunter.dao.impl.HunterMessageReceiverDaoImpl;
import com.techmaster.hunter.dao.types.HunterMessageReceiverDao;
import com.techmaster.hunter.obj.beans.HunterMessageReceiver;
import com.techmaster.hunter.util.HunterLogFactory;
import com.techmaster.hunter.util.HunterUtility;

public class GeneralTest {
	
	private static final Logger logger = Logger.getLogger(GeneralTest.class);
	
	public static void testQueryExecutor(ResultSet resultset){
		
		
		/*String query = hunterJDBCExecutor.getQueryForSqlId("testingJDBCExec");
		List<Object> values = new ArrayList<>();
		values.add(16L);
		ResultSet rs = hunterJDBCExecutor.executeQuery(query, values);
		GeneralTest.testQueryExecutor(rs);*/
		
		if(resultset != null){
			try {
				if(resultset.next()){
					Object obj1 = resultset.getObject(1);
					Object obj2 = resultset.getObject(2);
					Object obj3 = resultset.getObject(3);
					Object obj4 = resultset.getObject(4);
					HunterLogFactory.getLog(GeneralTest.class).debug("obj1 : " + obj1 + ", : ," + "obj2 : " + obj2 + ", : ," + "obj3 : " + obj3 + ", : ," + "obj43 : " + obj4 );
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void testHunterMessageReceiver(){
		HunterMessageReceiverDao hunterMessageReceiverDao = new HunterMessageReceiverDaoImpl();
		List<HunterMessageReceiver> hunterMessageReceivers = hunterMessageReceiverDao.getAllHunterMessageReceivers(); 
		logger.debug("Successfully loaded hunterMessageReceivers >> " + HunterUtility.stringifyList(hunterMessageReceivers)); 
	}
	
	public static void main(String[] args) {
		
		testHunterMessageReceiver();
		
	}
	

}
