package com.EventSphere.BackendAssignment.Service;

import com.EventSphere.BackendAssignment.Entities.Event;
import com.EventSphere.BackendAssignment.Exceptions.ResourceNotFoundException;
import com.EventSphere.BackendAssignment.Models.EventDTO;
import com.EventSphere.BackendAssignment.Repository.EventRepository;
import com.EventSphere.BackendAssignment.Utilities.Conversion;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private Conversion conversion;

    @Autowired
    private  EventRepository eventRepository;


    public Event createEvent(EventDTO eventDTO){
        return eventRepository.save(conversion.returnsEvent(eventDTO));
    }

    public Event updateEvent(@Valid Event event) {
        Event event1 = eventRepository.findById(event.getEventId()).orElseThrow(()->new ResourceNotFoundException("Event not found"));

        event1.setTicketPrice(event.getTicketPrice());
        event1.setTypeOfEvent(event.getTypeOfEvent());
        event1.setTotalTickets(event.getTotalTickets());
        event1.setTicketPrice(event.getTicketPrice());
        event1.setLocation(event.getLocation());

        return eventRepository.save(event1);
    }

    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }
}
