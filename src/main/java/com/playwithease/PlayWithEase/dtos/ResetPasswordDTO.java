package com.playwithease.PlayWithEase.dtos;

public class ResetPasswordDTO {
    private String username;
    private String newPassword;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
