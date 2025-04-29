package com.playwithease.PlayWithEase.controller;

import com.playwithease.PlayWithEase.config.JwtFilter;
import com.playwithease.PlayWithEase.model.BookedSlots;
import com.playwithease.PlayWithEase.model.Users;
import com.playwithease.PlayWithEase.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
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
        return authService.register(users);
    }

//    @PostMapping("/login")
//    public String login(@RequestBody Map<String, String> credentials) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password"))
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return "Login successful!";
//    }

    @PostMapping("/login")
    public String login(@RequestBody Users users){
        return authService.verifyUser(users);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return new ResponseEntity<>("Successfully logged out.", HttpStatus.OK);

    }

    @GetMapping("/user/{usersId}/slots")
    public ResponseEntity<List<BookedSlots>> getUserBookedSlots(@PathVariable Long usersId){
        return new ResponseEntity<>(authService.getUserBookedSlots(usersId),HttpStatus.OK);
    }


}


