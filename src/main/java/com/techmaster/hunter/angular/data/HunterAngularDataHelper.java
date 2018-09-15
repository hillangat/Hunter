package com.techmaster.hunter.angular.data;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

	public String getPropertyVal( NodeList properties, String propKey ) {
		if ( HunterUtility.isNodeListNotEmptpy(properties) ) {
			for( int i = 0; i < properties.getLength(); i++ ) {
				Node property = properties.item(i);
				if ( property != null && !property.getNodeName().equals("#text") ) {
					String key = HunterUtility.getNodeAttr(property, "key", String.class);;
					String value = HunterUtility.getNodeAttr(property, "value", String.class);
					value = "null".equals(value) ? null : value;
					if ( HunterUtility.notNullNotEmptyAndEquals(key, propKey) ) {
						return value;
					}
				}
			}
		}
		return null;
	}
	
	public List<HunterTableConfig> getHeadersBeans( String key ) {
		List<HunterTableConfig> tableConfigs = new ArrayList<>();
		XMLService gridHeaderService = HunterCacheUtil.getInstance().getXMLService(HunterConstants.ANGULAR_HEADERS_CONFIG_CACHED_SERVICE);
		String xPath = "headers/header[@name=\""+ key +"\"]";
		NodeList headers = gridHeaderService.getNodeListForPathUsingJavax(xPath);
		Node header = headers.item(0);
		NodeList fields = header.getChildNodes();
		if ( HunterUtility.isNodeListNotEmptpy(fields) )  {
			for( int i = 0; i < fields.getLength(); i++ ) {
				Node field = fields.item(i);
				if ( !field.getNodeName().equals("#text") ) {
					NodeList properties = field.getChildNodes();
					HunterTableConfig config = new HunterTableConfig();
					config.setSortable( Boolean.valueOf( getPropertyVal(properties, "sortable") ) );
					config.setShow( Boolean.valueOf( getPropertyVal(properties, "show") ) );
					config.setCheckBox( Boolean.valueOf( getPropertyVal(properties, "checkBox") ) );
					config.setCurrentOrder( Boolean.valueOf( getPropertyVal(properties, "currentOrder") ) );
					config.setActionCol( Boolean.valueOf( getPropertyVal(properties, "accctionCol") ) );
					config.setIndex( Integer.parseInt( getPropertyVal(properties, "index")) );
					config.setHeaderId(  getPropertyVal(properties, "headerId") ) ;
					config.setDataId( getPropertyVal(properties, "dataId") );
					config.setDisplayName( getPropertyVal(properties, "displayName") );
					config.setBootstrapIconName( getPropertyVal(properties, "bootstrapIconName") );
					config.setDataType( getPropertyVal(properties, "dataType") );
					config.setActionColIconName( getPropertyVal(properties, "actionColIconName") );
					config.setActionCellType( getPropertyVal(properties, "actionCellType") );
					config.setWidth( getPropertyVal(properties, "width") );
					tableConfigs.add( config );
				}
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
			angularData.setMessage( HunterUtility.getApplicationErrorMessage() );
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
			angularData.setMessage( HunterUtility.getApplicationErrorMessage() );
			angularData.setStatus(HunterConstants.STATUS_FAILED);
		}
		return angularData;
	}
	
	public AngularData getBeanForMsgAndSts( String message, String status, Object data ) {
		AngularData angularData = new AngularData();
		angularData.setData(data);
		try {
			angularData.setMessage( message );
			angularData.setStatus( status );
		} catch (Exception e) {
			e.printStackTrace();
			angularData.setMessage( HunterUtility.getApplicationErrorMessage() );
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

}
