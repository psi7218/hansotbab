package com.b209.hansotbab.user.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
public class NicknameDTO implements Serializable {

    private final String email;
    private final String newNickname;

    @Builder
    public NicknameDTO(String email, String newNickname) {
        this.email = email;
        this.newNickname = newNickname;
    }
}
