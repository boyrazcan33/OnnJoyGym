package com.onnjoy.backend.service;

import com.onnjoy.backend.entity.Club;
import com.onnjoy.backend.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public Club getClubById(Long id) {
        return clubRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Club not found with id: " + id));
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