package com.mycompany.lasttest.controller;

import com.mycompany.lasttest.bean.Inscrit;
import java.util.ArrayList;
import java.util.List;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mycompany.lasttest.bean.Role;
import com.mycompany.lasttest.repositery.InscritRepositery;
import com.mycompany.lasttest.repositery.RoleRepositery;
import com.mycompany.lasttest.util.SessionUtil;

@Scope(value = "session")
@Component(value = "roleController")
@ELBeanName(value = "roleController")
public class RoleController {

    private Role role;
    private List<Role> roles;

    @Autowired
    private RoleRepositery roleRepositery;

    @Autowired
    private InscritRepositery inscritRepositery;

    public void removeRoleFromList(Role roleToDelete){
        roles.remove(roleToDelete);
    }
    
    public void editRoleFromList(Role roleToEdit){
        System.out.println("3iw");
    }
    
    public List<Role> findRolesForConnected() {
        Inscrit connected = inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin());
        return roleRepositery.findByInscrit(connected);
    }

    public Role getRole() {
        if (role == null) {
            role = new Role();
        }
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Role> getRoles() {
        if (roles == null) {
            roles = roleRepositery.findByInscrit(inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin()));
            System.out.println("roles");
        }
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
