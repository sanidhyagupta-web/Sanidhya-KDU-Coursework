package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "houses")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE houses SET deleted = true, deleted_date = CURRENT_TIMESTAMP WHERE house_id = ?")
@SQLRestriction("deleted = false")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    private Long houseId;

    @Column(nullable = false)
    @Size(min = 3,max = 400 , message = "The address should be between 2 to 400 characters")
    private String address;

    private String pincode;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Column(name = "deleted_date")
    private LocalDate deletedDate;

    /**
     * Soft delete flag.
     * - false = active user (default)
     * - true = soft deleted user
     *
     * This field is automatically set to TRUE by @SQLDelete when delete() is called
     */
    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HouseUser> memberships;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;

    @OneToMany(mappedBy = "house",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Device> devices;

    public void addDevice(Device device) {
        devices.add(device);
        device.setHouse(this);
    }
}