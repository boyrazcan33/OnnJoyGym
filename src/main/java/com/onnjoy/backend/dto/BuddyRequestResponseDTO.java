package com.onnjoy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuddyRequestResponseDTO {

    private Long requestId;
    private Long senderId;
    private String senderEmail;
    private Long receiverId;
    private String status; // PENDING, ACCEPTED, REJECTED
    private LocalDateTime createdAt;

    // Only visible after acceptance
    private String telegramUsername;
}