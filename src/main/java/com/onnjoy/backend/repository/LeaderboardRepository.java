package com.onnjoy.backend.repository;

import com.onnjoy.backend.entity.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {

    @Query("SELECT l FROM Leaderboard l WHERE l.gym.id = :gymId AND l.category = :category ORDER BY l.weight DESC")
    List<Leaderboard> findTopByGymAndCategory(Long gymId, String category);

    List<Leaderboard> findByGymIdOrderByWeightDesc(Long gymId);
}