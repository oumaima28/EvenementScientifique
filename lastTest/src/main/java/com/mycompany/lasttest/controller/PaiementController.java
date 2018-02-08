package com.mycompany.lasttest.controller;

import com.mycompany.lasttest.bean.Article;
import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import java.util.ArrayList;
import java.util.List;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mycompany.lasttest.bean.Paiement;
import com.mycompany.lasttest.repositery.ArticleRepositery;
import com.mycompany.lasttest.repositery.EventRepositery;
import com.mycompany.lasttest.repositery.InscritRepositery;
import com.mycompany.lasttest.repositery.PaiementRepositery;
import com.mycompany.lasttest.repositery.RoleRepositery;
import com.mycompany.lasttest.util.SessionUtil;

@Scope(value = "session")
@Component(value = "paiementController")
@ELBeanName(value = "paiementController")
public class PaiementController {

    private Paiement paiement;
    private List<Paiement> paiements;

    private Paiement selectedPaiement;

    private String payedS;

    @Autowired
    private ArticleRepositery articleRepositery;
    @Autowired
    private PaiementRepositery paiementRepositery;
    @Autowired
    private InscritRepositery inscritRepositery;
    @Autowired
    private EventRepositery eventRepositery;
    @Autowired
    private RoleRepositery roleRepositery;

    public String articleName(Inscrit insc, Event ev) {
        System.err.println(insc);
        System.err.println(ev);
        Article art;
        art = articleRepositery.findByInscritAndEvent(insc, ev);
        return art.getTitre();
    }

    public String payed(int in) {
        if (in == 0) {
            return "Non payé";
        } else {
            return "payé";
        }
    }

    public void payer() {
        if (payedS.equals("Payer")) {
            selectedPaiement.setPayed(1);
        } else if (payedS.equals("Non Payer")) {
            selectedPaiement.setPayed(0);
        }
        System.out.println(selectedPaiement);
        paiementRepositery.save(selectedPaiement);
    }

    public Paiement getPaiement() {
        System.err.println("12 debut get Paiment");

        if (paiement == null) {
            paiement = new Paiement();
        }
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    public List<Paiement> getPaiements() {
        System.err.println("1 debut get Paiment");
//        if (paiements == null) {
        paiements = new ArrayList<>();
        System.err.println("debut get Paiment");
        Inscrit InscritConnected = ((Inscrit) SessionUtil.getAttribute("connected"));
        Inscrit loadedConnected = inscritRepositery.findOne(InscritConnected.getLogin());
        Event event = (Event) SessionUtil.getAttribute("selectedEventForPaiement");
        System.out.println(event);
        if (event != null) {
            paiements = paiementRepositery.findByEvent(eventRepositery.findOne(event.getId()));
        } else {
            for (Event e : roleRepositery.findEventsByRoleOrganisateur(loadedConnected.getLogin())) {
                System.out.println(e);
                paiements.addAll(paiementRepositery.findByEvent(e));
            }
//            paiements = paiementRepositery.findByEvent();
        }
        System.err.println("fin get Paiment" + paiements);
//        }
        return paiements;
    }

    public void setPaiements(List<Paiement> paiements) {
        this.paiements = paiements;
    }

    public Paiement getSelectedPaiement() {
        return selectedPaiement;
    }

    public void setSelectedPaiement(Paiement selectedPaiement) {
        this.selectedPaiement = selectedPaiement;
    }

    public String getPayedS() {
        return payedS;
    }

    public void setPayedS(String payedS) {
        this.payedS = payedS;
    }

}
