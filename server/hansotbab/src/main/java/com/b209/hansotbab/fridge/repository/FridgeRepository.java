package com.b209.hansotbab.fridge.repository;

import com.b209.hansotbab.fridge.entity.Fridge;
import com.b209.hansotbab.fridge.repository.custom.FridgeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface FridgeRepository extends JpaRepository<Fridge, Long> {

    Optional<Fridge> findByFridgeIdAndIsDeleteIsFalse(Long fridgeId);
}
