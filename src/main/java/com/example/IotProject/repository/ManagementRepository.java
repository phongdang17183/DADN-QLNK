package com.example.IotProject.repository;

import com.example.IotProject.model.ManagementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagementRepository extends JpaRepository<ManagementModel, Long> {
}
