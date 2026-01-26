package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Controller;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.Room;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.AuthRequest;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.RoomDTO;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Service.RoomService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room/v1")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/{userId}/house/{houseId}")
    public ResponseEntity<RoomDTO> addRoomToHouse(@PathVariable("userId") Long userId,
                                               @PathVariable("houseId") Long houseId,
                                               @Valid @RequestBody RoomDTO roomDTO){
        return new ResponseEntity<>(roomService.addRoomToHouse(userId,roomDTO,houseId), HttpStatus.CREATED);
    }

    @QueryMapping
    public List<Room> getAllRooms(@Argument("auth") AuthRequest authRequest){
        return roomService.getAllRooms(authRequest);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> softDeleteRoom(@PathVariable Long roomId) {
        roomService.softDeleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }


}
