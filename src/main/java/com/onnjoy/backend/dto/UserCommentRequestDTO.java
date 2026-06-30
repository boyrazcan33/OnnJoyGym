package com.onnjoy.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCommentRequestDTO {

    @NotNull
    private Long brandId;

    @NotNull
    @Min(1) @Max(5)
    private Integer rating;

    @NotBlank
    @Size(max = 300)
    private String comment;
}
