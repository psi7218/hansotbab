package com.b209.hansotbab.user.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
public class AdminReqestDTO implements Serializable {

    private final String uuid;
    private final Long fridgeId;

    @Builder
    public AdminReqestDTO(String uuid, Long fridgeId) {
        this.uuid = uuid;
        this.fridgeId = fridgeId;
    }
}
