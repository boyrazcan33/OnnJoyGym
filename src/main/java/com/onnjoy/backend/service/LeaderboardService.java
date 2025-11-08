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

    public List<Leaderboard> getLeaderboardByGym(Long gymId, String gender) {
        if (gender != null && !gender.isEmpty()) {
            return leaderboardRepository.findByGymIdAndGenderOrderByWeightDesc(gymId, gender);
        }
        return leaderboardRepository.findByGymIdOrderByWeightDesc(gymId);
    }

    public List<Leaderboard> getLeaderboardByGymAndCategory(Long gymId, String category, String gender) {
        if (gender != null && !gender.isEmpty()) {
            return leaderboardRepository.findByGymIdAndCategoryAndGenderOrderByWeightDesc(gymId, category, gender);
        }
        return leaderboardRepository.findTopByGymAndCategory(gymId, category);
    }

    public List<Leaderboard> getAllLeaderboard(String gender) {
        if (gender != null && !gender.isEmpty()) {
            return leaderboardRepository.findByGenderOrderByWeightDesc(gender);
        }
        return leaderboardRepository.findAll();
    }
}