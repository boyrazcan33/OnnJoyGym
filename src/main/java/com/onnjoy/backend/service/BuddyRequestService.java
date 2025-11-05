package com.onnjoy.backend.service;

import com.onnjoy.backend.dto.BuddyRequestResponseDTO;
import com.onnjoy.backend.dto.BuddySendRequestDTO;
import com.onnjoy.backend.entity.BuddyRequest;
import com.onnjoy.backend.entity.User;
import com.onnjoy.backend.repository.BuddyRequestRepository;
import com.onnjoy.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuddyRequestService {

    private final BuddyRequestRepository buddyRequestRepository;
    private final UserRepository userRepository;

    public BuddyRequestResponseDTO sendRequest(BuddySendRequestDTO dto) {
        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        // Check if request already exists
        if (buddyRequestRepository.existsBySenderIdAndReceiverId(dto.getSenderId(), dto.getReceiverId())) {
            throw new IllegalArgumentException("Buddy request already sent");
        }

        BuddyRequest request = new BuddyRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(BuddyRequest.RequestStatus.PENDING);
        request.setCreatedAt(LocalDateTime.now());

        buddyRequestRepository.save(request);

        return mapToDTO(request, false);
    }

    public BuddyRequestResponseDTO acceptRequest(Long requestId) {
        BuddyRequest request = buddyRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        request.setStatus(BuddyRequest.RequestStatus.ACCEPTED);
        request.setUpdatedAt(LocalDateTime.now());
        buddyRequestRepository.save(request);

        // After acceptance, show telegram username
        return mapToDTO(request, true);
    }

    public BuddyRequestResponseDTO rejectRequest(Long requestId) {
        BuddyRequest request = buddyRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        request.setStatus(BuddyRequest.RequestStatus.REJECTED);
        request.setUpdatedAt(LocalDateTime.now());
        buddyRequestRepository.save(request);

        return mapToDTO(request, false);
    }

    public List<BuddyRequestResponseDTO> getReceivedRequests(Long userId) {
        List<BuddyRequest> requests = buddyRequestRepository.findByReceiverIdAndStatus(
                userId, BuddyRequest.RequestStatus.PENDING);

        return requests.stream()
                .map(req -> mapToDTO(req, false))
                .collect(Collectors.toList());
    }

    public List<BuddyRequestResponseDTO> getSentRequests(Long userId) {
        List<BuddyRequest> requests = buddyRequestRepository.findBySenderId(userId);

        return requests.stream()
                .map(req -> mapToDTO(req, req.getStatus() == BuddyRequest.RequestStatus.ACCEPTED))
                .collect(Collectors.toList());
    }

    public List<BuddyRequestResponseDTO> getAcceptedConnections(Long userId) {
        List<BuddyRequest> sentAccepted = buddyRequestRepository.findBySenderId(userId).stream()
                .filter(req -> req.getStatus() == BuddyRequest.RequestStatus.ACCEPTED)
                .collect(Collectors.toList());

        List<BuddyRequest> receivedAccepted = buddyRequestRepository.findByReceiverId(userId).stream()
                .filter(req -> req.getStatus() == BuddyRequest.RequestStatus.ACCEPTED)
                .collect(Collectors.toList());

        List<BuddyRequestResponseDTO> connections = sentAccepted.stream()
                .map(req -> mapToDTO(req, true))
                .collect(Collectors.toList());

        connections.addAll(receivedAccepted.stream()
                .map(req -> mapToDTO(req, true))
                .collect(Collectors.toList()));

        return connections;
    }

    private BuddyRequestResponseDTO mapToDTO(BuddyRequest request, boolean showTelegram) {
        BuddyRequestResponseDTO dto = new BuddyRequestResponseDTO();
        dto.setRequestId(request.getId());
        dto.setSenderId(request.getSender().getId());
        dto.setSenderEmail(request.getSender().getEmail());
        dto.setReceiverId(request.getReceiver().getId());
        dto.setStatus(request.getStatus().name());
        dto.setCreatedAt(request.getCreatedAt());

        // Only show telegram username if request is accepted
        if (showTelegram && request.getStatus() == BuddyRequest.RequestStatus.ACCEPTED) {
            dto.setTelegramUsername(request.getReceiver().getTelegramUsername());
        }

        return dto;
    }
}