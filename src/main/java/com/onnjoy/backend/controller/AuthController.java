package com.onnjoy.backend.controller;

import com.onnjoy.backend.dto.AuthResponseDTO;
import com.onnjoy.backend.dto.LoginDTO;
import com.onnjoy.backend.dto.RegisterDTO;
import com.onnjoy.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterDTO registerDTO) {
        AuthResponseDTO response = authService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        AuthResponseDTO response = authService.login(loginDTO);
        return ResponseEntity.ok(response);
    }
}