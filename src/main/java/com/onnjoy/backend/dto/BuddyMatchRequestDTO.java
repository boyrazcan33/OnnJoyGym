package com.onnjoy.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class BuddyMatchRequestDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Training goal is required")
    private String trainingGoal; // HYPERTROPHY, FAT_LOSS, ENDURANCE, MOBILITY

    @NotBlank(message = "Gender is required")
    private String gender; // MALE, FEMALE, NON_BINARY

    @NotNull(message = "Preferred locations are required")
    @Size(min = 1, max = 5, message = "Select 1-5 gym locations")
    private List<Long> preferredLocations; // Gym IDs

    @NotNull(message = "Daily schedule is required")
    @Size(min = 1, max = 2, message = "Select 1-2 time slots")
    private List<String> dailySchedule; // Time slots

    @NotBlank(message = "Social behavior is required")
    private String socialBehavior; // FOCUSED, CHATTY, COMPETITIVE, etc.

    @NotBlank(message = "Age range is required")
    private String ageRange; // 16-30, 31-45, 45+

    @NotBlank(message = "Telegram username is required")
    @Pattern(regexp = "^@[a-zA-Z0-9_]{5,32}$", message = "Invalid Telegram username format (must start with @ and be 5-32 characters)")
    private String telegramUsername;
}