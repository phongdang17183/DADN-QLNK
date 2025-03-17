package com.example.IotProject.repository;

import com.example.IotProject.model.ZoneModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZoneRepository extends JpaRepository<ZoneModel, Long> {
    List<ZoneModel> findByName(String name);

    // Get list of all zone using pageable
    List<ZoneModel> findAll();
}
