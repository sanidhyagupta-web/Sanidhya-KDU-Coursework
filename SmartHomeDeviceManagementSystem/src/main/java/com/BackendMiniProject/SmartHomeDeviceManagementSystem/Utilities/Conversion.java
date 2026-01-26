package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Utilities;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.*;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Conversion {

    public House convertHouseDTO(HouseDTO houseDTO){
        return House.builder().
                address(houseDTO.getAddress()).
                pincode(houseDTO.getPincode()).
                build();
    }

    public UserDTO convertUser(User user){
        return UserDTO.builder().
                name(user.getName()).
                age(user.getAge()).
                email(user.getEmail()).
                createdDate(user.getCreatedDate()).
                modifiedDate(user.getModifiedDate()).
                build();
    }

    public HouseDTO convertHouse(House house){
        return HouseDTO.builder().
                address(house.getAddress()).
                pincode(house.getPincode()).
                createdDate(house.getCreatedDate()).
                modifiedDate(house.getModifiedDate()).
                build();
    }

    public HouseUserDTO convertHouseUser(HouseUser houseUser){
        return HouseUserDTO.builder().
                membershipId(houseUser.getMembershipId()).
                houseId(houseUser.getHouse().getHouseId()).
                userId(houseUser.getUser().getUserId()).
                role(houseUser.getRole()).
                build();
    }

    public Room convertRoomDTO(RoomDTO roomDTO,House house){
        return Room.builder().
                type(roomDTO.getType()).
                createdDate(roomDTO.getCreatedDate()).
                house(house).
                build();
    }

    public DeviceDTO convertDeviceDTO(Device device){
        return DeviceDTO.builder().
                kickstonId(device.getKickstonId()).
                deviceUsername(device.getDeviceUsername()).
                devicePassword(device.getDevicePassword()).
                manufactureDateTime(device.getManufactureDateTime()).
                manufactureFactoryPlace(device.getManufactureFactoryPlace()).
                createdDate(device.getCreatedDate()).
                modifiedDate(device.getModifiedDate()).
                build();
    }

    public RoomDTO convertRoom(Room room){
        return RoomDTO.builder().
                type(room.getType()).
                createdDate(room.getCreatedDate()).
                modifiedDate(room.getModifiedDate()).
                build();
    }
}
