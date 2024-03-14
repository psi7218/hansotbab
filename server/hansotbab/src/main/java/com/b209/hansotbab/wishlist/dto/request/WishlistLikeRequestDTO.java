package com.b209.hansotbab.wishlist.dto.request;

import lombok.Getter;

@Getter
public class WishlistLikeRequestDTO {

    private final Long userId;
    private final Long wishlistId;
    private final boolean isLikeWishlist;

    public WishlistLikeRequestDTO(Long userId, Long wishlistId,boolean isLikeWishlist){
        this.userId = userId;
        this.wishlistId = wishlistId;
        this.isLikeWishlist = isLikeWishlist();
    }
}
