package com.mycompany.lasttest.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.lasttest.bean.Event;

public interface EventRepositery extends JpaRepository<Event, Long>{

}
