package com.onnjoy.backend.service;

import com.onnjoy.backend.dto.WeeklyProgramDTO;
import com.onnjoy.backend.entity.Club;
import com.onnjoy.backend.entity.User;
import com.onnjoy.backend.repository.ClubRepository;
import com.onnjoy.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final UserRepository userRepository;
    private final ClubRepository clubRepository;

    public WeeklyProgramDTO generateWeeklyProgram(Long userId, Long clubId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("Club not found"));

        String level = club.getLevel();
        String goal = club.getGoal();

        WeeklyProgramDTO program = new WeeklyProgramDTO();
        program.setClubName(club.getName());
        program.setLevel(level);
        program.setGoal(goal);

        // Generate program based on level and goal
        if ("STRENGTH".equals(goal) && "BEGINNER".equals(level)) {
            program.setDays(generateBeginnerStrengthProgram());
        } else if ("STRENGTH".equals(goal) && "INTERMEDIATE".equals(level)) {
            program.setDays(generateIntermediateStrengthProgram());
        } else if ("STRENGTH".equals(goal) && "ADVANCED".equals(level)) {
            program.setDays(generateAdvancedStrengthProgram());
        } else if ("HYPERTROPHY".equals(goal) && "INTERMEDIATE".equals(level)) {
            program.setDays(generateHypertrophyProgram());
        } else {
            program.setDays(generateDefaultProgram());
        }

        return program;
    }

    private List<DayProgramDTO> generateBeginnerStrengthProgram() {
        List<DayProgramDTO> days = new ArrayList<>();

        // Day 1: Upper Body
        days.add(new DayProgramDTO("Monday - Upper Body", Arrays.asList(
                new ExerciseDTO("Bench Press", 3, "8-10", "2-3 min", "Focus on form"),
                new ExerciseDTO("Barbell Row", 3, "8-10", "2-3 min", "Keep back straight"),
                new ExerciseDTO("Overhead Press", 3, "8-10", "2 min", "Core tight"),
                new ExerciseDTO("Pull-ups (Assisted)", 3, "6-8", "2 min", "Full range of motion"),
                new ExerciseDTO("Bicep Curls", 2, "10-12", "90 sec", "Controlled movement")
        )));

        // Day 2: Lower Body
        days.add(new DayProgramDTO("Wednesday - Lower Body", Arrays.asList(
                new ExerciseDTO("Squat", 3, "8-10", "3 min", "Depth is key"),
                new ExerciseDTO("Romanian Deadlift", 3, "8-10", "2-3 min", "Hinge at hips"),
                new ExerciseDTO("Leg Press", 3, "10-12", "2 min", "Full range"),
                new ExerciseDTO("Leg Curl", 3, "10-12", "90 sec", ""),
                new ExerciseDTO("Calf Raises", 3, "15-20", "60 sec", "")
        )));

        // Day 3: Full Body
        days.add(new DayProgramDTO("Friday - Full Body", Arrays.asList(
                new ExerciseDTO("Deadlift", 3, "6-8", "3 min", "Perfect form required"),
                new ExerciseDTO("Dumbbell Bench Press", 3, "8-10", "2 min", ""),
                new ExerciseDTO("Goblet Squat", 3, "10-12", "2 min", ""),
                new ExerciseDTO("Lat Pulldown", 3, "10-12", "90 sec", ""),
                new ExerciseDTO("Plank", 3, "30-60 sec", "60 sec", "Core activation")
        )));

        return days;
    }

    private List<DayProgramDTO> generateIntermediateStrengthProgram() {
        List<DayProgramDTO> days = new ArrayList<>();

        days.add(new DayProgramDTO("Monday - Chest & Triceps", Arrays.asList(
                new ExerciseDTO("Bench Press", 4, "6-8", "3 min", "Progressive overload"),
                new ExerciseDTO("Incline Dumbbell Press", 4, "8-10", "2 min", ""),
                new ExerciseDTO("Dips", 3, "8-12", "2 min", "Weighted if possible"),
                new ExerciseDTO("Close-Grip Bench Press", 3, "8-10", "2 min", ""),
                new ExerciseDTO("Tricep Pushdown", 3, "10-12", "90 sec", "")
        )));

        days.add(new DayProgramDTO("Wednesday - Back & Biceps", Arrays.asList(
                new ExerciseDTO("Deadlift", 4, "5-6", "3-4 min", "Heavy compound"),
                new ExerciseDTO("Barbell Row", 4, "6-8", "2-3 min", ""),
                new ExerciseDTO("Pull-ups", 4, "6-10", "2 min", "Weighted if able"),
                new ExerciseDTO("Face Pulls", 3, "12-15", "90 sec", "Rear delts"),
                new ExerciseDTO("Barbell Curl", 3, "8-10", "90 sec", "")
        )));

        days.add(new DayProgramDTO("Friday - Legs & Shoulders", Arrays.asList(
                new ExerciseDTO("Squat", 4, "5-6", "3-4 min", "Heavy focus"),
                new ExerciseDTO("Front Squat", 3, "8-10", "2-3 min", ""),
                new ExerciseDTO("Overhead Press", 4, "6-8", "2-3 min", ""),
                new ExerciseDTO("Lateral Raises", 3, "12-15", "90 sec", ""),
                new ExerciseDTO("Leg Curl", 3, "10-12", "90 sec", "")
        )));

        return days;
    }

    private List<DayProgramDTO> generateAdvancedStrengthProgram() {
        List<DayProgramDTO> days = new ArrayList<>();

        days.add(new DayProgramDTO("Monday - Heavy Bench", Arrays.asList(
                new ExerciseDTO("Competition Bench Press", 5, "3-5", "4-5 min", "90% 1RM"),
                new ExerciseDTO("Pause Bench Press", 4, "5-6", "3 min", "2 sec pause"),
                new ExerciseDTO("Close-Grip Bench", 4, "6-8", "2-3 min", ""),
                new ExerciseDTO("Weighted Dips", 3, "6-8", "2 min", ""),
                new ExerciseDTO("Overhead Tricep Extension", 3, "8-10", "90 sec", "")
        )));

        days.add(new DayProgramDTO("Wednesday - Heavy Deadlift", Arrays.asList(
                new ExerciseDTO("Competition Deadlift", 5, "3-5", "4-5 min", "90% 1RM"),
                new ExerciseDTO("Deficit Deadlift", 4, "5-6", "3 min", ""),
                new ExerciseDTO("Barbell Row", 4, "6-8", "2 min", ""),
                new ExerciseDTO("Weighted Pull-ups", 4, "5-8", "2 min", ""),
                new ExerciseDTO("Face Pulls", 3, "15-20", "90 sec", "")
        )));

        days.add(new DayProgramDTO("Friday - Heavy Squat", Arrays.asList(
                new ExerciseDTO("Competition Squat", 5, "3-5", "4-5 min", "90% 1RM"),
                new ExerciseDTO("Pause Squat", 4, "5-6", "3 min", "2 sec pause"),
                new ExerciseDTO("Front Squat", 3, "6-8", "2-3 min", ""),
                new ExerciseDTO("Romanian Deadlift", 3, "8-10", "2 min", ""),
                new ExerciseDTO("Bulgarian Split Squat", 3, "8-10/leg", "90 sec", "")
        )));

        return days;
    }

    private List<DayProgramDTO> generateHypertrophyProgram() {
        List<DayProgramDTO> days = new ArrayList<>();

        days.add(new DayProgramDTO("Monday - Chest", Arrays.asList(
                new ExerciseDTO("Flat Bench Press", 4, "8-12", "2 min", ""),
                new ExerciseDTO("Incline Dumbbell Press", 4, "10-12", "90 sec", ""),
                new ExerciseDTO("Cable Flyes", 3, "12-15", "60 sec", ""),
                new ExerciseDTO("Dips", 3, "10-15", "90 sec", ""),
                new ExerciseDTO("Push-ups", 3, "to failure", "60 sec", "")
        )));

        days.add(new DayProgramDTO("Tuesday - Back", Arrays.asList(
                new ExerciseDTO("Pull-ups", 4, "8-12", "2 min", ""),
                new ExerciseDTO("Barbell Row", 4, "10-12", "90 sec", ""),
                new ExerciseDTO("Lat Pulldown", 3, "12-15", "60 sec", ""),
                new ExerciseDTO("Cable Row", 3, "12-15", "60 sec", ""),
                new ExerciseDTO("Face Pulls", 3, "15-20", "60 sec", "")
        )));

        days.add(new DayProgramDTO("Thursday - Legs", Arrays.asList(
                new ExerciseDTO("Squat", 4, "8-12", "2-3 min", ""),
                new ExerciseDTO("Leg Press", 4, "12-15", "90 sec", ""),
                new ExerciseDTO("Romanian Deadlift", 3, "10-12", "90 sec", ""),
                new ExerciseDTO("Leg Curl", 4, "12-15", "60 sec", ""),
                new ExerciseDTO("Calf Raises", 4, "15-20", "60 sec", "")
        )));

        days.add(new DayProgramDTO("Friday - Shoulders & Arms", Arrays.asList(
                new ExerciseDTO("Overhead Press", 4, "8-12", "2 min", ""),
                new ExerciseDTO("Lateral Raises", 4, "12-15", "60 sec", ""),
                new ExerciseDTO("Barbell Curl", 3, "10-12", "90 sec", ""),
                new ExerciseDTO("Tricep Pushdown", 3, "12-15", "60 sec", ""),
                new ExerciseDTO("Hammer Curl", 3, "12-15", "60 sec", "")
        )));

        return days;
    }

    private List<DayProgramDTO> generateDefaultProgram() {
        return generateBeginnerStrengthProgram();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class DayProgramDTO {
        private String dayName;
        private List<ExerciseDTO> exercises;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ExerciseDTO {
        private String name;
        private Integer sets;
        private String reps;
        private String rest;
        private String notes;
    }
}