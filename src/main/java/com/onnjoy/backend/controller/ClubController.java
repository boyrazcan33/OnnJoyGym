package com.onnjoy.backend.controller;

import com.onnjoy.backend.entity.Club;
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