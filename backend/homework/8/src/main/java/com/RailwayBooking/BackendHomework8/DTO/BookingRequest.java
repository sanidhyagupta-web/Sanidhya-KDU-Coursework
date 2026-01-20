package com.RailwayBooking.BackendHomework8.DTO;

public record BookingRequest(
        String userId,
        String trainId,
        String journeyDate,
        String seatId,
        String phone,
        int age
) {}

