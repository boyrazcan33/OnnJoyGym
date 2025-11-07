package com.onnjoy.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "gym_brands")
@Data
public class GymBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "total_locations", nullable = false)
    private Integer totalLocations = 1;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}