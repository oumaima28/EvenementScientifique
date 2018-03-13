package com.mycompany.lasttest.repositery;

import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.lasttest.bean.Paiement;
import com.mycompany.lasttest.custom.PaiementRepositeryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface PaiementRepositery extends JpaRepository<Paiement, Long>, PaiementRepositeryCustom{

    List<Paiement> findByEvent(Event event);
    
    List<Paiement> findByInscrit(Inscrit inscrit);
    
    @Query("SELECT p.payed FROM #{#entityName} p WHERE p.inscrit.login  = ?1 and p.event.id = ?2")
    int findPayedByInscritAndEvent(String inscritLogin, Long eventId);
    
    @Query("SELECT p.inscrit.login FROM #{#entityName} p WHERE p.event.id = ?2 and p.payed = ?1 and p.inscrit.login = ?3")
    String findLoginByPaiementAndEventAndInscrit(int payed, Long eventId, String login);
}
