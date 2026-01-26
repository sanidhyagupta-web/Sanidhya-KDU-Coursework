package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Utilities;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.HouseUser;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.Roles;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Exceptions.ForbiddenException;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Exceptions.ResourceNotFoundException;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository.HouseUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CheckRole {

    private final HouseUserRepository houseUserRepository;

    public CheckRole(HouseUserRepository houseUserRepository) {
        this.houseUserRepository = houseUserRepository;
    }

    Logger logger = LoggerFactory.getLogger(CheckRole.class);
    public HouseUser checkAdmin(Long userId,Long houseId){
        logger.info("checkAdmin: userId={}, houseId={}", userId, houseId);
        HouseUser houseUser = houseUserRepository.
                findByHouse_HouseIdAndUser_UserId(houseId,userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found for this house"));

        if(!houseUser.getRole().equals(Roles.ADMIN)){
            throw new ForbiddenException("Admin privileges required");
        }

        return houseUser;
    }
}