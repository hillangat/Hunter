package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.obj.beans.UserRole;

public interface UserRoleDao {
	
	public void insertUserRole(UserRole userRole);
	public UserRole getUserRoleById(Long roleId);
	public List<UserRole> getAllUserRole(); 
	public String addRoleToUser(Long userId, Long userRoleId);
	public String removeRoleFromUser(Long userId, Long userRoleId);
	public List<UserRole> getUserRolesForUserId(Long userId);
	public void editUserRole(UserRole userRole);
	public String deleteUserRole(UserRole userRole);
	public void createUserRole(UserRole userRole);  

}
