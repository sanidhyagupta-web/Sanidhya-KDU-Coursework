package com.EventSphere.BackendAssignment.Service;

import com.EventSphere.BackendAssignment.Entities.Event;
import com.EventSphere.BackendAssignment.Entities.Ticket;
import com.EventSphere.BackendAssignment.Entities.User;
import com.EventSphere.BackendAssignment.Exceptions.AllTicketsBookedException;
import com.EventSphere.BackendAssignment.Exceptions.NoTicketsBookedException;
import com.EventSphere.BackendAssignment.Exceptions.ResourceNotFoundException;
import com.EventSphere.BackendAssignment.Repository.EventRepository;
import com.EventSphere.BackendAssignment.Repository.TicketRepository;
import com.EventSphere.BackendAssignment.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Will convert it into constructor injection at end
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Transactional
    public Ticket reserveTicket(String userId,String eventId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with id " + userId + " was not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(()->new ResourceNotFoundException("Event with id " + eventId + " was not found"));
        checkTotalTicketsValidation(event);
        Ticket ticket = Ticket.builder().event(event).price(event.getTicketPrice()).build();
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket modifyReservation(String ticketId){
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()->new ResourceNotFoundException("Ticket with id " + ticketId + " was not found"));
        ticket.setTicketValidity(false);
        ticketRepository.deleteById(ticketId);
        deleteTotalTicketsCount(ticket);
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket SoftDeleteTicket(String ticketId){
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()->new ResourceNotFoundException("Ticket with id " + ticketId + " was not found"));
        deleteTotalTicketsCount(ticket);
        ticketRepository.deleteById(ticketId);
        return ticket;
    }

    @Transactional
    public Ticket deleteTicket(String ticketId){
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()->new ResourceNotFoundException("Ticket with id " + ticketId + " was not found"));
        deleteTotalTicketsCount(ticket);
        ticketRepository.deleteById(ticketId);
        return ticket;
    }

    @Transactional
    public synchronized void checkTotalTicketsValidation(Event event){
        if(event.getTotalTickets() < event.getTicketsBooked()+1){
            throw new AllTicketsBookedException("Sorry, All Tickets are booked");
        }else{
            event.setTicketsBooked(event.getTicketsBooked()+1);
            eventRepository.save(event);
        }
    }

    @Transactional
    public synchronized void deleteTotalTicketsCount(Ticket ticket){
        Event event = eventRepository.findById(ticket.getEvent().getEventId()).orElseThrow(()->new ResourceNotFoundException("Event not found"));
        if(event.getTicketsBooked()<=0){
            throw new NoTicketsBookedException("No tickets were booked");
        }else{
            event.setTicketsBooked(event.getTicketsBooked()-1);
            eventRepository.save(event);
        }
    }

}
