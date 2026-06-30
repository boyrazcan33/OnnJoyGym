package com.onnjoy.backend.service;

import com.onnjoy.backend.dto.UserCommentRequestDTO;
import com.onnjoy.backend.entity.GymBrand;
import com.onnjoy.backend.entity.User;
import com.onnjoy.backend.entity.UserComment;
import com.onnjoy.backend.repository.GymBrandRepository;
import com.onnjoy.backend.repository.UserCommentRepository;
import com.onnjoy.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommentService {

    private static final int MAX_COMMENTS_PER_USER_PER_BRAND = 2;

    private final UserCommentRepository userCommentRepository;
    private final UserRepository userRepository;
    private final GymBrandRepository gymBrandRepository;

    public void submitComment(String userEmail, UserCommentRequestDTO dto) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        GymBrand brand = gymBrandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("Gym brand not found"));

        int existingCount = userCommentRepository.countByUserIdAndGymBrandId(user.getId(), brand.getId());
        if (existingCount >= MAX_COMMENTS_PER_USER_PER_BRAND) {
            throw new IllegalStateException("You have already submitted the maximum number of comments for this gym.");
        }

        UserComment comment = new UserComment();
        comment.setUser(user);
        comment.setGymBrand(brand);
        comment.setRating(dto.getRating());
        comment.setComment(dto.getComment());
        userCommentRepository.save(comment);
    }
}
