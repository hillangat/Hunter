package com.techmaster.hunter.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.exception.HunterRemoteException;
import com.techmaster.hunter.exception.HunterRunTimeException;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.Message;
import com.techmaster.hunter.xml.XMLService;
import com.techmaster.hunter.xml.XMLServiceImpl;
import com.techmaster.hunter.xml.XMLTree;

public class HunterUtility {

public static  Logger logger = Logger.getLogger(HunterUtility.class);

  public static String getRequestBaseURL(HttpServletRequest request){
	  try {
		URL requestURL = new URL(request.getRequestURL().toString());
		String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
		return requestURL.getProtocol() + "://" + requestURL.getHost() + port;
	} catch (MalformedURLException e) {
		e.printStackTrace();
		throw new HunterRunTimeException(e.getMessage());
	} 
  }
	
   public static String urlEncodeRequestMap(Map<String, ?> params, String encodeFormat) throws UnsupportedEncodingException{ 
	   StringBuilder builder = new StringBuilder();
	   for (Entry<String, ?> param : params.entrySet()) {
           if (builder.length() != 0) {
        	   builder.append('&');
           }
           builder.append(URLEncoder.encode(param.getKey(), encodeFormat));
           builder.append('=');
           builder.append(URLEncoder.encode(String.valueOf(param.getValue()), encodeFormat));
       }
	   return builder.toString();
   }
   
   public static boolean isCollectionNotEmpty(Collection<?> collection){
	   return collection != null && !collection.isEmpty();
   }
   
   public static String getBlobStr(Blob blob){
	   
	   if( blob == null )
		   return null;
	   
	   try {
		byte[] bdata = blob.getBytes(1, (int) blob.length());
		   String s = new String(bdata);
		   return s;
	} catch (SQLException e) {
		e.printStackTrace();
	}
	   return null;
   }
   
   public static String getBlobStrFromDB( String blobField, String idField, String idValue, Class<?> clzz ){
	   
	   SessionFactory sessionFactory = HunterDaoFactory.getObject(SessionFactory.class);
	   Session session = null;
	   String blobStr = null;
	   
	   try {
		   session = sessionFactory.openSession();
		   String queryString = "SELECT o."+ blobField +" FROM " + clzz.getSimpleName() + " o WHERE o." + idField + " = " + idValue;
		   logger.debug("Created query string > " + queryString);
		   Query query = session.createQuery(queryString);
		   Blob blob = (Blob) query.uniqueResult();
		   if ( null != blob ) {
			   byte[] blobBytes = blob.getBytes(1, (int) blob.length());
		       blobStr = new String(blobBytes);
		       logger.debug("Stringified blob = " + blobStr);
		   }	       		   
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if ( null != session && session.isOpen() ) {
				session.close();
			}
		}
	   
	   return blobStr;
	   
   }
   
   public static List<String> getIdStrListForList( List<?> objList, String idField ) {	   
	   List<String> idList = new ArrayList<>();
	   for( Object obj : objList ) {
		   PropertyAccessor propertyAccessor = PropertyAccessorFactory.forBeanPropertyAccess(obj);
		   String id = HunterUtility.getStringOrNullOfObj(propertyAccessor.getPropertyValue(idField));
		   idList.add( id );
	   }
	   return idList;	   
   }
   
   public static Map<String, String> getBlobStrFromDBForList( List<?> objList, String blobField, String idField, Class<?> clzz ) {
	   List<String> idList = getIdStrListForList(objList, idField);
	   Map<String, String> blobStrMap = new HashMap<>();
	   if ( HunterUtility.isCollectionNotEmpty(idList) ) {
		   blobStrMap = getBlobStrFromDBForList(blobField, idField, idList, clzz);
		   logger.debug(HunterUtility.stringifyMap(blobStrMap));
		   return blobStrMap;
	   }
	   return new HashMap<>();
   }
   
   public static Map<String, String> getBlobStrFromDBForList( String blobField, String idField, List<String> idValues, Class<?> clzz) {
	   
	   SessionFactory sessionFactory = HunterDaoFactory.getObject(SessionFactory.class);
	   Session session = null;
	   Map<String, String> blobStrMap = new HashMap<>();
	   
	   try {
		   
		   session = sessionFactory.openSession();
		   String queryString = "SELECT o."+ blobField +", o." + idField + " FROM " + clzz.getSimpleName() + " o WHERE o." + idField + " IN ( " + HunterUtility.getCommaDelimitedStrings(idValues) + " )";
		   logger.debug("Created query string > " + queryString);
		   Query query = session.createQuery(queryString);
		   
		   Iterator<?> blobItr = query.list().iterator();
		   
		   while ( blobItr.hasNext() ) {
				Object[] blobRow = (Object[])blobItr.next();
				Blob blob = ((Blob)blobRow[0]);
				if ( null != blob ) {
					String mapKey = blobRow[1].toString();
					byte[] blobBytes = blob.getBytes(1, (int) blob.length());
					String blobStr = new String(blobBytes);
					blobStrMap.put(mapKey, blobStr);
				}				
		   }		   
	       
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if ( null != session && session.isOpen() ) {
				session.close();
			}
		}
	   
	   return blobStrMap;
   }
   
   public static Blob getStringBlob(String string){
	   if( string == null )
		   return null;
	   try {
		Blob blob = new SerialBlob(string.getBytes());
		return blob;
	} catch (SQLException e) {
		e.printStackTrace();
		throw new HunterRunTimeException("Exception while converting string to blob. String = " + string);
	}
   }
   
   public static boolean isMapNotEmpty(Map<?,?> map){
	   return map != null && !map.isEmpty();
   }
   
   public static String getFirstUpperCase(String string){
	   if(!notNullNotEmpty(string)){
		  return string; 
	   }else if(string.length() == 1){
		  return string.toUpperCase(); 
	   }
	   string = (string.substring(0,1)).toUpperCase()+ (string.substring(1,string.length()).toLowerCase());
	   return string;
   }
   
   public byte[] urlEncodeAndGetBytes(Map<String, ?> params, String encodeFormat) throws UnsupportedEncodingException{
	   String builder = urlEncodeRequestMap(params, encodeFormat);
	   return builder.getBytes();
   }

	public static void threadSleepFor(long milliSec) {
		try {
			logger.info("Thread with the name " + Thread.currentThread().getName() + " is going to sleep for " + milliSec + " milliseconds.");
			Thread.sleep(milliSec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
	public static String getStringOrNullOfObj(Object obj){
		if(obj == null) return null;
		else return obj.toString();
	}
	
	public static boolean validateReceiverType(String type){
		if(type == null) return false;
		String[] types = new String[]{
			HunterConstants.RECEIVER_TYPE_CALL,
			HunterConstants.RECEIVER_TYPE_EMAIL, 
			HunterConstants.RECEIVER_TYPE_TEXT,
			HunterConstants.RECEIVER_TYPE_VOICE_MAIL
		};
		boolean contains = false;
		for(String type_ : types){
			if(type_.equals(type)){
				contains = true;
				break;
			}
		}
		return contains;
	}
	
	public static JSONArray getJSONArray(JSONObject json){
		String jsonStr = json.toString(); 
		if(jsonStr.charAt(0) != '[' && jsonStr.charAt(jsonStr.length() - 1) != ']')
			jsonStr = "[" + json.toString() + "]";
		JSONArray array = new JSONArray(new JSONTokener(jsonStr));
		return array;
	}
	
	public static boolean validateJSON(String json){
		
		if(!notNullNotEmpty(json)){
			return false;
		}
		
		try {
			JSONObject.testValidity(json);
		} catch (JSONException e) {
			logger.error("Error. Json passed in is not valid!! " + e.getMessage()); 
			return false;
		} 
		
		return true;
	}
	
	public static JSONObject selectivelyCopyJSONObject(JSONObject jsonObject, String[] ignoredKeys){
		logger.debug("Preparing to selectively copy jsonObject >> " + jsonObject); 
		JSONObject json = new JSONObject(jsonObject.toString());
		logger.debug("Copied full jsonObject >> " + json);
		for(String key : ignoredKeys){
			if(json.has(key)){
				json.remove(key);
			}else{
				logger.error("Cannot remove key since it doe not exist in the copied json. Key >> " + key); 
			}
		}
		logger.debug("After removing marked elements >>> " + json);  
		return json;
		
	}
	
	public static boolean validateEmail(String email){
		if(email == null || email.trim().equals("")){
			return false;
		}
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}
	
	public static boolean validatePhoneNumber(String phoneNumber){
		if(phoneNumber == null || phoneNumber.trim().equals("")){
			return false;
		}
		// +2540726149750
		if(phoneNumber.matches("^\\+[0-9]{1,3}[0-9]{10}")) return true;
		//validate phone numbers of format "+254-726-149-750"
		else if(phoneNumber.matches("^\\+[0-9]{1,3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
		//validate phone numbers of format "254-726-149-750" or "254.726.149.750"
		else if(phoneNumber.matches("^[0-9]{1,3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
		//validate phone numbers of format "2541234567890"
		else if (phoneNumber.matches("\\d{13}")) return true;
		//validate phone numbers of format "2541234567890"
		else if (phoneNumber.matches("\\d{12}")) return true;
		//validate phone numbers of format "1234567890"
		else if (phoneNumber.matches("\\d{10}")) return true;
		//validate phone numbers of format "726149750"
		else if (phoneNumber.matches("\\d{9}")) return true;
        //validating phone number with -, . or spaces
        else if(phoneNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
        //validating phone number with extension length from 3 to 5
        //else if(phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
        //validating phone number where area code is in braces ()
        else if(phoneNumber.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
        //return false if nothing matches the input
        else return false;
	}
	
	public static JSONObject getJSONobjOrNullFromJsonObj(JSONObject json, String key){
		JSONObject json_ = null; 
		try {
			json_ = json.getJSONObject(key);
			logger.debug("Obtained json object : " + json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json_;
	}
	
	public static  Object getNullOrValFromJSONObj(JSONObject msgJson, String key){
		Object obj = msgJson.has(key) ? msgJson.get(key) : null;
		return obj;
	}
	
	public static String getStringOrNulFromJSONObj(JSONObject msgJson, String key){
		Object obj = getNullOrValFromJSONObj(msgJson, key);
		return obj == null ? null : obj.toString();
	}
	
	public static Long getLongOrNulFromJSONObj(JSONObject msgJson, String key){
		Object obj = getNullOrValFromJSONObj(msgJson, key);
		return obj == null ? null : getLongFromObject(obj); 
	}
	
	public static int getIntOrZeroFromJsonStr(JSONObject json, String key){
		String value = getStringOrNullFromJSONObj(json, key);
		int int_ = Integer.parseInt(value == null || value.equalsIgnoreCase("null") ? "0" : value);
		return int_;
	}
	
	public static long getLongOrZeroFromJsonStr(JSONObject json, String key){
		String value = getStringOrNullFromJSONObj(json, key);
		long long_ = Long.parseLong(value == null || value.equalsIgnoreCase("null") ? "0" : value);
		return long_; 
	}
	
	public static float getFloatOrZeroFromJsonStr(JSONObject json, String key){
		String value = getStringOrNullFromJSONObj(json, key);
		float float_ = Float.parseFloat(value == null || value.equalsIgnoreCase("null") ? "0" : value);
		return float_;
	}
	
	public static  String getStringOrNullFromJSONObj(JSONObject msgJson, String key){
		Object obj = getNullOrValFromJSONObj(msgJson, key);
		String objStr = obj == null ? null : obj.toString();
		return objStr;
	}
	
	public static Object getSpringBeanFromContext(String cntxtNmsSpace, String beanName){
		
		if(cntxtNmsSpace == null || cntxtNmsSpace.trim().equals(""))
			throw new IllegalArgumentException("Names space provded id either null or empty"); 
		
		
		Object obj = null;		
		ClassPathXmlApplicationContext ctx = null;
		
		try {
			ctx = new ClassPathXmlApplicationContext(cntxtNmsSpace);
			obj = ctx.getBean(beanName);
		} catch (BeansException e) {
			e.printStackTrace();
		} finally {
			if ( null != ctx )
				((ClassPathXmlApplicationContext)ctx).close(); 
		}	

		
		logger.debug("successfully obtained the bean (" + beanName + ") from context(" + cntxtNmsSpace + ") >> " + obj.toString()); 
		
		return obj;
	}
	
	public static List<Object> getObjectList(List<?> objects){
		List<Object> objs = new ArrayList<Object>();
		for(Object obj : objects){
			objs.add(obj);
		}
		return objs;
	}
	
	public static String stringifySet(Set<?> objects){
		if(objects == null) return null;
		StringBuilder builder = new StringBuilder();
		int counter = 0;
		for(Object obj : objects){
			if(counter == 0){
				counter = 2;
				builder.append("\n");
			}
			builder.append(obj.toString()).append("\n;"); 
		}
		return builder.toString();
	}
	
	public static String stringifyList(List<?> objects){
		if(objects == null) return null;
		StringBuilder builder = new StringBuilder();
		int counter = 0;
		for(Object obj : objects){
			if(counter == 0){
				counter = 2;
				builder.append("\n");
			}
			if(obj != null){
				builder.append(obj.toString()).append("\n"); 
			}
		}
		return builder.toString();
	}
	
	public static String stringifyMap(Map<?, ?> objects){
		
		if(objects.isEmpty()){
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		
		for(Map.Entry<?, ?> entry : objects.entrySet()){
			String key = entry.getKey() + "";
			String val = entry.getValue() + "";
			builder.append(" "+ key + " = " + val + ",");
		}

		String str = builder.toString();
		
		if(str.length() > 1){
			str = str.endsWith(",") ? str.substring(0, str.length() - 1) : str;
		}
		
		return str;
	}
	
	public static String stringifyElement(Element element, boolean omitDeclation){
		try {
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			StringWriter buffer = new StringWriter();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, omitDeclation ? "yes" : "no");  
			transformer.transform(new DOMSource(element),new StreamResult(buffer));
			String str = buffer.toString();
			return str;
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static HttpSession getSessionForRequest(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		return session;
	}
	
	public static Object getSessionAttribute(HttpServletRequest request, String attrName){
		return getSessionForRequest(request).getAttribute(attrName);
	}
	
	public static void setSessionAttribute(HttpServletRequest request, String attrName, Object obj){
		getSessionForRequest(request).setAttribute(attrName, obj);
	}
	
	public static boolean notNullNotEmpty(Object object){
		return object == null ? false : object.toString().trim().equals("") ? false : true;
	}
	
	public static boolean notNullNotEmptyAndEquals(Object object, String base){
		if(notNullNotEmpty(object) && notNullNotEmpty(base)){ 
			return object.toString().equals(base);
		}else{
			return false;
		}
	}
	
	public static String[] initArrayOrExpand(String[] rowArr) {
		if(rowArr == null)
			return new String [1]; 
		return Arrays.copyOf(rowArr, rowArr.length + 1);
	}
	
	public static String[] initArrayAndInsert(String[] rowArr, String input) {
		rowArr = initArrayOrExpand(rowArr);
		rowArr[rowArr.length - 1] = input;
		return rowArr;
	}
	
	public static Object[] initArrayOrExpand(Object[] rowArr) {
		if(rowArr == null)
			return new Object [1]; 
		return Arrays.copyOf(rowArr, rowArr.length + 1);
	}
	
	public static Object[] initArrayAndInsert(Object[] rowArr, Object input) {
		rowArr = initArrayOrExpand(rowArr);
		rowArr[rowArr.length - 1] = input;
		return rowArr;
	}
	
	public static Integer[] getIntegerArray(Object[] objects){
		if(objects != null){
			Integer[] integers = new Integer[objects.length];
			for(int i=0; i<objects.length; i++){
				Integer integer = (Integer)objects[i]; 
				integers[i] = integer;
			}
			return integers;
		}else{
			return new Integer[0];
		}
	}
	
	public static boolean isNumeric(Object obj){
		
		
		String input = String.valueOf(obj);
		if(!notNullNotEmpty(obj))
			return false;
		
		if(input.length() == 1){
			char c = input.charAt(0);
			if(!Character.isDigit(c))
				return false;
		}
		
		if(input.charAt(0) == '-') // in case it's a negative number.
			input = input.substring(1,input.length());
		
		if (input.contains(".")) {
			if (input.trim().length() == 1)
				return false;
			else {
				int dotCount = 0;
				for (char ch : input.toCharArray()) {
					if (ch == '.')
						dotCount++;
					if (dotCount > 1)
						return false;
				}
				input = input.replace(".", "");
			}
		}
		
		for(char ch : input.toCharArray()){
			if(!Character.isDigit(ch))
				return false;
		}
		 
		return true;
	}
	
	public static String getNewDateString(String formatStr){
		if(formatStr == null)
			formatStr = HunterConstants.DATE_FORMAT_STRING;
		java.util.Date now = new java.util.Date();
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		String date = format.format(now);
		System.out.println(date);
		return date;
	}
	
	public static String formatDate(java.util.Date date, String formatStr){
		if(date == null) return null;
		if(formatStr == null)
			formatStr = HunterConstants.DATE_FORMAT_STRING;
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		String dateStr = format.format(date);
		return dateStr;
	}
	
	public static Date getFormatedDate(java.util.Date date, String formatStr){
		if(formatStr == null)
			formatStr = HunterConstants.DATE_FORMAT_STRING;
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		String dateStr = format.format(date);
		System.out.println(dateStr);
		return date;
	}
	
	public static java.util.Date parseDate(String strDate, String format){
		
		if(strDate == null || format == null){
			logger.warn("either strDate of format date is null. strDate (" + strDate +")" + " format(" + format +")" ); 
			return null;
		}
		
		java.util.Date date = null;
		
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			date = formatter.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
	}
	
	public static String getStringOfDoc(Document doc){
		TransformerFactory tf = TransformerFactory.newInstance();
		StringWriter writer;
		String output = null;
		try {
			Transformer transformer = tf.newTransformer();
			//transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public static Document createXmlDocFromHttpUrl(String url) throws SAXException, IOException, ParserConfigurationException {
		
		if((url == null || url.trim().equals("")) || !url.startsWith("http"))
			throw new IllegalArgumentException("Url string provided to build document from Http URL is not correct.");
		
		Document doc = null;
		
		try {

			URL URL_ = new URL(url);
			logger.info("creating a document from the URL = "  + url); 
			InputStream inputStream = URL_.openStream();
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = db.parse(inputStream);
			inputStream.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public static Document createDocFromStr(String xmlStr) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xmlStr));
		Document doc = db.parse(is);
		if(doc == null){
			throw new NullPointerException();
		}else{
			return doc;
		}
	}
	
	public static Long getLongFromObject(Object obj){
		if(obj == null || obj.toString().trim().equals("")) return null;
		if(!isNumeric(obj)) throw new IllegalArgumentException("Object passed in is not numeric. Object > " + obj);
		String str = obj.toString();
		if(str.contains(".")){
			int dotIndx = str.indexOf(".");
			str = str.substring(0, dotIndx);
		}
		Long lng = Long.parseLong(str);
		return lng;
	}
	
	public static Float getFloatFromObject(Object obj){
		if(obj == null || obj.toString().trim().equals("")) return null;
		if(!isNumeric(obj)) throw new IllegalArgumentException("Object passed in is not numeric. Object > " + obj);
		String str = obj.toString() + 'f';
		Float flt = Float.parseFloat(str);
		return flt;
	}
	
	public static String getStackTrace(Throwable t){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();  
	}
	
	public static String getTextValueForEmailTemplate(String xPath) throws ParserConfigurationException, HunterRemoteException{
		XMLService xmlService = HunterCacheUtil.getInstance().getXMLService(HunterConstants.EMAIL_TEMPLATES_CACHED_SERVICE);
		String value = xmlService.getTextValue(xPath.toString());
		logger.debug(value); 
		return value;
	}
	
	
	
	public static String getQueryForSqlId(String id) {
		StringBuilder builder = new StringBuilder();
		builder.append("queries/query[@id=\"");
		builder.append(id);
		builder.append("\"]/statement");
		XMLService service = HunterCacheUtil.getInstance().getXMLService(HunterConstants.QUERY_XML_CACHED_SERVICE);
		String query  = service.getTextValue(builder.toString());
		return query.trim();
	}
	
	public static String getQueryMapperForMapId(String id) {
		StringBuilder builder = new StringBuilder();
		builder.append("queries/query[@id=\"");
		builder.append(id);
		builder.append("\"]/statement");
		XMLService service = HunterCacheUtil.getInstance().getXMLService(HunterConstants.QUERY_XML_CACHED_SERVICE);
		String query  = service.getTextValue(builder.toString());
		return query.trim();
	}
	
	public static String getQueryDescForSqlId(String id) {
		StringBuilder builder = new StringBuilder();
		builder.append("queries/query[@id=\"");
		builder.append(id);
		builder.append("\"]/description");
		XMLService service = HunterCacheUtil.getInstance().getXMLService(HunterConstants.QUERY_XML_CACHED_SERVICE);
		String description  = service.getTextValue(builder.toString());
		return description.trim();
	}
	
	public static XMLService createXMLServiceForDoc(Document doc) throws ParserConfigurationException, HunterRemoteException {
		XMLTree tree = new XMLTree(doc);
		XMLService service = new XMLServiceImpl(tree);
		return service;
	}
	
	public static String removeStartingAndEndingChar(String input){
		if(input == null) return null;
		if(input.length() <= 2) return "";
		input = input.substring(1, input.length());
		input = input.substring(0, input.length() - 1);
		return input;
	}

	public static String getCommaDelimitedStrings(Object[] objects){
		if(objects == null || objects.length == 0) return null;
		StringBuilder builder = new StringBuilder();
		if(objects.length == 1) {
			return HunterUtility.getStringOrNullOfObj(objects[0].toString()); 
		}else{
			for(Object obj : objects){
				String str = String.valueOf(obj);
				builder.append(str);
				builder.append(",");
			}
		}
		String finalStr = builder.toString();
		return removeLastChar(finalStr);
	}
	
	public static String getCommaDelimitedStrings(List<?> objects){
		if(objects == null || objects.size() == 0) return null;
		StringBuilder builder = new StringBuilder();
		if(objects.size() == 1) {
			return objects.get(0).toString(); 
		}else{
			for(Object obj : objects){
				String str = String.valueOf(obj);
				builder.append(str);
				builder.append(",");
			}
		}
		String finalStr = builder.toString();
		return removeLastChar(finalStr);
	}
	
	public static String getSingleQuotedCommaDelimitedForList(List<?> objects){
		
		if(objects == null || objects.isEmpty()){
			logger.warn("Empty or null list passed in. Returning null..."); 
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		int indx = 0;
		
		for(Object obj : objects){
			String quoted = singleQuote(obj);
			builder.append(quoted);
			if(indx != objects.size()-1){
				builder.append(",");
			}
			indx++;
		}
		String quoted = builder.toString();
		logger.debug("Quoted string : " + quoted); 
		return quoted;
	}
	
	/**
	 * 
	 * @param str
	 * @return if str.length == 1 or str.length == 0, it returns "". 
	 */
	public static String removeLastChar(String str){
		
		if(str == null)
			return str;
		if(str.trim() == "" || str.trim().length() == 1)
			return "";
		
		String returned = str.substring(0, str.length()-1);
		
		return returned;
	}
	
	public static String[] convertToStringArray(Object[] objects){
		String[] strings = null;
		for(Object obj : objects){
			String str = String.valueOf(obj);
			strings = initArrayAndInsert(strings, str);
		}
		return strings;
	}
	
	
	public static String createMessageWithLink(String message, String uiHyperLing, String hyperLinkUrl, String method) {
		StringBuilder output = new StringBuilder();
		output.append("<div style=\"text-align:center\" >")
		.append("<h4>"+ message +"</h4>")
		.append("<form")
		.append(" action=\""/*BarakaConstants.BARAKA_ROOT_LOCAL_HOST*/)
		.append(!hyperLinkUrl.startsWith("/") && !hyperLinkUrl.startsWith("http") ? "/" + hyperLinkUrl : hyperLinkUrl)
		.append("\" method = ")
		.append(method == null? "\"GET\"" : "\"" + method + "\"")
		.append(">")
		.append("<button type=\"submit\" class=\"button\">")
		.append(uiHyperLing)
		.append("</button>")
		.append("</form>")
		.append("</div>");
		return output.toString();
	}
	
	public static void logList(List<?> list){
		logger.info(Arrays.toString(list.toArray()));
	}
	
	public static String getCommaSeparatedSingleQuoteStrForList(List<?> objects){
		
		if(objects == null || objects.isEmpty()){
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		
		for(int i=0; i<objects.size();i++){
			builder.append(HunterUtility.singleQuote(objects.get(i))); 
			if(i <= objects.size() - 2)
				builder.append(",");
		}
		
		String finalStr = builder.toString();
		return finalStr;
	}
	
	public static String getParamNamesAsStringsFrmRqst(HttpServletRequest request){
		String enumsStr = "";
		Enumeration<?> enms = request.getParameterNames();
		while(enms.hasMoreElements()){
			String enm = String.valueOf(enms.nextElement());
			enumsStr += enm + ",";		
		}
		if(enumsStr != null && enumsStr.trim().length() > 0){
			enumsStr = enumsStr.substring(0, enumsStr.length()-1);
			return enumsStr.trim();
		}
		return enumsStr.trim();
	}
	
	public static String replaceCharAt(String component, int index, char replacement ){
		logger.info("index >> " + index + " replacement >> " + replacement + " component before replacement >> " + component); 
		String part1 = component.substring(0, index); 
		part1 += replacement;
		String part2 = component.substring(index+1, component.length());
		String replaced = part1 + part2;
		logger.info("component after replacement " + replaced); 
		return replaced;
	}
	
	public static String getFileExtension(File file){
		String extension = null;
		if(file != null){
			String name = file.getName();
			int i = name.lastIndexOf('.');
			if (i > 0) {
			    extension = name.substring(i+1);
			}
		}
		return extension;
	}
	
	public static String getFileExtensionWithDot(File file){
		String extension = getFileExtension(file);
		if(extension != null){
			extension = "." + extension;
		}
		return extension;
	}
	
	public static String getRequestBodyAsString(HttpServletRequest request) throws IOException {

	    String body = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    try {
	        InputStream inputStream = request.getInputStream();
	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
	        } else {
	            stringBuilder.append("");
	        }
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                throw ex;
	            }
	        }
	    }

	    body = stringBuilder.toString();
	    logger.debug("Returning body >> " + body);
	    return body;
	}
	
	public static XMLService getXMLServiceForFileLocation(String location){
		try {
			XMLTree tree = new XMLTree(location, false);
			XMLService service = new XMLServiceImpl(tree);
			HunterLogFactory.getLog(HunterUtility.class).info("Successfully obtained xml for location : " + location);  
			return service;
		} catch (HunterRunTimeException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static XMLService getXMLServiceForStringContent(String content){
		try {
			XMLTree tree = new XMLTree(content, true);
			XMLService service = new XMLServiceImpl(tree);
			logger.info("Successfully obtained xml for content : " + content);  
			return service;
		} catch (HunterRunTimeException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String singleQuote(Object obj){
		if(obj == null) return null;
		StringBuilder builder = new StringBuilder();
		builder.append("'").append(obj).append("'");
		return builder.toString(); 
	}
	
	public static String poundQuote(Object obj){
		if(obj == null) return null;
		StringBuilder builder = new StringBuilder();
		builder.append("#").append(obj).append("#");
		return builder.toString(); 
	}
	
	public static String doublePoundQuote(Object obj){
		if(obj == null) return null;
		StringBuilder builder = new StringBuilder();
		builder.append("##").append(obj).append("##");
		return builder.toString(); 
	}
	
	public static Object[] getWorkbookFromMultiPartRequest(MultipartHttpServletRequest request){
		
		 Workbook workbook = null;
		 Iterator<String> itr =  request.getFileNames();
		 MultipartFile mpf = request.getFile(itr.next());
		 String fileName = mpf.getOriginalFilename();
		 
		 try {
			InputStream is = mpf.getInputStream();
			workbook = WorkbookFactory.create(is);
			return new Object[]{workbook, fileName};
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		 
		 return new Object[]{};
		 
	}
	
	public static Object[] getWorkbookFromRequest(HttpServletRequest request){
		
		logger.debug("Extracting workbook from request...."); 
		
		String originalFileName = null;
		
		boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
		
		if(!isMultiPart){
			logger.warn("Request is not mult-part. Returning null workbook!!"); 
			return null;
		}
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		InputStream inputStream = null;
		Workbook workBook = null;
		
		List<?> items = null;
		Iterator<?> itemItr = null;
		
		try {
			items = upload.parseRequest(request);
			itemItr = items.iterator();
			
			while(itemItr.hasNext()){
				FileItem item = (FileItem)itemItr.next();
				if(!item.isFormField()){
					inputStream = item.getInputStream();
					originalFileName = item.getName();
					logger.info("workbook file name >>>" + originalFileName); 
				}
			}
			if(inputStream != null){
				workBook = WorkbookFactory.create(inputStream);
			}
			logger.info("Successful workbook extraction!!"); 
			logger.debug("Finished extracting workbook from request!"); 
			return new Object[]{workBook, originalFileName};
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.warn("Workbook extraction from request failed. Returning empty array!!"); 
		return new Object[]{};
	}
	
	public static AuditInfo getAuditInforForNow(Date cretDate, String createdBy, Date lastUpdate, String lastUpdatedBy){
		AuditInfo auditInfo = new AuditInfo();
		auditInfo.setCreatedBy(createdBy);
		auditInfo.setCretDate(cretDate);
		auditInfo.setLastUpdate(lastUpdate);
		auditInfo.setLastUpdatedBy(lastUpdatedBy);
		return auditInfo;
	}
	
	public static AuditInfo getAuditInfoFromRequestForNow(HttpServletRequest request, String userName){
		Object sessUserName = null;
		if(request != null){
			sessUserName = getSessionAttribute(request, HunterConstants.SECURITY_USER_NAME_STR);
		}
		if(sessUserName != null)
			userName =  sessUserName.toString();
		Date now = new Date();
		AuditInfo auditInfo = getAuditInforForNow(now, userName, now, userName);
		return auditInfo;
	}
	
	public static int getSqlType(Object obj){
		
		if(obj == null) throw new NullPointerException("Operant cannot be null!!");
		
		if(obj.getClass().isAssignableFrom(Long.class)){
			return java.sql.Types.BIGINT;
		}else if(obj.getClass().isAssignableFrom(Integer.class)){
			return java.sql.Types.INTEGER;
		}else if(obj.getClass().isAssignableFrom(Float.class)){
			return java.sql.Types.FLOAT;
		}else if(obj.getClass().isAssignableFrom(Double.class)){
			return java.sql.Types.DOUBLE;
		}else if(obj.getClass().isAssignableFrom(Short.class)){
			return java.sql.Types.SMALLINT;
		}else if(obj.getClass().isAssignableFrom(Character.class)){
			return java.sql.Types.CHAR;
		}else if(obj.getClass().isAssignableFrom(Date.class)){
			return java.sql.Types.DATE;
		}else if(obj.getClass().isAssignableFrom(String.class)){
			return java.sql.Types.VARCHAR;
		}else if(obj.getClass().isAssignableFrom(Boolean.class)){
			return java.sql.Types.BOOLEAN;
		}else{
			throw new IllegalArgumentException("The class is not yet mapped !! " + obj.getClass().getName());
		}
		
	}
	
	public static String getYNForBoolean(boolean boolean_){
		String yN = boolean_ ? "Y" : "N";
		return yN;
	}
	
	public static boolean getBooleanForYN(String yn){
		boolean ynb = "Y".equalsIgnoreCase(yn) ? true : false;
		return ynb;
	}
	
	public static void testMap(){
		HunterCacheUtil.getInstance();
		XMLService xmlService =(XMLServiceImpl) HunterCacheUtil.getInstance().getXMLService(HunterConstants.EMAIL_TEMPLATES_CACHED_SERVICE);
		NodeList nodeList = xmlService.getNodeListForPathUsingJavax("//template[@name='taskpProcessRequestNotification']/context/miscelaneous/*");
		if(nodeList != null && nodeList.getLength() >= 1){
			for(int i=0; i<nodeList.getLength(); i++){
				Node node = nodeList.item(i);
				if(node.getNodeName().equals("config")){
					NodeList configs = node.getChildNodes();
					String key = configs.item(1).getTextContent();logger.debug("key = " + key);
					String value = configs.item(3).getTextContent();logger.debug("value = " + value);
				}
			}
		}
	}
	
	public static JSONObject setJSONObjectForSuccess(JSONObject json, String message){
		json = json == null ? new JSONObject() : json;
		json.put(HunterConstants.STATUS_STRING, HunterConstants.STATUS_SUCCESS);
		json.put(HunterConstants.MESSAGE_STRING, message);
		return json;
	}
	
	public static JSONObject setJSONObjectForFailure(JSONObject json, String message){
		json = json == null ? new JSONObject() : json;
		json.put(HunterConstants.STATUS_STRING, HunterConstants.STATUS_FAILED);
		json.put(HunterConstants.MESSAGE_STRING, message);
		return json;
	}
	
	public static Map<String,Double> getRationedImgDimens(double maxHeight, double maxWidth, double height, double width){
		Map<String,Double> dimensMap = new HashMap<>();
		if( height > maxHeight || width > maxWidth ) {
			double ratio = maxHeight > maxWidth ? maxWidth/maxHeight : maxHeight/maxWidth;
			height *= ratio;
			width  *= ratio;
		}
		dimensMap.put("height", height);
		dimensMap.put("width", width);
		return dimensMap;
		
	}
	
	public static String getWhrClsFrRcvrRgnTyp(String type, Map<String,String> regionNames){
		   logger.debug("Fetching where clause for : " + HunterUtility.stringifyMap(regionNames)); 
		   String where = 
		   	" WHERE CNTRY IS NULL "	+
		      "AND CNTY IS NULL "	+
		      "AND STATE IS NULL "	+
		      "AND CNSTTNCY IS NULL "+
		      "AND WRD IS NULL " 	+ 
		      "AND VLLG IS NULL ";
		   
		   String country = regionNames.get(HunterConstants.RECEIVER_LEVEL_COUNTRY);
		   String county = regionNames.get(HunterConstants.RECEIVER_LEVEL_COUNTY);
		   String conscy = regionNames.get(HunterConstants.RECEIVER_LEVEL_CONSITUENCY);
		   String consWard = regionNames.get(HunterConstants.RECEIVER_LEVEL_WARD);
		   String village = regionNames.get(HunterConstants.RECEIVER_LEVEL_VILLAGE);
		   
		   if(HunterConstants.RECEIVER_LEVEL_COUNTRY.equals(type)){
			   where = where.replace("CNTRY IS NULL", "CNTRY = " + HunterUtility.singleQuote(country));   
		   }else if(HunterConstants.RECEIVER_LEVEL_COUNTY.equals(type)){
			   where = where.replace("CNTRY IS NULL", "CNTRY = " + HunterUtility.singleQuote(country));
			   where = where.replace("CNTY IS NULL", "CNTY = " + HunterUtility.singleQuote(county));
		   }else if(HunterConstants.RECEIVER_LEVEL_CONSITUENCY.equals(type)){
			   where = where.replace("CNTRY IS NULL", "CNTRY = " + HunterUtility.singleQuote(country));
			   where = where.replace("CNTY IS NULL", "CNTY = " + HunterUtility.singleQuote(county));
			   where = where.replace("CNSTTNCY IS NULL", "CNSTTNCY = " + HunterUtility.singleQuote(conscy));
		   }else if(HunterConstants.RECEIVER_LEVEL_WARD.equals(type)){
			   where = where.replace("CNTRY IS NULL", "CNTRY = " + HunterUtility.singleQuote(country));
			   where = where.replace("CNTY IS NULL", "CNTY = " + HunterUtility.singleQuote(county));
			   where = where.replace("CNSTTNCY IS NULL", "CNSTTNCY = " + HunterUtility.singleQuote(conscy));
			   where = where.replace("WRD IS NULL", "WRD = " + HunterUtility.singleQuote(consWard));
		   }else if(HunterConstants.RECEIVER_LEVEL_VILLAGE.equals(type)){
			   where = where.replace("CNTRY IS NULL", "CNTRY = " + HunterUtility.singleQuote(country));
			   where = where.replace("CNTY IS NULL", "CNTY = " + HunterUtility.singleQuote(county));
			   where = where.replace("CNSTTNCY IS NULL", "CNSTTNCY = " + HunterUtility.singleQuote(conscy));
			   where = where.replace("WRD IS NULL", "WRD = " + HunterUtility.singleQuote(consWard));
			   where = where.replace("VLLG IS NULL", "VLLG = " + HunterUtility.singleQuote(village));
		   }
		   logger.debug("Returning : \n" + where); 
		   return where;	   
	   }
	
	public static String getFlatNumFromExponetNumber(String exponent){
		if(!HunterUtility.notNullNotEmpty(exponent)) 
			return exponent;
		BigDecimal bd = new BigDecimal(exponent);
	    exponent = bd.longValue() + "";
	    return exponent;
	}
	
	public static void main(String[] args) {
		Map<String,Double> dimens = getRationedImgDimens(60d, 40d, 45d, 55d);
		logger.debug(HunterUtility.stringifyMap(dimens));  
	}
	
	public static String getLevelNameOrType(String levelNameType, final String countryName, final String countyName, String consName, String wardName){
		if( null == countryName ){
			if( HunterConstants.REGION_LEVEL_NAME.equals(levelNameType) ){ 
				return countryName;
			}else{
				return HunterConstants.RECEIVER_LEVEL_COUNTRY;
			}
		}
		if( null == countyName ){
			if( HunterConstants.REGION_LEVEL_NAME.equals(levelNameType) ){ 
				return countyName;
			}else{
				return HunterConstants.RECEIVER_LEVEL_COUNTY;
			}
		}
		if( null == consName ){
			if( HunterConstants.REGION_LEVEL_NAME.equals(levelNameType) ){ 
				return consName;
			}else{
				return HunterConstants.RECEIVER_LEVEL_CONSITUENCY;
			}
		}
		if( null != wardName ){
			if( HunterConstants.REGION_LEVEL_NAME.equals(levelNameType) ){ 
				return wardName;
			}else{
				return HunterConstants.RECEIVER_LEVEL_WARD;
			}
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append(levelNameType).append(",").
		append(countryName).append(",").
		append(countyName).append(",").
		append(consName).append(",").
		append(wardName);
		
		throw new HunterRunTimeException("Inside get level name or type : Cannot mapped the request ( " + builder.toString()  +" )"); 
	}
	
	
	public static AuditInfo getAuditInfoForSclMsg(Message message){
		AuditInfo auditInfo = new AuditInfo();
		auditInfo.setCreatedBy(message.getCreatedBy());
		auditInfo.setCretDate(message.getCretDate());
		auditInfo.setLastUpdate(message.getLastUpdate());
		auditInfo.setLastUpdatedBy(message.getLastUpdatedBy()); 
		return auditInfo;
	}
	
	public static Date getFutureDate( String type, int count ){
		
		if( type == null || count == 0){
			return new Date();
		}
		
		Calendar cal = Calendar.getInstance();
		
		if( type.equals( HunterConstants.YEAR ) ){
			cal.add(Calendar.YEAR, count); // to get previous year add -1
		}else if( type.equals( HunterConstants.MONTH ) ){
			cal.add(Calendar.MONTH, count); // to get previous year add -1
		}else if( type.equals( HunterConstants.DAY ) ){
			cal.add(Calendar.DAY_OF_MONTH, count); // to get previous year add -1
		}else if( type.equals( HunterConstants.WEEK ) ){
			cal.add(Calendar.WEEK_OF_YEAR, count); // to get previous year add -1
		}else if( type.equals( HunterConstants.HOUR ) ){
			cal.add(Calendar.HOUR, count); // to get previous year add -1
		}else if( type.equals( HunterConstants.MINUTE ) ){
			cal.add(Calendar.MINUTE, count); // to get previous year add -1
		}else if( type.equals( HunterConstants.SECOND ) ){
			cal.add(Calendar.SECOND, count); // to get previous year add -1
		}else{
			throw new IllegalArgumentException( "Invalid future time type : " + type );
		}
		
		Date futureDate = cal.getTime();
		return futureDate;
	}
	
	public static String convertFileToString( String location ){
		try {
			final String EoL = System.getProperty("line.separator");
			List<String> lines = Files.readAllLines(Paths.get(location),Charset.defaultCharset());
			StringBuilder sb = new StringBuilder();
			for (String line : lines) {
			    sb.append(line).append(EoL);
			}
			final String content = sb.toString();
			return content;
		} catch (IOException e) {
			e.printStackTrace();
			throw new HunterRunTimeException(e.getMessage());
		}
	}
	
	public static JSONObject getServerResponse( String message, String status, JSONObject data ){			
		return getMessageAndStatus(message, status, null, data);
	}
	
	public static JSONObject getServerResponse( String message, String status, JSONArray data ){			
		return getMessageAndStatus(message, status, data, null);
	}
	
	public static JSONObject getMessageAndStatus( String message, String status, JSONArray array, JSONObject jsonObject ){
		JSONObject response = new JSONObject();
		response.put("message", message);
		response.put("status", status);
		response.put("data",null == array ? jsonObject : array);
		return response;
	}
	
	public static JSONObject getServerError( String message ){
		JSONObject json = null;
		return getServerResponse(message, HunterConstants.STATUS_FAILED, json);
	}
	
	public static JSONObject getServerSuccess( String message ){
		JSONObject json = null;
		return getServerResponse(message, HunterConstants.STATUS_SUCCESS, json);
	}
	
	
	
	
	
}

