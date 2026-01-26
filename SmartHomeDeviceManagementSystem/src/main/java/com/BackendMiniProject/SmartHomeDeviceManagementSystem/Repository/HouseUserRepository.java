package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.HouseUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseUserRepository extends JpaRepository<HouseUser, Long> {
    Optional<HouseUser> findByHouse_HouseIdAndUser_UserId(Long houseId,Long userId);

    Page<HouseUser> findByUser_UserId(Long userId, Pageable pageable);
}