package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "membership",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "house_id"})
        // Unique columns already get indexed in MySQL.
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long membershipId;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role;
}