package com.LibraryManagement2.ProductionReadyLib.Utilities;

import com.LibraryManagement2.ProductionReadyLib.DTOs.Request.CreateUserRequest;
import com.LibraryManagement2.ProductionReadyLib.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class Conversion {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User convert(CreateUserRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return user;
    }

}
