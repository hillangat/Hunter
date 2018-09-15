package com.techmaster.hunter.util;

import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.exception.HunterRunTimeException;
import com.techmaster.hunter.xml.XMLService;
import com.techmaster.hunter.xml.XMLServiceImpl;
import com.techmaster.hunter.xml.XMLTree;

public class ConvertGridHeaders {
	
	public XMLService getOldHeaders() {
		XMLService gridHeaderService = HunterCacheUtil.getInstance().getXMLService(HunterConstants.ANGULAR_HEADERS_CONFIG_CACHED_SERVICE);
		return gridHeaderService;
	}
	
	private String getPath( String gridName ) {
		return "headers/header[@name=\""+ gridName +"\"]";
	}
	
	public String getJsonStr( String key, XMLService gridHeaderService ) {
		if ( null != gridHeaderService ) {
			String json = gridHeaderService.getCData( getPath( key ) );
			return anEscape( json );
		}
		return null;
	}
	
	private String anEscape( String content ) {
		return null != content ? content.replaceAll("&#91;", "[").replaceAll("&#93;", "]") : null;
	}
	
	public void convert() {
		XMLService oldService = getOldHeaders();
		XMLService xmlService = getNewHeaders();
		NodeList headers = oldService.getNodeListForPathUsingJavax("headers/header");
		if ( HunterUtility.isNodeListNotEmptpy(headers) ) {
			for( int i = 0; i < headers.getLength(); i++ ) {
				Node header = headers.item(i);
					if ( !header.getNodeName().equals("#text") ) {
					String key = HunterUtility.getNodeAttr(header, "name", String.class);;
					if ( HunterUtility.notNullNotEmpty(key) ) {
						String jsonStr = getJsonStr(key, oldService);
						JSONArray json = jsonStr != null ? new JSONArray( jsonStr.trim() ) : null;
						if ( json != null && json.length() > 0 ) {
							Element e = this.addHeaderElement(xmlService, key);
							for( int j = 0; j < json.length(); j++ ) {
								JSONObject object = json.getJSONObject(j);
								Element field = xmlService.getXmlTree().getDoc().createElement("field");
								field.setAttribute("name", object.get("headerId").toString());
								Iterator<?> propsKeys = object.keys();
								while( propsKeys.hasNext() ) {
									String propKey = propsKeys.next().toString();
									Element prop = xmlService.getXmlTree().getDoc().createElement("prop");
									prop.setAttribute("key", propKey);
									prop.setAttribute("value", object.get(propKey).toString() );
									field.appendChild(prop);
								}
								e.appendChild(field);
								xmlService.getXmlTree().getDoc().getDocumentElement().appendChild(e);
							}
						}
					}
				}
			}
		}
	}
	
	public Element addHeaderElement( XMLService xmlService, String headerName ) {
		Element e = xmlService.getXmlTree().getDoc().createElement("header");
		e.setAttribute("name", headerName);
		return e;
	}
	
	public XMLService getNewHeaders() {
		try {
			XMLService service = new XMLServiceImpl(new XMLTree("<headers></headers>", true ));
			return  service;
		} catch (HunterRunTimeException | ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}

}
