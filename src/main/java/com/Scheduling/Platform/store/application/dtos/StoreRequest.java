package com.Scheduling.Platform.store.application.dtos;

public record StoreRequest(
        String name,
        String description,
        String slug
) {}
