package com.b209.hansotbab.user.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class RegisterRequestDTO implements Serializable {

    private final String uniqueId;
    private final String email;
    private final String nickname;
    private final String profileImgUrl;

    @Builder
    public RegisterRequestDTO(String uniqueId, String email, String nickname, String profileImgUrl) {
        this.uniqueId = uniqueId;
        this.email = email;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }
}
