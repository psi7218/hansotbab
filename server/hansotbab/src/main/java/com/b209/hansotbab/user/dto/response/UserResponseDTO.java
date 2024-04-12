package com.b209.hansotbab.user.dto.response;

import com.b209.hansotbab.user.entity.RoleType;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserResponseDTO implements Serializable {

    private final String email;
    private final String nickname;
    private final String role;
    private final String profileImgUrl;
    private final String phoneNumber;
    private final String name;
    private final boolean alarmApproval;

    @Builder
    public UserResponseDTO (String email, String nickname, String role, String profileImgUrl, String phoneNumber, String name, boolean alarmApproval) {
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.profileImgUrl = profileImgUrl;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.alarmApproval = alarmApproval;
    }
}
