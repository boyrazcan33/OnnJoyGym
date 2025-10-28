package com.onnjoy.backend.repository;

import com.onnjoy.backend.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Gym, Long> {
}