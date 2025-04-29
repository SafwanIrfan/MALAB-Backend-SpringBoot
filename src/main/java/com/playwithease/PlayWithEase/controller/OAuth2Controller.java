package com.playwithease.PlayWithEase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class OAuth2Controller {
    @GetMapping("/callback")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal OAuth2User oauthUser) {
        if (oauthUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("name", oauthUser.getAttribute("name"));
        userDetails.put("email", oauthUser.getAttribute("email"));
        userDetails.put("picture", oauthUser.getAttribute("picture"));

        return ResponseEntity.ok(userDetails);
    }
}
