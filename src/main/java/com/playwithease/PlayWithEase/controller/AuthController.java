package com.playwithease.PlayWithEase.controller;

import com.playwithease.PlayWithEase.config.JwtFilter;
import com.playwithease.PlayWithEase.model.Users;
import com.playwithease.PlayWithEase.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private AuthService authService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }

    @PostMapping("/register")
    public Users user(@RequestBody Users users){
        System.out.println(users.getUsername());
        return authService.register(users);

    }

    @PostMapping("/login")
    public String login(@RequestBody Users users){
        return authService.verifyUser(users);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return new ResponseEntity<>("Successfully logged out.", HttpStatus.OK);

    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Users> getUserByUsername (@PathVariable String username) {
        Users user = authService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}


