package com.onnjoy.backend.service;

import com.onnjoy.backend.dto.WeeklyProgramDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ClubProgramService {

    // 100kg Bench Press Club - 12 Week Program
    public List<WeeklyProgramDTO> getBenchPress12WeekProgram(BigDecimal startingMax, Integer currentWeek) {
        List<WeeklyProgramDTO> program = new ArrayList<>();

        // Weeks 1-4
        program.add(createWeeklyProgram(
                1, "Weeks 1-4", "Bench Press", 3, "8",
                "60-70% of max", "3 times per week",
                Arrays.asList("Push-ups", "Dumbbell Press", "Rows"),
                "Foundation building phase focusing on form and consistency",
                startingMax, 0.60, 0.70, currentWeek
        ));

        // Weeks 5-8
        program.add(createWeeklyProgram(
                5, "Weeks 5-8", "Bench Press", 4, "6",
                "70-80% of max", "3 times per week",
                Arrays.asList("Paused Bench", "Dips", "Rows"),
                "Strength building phase with increased intensity",
                startingMax, 0.70, 0.80, currentWeek
        ));

        // Weeks 9-11
        program.add(createWeeklyProgram(
                9, "Weeks 9-11", "Bench Press", 5, "3-5",
                "80-90% of max", "3 times per week",
                Arrays.asList("Board Press", "Close-Grip Bench"),
                "Peak strength phase focusing on maximum weight",
                startingMax, 0.80, 0.90, currentWeek
        ));

        // Week 12
        program.add(createWeeklyProgram(
                12, "Week 12", "Max Test", 1, "1",
                "100kg goal", "Rest and test",
                Arrays.asList(),
                "Rest week and test your 100kg bench press!",
                BigDecimal.valueOf(100), 1.0, 1.0, currentWeek
        ));

        return program;
    }

    // 100kg Bench Press Club - 8 Week Program (Advanced)
    public List<WeeklyProgramDTO> getBenchPress8WeekProgram(BigDecimal startingMax, Integer currentWeek) {
        List<WeeklyProgramDTO> program = new ArrayList<>();

        // Weeks 1-4
        program.add(createWeeklyProgram(
                1, "Weeks 1-4", "Bench Press", 4, "5",
                "75-85% of max", "3 times per week",
                Arrays.asList("Close-Grip Bench", "Paused Bench", "Dips"),
                "Technical refinement with moderate-high intensity",
                startingMax, 0.75, 0.85, currentWeek
        ));

        // Weeks 5-7
        program.add(createWeeklyProgram(
                5, "Weeks 5-7", "Bench Press", 5, "3",
                "85-95% of max", "3 times per week",
                Arrays.asList("Board Press", "Slingshot Bench"),
                "Peak intensity phase building to maximum strength",
                startingMax, 0.85, 0.95, currentWeek
        ));

        // Week 8
        program.add(createWeeklyProgram(
                8, "Week 8", "Max Test", 1, "1",
                "100kg goal", "Rest and test",
                Arrays.asList(),
                "Rest week and test your 100kg bench press!",
                BigDecimal.valueOf(100), 1.0, 1.0, currentWeek
        ));

        return program;
    }

    // 140kg Hip Thrust Club - 12 Week Program
    public List<WeeklyProgramDTO> getHipThrust12WeekProgram(BigDecimal startingMax, Integer currentWeek) {
        List<WeeklyProgramDTO> program = new ArrayList<>();

        program.add(createWeeklyProgram(
                1, "Weeks 1-4", "Hip Thrust", 3, "10",
                "60-70% of max", "3 times per week",
                Arrays.asList("Glute Bridge", "Romanian Deadlift", "Leg Curls"),
                "Building glute strength foundation with proper form",
                startingMax, 0.60, 0.70, currentWeek
        ));

        program.add(createWeeklyProgram(
                5, "Weeks 5-8", "Hip Thrust", 4, "8",
                "70-80% of max", "3 times per week",
                Arrays.asList("Single-Leg Hip Thrust", "Cable Pull-Through", "Bulgarian Split Squat"),
                "Progressive overload with increased volume",
                startingMax, 0.70, 0.80, currentWeek
        ));

        program.add(createWeeklyProgram(
                9, "Weeks 9-11", "Hip Thrust", 5, "5",
                "80-90% of max", "3 times per week",
                Arrays.asList("Paused Hip Thrust", "Banded Hip Thrust"),
                "Maximum strength development",
                startingMax, 0.80, 0.90, currentWeek
        ));

        program.add(createWeeklyProgram(
                12, "Week 12", "Max Test", 1, "1",
                "140kg goal", "Rest and test",
                Arrays.asList(),
                "Rest week and test your 140kg hip thrust!",
                BigDecimal.valueOf(140), 1.0, 1.0, currentWeek
        ));

        return program;
    }

    // 140kg Hip Thrust Club - 8 Week Program (Advanced)
    public List<WeeklyProgramDTO> getHipThrust8WeekProgram(BigDecimal startingMax, Integer currentWeek) {
        List<WeeklyProgramDTO> program = new ArrayList<>();

        program.add(createWeeklyProgram(
                1, "Weeks 1-4", "Hip Thrust", 4, "6",
                "75-85% of max", "3 times per week",
                Arrays.asList("Paused Hip Thrust", "Single-Leg Hip Thrust", "Cable Pull-Through"),
                "Technical refinement with moderate-high loads",
                startingMax, 0.75, 0.85, currentWeek
        ));

        program.add(createWeeklyProgram(
                5, "Weeks 5-7", "Hip Thrust", 5, "4",
                "85-95% of max", "3 times per week",
                Arrays.asList("Banded Hip Thrust", "Deficit Hip Thrust"),
                "Peak intensity phase",
                startingMax, 0.85, 0.95, currentWeek
        ));

        program.add(createWeeklyProgram(
                8, "Week 8", "Max Test", 1, "1",
                "140kg goal", "Rest and test",
                Arrays.asList(),
                "Rest week and test your 140kg hip thrust!",
                BigDecimal.valueOf(140), 1.0, 1.0, currentWeek
        ));

        return program;
    }

    // 50 Push-Ups Club - 6 Week Program
    public List<WeeklyProgramDTO> getPushUp6WeekProgram(BigDecimal startingMax, Integer currentWeek) {
        List<WeeklyProgramDTO> program = new ArrayList<>();

        program.add(createWeeklyProgram(
                1, "Weeks 1-3", "Push-ups", 4, "Max reps",
                "Progressive sets", "4-5 times per week",
                Arrays.asList("Incline Push-ups", "Knee Push-ups", "Plank Hold"),
                "Build endurance base with volume training",
                startingMax, 1.0, 1.2, currentWeek
        ));

        program.add(createWeeklyProgram(
                4, "Weeks 4-5", "Push-ups", 5, "Max reps",
                "Pyramid sets", "4-5 times per week",
                Arrays.asList("Diamond Push-ups", "Wide Push-ups", "Decline Push-ups"),
                "Peak endurance with varied techniques",
                startingMax, 1.2, 1.5, currentWeek
        ));

        program.add(createWeeklyProgram(
                6, "Week 6", "Max Test", 1, "50 reps",
                "50 push-ups goal", "Rest and test",
                Arrays.asList(),
                "Rest and test your 50 push-ups in one set!",
                BigDecimal.valueOf(50), 1.0, 1.0, currentWeek
        ));

        return program;
    }

    // 50 Push-Ups Club - 4 Week Program (Advanced)
    public List<WeeklyProgramDTO> getPushUp4WeekProgram(BigDecimal startingMax, Integer currentWeek) {
        List<WeeklyProgramDTO> program = new ArrayList<>();

        program.add(createWeeklyProgram(
                1, "Weeks 1-2", "Push-ups", 5, "Max reps",
                "Grease the groove", "5-6 times per week",
                Arrays.asList("Explosive Push-ups", "Archer Push-ups", "Weighted Push-ups"),
                "High frequency endurance building",
                startingMax, 1.0, 1.3, currentWeek
        ));

        program.add(createWeeklyProgram(
                3, "Week 3", "Push-ups", 5, "Max reps",
                "Peak sets", "5-6 times per week",
                Arrays.asList("Plyometric Push-ups", "One-Arm Assisted"),
                "Final push to 50 reps",
                startingMax, 1.3, 1.6, currentWeek
        ));

        program.add(createWeeklyProgram(
                4, "Week 4", "Max Test", 1, "50 reps",
                "50 push-ups goal", "Rest and test",
                Arrays.asList(),
                "Rest and test your 50 push-ups in one set!",
                BigDecimal.valueOf(50), 1.0, 1.0, currentWeek
        ));

        return program;
    }

    // 10 Pull-Ups Club - 8 Week Program
    public List<WeeklyProgramDTO> getPullUp8WeekProgram(BigDecimal startingMax, Integer currentWeek) {
        List<WeeklyProgramDTO> program = new ArrayList<>();

        program.add(createWeeklyProgram(
                1, "Weeks 1-4", "Pull-ups", 4, "Max reps",
                "Assisted progression", "3-4 times per week",
                Arrays.asList("Band-Assisted Pull-ups", "Negative Pull-ups", "Lat Pulldown"),
                "Build pulling strength foundation",
                startingMax, 1.0, 1.3, currentWeek
        ));

        program.add(createWeeklyProgram(
                5, "Weeks 5-7", "Pull-ups", 5, "Max reps",
                "Reduced assistance", "3-4 times per week",
                Arrays.asList("Chin-ups", "Australian Pull-ups", "Dead Hangs"),
                "Progressive overload to 10 reps",
                startingMax, 1.3, 1.7, currentWeek
        ));

        program.add(createWeeklyProgram(
                8, "Week 8", "Max Test", 1, "10 reps",
                "10 pull-ups goal", "Rest and test",
                Arrays.asList(),
                "Rest and test your 10 strict pull-ups!",
                BigDecimal.valueOf(10), 1.0, 1.0, currentWeek
        ));

        return program;
    }

    // 10 Pull-Ups Club - 6 Week Program (Advanced)
    public List<WeeklyProgramDTO> getPullUp6WeekProgram(BigDecimal startingMax, Integer currentWeek) {
        List<WeeklyProgramDTO> program = new ArrayList<>();

        program.add(createWeeklyProgram(
                1, "Weeks 1-3", "Pull-ups", 5, "Max reps",
                "Volume training", "4-5 times per week",
                Arrays.asList("Weighted Pull-ups", "Typewriter Pull-ups", "L-Sit Pull-ups"),
                "High-volume pulling work",
                startingMax, 1.0, 1.4, currentWeek
        ));

        program.add(createWeeklyProgram(
                4, "Weeks 4-5", "Pull-ups", 5, "Max reps",
                "Peak sets", "4-5 times per week",
                Arrays.asList("Explosive Pull-ups", "Chest-to-Bar Pull-ups"),
                "Final push to 10 reps",
                startingMax, 1.4, 1.8, currentWeek
        ));

        program.add(createWeeklyProgram(
                6, "Week 6", "Max Test", 1, "10 reps",
                "10 pull-ups goal", "Rest and test",
                Arrays.asList(),
                "Rest and test your 10 strict pull-ups!",
                BigDecimal.valueOf(10), 1.0, 1.0, currentWeek
        ));

        return program;
    }

    // Helper method to create weekly program with personalized calculations
    private WeeklyProgramDTO createWeeklyProgram(
            Integer weekNumber, String weekRange, String mainExercise,
            Integer sets, String reps, String intensity, String frequency,
            List<String> accessories, String description,
            BigDecimal startingMax, Double minPercent, Double maxPercent,
            Integer currentWeek
    ) {
        WeeklyProgramDTO dto = new WeeklyProgramDTO();
        dto.setWeekNumber(weekNumber);
        dto.setWeekRange(weekRange);
        dto.setMainExercise(mainExercise);
        dto.setSets(sets);
        dto.setReps(reps);
        dto.setIntensity(intensity);
        dto.setFrequency(frequency);
        dto.setAccessories(accessories);
        dto.setDescription(description);

        // Calculate personalized weight/reps recommendations
        if (startingMax != null) {
            BigDecimal minWeight = startingMax.multiply(BigDecimal.valueOf(minPercent))
                    .setScale(1, RoundingMode.HALF_UP);
            BigDecimal maxWeight = startingMax.multiply(BigDecimal.valueOf(maxPercent))
                    .setScale(1, RoundingMode.HALF_UP);

            dto.setRecommendedWeightMin(minWeight);
            dto.setRecommendedWeightMax(maxWeight);
        }

        // Lock/unlock logic
        dto.setIsLocked(weekNumber > currentWeek);
        dto.setIsCompleted(weekNumber < currentWeek);

        return dto;
    }

    // Get program based on club ID
    public List<WeeklyProgramDTO> getProgramForClub(Long clubId, BigDecimal startingMax, Integer currentWeek) {
        // Club IDs from V12__seed_clubs.sql
        // 1: 100kg Bench Press Club (12 weeks)
        // 2: 100kg Bench Press Club (Advanced) (8 weeks)
        // 3: 140kg Hip Thrust Club (12 weeks)
        // 4: 140kg Hip Thrust Club (Advanced) (8 weeks)
        // 5: 50 Push-Ups Club (6 weeks)
        // 6: 50 Push-Ups Club (Advanced) (4 weeks)
        // 7: 10 Pull-Ups Club (8 weeks)
        // 8: 10 Pull-Ups Club (Advanced) (6 weeks)

        switch (clubId.intValue()) {
            case 1:
                return getBenchPress12WeekProgram(startingMax, currentWeek);
            case 2:
                return getBenchPress8WeekProgram(startingMax, currentWeek);
            case 3:
                return getHipThrust12WeekProgram(startingMax, currentWeek);
            case 4:
                return getHipThrust8WeekProgram(startingMax, currentWeek);
            case 5:
                return getPushUp6WeekProgram(startingMax, currentWeek);
            case 6:
                return getPushUp4WeekProgram(startingMax, currentWeek);
            case 7:
                return getPullUp8WeekProgram(startingMax, currentWeek);
            case 8:
                return getPullUp6WeekProgram(startingMax, currentWeek);
            default:
                throw new IllegalArgumentException("Unknown club ID: " + clubId);
        }
    }

    // Get total weeks for a club
    public Integer getTotalWeeksForClub(Long clubId) {
        switch (clubId.intValue()) {
            case 1: return 12;
            case 2: return 8;
            case 3: return 12;
            case 4: return 8;
            case 5: return 6;
            case 6: return 4;
            case 7: return 8;
            case 8: return 6;
            default: throw new IllegalArgumentException("Unknown club ID: " + clubId);
        }
    }
}