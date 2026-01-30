package com.Scheduling.Platform.application.dtos;

public record RegisterRequest(
        String name,
        String email,
        String password
) {}
