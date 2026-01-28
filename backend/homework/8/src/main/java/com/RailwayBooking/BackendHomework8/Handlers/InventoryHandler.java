package com.RailwayBooking.BackendHomework8.Handlers;

import com.RailwayBooking.BackendHomework8.Brokers.TopicBroker;
import com.RailwayBooking.BackendHomework8.DTO.TicketBookedEvent;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class InventoryHandler {

    private final TopicBroker broker;

    public InventoryHandler(TopicBroker broker) {
        this.broker = broker;
    }

    @Retryable(
            value = IllegalArgumentException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    public void handle(TicketBookedEvent e) {
        if (e.getAge() < 0) {
            System.out.printf("[INVENTORY] FAIL bookingId=%s age=%d%n",
                    e.getBookingId(), e.getAge());
            throw new IllegalArgumentException("Age cannot be negative");
        }

        System.out.printf("[INVENTORY] Seat Occupied seatId=%s bookingId=%s age=%d%n",
                e.getSeatId(), e.getBookingId(), e.getAge());
    }

    // DLQ after retries exhausted
    @Recover
    public void recover(IllegalArgumentException ex, TicketBookedEvent e) {
        System.out.printf("[INVENTORY] DLQ move bookingId=%s reason=%s%n",
                e.getBookingId(), ex.getMessage());

        broker.publish("booking-error-queue", e);
    }
}
