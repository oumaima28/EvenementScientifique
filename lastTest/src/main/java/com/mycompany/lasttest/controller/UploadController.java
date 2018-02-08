/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lasttest.controller;

//import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.ocpsoft.rewrite.el.ELBeanName;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author HP
 */
@ManagedBean
public class UploadController {

    private UploadedFile file;
    private UploadedFile fichier;
    private String value;

    public UploadedFile getFile() {
        System.out.println("3iw");
        System.out.println("ha lfichier"+file);
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFichier() {
        System.out.println("1");
        if (fichier == null){
            System.out.println("2");
        }
        return fichier;
    }

    public void setFichier(UploadedFile fichier) {
        this.fichier = fichier;
    }

    public String getValue() {
        System.out.println("aaaaa");
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void upload() {
        System.out.println("dkhl");
//        System.out.println("ha lfile"+file);
        System.out.println("ha lfile"+fichier);
        System.out.println("ha string"+value);
        if (file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);

            // makaydkholx lya lhad l if ila knt dayraah fost proj dima kaygoli rah file null 
        }
    }
}
