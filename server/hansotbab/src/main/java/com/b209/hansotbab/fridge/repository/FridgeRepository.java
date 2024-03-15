package com.b209.hansotbab.fridge.repository;

import com.b209.hansotbab.fridge.entity.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Long> {
    Optional<Fridge> findByFridgeIdAndIsDeleteFalse(Long fridgeId);
}
