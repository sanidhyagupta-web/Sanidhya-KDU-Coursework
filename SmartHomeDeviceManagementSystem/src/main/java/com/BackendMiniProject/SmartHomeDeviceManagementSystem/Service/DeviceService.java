package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Service;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.Device;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.House;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.Room;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Exceptions.ResourceNotFoundException;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.*;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository.DeviceRepository;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository.HouseRepository;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository.HouseUserRepository;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Repository.RoomRepository;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Utilities.CheckRole;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Utilities.Conversion;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    private final HouseRepository houseRepository;

    private final RoomRepository roomRepository;

    private final HouseUserRepository houseUserRepository;

    private final CheckRole checkRole;

    private final Conversion conversion;

    public DeviceService(DeviceRepository deviceRepository, HouseRepository houseRepository, RoomRepository roomRepository, HouseUserRepository houseUserRepository, CheckRole checkRole, Conversion conversion) {
        this.deviceRepository = deviceRepository;
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.houseUserRepository = houseUserRepository;
        this.checkRole = checkRole;
        this.conversion = conversion;
    }

    Logger logger = LoggerFactory.getLogger(DeviceService.class);

    Set<String> idempotency = new HashSet<>();

    @Transactional
    public HouseDTO addDeviceToHouse(Long userId, Long houseId, String kickston_id, DeviceCreds deviceCreds){
        logger.info("Adding device to the house");

        String key = userId + houseId + kickston_id + deviceCreds.hashCode();

        if(idempotency.contains(key)){
            return  null;
        }else{
            idempotency.add(key);
        }

        checkRole.checkAdmin(userId,houseId);

        String device_username = deviceCreds.getDevice_username();
        String device_password = deviceCreds.getDevice_password();

        Device device = deviceRepository.
                findByKickstonIdAndDeviceUsernameAndDevicePassword(kickston_id,device_username,device_password).
                orElseThrow(()->new ResourceNotFoundException("Device not found"));
        device.setModifiedDate(LocalDate.now());

        House house = houseRepository.findById(houseId).
                orElseThrow(()->new ResourceNotFoundException("House not found"));
        house.addDevice(device);
        house.setModifiedDate(LocalDate.now());
        houseRepository.save(house);

        return conversion.convertHouse(house);

    }

    @Transactional
    public RoomDTO assignDeviceToRoom(String kickston_id, AuthRequest authRequest, Long roomId){
        logger.info("Assigning device to the room");

        String key =  kickston_id + authRequest.hashCode() + roomId;

        if(idempotency.contains(key)){
            return  null;
        }else{
            idempotency.add(key);
        }

        Long userId = authRequest.getUserId();
        Long houseId = authRequest.getHouseId();


        House house = houseRepository.findById(houseId).
                orElseThrow(()->new ResourceNotFoundException("House not found"));

        Device device = house.getDevices().
                stream().
                filter(x-> Objects.equals(x.getKickstonId(), kickston_id)).
                findFirst().
                orElseThrow(()->new ResourceNotFoundException("Device not found"));
        device.setModifiedDate(LocalDate.now());
        deviceRepository.save(device);

        Room room = roomRepository.findById(roomId).orElseThrow(()->new ResourceNotFoundException("Room not found"));
        room.addDevice(device);
        room.setModifiedDate(LocalDate.now());
        roomRepository.save(room);

        DeviceDTO deviceDTO = conversion.convertDeviceDTO(device);

        RoomDTO roomDTO = conversion.convertRoom(room);
        roomDTO.getDevices().add(deviceDTO);

        return roomDTO;
    }


    @Transactional
    public RoomDTO moveDeviceFromOneRoomToAnother(Long roomId, Long moveToRoomId,
                                                  String kickstonId, AuthRequest authRequest) {
        logger.info("Moving device from one room to another");

        String key = roomId + moveToRoomId + kickstonId + authRequest.getUserId() + authRequest.getHouseId();

        if(idempotency.contains(key)){
            return  null;
        }else{
            idempotency.add(key);
        }

        Long userId = authRequest.getUserId();
        Long houseId = authRequest.getHouseId();

        houseUserRepository.findByHouse_HouseIdAndUser_UserId(houseId,userId).
                orElseThrow(()->new ResourceNotFoundException("User not present in house"));

        Room from = roomRepository.findByRoomIdAndHouse_HouseId(roomId, houseId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        from.setModifiedDate(LocalDate.now());

        Room to = roomRepository.findByRoomIdAndHouse_HouseId(moveToRoomId,houseId).
                orElseThrow(()->new ResourceNotFoundException("Room not found"));
        to.setModifiedDate(LocalDate.now());

        Device device = from.getDevices().stream()
                .filter(d -> Objects.equals(d.getKickstonId(), kickstonId))
                .findFirst()
                .orElseThrow(()->new ResourceNotFoundException("Device not found"));

        // update both sides + owning side
        from.getDevices().remove(device);
        to.getDevices().add(device);
        device.setRoom(to); //  this is the key as Device owns the FK

        roomRepository.save(from);
        to = roomRepository.save(to);

        device.setModifiedDate(LocalDate.now());
        deviceRepository.save(device);

        DeviceDTO deviceDTO = conversion.convertDeviceDTO(device);

        RoomDTO roomDTO = conversion.convertRoom(to);
        roomDTO.getDevices().add(deviceDTO);
        return roomDTO;
    }

    @Transactional
    public List<Room> getAllRoomsAllDevices(AuthRequest authRequest, Pageable pageable){
        logger.info("Getting all devices within all rooms in a house");

        Long userId = authRequest.getUserId();
        Long houseId = authRequest.getHouseId();

        houseUserRepository.findByHouse_HouseIdAndUser_UserId(houseId,userId).
                orElseThrow(()->new ResourceNotFoundException("User not present in this house"));

        return roomRepository.findByHouseIdWithDevices(houseId,pageable);
    }

    public void softDeleteDevice(Long kickstonId){
        Device device = deviceRepository.findById(kickstonId).
                orElseThrow(()->new ResourceNotFoundException("Device not found"));

        deviceRepository.delete(device);
    }

}