package com.LibraryManagement2.ProductionReadyLib.Controller;

import com.LibraryManagement2.ProductionReadyLib.DTOs.Request.CreateUserRequest;
import com.LibraryManagement2.ProductionReadyLib.DTOs.Response.UserResponse;
import com.LibraryManagement2.ProductionReadyLib.Entities.User;
import com.LibraryManagement2.ProductionReadyLib.LibraryServiceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserResponse> addUser(@RequestBody CreateUserRequest createUserRequest){
        return new ResponseEntity<>(userService.addUser(createUserRequest), HttpStatus.CREATED);
    }


    @PostMapping("/user/admin")
    public ResponseEntity<UserResponse> addUserAdmin(@RequestBody CreateUserRequest createUserRequest){
        return new ResponseEntity<>(userService.addUserAdmin(createUserRequest), HttpStatus.CREATED);
    }

}
