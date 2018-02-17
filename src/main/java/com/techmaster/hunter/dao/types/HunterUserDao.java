package com.techmaster.hunter.dao.types;

import java.util.List;
import java.util.Map;

import com.techmaster.hunter.json.HunterSelectValue;
import com.techmaster.hunter.json.HunterUserJson;
import com.techmaster.hunter.obj.beans.HunterUser;
import com.techmaster.hunter.obj.beans.UserLoginBean;

public interface HunterUserDao {
	
	
	public void insertHunterUser(HunterUser user);
	public void insertHunterUsers(List<HunterUser> hunterUsers);
	public void deleteHunterUserById(Long userId);
	public String validateAndDeleteById(Long userId);
	public void deleteHunterUser(HunterUser user);
	public HunterUser getHunterUserById(Long id);
	public List<HunterUser> getAllUsers();
	public void updateUser(HunterUser update);
	public Long getNextUserId();
	public HunterUser getUserByUserName(String userName);
	public Map<String, List<String>> getFullNamesForUserNames(List<String> userNames);
	
	public List<HunterUserJson> getAllUsersWhoAreClients();
	public List<HunterUserJson> getAllUserJson();
	
	public List<UserLoginBean> getUserLoginBeanByUserName(String userName);
	public void updateUserLoginBean(UserLoginBean userLoginBean);
	public String getClientsForAngularUI();
	public List<HunterSelectValue> getAllUsersSelValues();
	
	

}
