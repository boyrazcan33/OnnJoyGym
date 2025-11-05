package com.onnjoy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuddyMatchResponseDTO {

    private Long userId;
    private String trainingGoal;
    private String gender;
    private String socialBehavior;
    private String ageRange;
    private List<String> commonTimeSlots;
    private List<Long> commonGyms;
    private Integer matchScore; // 0-100 compatibility score

    // Telegram username - only visible after request accepted
    private String telegramUsername;
    private Boolean isConnected; // true if buddy request already accepted
}