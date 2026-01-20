package com.RailwayBooking.BackendHomework8.Controller;

import com.RailwayBooking.BackendHomework8.DTO.BookingRequest;
import com.RailwayBooking.BackendHomework8.DTO.BookingResponse;
import com.RailwayBooking.BackendHomework8.DTO.TicketBookedEvent;
import com.RailwayBooking.BackendHomework8.Brokers.TopicBroker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final TopicBroker broker;

    public BookingController(TopicBroker broker) {
        this.broker = broker;
    }

    @PostMapping
    public ResponseEntity<?> book(@RequestBody BookingRequest req) {
        String bookingId = "BKG-" + UUID.randomUUID();

        // If you WANT the poison pill to reach consumers (for retry/DLQ demo),
        // keep this validation OFF.
        //
        // If you DON'T want corrupt data to enter the system, turn it ON.
        //
        // if (req.age() < 0) {
        //     return ResponseEntity.badRequest().body("Invalid age: " + req.age());
        // }

        TicketBookedEvent event = new TicketBookedEvent(
                        UUID.randomUUID().toString(), // eventId
                        bookingId,
                        req.userId(),
                        req.trainId(),
                        req.journeyDate(),
                        req.seatId(),
                        req.phone(),
                        Instant.now(),
                        req.age()
                );

        broker.publish("ticket.booked", event);
        System.out.printf("[BOOKING] Published Ticket_Booked bookingId=%s age=%d%n",
                bookingId, req.age());

        return ResponseEntity.accepted().body(new BookingResponse(
                bookingId, "PENDING", "Booking in progress"
        ));
    }

}
