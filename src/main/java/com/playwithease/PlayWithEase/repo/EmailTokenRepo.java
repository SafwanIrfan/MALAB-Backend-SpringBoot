package com.playwithease.PlayWithEase.repo;

import com.playwithease.PlayWithEase.model.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTokenRepo extends JpaRepository<EmailToken,Long> {
    public EmailToken findByToken(String token);
}
