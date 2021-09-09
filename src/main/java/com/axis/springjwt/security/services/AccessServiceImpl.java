package com.axis.springjwt.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.axis.springjwt.models.Resource;
import com.axis.springjwt.models.ResourcePerm;
import com.axis.springjwt.models.Role;
import com.axis.springjwt.models.RolePermission;
import com.axis.springjwt.models.User;
import com.axis.springjwt.repository.AccessRepository;
import com.axis.springjwt.repository.PermissionRepository;
import com.axis.springjwt.repository.RoleRepository;
import com.axis.springjwt.repository.UserRepository;



@Service
public class AccessServiceImpl implements AccessService {

	@Autowired
	AccessRepository accessRepository;

	@Autowired
	PermissionRepository permissionRepository;


	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;


	public Resource addResource(Resource resource) {

		return accessRepository.save(resource) ;
	}

	public RolePermission grantPermissions(RolePermission rolePermission) {

		return permissionRepository.save(rolePermission);

	}


	public Role addRole(Role role) {

		return roleRepository.save(role) ;
	}

	@Override
	public List<RolePermission> getPermissionsByRoleId(int roleId) {

		return permissionRepository.findByroleId( roleId);
	}


	@Override
	public Role getRoleByRoleID(int roleID) {

		return roleRepository.findByroleID(roleID);
	}

	@Override
	public Role getByRoleName(String roleName) {

		return roleRepository.findByroleName(roleName);
	}

	public List<Role> getAllRole() {
		return roleRepository.findAll();
	}
	
	public List<Resource> getAllResource() {
		return accessRepository.findAll();
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public String updatepermissionsByRoleIDAndResourceId(ResourcePerm resourcePerm) {

		Role role=getByRoleName(resourcePerm.getRoleName());

		System.out.println(resourcePerm.getPermissionList());

		for(RolePermission p:resourcePerm.getPermissionList()) {

			p.setRoleId(role.getRoleID());
		}

		System.out.println(resourcePerm);

		for(RolePermission p:resourcePerm.getPermissionList()) {

			RolePermission rp=permissionRepository.findByRoleIdAndResourceId(p.getRoleId(),p.getResourceId());

			rp.setCanView(p.isCanView());
			rp.setCanEdit(p.isCanEdit());
			rp.setCanAdd(p.isCanAdd());
			rp.setCanDelete(p.isCanDelete());
			System.out.println(rp);
			permissionRepository.save(rp);

		}
		return "success";
	}



}
