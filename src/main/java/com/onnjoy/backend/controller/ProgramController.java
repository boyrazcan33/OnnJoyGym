package com.onnjoy.backend.controller;

import com.onnjoy.backend.dto.WeeklyProgramDTO;
import com.onnjoy.backend.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @GetMapping("/weekly")
    public ResponseEntity<WeeklyProgramDTO> getWeeklyProgram(
            @RequestParam Long userId,
            @RequestParam Long clubId) {
        return ResponseEntity.ok(programService.generateWeeklyProgram(userId, clubId));
    }
}