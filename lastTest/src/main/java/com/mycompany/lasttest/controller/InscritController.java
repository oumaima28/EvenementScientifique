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
import com.mycompany.lasttest.util.HashageUtil;
import com.mycompany.lasttest.util.JsfUtil;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Scope(value = "session")
@Component(value = "inscritController")
@ELBeanName(value = "inscritController")
public class InscritController {
    //test

    private Inscrit inscrit;
    private List<Inscrit> inscrits;

    @Autowired
    private InscritRepositery inscritRepositery;

    private String confirmEmail;
    private String password;

    public void save() {
        System.out.println("--------------- Sign Up Method---------------");
        inscrit.setBloqued(0);
        System.out.println("----------------Confirm Email-----------------");
        Boolean isConfirmed = inscritRepositery.confirmEmail(inscrit.getEmail(), confirmEmail);
        if (!isConfirmed) {
            JsfUtil.addErrorMessage("Confirmed Email different from Email");
            
        } else {
            Boolean validEmail = inscritRepositery.checkValidityOfEmail(inscrit.getEmail());
            if (!validEmail) {
                JsfUtil.addErrorMessage("Invalid email address");
            } else {
                System.out.println("----------------Login is-> " + inscrit.getLogin() + "--------------");
                System.out.println("----------------Password is-> " + password + "--------------");
                inscrit.setPassword(HashageUtil.sha256(password));
                inscritRepositery.sendEmail(inscrit, password);
                inscritRepositery.save(inscrit);
            }
        }
    }

    public Inscrit getInscrit() {
        if (inscrit == null) {
            inscrit = new Inscrit();
        }
        return inscrit;
    }

    public void setInscrit(Inscrit inscrit) {
        this.inscrit = inscrit;
    }

    public List<Inscrit> getInscrits() {
        if (inscrits == null) {
            inscrits = new ArrayList<>();
        }
        return inscrits;
    }

    public void setInscrits(List<Inscrit> inscrits) {
        this.inscrits = inscrits;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
