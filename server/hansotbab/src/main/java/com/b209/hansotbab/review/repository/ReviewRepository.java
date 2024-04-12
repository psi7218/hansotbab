package com.b209.hansotbab.review.repository;

import com.b209.hansotbab.fridge.entity.Fridge;
import com.b209.hansotbab.fridge.entity.Product;
import com.b209.hansotbab.review.entity.Review;
import com.b209.hansotbab.review.entity.ReviewLike;
import com.b209.hansotbab.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByFridge(Fridge fridge);

    Optional<Review> findByReviewIdAndIsDeleteFalse(Long reviewId);

    List<Review> findAllByProduct(Product product);

    List<ReviewLike> findByUserUuid(UUID uuid);
}
