package com.Scheduling.Platform.serviceOffered.application.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentRequest(
        UUID serviceId,
        UUID employeeId,
        LocalDateTime startTime
) {}