package com.playwithease.PlayWithEase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_username", columnNames = "username"),
                @UniqueConstraint(name = "unique_phone", columnNames = "phone_no"),
                @UniqueConstraint(name = "unique_email", columnNames = "email")
        }
)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    @Pattern(regexp = "^(0\\d{10}|\\+92\\d{10})$", message = "Invalid Phone Number") //Regex : two backslash in JAVA and one blackslash in JS
    @Size(min = 11, max = 13)
    private String phoneNo;

    @Column(unique = true)
    private String email;

    private boolean isVerified;

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
}
