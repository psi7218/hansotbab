package com.b209.hansotbab.wishlist.repository;

import com.b209.hansotbab.fridge.entity.Fridge;
import com.b209.hansotbab.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findAllByFridge(Fridge fridge);
}