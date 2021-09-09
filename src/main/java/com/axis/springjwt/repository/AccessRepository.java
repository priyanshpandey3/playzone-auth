package com.axis.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axis.springjwt.models.Resource;
import com.axis.springjwt.models.RolePermission;




public interface AccessRepository extends JpaRepository<Resource,Integer> {

	RolePermission save(RolePermission rolePermission);

}
