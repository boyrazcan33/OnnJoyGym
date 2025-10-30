package com.onnjoy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO {
    private String name;
    private Integer sets;
    private String reps;
    private String rest;
    private String notes;
}
