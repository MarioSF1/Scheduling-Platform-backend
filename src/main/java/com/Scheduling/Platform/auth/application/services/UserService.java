package com.Scheduling.Platform.auth.application.services;

import com.Scheduling.Platform.auth.domain.model.User;
import com.Scheduling.Platform.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> userByEmail (String email) {
        return userRepository.findByEmail(email);
    }
}
