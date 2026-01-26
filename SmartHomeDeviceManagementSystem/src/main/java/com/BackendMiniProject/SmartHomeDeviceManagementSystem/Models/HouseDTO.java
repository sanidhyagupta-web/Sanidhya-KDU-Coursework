package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseDTO {

    @Column(nullable = false)
    private String address;

    private String pincode;

    private LocalDate createdDate;

    private LocalDate modifiedDate;

    private LocalDate deletedDate;

}
