package com.mycompany.lasttest.custom;

import com.mycompany.lasttest.bean.Auteur;
import com.mycompany.lasttest.bean.Inscrit;

public interface AuteurRepositeryCustom {

    Auteur cloneAuteur(Auteur auteur,Auteur clonedAuteur);
    
    void saveInscrit(Inscrit inscrit);
}
