package com.playwithease.PlayWithEase.service;

import com.playwithease.PlayWithEase.dtos.UsersDTO;
import com.playwithease.PlayWithEase.model.Users;
import com.playwithease.PlayWithEase.model.EmailToken;
import com.playwithease.PlayWithEase.repo.BookedSlotsRepo;
import com.playwithease.PlayWithEase.repo.CourtsRepo;
import com.playwithease.PlayWithEase.repo.EmailTokenRepo;
import com.playwithease.PlayWithEase.repo.UsersRepo;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private CourtsRepo courtsRepo;

    @Autowired
    private BookedSlotsRepo bookedSlotsRepo;

    @Autowired
    private EmailTokenRepo emailTokenRepo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

//    public boolean checkUsernameExists(String username) {
//       return usersRepo.existsByUsername(username);
//    }

    public boolean checkUsernameExists(String username) {
        return usersRepo.existsByUsername(username);
    }

    public void register(Users users){
     try {
      users.setPassword(encoder.encode(users.getPassword()));
      usersRepo.save((users));
      sendEmail(users);
     } catch (DataIntegrityViolationException e){
         throw e; //handled by GlobalExceptionHandler
     } catch (ConstraintViolationException e){
         throw e;
     }
    }

    public String sendEmail(Users users){
        String token = UUID.randomUUID().toString();
        EmailToken emailToken = new EmailToken();
        emailToken.setToken(token);
        emailToken.setUsers(users);
        emailToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        emailTokenRepo.save(emailToken);

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            String verifyUrl = "http://localhost:8080/auth/api/verify/user?token=" + token;
            mailMessage.setFrom(sender);
            mailMessage.setTo(users.getEmail());
            mailMessage.setSubject("Please Verify Your Email");
            mailMessage.setText("Click the link to verify : " + verifyUrl);

            javaMailSender.send(mailMessage);
            return "Mail sent successfully.";
        } catch (Exception e) {
            return "Error while sending mail : " + e;
        }
    }

    public String verifyUser(Users users){
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));
        System.out.println(users.getUsername());
        if(authentication.isAuthenticated())
            return jwtService.generateToken(users.getUsername()) ;

        return "Fail";
    }

    public UsersDTO getUserByUsername(String username){
         Users user = usersRepo.findByUsername(username);
         return fromEntityToDTO(user);
    }

    public UsersDTO fromEntityToDTO(Users user){
        if(user == null) return null;
        return new UsersDTO(user.getId(),user.getUsername(),user.getIsVerified());
    }

    public String verifyUserEmail(String token){
        EmailToken vt = emailTokenRepo.findByToken(token);
        if(vt == null){
            return "Invalid token";
        }

        if(vt.getExpiryDate().isBefore(LocalDateTime.now())){
            return "Oh! This link is expired. Create an account and click the link before it expires.";
        }

        Users users = vt.getUsers();
        users.setIsVerified(true);
        usersRepo.save(users);
        emailTokenRepo.delete(vt);

        return "Your account has been verified! Now you can login.";
    }
}
