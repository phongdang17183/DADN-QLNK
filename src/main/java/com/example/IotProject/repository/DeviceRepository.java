package com.example.IotProject.repository;

import com.example.IotProject.model.DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceModel, Integer> {

}
