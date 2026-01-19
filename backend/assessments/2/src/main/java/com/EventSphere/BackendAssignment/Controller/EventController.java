package com.EventSphere.BackendAssignment.Controller;

import com.EventSphere.BackendAssignment.Entities.Event;
import com.EventSphere.BackendAssignment.Models.EventDTO;
import com.EventSphere.BackendAssignment.Service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody EventDTO eventDTO){
        return new ResponseEntity<>(eventService.createEvent(eventDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Event> updateEvent(@Valid @RequestBody EventDTO eventDTO){
        return new ResponseEntity<>(eventService.updateEvent(eventDTO),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Event>> getAllEvents(
            @PageableDefault(size = 5)
            Pageable pageable
    ){
        return new ResponseEntity<>(eventService.getAllEvents(pageable),HttpStatus.OK);
    }


}
