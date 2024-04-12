package com.b209.hansotbab.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class LikedFridgeDTO implements Serializable {

    private final Long fridgeId;
    private final String fridgeLocationName;
    private final String fridgeLocationAddress;

    @Builder
    public LikedFridgeDTO(Long fridgeId, String locationName, String locationAddress){
        this.fridgeId = fridgeId;
        this.fridgeLocationName = locationName;
        this.fridgeLocationAddress = locationAddress;
    }
}
