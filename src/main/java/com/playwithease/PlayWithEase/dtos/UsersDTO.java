package com.playwithease.PlayWithEase.dtos;

import com.playwithease.PlayWithEase.model.Enums.RoleNames;
import com.playwithease.PlayWithEase.model.UserImageUrl;

public class UsersDTO {
    private Long id;
    private String fullName;
    private String username;
    private boolean isVerified;
    private boolean canChangePassword;
    private RoleNames role;
    private UserImageUrl userImageUrl;

    public UsersDTO(
            Long id,
            String fullName,
            String username,
            boolean isVerified,
            boolean canChangePassword,
            RoleNames role,
            UserImageUrl userImageUrl
    )
    {
        this.username = username;
        this.id = id;
        this.fullName = fullName;
        this.isVerified = isVerified;
        this.canChangePassword = canChangePassword;
        this.role = role;
        this.userImageUrl = userImageUrl;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public RoleNames getRole() {
        return role;
    }

    public void setRole(RoleNames role) {
        this.role = role;
    }


    public UserImageUrl getUserImageUrl() {
        return userImageUrl;
    }
    public void setUserImageUrl(UserImageUrl userImageUrl) {
        this.userImageUrl = userImageUrl;
    }
}
