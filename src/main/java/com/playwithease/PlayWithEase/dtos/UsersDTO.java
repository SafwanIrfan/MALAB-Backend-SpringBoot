package com.playwithease.PlayWithEase.dtos;

public class UsersDTO {
    private Long id;
    private String username;
    private boolean isVerified;
    private boolean canChangePassword;

    public UsersDTO(Long id, String username, boolean isVerified, boolean canChangePassword){
        this.id = id;
        this.username = username;
        this.isVerified = isVerified;
        this.canChangePassword = canChangePassword;
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

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public boolean getCanChangePassword() {
        return canChangePassword;
    }

    public void setCanChangePassword(boolean canChangePassword) {
        this.canChangePassword = canChangePassword;
    }
}
