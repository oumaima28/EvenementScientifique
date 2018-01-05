package com.mycompany.lasttest.implementationCustom;

import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.bean.Role;
import com.mycompany.lasttest.custom.EventRepositeryCustom;
import com.mycompany.lasttest.repositery.EventRepositery;
import com.mycompany.lasttest.repositery.InscritRepositery;
import com.mycompany.lasttest.repositery.RoleRepositery;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;

public class EventRepositeryImpl implements EventRepositeryCustom{

    @Autowired
    private EventRepositery eventRepositery;
    
    @Autowired
    private RoleRepositery roleRepositery;
    
    @Autowired
    private InscritRepositery inscritRepositery;
    
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
             eventRepositery.saveTag(eventId,tag);
        }
    }
    

    
}
