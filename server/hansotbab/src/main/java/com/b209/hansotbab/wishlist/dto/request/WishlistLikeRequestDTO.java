package com.b209.hansotbab.wishlist.dto.request;

import lombok.Getter;

@Getter
public class WishlistLikeRequestDTO {

    private final String uuid;
    private final Long wishlistId;
    private final boolean isLikeWishlist;

    public WishlistLikeRequestDTO(String uuid, Long wishlistId,boolean isLikeWishlist){
        this.uuid = uuid;
        this.wishlistId = wishlistId;
        this.isLikeWishlist = isLikeWishlist();
    }
}
