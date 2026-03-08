package com.Scheduling.Platform.store.api;

import com.Scheduling.Platform.store.application.dtos.StoreRequest;
import com.Scheduling.Platform.store.application.services.StoreService;
import com.Scheduling.Platform.store.domain.model.Store;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/stores")
@RequiredArgsConstructor
@Tag(name = "Loja", description = "Gerenciamento da Loja")
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    @Operation(summary = "Criar loja", description = "Cria a loja de acordo com o request passado")
    public ResponseEntity<Store> create(@RequestBody StoreRequest request, Authentication authentication) {
        // O Spring Security injeta o 'authentication' automaticamente se o Token for válido
        // String email = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.createStore(request));
    }
}
