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

    // ✅ Allowed video formats
    private static final List<String> ALLOWED_VIDEO_FORMATS = Arrays.asList(".mp4", ".mov", ".avi");
    private static final long MAX_FILE_SIZE_MB = 100; // 100 MB max

    public Map<String, String> generateUploadUrl(VideoUploadDTO uploadDTO) {
        // ✅ 1. Validate file extension
        String fileName = uploadDTO.getFileName().toLowerCase();
        if (!isValidVideoFormat(fileName)) {
            throw new IllegalArgumentException(
                    "Invalid file format. Only MP4, MOV, and AVI formats are supported. Your file: " + fileName
            );
        }

        // ✅ 2. Validate reps (must be exactly 3)
        if (uploadDTO.getReps() == null || uploadDTO.getReps() != 3) {
            throw new IllegalArgumentException("Video must contain exactly 3 reps");
        }

        // ✅ 3. Get user and validate gender/category
        User user = userRepository.findById(uploadDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getGender() == null) {
            throw new IllegalArgumentException("User gender not set. Please update profile first.");
        }

        // ✅ 4. Validate category based on gender
        validateCategoryForGender(uploadDTO.getCategory(), user.getGender());

        // ✅ 5. Generate unique S3 key with sanitized filename
        String sanitizedFileName = sanitizeFileName(uploadDTO.getFileName());
        String s3Key = "videos/" + UUID.randomUUID() + "/" + sanitizedFileName;

        // ✅ 6. Generate pre-signed URL using S3Service
        String uploadUrl = s3Service.generatePresignedUploadUrl(s3Key);
        String publicUrl = s3Service.getPublicUrl(s3Key);

        // ✅ 7. Get gym
        Gym gym = gymRepository.findById(uploadDTO.getGymId())
                .orElseThrow(() -> new IllegalArgumentException("Gym not found"));

        // ✅ 8. Create video record
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

        // ✅ 9. Activate user after video upload
        if (!user.getIsActivated()) {
            userService.activateUser(user.getId());
        }

        // ✅ 10. Return response
        Map<String, String> response = new HashMap<>();
        response.put("uploadUrl", uploadUrl);
        response.put("s3Key", s3Key);
        response.put("videoId", video.getId().toString());
        response.put("publicUrl", publicUrl);

        return response;
    }

    /**
     * ✅ Validate video file format
     */
    private boolean isValidVideoFormat(String fileName) {
        return ALLOWED_VIDEO_FORMATS.stream()
                .anyMatch(fileName::endsWith);
    }

    /**
     * ✅ Sanitize filename to prevent path traversal attacks
     */
    private String sanitizeFileName(String fileName) {
        // Remove path separators and keep only the filename
        String name = fileName.replaceAll("[/\\\\]", "");

        // Remove special characters except dots, hyphens, and underscores
        name = name.replaceAll("[^a-zA-Z0-9._-]", "_");

        // Ensure filename is not empty
        if (name.isEmpty()) {
            name = "video.mp4";
        }

        return name;
    }

    /**
     * ✅ Validate category based on user gender
     */
    private void validateCategoryForGender(String category, String gender) {
        if ("MALE".equalsIgnoreCase(gender)) {
            if (!MALE_CATEGORIES.contains(category)) {
                throw new IllegalArgumentException(
                        "Invalid category for male athletes. Allowed categories: " +
                                String.join(", ", MALE_CATEGORIES)
                );
            }
        } else if ("FEMALE".equalsIgnoreCase(gender)) {
            if (!FEMALE_CATEGORIES.contains(category)) {
                throw new IllegalArgumentException(
                        "Invalid category for female athletes. Allowed categories: " +
                                String.join(", ", FEMALE_CATEGORIES)
                );
            }
        } else {
            throw new IllegalArgumentException("Invalid gender. Must be MALE or FEMALE.");
        }
    }

    /**
     * Get all videos by user ID
     */
    public List<Video> getVideosByUserId(Long userId) {
        return videoRepository.findByUserId(userId);
    }

    /**
     * Get all pending videos (for admin moderation)
     */
    public List<Video> getPendingVideos() {
        return videoRepository.findByStatus(Video.VideoStatus.PENDING);
    }

    /**
     * Get video by ID
     */
    public Video getVideoById(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Video not found with id: " + id));
    }

    /**
     * Approve video
     */
    public Video approveVideo(Long id) {
        Video video = getVideoById(id);
        video.setStatus(Video.VideoStatus.APPROVED);
        video.setUpdatedAt(LocalDateTime.now());
        return videoRepository.save(video);
    }

    /**
     * Reject video with reason
     */
    public Video rejectVideo(Long id, String reason) {
        Video video = getVideoById(id);
        video.setStatus(Video.VideoStatus.REJECTED);
        video.setRejectionReason(reason);
        video.setUpdatedAt(LocalDateTime.now());
        return videoRepository.save(video);
    }
}