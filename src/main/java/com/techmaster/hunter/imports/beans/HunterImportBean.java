package com.techmaster.hunter.imports.beans;

import java.sql.Blob;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.Workbook;

import com.techmaster.hunter.obj.beans.AuditInfo;

public class HunterImportBean {
	
	private Long importId;
	private String originalFileName;
	private double byteLen;
	private String beanName;
	private String status;
	private String tempLocation;
	private AuditInfo auditInfo;
	private byte[] excelBytes;
	private Blob excelBlob;
	
	@SuppressWarnings("unused")
	private Workbook workbook;
	
	public HunterImportBean() { 
		super();
	}
	
	public Long getImportId() {
		return importId;
	}
	public void setImportId(Long importId) {
		this.importId = importId;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public double getByteLen() {
		return byteLen;
	}
	public void setByteLen(double byteLen) {
		this.byteLen = byteLen;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}
	public byte[] getExcelBytes() {
		return excelBytes;
	}
	public void setExcelBytes(byte[] excelBytes) {
		this.excelBytes = excelBytes;
	}
	public Workbook getWorkbook() {
		return ImportHelper.getWorkbookForImportBean(this);  
	}
	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Blob getExcelBlob() {
		return excelBlob;
	}
	public void setExcelBlob(Blob excelBlob) {
		this.excelBlob = excelBlob;
	}
	public String getTempLocation() {
		return tempLocation;
	}
	public void setTempLocation(String tempLocation) {
		this.tempLocation = tempLocation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((auditInfo == null) ? 0 : auditInfo.hashCode());
		result = prime * result
				+ ((beanName == null) ? 0 : beanName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(byteLen);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Arrays.hashCode(excelBytes);
		result = prime * result
				+ ((importId == null) ? 0 : importId.hashCode());
		result = prime
				* result
				+ ((originalFileName == null) ? 0 : originalFileName.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((tempLocation == null) ? 0 : tempLocation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HunterImportBean other = (HunterImportBean) obj;
		if (auditInfo == null) {
			if (other.auditInfo != null)
				return false;
		} else if (!auditInfo.equals(other.auditInfo))
			return false;
		if (beanName == null) {
			if (other.beanName != null)
				return false;
		} else if (!beanName.equals(other.beanName))
			return false;
		if (Double.doubleToLongBits(byteLen) != Double
				.doubleToLongBits(other.byteLen))
			return false;
		if (!Arrays.equals(excelBytes, other.excelBytes))
			return false;
		if (importId == null) {
			if (other.importId != null)
				return false;
		} else if (!importId.equals(other.importId))
			return false;
		if (originalFileName == null) {
			if (other.originalFileName != null)
				return false;
		} else if (!originalFileName.equals(other.originalFileName))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tempLocation == null) {
			if (other.tempLocation != null)
				return false;
		} else if (!tempLocation.equals(other.tempLocation))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "HunterImportBean [importId=" + importId + ", originalFileName="
				+ originalFileName + ", byteLen=" + byteLen + ", beanName="
				+ beanName + ", status=" + status + ", tempLocation="
				+ tempLocation + ", auditInfo=" + auditInfo + ", excelBytes="
				+ Arrays.toString(excelBytes) + "]";
	}

	

	

	
	
	
	

}
