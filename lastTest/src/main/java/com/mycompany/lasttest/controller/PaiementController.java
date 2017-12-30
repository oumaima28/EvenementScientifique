package com.mycompany.lasttest.controller;

import java.util.ArrayList;
import java.util.List;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mycompany.lasttest.bean.Paiement;
import com.mycompany.lasttest.repositery.PaiementRepositery;

@Scope(value = "session")
@Component(value = "paiementController")
@ELBeanName(value = "paiementController")
public class PaiementController {

	private Paiement paiement;
	private List<Paiement> paiements;
	
	@Autowired
	private PaiementRepositery paiementRepositery;

	
	
	public Paiement getPaiement() {
		if(paiement == null) {
			paiement = new Paiement();
		}
		return paiement;
	}

	public void setPaiement(Paiement paiement) {
		this.paiement = paiement;
	}

	public List<Paiement> getPaiements() {
		if(paiements == null) {
			paiements = new ArrayList<>();
		}
		return paiements;
	}

	public void setPaiements(List<Paiement> paiements) {
		this.paiements = paiements;
	}
	
}
