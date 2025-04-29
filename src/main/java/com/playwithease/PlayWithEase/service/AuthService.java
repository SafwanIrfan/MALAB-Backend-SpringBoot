package com.playwithease.PlayWithEase.service;

import com.playwithease.PlayWithEase.model.BookedSlots;
import com.playwithease.PlayWithEase.model.Users;
import com.playwithease.PlayWithEase.repo.BookedSlotsRepo;
import com.playwithease.PlayWithEase.repo.CourtsRepo;
import com.playwithease.PlayWithEase.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private CourtsRepo courtsRepo;

    @Autowired
    private BookedSlotsRepo bookedSlotsRepo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users users){
      users.setPassword(encoder.encode(users.getPassword()));
      return usersRepo.save((users));
    }

    public String verifyUser(Users users){
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));
        System.out.println(users.getUsername());
        if(authentication.isAuthenticated())
            return jwtService.generateToken(users.getUsername()) ;

        return "Fail";
    }

    public List<BookedSlots> getUserBookedSlots(Long usersId) {
            return bookedSlotsRepo.findByUsersId(usersId);
    }
}
