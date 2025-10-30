package com.onnjoy.backend.service;

import com.onnjoy.backend.entity.Leaderboard;
import com.onnjoy.backend.repository.LeaderboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;

    public List<Leaderboard> getLeaderboardByGym(Long gymId) {
        return leaderboardRepository.findByGymIdOrderByWeightDesc(gymId);
    }

    public List<Leaderboard> getLeaderboardByGymAndCategory(Long gymId, String category) {
        return leaderboardRepository.findTopByGymAndCategory(gymId, category);
    }

    public List<Leaderboard> getAllLeaderboard() {
        return leaderboardRepository.findAll();
    }
}