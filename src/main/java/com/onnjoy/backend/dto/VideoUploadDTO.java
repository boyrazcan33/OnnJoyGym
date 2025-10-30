package com.onnjoy.backend.dto;

import lombok.Data;

@Data
public class VideoUploadDTO {
    private Long userId;
    private Long gymId;
    private String category;
    private Double weight;
    private String fileName;
}