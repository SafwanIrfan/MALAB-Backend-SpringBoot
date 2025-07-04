package com.playwithease.PlayWithEase.dtos;

public class UsersDTO {
    private Long id;
    private String username;
    private boolean isVerified;

    public UsersDTO(Long id, String username, boolean isVerified){
        this.id = id;
        this.username = username;
        this.isVerified = isVerified;
    }

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

    public boolean getIsVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
