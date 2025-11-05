package com.onnjoy.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    @NotBlank(message = "Telegram username is required")
    @Pattern(regexp = "^@[a-zA-Z0-9_]{5,32}$", message = "Invalid Telegram username format (must start with @ and be 5-32 characters)")
    private String telegramUsername;
}