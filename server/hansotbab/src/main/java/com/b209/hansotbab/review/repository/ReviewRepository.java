package com.b209.hansotbab.review.repository;

import com.b209.hansotbab.fridge.entity.Fridge;
import com.b209.hansotbab.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByFridge(Fridge fridge);

    Optional<Review> findByReviewIdAndIsDeleteFalse(Long reviewId);
}
