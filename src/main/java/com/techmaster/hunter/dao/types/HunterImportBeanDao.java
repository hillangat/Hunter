package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.imports.beans.HunterImportBean;

public interface HunterImportBeanDao {
	
	public void insertHunterImportBean(HunterImportBean hunterImportBean);
	public void insertHunterImportBeanUsngJDBC(HunterImportBean hunterImportBean);
	public HunterImportBean getHunterImportBeanById(Long importId);
	public List<HunterImportBean> getImportBeanByBeanName(String beanName);
	public List<HunterImportBean> getImportBeanByCrtdUserName(String userName);
	public void updateHunterImportBean(HunterImportBean update);
	public void deleteHunterImportBean(HunterImportBean hunterImportBean);
	public void deleteHunterImportById(Long importId);

}
