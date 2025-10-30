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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final GymRepository gymRepository;

    public Map<String, String> generateUploadUrl(VideoUploadDTO uploadDTO) {
        // Generate unique S3 key
        String s3Key = "videos/" + UUID.randomUUID() + "/" + uploadDTO.getFileName();

        // In production, this would generate an actual S3 pre-signed URL
        // For now, we'll create a placeholder
        String uploadUrl = "https://onjoygym-videos.s3.eu-north-1.amazonaws.com/" + s3Key;

        // Create video record in database
        User user = userRepository.findById(uploadDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Gym gym = gymRepository.findById(uploadDTO.getGymId())
                .orElseThrow(() -> new IllegalArgumentException("Gym not found"));

        Video video = new Video();
        video.setUser(user);
        video.setGym(gym);
        video.setCategory(uploadDTO.getCategory());
        video.setWeight(uploadDTO.getWeight());
        video.setS3Key(s3Key);
        video.setS3Url(uploadUrl);
        video.setStatus(Video.VideoStatus.PENDING);
        video.setCreatedAt(LocalDateTime.now());
        video.setUpdatedAt(LocalDateTime.now());

        videoRepository.save(video);

        Map<String, String> response = new HashMap<>();
        response.put("uploadUrl", uploadUrl);
        response.put("s3Key", s3Key);
        response.put("videoId", video.getId().toString());

        return response;
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