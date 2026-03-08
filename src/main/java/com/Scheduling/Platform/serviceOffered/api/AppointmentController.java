package com.Scheduling.Platform.serviceOffered.api;

import com.Scheduling.Platform.serviceOffered.application.dtos.AppointmentRequest;
import com.Scheduling.Platform.serviceOffered.application.services.AppointmentService;
import com.Scheduling.Platform.serviceOffered.domain.model.Appointment;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "Endpoints para usuários finais realizarem agendamentos")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> create(
            @RequestBody AppointmentRequest request,
            Authentication authentication) {

        // O email vem direto do JWT validado pelo nosso filtro
        String customerEmail = authentication.getName();

        Appointment appointment = appointmentService.createAppointment(request, customerEmail);

        return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
    }
}
