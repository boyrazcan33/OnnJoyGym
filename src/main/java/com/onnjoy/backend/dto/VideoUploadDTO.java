package com.onnjoy.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VideoUploadDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Gym ID is required")
    private Long gymId;

    @NotNull(message = "Category is required")
    @Pattern(
            regexp = "BENCH_PRESS|SQUAT|DEADLIFT|PULL_UP",
            message = "Invalid category. Must be: BENCH_PRESS, SQUAT, DEADLIFT, or PULL_UP"
    )
    private String category;

    @NotNull(message = "Weight is required")
    @Min(value = 1, message = "Weight must be at least 1 kg")
    @Max(value = 500, message = "Weight cannot exceed 500 kg")
    private Double weight;

    @NotNull(message = "File name is required")
    @Pattern(
            regexp = ".*\\.mp4$",
            message = "Only MP4 format is accepted",
            flags = Pattern.Flag.CASE_INSENSITIVE
    )
    private String fileName;

    @NotNull(message = "Reps count is required")
    @Min(value = 3, message = "Must be exactly 3 reps")
    @Max(value = 3, message = "Must be exactly 3 reps")
    private Integer reps = 3;
}