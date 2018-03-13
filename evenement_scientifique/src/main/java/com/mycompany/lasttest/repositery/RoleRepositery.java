package com.mycompany.lasttest.repositery;

import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.lasttest.bean.Role;
import com.mycompany.lasttest.custom.RoleRepositeryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepositery extends JpaRepository<Role, Long>, RoleRepositeryCustom {

    List<Role> findByInscrit(Inscrit inscrit);

    List<Role> findByEvent(Event event);

    @Query("select r.event from #{#entityName} r where r.role = 1 and r.inscrit.login = ?1")
    List<Event> findEventsByRoleOrganisateur(String organisateurLogin);

    @Query("select r.inscrit from #{#entityName} r where r.role = 2 and r.verifiedRapporteur = 1 and r.event.id = ?1")
    List<Inscrit> findRapporteursByEventAndVerified(Long eventId);
    
    @Query("select r from #{#entityName} r where r.role = 2 and r.verifiedRapporteur = 1 and r.inscrit.login = ?1")
    List<Role> findRoleRapporteursByInscritAndVerified(String login);

    @Query("select r from #{#entityName} r where r.role = 2 and r.verifiedRapporteur = 1 and r.event.id = ?1 and r.inscrit.login = ?2")
    Role findRapporteurByEventAndInscrit(Long eventId, String inscritLogin);
    
     List<Role> findByInscritAndRoleAndVerifiedRapporteur(Inscrit inscrit,int role, Boolean verifiedRapporteur);
     
     @Query("SELECT role FROM #{#entityName} role WHERE role.role = ?1 and role.inscrit=?2")
    List<Role> findByRole_event_inscrit(int role, Inscrit inscrit);
    
    @Query("select r.inscrit from #{#entityName} r where r.role = 1 and r.event.id = ?1")
    Inscrit findInscritByEvent(Long eventId);
}
