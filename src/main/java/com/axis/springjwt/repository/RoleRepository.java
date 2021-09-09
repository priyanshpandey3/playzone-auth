package com.axis.springjwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axis.springjwt.models.ERole;
import com.axis.springjwt.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	//Optional<Role> findByName(ERole roleName);
	
	public Role findByroleID(int roleId);


	 public Role findByroleName(String roleName);
}
