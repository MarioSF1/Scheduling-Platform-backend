package com.Scheduling.Platform.serviceOffered.application.services;

import com.Scheduling.Platform.serviceOffered.application.dtos.ServiceRequest;
import com.Scheduling.Platform.serviceOffered.domain.model.ServiceOffered;
import com.Scheduling.Platform.serviceOffered.domain.repository.ServiceOfferedRepository;
import com.Scheduling.Platform.store.domain.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceOfferedService {

    private final ServiceOfferedRepository repository;
    private final StoreRepository storeRepository;

    @Transactional
    public ServiceOffered create(UUID storeId, ServiceRequest dto, String ownerEmail) {
        var store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Loja não encontrada"));

        // Regra de Negócio: Apenas o dono pode adicionar serviços
        if (!store.getOwner().getEmail().equals(ownerEmail)) {
            throw new RuntimeException("Acesso negado: Você não é o dono desta loja");
        }

        var service = ServiceOffered.builder()
                .name(dto.name())
                .price(dto.price())
                .durationMinutes(dto.durationMinutes())
                .store(store)
                .build();

        return repository.save(service);
    }
}
