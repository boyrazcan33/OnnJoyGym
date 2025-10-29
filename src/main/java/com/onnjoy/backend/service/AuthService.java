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

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponseDTO register(RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRole("USER");

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new AuthResponseDTO(token, user.getEmail(), user.getRole());
    }

    public AuthResponseDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new AuthResponseDTO(token, user.getEmail(), user.getRole());
    }
}