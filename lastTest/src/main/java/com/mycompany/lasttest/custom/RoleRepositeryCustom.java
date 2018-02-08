package com.mycompany.lasttest.custom;

import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import java.util.List;

public interface RoleRepositeryCustom {

    void createRoleForOrganisateur(Inscrit organisateur, Event event);
    
    void createRoleForRapporteurs(List<Inscrit> rapporteurs, Event event);
    
    void createRoleForInscrit(Event event);

}
