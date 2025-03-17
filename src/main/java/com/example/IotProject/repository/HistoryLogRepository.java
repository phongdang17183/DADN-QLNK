package com.example.IotProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IotProject.model.HistoryLogModel;

@Repository
public interface HistoryLogRepository extends JpaRepository<HistoryLogModel, Long> {
}
