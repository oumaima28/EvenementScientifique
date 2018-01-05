package com.mycompany.lasttest.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.custom.EventRepositeryCustom;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepositery extends JpaRepository<Event, Long>, EventRepositeryCustom{

    @Modifying
    @Query(value = "insert into event_tags (event_id,tags) VALUES (:id,:tag)", nativeQuery = true)
    @Transactional
    public void saveTag(@Param("id") Long eventId ,@Param("tag") String tag);
}
