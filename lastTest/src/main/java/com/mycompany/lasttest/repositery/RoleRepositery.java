package com.mycompany.lasttest.repositery;

import com.mycompany.lasttest.bean.Inscrit;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.lasttest.bean.Role;
import com.mycompany.lasttest.custom.RoleRepositeryCustom;
import java.util.List;

public interface RoleRepositery extends JpaRepository<Role, Long>, RoleRepositeryCustom{

    List<Role> findByInscrit(Inscrit inscrit);
}
