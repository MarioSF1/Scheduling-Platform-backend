package com.Scheduling.Platform.application.services;

import com.Scheduling.Platform.application.dtos.StoreRequest;
import com.Scheduling.Platform.application.dtos.StoreResponse;
import com.Scheduling.Platform.domain.exception.InvalidCredentialsException;
import com.Scheduling.Platform.domain.model.Store;
import com.Scheduling.Platform.domain.model.User;
import com.Scheduling.Platform.domain.repository.EmployeeRepository;
import com.Scheduling.Platform.domain.repository.StoreRepository;
import com.Scheduling.Platform.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public Store createStore(StoreRequest request, String ownerEmail) {
        // 1. Busca o usuário pelo email vindo do Token
        var owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // 2. Valida se o slug já existe
        if (storeRepository.findBySlug(request.slug()).isPresent()) {
            throw new RuntimeException("Este endereço (slug) já está em uso");
        }

        // 3. Constrói e salva a loja
        var store = Store.builder()
                .name(request.name())
                .description(request.description())
                .slug(request.slug().toLowerCase().replaceAll("\\s+", "-")) // Garante slug limpo
                .owner(owner)
                .build();

        return storeRepository.save(store);
    }

    @Transactional(readOnly = true)
    public List<StoreResponse> getMyStores(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Usuário não encontrado"));

        // 1. Busca as lojas onde ele é o dono
        List<Store> ownedStores = storeRepository.findByOwnerId(user.getId());

        // 2. Busca as lojas onde ele é funcionário
        List<Store> employeeStores = employeeRepository.findStoresByUserId(user.getId());

        // 3. Une as listas (usando um Set para evitar duplicatas se o dono for funcionário da própria loja)
        Set<Store> allStores = new HashSet<>(ownedStores);
        allStores.addAll(employeeStores);

        return allStores.stream()
                .map(store -> mapToResponse(store, user)) // Método para adicionar a "flag" de permissão
                .toList();
    }

    private StoreResponse mapToResponse(Store store, User user) {
        String role = "CUSTOMER";

        if (store.getOwner().getId().equals(user.getId())) {
            role = "OWNER";
        } else if (employeeRepository.existsByUserIdAndStoreId(user.getId(), store.getId())) {
            role = "EMPLOYEE";
        }

        return new StoreResponse(
                store.getId(),
                store.getName(),
                store.getSlug(),
                role
        );
    }
}