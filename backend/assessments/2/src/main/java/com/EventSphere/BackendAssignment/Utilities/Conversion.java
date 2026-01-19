package com.EventSphere.BackendAssignment.Utilities;

import com.EventSphere.BackendAssignment.Entities.Event;
import com.EventSphere.BackendAssignment.Models.EventDTO;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@Component
public class Conversion {

    public Event returnsEvent(EventDTO eventDTO){
        Event event = new Event();
        event.setEventId(UUID.randomUUID().toString());
        event.setTypeOfEvent(eventDTO.getTypeOfEvent());
        event.setLocation(eventDTO.getLocation());
        event.setTotalTickets(eventDTO.getTotalTickets());
        event.setTicketsBooked(0L);
        event.setScheduledDate(LocalDate.now());
        event.setTicketPrice(eventDTO.getTicketPrice());
        return event;
    }
}