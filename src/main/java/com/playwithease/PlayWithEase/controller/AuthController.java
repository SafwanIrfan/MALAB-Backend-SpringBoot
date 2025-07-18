package com.playwithease.PlayWithEase.controller;

import com.playwithease.PlayWithEase.config.JwtFilter;
import com.playwithease.PlayWithEase.dtos.ResetPasswordDTO;
import com.playwithease.PlayWithEase.dtos.UsersDTO;
import com.playwithease.PlayWithEase.model.Users;
import com.playwithease.PlayWithEase.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

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
    public void user(@RequestBody Users users){
        authService.register(users);
    }

    @PostMapping("/forget-pass/send-email")
    public ResponseEntity<UsersDTO> verifyUserForNewPassword (@RequestBody Map<String,String> body){
        UsersDTO result = authService.verifyUserForNewPassword(body.get("email"));
        return new ResponseEntity<>(result , HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO request) {
        String result = authService.resetPassword(request.getUsername(), request.getNewPassword());
        return new ResponseEntity<>(result, HttpStatus.OK);
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
    public ResponseEntity<UsersDTO> getUserByUsername (@PathVariable String username) {
        System.out.println(username);
        UsersDTO user = authService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/api/verify/user")
    public ResponseEntity<String> verifyUserEmail(@RequestParam("token") String token){
        String verify = authService.verifyUserEmail(token);
        return new ResponseEntity<>(verify,HttpStatus.OK);
    }


}


