package com.onnjoy.backend.controller;

import com.onnjoy.backend.dto.*;
import com.onnjoy.backend.entity.User;
import com.onnjoy.backend.service.BuddyMatchingService;
import com.onnjoy.backend.service.BuddyRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buddies")
@RequiredArgsConstructor
public class BuddyMatchingController {

    private final BuddyMatchingService buddyMatchingService;
    private final BuddyRequestService buddyRequestService;

    @PostMapping("/preferences")
    public ResponseEntity<User> saveBuddyPreferences(@Valid @RequestBody BuddyMatchRequestDTO requestDTO) {
        User user = buddyMatchingService.saveBuddyPreferences(requestDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/match/{userId}")
    public ResponseEntity<List<BuddyMatchResponseDTO>> findMatches(@PathVariable Long userId) {
        List<BuddyMatchResponseDTO> matches = buddyMatchingService.findMatches(userId);
        return ResponseEntity.ok(matches);
    }

    @PostMapping("/requests/send")
    public ResponseEntity<BuddyRequestResponseDTO> sendRequest(@Valid @RequestBody BuddySendRequestDTO dto) {
        BuddyRequestResponseDTO response = buddyRequestService.sendRequest(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/requests/{requestId}/accept")
    public ResponseEntity<BuddyRequestResponseDTO> acceptRequest(@PathVariable Long requestId) {
        BuddyRequestResponseDTO response = buddyRequestService.acceptRequest(requestId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/requests/{requestId}/reject")
    public ResponseEntity<BuddyRequestResponseDTO> rejectRequest(@PathVariable Long requestId) {
        BuddyRequestResponseDTO response = buddyRequestService.rejectRequest(requestId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/requests/received/{userId}")
    public ResponseEntity<List<BuddyRequestResponseDTO>> getReceivedRequests(@PathVariable Long userId) {
        List<BuddyRequestResponseDTO> requests = buddyRequestService.getReceivedRequests(userId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/requests/sent/{userId}")
    public ResponseEntity<List<BuddyRequestResponseDTO>> getSentRequests(@PathVariable Long userId) {
        List<BuddyRequestResponseDTO> requests = buddyRequestService.getSentRequests(userId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/connections/{userId}")
    public ResponseEntity<List<BuddyRequestResponseDTO>> getAcceptedConnections(@PathVariable Long userId) {
        List<BuddyRequestResponseDTO> connections = buddyRequestService.getAcceptedConnections(userId);
        return ResponseEntity.ok(connections);
    }
}