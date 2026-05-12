package com.fieldops.service;

import com.fieldops.dto.AuthRequest;
import com.fieldops.dto.AuthResponse;
import com.fieldops.dto.RegisterRequest;
import com.fieldops.model.AppUser;
import com.fieldops.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (appUserRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya esta registrado");
        }

        AppUser user = AppUser.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        AppUser savedUser = appUserRepository.save(user);
        String token = jwtService.generateToken(savedUser.getEmail(), savedUser.getRole());

        return AuthResponse.builder()
                .token(token)
                .email(savedUser.getEmail())
                .nombre(savedUser.getNombre())
                .role(savedUser.getRole().name())
                .build();
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User userDetails = (User) authentication.getPrincipal();
        AppUser user = appUserRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        String token = jwtService.generateToken(user.getEmail(), user.getRole());

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .nombre(user.getNombre())
                .role(user.getRole().name())
                .build();
    }
}
