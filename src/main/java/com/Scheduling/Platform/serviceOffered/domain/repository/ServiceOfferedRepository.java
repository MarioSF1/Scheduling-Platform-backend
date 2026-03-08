package com.Scheduling.Platform.serviceOffered.domain.repository;

import com.Scheduling.Platform.serviceOffered.domain.model.ServiceOffered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServiceOfferedRepository extends JpaRepository<ServiceOffered, UUID> {
    List<ServiceOffered> findByStoreId(UUID storeId);
}
