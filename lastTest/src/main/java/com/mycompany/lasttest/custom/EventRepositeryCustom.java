package com.mycompany.lasttest.custom;

import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import java.util.List;

public interface EventRepositeryCustom {

     void createEvent(Inscrit organisateur, Event event, List<String> emails, List<String> tags);
     
     void saveTags(Long eventId, List<String> tags);
     
}
