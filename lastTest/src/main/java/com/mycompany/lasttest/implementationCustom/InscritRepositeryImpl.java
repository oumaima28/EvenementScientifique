package com.mycompany.lasttest.implementationCustom;

import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.custom.InscritRepositeryCustom;
import com.mycompany.lasttest.repositery.InscritRepositery;
import com.mycompany.lasttest.util.EmailUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;

public class InscritRepositeryImpl implements InscritRepositeryCustom {

    @Autowired
    private InscritRepositery inscritRepositery;

    @Override
    public void customTest(Inscrit inscrit) {
        System.out.println("ha l'inscrit: " + inscrit);

    }

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

}
