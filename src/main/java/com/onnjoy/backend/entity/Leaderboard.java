package com.onnjoy.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "leaderboard")
@Data
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "gym_id", nullable = false)
    private Gym gym;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Column(nullable = false)
    private String category; // BENCH_PRESS, SQUAT, DEADLIFT, etc.

    @Column(nullable = false)
    private Double weight;

    @Column(name = "ranking")
    private Integer ranking; // Position in leaderboard for this gym+category

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}