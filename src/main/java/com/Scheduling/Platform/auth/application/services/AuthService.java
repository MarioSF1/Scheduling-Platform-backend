package com.Scheduling.Platform.auth.application.services;

import com.Scheduling.Platform.auth.application.dtos.LoginRequest;
import com.Scheduling.Platform.auth.application.dtos.RegisterRequest;
import com.Scheduling.Platform.exception.InvalidCredentialsException;
import com.Scheduling.Platform.auth.domain.model.User;
import com.Scheduling.Platform.auth.domain.repository.UserRepository;
import com.Scheduling.Platform.auth.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        var user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .owner(false)
                .build();

        userRepository.save(user);

        return jwtService.generateToken(user.getEmail());
    }

    public String login(LoginRequest request) {

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("E-mail ou senha inválidos"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException("E-mail ou senha inválidos");
        }

        return jwtService.generateToken(user.getEmail());
    }
}
