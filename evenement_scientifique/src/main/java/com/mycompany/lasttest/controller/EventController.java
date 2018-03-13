package com.mycompany.lasttest.controller;

import java.util.ArrayList;
import java.util.List;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.bean.Role;
import com.mycompany.lasttest.repositery.EventRepositery;
import com.mycompany.lasttest.repositery.InscritRepositery;
import com.mycompany.lasttest.repositery.RoleRepositery;
import com.mycompany.lasttest.util.JsfUtil;
import com.mycompany.lasttest.util.SearchUtil;
import com.mycompany.lasttest.util.SessionUtil;
import java.io.IOException;
import java.util.Date;

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

    //Search events for inscrit
    private List<Event> allEvents;
    private String ViewDescription;
    private String nomEventRecherchee;
    private String montantEventMin;
    private String montantEventMax;
    private String descriptionEventRecherche;
    private Date dateDebutMin;
    private Date heureDebutMin;
    private Date heureDebutMax;
    private Date dateDebutMax;
    private Date dateFinMin;
    private Date dateFinMax;
    private List<String> choosedTagsForRecherche;
    private List<String> rechercheTags;

    //watchlist
    private List<Event> watchlist;

    public void save() {
        if (event.getDateDebut().getTime() >= event.getDateFin().getTime()) {
            JsfUtil.addErrorMessage("Date Debut plus grande que date fin");
        }
        if (event.getDateLimiteEnvoieArticle().getTime() >= event.getDateDebut().getTime()) {
            JsfUtil.addErrorMessage("Date Limite Envoie article est plus grande que la date debut de l'evenement");
        } else {
            event.setMontant(new Double(montant));
            Event testEvent = eventRepositery.findOne(1L);
            connectedOrganisateur = inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin());
            eventRepositery.createEvent(connectedOrganisateur, event, rapporteursEmails, choosedTags);
            JsfUtil.addSuccessMessage("Votre evenement a bien ete Enregistrer, Veuillez Consulter la liste des roles");
            event = new Event();
            rapporteursEmails = new ArrayList<>();
            choosedTags = new ArrayList<>();
        }
    }

    public void addEmailToList() {
        Inscrit emailInscrit = inscritRepositery.findByEmail(selectedRapporteurEmail);
        if (emailInscrit.equals(inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin()))) {
            JsfUtil.addErrorMessage("L'email que vous avez entrez n'est pas valable");
        } else {
            rapporteursEmails.add(selectedRapporteurEmail);
        }
    }

    public void removeEmailFromList(String deletedEmail) {
        rapporteursEmails.remove(deletedEmail);
    }

    public void goToAccorderList(Event selectedEvent) throws IOException {
        SessionUtil.setAttribute("selectedEventForAccorderArticles", selectedEvent);
        SessionUtil.redirect("../article/accorderList");
    }

    public void goToPaiementList(Event selectedEvent) throws IOException {
        SessionUtil.setAttribute("selectedEventForPaiement", selectedEvent);
        SessionUtil.redirect("../paiement/paiement");
    }

    //recherche events
    public List<String> getTags(Event event) {
        return eventRepositery.findTagByEvent(event.getId());
    }

    public void getDescription(Event event) {
        ViewDescription = event.getDescription();
    }

    public void addTagsToList() {
        rechercheTags.addAll(choosedTagsForRecherche);
    }

    public void deleteTagFromList(String tag) {
        rechercheTags.remove(tag);
    }

    public void rechercher() {
        System.out.println("dkhl");
        Double montantMin = null;
        Double montantMax = null;
        System.out.println("0");
        System.out.println("ha lmontant" + montantEventMin);
        if (!montantEventMin.equals("")) {
            montantMin = new Double(montantEventMin);
        }
        System.out.println("1");
        if (!montantEventMax.equals("")) {
            montantMax = new Double(montantEventMax);
        }
        System.out.println("2");
        System.out.println("ha nom" + nomEventRecherchee);
        List<Event> eventsR = eventRepositery.recherche(nomEventRecherchee, montantMin, montantMax, descriptionEventRecherche, dateDebutMin, heureDebutMin, heureDebutMax, dateDebutMax, dateFinMin, dateFinMax, rechercheTags);
        System.out.println("ha les events" + eventsR);
        allEvents = eventRepositery.eventForSendArticle(eventsR, inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin()));
    }

    public void addToWatchlist(Event event) {
        String foundLog = eventRepositery.checkWatchlistExist(((Inscrit) SessionUtil.getAttribute("connected")).getLogin(), event.getId());
        System.out.println("1");
        System.out.println("found" + foundLog);
        if (foundLog != null) {
            JsfUtil.addErrorMessage("Vous avez deja ajouter cet evenement a votre watchlist");
        } else {
            eventRepositery.saveToWatchlist(((Inscrit) SessionUtil.getAttribute("connected")).getLogin(), event.getId());
        JsfUtil.addSuccessMessage("Ajouter avec succes, Consulter la liste des evenemnts interesse");
        }
    }

    public void goToSendArticle(Event event) throws IOException {
        SessionUtil.setAttribute("event", event);
        SessionUtil.redirect("../article/send_Article");
    }
    
    public void deleteInteresee(Event event){
        eventRepositery.deleteFromWatchlist(inscritRepositery.connectedInscrit().getLogin(), event.getId());
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
//        if (events == null) {
//            List<Role> roles = roleRepositery.findByInscrit(inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin()));
            events = roleRepositery.findEventsByRoleOrganisateur(((Inscrit) SessionUtil.getAttribute("connected")).getLogin());
//        }
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

    public List<Event> getAllEvents() {
        if (allEvents == null) {
//            allEvents = eventRepositery.findNewEvents(SearchUtil.convertToSqlDate(new Date()));
            allEvents = eventRepositery.eventForSendArticle(eventRepositery.findNewEvents(SearchUtil.convertToSqlDate(new Date())), inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin()));
        }
        return allEvents;
    }

    public void setAllEvents(List<Event> allEvents) {
        this.allEvents = allEvents;
    }

    public String getViewDescription() {
        return ViewDescription;
    }

    public void setViewDescription(String ViewDescription) {
        this.ViewDescription = ViewDescription;
    }

    public String getNomEventRecherchee() {
        return nomEventRecherchee;
    }

    public void setNomEventRecherchee(String nomEventRecherchee) {
        this.nomEventRecherchee = nomEventRecherchee;
    }

    public String getMontantEventMin() {
        return montantEventMin;
    }

    public void setMontantEventMin(String montantEventMin) {
        this.montantEventMin = montantEventMin;
    }

    public String getMontantEventMax() {
        return montantEventMax;
    }

    public void setMontantEventMax(String montantEventMax) {
        this.montantEventMax = montantEventMax;
    }

    public String getDescriptionEventRecherche() {
        return descriptionEventRecherche;
    }

    public void setDescriptionEventRecherche(String descriptionEventRecherche) {
        this.descriptionEventRecherche = descriptionEventRecherche;
    }

    public Date getDateDebutMin() {
        return dateDebutMin;
    }

    public void setDateDebutMin(Date dateDebutMin) {
        this.dateDebutMin = dateDebutMin;
    }

    public Date getDateDebutMax() {
        return dateDebutMax;
    }

    public void setDateDebutMax(Date dateDebutMax) {
        this.dateDebutMax = dateDebutMax;
    }

    public Date getDateFinMin() {
        return dateFinMin;
    }

    public void setDateFinMin(Date dateFinMin) {
        this.dateFinMin = dateFinMin;
    }

    public Date getDateFinMax() {
        return dateFinMax;
    }

    public void setDateFinMax(Date dateFinMax) {
        this.dateFinMax = dateFinMax;
    }

    public List<String> getChoosedTagsForRecherche() {
        if (choosedTagsForRecherche == null) {
            choosedTagsForRecherche = new ArrayList<>();
        }
        return choosedTagsForRecherche;
    }

    public void setChoosedTagsForRecherche(List<String> choosedTagsForRecherche) {
        this.choosedTagsForRecherche = choosedTagsForRecherche;
    }

    public List<String> getRechercheTags() {
        if (rechercheTags == null) {
            rechercheTags = new ArrayList<>();
        }
        return rechercheTags;
    }

    public void setRechercheTags(List<String> rechercheTags) {
        this.rechercheTags = rechercheTags;
    }

    public Date getHeureDebutMin() {
        return heureDebutMin;
    }

    public void setHeureDebutMin(Date heureDebutMin) {
        this.heureDebutMin = heureDebutMin;
    }

    public Date getHeureDebutMax() {
        return heureDebutMax;
    }

    public void setHeureDebutMax(Date heureDebutMax) {
        this.heureDebutMax = heureDebutMax;
    }

    public List<Event> getWatchlist() {
//        if(watchlist == null){
        watchlist = new ArrayList<>();
        List<Long> watchlistIds = eventRepositery.findWatchlist(((Inscrit) SessionUtil.getAttribute("connected")).getLogin());
        System.out.println("ha les ids" + watchlistIds);
        System.out.println("1");
        System.out.println("id" + watchlistIds.get(0));
//        Long id = watchlistIds.get(0);
        System.out.println("2");
        if (!watchlistIds.isEmpty()) {
            watchlist.addAll(eventRepositery.findByIds(watchlistIds));
        }
//        for (int i = 0; i < watchlistIds.size(); i++) {
//            System.out.println("2");
//            Long watchlistId = watchlistIds.get(i);
//            System.out.println("3");
//            System.out.println(watchlistId);
//            System.out.println("4");
//            watchlist.add(eventRepositery.findOne(watchlistId));
//            System.out.println("5");
//
//        }
//        for (Event eventR : eventRepositery.findAll()) {
//            for (int i = 0; i < watchlistIds.size(); i++) {
//                Object watchlistId = watchlistIds.get(i);
//                System.out.println("ha l id"+watchlistId);
//                Object id = eventR.getId();
//                System.out.println("ha eventId"+id);
//                if (id.equals(watchlistId)) {
//                    System.out.println("egale");
//                  watchlist.add(eventR);
//                }
//            }
//        }
        System.out.println("ha lista" + watchlist);
//        }
        return watchlist;
    }

    public void setWatchlist(List<Event> watchlist) {
        this.watchlist = watchlist;
    }

}
