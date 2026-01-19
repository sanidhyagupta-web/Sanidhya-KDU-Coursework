package com.EventSphere.BackendAssignment.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userId;

    private String eventId;

    private String ticketId;

    @Column(nullable = false)
    @Size(min = 3 , max = 50 , message = "Name must be between 3 and 50 characters")
    private String name;

    @Column(nullable = false)
    @Size(min = 3 , max = 200 , message = "Location must be between 3 and 200 characters")
    private String address;

    @Column(nullable = false)
    @Min(value = 0 , message = "Age cannot be less than 0")
    private Integer age;

    @Column(nullable = false)
    private boolean hasTicket;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

}
