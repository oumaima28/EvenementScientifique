package com.mycompany.lasttest.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.lasttest.bean.Role;
import com.mycompany.lasttest.custom.RoleRepositeryCustom;

public interface RoleRepositery extends JpaRepository<Role, Long>, RoleRepositeryCustom{

}
