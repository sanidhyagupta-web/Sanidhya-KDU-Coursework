package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.Roles;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseUserDTO {
    private Long membershipId;

    private Long houseId;

    private Long userId;

    private Roles role;

}
