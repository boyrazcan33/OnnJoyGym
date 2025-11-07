package com.onnjoy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyProgramDTO {
    private Integer weekNumber;
    private String weekRange;
    private String mainExercise;
    private Integer sets;
    private String reps;
    private String intensity;
    private String frequency;
    private List<String> accessories;
    private String description;

    // Personalized recommendations
    private BigDecimal recommendedWeightMin;
    private BigDecimal recommendedWeightMax;

    // Lock/unlock status
    private Boolean isLocked;
    private Boolean isCompleted;
}