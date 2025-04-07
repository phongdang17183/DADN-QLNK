package com.example.IotProject.repository;

import com.example.IotProject.model.RuleModel;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RuleRepository extends JpaRepository<RuleModel, Long> {
    RuleModel findAllById(Long id);

    @Query("SELECT r FROM RuleModel r WHERE r.device.id = :deviceId")
//    r.device.id = :deviceId: Ở đây, device.id chính là thuộc tính bên DeviceModel được đánh dấu @Id, kiểu String.
    List<RuleModel> findRulesByDeviceId(@Param("deviceId") String deviceId);
}
