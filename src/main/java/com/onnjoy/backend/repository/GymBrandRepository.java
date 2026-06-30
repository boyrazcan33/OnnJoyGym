package com.onnjoy.backend.repository;

import com.onnjoy.backend.entity.GymBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GymBrandRepository extends JpaRepository<GymBrand, Long> {

    List<GymBrand> findByCountryOrderByNameAsc(String country);

    @Query("SELECT DISTINCT g.country FROM GymBrand g WHERE g.country IS NOT NULL ORDER BY g.country ASC")
    List<String> findDistinctCountries();
}
