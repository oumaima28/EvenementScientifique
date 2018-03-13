package com.mycompany.lasttest.controller;

import java.util.ArrayList;
import java.util.List;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mycompany.lasttest.bean.Auteur;
import com.mycompany.lasttest.repositery.AuteurRepositery;

@Scope(value = "session")
@Component(value = "auteurController")
@ELBeanName(value = "auteurController")
public class AuteurController {

	private Auteur auteur;
	private List<Auteur> auteurs;
	
	@Autowired
	private AuteurRepositery auteurRepositery;

	
	
	public Auteur getAuteur() {
		if(auteur == null) {
			auteur = new Auteur();
		}
		return auteur;
	}

	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

	public List<Auteur> getAuteurs() {
		if(auteurs == null) {
			auteurs = new ArrayList<>();
		}
		return auteurs;
	}

	public void setAuteurs(List<Auteur> auteurs) {
		this.auteurs = auteurs;
	}
	
}
