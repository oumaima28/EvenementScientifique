package com.mycompany.lasttest.implementationCustom;

import com.mycompany.lasttest.bean.Article;
import com.mycompany.lasttest.bean.Auteur;
import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.bean.Role;
import com.mycompany.lasttest.custom.InscritRepositeryCustom;
import com.mycompany.lasttest.repositery.InscritRepositery;
import com.mycompany.lasttest.repositery.ArticleRepositery;
import com.mycompany.lasttest.repositery.AuteurRepositery;
import com.mycompany.lasttest.repositery.RoleRepositery;
import com.mycompany.lasttest.util.EmailUtil;
import com.mycompany.lasttest.util.HashageUtil;
import com.mycompany.lasttest.util.JsfUtil;
import com.mycompany.lasttest.util.RandomStringUtil;
import com.mycompany.lasttest.util.SessionUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;

public class InscritRepositeryImpl implements InscritRepositeryCustom {

    @Autowired
    private InscritRepositery inscritRepositery;
    @Autowired
    private ArticleRepositery articleRepositery;
    @Autowired
    private AuteurRepositery auteurRepositery;
    @Autowired
    private RoleRepositery roleRepositery;

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
        String message = "We thank u for subscribing in our website <br/>" + "Here is your login Informations: <br/>"
                + "Login: " + inscrit.getLogin() + "<br/> Password: " + password + "<br/> Please make sure to remember your password";
        String subject = "Welcome Mail to " + inscrit.getLogin();
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
        System.out.println("emails -> " + emails);
        if (!emails.contains(email)) {
            return true;
        } else {
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

    @Override
    public Inscrit connectedInscrit() {
        Inscrit inscrit = inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin());
        System.out.println("======= inscrit connected  " + inscrit);
        return inscrit;
    }

    @Override
    public void emailForgetPswrd(String emailDestination) {
        Inscrit inscrit = inscritRepositery.findByEmail(emailDestination);
        if (inscrit == null) {
            JsfUtil.addErrorMessage("Cet email ne correspend à aucun utilisateur ! ", "email");
        } else {
            String pswrd = RandomStringUtil.generate();
            String msg = "Bienvenu Mr. " + inscrit.getNom() + " " + inscrit.getPrenom() + " ,<br/>"
                    + "D'après votre demande de reinitialiser le mot de passe de votre compte, nous avons generer ce mot de passe pour vous.\n"
                    + "<br/><br/>"
                    + "      Nouveau Mot de Passe: <br/><center><b>"
                    + pswrd
                    + "</b></center><br/><br/><b><i>PS:</i></b>  SVP changer ce mot de passe apres que vous avez connecter pour des raison du securité .<br/> Cree votre propre mot de passe";
            String Subject = "Mote de passe ";
            inscrit.setPassword(HashageUtil.sha256(pswrd));
            inscritRepositery.save(inscrit);
            try {
                EmailUtil.sendMail(null, pswrd, msg, emailDestination, Subject);
            } catch (MessagingException ex) {
                Logger.getLogger(InscritRepositeryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void sendEmailOfEtatArticle(Article article) {
        List<String> emails = articleRepositery.findEmailsByArticle(article.getId());
        List<Auteur> auteurs = new ArrayList<>();
        for (String email : emails) {
            auteurs.add(auteurRepositery.findByEmail(email));
        }
        for (Auteur auteur : auteurs) {
            System.out.println(auteur);
            String msg = "Bienvenu Mr. " + auteur.getNom() + " " + auteur.getPrenom() + " ,<br/>"
                    + "D'après votre envoie d'un Article a travers notre site, on vous envoie ci dessus la reponse.\n"
                    + "<br/><b>Details de l'article:</b><br/>  Nom: " + article.getTitre() + "<br/>"
                    + "  Description: " + article.getDescription() + "  <center><b>";
            if (article.getEtat().equals("Accepted")) {
                msg += "      Votre article a bien ete accepte</b></center><br/><br/><b><i>PS:</i></b>  Il faut payer l'article rapidement s'il a ete acceptee";
            } else if (article.getEtat().equals("Not accepted")) {
                msg += "        Votre article n'a pas ete accepte malheureusement</b></center><br/><br/><b><i>PS:</i></b>  Essayer pour d'autres evenements";
            }
            String subject = "Reponse Envoie Article " + article.getTitre();
            try {
                EmailUtil.sendMail(null, null, msg, auteur.getEmail(), subject);
            } catch (MessagingException ex) {
                Logger.getLogger(InscritRepositeryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void sendEmailofInvitationRapporteur(Role role, Boolean b) {
        Inscrit orga = roleRepositery.findInscritByEvent(role.getEvent().getId());
        String msg = "Bienvenu Mr. " + orga.getNom() + " " + orga.getPrenom() + " ,<br/>"
                + "D'après votre demande d'inviter Mr " + role.getInscrit().getNom() + " " + role.getInscrit().getPrenom() + ". On vous envoie ci dessus la reponse.\n"
                + "<br/><br/><center><b>";
        String subject = "Invitation du rapporteur " + role.getInscrit().getNom() + " " + role.getInscrit().getPrenom();
        if (b.equals(Boolean.TRUE)) {
            msg += "      Votre invitation a bien ete accepte</b></center><br/><br/>Felicitation";
        } else {
            msg += "        Votre invitation n'a pas ete accepte malheureusement</b></center><br/><br/><b><i>PS:</i></b>  Essayer d'inviter d'autres rapporteurs";
        }
        try {
            System.out.println(orga.getEmail());
            EmailUtil.sendMail(null, null, msg, orga.getEmail(), subject);
        } catch (MessagingException ex) {
            Logger.getLogger(InscritRepositeryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
