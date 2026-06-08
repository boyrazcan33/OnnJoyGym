package com.onnjoy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private Long userId;
    private String email;
    private String username;
    private String role;
    private Boolean isActivated;
    private Boolean emailVerified;
}
