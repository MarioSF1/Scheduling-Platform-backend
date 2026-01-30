package com.Scheduling.Platform.domain.repository;

import com.Scheduling.Platform.domain.model.Employee;
import com.Scheduling.Platform.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query("SELECT e.store FROM Employee e WHERE e.user.id = :userId")
    List<Store> findStoresByUserId(UUID userId);

    boolean existsByUserIdAndStoreId(UUID id, UUID id1);
}
