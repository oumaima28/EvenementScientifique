package com.mycompany.lasttest.controller;

import java.util.ArrayList;
import java.util.List;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mycompany.lasttest.bean.Role;
import com.mycompany.lasttest.repositery.RoleRepositery;

@Scope(value = "session")
@Component(value = "roleController")
@ELBeanName(value = "roleController")
public class RoleController {

	private Role role;
	private List<Role> roles;
	
	@Autowired
	private RoleRepositery roleRepositery;

	
	
	public Role getRole() {
		if(role == null) {
			role = new Role();
		}
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Role> getRoles() {
		if(roles == null) {
			roles = new ArrayList<>();
		}
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
