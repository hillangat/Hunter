package com.techmaster.hunter.imports.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.techmaster.hunter.constants.HunterURLConstants;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.util.HunterHibernateHelper;

public class ImportHelper {
	
	private static Logger logger = Logger.getLogger(ImportHelper.class);

	public static HunterImportBean createHntrImprtBnFrmWrkbk(Workbook workbook, AuditInfo auditInfo, String fileName, String beanName, String status){
		
		ByteArrayOutputStream bos = null;
		
		try {
			bos = new ByteArrayOutputStream();
			workbook.write(bos);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
		    try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		byte[] bytes = bos.toByteArray();
		
		HunterImportBean hunterImportBean = new HunterImportBean();
		hunterImportBean.setWorkbook(workbook);
		hunterImportBean.setOriginalFileName(fileName);
		hunterImportBean.setBeanName(beanName); 
		hunterImportBean.setByteLen(bytes.length); 
		hunterImportBean.setExcelBytes(bytes);
		hunterImportBean.setAuditInfo(auditInfo);
		hunterImportBean.setStatus(status);
		
		return hunterImportBean;
	}
	
	public static Workbook getWorkBookForImportBean(HunterImportBean hunterImportBean){
		byte[] bytes = hunterImportBean.getExcelBytes();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(byteArrayInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}
	
	public static void createImportBeanBlobAndSave(HunterImportBean hunterImportBean, Session session){
		Blob blob = null;
		Transaction trans = null;
		try {
			trans = session.beginTransaction();
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(hunterImportBean.getExcelBytes()); 
			blob = Hibernate.getLobCreator(session).createBlob(byteArrayInputStream, (long)hunterImportBean.getByteLen()); 
			hunterImportBean.setExcelBlob(blob);
			session.save(hunterImportBean);
			trans.commit();
			logger.debug("Successfully saved the import bean with it's blob!!"); 
		} catch (HibernateException e) {
			e.printStackTrace();
			HunterHibernateHelper.rollBack(trans);
		}finally{
			HunterHibernateHelper.closeSession(session); 
		}
	}
	
	public static Workbook getWorkbookForImportBean(HunterImportBean hunterImportBean){
		logger.debug("Fetching workbook for import bean.."); 
		if(hunterImportBean.getExcelBlob() == null){
			logger.debug("bean does not have import blob. Returning null");
			return null;
		}
		Workbook workbook = null;
		Blob blob = hunterImportBean.getExcelBlob();
		try {
			InputStream inputStream =  blob.getBinaryStream();
			workbook = WorkbookFactory.create(inputStream);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		logger.debug("Successfully recreated workbook!"); 
		return workbook;
	}
	
	public static Workbook getTempSavedWorkbook(String fileName){
		 File file = new File(HunterURLConstants.RESOURCE_TEMPL_FOLDER + "/" + fileName);
		 XSSFWorkbook workbook = null;
	      try {
			FileInputStream fIP = new FileInputStream(file);
			workbook = new XSSFWorkbook(fIP);
			if(workbook != null)
				logger.debug("Successfully created workbook from directory : " + file.getAbsolutePath()); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	      if(file.isFile() && file.exists())
	      {
	         System.out.println(
	         "File opened successfully.");
	      }
	      else
	      {
	         System.out.println(
	         "Error to open workbook!!");
	      }
	     return workbook;
	}
	
	public static void saveWorkbookToTempLocation(Workbook workbook, String fileName){
		logger.debug("Saving file to temp location : " + fileName);
		FileOutputStream out = null;
		try {
			File file = new File(HunterURLConstants.RESOURCE_TEMPL_FOLDER);
			if(!file.exists()){
				file.mkdir();
			}
			out = new FileOutputStream(file);
			workbook.write(out);
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
				logger.debug("Successfully closed output stream!!"); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.debug("Finished saving workbook to temporary directory!!"); 
	}
	
}
