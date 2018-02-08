package com.mycompany.lasttest.implementationCustom;

import com.mycompany.lasttest.bean.Auteur;
import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.custom.AuteurRepositeryCustom;
import com.mycompany.lasttest.repositery.AuteurRepositery;
import org.springframework.beans.factory.annotation.Autowired;

public class AuteurRepositeryImpl implements AuteurRepositeryCustom{

    @Autowired
    private AuteurRepositery auteurRepositery;
    
    @Override
    public Auteur cloneAuteur(Auteur auteur,Auteur clonedAuteur) {
        clonedAuteur.setCin(auteur.getCin());
        clonedAuteur.setDescription(auteur.getDescription());
        clonedAuteur.setEmail(auteur.getEmail());
        clonedAuteur.setNom(auteur.getNom());
        clonedAuteur.setPrenom(auteur.getPrenom());
        return clonedAuteur;
    }
    
    @Override
    public void saveInscrit(Inscrit inscrit) {
         Auteur auteur = new Auteur();
         auteur.setCin(inscrit.getCin());
         auteur.setDescription(inscrit.getDescription());
         auteur.setEmail(inscrit.getEmail());
         auteur.setNom(inscrit.getNom());
         auteur.setPrenom(inscrit.getPrenom());
         auteurRepositery.save(auteur);
    }
}
