package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Controller;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.House;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.Room;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.AuthRequest;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.DeviceCreds;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.HouseDTO;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.RoomDTO;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device/v1")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/{kickstonId}/user/{userId}/house/{houseId}/move")
    public ResponseEntity<HouseDTO> addDeviceToHouse(@PathVariable("kickstonId") String kickstonId,
                                                     @PathVariable("userId") Long userId,
                                                     @PathVariable("houseId") Long houseId,
                                                     @Valid @RequestBody DeviceCreds deviceCreds){

        return new ResponseEntity<>(deviceService.addDeviceToHouse(userId,houseId,kickstonId,deviceCreds), HttpStatus.CREATED);
    }

    @PostMapping("/{kickstonId}/room/{roomId}")
    public ResponseEntity<RoomDTO> assignDeviceToRoom(@PathVariable("kickstonId") String kickstonId,
                                                      @RequestBody AuthRequest authRequest,
                                                      @PathVariable("roomId") Long roomId){
        return new ResponseEntity<>(deviceService.assignDeviceToRoom(kickstonId.trim(),authRequest,roomId),HttpStatus.CREATED);
    }

    @PostMapping("/{kickstonId}/room/{roomId}/moveTo/{moveToRoomId}")
    public ResponseEntity<RoomDTO> moveDeviceFromOneRoomToAnother(@PathVariable String kickstonId,
                                                               @PathVariable Long roomId,
                                                               @PathVariable Long moveToRoomId,
                                                               @RequestBody AuthRequest authRequest){

        return new ResponseEntity<>(deviceService.moveDeviceFromOneRoomToAnother(roomId,moveToRoomId,kickstonId.trim(),authRequest),HttpStatus.OK);
    }

    @QueryMapping
    public List<Room> getAllRoomsAllDevices(@Argument("auth") AuthRequest authRequest,
                                            @Argument Integer page,
                                            @Argument Integer size){
        int p = (page == null) ? 0 : page;
        int s = (size == null) ? 10 : size;
        PageRequest pageable = PageRequest.of(p,s);
        return deviceService.getAllRoomsAllDevices(authRequest,pageable);
    }

    @DeleteMapping("/{KickstonId}")
    public ResponseEntity<Void> softDeleteDevice(@PathVariable("KickstonId") Long KickstonId){
        deviceService.softDeleteDevice(KickstonId);
        return ResponseEntity.noContent().build();
    }
}