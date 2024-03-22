package com.b209.hansotbab.fridge.repository;


import com.b209.hansotbab.fridge.entity.FridgeLike;
import com.b209.hansotbab.fridge.entity.ProductBring;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductBringRepository extends JpaRepository<ProductBring, Long> {
    Optional<ProductBring> findByUserUserIdAndProductProductId(Long loginUserId, Long productId);
}
