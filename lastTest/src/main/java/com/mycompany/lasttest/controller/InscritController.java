package com.mycompany.lasttest.controller;

import java.util.ArrayList;
import java.util.List;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mycompany.lasttest.bean.Article;
import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.bean.Role;
import com.mycompany.lasttest.repositery.InscritRepositery;

@Scope(value = "session")
@Component(value = "inscritController")
@ELBeanName(value = "inscritController")
public class InscritController {
	//test
	
	private Inscrit inscrit;
	private List<Inscrit> inscrits;

	@Autowired
	private InscritRepositery inscritRepositery;

	public void save() {
		System.out.println("hani");
		System.out.println(inscrit);
		inscrit.setBloqued(0);
		inscrit.setRole(null);
		inscritRepositery.save(inscrit);
		inscritRepositery.customTest(inscrit);
		System.out.println("ha mol lemail"+inscritRepositery.findByEmail("lwezEmail"));
		
		List <Inscrit> inscrits =inscritRepositery.findByBloqued(0);
		System.out.println(inscrits.get(0));
	}

	
	public Inscrit getInscrit() {
		if(inscrit == null) {
			inscrit = new Inscrit();
		}
		return inscrit;
	}

	public void setInscrit(Inscrit inscrit) {
		this.inscrit = inscrit;
	}


	public List<Inscrit> getInscrits() {
		if(inscrits == null) {
			inscrits = new ArrayList<>();
		}
		return inscrits;
	}


	public void setInscrits(List<Inscrit> inscrits) {
		this.inscrits = inscrits;
	}

	
}
