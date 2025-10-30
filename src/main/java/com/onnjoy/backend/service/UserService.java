package com.onnjoy.backend.service;

import com.onnjoy.backend.dto.ProfileUpdateDTO;
import com.onnjoy.backend.entity.Club;
import com.onnjoy.backend.entity.User;
import com.onnjoy.backend.entity.UserClub;
import com.onnjoy.backend.repository.ClubRepository;
import com.onnjoy.backend.repository.UserClubRepository;
import com.onnjoy.backend.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserClubRepository userClubRepository;
    private final ClubRepository clubRepository;
    private final EntityManager entityManager;

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    public User updateProfile(Long id, ProfileUpdateDTO profileDTO) {
        User user = getUserById(id);
        user.setBio(profileDTO.getBio());
        user.setGoals(profileDTO.getGoals());
        user.setExperience(profileDTO.getExperience());
        user.setGymPreference(profileDTO.getGymPreference());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public UserClub joinClub(Long userId, Long clubId) {
        if (userClubRepository.existsByUserIdAndClubId(userId, clubId)) {
            throw new IllegalArgumentException("User already joined this club");
        }

        User user = getUserById(userId);
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("Club not found with id: " + clubId));

        UserClub userClub = new UserClub();
        userClub.setUser(user);
        userClub.setClub(club);
        userClub.setJoinedAt(LocalDateTime.now());

        return userClubRepository.save(userClub);
    }

    public void leaveClub(Long userId, Long clubId) {
        UserClub userClub = userClubRepository.findByUserIdAndClubId(userId, clubId)
                .orElseThrow(() -> new IllegalArgumentException("User is not a member of this club"));
        userClubRepository.delete(userClub);
    }

    public List<UserClub> getUserClubs(Long userId) {
        return userClubRepository.findByUserId(userId);
    }

    public List<User> searchUsers(Long gymId, String goal, String experience) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        if (gymId != null) {
            predicates.add(cb.equal(user.get("gymPreference"), gymId));
        }

        if (goal != null && !goal.isEmpty()) {
            predicates.add(cb.like(cb.lower(user.get("goals")), "%" + goal.toLowerCase() + "%"));
        }

        if (experience != null && !experience.isEmpty()) {
            predicates.add(cb.equal(cb.lower(user.get("experience")), experience.toLowerCase()));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}