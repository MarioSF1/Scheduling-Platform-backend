package com.Scheduling.Platform.application.dtos;

import java.util.UUID;

public record StoreResponse(
        UUID id,
        String name,
        String slug,
        String role // "OWNER", "EMPLOYEE" ou "CUSTOMER"
) {}
