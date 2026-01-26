package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDTO {

    @Column(name = "kickston_id", length = 6)
    private String kickstonId;

    @Column(name = "device_username", nullable = false)
    @Size(min = 2 , max = 50 , message = "Device username should lie between 2 to 50 characters")
    private String deviceUsername;

    @Column(name = "device_password", nullable = false)
    @Size(min = 2 , max = 50 , message = "Device password should lie between 2 to 50 characters")
    private String devicePassword;

    @Column(name = "manufacture_date_time", nullable = false)
    private LocalDate manufactureDateTime;

    @Column(name = "manufacture_factory_place", nullable = false)
    @Size(min = 2 , max = 20 , message = "Device username should lie between 2 to 50 characters")
    private String manufactureFactoryPlace;

    private LocalDate createdDate;

    private LocalDate modifiedDate;

    private LocalDate deletedDate;

}
