package com.Scheduling.Platform.serviceOffered.application.services;

import com.Scheduling.Platform.exception.BusinessException;
import com.Scheduling.Platform.serviceOffered.domain.repository.AppointmentRepository;
import com.Scheduling.Platform.serviceOffered.domain.repository.WorkScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final WorkScheduleRepository scheduleRepository;
    private final AppointmentRepository appointmentRepository;

    public List<LocalTime> getAvailableSlots(UUID employeeId, LocalDate date, Integer serviceDuration) {
        // 1. Identifica o dia da semana (1-7)
        int dayOfWeek = date.getDayOfWeek().getValue();

        // 2. Busca a configuração de trabalho para aquele dia
        var schedule = scheduleRepository.findByEmployeeIdAndDayOfWeek(employeeId, dayOfWeek)
                .orElseThrow(() -> new BusinessException("Funcionário não trabalha neste dia."));

        // 3. Busca agendamentos ocupados no dia (das 00:00 às 23:59)
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        var busyAppointments = appointmentRepository.findActiveAppointmentsByEmployeeAndDate(employeeId, startOfDay, endOfDay);

        // 4. Gera os slots possíveis
        List<LocalTime> availableSlots = new ArrayList<>();
        LocalTime currentSlot = schedule.getStartTime();

        // Enquanto o slot + duração do serviço não ultrapassar o fim do expediente
        while (!currentSlot.plusMinutes(serviceDuration).isAfter(schedule.getEndTime())) {

            final LocalTime slotFinal = currentSlot;

            // Verifica se o slot está no horário de almoço
            boolean isLunch = isTimeInInterval(slotFinal, serviceDuration, schedule.getLunchStart(), schedule.getLunchEnd());

            // Verifica se o slot bate com algum agendamento existente
            boolean isBusy = busyAppointments.stream().anyMatch(app ->
                    isTimeInInterval(slotFinal, serviceDuration, app.getStartTime().toLocalTime(), app.getEndTime().toLocalTime())
            );

            if (!isLunch && !isBusy) {
                availableSlots.add(slotFinal);
            }

            // Pula para o próximo slot (você pode definir um intervalo fixo de 15 ou 30 min, ou usar a duração do serviço)
            currentSlot = currentSlot.plusMinutes(30);
        }

        return availableSlots;
    }

    private boolean isTimeInInterval(LocalTime slotStart, int duration, LocalTime intervalStart, LocalTime intervalEnd) {
        if (intervalStart == null || intervalEnd == null) return false;
        LocalTime slotEnd = slotStart.plusMinutes(duration);

        // Lógica de intersecção de intervalos
        return slotStart.isBefore(intervalEnd) && slotEnd.isAfter(intervalStart);
    }
}
