package com.Scheduling.Platform.application.services;

import com.Scheduling.Platform.application.dtos.WorkScheduleRequest;
import com.Scheduling.Platform.domain.exception.BusinessException;
import com.Scheduling.Platform.domain.model.WorkSchedule;
import com.Scheduling.Platform.domain.repository.EmployeeRepository;
import com.Scheduling.Platform.domain.repository.WorkScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkScheduleService {

    private final WorkScheduleRepository repository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public void saveSchedule(UUID employeeId, WorkScheduleRequest dto) {
        if (dto.startTime().isAfter(dto.endTime())) {
            throw new BusinessException("Horário de início não pode ser após o fim.");
        }

        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));

        // Atualiza se já existir para aquele dia, ou cria novo
        var schedule = repository.findByEmployeeIdAndDayOfWeek(employeeId, dto.dayOfWeek())
                .orElse(new WorkSchedule());

        schedule.setEmployee(employee);
        schedule.setDayOfWeek(dto.dayOfWeek());
        schedule.setStartTime(dto.startTime());
        schedule.setEndTime(dto.endTime());
        schedule.setLunchStart(dto.lunchStart());
        schedule.setLunchEnd(dto.lunchEnd());

        repository.save(schedule);
    }

    @Transactional
    public void saveFullWeek(UUID employeeId, List<WorkScheduleRequest> schedules, String requesterEmail) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));

        if (!employee.getStore().getOwner().getEmail().equals(requesterEmail)) {
            throw new BusinessException("Acesso negado: Você não gerencia este funcionário.");
        }

        for (WorkScheduleRequest dto : schedules) {
            // Validação básica de consistência
            if (dto.startTime().isAfter(dto.endTime())) {
                throw new BusinessException("Erro no dia " + dto.dayOfWeek() + ": Início após o fim.");
            }

            // Busca se já existe um registro para esse dia da semana
            var schedule = repository.findByEmployeeIdAndDayOfWeek(employeeId, dto.dayOfWeek())
                    .orElse(new WorkSchedule());

            schedule.setEmployee(employee);
            schedule.setDayOfWeek(dto.dayOfWeek());
            schedule.setStartTime(dto.startTime());
            schedule.setEndTime(dto.endTime());
            schedule.setLunchStart(dto.lunchStart());
            schedule.setLunchEnd(dto.lunchEnd());

            repository.save(schedule);
        }
    }
}
