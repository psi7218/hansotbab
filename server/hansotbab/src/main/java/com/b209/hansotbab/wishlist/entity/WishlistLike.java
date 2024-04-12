package com.b209.hansotbab.wishlist.entity;

import com.b209.hansotbab.global.entity.BaseEntity;
import com.b209.hansotbab.global.entity.BaseTimeEntity;
import com.b209.hansotbab.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WishlistLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_id", nullable = false)
    private Wishlist wishlist;

    @Builder
    public WishlistLike(Long wishlistLikeId, User user, Wishlist wishlist) {
        this.wishlistLikeId = wishlistLikeId;
        this.user = user;
        this.wishlist = wishlist;
    }
}
