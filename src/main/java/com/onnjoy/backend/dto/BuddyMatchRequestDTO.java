package com.onnjoy.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class BuddyMatchRequestDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    // Optional — inactive in matching scoring but kept for future use
    private String trainingGoal; // HYPERTROPHY, FAT_LOSS, ENDURANCE, MOBILITY

    // Collected at registration — not re-sent from buddy preferences form
    private String gender; // MALE, FEMALE, NON_BINARY

    @NotNull(message = "Preferred locations are required")
    @Size(min = 3, message = "Select at least 3 gym branches")
    private List<Long> preferredLocations; // Gym IDs (min 3, max 2 brands enforced on frontend)

    @NotNull(message = "Daily schedule is required")
    @Size(min = 1, max = 4, message = "Select 1-4 time slots")
    private List<String> dailySchedule; // 4 slots: MORNING, AFTERNOON, EVENING, NIGHT

    // Optional — inactive in matching scoring but kept for future use
    private String socialBehavior; // FOCUSED, CHATTY, COMPETITIVE, etc.

    // Optional — inactive in matching scoring but kept for future use
    private String ageRange; // 16-30, 31-45, 45+

    // Collected at registration — not re-sent from buddy preferences form
    private String telegramUsername;
}