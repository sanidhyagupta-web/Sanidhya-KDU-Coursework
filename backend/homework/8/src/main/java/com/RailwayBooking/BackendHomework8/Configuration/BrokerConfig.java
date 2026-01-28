package com.RailwayBooking.BackendHomework8.Configuration;

import com.RailwayBooking.BackendHomework8.Brokers.TopicBroker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class BrokerConfig {

    @Bean
    public TopicBroker topicBroker() {
        return new TopicBroker(
                32,                     // worker threads
                Duration.ofSeconds(5)   // ack timeout before redelivery
        );
    }
}
