package com.b209.hansotbab.wishlist.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WishlistRequestDTO {

    private final Long userId;
    private final String wishlistContent;

    @Builder
    public WishlistRequestDTO(Long userId, String wishlistContent){
        this.userId = userId;
        this.wishlistContent = wishlistContent;
    }
}
