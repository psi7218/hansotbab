package com.b209.hansotbab.fridge.dto.response;

import com.b209.hansotbab.fridge.entity.Fridge;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FridgeListResponseDTO {

    private final Long fridgeId;
    private final Integer fridgeNumber;
    private final String fridgeLocationName;
    private final String fridgeLocationAddress;
    private final Long fridgeDonated;

    @Builder
    public FridgeListResponseDTO(Long fridgeId, Integer fridgeNumber, String fridgeLocationName, String fridgeLocationAddress, Long fridgeDonated) {
        this.fridgeId = fridgeId;
        this.fridgeNumber = fridgeNumber;
        this.fridgeLocationName = fridgeLocationName;
        this.fridgeLocationAddress = fridgeLocationAddress;
        this.fridgeDonated = fridgeDonated;
    }

    public static FridgeListResponseDTO fromEntity(Fridge fridge, Long fridgeDonated) {
        return FridgeListResponseDTO.builder()
                .fridgeId(fridge.getFridgeId())
                .fridgeNumber(fridge.getFridgeNumber())
                .fridgeLocationName(fridge.getFridgeLocationName())
                .fridgeLocationAddress(fridge.getFridgeLocationAddress())
                .fridgeDonated(fridgeDonated)
                .build();
    }
}
