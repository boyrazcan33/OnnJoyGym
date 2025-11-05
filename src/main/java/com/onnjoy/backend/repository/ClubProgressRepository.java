package com.onnjoy.backend.repository;

import com.onnjoy.backend.entity.ClubProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ClubProgressRepository extends JpaRepository<ClubProgress, Long> {
    Optional<ClubProgress> findByUserIdAndClubId(Long userId, Long clubId);
    List<ClubProgress> findByUserId(Long userId);
    boolean existsByUserIdAndClubId(Long userId, Long clubId);
}