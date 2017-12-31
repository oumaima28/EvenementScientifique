/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lasttest.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Inscrit implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String login;
	private String password;
	private int bloqued; // 0->not bloqued, 1->bloqued
	private String nom;
	private String prenom;
	private String tel;
	private String email;
	private String description;
	@OneToMany(mappedBy = "auteur")
	private List<Paiement> paiements;
	@OneToMany(mappedBy = "rapporteur")
	private List<Revision> revisions;
	@OneToMany(mappedBy = "inscrit")
	private List<Article> articles;

	public Inscrit() {
	}

	public List<Paiement> getPaiements() {
		return paiements;
	}

	public void setPaiements(List<Paiement> paiements) {
		this.paiements = paiements;
	}

	public List<Revision> getRevisions() {
		return revisions;
	}

	public void setRevisions(List<Revision> revisions) {
		this.revisions = revisions;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getBloqued() {
		return bloqued;
	}

	public void setBloqued(int bloqued) {
		this.bloqued = bloqued;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + Objects.hashCode(this.login);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Inscrit other = (Inscrit) obj;
		if (!Objects.equals(this.login, other.login)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Inscrit{" + "id=" + login + ", password=" + password + ", bloqued=" + bloqued + ", nom=" + nom
				+ ", prenom=" + prenom + ", tel=" + tel + ", email=" + email + ", description=" + description + '}';
	}

}
