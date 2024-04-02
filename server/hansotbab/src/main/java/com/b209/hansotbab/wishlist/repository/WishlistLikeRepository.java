package com.b209.hansotbab.wishlist.repository;

import com.b209.hansotbab.wishlist.entity.WishlistLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WishlistLikeRepository extends JpaRepository<WishlistLike, Long> {
    List<WishlistLike> findByUserUuid(UUID uuid);
}
