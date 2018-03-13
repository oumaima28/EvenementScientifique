package com.mycompany.lasttest.implementationCustom;

import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.bean.Role;
import com.mycompany.lasttest.custom.RoleRepositeryCustom;
import com.mycompany.lasttest.repositery.InscritRepositery;
import com.mycompany.lasttest.repositery.RoleRepositery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleRepositeryImpl implements RoleRepositeryCustom {

    @Autowired
    private RoleRepositery roleRepositery;
    @Autowired
    private InscritRepositery inscritRepositery;

    @Override
    public void createRoleForOrganisateur(Inscrit organisateur, Event event) {
        Role role = new Role();
        role.setInscrit(organisateur);
        role.setEvent(event);
        role.setRole(1);
        roleRepositery.save(role);
    }

    @Override
    public void createRoleForRapporteurs(List<Inscrit> rapporteurs, Event event) {
        for (Inscrit rapp : rapporteurs) {
            Role role = new Role();
            role.setEvent(event);
            role.setInscrit(rapp);
            role.setRole(2);
            role.setVerifiedRapporteur(false);
            roleRepositery.save(role);
        }
    }
    
       @Override
    public void createRoleForInscrit(Event event) {
           System.err.println("hani");
        Inscrit inscrit = inscritRepositery.connectedInscrit();
           System.out.println("ha inscrit"+inscritRepositery.connectedInscrit());
        Role role = new Role();
        role.setEvent(event);
        role.setInscrit(inscrit);
        role.setRole(3);
        roleRepositery.save(role);
    }

}
