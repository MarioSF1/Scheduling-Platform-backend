package com.Scheduling.Platform.application.dtos;

import java.time.LocalTime;

public record WorkScheduleRequest(
        Integer dayOfWeek,
        LocalTime startTime,
        LocalTime endTime,
        LocalTime lunchStart,
        LocalTime lunchEnd
) {}