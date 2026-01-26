package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users" ,
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "age","email"}))
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE users SET deleted = true, deleted_date = CURRENT_TIMESTAMP WHERE user_Id = ?")
@SQLRestriction("deleted = false")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_Id")
    private Long userId;

    @Column(nullable = false)
    @Size(min = 2,max = 50,message = "The length of name should be between 2 to 50 characters")
    private String name;

    @Column(nullable = false)
    @Min(value = 0,message = "The age cannot be negative")
    @Max(value = 150,message = "The age cannot be more than 150")
    private int age;

    @Column(nullable = false)
    @Size(min = 2,max = 50,message = "The length of email should be between 2 to 50 characters")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Column(name = "deleted_date")
    private LocalDate deletedDate;

    @Column(nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HouseUser> memberships;

}