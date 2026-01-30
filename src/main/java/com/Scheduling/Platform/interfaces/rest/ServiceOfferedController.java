package com.Scheduling.Platform.interfaces.rest;

import com.Scheduling.Platform.application.dtos.ServiceRequest;
import com.Scheduling.Platform.application.services.ServiceOfferedService;
import com.Scheduling.Platform.domain.model.ServiceOffered;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/stores/{storeId}/services")
@RequiredArgsConstructor
@Tag(name = "Serviços", description = "Gerenciamento de serviços da loja")
public class ServiceOfferedController {

    private final ServiceOfferedService service;

    @PostMapping
    public ResponseEntity<ServiceOffered> create(
            @PathVariable UUID storeId,
            @RequestBody ServiceRequest request,
            Authentication authentication) {

        String email = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(storeId, request, email));
    }
}
