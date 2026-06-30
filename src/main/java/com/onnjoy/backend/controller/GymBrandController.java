package com.onnjoy.backend.controller;

import com.onnjoy.backend.entity.GymBrand;
import com.onnjoy.backend.repository.GymBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gym-brands")
@RequiredArgsConstructor
public class GymBrandController {

    private final GymBrandRepository gymBrandRepository;

    @GetMapping
    public ResponseEntity<List<GymBrand>> getBrands(@RequestParam(required = false) String country) {
        if (country != null && !country.isBlank()) {
            return ResponseEntity.ok(gymBrandRepository.findByCountryOrderByNameAsc(country));
        }
        return ResponseEntity.ok(gymBrandRepository.findAll());
    }

    @GetMapping("/countries")
    public ResponseEntity<List<String>> getCountries() {
        return ResponseEntity.ok(gymBrandRepository.findDistinctCountries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GymBrand> getBrandById(@PathVariable Long id) {
        return ResponseEntity.ok(gymBrandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found")));
    }
}
