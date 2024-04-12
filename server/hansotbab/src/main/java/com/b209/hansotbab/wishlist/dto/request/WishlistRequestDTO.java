package com.b209.hansotbab.wishlist.dto.request;

import com.b209.hansotbab.fridge.entity.Fridge;
import com.b209.hansotbab.user.entity.User;
import com.b209.hansotbab.wishlist.entity.Wishlist;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WishlistRequestDTO {

    private String wishlistContent;

    @Builder
    public WishlistRequestDTO(String wishlistContent){
        this.wishlistContent = wishlistContent;
    }

    public Wishlist toWishlist(Fridge fridge, User user) {
        return Wishlist.builder()
                .user(user)
                .wishlistContent(this.wishlistContent)
                .fridge(fridge)
                .build();
    }

}
