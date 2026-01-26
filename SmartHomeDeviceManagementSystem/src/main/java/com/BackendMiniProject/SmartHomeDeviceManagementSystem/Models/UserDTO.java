package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String email;

    private LocalDate createdDate;

    private LocalDate modifiedDate;

    private LocalDate deletedDate;
}