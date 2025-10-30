package com.onnjoy.backend.service;

import com.onnjoy.backend.entity.Gym;
import com.onnjoy.backend.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;

    public List<Gym> getAllGyms() {
        return gymRepository.findAll();
    }

    public Gym getGymById(Long id) {
        return gymRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gym not found with id: " + id));
    }

    public Gym createGym(Gym gym) {
        return gymRepository.save(gym);
    }

    public Gym updateGym(Long id, Gym gymDetails) {
        Gym gym = getGymById(id);
        gym.setName(gymDetails.getName());
        gym.setAddress(gymDetails.getAddress());
        gym.setDescription(gymDetails.getDescription());
        return gymRepository.save(gym);
    }

    public void deleteGym(Long id) {
        Gym gym = getGymById(id);
        gymRepository.delete(gym);
    }
}