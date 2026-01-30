package com.Scheduling.Platform.application.dtos;

public record StoreRequest(
        String name,
        String description,
        String slug
) {}
