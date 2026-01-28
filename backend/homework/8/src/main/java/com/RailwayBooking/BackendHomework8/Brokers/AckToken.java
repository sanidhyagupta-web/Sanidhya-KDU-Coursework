package com.RailwayBooking.BackendHomework8.Brokers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AckToken {
    private final Runnable ackFn;
    private volatile boolean acked = false;

    public AckToken(Runnable ackFn) { this.ackFn = ackFn; }

    public void ack() {
        if (!acked) {
            acked = true;
            ackFn.run();
        }
    }

}

