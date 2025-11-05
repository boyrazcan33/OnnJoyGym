package com.onnjoy.backend.controller;

import com.onnjoy.backend.entity.Club;
import com.onnjoy.backend.entity.ClubProgress;
import com.onnjoy.backend.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/{clubId}/join/{userId}")
    public ResponseEntity<ClubProgress> joinClub(@PathVariable Long clubId, @PathVariable Long userId) {
        ClubProgress progress = clubService.joinClubWithProgress(userId, clubId);
        return new ResponseEntity<>(progress, HttpStatus.CREATED);
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