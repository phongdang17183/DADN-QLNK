package com.example.IotProject.repository;

import com.example.IotProject.model.ManagementModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagementRepository extends JpaRepository<ManagementModel, Long> {
}
