package com.onnjoy.backend.service;

import com.onnjoy.backend.dto.WeeklyProgramDTO;
import com.onnjoy.backend.entity.Club;
import com.onnjoy.backend.entity.User;
import com.onnjoy.backend.repository.ClubRepository;
import com.onnjoy.backend.repository.UserRepository;
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

    private List<com.onnjoy.backend.dto.DayProgramDTO> generateBeginnerStrengthProgram() {
        List<com.onnjoy.backend.dto.DayProgramDTO> days = new ArrayList<>();

        // Day 1: Upper Body
        days.add(new com.onnjoy.backend.dto.DayProgramDTO("Monday - Upper Body", Arrays.asList(
                new com.onnjoy.backend.dto.ExerciseDTO("Bench Press", 3, "8-10", "2-3 min", "Focus on form"),
                new com.onnjoy.backend.dto.ExerciseDTO("Barbell Row", 3, "8-10", "2-3 min", "Keep back straight"),
                new com.onnjoy.backend.dto.ExerciseDTO("Overhead Press", 3, "8-10", "2 min", "Core tight"),
                new com.onnjoy.backend.dto.ExerciseDTO("Pull-ups (Assisted)", 3, "6-8", "2 min", "Full range of motion"),
                new com.onnjoy.backend.dto.ExerciseDTO("Bicep Curls", 2, "10-12", "90 sec", "Controlled movement")
        )));

        // Day 2: Lower Body
        days.add(new com.onnjoy.backend.dto.DayProgramDTO("Wednesday - Lower Body", Arrays.asList(
                new com.onnjoy.backend.dto.ExerciseDTO("Squat", 3, "8-10", "3 min", "Depth is key"),
                new com.onnjoy.backend.dto.ExerciseDTO("Romanian Deadlift", 3, "8-10", "2-3 min", "Hinge at hips"),
                new com.onnjoy.backend.dto.ExerciseDTO("Leg Press", 3, "10-12", "2 min", "Full range"),
                new com.onnjoy.backend.dto.ExerciseDTO("Leg Curl", 3, "10-12", "90 sec", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Calf Raises", 3, "15-20", "60 sec", "")
        )));

        // Day 3: Full Body
        days.add(new com.onnjoy.backend.dto.DayProgramDTO("Friday - Full Body", Arrays.asList(
                new com.onnjoy.backend.dto.ExerciseDTO("Deadlift", 3, "6-8", "3 min", "Perfect form required"),
                new com.onnjoy.backend.dto.ExerciseDTO("Dumbbell Bench Press", 3, "8-10", "2 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Goblet Squat", 3, "10-12", "2 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Lat Pulldown", 3, "10-12", "90 sec", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Plank", 3, "30-60 sec", "60 sec", "Core activation")
        )));

        return days;
    }

    private List<com.onnjoy.backend.dto.DayProgramDTO> generateIntermediateStrengthProgram() {
        List<com.onnjoy.backend.dto.DayProgramDTO> days = new ArrayList<>();

        days.add(new com.onnjoy.backend.dto.DayProgramDTO("Monday - Chest & Triceps", Arrays.asList(
                new com.onnjoy.backend.dto.ExerciseDTO("Bench Press", 4, "6-8", "3 min", "Progressive overload"),
                new com.onnjoy.backend.dto.ExerciseDTO("Incline Dumbbell Press", 4, "8-10", "2 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Dips", 3, "8-12", "2 min", "Weighted if possible"),
                new com.onnjoy.backend.dto.ExerciseDTO("Close-Grip Bench Press", 3, "8-10", "2 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Tricep Pushdown", 3, "10-12", "90 sec", "")
        )));

        days.add(new com.onnjoy.backend.dto.DayProgramDTO("Wednesday - Back & Biceps", Arrays.asList(
                new com.onnjoy.backend.dto.ExerciseDTO("Deadlift", 4, "5-6", "3-4 min", "Heavy compound"),
                new com.onnjoy.backend.dto.ExerciseDTO("Barbell Row", 4, "6-8", "2-3 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Pull-ups", 4, "6-10", "2 min", "Weighted if able"),
                new com.onnjoy.backend.dto.ExerciseDTO("Face Pulls", 3, "12-15", "90 sec", "Rear delts"),
                new com.onnjoy.backend.dto.ExerciseDTO("Barbell Curl", 3, "8-10", "90 sec", "")
        )));

        days.add(new com.onnjoy.backend.dto.DayProgramDTO("Friday - Legs & Shoulders", Arrays.asList(
                new com.onnjoy.backend.dto.ExerciseDTO("Squat", 4, "5-6", "3-4 min", "Heavy focus"),
                new com.onnjoy.backend.dto.ExerciseDTO("Front Squat", 3, "8-10", "2-3 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Overhead Press", 4, "6-8", "2-3 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Lateral Raises", 3, "12-15", "90 sec", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Leg Curl", 3, "10-12", "90 sec", "")
        )));

        return days;
    }

    private List<com.onnjoy.backend.dto.DayProgramDTO> generateAdvancedStrengthProgram() {
        List<com.onnjoy.backend.dto.DayProgramDTO> days = new ArrayList<>();

        days.add(new com.onnjoy.backend.dto.DayProgramDTO("Monday - Heavy Bench", Arrays.asList(
                new com.onnjoy.backend.dto.ExerciseDTO("Competition Bench Press", 5, "3-5", "4-5 min", "90% 1RM"),
                new com.onnjoy.backend.dto.ExerciseDTO("Pause Bench Press", 4, "5-6", "3 min", "2 sec pause"),
                new com.onnjoy.backend.dto.ExerciseDTO("Close-Grip Bench", 4, "6-8", "2-3 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Weighted Dips", 3, "6-8", "2 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Overhead Tricep Extension", 3, "8-10", "90 sec", "")
        )));

        days.add(new com.onnjoy.backend.dto.DayProgramDTO("Wednesday - Heavy Deadlift", Arrays.asList(
                new com.onnjoy.backend.dto.ExerciseDTO("Competition Deadlift", 5, "3-5", "4-5 min", "90% 1RM"),
                new com.onnjoy.backend.dto.ExerciseDTO("Deficit Deadlift", 4, "5-6", "3 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Barbell Row", 4, "6-8", "2 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Weighted Pull-ups", 4, "5-8", "2 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Face Pulls", 3, "15-20", "90 sec", "")
        )));

        days.add(new com.onnjoy.backend.dto.DayProgramDTO("Friday - Heavy Squat", Arrays.asList(
                new com.onnjoy.backend.dto.ExerciseDTO("Competition Squat", 5, "3-5", "4-5 min", "90% 1RM"),
                new com.onnjoy.backend.dto.ExerciseDTO("Pause Squat", 4, "5-6", "3 min", "2 sec pause"),
                new com.onnjoy.backend.dto.ExerciseDTO("Front Squat", 3, "6-8", "2-3 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Romanian Deadlift", 3, "8-10", "2 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Bulgarian Split Squat", 3, "8-10/leg", "90 sec", "")
        )));

        return days;
    }

    private List<com.onnjoy.backend.dto.DayProgramDTO> generateHypertrophyProgram() {
        List<com.onnjoy.backend.dto.DayProgramDTO> days = new ArrayList<>();

        days.add(new com.onnjoy.backend.dto.DayProgramDTO("Monday - Chest", Arrays.asList(
                new com.onnjoy.backend.dto.ExerciseDTO("Flat Bench Press", 4, "8-12", "2 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Incline Dumbbell Press", 4, "10-12", "90 sec", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Cable Flyes", 3, "12-15", "60 sec", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Dips", 3, "10-15", "90 sec", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Push-ups", 3, "to failure", "60 sec", "")
        )));

        days.add(new com.onnjoy.backend.dto.DayProgramDTO("Tuesday - Back", Arrays.asList(
                new com.onnjoy.backend.dto.ExerciseDTO("Pull-ups", 4, "8-12", "2 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Barbell Row", 4, "10-12", "90 sec", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Lat Pulldown", 3, "12-15", "60 sec", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Cable Row", 3, "12-15", "60 sec", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Face Pulls", 3, "15-20", "60 sec", "")
        )));

        days.add(new com.onnjoy.backend.dto.DayProgramDTO("Thursday - Legs", Arrays.asList(
                new com.onnjoy.backend.dto.ExerciseDTO("Squat", 4, "8-12", "2-3 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Leg Press", 4, "12-15", "90 sec", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Romanian Deadlift", 3, "10-12", "90 sec", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Leg Curl", 4, "12-15", "60 sec", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Calf Raises", 4, "15-20", "60 sec", "")
        )));

        days.add(new com.onnjoy.backend.dto.DayProgramDTO("Friday - Shoulders & Arms", Arrays.asList(
                new com.onnjoy.backend.dto.ExerciseDTO("Overhead Press", 4, "8-12", "2 min", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Lateral Raises", 4, "12-15", "60 sec", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Barbell Curl", 3, "10-12", "90 sec", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Tricep Pushdown", 3, "12-15", "60 sec", ""),
                new com.onnjoy.backend.dto.ExerciseDTO("Hammer Curl", 3, "12-15", "60 sec", "")
        )));

        return days;
    }

    private List<com.onnjoy.backend.dto.DayProgramDTO> generateDefaultProgram() {
        return generateBeginnerStrengthProgram();
    }
}