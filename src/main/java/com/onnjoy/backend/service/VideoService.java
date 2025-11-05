package com.onnjoy.backend.service;

import com.onnjoy.backend.dto.VideoUploadDTO;
import com.onnjoy.backend.entity.Video;
import com.onnjoy.backend.entity.Gym;
import com.onnjoy.backend.entity.User;
import com.onnjoy.backend.repository.VideoRepository;
import com.onnjoy.backend.repository.GymRepository;
import com.onnjoy.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final GymRepository gymRepository;
    private final S3Service s3Service;
    private final UserService userService;

    // Gender-based categories
    private static final List<String> MALE_CATEGORIES = Arrays.asList(
            "BENCH_PRESS", "SQUAT", "DEADLIFT", "HAMMER_CURL", "WIDE_GRIP_LAT_PULLDOWN"
    );

    private static final List<String> FEMALE_CATEGORIES = Arrays.asList(
            "BENCH_PRESS", "SQUAT", "DEADLIFT", "HIP_THRUST", "BARBELL_LUNGE"
    );

    public Map<String, String> generateUploadUrl(VideoUploadDTO uploadDTO) {
        // Validate file extension
        String fileName = uploadDTO.getFileName().toLowerCase();
        if (!fileName.endsWith(".mp4")) {
            throw new IllegalArgumentException("Only MP4 format is supported");
        }

        // Validate reps (must be exactly 3)
        if (uploadDTO.getReps() == null || uploadDTO.getReps() != 3) {
            throw new IllegalArgumentException("Video must contain exactly 3 reps");
        }

        // Get user and validate gender/category
        User user = userRepository.findById(uploadDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getGender() == null) {
            throw new IllegalArgumentException("User gender not set. Please update profile first.");
        }

        // Validate category based on gender
        validateCategoryForGender(uploadDTO.getCategory(), user.getGender());

        // Generate unique S3 key
        String s3Key = "videos/" + UUID.randomUUID() + "/" + uploadDTO.getFileName();

        // Generate REAL pre-signed URL using S3Service
        String uploadUrl = s3Service.generatePresignedUploadUrl(s3Key);
        String publicUrl = s3Service.getPublicUrl(s3Key);

        Gym gym = gymRepository.findById(uploadDTO.getGymId())
                .orElseThrow(() -> new IllegalArgumentException("Gym not found"));

        Video video = new Video();
        video.setUser(user);
        video.setGym(gym);
        video.setCategory(uploadDTO.getCategory());
        video.setWeight(uploadDTO.getWeight());
        video.setReps(uploadDTO.getReps());
        video.setUserGender(user.getGender());
        video.setS3Key(s3Key);
        video.setS3Url(publicUrl);
        video.setStatus(Video.VideoStatus.PENDING);
        video.setCreatedAt(LocalDateTime.now());
        video.setUpdatedAt(LocalDateTime.now());

        videoRepository.save(video);

        // Activate user after video upload
        if (!user.getIsActivated()) {
            userService.activateUser(user.getId());
        }

        Map<String, String> response = new HashMap<>();
        response.put("uploadUrl", uploadUrl);
        response.put("s3Key", s3Key);
        response.put("videoId", video.getId().toString());
        response.put("publicUrl", publicUrl);

        return response;
    }

    private void validateCategoryForGender(String category, String gender) {
        if ("MALE".equalsIgnoreCase(gender)) {
            if (!MALE_CATEGORIES.contains(category)) {
                throw new IllegalArgumentException("Invalid category for male. Allowed: " + MALE_CATEGORIES);
            }
        } else if ("FEMALE".equalsIgnoreCase(gender)) {
            if (!FEMALE_CATEGORIES.contains(category)) {
                throw new IllegalArgumentException("Invalid category for female. Allowed: " + FEMALE_CATEGORIES);
            }
        } else {
            throw new IllegalArgumentException("Invalid gender. Must be MALE or FEMALE.");
        }
    }

    public List<Video> getVideosByUserId(Long userId) {
        return videoRepository.findByUserId(userId);
    }

    public List<Video> getPendingVideos() {
        return videoRepository.findByStatus(Video.VideoStatus.PENDING);
    }

    public Video getVideoById(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Video not found with id: " + id));
    }

    public Video approveVideo(Long id) {
        Video video = getVideoById(id);
        video.setStatus(Video.VideoStatus.APPROVED);
        video.setUpdatedAt(LocalDateTime.now());
        return videoRepository.save(video);
    }

    public Video rejectVideo(Long id, String reason) {
        Video video = getVideoById(id);
        video.setStatus(Video.VideoStatus.REJECTED);
        video.setRejectionReason(reason);
        video.setUpdatedAt(LocalDateTime.now());
        return videoRepository.save(video);
    }
}