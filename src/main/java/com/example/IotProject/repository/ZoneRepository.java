package com.example.IotProject.repository;

import com.example.IotProject.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    List<Zone> findByName(String name);

    // Get list of all zone using pageable
    List<Zone> findAll();
}
