package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {
    String name;
    String password;
}