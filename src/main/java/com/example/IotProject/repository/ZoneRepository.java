package com.example.IotProject.repository;

import com.example.IotProject.model.UserModel;
import com.example.IotProject.model.ZoneModel;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<ZoneModel, Long> {
    ZoneModel findByName(String name);

    // Get list of all zone using pageable
    @Query(value = "SELECT * FROM zones", nativeQuery = true)
    List<ZoneModel> findAll();
}
