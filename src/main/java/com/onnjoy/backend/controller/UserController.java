package com.onnjoy.backend.controller;

import com.onnjoy.backend.dto.ProfileUpdateDTO;
import com.onnjoy.backend.entity.User;
import com.onnjoy.backend.entity.UserClub;
import com.onnjoy.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}/profile")
    public ResponseEntity<User> getUserProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<User> updateProfile(@PathVariable Long id, @RequestBody ProfileUpdateDTO profileDTO) {
        return ResponseEntity.ok(userService.updateProfile(id, profileDTO));
    }

    @PostMapping("/{userId}/clubs/{clubId}/join")
    public ResponseEntity<UserClub> joinClub(@PathVariable Long userId, @PathVariable Long clubId) {
        UserClub userClub = userService.joinClub(userId, clubId);
        return new ResponseEntity<>(userClub, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/clubs/{clubId}/leave")
    public ResponseEntity<Void> leaveClub(@PathVariable Long userId, @PathVariable Long clubId) {
        userService.leaveClub(userId, clubId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/clubs")
    public ResponseEntity<List<UserClub>> getUserClubs(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserClubs(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(
            @RequestParam(required = false) Long gymId,
            @RequestParam(required = false) String goal,
            @RequestParam(required = false) String experience) {
        return ResponseEntity.ok(userService.searchUsers(gymId, goal, experience));
    }
}