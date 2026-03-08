package com.Scheduling.Platform.serviceOffered.application.dtos;

import java.math.BigDecimal;

public record ServiceRequest(
        String name,
        BigDecimal price,
        Integer durationMinutes
) {}
