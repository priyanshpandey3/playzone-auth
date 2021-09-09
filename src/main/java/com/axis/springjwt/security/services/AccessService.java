package com.axis.springjwt.security.services;

import java.util.List;

import com.axis.springjwt.models.Resource;
import com.axis.springjwt.models.ResourcePerm;
import com.axis.springjwt.models.Role;
import com.axis.springjwt.models.RolePermission;
import com.axis.springjwt.models.User;



public interface AccessService {

	
	public Resource addResource(Resource resource);
	public Role addRole(Role role);
	
	public RolePermission grantPermissions(RolePermission rolePermission);
	
	public List<RolePermission>  getPermissionsByRoleId(int roleId);
	
	public Role getRoleByRoleID(int roleID);
	
	public Role getByRoleName(String roleName);
	
	public String updatepermissionsByRoleIDAndResourceId( ResourcePerm resourcePerm);
	
	public List<Role> getAllRole();
	
	public List<Resource> getAllResource();
	
	public List<User> getAllUsers();
	


	

	
	
	//public String getPermissionsByRoleName(ResourcePerm resourcePerm);
	
	
	
}
