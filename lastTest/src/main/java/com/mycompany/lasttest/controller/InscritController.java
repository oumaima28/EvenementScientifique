package com.mycompany.lasttest.controller;

import java.util.ArrayList;
import java.util.List;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

import com.mycompany.lasttest.bean.Article;
import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.bean.Role;
import com.mycompany.lasttest.repositery.EventRepositery;
import com.mycompany.lasttest.repositery.InscritRepositery;
import com.mycompany.lasttest.util.HashageUtil;
import com.mycompany.lasttest.util.JsfUtil;
import com.mycompany.lasttest.util.SearchUtil;
import com.mycompany.lasttest.util.SessionUtil;
import java.io.IOException;
import java.sql.Date;
import java.util.Vector;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.script.ScriptException;

@Scope(value = "session")
@Component(value = "inscritController")
@ELBeanName(value = "inscritController")
public class InscritController {

    private Inscrit inscrit;
    private List<Inscrit> inscrits;

    @Autowired
    private InscritRepositery inscritRepositery;
    @Autowired
    private EventRepositery eventRepositery;

    private String confirmEmail;
    private String password;
    private int checkedEmail = 0;

    private Inscrit connected;
    private String login;
    private int messageValue;

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    //forgot password
    private String email;
    private String confirmeEmail;

    public List<Event> getLatestEvents() {
        return eventRepositery.findNewEvents(SearchUtil.convertToSqlDate(new java.util.Date()));
    }

    public List<Vector> getTags() {
        List<Vector> vectors = new ArrayList<>();
        for (Event event : eventRepositery.findNewEvents(SearchUtil.convertToSqlDate(new java.util.Date()))) {
            List<String> tags = eventRepositery.findTagByEvent(event.getId());
            System.out.println(tags);
            Vector vector = new Vector(tags.size());
            vector.addAll(tags);
            System.out.println(vector);
            vectors.add(vector);
        }
        return vectors;
    }

    public void sendEmailforgotPswrd() {
        if (email.equals(confirmeEmail)) {
            JsfUtil.addSuccessMessage("Veuillez consulter votre boite email svp ! ", "successMsg");
            inscritRepositery.emailForgetPswrd(email);
        } else {
            JsfUtil.addErrorMessage("Les deux champs ne sont pas identiques ", "errorMsg");
        }
    }

    public void verifyPassword() throws IOException, ScriptException, NoSuchMethodException {
        if (HashageUtil.sha256(oldPassword).equals(inscrit.getPassword())) {
            if (newPassword.equals(confirmPassword)) {
                if (oldPassword.equals(confirmPassword)) {
                    JsfUtil.addErrorMessage("Vous avez saisi le meme mot de passe !", "confirmPassword");
                    return;
                }
                JsfUtil.addSuccessMessage("Mot de passe modified !", "newPassword");
                inscrit.setPassword(HashageUtil.sha256(newPassword));
                inscritRepositery.save(inscrit);
            } else {
                JsfUtil.addErrorMessage("les mot de passe sont pas identiques !", "confirmPassword");
            }
        } else {
            JsfUtil.addErrorMessage("Mor de passe incorrect !", "newPassword");
        }
    }

    public void modifyInscrit() {
        System.err.println("here 1");
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String desc = request.getParameter("description");
        String tel = request.getParameter("tel");
        String email = request.getParameter("email");
        System.err.println("here 2");
        inscrit.setNom(nom);
        inscrit.setPrenom(prenom);
        inscrit.setDescription(desc);
        inscrit.setTel(tel);
        inscrit.setEmail(email);
        inscritRepositery.save(inscrit);
        System.err.println("here 3");
    }

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
            System.out.println("sessionUtil connected ->" + SessionUtil.getAttribute("connected"));
            SessionUtil.redirect("../profil/profil.jsf");
        }
    }

//    public Inscrit getConnectedInscrit() {
//        Inscrit thisInscrit = inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin());
//        return thisInscrit;
//    }
    public void signOut() throws IOException {
        System.out.println("3iw");
        SessionUtil.deconnectUser();
        SessionUtil.redirect("../connection/signIn.jsf");
    }

    public void save() throws IOException {
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
                SessionUtil.redirect("../connection/signIn.jsf");
            }
        }
    }

    public void checkEmailValid() {
        System.out.println("3iw");
//        if (inscrit.getEmail().equals("")) {
//            JsfUtil.addErrorMessage("Remplir le champ login svp ! ", "confirmEmail");
//        }
//        if (confirmEmail.equals("")) {
//            JsfUtil.addErrorMessage("Remplir le champ login svp ! ", "email");
//        }
        Boolean isConfirmed = inscritRepositery.confirmEmail(inscrit.getEmail(), confirmEmail);
        if (!isConfirmed) {
            System.out.println("skhl");
            JsfUtil.addErrorMessage("Confirmed Email different from Email", "nom");
            checkedEmail = 0;
        } else {
            Boolean validEmail = inscritRepositery.checkValidityOfEmail(inscrit.getEmail());
            if (!validEmail) {
                JsfUtil.addErrorMessage("Invalid email address", "nom");
                checkedEmail = 0;
            }
            checkedEmail = 1;
        }
    }

    public void checkConnection() throws IOException {
        if (inscritRepositery.connectedInscrit() == null) {
            System.out.println("dkhl");
            SessionUtil.redirect("../connection/signIn.jsf");
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
            connected = inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin());
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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmeEmail() {
        return confirmeEmail;
    }

    public void setConfirmeEmail(String confirmeEmail) {
        this.confirmeEmail = confirmeEmail;
    }
}
