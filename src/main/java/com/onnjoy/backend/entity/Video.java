package com.onnjoy.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "videos")
@Data
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "gym_id", nullable = false)
    private Gym gym;

    @Column(nullable = false)
    private String category; // Gender-based categories

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Integer reps = 3;

    @Column(name = "user_gender", length = 50)
    private String userGender; // MALE or FEMALE - for category validation

    @Column(name = "s3_key", nullable = false)
    private String s3Key;

    @Column(name = "s3_url")
    private String s3Url;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VideoStatus status = VideoStatus.PENDING;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum VideoStatus {
        PENDING,
        APPROVED,
        REJECTED,
        BANNED
    }
}