package com.onnjoy.backend.controller;

import com.onnjoy.backend.entity.Club;
import com.onnjoy.backend.entity.ClubProgress;
import com.onnjoy.backend.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @GetMapping
    public ResponseEntity<List<Club>> getAllClubs() {
        return ResponseEntity.ok(clubService.getAllClubs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Club> getClubById(@PathVariable Long id) {
        return ResponseEntity.ok(clubService.getClubById(id));
    }

    // NEW: Get club recommendation based on user's starting value
    @PostMapping("/recommend")
    public ResponseEntity<Map<String, Object>> getRecommendation(@RequestBody Map<String, Object> request) {
        Long clubId = Long.valueOf(request.get("clubId").toString());
        BigDecimal userValue = new BigDecimal(request.get("userValue").toString());

        Map<String, Object> recommendation = clubService.getClubRecommendation(clubId, userValue);
        return ResponseEntity.ok(recommendation);
    }

    // UPDATED: Join club with starting_max
    @PostMapping("/{clubId}/join/{userId}")
    public ResponseEntity<ClubProgress> joinClub(
            @PathVariable Long clubId,
            @PathVariable Long userId,
            @RequestBody Map<String, Object> request
    ) {
        BigDecimal startingMax = new BigDecimal(request.get("startingMax").toString());
        ClubProgress progress = clubService.joinClubWithProgress(userId, clubId, startingMax);
        return new ResponseEntity<>(progress, HttpStatus.CREATED);
    }

    // NEW: Complete current week
    @PostMapping("/{clubId}/complete-week/{userId}")
    public ResponseEntity<ClubProgress> completeWeek(
            @PathVariable Long clubId,
            @PathVariable Long userId
    ) {
        ClubProgress progress = clubService.completeWeek(userId, clubId);
        return ResponseEntity.ok(progress);
    }

    @GetMapping("/{clubId}/progress/{userId}")
    public ResponseEntity<ClubProgress> getCurrentProgress(@PathVariable Long clubId, @PathVariable Long userId) {
        ClubProgress progress = clubService.getCurrentProgress(userId, clubId);
        return ResponseEntity.ok(progress);
    }

    @GetMapping("/progress/{userId}")
    public ResponseEntity<List<ClubProgress>> getUserClubProgress(@PathVariable Long userId) {
        List<ClubProgress> progress = clubService.getUserClubProgress(userId);
        return ResponseEntity.ok(progress);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Club> createClub(@RequestBody Club club) {
        Club created = clubService.createClub(club);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Club> updateClub(@PathVariable Long id, @RequestBody Club club) {
        return ResponseEntity.ok(clubService.updateClub(id, club));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        clubService.deleteClub(id);
        return ResponseEntity.noContent().build();
    }
}