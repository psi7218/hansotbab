package com.b209.hansotbab.fridge.repository;

import com.b209.hansotbab.fridge.entity.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FridgeRepository extends JpaRepository<Fridge, Long> {

    Optional<Fridge> findByFridgeIdAndIsDeleteIsFalse(Long fridgeId);
}
