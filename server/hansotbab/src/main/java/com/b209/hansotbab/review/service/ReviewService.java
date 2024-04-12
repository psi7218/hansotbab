package com.b209.hansotbab.review.service;

import com.b209.hansotbab.review.dto.request.ReviewLikeRequestDTO;
import com.b209.hansotbab.review.dto.request.ReviewRequestDTO;
import com.b209.hansotbab.review.dto.response.ReviewResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    List<ReviewResponseDTO> findAllReview(Long fridgeId, UUID uuid);

    ReviewResponseDTO findReviewInfo(Long reviewId, UUID uuid);

    void createReview(Long productId, ReviewRequestDTO reviewRequestDTO, UUID uuid);

    void updateReview(Long reviewId, ReviewRequestDTO reviewRequestDTO, UUID uuid);

    void deleteReview(Long reviewId, UUID uuid);

    boolean updateReviewLike(ReviewLikeRequestDTO reviewLikeRequestDTO);

    void validateReviewRequiredFields(ReviewRequestDTO reviewRequestDTO);
    List<ReviewResponseDTO> findUserReview(UUID uuid);

    void createFridgeReview(Long fridgeId, ReviewRequestDTO reviewRequestDTO, UUID uuid);
}
