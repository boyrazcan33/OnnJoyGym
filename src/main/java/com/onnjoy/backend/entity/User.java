package com.onnjoy.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "USER"; // USER or ADMIN

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(length = 500)
    private String goals;

    @Column(length = 50)
    private String experience; // BEGINNER, INTERMEDIATE, ADVANCED

    @Column(name = "gym_preference")
    private Long gymPreference;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}