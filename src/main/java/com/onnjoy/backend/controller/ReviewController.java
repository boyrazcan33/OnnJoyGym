package com.onnjoy.backend.controller;

import com.onnjoy.backend.entity.Review;
import com.onnjoy.backend.entity.User;
import com.onnjoy.backend.repository.UserRepository;
import com.onnjoy.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam(required = false) Long userId) {
        // Check if user is activated (if userId provided)
        if (userId != null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            if (!user.getIsActivated()) {
                throw new IllegalArgumentException("You must complete buddy matching or upload a video to view gym reviews");
            }
        }

        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/gym/{gymId}")
    public ResponseEntity<List<Review>> getReviewsByGym(@PathVariable Long gymId, @RequestParam(required = false) Long userId) {
        // Check if user is activated (if userId provided)
        if (userId != null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            if (!user.getIsActivated()) {
                throw new IllegalArgumentException("You must complete buddy matching or upload a video to view gym reviews");
            }
        }

        return ResponseEntity.ok(reviewService.getReviewsByGymId(gymId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review created = reviewService.createReview(review);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.updateReview(id, review));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}