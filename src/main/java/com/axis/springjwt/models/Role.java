package com.axis.springjwt.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @Entity
 * 
 * @Table(name = "roles") public class Role {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;
 * 
 * @Enumerated(EnumType.STRING)
 * 
 * @Column(length = 20) private ERole roleName;
 * 
 * public Role() {
 * 
 * }
 * 
 * public Role(ERole name) { this.roleName = name; }
 * 
 * public Integer getId() { return id; }
 * 
 * public void setId(Integer id) { this.id = id; }
 * 
 * public ERole getName() { return roleName; }
 * 
 * public void setName(ERole name) { this.roleName = name; } }
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int roleID;
	
	@Column(name="ROLE_NAME")
	private String roleName;
	
	@Column(name="ROLE_DESCRIPTION")
	private String roleDescription;

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	
	
}
