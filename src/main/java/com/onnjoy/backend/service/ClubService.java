package com.onnjoy.backend.service;

import com.onnjoy.backend.entity.Club;
import com.onnjoy.backend.entity.ClubProgress;
import com.onnjoy.backend.entity.User;
import com.onnjoy.backend.repository.ClubProgressRepository;
import com.onnjoy.backend.repository.ClubRepository;
import com.onnjoy.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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

    public ClubProgress joinClubWithProgress(Long userId, Long clubId) {
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
        progress.setCurrentWeek(1);
        progress.setStartDate(LocalDate.now());
        progress.setLastUpdated(LocalDateTime.now());

        return clubProgressRepository.save(progress);
    }

    public ClubProgress getCurrentProgress(Long userId, Long clubId) {
        ClubProgress progress = clubProgressRepository.findByUserIdAndClubId(userId, clubId)
                .orElseThrow(() -> new IllegalArgumentException("Not a member of this club"));

        // Auto-update week based on time passed
        long daysSinceStart = ChronoUnit.DAYS.between(progress.getStartDate(), LocalDate.now());
        int calculatedWeek = (int) (daysSinceStart / 7) + 1;

        if (calculatedWeek != progress.getCurrentWeek()) {
            progress.setCurrentWeek(calculatedWeek);
            progress.setLastUpdated(LocalDateTime.now());
            clubProgressRepository.save(progress);
        }

        return progress;
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