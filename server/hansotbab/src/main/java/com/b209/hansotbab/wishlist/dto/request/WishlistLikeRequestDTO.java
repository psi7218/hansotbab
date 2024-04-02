package com.b209.hansotbab.wishlist.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@NoArgsConstructor
public class WishlistLikeRequestDTO {

    private final String uuid;
    private final Long wishlistId;

    public WishlistLikeRequestDTO(String uuid, Long wishlistId){
        this.uuid = uuid;
        this.wishlistId = wishlistId;
    }
}
