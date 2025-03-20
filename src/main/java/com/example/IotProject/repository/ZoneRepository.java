package com.example.IotProject.repository;

import com.example.IotProject.model.ZoneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<ZoneModel, Long> {
    List<ZoneModel> findByName(String name);

    // Get list of all zone using pageable
    List<ZoneModel> findAll();
}
