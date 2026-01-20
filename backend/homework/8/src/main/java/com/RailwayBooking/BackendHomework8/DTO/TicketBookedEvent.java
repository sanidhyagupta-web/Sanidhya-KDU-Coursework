package com.RailwayBooking.BackendHomework8.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class TicketBookedEvent {
    private String eventId;
    private String bookingId;
    private String userId;
    private String trainId;
    private String journeyDate;
    private String seatId;
    private String phone;
    private Instant occurredAt;
    private Integer age;
}
