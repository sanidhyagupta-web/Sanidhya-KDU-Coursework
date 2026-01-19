package com.EventSphere.BackendAssignment.Models;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDTO {
    @Size(min = 3 , max = 50 , message = "Location must be between 3 and 100 characters")
    private String location;

    @Size(min = 3 , max = 20 , message = "TypeOfEvent must be between 3 and 100 characters")
    private String typeOfEvent;

    @Column(nullable = false)
    @CreatedDate
    private Instant scheduledDate;

    @Min(value = 20 ,message = "Total tickets must be atleast 20")
    private Long totalTickets;

    @Column(nullable = false)
    private Integer ticketPrice;

}
