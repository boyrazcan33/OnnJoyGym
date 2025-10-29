package com.onnjoy.backend.repository;

import com.onnjoy.backend.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
}