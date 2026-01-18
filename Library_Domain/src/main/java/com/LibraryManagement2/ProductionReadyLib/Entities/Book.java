package com.LibraryManagement2.ProductionReadyLib.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 2)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    // @Version adds a version column to your table and makes JPA use it to detect conflicts during UPDATE.
    // OptimisticLockException is thrown if conflicts are seen during commits.
    @Version
    private Long version;

    /**
     * Created only for testing purpose
     * Will be removed during production
     * @param spring
     * @param status
     */
    public Book(String spring, Status status) {
        this.title = spring;
        this.status = status;
    }
}
