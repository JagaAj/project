package com.trio.Elitecar.controller;

import com.trio.Elitecar.model.User;
import com.trio.Elitecar.service.JwtService;
import com.trio.Elitecar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth") // ✅ This ensures /auth/login & /auth/register match frontend
@CrossOrigin(origins = "http://localhost:5173") // ✅ Allow frontend access
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.saveUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getEmail());
            } else {
                return "Login failed";
            }
        } catch (Exception e) {
            return "Invalid credentials";
        }
    }

}
