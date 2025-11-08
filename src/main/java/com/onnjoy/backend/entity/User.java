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

    @Column(length = 50)
    private String experience; // BEGINNER, INTERMEDIATE, ADVANCED

    @Column(name = "gym_preference")
    private Long gymPreference;

    @Column(name = "telegram_username", length = 100)
    private String telegramUsername; // @username format for buddy contact

    // ===== BUDDY MATCHING FIELDS =====

    @Column(name = "training_goal", length = 100)
    private String trainingGoal; // HYPERTROPHY, FAT_LOSS, ENDURANCE, MOBILITY

    @Column(length = 50)
    private String gender; // MALE, FEMALE, NON_BINARY

    @Column(name = "preferred_locations", columnDefinition = "TEXT")
    private String preferredLocations; // JSON array of gym IDs (max 5)

    @Column(name = "daily_schedule", columnDefinition = "TEXT")
    private String dailySchedule; // JSON array of time slots (max 2)

    @Column(name = "social_behavior", length = 100)
    private String socialBehavior; // FOCUSED, CHATTY, COMPETITIVE, SUPPORTIVE, etc.

    @Column(name = "age_range", length = 20)
    private String ageRange; // 16-30, 31-45, 45+

    @Column(name = "is_activated")
    private Boolean isActivated = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "email_verified")
    private Boolean emailVerified = false;

    @Column(name = "verification_token")
    private String verificationToken;
}