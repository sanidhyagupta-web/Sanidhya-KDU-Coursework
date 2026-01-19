package com.EventSphere.BackendAssignment.Controller;

import com.EventSphere.BackendAssignment.Entities.Ticket;
import com.EventSphere.BackendAssignment.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/{userId}/reserveTicket/{eventId}")
    public ResponseEntity<Ticket> reserveTicket(@PathVariable("userId") String userId,@PathVariable("eventId") String eventId){
        return new ResponseEntity<>(userService.reserveTicket(userId,eventId), HttpStatus.CREATED);
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<Ticket> modifyReservation(@PathVariable("ticketId") String ticketId){
        return new ResponseEntity<>(userService.modifyReservation(ticketId),HttpStatus.OK);
    }

    @DeleteMapping("soft/{ticketId}")
    public ResponseEntity<Ticket> deleteReservation(@PathVariable("ticketId") String ticketId){
        return new ResponseEntity<>(userService.SoftDeleteTicket(ticketId),HttpStatus.OK);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Ticket> deleteTicket(@PathVariable("ticketId") String ticketId){
        return new ResponseEntity<>(userService.deleteTicket(ticketId),HttpStatus.OK);
    }
}
