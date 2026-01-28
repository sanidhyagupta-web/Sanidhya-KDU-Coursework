package com.LibraryManagement2.ProductionReadyLib.DTOs.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * Request payload used to create a new User.
 */
public class CreateUserRequest {

    /**
     * Username of the user
     */
    private String username;
    /**
     * Password of the user
     */
    private String password;
}