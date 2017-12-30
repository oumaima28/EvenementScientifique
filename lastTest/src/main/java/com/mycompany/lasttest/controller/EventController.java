package com.mycompany.lasttest.controller;

import java.util.ArrayList;
import java.util.List;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.repositery.EventRepositery;

@Scope(value = "session")
@Component(value = "eventController")
@ELBeanName(value = "eventController")
public class EventController {

	private Event event;
	private List<Event> events;
	
	@Autowired
	private EventRepositery eventRepositery;

	
	
	public Event getEvent() {
		if(event == null) {
			event = new Event();
		}
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public List<Event> getEvents() {
		if(events == null) {
			events = new ArrayList<>();
		}
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
}
