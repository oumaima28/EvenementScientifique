package com.mycompany.lasttest.controller;

import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import java.util.ArrayList;
import java.util.List;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mycompany.lasttest.bean.Role;
import com.mycompany.lasttest.repositery.EventRepositery;
import com.mycompany.lasttest.repositery.InscritRepositery;
import com.mycompany.lasttest.repositery.PaiementRepositery;
import com.mycompany.lasttest.repositery.RoleRepositery;
import com.mycompany.lasttest.util.JsfUtil;
import com.mycompany.lasttest.util.SessionUtil;
import java.io.IOException;

@Scope(value = "session")
@Component(value = "roleController")
@ELBeanName(value = "roleController")
public class RoleController {

    private Role role;
    private List<Role> roles;

    //accepter refuser role rapporteurs view
    private Role selectedRole;
    private List<Role> myRoles;

    @Autowired
    private RoleRepositery roleRepositery;
    @Autowired
    private EventRepositery eventRepositery;
    @Autowired
    private InscritRepositery inscritRepositery;
    @Autowired
    private PaiementRepositery paiementRepositery;

    // this event is the selected event for edit
    private Event selectedEvent;
    private String montantEvent;

    //domaines(tags) d'ou choisir
    private List<String> availableTags;
    private List<String> choosedTags;
    private List<String> eventTags;
    private List<String> deletedTags = new ArrayList<>();
    private String deletedTag;

    //accpter refuser Role rapporteur
    public void modifyEtatRole(boolean b) {
        System.err.println("modifyEtatRole");
        System.out.println("ha lrole" + selectedRole);
        System.out.println("ha lid role" + selectedRole.getId());
        inscritRepositery.sendEmailofInvitationRapporteur(selectedRole, b);
        if (b == true) {
            selectedRole.setVerifiedRapporteur(true);
            roleRepositery.save(selectedRole);
        } else {
            roleRepositery.delete(selectedRole.getId());
        }
    }

    public String showActions(boolean b) {
        if (b == true) {
            return "accepté";
        } else if (b == false) {
            return "pas encore";
        } else {
            return "Euu3";
        }
    }

    public void editRoleFromList() {
        System.out.println("3iw");
        System.out.println("ha event t role" + role.getEvent());
        selectedEvent.setMontant(new Double(montantEvent));
        eventRepositery.save(selectedEvent);
        System.out.println("ha l id t event" + selectedEvent.getId());
        eventRepositery.deleteTag(selectedEvent.getId());
        eventRepositery.saveTags(selectedEvent.getId(), eventTags);
    }

    public void removeRoleFromList() {
        if (role.getRole() == 1) {
            System.out.println("organisateur");
            System.out.println("ha role" + role);
            Event loadedEvent = eventRepositery.findByNom(role.getEvent().getNom()).get(0);
            System.out.println("ha event t orga" + loadedEvent);
            if (paiementRepositery.findByEvent(loadedEvent) == null) {
                System.out.println("dkhl lmethode");
                eventRepositery.deleteEvent(loadedEvent, role);
                roles.remove(role);
            }
            else{
                JsfUtil.addErrorMessage("Impossible de supprimer l'evenement car il y a des paiements deja effectuer");
            }
        } else if (role.getRole() == 2) {
            roleRepositery.delete(role);
            roles.remove(role);
        }
    }

    public void deleteTag(String tag) {
        System.out.println("dkhl");
//        eventRepositery.deleteTagByEventAndName(selectedEvent.getId(), tag);
        System.out.println("khrj");
        eventTags.remove(tag);
        deletedTags.add(tag);
        deletedTag = tag;
    }

    public void addTagsToList() {
//        eventRepositery.saveTags(selectedEvent.getId(), choosedTags);
        System.out.println("ha tagat" + choosedTags);
        System.out.println("ha eventTags" + eventTags);
        eventTags.addAll(choosedTags);
        System.out.println("ha eventTags" + eventTags);
    }

    //methode dyalk a zhor hna setti session t event w redirecti
    public void goToArticlesForRapporteur() {
        selectedEvent = eventRepositery.findByNom(role.getEvent().getNom()).get(0);
    }

    public List<Role> findRolesForConnected() {
        Inscrit connected = inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin());
        return roleRepositery.findByInscrit(connected);
    }

    public void showArticlesOfEvent(Event eve) throws IOException {
        SessionUtil.setAttribute("selectedEventForArticls", eve);
        SessionUtil.redirect("../article/articlesOfEvents");
    }

    public Role getRole() {
        if (role == null) {
            role = new Role();
        }
        return role;
    }

    public void setRole(Role role) {
        montantEvent = role.getEvent().getMontant() + "";
        this.role = role;
    }

    public List<Role> getRoles() {
//        if (roles == null) {
            roles = roleRepositery.findByInscrit(inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin()));
            System.out.println("roles");
//        }
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Event getSelectedEvent() {
        if (role == null) {
            selectedEvent = new Event();
        } else {
            selectedEvent = role.getEvent();
        }
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public String getMontantEvent() {
        return montantEvent;
    }

    public void setMontantEvent(String montantEvent) {
        this.montantEvent = montantEvent;
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

    public List<String> getEventTags() {
        if (role == null) {
//        if(eventTags == null){
            eventTags = new ArrayList<>();
        } else {
            if (choosedTags == null) {
                eventTags = eventRepositery.findTagByEvent(role.getEvent().getId());
            } else {
                System.out.println("dkhl l getEventTag");
                System.out.println("id dyal event" + role.getEvent().getId());
                eventTags = eventRepositery.findTagByEvent(role.getEvent().getId());
                System.out.println("ha tags" + eventTags);
                eventTags.addAll(choosedTags);
                if (deletedTag != null) {
                    eventTags.remove(deletedTag);
                }
            }
        }
        return eventTags;
    }

    public void setEventTags(List<String> eventTags) {
        this.eventTags = eventTags;
    }

    public List<Role> getMyRoles() {
        Inscrit InscritConnected = ((Inscrit) SessionUtil.getAttribute("connected"));
        Inscrit loadedConnected = inscritRepositery.findOne(InscritConnected.getLogin());
        return myRoles = roleRepositery.findByInscritAndRoleAndVerifiedRapporteur(loadedConnected, 2, Boolean.FALSE);
    }

    public void setMyRoles(List<Role> myRoles) {
        this.myRoles = myRoles;
    }

    public Role getSelectedRole() {
        if (selectedRole == null) {
            selectedRole = new Role();
        }
        return selectedRole;
    }

    public void setSelectedRole(Role selectedRole) {
        this.selectedRole = selectedRole;
    }
}
