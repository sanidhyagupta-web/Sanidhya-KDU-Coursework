package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Table(name = "devices",
    indexes = {
        @Index(name = "Device_index_house_id" , columnList = "house_id"),
        @Index(name = "Device_index_room_id" , columnList = "room_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE devices SET deleted = true, deletedDate = CURRENT_TIMESTAMP WHERE kickston_id = ?")
@SQLRestriction("deleted = false")
public class Device {

    @Id
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
    @Size(min = 2 , max = 20 , message = "Device manufacture factory place should lie between 2 to 20 characters")
    private String manufactureFactoryPlace;

    private LocalDate createdDate;

    private LocalDate modifiedDate;

    private LocalDate deletedDate;

    @Column(nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

}
