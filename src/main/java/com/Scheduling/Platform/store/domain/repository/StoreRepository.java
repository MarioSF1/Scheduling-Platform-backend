package com.Scheduling.Platform.store.domain.repository;

import com.Scheduling.Platform.store.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {
    Optional<Store> findBySlug(String slug);

    List<Store> findByOwnerId(UUID ownerId);
}
