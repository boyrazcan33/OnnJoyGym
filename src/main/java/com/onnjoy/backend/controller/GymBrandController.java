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
    public ResponseEntity<List<GymBrand>> getAllBrands() {
        return ResponseEntity.ok(gymBrandRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GymBrand> getBrandById(@PathVariable Long id) {
        return ResponseEntity.ok(gymBrandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found")));
    }
}