package com.onnjoy.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gym_id", nullable = false)
    private Gym gym;

    @Column(nullable = false)
    private Integer rating; // 1-5

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "is_expert")
    private Boolean isExpert = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}