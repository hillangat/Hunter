package com.techmaster.hunter.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.techmaster.hunter.exception.HunterRunTimeException;
import com.techmaster.hunter.util.HunterUtility;
/**
 * @author hillangat
 * This class will act as a container of xml contents
 * It will be acted upon by xmlhandler. 
 *
 */
public class XMLTree {

	private Document doc = null;
	private String path = null;
	private Logger logger = Logger.getLogger(getClass());
	
	private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	
	public XMLTree(Document doc) {
		this.doc = doc;
	}
	
	public XMLTree(String pathOrXmlString, boolean isString) throws ParserConfigurationException {
		if(pathOrXmlString != null){
			try {
				if(isString && pathOrXmlString.startsWith("<?xml version")){
					this.doc = HunterUtility.createDocFromStr(pathOrXmlString);
				}else if(isString && !pathOrXmlString.startsWith("<?xml version")){
					// obtain the root element
					pathOrXmlString = pathOrXmlString.trim();
					int preTag = pathOrXmlString.indexOf("<");
					int endTag = pathOrXmlString.indexOf(">");
					String root = pathOrXmlString.substring(1, endTag);
					
					if(preTag > 0) throw new IllegalArgumentException("The string passed in does not start with a valid xml tag >> " + pathOrXmlString);
					if(root.trim().length() <= 2) throw new IllegalArgumentException("The string passed in does not start with a valid xml tag >> " + pathOrXmlString);
					
					if(pathOrXmlString.startsWith("<" + root + ">") && pathOrXmlString.endsWith("</" + root + ">")){
						
						this.doc = HunterUtility.createDocFromStr(pathOrXmlString);
						logger.info("Successfully created a xml document from the string >> " + this.doc); 
						
					}else{
						throw new IllegalArgumentException("The string provided does not seem valid >> " + pathOrXmlString);
					}
					
					
				}else{
					this.setPath(pathOrXmlString);
					this.doc = getDocFromDir(this.path);
				}
			} catch (HunterRunTimeException | IOException | SAXException e) {
				e.printStackTrace();
			}
		}else{
			logger.debug("Path or xml string is null..."); 
			throw new IllegalArgumentException("pathOrXmlString cannot be null!");
		}
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	
	
	private DocumentBuilder getBuilder() throws HunterRunTimeException{
		try {
			return factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new HunterRunTimeException(HunterUtility.getStackTrace(e));
		}
	}
	
	
	private Document getDocFromDir(String location) throws HunterRunTimeException, IOException, SAXException{
		if(location != null && new File(location).exists()){
			FileInputStream fis = new FileInputStream(location);
			Document doc = getBuilder().parse(new InputSource(fis)); 
			return doc;
		}else{
			throw new FileNotFoundException("The XML tree could not be build from the location : " + location);
		}
	}

	

}
