package com.onnjoy.backend.controller;

import com.onnjoy.backend.dto.WeeklyProgramDTO;
import com.onnjoy.backend.entity.ClubProgress;
import com.onnjoy.backend.service.ClubProgramService;
import com.onnjoy.backend.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
public class ProgramController {

    private final ClubProgramService clubProgramService;
    private final ClubService clubService;

    @GetMapping("/weekly")
    public ResponseEntity<List<WeeklyProgramDTO>> getWeeklyProgram(
            @RequestParam Long userId,
            @RequestParam Long clubId) {

        // Get user's progress
        ClubProgress progress = clubService.getCurrentProgress(userId, clubId);

        // Get weekly program with personalized recommendations
        List<WeeklyProgramDTO> program = clubProgramService.getProgramForClub(
                clubId,
                progress.getStartingMax(),
                progress.getCurrentWeek()
        );

        return ResponseEntity.ok(program);
    }
}