package com.b209.hansotbab.fridge.repository;

import com.b209.hansotbab.fridge.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
