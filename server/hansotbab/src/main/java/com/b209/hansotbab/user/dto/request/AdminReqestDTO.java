package com.b209.hansotbab.user.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class AdminReqestDTO implements Serializable {

    private final String userName;
    private final String userPhone;
    private final Long fridgeId;

    @Builder
    public AdminReqestDTO(String name, String phone, Long fridgeId) {
        this.userName = name;
        this.userPhone = phone;
        this.fridgeId = fridgeId;
    }
}
