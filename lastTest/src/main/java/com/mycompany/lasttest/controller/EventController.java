package com.mycompany.lasttest.controller;

import java.util.ArrayList;
import java.util.List;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.repositery.EventRepositery;
import com.mycompany.lasttest.repositery.InscritRepositery;
import com.mycompany.lasttest.repositery.RoleRepositery;
import com.mycompany.lasttest.util.SessionUtil;

@Scope(value = "session")
@Component(value = "eventController")
@ELBeanName(value = "eventController")
public class EventController {

    private Event event;
    private List<Event> events;

    @Autowired
    private EventRepositery eventRepositery;

    @Autowired
    private InscritRepositery inscritRepositery;

    @Autowired
    private RoleRepositery roleRepositery;

    //montant du event en string pour transformer en double
    private String montant;
    //List des pays available dans la combobox
    private List<String> pays;

    //domaines(tags) d'ou choisir
    private List<String> availableTags;
    private List<String> choosedTags;

    //organisateur a creer
    private Inscrit connectedOrganisateur;

    private String selectedRapporteurEmail;
    private List<String> rapporteursEmails;

    public void save() {
        event.setMontant(new Double(montant));
        System.out.println("ha l event" + event);
        System.out.println("ha tags" + choosedTags);
        System.out.println("ha les emails" + rapporteursEmails);
        Event testEvent = eventRepositery.findOne(1L);
        System.out.println("connected->" + (Inscrit) SessionUtil.getAttribute("connected"));
        connectedOrganisateur = inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin());
        System.out.println("ha connectedUser" + connectedOrganisateur);
        eventRepositery.createEvent(connectedOrganisateur, event, rapporteursEmails, choosedTags);
    }

    public void addEmailToList() {
        rapporteursEmails.add(selectedRapporteurEmail);
    }

    public void removeEmailFromList(String deletedEmail) {
        rapporteursEmails.remove(deletedEmail);
    }

    public Event getEvent() {
        if (event == null) {
            event = new Event();
        }
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Event> getEvents() {
        if (events == null) {
            events = new ArrayList<>();
        }
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public List<String> getPays() {
        if (pays == null) {
            pays = new ArrayList<>();
            pays.add("Morroco");
            pays.add("France");
            pays.add("America");
        }
        return pays;
    }

    public void setPays(List<String> pays) {
        this.pays = pays;
    }

    public List<String> getAvailableTags() {
        if (availableTags == null) {
            availableTags = new ArrayList<>();
            availableTags.add("BigData");
            availableTags.add("Social Engineering");
            availableTags.add("Electronics");
            availableTags.add("Physiques");
            availableTags.add("Mathematics");
            availableTags.add("Informatics");
            availableTags.add("Robotics");
            availableTags.add("Artificial Intelligence");
        }
        return availableTags;
    }

//    public Inscrit getConnectedInscrit() {
//        Inscrit thisInscrit = inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin());
//        return thisInscrit;
//    }
    public void setAvailableTags(List<String> availableTags) {
        this.availableTags = availableTags;
    }

    public List<String> getChoosedTags() {
        if (choosedTags == null) {
            choosedTags = new ArrayList<>();
        }
        return choosedTags;
    }

    public void setChoosedTags(List<String> choosedTags) {
        this.choosedTags = choosedTags;
    }

    public Inscrit getConnectedOrganisateur() {
//        if(connectedOrganisateur == null){
//            connectedOrganisateur = getConnectedInscrit();
//        }
        return connectedOrganisateur;
    }

    public void setConnectedOrganisateur(Inscrit connectedOrganisateur) {
        this.connectedOrganisateur = connectedOrganisateur;
    }

    public String getSelectedRapporteurEmail() {
        return selectedRapporteurEmail;
    }

    public void setSelectedRapporteurEmail(String selectedRapporteurEmail) {
        this.selectedRapporteurEmail = selectedRapporteurEmail;
    }

    public List<String> getRapporteursEmails() {
        if (rapporteursEmails == null) {
            rapporteursEmails = new ArrayList<>();
        }
        return rapporteursEmails;
    }

    public void setRapporteursEmails(List<String> rapporteursEmails) {
        this.rapporteursEmails = rapporteursEmails;
    }

}
