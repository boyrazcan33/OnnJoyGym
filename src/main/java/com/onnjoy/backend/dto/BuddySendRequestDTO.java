package com.onnjoy.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BuddySendRequestDTO {

    @NotNull(message = "Sender ID is required")
    private Long senderId;

    @NotNull(message = "Receiver ID is required")
    private Long receiverId;
}