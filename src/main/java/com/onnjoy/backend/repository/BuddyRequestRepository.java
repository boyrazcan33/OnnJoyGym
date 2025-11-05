package com.onnjoy.backend.repository;

import com.onnjoy.backend.entity.BuddyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BuddyRequestRepository extends JpaRepository<BuddyRequest, Long> {
    List<BuddyRequest> findBySenderId(Long senderId);
    List<BuddyRequest> findByReceiverId(Long receiverId);
    List<BuddyRequest> findByReceiverIdAndStatus(Long receiverId, BuddyRequest.RequestStatus status);
    Optional<BuddyRequest> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);
}