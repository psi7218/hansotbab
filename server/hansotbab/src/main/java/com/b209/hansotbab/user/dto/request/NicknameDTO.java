package com.b209.hansotbab.user.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class NicknameDTO implements Serializable {

    private final Long userId;
    private final String newNickname;

    @Builder
    public NicknameDTO(Long userId, String newNickname) {
        this.userId = userId;
        this.newNickname = newNickname;
    }
}
