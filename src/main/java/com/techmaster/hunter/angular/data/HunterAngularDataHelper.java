package com.techmaster.hunter.angular.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.constants.HunterDaoConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.TaskDao;
import com.techmaster.hunter.json.TaskAngular;
import com.techmaster.hunter.util.HunterQueryToBeanMapper;
import com.techmaster.hunter.util.HunterUtility;
import com.techmaster.hunter.xml.XMLService;

public class HunterAngularDataHelper {
	
	private static volatile HunterAngularDataHelper instance;	
	
	static {
		if ( instance == null ) {
			synchronized (HunterAngularDataHelper.class) {
				instance = new HunterAngularDataHelper();
			}
		}
	}
	
	/**
	 * Prevent initialization
	 */
	private HunterAngularDataHelper() {  }
	
	
	public static HunterAngularDataHelper getIntance() {
		return instance;
	}


	public String getJsonStr( String key ) {
		XMLService gridHeaderService = HunterCacheUtil.getInstance().getXMLService(HunterConstants.ANGULAR_HEADERS_CONFIG_CACHED_SERVICE);
		if ( null != gridHeaderService ) {
			String json = gridHeaderService.getCData( getPath( key ) );
			return anEscape( json );
		}
		return null;
	}
	
	public List<HunterTableConfig> getHeadersBeans( String key ) {
		List<HunterTableConfig> tableConfigs = new ArrayList<>();
		String jsons = this.getJsonStr(key);
		if ( null != jsons ) {
			JSONArray array = new JSONArray( jsons );
			for ( int i = 0; i < array.length(); i++ ) {
				JSONObject obj = array.getJSONObject(i);
				HunterTableConfig config = new HunterTableConfig();				
				config.setSortable(obj.getBoolean("sortable"));
				config.setShow(obj.getBoolean("show"));
				config.setCheckBox(obj.getBoolean("checkBox"));
				config.setCurrentOrder(obj.getBoolean("currentOrder"));
				config.setActionCol(obj.getBoolean("actionCol"));
				config.setIndex(obj.getInt("index"));
				config.setHeaderId(getStr( obj, "headerId"));
				config.setDataId(getStr( obj, "dataId"));
				config.setDisplayName(getStr( obj, "displayName"));
				config.setBootstrapIconName(getStr( obj, "bootstrapIconName"));
				config.setDataType(getStr( obj, "dataType"));
				config.setActionColIconName(getStr( obj, "actionColIconName"));
				config.setWidth(getStr( obj, "width"));
				tableConfigs.add( config );
			}
		}
		return tableConfigs;
	}
	
	public AngularData getDataBean( Object data, String headersKey ) {
		AngularData angularData = new AngularData();
		try {
			angularData.setData(data);
			angularData.setHeaders( null != headersKey ? getHeadersBeans(headersKey) : null );
			angularData.setMessage(null);
			angularData.setStatus(HunterConstants.STATUS_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			angularData.setMessage( "Application error occurred." );
			angularData.setStatus(HunterConstants.STATUS_FAILED);
		}
		return angularData;
	}
	
	public <T> AngularData getBeanForQuery( Class<T> clss, String queryId, List<Object> values, String headersKey ) {
		AngularData angularData = new AngularData();
		try {
			List<T> data = HunterQueryToBeanMapper.getInstance().map(clss, queryId, values);
			return getDataBean(data, headersKey);
		} catch (Exception e) {
			e.printStackTrace();
			angularData.setMessage( "Application error occurred." );
			angularData.setStatus(HunterConstants.STATUS_FAILED);
		}
		return angularData;
	}
	
	public AngularData getBeanForMsgAndSts( String message, String status ) {
		AngularData angularData = new AngularData();
		try {
			angularData.setMessage( message );
			angularData.setStatus( status );
		} catch (Exception e) {
			e.printStackTrace();
			angularData.setMessage( "Application error occurred." );
			angularData.setStatus(HunterConstants.STATUS_FAILED);
		}
		return angularData;
	}
	
	public AngularData getTestingTaskPayload() {
		return getDataBean(getTestingTasks(), HunterDaoConstants.TASK_GRID_HEADERS);
	}
	
	private List<TaskAngular> getTestingTasks() {
		TaskDao taskDao = HunterDaoFactory.getDaoObject(TaskDao.class);
		List<TaskAngular> tasks = taskDao.getAllAngularTasks();
		return tasks;
	}
	
	private String anEscape( String content ) {
		return null != content ? content.replaceAll("&#91;", "[").replaceAll("&#93;", "]") : null;
	}
	
	private String getPath( String gridName ) {
		return "headers/header[@name=\""+ gridName +"\"]";
	}
	
	private String getStr( JSONObject obj, String key ) {
		return HunterUtility.getStringOrNulFromJSONObj(obj, key);
	}

}
