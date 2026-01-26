package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Service;


import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.House;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.HouseUser;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.Roles;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Exceptions.ResourceNotFoundException;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.*;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository.HouseUserRepository;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.User;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository.HouseRepository;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository.UserRepository;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Utilities.CheckRole;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Utilities.Conversion;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserHouseService {

    private final UserRepository userRepository;

    private final HouseRepository houseRepository;

    private final HouseUserRepository houseUserRepository;

    private final CheckRole checkRole;

    private final Conversion conversion;

    Logger logger = LoggerFactory.getLogger(UserHouseService.class);

    Set<String> idempotency = new HashSet<>();

    public UserHouseService(UserRepository userRepository, HouseRepository houseRepository,
                            HouseUserRepository houseUserRepository, CheckRole checkRole, Conversion conversion) {
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
        this.houseUserRepository = houseUserRepository;
        this.checkRole = checkRole;
        this.conversion = conversion;
    }

    @Transactional
    public House createHouse(Long userId,HouseDTO houseDTO){
        logger.info("Creating the house");

        String key = userId + houseDTO.getAddress() + houseDTO.getPincode() ;

        if(idempotency.contains(key)){
            return null;
        }else {
            idempotency.add(key);
        }

        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        user.setModifiedDate(LocalDate.now());
        userRepository.save(user);

        House house = conversion.convertHouseDTO(houseDTO);
        house.setCreatedDate(LocalDate.now());
        houseRepository.save(house);

        HouseUser houseUser = HouseUser.builder().
                house(house).
                user(user).
                role(Roles.ADMIN).
                build();

        houseUserRepository.save(houseUser);

        return house;
    }

    /**
     *
     * @param addUserId
     * @param authRequest
     * @return
     */
    @Transactional
    public UserDTO addUsersToHouse(Long addUserId,AuthRequest authRequest){
        logger.info("Adding user to the house");

        String key = String.valueOf(addUserId + authRequest.getUserId() + authRequest.getHouseId());

        if(idempotency.contains(key)){
            return null;
        }else {
            idempotency.add(key);
        }

        Long userId = authRequest.getUserId();
        Long houseId = authRequest.getHouseId();

        checkRole.checkAdmin(userId,houseId);

        User user = userRepository.findById(addUserId).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        user.setModifiedDate(LocalDate.now());
        userRepository.save(user);

        House house = houseRepository.findById(houseId).orElseThrow(()-> new ResourceNotFoundException("House not found"));
        house.setModifiedDate(LocalDate.now());
        houseRepository.save(house);

        HouseUser houseUser1 = HouseUser.builder().
                house(house).
                user(user).
                role(Roles.USER).
                build();
        houseUserRepository.save(houseUser1);

        return conversion.convertUser(user);
    }

    @Transactional
    public Page<House> getAllHousesUserBelong( Long userId, Pageable pageable) {
        logger.info("Get all houses belonging to a particular user");

        return  houseUserRepository
                .findByUser_UserId(userId, pageable)
                .map(HouseUser::getHouse);
    }


    @Transactional
    public HouseDTO updateHouseAddress(Long userId, Long houseId, UpdateHouseAddressRequest updateHouseAddressRequest){
        logger.info("Updating the house address");

        String key = userId + houseId + updateHouseAddressRequest.getAddress() ;

        if(idempotency.contains(key)){
            return null;
        }else {
            idempotency.add(key);
        }

        checkRole.checkAdmin(userId,houseId);

        String address = updateHouseAddressRequest.getAddress();

        House house = houseRepository.findById(houseId).orElseThrow(()-> new ResourceNotFoundException("House not found"));

        house.setAddress(address);
        house.setModifiedDate(LocalDate.now());
        houseRepository.save(house);

        return conversion.convertHouse(house);
    }

    @Transactional
    public HouseUserDTO changeOwnershipOfHouse(Long currentAdminId, AuthRequest authRequest){
        logger.info("Changing the ownership of house");

        String key = String.valueOf(currentAdminId + authRequest.getHouseId() + authRequest.getUserId());

        if(idempotency.contains(key)){
            return null;
        }else {
            idempotency.add(key);
        }

        Long userId = authRequest.getUserId();
        Long houseId = authRequest.getHouseId();

        HouseUser houseUser = checkRole.checkAdmin(currentAdminId,houseId);

        HouseUser houseUser1 = houseUserRepository.
                findByHouse_HouseIdAndUser_UserId(houseId,userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found for this house"));

        houseUser.setRole(Roles.USER);
        houseUser1.setRole(Roles.ADMIN);

        houseUserRepository.save(houseUser);
        houseUserRepository.save(houseUser1);

        return conversion.convertHouseUser(houseUser1);

    }

    public void softdeleteHouse(Long houseId) {
        House house = houseRepository.findById(houseId).orElseThrow(()->
                new ResourceNotFoundException("House not found"));

        houseRepository.delete(house);

    }

    public void softDeleteuser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User not found"));

        userRepository.delete(user);
    }
}