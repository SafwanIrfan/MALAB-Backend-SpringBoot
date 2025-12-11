package com.playwithease.PlayWithEase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playwithease.PlayWithEase.dtos.UsersDTO;
import jakarta.persistence.*;

@Entity
public class UserImageUrl {

    public UserImageUrl(){}

    public UserImageUrl(String url) {
        this.url = url;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @OneToOne
    @JoinColumn(name = "users_id", nullable = false)
    @JsonIgnore
    private Users user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
