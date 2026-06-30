package com.onnjoy.backend.repository;

import com.onnjoy.backend.entity.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCommentRepository extends JpaRepository<UserComment, Long> {

    int countByUserIdAndGymBrandId(Long userId, Long brandId);
}
