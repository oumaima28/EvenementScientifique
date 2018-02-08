package com.mycompany.lasttest.implementationCustom;

import com.mycompany.lasttest.bean.Article;
import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.bean.Revision;
import com.mycompany.lasttest.bean.Role;
import com.mycompany.lasttest.custom.EventRepositeryCustom;
import com.mycompany.lasttest.repositery.ArticleRepositery;
import com.mycompany.lasttest.repositery.EventRepositery;
import com.mycompany.lasttest.repositery.InscritRepositery;
import com.mycompany.lasttest.repositery.PaiementRepositery;
import com.mycompany.lasttest.repositery.RevisionRepositery;
import com.mycompany.lasttest.repositery.RoleRepositery;
import com.mycompany.lasttest.util.SearchUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;

public class EventRepositeryImpl implements EventRepositeryCustom {

    @Autowired
    private EventRepositery eventRepositery;

    @Autowired
    private RoleRepositery roleRepositery;

    @Autowired
    private InscritRepositery inscritRepositery;

    @Autowired
    private ArticleRepositery articleRepositery;

    @Autowired
    private RevisionRepositery revisionRepositery;

    @Autowired
    private PaiementRepositery paiementRepositery;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createEvent(Inscrit organisateur, Event event, List<String> emails, List<String> tags) {
        Event loadedEvent = eventRepositery.save(event);
        List<Inscrit> rapporteurs = inscritRepositery.findByEmails(emails);
        roleRepositery.createRoleForOrganisateur(organisateur, event);
        roleRepositery.createRoleForRapporteurs(rapporteurs, event);
        saveTags(loadedEvent.getId(), tags);
    }

    @Override
    public void saveTags(Long eventId, List<String> tags) {
        for (String tag : tags) {
            eventRepositery.saveTag(eventId, tag);
        }
    }

    @Override
    public void deleteEvent(Event event, Role roleOrganisateur) {
        Event loadedEvent = eventRepositery.findByNom(event.getNom()).get(0);
        if (articleRepositery.findByEvent(event) != null) {
            for (Article article : articleRepositery.findByEvent(loadedEvent)) {
                revisionRepositery.delete(revisionRepositery.findByArticle(article));
                articleRepositery.delete(article);
                articleRepositery.deleteArticle_Auteurs(article.getId());
                articleRepositery.deleteAuteur_Articles(article.getId());
            }
        }
        System.out.println("hani");
        System.out.println("ha l'event->" + loadedEvent);
        eventRepositery.deleteTag(loadedEvent.getId());
        roleRepositery.delete(roleOrganisateur);
        roleRepositery.delete(roleRepositery.findByEvent(loadedEvent));
        eventRepositery.delete(loadedEvent);
    }

    @Override
    public List<Event> recherche(String nom, Double montantMin, Double montantMax, String description, Date dateDebutMin, Date heureDebutMin, Date heureDebutMax, Date dateDebutMax, Date dateFinMin, Date dateFinMax, List<String> tags) {
        System.out.println("dkhl l recherche");
        String query = "Select e From Event e WHERE 1=1";
        int found = 0;
        if (nom != null && !nom.equals("")) {
            query += " AND e.nom = '" + nom + "'";
        }
        if (description != null && !description.equals("")) {
            query += " AND e.description = '" + description + "'";
        }
        if (montantMin != null) {
            query += " AND e.montant >= '" + montantMin + "'";
        }
        if (montantMax != null) {
            query += " AND e.montant <= '" + montantMax + "'";
        }
        if (dateDebutMin != null) {
            query += " AND e.dateDebut >= '" + SearchUtil.convertToSqlDate(dateDebutMin) + "'";
        }
        if (dateDebutMax != null) {
            query += " AND e.dateDebut <= '" + SearchUtil.convertToSqlDate(dateDebutMax) + "'";
        }
        if (heureDebutMin != null) {
            query += " AND e.heureDebut >= '" + SearchUtil.convertToSqlDate(heureDebutMin) + "'";
        }
        if (heureDebutMax != null) {
            query += " AND e.heureDebut <= '" + SearchUtil.convertToSqlDate(heureDebutMax) + "'";
        }
        if (dateFinMin != null) {
            query += " AND e.dateFin >= '" + SearchUtil.convertToSqlDate(dateFinMin) + "'";
        }
        if (dateFinMax != null) {
            query += " AND e.dateFin <= '" + SearchUtil.convertToSqlDate(dateFinMax) + "'";
        }
        if (tags != null) {
            for (String tag : tags) {
                List<Long> eventIds = new ArrayList<>();
                eventIds.addAll(eventRepositery.rechercherEventForTag(tag));
                if (eventIds != null) {
                    System.out.println("ha les ids" + eventIds);
                    int t = 0;
                    if (eventIds.size() == 0) {
                        System.out.println("aaaaa");
                        t = 1;
                        System.out.println("ha t" + t);
                        System.out.println("bbbbb");
                    }
                    Long id = 1L;
                    if (t == 1) {
                        query += " AND e.id = '" + 1L + "'";
                    }
//                    System.out.println("ha l id tl event" + eventId);
                    if (eventIds.size() == 1) {
                        query += " AND e.id = '" + eventIds.get(0) + "'";
                    } else {
                        query += " AND";
                        for (int i = 0; i < eventIds.size() - 1; i++) {
                            query += " e.id = '" + eventIds.get(i) + "' OR";
                        }
                        query += " e.id = '" + eventIds.get(eventIds.size() - 1) + "'";
                    }
                }
            }
        }
        System.out.println("ha lquery" + query);
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Event> findByIds(List<Long> ids) {
        String query = "Select e from Event e where ";
        for (int i = 0; i < ids.size() - 1; i++) {
            query += "e.id = '" + ids.get(i) + "' or ";
        }
        query += "e.id = '" + ids.get(ids.size() - 1) + "'";
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Event> eventForSendArticle(List<Event> events, Inscrit inscrit) {
        List<Role> roleOrgnt = roleRepositery.findByRole_event_inscrit(1, inscrit);
        List<Role> roleRapprt = roleRepositery.findByRole_event_inscrit(2, inscrit);
//        List<Event> events = eventRepositery.findAll();
        System.out.println("role orgnst " + roleOrgnt);
        System.out.println("role role rapprtr  " + roleRapprt);
        if (roleOrgnt != null) {
            for (Role role : roleOrgnt) {
                events.remove(role.getEvent());
            }
        }
        if (roleRapprt.size() != 0) {
            System.out.println("hani");
            for (Role role : roleRapprt) {
                events.remove(role.getEvent());
            }
        }
        if (events != null) {
            for (Event myEvent : events) {
                if (myEvent.getDateLimiteEnvoieArticle().getTime() < new Date().getTime()) {
                    events.remove(myEvent);
                }
            }
        }
        System.out.println("hahya list de mes events  " + events);
        return events;
    }
}
