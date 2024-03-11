package com.b209.hansotbab.wishlist.dto.request;

import lombok.Getter;

@Getter
public class WishlistLikeRequestDTO {

    private final Long userId;
    private final Long fridgeId;
    private final Long wishlistId;

    public WishlistLikeRequestDTO(Long userId, Long fridgeId, Long wishlistId){
        this.userId = userId;
        this.fridgeId = fridgeId;
        this.wishlistId = wishlistId;
    }
}
