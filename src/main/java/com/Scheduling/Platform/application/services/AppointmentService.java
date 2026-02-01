package com.Scheduling.Platform.application.services;

import com.Scheduling.Platform.application.dtos.AppointmentRequest;
import com.Scheduling.Platform.domain.exception.BusinessException;
import com.Scheduling.Platform.domain.model.Appointment;
import com.Scheduling.Platform.domain.model.AppointmentStatus;
import com.Scheduling.Platform.domain.repository.AppointmentRepository;
import com.Scheduling.Platform.domain.repository.EmployeeRepository;
import com.Scheduling.Platform.domain.repository.ServiceOfferedRepository;
import com.Scheduling.Platform.domain.repository.UserRepository;
import com.Scheduling.Platform.domain.service.AvailabilityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AvailabilityService availabilityService;
    private final ServiceOfferedRepository serviceRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Appointment createAppointment(AppointmentRequest dto, String customerEmail) {
        // 1. Busca as entidades básicas
        var service = serviceRepository.findById(dto.serviceId())
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));

        var employee = employeeRepository.findById(dto.employeeId())
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));

        var customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        // 2. Cálculo do horário de término baseado na duração do serviço
        LocalDateTime start = dto.startTime();
        LocalDateTime end = start.plusMinutes(service.getDurationMinutes());

        // 3. O "PULO DO GATO": Validar disponibilidade em tempo real
        // Chamamos o serviço que acabamos de criar
        List<LocalTime> availableSlots = availabilityService.getAvailableSlots(
                employee.getId(),
                start.toLocalDate(),
                service.getDurationMinutes()
        );

        // Verifica se o horário solicitado ainda está na lista de disponíveis
        boolean stillAvailable = availableSlots.contains(start.toLocalTime());

        if (!stillAvailable) {
            throw new BusinessException("Este horário acabou de ser ocupado. Por favor, escolha outro.");
        }

        // 4. Cria o agendamento
        var appointment = Appointment.builder()
                .store(employee.getStore())
                .employee(employee)
                .customer(customer)
                .service(service)
                .startTime(start)
                .endTime(end)
                .status(AppointmentStatus.PENDING) // Começa como pendente
                .build();

        return appointmentRepository.save(appointment);
    }
}