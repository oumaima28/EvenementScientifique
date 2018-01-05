package com.mycompany.lasttest.implementationCustom;

import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.custom.InscritRepositeryCustom;
import com.mycompany.lasttest.repositery.InscritRepositery;
import com.mycompany.lasttest.util.EmailUtil;
import com.mycompany.lasttest.util.HashageUtil;
import com.mycompany.lasttest.util.JsfUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;

public class InscritRepositeryImpl implements InscritRepositeryCustom {

    @Autowired
    private InscritRepositery inscritRepositery;

    @Override
    public Boolean confirmEmail(String email, String emailConfirmation) {
        if (email.equals(emailConfirmation)) {
            return true;

        } else {
            return false;
        }
    }

    @Override
    public int sendEmail(Inscrit inscrit, String password) {
        String message = "We thank u for subscribing in our website <br/>" + "Here is your login Informations: <br/>"+
                "Login: " +inscrit.getLogin() + "<br/> Password: " +password + "<br/> Please make sure to remember your password";
        String subject = "Welcome Mail to "+inscrit.getLogin();
        try {
           Boolean sent = EmailUtil.sendMail(null, null, message, inscrit.getEmail(), subject);
           return 1;
        } catch (MessagingException ex) {
            Logger.getLogger(InscritRepositeryImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public Boolean checkValidityOfEmail(String email) {
        List<String> emails = inscritRepositery.findAllEmails();
        System.out.println("emails -> "+emails);
        if (!emails.contains(email)) return true;
        else {
            return false;
        }
    }

    @Override
    public List<Inscrit> findByEmails(List<String> emails) {
        List<Inscrit> inscrits = new ArrayList<>();
           for (String email : emails) {
             inscrits.add(inscritRepositery.findByEmail(email));
        }
           return inscrits;
    }
    
     @Override
    public Object[] seConnecter(String login, String pswrd) {
        if (login == null) {
            System.out.println("=== login null");
            return new Object[]{-1, null};
        } else {
            Inscrit inscrit = inscritRepositery.findOne(login);
            if (inscrit == null) { //no user 
                JsfUtil.addErrorMessage("Votre login est incorrecte !", "login");
                return new Object[]{-2, null};
            } else if (!HashageUtil.sha256(pswrd).equals(inscrit.getPassword())) {
                JsfUtil.addErrorMessage("Votre mot de passe est incorrecte", "password");
                return new Object[]{-3, null};
            } else {
                return new Object[]{1, inscrit};
            }
        }
    }

    @Override
    public Inscrit cloneInscrit(Inscrit inscrit) {
        Inscrit clonedInscrit = new Inscrit();
        clonedInscrit.setBloqued(inscrit.getBloqued());
        clonedInscrit.setDescription(inscrit.getDescription());
        clonedInscrit.setArticles(inscrit.getArticles());
        clonedInscrit.setEmail(inscrit.getEmail());
        clonedInscrit.setLogin(inscrit.getLogin());
        clonedInscrit.setNom(inscrit.getNom());
        clonedInscrit.setPaiements(inscrit.getPaiements());
        clonedInscrit.setPassword(inscrit.getPassword());
        clonedInscrit.setPrenom(inscrit.getPrenom());
        clonedInscrit.setRevisions(inscrit.getRevisions());
        clonedInscrit.setTel(inscrit.getTel());
        return clonedInscrit;
    }

}
