package com.EventSphere.BackendAssignment.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE table_product SET deleted = true WHERE id=?")
@SQLRestriction("deleted = false")
public class Event {

    @Id
    private String eventId;

    @Column(nullable = false)
    @Size(min = 3 , max = 50 , message = "Location must be between 3 and 100 characters")
    private String location;

    @Column(nullable = false)
    @Size(min = 3 , max = 20 , message = "TypeOfEvent must be between 3 and 100 characters")
    private String typeOfEvent;

    @Column(nullable = false)
    @CreatedDate
    private LocalDate scheduledDate;

    private Long ticketsBooked;

    @Column(nullable = false)
    @Min(value = 20 ,message = "Total tickets must be atleast 20")
    private Long totalTickets;

    @Column(nullable = false)
    @Min(value = 10,message = "Ticket Price cannot be less than 10")
    private Integer ticketPrice;

    private boolean deleted = Boolean.FALSE;
}
