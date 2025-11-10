package com.onnjoy.backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onnjoy.backend.dto.BuddyMatchRequestDTO;
import com.onnjoy.backend.dto.BuddyMatchResponseDTO;
import com.onnjoy.backend.entity.BuddyRequest;
import com.onnjoy.backend.entity.User;
import com.onnjoy.backend.repository.BuddyRequestRepository;
import com.onnjoy.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuddyMatchingService {

    private final UserRepository userRepository;
    private final BuddyRequestRepository buddyRequestRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public User saveBuddyPreferences(BuddyMatchRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Save preferences
        user.setTrainingGoal(requestDTO.getTrainingGoal());
        user.setGender(requestDTO.getGender());
        user.setSocialBehavior(requestDTO.getSocialBehavior());
        user.setAgeRange(requestDTO.getAgeRange());
        user.setTelegramUsername(requestDTO.getTelegramUsername());
        user.setIsActivated(true); // Activate after preferences saved

        // Convert lists to JSON strings
        try {
            user.setPreferredLocations(objectMapper.writeValueAsString(requestDTO.getPreferredLocations()));
            user.setDailySchedule(objectMapper.writeValueAsString(requestDTO.getDailySchedule()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to save preferences: " + e.getMessage());
        }

        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public List<BuddyMatchResponseDTO> findMatches(Long userId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!currentUser.getIsActivated()) {
            throw new IllegalArgumentException("User must complete buddy preferences first");
        }

        List<User> allUsers = userRepository.findAll();
        List<BuddyMatchResponseDTO> matches = new ArrayList<>();

        // Get current user's preferences
        List<Long> userGyms = parseJsonToLongList(currentUser.getPreferredLocations());
        List<String> userSchedule = parseJsonToStringList(currentUser.getDailySchedule());

        for (User otherUser : allUsers) {
            // Skip self and non-activated users
            if (otherUser.getId().equals(userId) || !otherUser.getIsActivated()) {
                continue;
            }

            int score = calculateMatchScore(currentUser, otherUser, userGyms, userSchedule);

            // Only return matches with score >= 50
            if (score >= 50) {
                BuddyMatchResponseDTO match = new BuddyMatchResponseDTO();
                match.setUserId(otherUser.getId());
                match.setTrainingGoal(otherUser.getTrainingGoal());
                match.setGender(otherUser.getGender());
                match.setSocialBehavior(otherUser.getSocialBehavior());
                match.setAgeRange(otherUser.getAgeRange());
                match.setMatchScore(score);

                // Find common gyms and time slots
                List<Long> otherGyms = parseJsonToLongList(otherUser.getPreferredLocations());
                List<String> otherSchedule = parseJsonToStringList(otherUser.getDailySchedule());

                List<Long> commonGyms = userGyms.stream()
                        .filter(otherGyms::contains)
                        .collect(Collectors.toList());

                List<String> commonTimeSlots = userSchedule.stream()
                        .filter(otherSchedule::contains)
                        .collect(Collectors.toList());

                match.setCommonGyms(commonGyms);
                match.setCommonTimeSlots(commonTimeSlots);

                // Check if already connected
                boolean isConnected = buddyRequestRepository
                        .findBySenderIdAndReceiverId(userId, otherUser.getId())
                        .map(req -> req.getStatus() == BuddyRequest.RequestStatus.ACCEPTED)
                        .orElse(false);

                match.setIsConnected(isConnected);

                // Only show telegram if connected
                if (isConnected) {
                    match.setTelegramUsername(otherUser.getTelegramUsername());
                } else {
                    match.setTelegramUsername(null); // Hidden until accepted
                }

                matches.add(match);
            }
        }

        // Sort by match score (highest first)
        matches.sort((a, b) -> b.getMatchScore().compareTo(a.getMatchScore()));

        return matches;
    }

    private int calculateMatchScore(User user1, User user2, List<Long> user1Gyms, List<String> user1Schedule) {
        int score = 0;

        // Training goal match (+30 points)
        if (user1.getTrainingGoal() != null && user1.getTrainingGoal().equals(user2.getTrainingGoal())) {
            score += 30;
        }

        // Common gym (+25 points)
        List<Long> user2Gyms = parseJsonToLongList(user2.getPreferredLocations());
        boolean hasCommonGym = user1Gyms.stream().anyMatch(user2Gyms::contains);
        if (hasCommonGym) {
            score += 25;
        }

        // Common time slot (+25 points)
        List<String> user2Schedule = parseJsonToStringList(user2.getDailySchedule());
        boolean hasCommonTime = user1Schedule.stream().anyMatch(user2Schedule::contains);
        if (hasCommonTime) {
            score += 25;
        }

        // Social behavior compatibility (+10 points)
        if (isSocialBehaviorCompatible(user1.getSocialBehavior(), user2.getSocialBehavior())) {
            score += 10;
        }

        // Same age range (+10 points)
        if (user1.getAgeRange() != null && user1.getAgeRange().equals(user2.getAgeRange())) {
            score += 10;
        }

        return score;
    }

    private boolean isSocialBehaviorCompatible(String behavior1, String behavior2) {
        if (behavior1 == null || behavior2 == null) return false;

        // Define compatible pairs
        Map<String, List<String>> compatibilityMap = new HashMap<>();
        compatibilityMap.put("FOCUSED", Arrays.asList("FOCUSED", "NEUTRAL"));
        compatibilityMap.put("CHATTY", Arrays.asList("CHATTY", "SUPPORTIVE", "NEUTRAL"));
        compatibilityMap.put("COMPETITIVE", Arrays.asList("COMPETITIVE", "COACH_STYLE"));
        compatibilityMap.put("SUPPORTIVE", Arrays.asList("SUPPORTIVE", "CHATTY", "NEUTRAL"));
        compatibilityMap.put("NEUTRAL", Arrays.asList("NEUTRAL", "FOCUSED", "CHATTY", "SUPPORTIVE"));
        compatibilityMap.put("COACH_STYLE", Arrays.asList("COACH_STYLE", "COMPETITIVE"));
        compatibilityMap.put("SPOTTER_ONLY", Arrays.asList("SPOTTER_ONLY", "NEUTRAL"));

        return compatibilityMap.getOrDefault(behavior1, Collections.emptyList()).contains(behavior2);
    }

    /**
     * Parse JSON to List<Long> with proper Integer to Long conversion
     */
    private List<Long> parseJsonToLongList(String json) {
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            // First parse as List<Integer> since JSON numbers are typically integers
            List<Integer> integers = objectMapper.readValue(json, new TypeReference<List<Integer>>() {});
            // Convert to Long list
            return integers.stream()
                    .map(Integer::longValue)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Parse JSON to List<String>
     */
    private List<String> parseJsonToStringList(String json) {
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}