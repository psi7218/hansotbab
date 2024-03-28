package com.b209.hansotbab.user.dto.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class TokenDTO implements Serializable {

    private final String uuid;
    private final String accessToken;
    private final String refreshToken;

    public TokenDTO(String uuid, String accessToken, String refreshToken) {
        this.uuid = uuid;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
