package com.mycompany.lasttest.custom;

import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.bean.Role;
import java.util.Date;
import java.util.List;

public interface EventRepositeryCustom {

     void createEvent(Inscrit organisateur, Event event, List<String> emails, List<String> tags);
     
     void saveTags(Long eventId, List<String> tags);
     
     void deleteEvent(Event event, Role roleOrganisateur);
     
     List<Event> findByIds(List<Long> ids);
     
     List<Event> recherche(String nomEventRecherchee, Double montantMin, Double montantMax, String descriptionEventRecherche, Date dateDebutMin, Date heureDebutMin, Date heureDebutMax, Date dateDebutMax, Date dateFinMin, Date dateFinMax, List<String> rechercheTags);
     
     List<Event> eventForSendArticle(List<Event> events, Inscrit inscrit);
}
