package com.onnjoy.backend.repository;

import com.onnjoy.backend.entity.UserClub;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserClubRepository extends JpaRepository<UserClub, Long> {
    List<UserClub> findByUserId(Long userId);
    List<UserClub> findByClubId(Long clubId);
    Optional<UserClub> findByUserIdAndClubId(Long userId, Long clubId);
    boolean existsByUserIdAndClubId(Long userId, Long clubId);
}