package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByName(String username);
}