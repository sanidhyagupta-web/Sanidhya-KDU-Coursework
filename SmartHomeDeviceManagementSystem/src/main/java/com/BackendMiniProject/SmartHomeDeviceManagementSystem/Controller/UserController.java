package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Controller;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.AuthRequest;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.HouseUserDTO;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.UserDTO;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Service.UserHouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/v1")
public class UserController {

    private final UserHouseService userHouseService;

    public UserController(UserHouseService userHouseService) {
        this.userHouseService = userHouseService;
    }

    @PostMapping("/{addUserId}")
    public ResponseEntity<UserDTO> addUserToHouse(@PathVariable("addUserId") Long addUserId,
                                                  @RequestBody AuthRequest authRequest){
        return new ResponseEntity<>(userHouseService.addUsersToHouse(addUserId,authRequest), HttpStatus.CREATED);
    }

    @PostMapping("/owner")
    public ResponseEntity<HouseUserDTO> changeOwnership(@RequestBody AuthRequest authRequest,
                                                        @RequestParam("currentAdminId") Long currentAdminId){
        System.out.println("Here");
        return new ResponseEntity<>(userHouseService.changeOwnershipOfHouse(currentAdminId,authRequest),HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> softDeleteRoom(@PathVariable Long userId) {
        userHouseService.softDeleteuser(userId);
        return ResponseEntity.noContent().build();
    }
}
