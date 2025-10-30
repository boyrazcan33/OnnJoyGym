package com.onnjoy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyProgramDTO {
    private String clubName;
    private String level;
    private String goal;
    private List<DayProgramDTO> days;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class DayProgramDTO {
    private String dayName;
    private List<ExerciseDTO> exercises;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ExerciseDTO {
    private String name;
    private Integer sets;
    private String reps;
    private String rest;
    private String notes;
}