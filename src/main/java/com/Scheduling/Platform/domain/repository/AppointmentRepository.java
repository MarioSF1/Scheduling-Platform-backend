package com.Scheduling.Platform.domain.repository;

import com.Scheduling.Platform.domain.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    // Busca todos os agendamentos de um funcionário entre dois horários (ex: um dia inteiro)
    @Query("SELECT a FROM Appointment a WHERE a.employee.id = :employeeId " +
            "AND a.startTime >= :start AND a.endTime <= :end " +
            "AND a.status <> 'CANCELLED'")
    List<Appointment> findActiveAppointmentsByEmployeeAndDate(
            UUID employeeId, LocalDateTime start, LocalDateTime end);
}