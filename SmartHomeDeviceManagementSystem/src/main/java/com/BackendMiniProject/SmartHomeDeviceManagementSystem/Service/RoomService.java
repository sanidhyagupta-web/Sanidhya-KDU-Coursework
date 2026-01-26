package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Service;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.House;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.Room;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Exceptions.ResourceNotFoundException;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.AuthRequest;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.RoomDTO;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository.HouseRepository;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository.HouseUserRepository;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository.RoomRepository;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Utilities.CheckRole;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Utilities.Conversion;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoomService {

    private final HouseUserRepository houseUserRepository;
    private final RoomRepository roomRepository;
    private final HouseRepository houseRepository;
    private final CheckRole checkRole;
    private final Conversion conversion;

    Logger logger = LoggerFactory.getLogger(RoomService.class);

    public RoomService(HouseUserRepository houseUserRepository, RoomRepository roomRepository, HouseRepository houseRepository, CheckRole checkRole, Conversion conversion) {
        this.houseUserRepository = houseUserRepository;
        this.roomRepository = roomRepository;
        this.houseRepository = houseRepository;
        this.checkRole = checkRole;
        this.conversion = conversion;
    }

    Set<String> idempotency = new HashSet<>();

    @Transactional
    public RoomDTO addRoomToHouse(Long userId,RoomDTO roomDTO,Long houseId){
        logger.info("Add room to the house");

        String key = userId + roomDTO.getType() + houseId ;

        if(idempotency.contains(key)){
            return null;
        }else {
            idempotency.add(key);
        }

        checkRole.checkAdmin(userId,houseId);

        House house = houseRepository.findById(houseId).
                orElseThrow(()->new ResourceNotFoundException("House not found"));

        roomDTO.setCreatedDate(LocalDate.now());
        Room room = conversion.convertRoomDTO(roomDTO,house);
        roomRepository.save(room);

        house.getRooms().add(room);

        houseRepository.save(house);

        return roomDTO;
    }

    /**
     *
     * @return
     */
    public List<Room> getAllRooms(AuthRequest authRequest){
        logger.info("Getting all rooms in a house");

        Long userId = authRequest.getUserId();
        Long houseId = authRequest.getHouseId();

        houseUserRepository.findByHouse_HouseIdAndUser_UserId(houseId,userId).orElseThrow(()->new ResourceNotFoundException("User not present in this house"));

        House house = houseRepository.findById(houseId).orElseThrow(()->new ResourceNotFoundException("House not found"));
        return house.getRooms();
    }

    public void softDeleteRoom(Long roomId){
        Room room = roomRepository.findById(roomId).
                orElseThrow(()->new ResourceNotFoundException("Room not found"));

        roomRepository.delete(room);


    }
}
