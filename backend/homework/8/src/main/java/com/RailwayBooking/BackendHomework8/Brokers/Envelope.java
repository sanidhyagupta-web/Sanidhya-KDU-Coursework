package com.RailwayBooking.BackendHomework8.Brokers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@AllArgsConstructor
@Getter
@Setter
@Builder
public class Envelope<T> {
    private final String messageId;   // use transactionId here
    private final T payload;
    private final Instant createdAt;
}


