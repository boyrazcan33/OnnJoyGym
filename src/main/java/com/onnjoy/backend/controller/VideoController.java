package com.onnjoy.backend.controller;

import com.onnjoy.backend.dto.VideoUploadDTO;
import com.onnjoy.backend.entity.Video;
import com.onnjoy.backend.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/upload-url")
    public ResponseEntity<Map<String, String>> getUploadUrl(@Valid @RequestBody VideoUploadDTO uploadDTO) {
        Map<String, String> response = videoService.generateUploadUrl(uploadDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-videos")
    public ResponseEntity<List<Video>> getMyVideos(@RequestParam Long userId) {
        return ResponseEntity.ok(videoService.getVideosByUserId(userId));
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Video>> getPendingVideos() {
        return ResponseEntity.ok(videoService.getPendingVideos());
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Video> approveVideo(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.approveVideo(id));
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Video> rejectVideo(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String reason = body.get("reason");
        return ResponseEntity.ok(videoService.rejectVideo(id, reason));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.getVideoById(id));
    }
}