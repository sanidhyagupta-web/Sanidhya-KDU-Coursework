package com.RailwayBooking.BackendHomework8.Brokers;


@FunctionalInterface
public interface AckConsumer<T> {

    void onMessage(T message, AckToken ack);
}

