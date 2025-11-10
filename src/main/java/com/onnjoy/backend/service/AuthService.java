package com.onnjoy.backend.service;

import com.onnjoy.backend.dto.AuthResponseDTO;
import com.onnjoy.backend.dto.LoginDTO;
import com.onnjoy.backend.dto.RegisterDTO;
import com.onnjoy.backend.entity.User;
import com.onnjoy.backend.repository.UserRepository;
import com.onnjoy.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public AuthResponseDTO register(RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setTelegramUsername(registerDTO.getTelegramUsername());
        user.setGender(registerDTO.getGender());
        user.setRole("USER");
        user.setIsActivated(false);

        // Email verification
        user.setEmailVerified(false);
        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);

        userRepository.save(user);

        // Send verification email (async, doesn't block)
        emailService.sendVerificationEmail(user.getEmail(), verificationToken);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        //  DEĞIŞIKLIK: userId eklendi
        return new AuthResponseDTO(token, user.getId(), user.getEmail(), user.getRole());
    }

    public AuthResponseDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        //  DEĞIŞIKLIK: userId eklendi
        return new AuthResponseDTO(token, user.getId(), user.getEmail(), user.getRole());
    }

    public String verifyEmail(String token) {
        User user = userRepository.findAll().stream()
                .filter(u -> token.equals(u.getVerificationToken()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid verification token"));

        user.setEmailVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);

        return "Email verified successfully! You can now use all features.";
    }
}