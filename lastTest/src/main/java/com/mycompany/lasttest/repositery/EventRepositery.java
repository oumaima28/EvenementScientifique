package com.mycompany.lasttest.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.custom.EventRepositeryCustom;
import java.sql.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepositery extends JpaRepository<Event, Long>, EventRepositeryCustom {

    @Modifying
    @Query(value = "insert into event_tags (event_id,tags) VALUES (:id,:tag)", nativeQuery = true)
    @Transactional
    public void saveTag(@Param("id") Long eventId, @Param("tag") String tag);

    @Modifying
    @Query(value = "delete from event_tags where event_id = ?1", nativeQuery = true)
    @Transactional
    public void deleteTag(Long eventId);

    List<Event> findByNom(String nom);

    @Query(value = "select t.tags from event_tags t where t.event_id = ?1", nativeQuery = true)
    List<String> findTagByEvent(Long eventId);

    @Modifying
    @Query(value = "delete from event_tags where event_id = ?1 and tags = ?2", nativeQuery = true)
    @Transactional
    public void deleteTagByEventAndName(Long eventId, String name);

    @Query(value = "select t.event_id from event_tags t where t.tags = ?1", nativeQuery = true)
    List<Long> rechercherEventForTag(String tag);

    @Query("SELECT e FROM #{#entityName} e WHERE e.dateDebut > ?1")
    List<Event> findNewEvents(Date today);

    @Modifying
    @Query(value = "insert into inscrit_events (inscrit_login,events_id) VALUES (:inscrit,:event)", nativeQuery = true)
    @Transactional
    void saveToWatchlist(@Param("inscrit") String login, @Param("event") Long eventId);
    
    @Modifying
    @Query(value = "delete from inscrit_events where inscrit_login = ?1 and events_id = ?2", nativeQuery = true)
    @Transactional
    void deleteFromWatchlist(String login, Long eventId);

    @Query(value = "select e.events_id from inscrit_events e where e.inscrit_login = ?1", nativeQuery = true)
    List<Long> findWatchlist(String login);

    @Query("SELECT e FROM #{#entityName} e WHERE id = ?1")
    List<Event> findById(Long id);

    @Query(value = "select e.inscrit_login from inscrit_events e where e.inscrit_login = ?1 and e.events_id = ?2", nativeQuery = true)
    String checkWatchlistExist(String login, Long eventId);
}
