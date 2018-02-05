package com.techmaster.hunter.angular.grid;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.ant.FindLeaksTask;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.util.HunterQueryToBeanMapper;
import com.techmaster.hunter.util.HunterUtility;
import com.techmaster.hunter.xml.XMLService;

public class GridQueryHandler {
	
	private static GridQueryHandler instance;
	private GridQueryHandler() {}
	private Logger logger = Logger.getLogger(this.getClass());
	
	static {
		if ( instance == null ) {
			synchronized( GridQueryHandler.class ) {
				instance = new GridQueryHandler();
			}
		}
	}
	
	public <T>List<T> executeQridQueryForRequest( Class<T> clzz, HttpServletRequest request ) {
		String finalQuery = createQueryFromReq( request );
		List<T> ts = HunterQueryToBeanMapper.getInstance().mapForQuery( clzz, finalQuery, null );
		return ts;
	}
	
	public static GridQueryHandler getInstance() {
		return instance;
	}
	
	public String createQueryFromReq( HttpServletRequest request ) {
		GridDataQueryReq req = getReqBeanFromRequest(request);
		String whereAndGroupByClause = createWhereAngGroupClause(req);
		return harmonizeQuery(req, whereAndGroupByClause);
	}
	
	public GridDataQueryReq getReqBeanFromRequest( HttpServletRequest request ) {
		String reqStr = HunterUtility.getRequestBodyAsStringSafely(request);
		JSONObject reqObj = reqStr != null ? new JSONObject( reqStr ) : null;
		return getReqBeanFromJson(reqObj);
	}
	
	public String createWhereAngGroupClause( GridDataQueryReq req ) {
		StringBuilder builder = new StringBuilder();
		if ( req != null ) {
			builder.append(" WHERE ");
			for( int i = 0; req.getFilterBy() != null && i < req.getFilterBy().length; i++ ) {
				GridFieldUserInput filter = req.getFilterBy()[i];
				builder.append(" ").append( GridQueryOperationEnum.getSqlFragment(filter.getFieldAlias(), filter.getDbName(), filter.getUserInput(), filter.getOperation().getUiName() ) ).append(" ");
				if ( i < req.getFilterBy().length - 1 && req.getFilterBy().length > 1 ) {
					builder.append(" AND ");
				}
			}
			if ( HunterUtility.isArrayNotEmpty(req.getOrderBy()) ) {
				builder.append(" ORDER BY ");
				for( int i = 0; req.getOrderBy() != null && i < req.getOrderBy().length; i++  ) {
					GridFieldUserInput orderBy = req.getOrderBy()[i];
					builder.append(orderBy.getFieldAlias()).append(".").append( orderBy.getDbName() ).append(" ").append(orderBy.getDir().getName().toUpperCase() ).append(" ");
					if ( i < req.getOrderBy().length - 1 && req.getOrderBy().length > 1 ) {
						builder.append(", ");
					}
				}
			}
		}
		String whereStr = builder.toString();
		return whereStr;
	}
	
	public String harmonizeQuery( GridDataQueryReq req, String whereAndGroupByClause ) {
		String queryId = getReferenceQueryId( req );
		String query = HunterDaoFactory.getObject(HunterJDBCExecutor.class).getQueryForSqlId( queryId );
		String finalQuery = query + " " + whereAndGroupByClause;
		this.logger.debug("finalQuery >>> " + finalQuery);
		return finalQuery;
	}
	
	private String getReferenceQueryId( GridDataQueryReq req ) {
		NodeList references = getReferences(req);
		if ( references != null && references.getLength() > 0 ) {
			Node reference = references.item(0);
			String queryId = reference.getAttributes().getNamedItem("queryId").getTextContent().toString();
			return queryId;
		}
		return null;
	}
	
	private NodeList getReferences( GridDataQueryReq req ) {
		XMLService mappings = HunterCacheUtil.getInstance().getXMLService(HunterConstants.QUERY_GRID_FIELD_MAPPER);
		String xPath = "mappings/references/reference[@name='"+ req.getReference() + "']";
		NodeList references = mappings.getNodeListForPathUsingJavax(xPath);
		return references;
	}
	
	public GridDataQueryReq setDbNames( GridDataQueryReq req ) {
		NodeList references = getReferences(req);
		if ( references != null &&  references.getLength() > 0 ) {
			for( int i = 0 ; i < references.getLength(); i++ ) {
				Node reference = references.item(i);
				String tableName = reference.getAttributes().getNamedItem("table").toString();
				String alias = reference.getAttributes().getNamedItem("alias").toString();
				this.logger.debug("tableName = " + tableName + ", alias = " + alias);
				NodeList fields = reference.getChildNodes();
				if ( references != null &&  references.getLength() > 0 ) {
					for( int j = 0 ; j < fields.getLength(); j++ ) {
						Node field = fields.item(j);
						if ( !field.getNodeName().equals("#text") ) {
							String uiName = field.getAttributes().getNamedItem("ui").getTextContent().toString();
							String dbName = field.getAttributes().getNamedItem("db").getTextContent().toString();
							for ( GridFieldUserInput filter : req.getFilterBy() ) {
								if ( uiName.equals(filter.getFieldName()) ) {
									filter.setDbName(dbName);
								}
							}
							for ( GridFieldUserInput orderBy : req.getOrderBy() ) {
								if ( uiName.equals(orderBy.getFieldName()) ) {
									orderBy.setDbName(dbName);
								}
							}
						}
					}
				}
			}
		}
		return req;
	}
	
	public GridDataQueryReq getReqBeanFromJson( JSONObject obj ) {
		
		if ( obj == null ) {
			return null;
		}
		
		GridDataQueryReq  req = new GridDataQueryReq();
		
		JSONArray filterByArr = getArray( "filterBy", obj );
		GridFieldUserInput filterBy[] = createUserInputs(filterByArr);
		req.setFilterBy(filterBy);
		
		
		JSONArray orderByArr = getArray( "orderBy", obj );
		GridFieldUserInput orderBy[] = createUserInputs(orderByArr);
		req.setOrderBy(orderBy);
		
		String pageNoStr = getNullOrVal(obj, "pageNo");
		req.setPageNo( Integer.parseInt( pageNoStr != null ? pageNoStr : "0" ) );
		
		String pagSizeStr = getNullOrVal(obj, "pageSize");
		req.setPageSize( Integer.parseInt( pagSizeStr != null ? pagSizeStr : "0" ) );
		
		String reference = getNullOrVal(obj, "reference");
		req.setReference( reference );
		
		req = setFieldAliases(req);
		req = setDbNames(req);
		
		return req;
	}
	
	public GridFieldUserInput[] createUserInputs( JSONArray array ) {
		if ( array != null && array.length() > 0 ) {
			GridFieldUserInput [] inputs = new GridFieldUserInput[array.length()];
			for( int i = 0; i < inputs.length; i++ ) {
				JSONObject inputObj = array.getJSONObject(i);
				GridFieldUserInput input = new GridFieldUserInput();
				input.setDir( GridQueryOrderDirEnum.getEnumForName( getNullOrVal(inputObj, "dir") )  );
				input.setFieldName( getNullOrVal(inputObj, "fieldName") );
				input.setOperation( GridQueryOperationEnum.getEnumForName(getNullOrVal(inputObj, "operation")) ); 
				input.setUserInput( getNullOrVal(inputObj, "userInput"));
				inputs[i] = input;
			}
			return inputs;
		}
		return new GridFieldUserInput[0];
	}
	
	public GridDataQueryReq setFieldAliases( GridDataQueryReq req ) {
		NodeList references = getReferences(req);
		Node reference = references != null && references.getLength() > 0 ? references.item(0) : null;
		setAliasForInputs(req.getFilterBy(), reference);
		setAliasForInputs(req.getOrderBy(), reference);
		return req;
	}
	
	public void setAliasForInputs( GridFieldUserInput inputs[], Node reference ) {
		for( int i = 0 ; i < inputs.length && inputs != null; i++ ) {
			GridFieldUserInput filter = inputs[i];
			String fieldName = filter.getFieldName();
			NodeList fields = reference.getChildNodes();
			for( int j= 0 ; fields != null && j < fields.getLength(); j++ ) {
				Node field = fields.item(j);
				if ( !field.getNodeName().equals("#text") ) {
					String uiName = field.getAttributes().getNamedItem("ui").getTextContent().toString();
					String alias = field.getAttributes().getNamedItem("alias").getTextContent().toString();
					if ( fieldName.equals(uiName) ) {
						filter.setFieldAlias(alias);
					}
				}
			}
		}
	}
	
	private String getNullOrVal( JSONObject obj, String key ) {
		return HunterUtility.getStringOrNulFromJSONObj(obj, key);
	}
	
	public JSONArray getArray( String key, JSONObject obj ) {
		if ( obj.get( key ) != null ) {
			return obj.getJSONArray(key);
		} else {
			return new JSONArray();
		}
	}

}
