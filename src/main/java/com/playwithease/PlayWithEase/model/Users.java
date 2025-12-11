package com.playwithease.PlayWithEase.model;

import com.playwithease.PlayWithEase.model.Enums.RoleNames;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_username", columnNames = "username"),
                @UniqueConstraint(name = "unique_phone", columnNames = "phone_no"),
                @UniqueConstraint(name = "unique_email", columnNames = "email")
        }
)
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String fullName;

    private String password;

    //"^(0\\d{10}|\\+92\\d{10})$"

    @Column(unique = true)
    @Pattern(regexp = "^0\\d{10}$", message = "Invalid Phone Number") //Regex : two backslash in JAVA and one blackslash in JS
    private String phoneNo;

    @Column(unique = true)
    private String email;

    private boolean isVerified;

    private boolean canChangePassword;

    @Enumerated(EnumType.STRING)
    private RoleNames role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserImageUrl userImageUrl;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Court> courts;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourtsFav> courtsFavorites;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<CourtsFav> getCourtsFavorites() {
        return courtsFavorites;
    }

    public void setCourtsFavorites(List<CourtsFav> courtsFavorites) {
        this.courtsFavorites = courtsFavorites;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsVerified(){
        return isVerified;
    }
    public void setIsVerified(Boolean isVerified){
        this.isVerified = isVerified;
    }

    public boolean getCanChangePassword() {
        return canChangePassword;
    }

    public void setCanChangePassword(boolean canChangePassword) {
        this.canChangePassword = canChangePassword;
    }

    public List<Court> getCourts() {
        return courts;
    }

    public void setCourts(List<Court> courts) {
        this.courts = courts;
    }

    public UserImageUrl getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(UserImageUrl userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public RoleNames getRole() {
        return role;
    }
    public void setRole(RoleNames role) {
        this.role = role;
    }
}
