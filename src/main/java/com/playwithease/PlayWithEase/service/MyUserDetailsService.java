package com.playwithease.PlayWithEase.service;

import com.playwithease.PlayWithEase.model.UserPrincipal;
import com.playwithease.PlayWithEase.model.Users;
import com.playwithease.PlayWithEase.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found in userDetails"));

        return new UserPrincipal(users);
    }
}
