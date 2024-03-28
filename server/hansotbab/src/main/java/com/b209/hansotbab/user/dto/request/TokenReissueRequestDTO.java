package com.b209.hansotbab.user.dto.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class TokenReissueRequestDTO implements Serializable {

    private final String uuid;
    private final String accessToken;

    public TokenReissueRequestDTO(String uuid, String accessToken) {
        this.uuid = uuid;
        this.accessToken = accessToken;
    }
}
