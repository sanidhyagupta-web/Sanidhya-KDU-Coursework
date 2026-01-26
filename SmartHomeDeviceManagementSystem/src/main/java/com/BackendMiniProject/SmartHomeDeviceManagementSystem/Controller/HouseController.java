package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Controller;

import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Entities.House;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.*;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Service.UserHouseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/house/v1")
public class HouseController {

    private final UserHouseService userHouseService;

    public HouseController(UserHouseService userHouseService) {
        this.userHouseService = userHouseService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<House> createHouse(@Valid @RequestBody HouseDTO houseDTO, @PathVariable("userId") Long userId){
        return new ResponseEntity<>(userHouseService.createHouse(userId,houseDTO), HttpStatus.CREATED);
    }


    @PatchMapping("/{houseId}/user/{userId}")
    public ResponseEntity<HouseDTO> updateAddress(@PathVariable("userId") Long userId,
                                                  @PathVariable("houseId") Long houseId,
                                                 @Valid @RequestBody UpdateHouseAddressRequest addressRequest){

        return new ResponseEntity<>(userHouseService.updateHouseAddress(userId,houseId,addressRequest),HttpStatus.OK);

    }

    @QueryMapping
    public Page<House> getAllHousesUserBelong(@Argument Long userId,
                                              @Argument int page,
                                              @Argument int size){
        Pageable pageable = PageRequest.of(page,size);
        return userHouseService.getAllHousesUserBelong(userId,pageable);
    }

    @DeleteMapping("/{houseId}")
    public ResponseEntity<Void> deleteHouse(@PathVariable("houseId") Long houseId){
        userHouseService.softdeleteHouse(houseId);
        return ResponseEntity.noContent().build();
    }
}