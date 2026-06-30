package com.onnjoy.backend.controller;

import com.onnjoy.backend.dto.UserCommentRequestDTO;
import com.onnjoy.backend.service.UserCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user-comments")
@RequiredArgsConstructor
public class UserCommentController {

    private final UserCommentService userCommentService;

    @PostMapping
    public ResponseEntity<Map<String, String>> submitComment(
            @Valid @RequestBody UserCommentRequestDTO dto,
            Authentication authentication) {

        String userEmail = (String) authentication.getPrincipal();
        userCommentService.submitComment(userEmail, dto);
        return ResponseEntity.ok(Map.of("message", "Thank you! We will review your comment."));
    }
}
