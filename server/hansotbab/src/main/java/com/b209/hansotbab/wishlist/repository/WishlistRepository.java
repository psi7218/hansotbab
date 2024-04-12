package com.b209.hansotbab.wishlist.repository;

import com.b209.hansotbab.fridge.entity.Fridge;
import com.b209.hansotbab.user.entity.User;
import com.b209.hansotbab.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findAllByFridgeAndIsDeleteIsFalse(Fridge fridge);
    Optional<Wishlist> findByWishlistIdAndIsDeleteFalse(Long wishlistId);
}