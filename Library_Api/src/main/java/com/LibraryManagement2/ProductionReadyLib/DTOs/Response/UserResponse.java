package com.LibraryManagement2.ProductionReadyLib.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
/**
 * Response payload used to create a new User.
 */
public class UserResponse {
    /**
     * Username of the user.
     */
    private String username;
    /**
     * Role of the user.
     */
    private String role;
}