package com.techmaster.hunter.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.constants.HunterURLConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.obj.beans.QueryToBeanMapperField;
import com.techmaster.hunter.xml.XMLService;

public class HunterQueryToBeanMapper {
	
	private static Logger logger = Logger.getLogger(HunterQueryToBeanMapper.class);
	private HunterQueryToBeanMapper() {}
	private static HunterQueryToBeanMapper instance; 
	static {
		if ( instance == null ) {
			synchronized (HunterQueryToBeanMapper.class) {
				instance = new HunterQueryToBeanMapper();
			}
		}
	}
	
	public static HunterQueryToBeanMapper getInstance() {
		return instance;
	}
	
	public <T>List<T> map( Class<T> clzz, String queryId, List<Object> valueList ){	
		
		String mapId = clzz.getSimpleName();
		logger.debug("Mapping for query ID: " + queryId);		
		
		HunterJDBCExecutor executor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
		String query = executor.getQueryForSqlId(queryId);
		XMLService xmlService = HunterCacheUtil.getInstance().getXMLService(HunterConstants.QUERY_TO_BEAN_MAPPER);
		
		if ( HunterUtility.notNullNotEmpty(query) ) {
			logger.debug("Retrieved query: " + query);			
			if ( HunterUtility.notNullNotEmpty( xmlService ) ) {
				List<Map<String, Object>> rowMapList = executor.executeQueryRowMap(query, valueList);
				List<QueryToBeanMapperField> mapperFields = getMapperFields(xmlService, mapId);
				if ( HunterUtility.isCollectionNotEmpty(rowMapList) && HunterUtility.isCollectionNotEmpty(mapperFields) ) {
					List<T> returnList = new ArrayList<>();
					for( Map<String, Object> rowMap : rowMapList ) {
						T t = getBean( clzz, xmlService, mapId, query, valueList, mapperFields, rowMap );
						// logger.debug("Object contructed >>> " + t.toString());
						returnList.add(t);
					}
					return returnList;
				} else {
					logger.warn( "No data found for the query: " + query + "\n and values: " + HunterUtility.stringifyList(valueList) ); 
				}
			} else {
				logger.error("No XML service found for query to bean mapper : " + HunterURLConstants.QUERY_TO_BEAN_MAPPER ); 
			}
			
		} else {
			logger.error( "No query found for query ID: " + queryId );
		}
		
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private <T>T getBean( Class<T> clzz, XMLService xmlService, String mapId, String query, List<Object> valueList, List<QueryToBeanMapperField> mapperFields, Map<String, Object> rowMap ) { 
		try {
			Object obj = clzz.newInstance();
			for ( QueryToBeanMapperField mapperField : mapperFields ) {
				Object dbValue = rowMap.get(mapperField.getDbField() );
				dbValue = mapperField.isYesNo() ? HunterUtility.getBooleanForYN(dbValue.toString()) : dbValue;
				this.setFieldValue(clzz, obj, mapperField, dbValue); 
			}
			return (T) obj;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<QueryToBeanMapperField> getMapperFields( XMLService xmlService, String mapId ) {
		String xPath = "maps/map[@id='" + mapId + "']/field";
		NodeList fields = xmlService.getNodeListForPathUsingJavax(xPath);
		if ( fields != null && fields.getLength() > 0 ) {
			List<QueryToBeanMapperField> mapperFields = new ArrayList<>();
			for ( int i=0; i<fields.getLength(); i++ ) {
				Node fieldNode = fields.item(i);
				String dbName = fieldNode.getAttributes().getNamedItem("dbName").getTextContent();
				String fieldName = fieldNode.getAttributes().getNamedItem("fieldName").getTextContent();
				String type = fieldNode.getAttributes().getNamedItem("type").getTextContent();
				boolean yesNo = Boolean.valueOf(fieldNode.getAttributes().getNamedItem("yesNo").getTextContent());
				mapperFields.add( new QueryToBeanMapperField(mapId, dbName, fieldName, yesNo, type) );
			}
			if ( HunterUtility.isCollectionNotEmpty(mapperFields) ) {
				return mapperFields;
			} else {
				logger.debug("No mapping fields found for mapId = " + mapId);
			}
		} else {
			logger.debug("No fields found for mapId = " + mapId); 
		}
		return null;
	}
	
	private void setFieldValue( Class<?> clzz, Object obj, QueryToBeanMapperField mapperField, Object fieldVal ) {
		logger.info("Setting value : " + ( fieldVal != null ? fieldVal.toString() : " " )  + " to mapper field : " + mapperField.getFieldName() ); 
		String fieldName = mapperField.getFieldName();
		String setterMethodName = getSetterMethodName(fieldName);
		try {
			Method setterMethod = clzz.getDeclaredMethod(setterMethodName, getTypeParams(mapperField));
			fieldVal = mapperField.isYesNo() ? HunterUtility.getBooleanForYN(fieldVal.toString()) : fieldVal;
			fieldVal = mapperField.getType().equals("java.lang.Long") ? HunterUtility.getLongFromObject(fieldVal) : fieldVal;
			fieldVal = mapperField.getType().equals("java.lang.Float") ? HunterUtility.getFloatFromObject(fieldVal) : fieldVal;
			setterMethod.invoke(obj, fieldVal);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private  Class<?>[] getTypeParams( QueryToBeanMapperField mapperField ) {
		try {
			return new Class[] { Class.forName( mapperField.getType() ) };
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getSetterMethodName( String fieldName ) {
		String methodName = "set";
		String firstChar = fieldName.substring(0, 1).toUpperCase();
		String lastPart = fieldName.substring(1, fieldName.length());		
		methodName += firstChar + lastPart; 
		// logger.debug("Created setter method name : " + methodName);
		return methodName;
	}

}
