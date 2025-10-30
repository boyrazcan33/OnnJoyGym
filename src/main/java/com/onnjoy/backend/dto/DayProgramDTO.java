package com.onnjoy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayProgramDTO {
    private String dayName;
    private List<ExerciseDTO> exercises;
}
