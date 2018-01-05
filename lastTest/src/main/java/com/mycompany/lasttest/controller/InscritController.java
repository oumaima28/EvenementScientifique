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
import com.mycompany.lasttest.util.SessionUtil;
import java.io.IOException;
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
    private int checkedEmail = 0;

    private Inscrit connected;
    private String login;
    private int messageValue;

    public void seConnecter() throws IOException {
        Object[] result = inscritRepositery.seConnecter(login, password);
        messageValue = (int) result[0];
        int value = (int) result[0];
        inscrit = (Inscrit) result[1];
        if (value < 0) {
            System.out.println(" ++++++ " + value);
            
        } else {
            System.out.println("----- controllerInscrit connected");
            System.out.println("=== inscrit information : " + inscrit);
            SessionUtil.setAttribute("connected", inscrit);
            System.out.println("sessionUtil connected ->"+SessionUtil.getAttribute("connected"));
            SessionUtil.redirect("../event/create.jsf");
        }
    }

//    public Inscrit getConnectedInscrit() {
//        Inscrit thisInscrit = inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin());
//        return thisInscrit;
//    }

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
                SessionUtil.setAttribute("connected", inscrit);
            }
        }
    }

    public void checkEmail() {
        System.out.println("3iw");
        Boolean isConfirmed = inscritRepositery.confirmEmail(inscrit.getEmail(), confirmEmail);
        if (!isConfirmed) {
            JsfUtil.addErrorMessage("Confirmed Email different from Email");
            checkedEmail = 0;
        } else {
            Boolean validEmail = inscritRepositery.checkValidityOfEmail(inscrit.getEmail());
            if (!validEmail) {
                JsfUtil.addErrorMessage("Invalid email address");
                checkedEmail = 0;
            }
            checkedEmail = 1;
        }
    }

    public void connect() {
        System.out.println("dkhl");
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

    public Inscrit getConnected() {
        if (connected == null) {
            connected = new Inscrit();
        }
        return connected;
    }

    public void setConnected(Inscrit connected) {
        this.connected = connected;
    }

    public int getCheckedEmail() {
        return checkedEmail;
    }

    public void setCheckedEmail(int checkedEmail) {
        this.checkedEmail = checkedEmail;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getMessageValue() {
        return messageValue;
    }

    public void setMessageValue(int messageValue) {
        this.messageValue = messageValue;
    }

}
