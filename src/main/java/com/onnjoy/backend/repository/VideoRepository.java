package com.onnjoy.backend.repository;

import com.onnjoy.backend.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByUserId(Long userId);
    List<Video> findByStatus(Video.VideoStatus status);
}