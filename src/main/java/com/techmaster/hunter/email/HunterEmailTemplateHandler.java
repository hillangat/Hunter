package com.techmaster.hunter.email;

import java.util.List;

import org.apache.log4j.Logger;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.util.HunterUtility;
import com.techmaster.hunter.xml.XMLService;

public class HunterEmailTemplateHandler {
	
	private static HunterEmailTemplateHandler instance;
	private static Logger logger = Logger.getLogger(HunterEmailTemplateHandler.class);
	
	private static final String JAVA_SPACE_REPLACE = "\\t";
	private static final String JAVA_NEW_LINE_REPLACE = "\\n";
	
	static{
		if(instance == null){
			synchronized (HunterEmailTemplateHandler.class) {
				instance = new HunterEmailTemplateHandler();
			}
		}
	}
	
	public static HunterEmailTemplateHandler getInstance(){
		return instance;
	}
	
	public String getTemplateForName(String templateName){
		XMLService xmlService = HunterCacheUtil.getInstance().getXMLService(HunterConstants.EMAIL_TEMPLATES_CACHED_SERVICE);
		String xPath = "//template[@name='"+ templateName +"']/content/text()";
		String html = xmlService.getCData(xPath);
		html = html.replaceAll("<html>", "");
		html = html.replaceAll("</html>", "");
		html = html.replaceAll("<body>", "");
		html = html.replaceAll("</body>", "");
		html = html.replaceAll(JAVA_SPACE_REPLACE, " ");
		html = html.replaceAll(JAVA_NEW_LINE_REPLACE, " ");
		logger.debug("Returning html \n" + html); 
		return html;
	}
	
	
	public List<String> getAllExistingTemplatesNames(){
		List<String> existentTemplates = HunterCacheUtil.getInstance().getExistentEmailTemplateNames();
		logger.debug("Returning existing templates \n " + HunterUtility.stringifyList(existentTemplates)); 
		return existentTemplates;
	}
	
}

