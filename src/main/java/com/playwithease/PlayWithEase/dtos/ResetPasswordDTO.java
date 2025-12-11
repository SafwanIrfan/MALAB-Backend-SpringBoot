package com.playwithease.PlayWithEase.dtos;

public class ResetPasswordDTO {
    private String phoneNo;
    private String newPassword;


    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String username) {
        this.phoneNo = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
