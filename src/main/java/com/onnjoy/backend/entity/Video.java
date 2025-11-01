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
    private String category; // BENCH_PRESS, SQUAT, DEADLIFT, PULL_UP, etc.

    @Column(nullable = false)
    private Double weight; // Weight lifted in kg

    @Column(nullable = false)
    private Integer reps = 3; // 👈 YENİ FIELD - Fixed to 3 reps

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