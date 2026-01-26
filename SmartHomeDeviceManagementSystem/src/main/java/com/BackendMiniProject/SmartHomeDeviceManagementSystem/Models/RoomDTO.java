package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {

    @Column(nullable = false)
    private String type;

    private LocalDate createdDate;

    private LocalDate modifiedDate;

    private LocalDate deletedDate;

    @Builder.Default
    private List<DeviceDTO> devices = new ArrayList<>();
}