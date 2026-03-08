package com.Scheduling.Platform.serviceOffered.domain.repository;

import com.Scheduling.Platform.serviceOffered.domain.model.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, UUID> {
    List<WorkSchedule> findByEmployeeIdOrderByDayOfWeekAsc(UUID employeeId);

    // Útil para validar sobreposição de horários
    Optional<WorkSchedule> findByEmployeeIdAndDayOfWeek(UUID employeeId, Integer dayOfWeek);
}