package com.b209.hansotbab.fridge.repository;


import com.b209.hansotbab.fridge.entity.FridgeLike;
import com.b209.hansotbab.fridge.entity.ProductBring;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductBringRepository extends JpaRepository<ProductBring, Long> {
    Optional<ProductBring> findByUserUuidAndProductProductId(UUID loginUuid, Long productId);
    List<ProductBring> findAllByUserUuid(UUID loginUuid);

    List<ProductBring> findAllByProductProductId(Long productId);
}

