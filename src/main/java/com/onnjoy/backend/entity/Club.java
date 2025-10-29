package com.onnjoy.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "clubs")
@Data
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String goal; // e.g., STRENGTH, HYPERTROPHY, ENDURANCE, WEIGHT_LOSS

    @Column(nullable = false)
    private String level; // BEGINNER, INTERMEDIATE, ADVANCED

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}