package com.onnjoy.backend.dto;

import lombok.Data;

@Data
public class ProfileUpdateDTO {
    private String bio;
    private String goals;
    private String experience;
    private Long gymPreference;
}