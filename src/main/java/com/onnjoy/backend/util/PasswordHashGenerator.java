package com.onnjoy.backend.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Admin password
        String adminPassword = "Onnjoyadmin2025?";
        String adminHashed = encoder.encode(adminPassword);
        System.out.println("=== ADMIN CREDENTIALS ===");
        System.out.println("Email: tallinntraining@gmail.com");
        System.out.println("Password: " + adminPassword);
        System.out.println("Hashed: " + adminHashed);
        System.out.println("\n=== USE THIS FOR SQL ===");
        System.out.println("UPDATE users SET password = '" + adminHashed + "' WHERE email = 'tallinntraining@gmail.com';");
    }
}