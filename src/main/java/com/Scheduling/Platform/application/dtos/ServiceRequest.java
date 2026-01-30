package com.Scheduling.Platform.application.dtos;

import java.math.BigDecimal;

public record ServiceRequest(
        String name,
        BigDecimal price,
        Integer durationMinutes
) {}
