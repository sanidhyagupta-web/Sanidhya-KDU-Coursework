package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Long> {
    Optional<Device> findByKickstonIdAndDeviceUsernameAndDevicePassword(
            String kickstonId,
            String deviceUsername,
            String devicePassword
    );
}