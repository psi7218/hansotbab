package com.b209.hansotbab.wishlist.entity;

import com.b209.hansotbab.global.entity.BaseEntity;
import com.b209.hansotbab.fridge.entity.Fridge;
import com.b209.hansotbab.user.entity.User;
import com.b209.hansotbab.wishlist.dto.request.WishlistRequestDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wishlist extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    @Column(nullable = false, length = 20)
    private String wishlistContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fridge_id", nullable = false)
    private Fridge fridge;

    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishlistLike> wishlistlikes = new ArrayList<>();

    @Builder
    public Wishlist(Long wishlistId, String wishlistContent, User user, Fridge fridge){
        this.wishlistId = wishlistId;
        this.wishlistContent = wishlistContent;
        this.user = user;
        this.fridge = fridge;
    }

    public void update(WishlistRequestDTO wishlistRequestDTO) {
        this.wishlistContent = wishlistRequestDTO.getWishlistContent();
    }
    public void updateDelete(){
        this.isDelete = true;
    }


}
