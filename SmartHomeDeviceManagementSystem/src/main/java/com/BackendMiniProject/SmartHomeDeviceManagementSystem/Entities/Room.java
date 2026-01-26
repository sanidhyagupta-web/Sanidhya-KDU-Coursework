package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE rooms SET deleted = true, deleted_date = CURRENT_TIMESTAMP WHERE room_Id = ?")
@SQLRestriction("deleted = false")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_Id")
    private Long roomId;

    @Column(nullable = false)
    @Size(min = 2 , max = 50 ,message = "The type of house should be between 2 to 50 characters")
    private String type;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Column(name = "deleted_date")
    private LocalDate deletedDate;

    @Column(nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "room_house_id")
    private House house;

    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Device> devices;

    public void addDevice(Device device) {
        devices.add(device);
        device.setRoom(this);
    }
}
