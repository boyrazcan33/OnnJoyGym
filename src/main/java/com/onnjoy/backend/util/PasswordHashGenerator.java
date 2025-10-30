package com.onnjoy.backend.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "admin123";
        String hashed = encoder.encode(password);
        System.out.println("Password: " + password);
        System.out.println("Hashed: " + hashed);
    }
}