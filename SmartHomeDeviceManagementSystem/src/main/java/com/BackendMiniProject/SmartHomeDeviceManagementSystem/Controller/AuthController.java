package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Controller;


import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.AuthRequest;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.JwtRequest;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Models.JwtResponse;
import com.BackendMiniProject.SmartHomeDeviceManagementSystem.Security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager,
                          UserDetailsService userDetailsService,
                          JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getName().trim(), request.getPassword().trim()));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).build();
        }

        UserDetails ud = userDetailsService.loadUserByUsername(request.getName());
        String token = jwtUtil.generateToken(ud);
        return ResponseEntity.ok(new JwtResponse(token,"Success"));
    }
}
