package com.onnjoy.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id") // CHANGED: from gym_id to brand_id
    private GymBrand gymBrand; // CHANGED: from Gym to GymBrand

    @Column(nullable = false)
    private Integer rating; // 1-5

    @Column(name = "rating_decimal")
    private BigDecimal ratingDecimal; // NEW: 4.7, 3.5, etc.

    @Column(name = "price_info", columnDefinition = "TEXT")
    private String priceInfo; // NEW: "€59/month (single club)"

    @Column(name = "pros", columnDefinition = "TEXT")
    private String pros; // NEW: JSON array of pros

    @Column(name = "cons", columnDefinition = "TEXT")
    private String cons; // NEW: JSON array of cons

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