package com.Scheduling.Platform.auth.application.dtos;

public record RegisterRequest(
        String name,
        String email,
        String password
) {}
