package com.EventSphere.BackendAssignment.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String ticketId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @Column(nullable = false)
    @Min(value = 0,message = "Ticket Price should be greater than 10")
    private Integer price;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private Instant purchasedAt;

    private boolean ticketValidity=true;
}
