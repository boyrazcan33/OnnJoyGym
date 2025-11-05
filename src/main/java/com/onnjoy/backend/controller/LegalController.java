package com.onnjoy.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/legal")
public class LegalController {

    @GetMapping("/privacy")
    public ResponseEntity<Map<String, String>> getPrivacyPolicy() {
        Map<String, String> response = new HashMap<>();
        response.put("title", "Privacy Policy");
        response.put("content", "Your privacy is important to us. This policy outlines how we collect, use, and protect your personal information...");
        response.put("lastUpdated", "2025-01-01");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/terms")
    public ResponseEntity<Map<String, String>> getTermsOfService() {
        Map<String, String> response = new HashMap<>();
        response.put("title", "Terms of Service");
        response.put("content", "By using OnnJoyGym, you agree to the following terms and conditions...");
        response.put("lastUpdated", "2025-01-01");
        return ResponseEntity.ok(response);
    }
}