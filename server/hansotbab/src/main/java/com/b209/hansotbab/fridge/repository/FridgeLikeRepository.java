package com.b209.hansotbab.fridge.repository;


import com.b209.hansotbab.fridge.entity.Fridge;
import com.b209.hansotbab.fridge.entity.FridgeLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FridgeLikeRepository extends JpaRepository<FridgeLike, Long> {


    Optional<FridgeLike> findByUserUuidAndFridgeFridgeId(UUID loginUuid, Long fridgeId);

    List<FridgeLike> findByUserUuid(UUID loginUuid);

    List<FridgeLike> findAllByFridge(Fridge fridge);
}
