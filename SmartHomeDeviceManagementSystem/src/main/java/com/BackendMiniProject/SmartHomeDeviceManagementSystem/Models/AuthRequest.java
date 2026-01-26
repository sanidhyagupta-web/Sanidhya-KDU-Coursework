package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private Long userId;
    private Long houseId;
}
