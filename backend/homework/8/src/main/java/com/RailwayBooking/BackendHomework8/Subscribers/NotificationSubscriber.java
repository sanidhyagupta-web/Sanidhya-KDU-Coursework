package com.RailwayBooking.BackendHomework8.Subscribers;

import com.RailwayBooking.BackendHomework8.Brokers.AckToken;
import com.RailwayBooking.BackendHomework8.Brokers.Envelope;
import com.RailwayBooking.BackendHomework8.Brokers.TopicBroker;
import com.RailwayBooking.BackendHomework8.DTO.TicketBookedEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class NotificationSubscriber {

    private final TopicBroker broker;

    public NotificationSubscriber(TopicBroker broker) {
        this.broker = broker;
    }

    @PostConstruct
    public void init() {
        broker.subscribe("ticket.booked", (Envelope<Object> env, AckToken ack) -> {
            TicketBookedEvent e = (TicketBookedEvent) env.getPayload();

            // Simulate sending SMS
            System.out.printf("[NOTIFICATION] SMS Sent bookingId=%s phone=%s%n",
                    e.getBookingId(), e.getPhone());

            // Manual ack after successful processing
            ack.ack();
        });
    }
}
