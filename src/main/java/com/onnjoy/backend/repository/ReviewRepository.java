package com.onnjoy.backend.repository;

import com.onnjoy.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByGymId(Long gymId);
}