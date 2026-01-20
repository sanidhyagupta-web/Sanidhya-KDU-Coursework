package com.RailwayBooking.BackendHomework8.Subscribers;

import com.RailwayBooking.BackendHomework8.Brokers.AckToken;
import com.RailwayBooking.BackendHomework8.Brokers.Envelope;
import com.RailwayBooking.BackendHomework8.Brokers.TopicBroker;
import com.RailwayBooking.BackendHomework8.DTO.TicketBookedEvent;
import com.RailwayBooking.BackendHomework8.Handlers.InventoryHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class InventorySubscriber {

    private final TopicBroker broker;
    private final InventoryHandler handler;

    public InventorySubscriber(TopicBroker broker, InventoryHandler handler) {
        this.broker = broker;
        this.handler = handler;
    }

    @PostConstruct
    public void init() {
        broker.subscribe("ticket.booked", (Envelope<Object> env, AckToken ack) -> {
            TicketBookedEvent event = (TicketBookedEvent) env.getPayload();

            // Business logic (retries + DLQ handled inside handler)
            handler.handle(event);

            // IMPORTANT:
            // Ack ONLY after handler succeeds.
            // If handler throws (and retries are exhausted),
            // @Recover handles DLQ and this method will NOT ack,
            // causing at-least-once semantics.
            ack.ack();
        });
    }
}
