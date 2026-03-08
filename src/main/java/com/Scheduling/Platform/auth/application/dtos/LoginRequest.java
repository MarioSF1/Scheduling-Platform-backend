package com.Scheduling.Platform.auth.application.dtos;

public record LoginRequest(
        String email,
        String password
) {}
