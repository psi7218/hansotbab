package com.b209.hansotbab.user.dto.request;

import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
public class LoginRequestDTO implements Serializable {

    private final String uuid;
    private final String email;

    public LoginRequestDTO(String uuid, String email) {
        this.uuid = uuid;
        this.email = email;
    }
}
