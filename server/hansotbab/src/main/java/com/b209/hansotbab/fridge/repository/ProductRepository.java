package com.b209.hansotbab.fridge.repository;

import com.b209.hansotbab.fridge.entity.Product;
import com.b209.hansotbab.fridge.entity.ProductCategory;
import com.b209.hansotbab.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductIdAndIsDeleteIsFalse(Long productId);

    List<Product> findTop7ByIsDeleteIsFalseOrderByCreatedDateDesc();

    List<Product> findAllByFridgeFridgeIdAndIsDeleteIsFalseOrderByCreatedDateDesc(Long fridgeId);

    List<Product> findAllByFridgeFridgeIdAndProductCategoryEqualsAndIsDeleteIsFalseOrderByCreatedDateDesc(Long fridgeId, ProductCategory category);

    List<Product> findAllByUserUuidAndIsDeleteIsFalseOrderByCreatedDateDesc(UUID loginUuid);

    List<Product> findAllByUser(User user);
}
