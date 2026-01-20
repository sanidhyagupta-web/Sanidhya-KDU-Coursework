package com.RailwayBooking.BackendHomework8.Controller;

import com.RailwayBooking.BackendHomework8.Brokers.TopicBroker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/debug")
public class DlqController {

    private final TopicBroker broker;

    public DlqController(TopicBroker broker) {
        this.broker = broker;
    }

    @GetMapping("/dlq")
    public List<Object> dlq() {
        return broker.drain("booking-error-queue");
    }
}

