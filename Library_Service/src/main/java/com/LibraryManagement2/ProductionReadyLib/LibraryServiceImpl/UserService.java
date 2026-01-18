package com.LibraryManagement2.ProductionReadyLib.LibraryServiceImpl;

import com.LibraryManagement2.ProductionReadyLib.DTOs.Request.CreateUserRequest;
import com.LibraryManagement2.ProductionReadyLib.DTOs.Response.UserResponse;
import com.LibraryManagement2.ProductionReadyLib.Entities.Role;
import com.LibraryManagement2.ProductionReadyLib.Entities.User;
import com.LibraryManagement2.ProductionReadyLib.Repository.UserRepository;
import com.LibraryManagement2.ProductionReadyLib.Utilities.Conversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing users in the library.
 * <p>
 * Handles book creation of MEMBER and LIBRARIAN type roles of users
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Conversion conversion;

    /**
     * Creates a user with role MEMBER
     *
     * @param createUserRequest
     * @return UserResponse
     */
    public UserResponse addUser(CreateUserRequest createUserRequest){
        User user = conversion.convert(createUserRequest);
        user.setRole(Role.MEMBER);
        userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setRole("MEMBER");
        userResponse.setUsername(user.getUsername());
        return userResponse;
    }

    /**
     * Creates a user with role ADMIN
     *
     * @param createUserRequest
     * @return UserResponse
     */
    public UserResponse addUserAdmin(CreateUserRequest createUserRequest) {
        User user = conversion.convert(createUserRequest);
        user.setRole(Role.LIBRARIAN);
        userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setRole("LIBRARIAN");
        userResponse.setUsername(user.getUsername());
        return userResponse;
    }
}