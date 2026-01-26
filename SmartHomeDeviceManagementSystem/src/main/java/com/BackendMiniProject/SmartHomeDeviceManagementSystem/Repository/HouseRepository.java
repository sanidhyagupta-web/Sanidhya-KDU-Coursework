package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House,Long> {
}
