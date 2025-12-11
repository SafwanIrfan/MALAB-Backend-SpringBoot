package com.playwithease.PlayWithEase.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.playwithease.PlayWithEase.dtos.UsersDTO;
import com.playwithease.PlayWithEase.model.UserImageUrl;
import com.playwithease.PlayWithEase.model.Users;
import com.playwithease.PlayWithEase.model.EmailToken;
import com.playwithease.PlayWithEase.repo.CourtsRepo;
import com.playwithease.PlayWithEase.repo.EmailTokenRepo;
import com.playwithease.PlayWithEase.repo.UserImageUrlsRepo;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private UserImageUrlsRepo userImageUrlsRepo;

    @Autowired
    private CourtsRepo courtsRepo;

    @Autowired
    private EmailTokenRepo emailTokenRepo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    Cloudinary cloudinary;

    @Value("${spring.mail.username}") private String sender;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

//    public boolean checkUsernameExists(String username) {
//       return usersRepo.existsByUsername(username);
//    }

    public boolean checkUsernameExists(String username) {
        return usersRepo.existsByUsername(username);
    }


    public String resetPassword (String username, String newPassword){
        Users users = usersRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found for reseting password."));
        users.setPassword(encoder.encode(newPassword));
        users.setCanChangePassword(false);
        usersRepo.save(users);
        return ("Password Updated Successfully!");
    }

    public UsersDTO verifyUserForNewPassword(String email){
        Users user = usersRepo.findByEmail(email);
        System.out.println(user);
        if (user == null) return null;

        System.out.println("USER FOUND");
        sendEmail(user, "newPassword");
        return fromEntityToDTO(user);

    }

    public void register(Users users){
     try {
         users.setPassword(encoder.encode(users.getPassword()));
      usersRepo.save((users));

      sendEmail(users, "register");
     } catch (DataIntegrityViolationException e){
         throw e; //handled by GlobalExceptionHandler
     } catch (ConstraintViolationException e){
         throw e;
     }
    }

    public String addUserImage(String username, MultipartFile file) throws IOException {

        Users user = usersRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found for adding image."));

        Map uploadUserImage = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String userImageUrl = uploadUserImage.get("secure_url").toString();
        UserImageUrl userUrl = new UserImageUrl(userImageUrl);
        userUrl.setUser(user);
        user.setUserImageUrl(userUrl);
        usersRepo.save(user);

        return "User image successfully saved.";


    }

    public String sendEmail(Users users, String purpose){
        String token = UUID.randomUUID().toString();
        EmailToken emailToken = new EmailToken();
        emailToken.setToken(token);
        emailToken.setUsers(users);
        emailToken.setPurpose(purpose);
        emailToken.setExpiryDate(LocalDateTime.now().plusMinutes(1));

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
        if(authentication.isAuthenticated())
            return jwtService.generateToken(users.getUsername()) ;

        return "Invalid credentials.";
    }

    public UsersDTO getUserByUsername(String username){
         Users user = usersRepo.findByUsername(username)
                 .orElseThrow(() -> new RuntimeException("User not found for converting it into UsersDTO class."));
         return fromEntityToDTO(user);
    }

    public UsersDTO fromEntityToDTO(Users user){
        if(user == null) return null;
        return new UsersDTO(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getIsVerified(),
                user.getCanChangePassword(),
                user.getRole()
        );
    }

    public String verifyUserEmail(String token){
        EmailToken verificationToken = emailTokenRepo.findByToken(token);
        if(verificationToken == null){
            return "Invalid token";
        }

        if(verificationToken.getExpiryDate().isBefore(LocalDateTime.now())){
            emailTokenRepo.delete(verificationToken);
            if(Objects.equals(verificationToken.getPurpose(), "register")) {
                usersRepo.delete(verificationToken.getUsers());
            }
            return "Oh! This link is expired.";
        }

        Users users = verificationToken.getUsers();

        if(Objects.equals(verificationToken.getPurpose(), "register")) {
            users.setIsVerified(true);
            usersRepo.save(users);
            emailTokenRepo.delete(verificationToken);
            return "Your account has been verified! Now you can login.";
        }
       else {
            users.setCanChangePassword(true);
            usersRepo.save(users);
            emailTokenRepo.delete(verificationToken);
            System.out.println("DELETED TOKEN!");
            return "Successfully Verified. Now you can change your password.";
        }
    }
}
