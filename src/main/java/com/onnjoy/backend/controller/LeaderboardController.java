package com.onnjoy.backend.controller;

import com.onnjoy.backend.entity.Leaderboard;
import com.onnjoy.backend.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping("/gym/{gymId}")
    public ResponseEntity<List<Leaderboard>> getLeaderboardByGym(@PathVariable Long gymId) {
        return ResponseEntity.ok(leaderboardService.getLeaderboardByGym(gymId));
    }

    @GetMapping("/gym/{gymId}/category/{category}")
    public ResponseEntity<List<Leaderboard>> getLeaderboardByGymAndCategory(
            @PathVariable Long gymId,
            @PathVariable String category) {
        return ResponseEntity.ok(leaderboardService.getLeaderboardByGymAndCategory(gymId, category));
    }

    @GetMapping
    public ResponseEntity<List<Leaderboard>> getAllLeaderboard() {
        return ResponseEntity.ok(leaderboardService.getAllLeaderboard());
    }
}