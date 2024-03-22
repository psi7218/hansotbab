package com.b209.hansotbab.fridge.repository;


import com.b209.hansotbab.fridge.entity.FridgeLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FridgeLikeRepository extends JpaRepository<FridgeLike, Long> {


    Optional<FridgeLike> findByUserUserIdAndFridgeFridgeId(Long loginUserId, Long fridgeId);

    List<FridgeLike> findByUserUserId(Long loginUserId);
}
