package com.onnjoy.backend.service;

import com.onnjoy.backend.entity.Club;
import com.onnjoy.backend.entity.ClubProgress;
import com.onnjoy.backend.entity.User;
import com.onnjoy.backend.repository.ClubProgressRepository;
import com.onnjoy.backend.repository.ClubRepository;
import com.onnjoy.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubProgressRepository clubProgressRepository;
    private final UserRepository userRepository;

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public Club getClubById(Long id) {
        return clubRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Club not found with id: " + id));
    }

    // NEW: Get club recommendation based on user's starting value
    public Map<String, Object> getClubRecommendation(Long clubId, BigDecimal userValue) {
        Map<String, Object> response = new HashMap<>();

        switch (clubId.intValue()) {
            case 1: // 100kg Bench Press Club
            case 2: // 100kg Bench Press Club (Advanced)
                if (userValue.compareTo(BigDecimal.valueOf(70)) < 0) {
                    response.put("recommendedClubId", 1L);
                    response.put("recommendedClubName", "100kg Bench Press Club (12-week program)");
                    response.put("message", "We recommend the 12-week program since your current max (" + userValue + "kg) is below 70kg");
                } else {
                    response.put("recommendedClubId", 2L);
                    response.put("recommendedClubName", "100kg Bench Press Club - Advanced (8-week program)");
                    response.put("message", "We recommend the 8-week program since your current max (" + userValue + "kg) is 70kg or above");
                }
                break;

            case 3: // 140kg Hip Thrust Club
            case 4: // 140kg Hip Thrust Club (Advanced)
                if (userValue.compareTo(BigDecimal.valueOf(80)) < 0) {
                    response.put("recommendedClubId", 3L);
                    response.put("recommendedClubName", "140kg Hip Thrust Club (12-week program)");
                    response.put("message", "We recommend the 12-week program since your current max (" + userValue + "kg) is below 80kg");
                } else {
                    response.put("recommendedClubId", 4L);
                    response.put("recommendedClubName", "140kg Hip Thrust Club - Advanced (8-week program)");
                    response.put("message", "We recommend the 8-week program since your current max (" + userValue + "kg) is 80kg or above");
                }
                break;

            case 5: // 50 Push-Ups Club
            case 6: // 50 Push-Ups Club (Advanced)
                if (userValue.compareTo(BigDecimal.valueOf(20)) < 0) {
                    response.put("recommendedClubId", 5L);
                    response.put("recommendedClubName", "50 Push-Ups Club (6-week program)");
                    response.put("message", "We recommend the 6-week program since your current max (" + userValue + " reps) is below 20 reps");
                } else {
                    response.put("recommendedClubId", 6L);
                    response.put("recommendedClubName", "50 Push-Ups Club - Advanced (4-week program)");
                    response.put("message", "We recommend the 4-week program since your current max (" + userValue + " reps) is 20 reps or above");
                }
                break;

            case 7: // 10 Pull-Ups Club
            case 8: // 10 Pull-Ups Club (Advanced)
                if (userValue.compareTo(BigDecimal.valueOf(3)) < 0) {
                    response.put("recommendedClubId", 7L);
                    response.put("recommendedClubName", "10 Pull-Ups Club (8-week program)");
                    response.put("message", "We recommend the 8-week program since your current max (" + userValue + " reps) is below 3 reps");
                } else {
                    response.put("recommendedClubId", 8L);
                    response.put("recommendedClubName", "10 Pull-Ups Club - Advanced (6-week program)");
                    response.put("message", "We recommend the 6-week program since your current max (" + userValue + " reps) is 3 reps or above");
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown club: " + clubId);
        }

        return response;
    }

    // UPDATED: Join club with starting_max
    public ClubProgress joinClubWithProgress(Long userId, Long clubId, BigDecimal startingMax) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Check if user is activated
        if (!user.getIsActivated()) {
            throw new IllegalArgumentException("You must complete buddy matching or upload a video before joining clubs");
        }

        if (clubProgressRepository.existsByUserIdAndClubId(userId, clubId)) {
            throw new IllegalArgumentException("Already joined this club");
        }

        Club club = getClubById(clubId);

        ClubProgress progress = new ClubProgress();
        progress.setUser(user);
        progress.setClub(club);
        progress.setStartingMax(startingMax); // NEW
        progress.setCurrentWeek(1);
        progress.setStartDate(LocalDate.now());
        progress.setLastUpdated(LocalDateTime.now());

        return clubProgressRepository.save(progress);
    }

    // UPDATED: Complete week (manual progression)
    public ClubProgress completeWeek(Long userId, Long clubId) {
        ClubProgress progress = clubProgressRepository.findByUserIdAndClubId(userId, clubId)
                .orElseThrow(() -> new IllegalArgumentException("Not a member of this club"));

        // Check if 1 week has passed since start_date
        long daysSinceStart = java.time.temporal.ChronoUnit.DAYS.between(
                progress.getStartDate(),
                LocalDate.now()
        );
        int weeksPassedSinceStart = (int) (daysSinceStart / 7);

        if (weeksPassedSinceStart < progress.getCurrentWeek()) {
            throw new IllegalArgumentException("You must wait at least 1 week before completing this week");
        }

        // Get total weeks for this club
        int totalWeeks = getTotalWeeksForClub(clubId);

        if (progress.getCurrentWeek() >= totalWeeks) {
            throw new IllegalArgumentException("You have already completed all weeks of this program");
        }

        // Increment current week
        progress.setCurrentWeek(progress.getCurrentWeek() + 1);
        progress.setLastUpdated(LocalDateTime.now());

        return clubProgressRepository.save(progress);
    }

    // NEW: Get total weeks for a club
    private int getTotalWeeksForClub(Long clubId) {
        switch (clubId.intValue()) {
            case 1: return 12; // 100kg Bench Press Club
            case 2: return 8;  // 100kg Bench Press Club (Advanced)
            case 3: return 12; // 140kg Hip Thrust Club
            case 4: return 8;  // 140kg Hip Thrust Club (Advanced)
            case 5: return 6;  // 50 Push-Ups Club
            case 6: return 4;  // 50 Push-Ups Club (Advanced)
            case 7: return 8;  // 10 Pull-Ups Club
            case 8: return 6;  // 10 Pull-Ups Club (Advanced)
            default: throw new IllegalArgumentException("Unknown club ID: " + clubId);
        }
    }

    public ClubProgress getCurrentProgress(Long userId, Long clubId) {
        return clubProgressRepository.findByUserIdAndClubId(userId, clubId)
                .orElseThrow(() -> new IllegalArgumentException("Not a member of this club"));
    }

    public List<ClubProgress> getUserClubProgress(Long userId) {
        return clubProgressRepository.findByUserId(userId);
    }

    public Club createClub(Club club) {
        return clubRepository.save(club);
    }

    public Club updateClub(Long id, Club clubDetails) {
        Club club = getClubById(id);
        club.setName(clubDetails.getName());
        club.setDescription(clubDetails.getDescription());
        club.setGoal(clubDetails.getGoal());
        club.setLevel(clubDetails.getLevel());
        return clubRepository.save(club);
    }

    public void deleteClub(Long id) {
        Club club = getClubById(id);
        clubRepository.delete(club);
    }
}