package com.b209.hansotbab.review.service;

import com.b209.hansotbab.fridge.entity.Fridge;
import com.b209.hansotbab.fridge.entity.Product;
import com.b209.hansotbab.fridge.repository.FridgeRepository;
import com.b209.hansotbab.fridge.repository.ProductRepository;
import com.b209.hansotbab.review.dto.request.ReviewLikeRequestDTO;
import com.b209.hansotbab.review.dto.request.ReviewRequestDTO;
import com.b209.hansotbab.review.dto.response.ReviewResponseDTO;
import com.b209.hansotbab.review.entity.Review;
import com.b209.hansotbab.review.entity.ReviewLike;
import com.b209.hansotbab.review.repository.ReviewRepository;
import com.b209.hansotbab.user.entity.User;
import com.b209.hansotbab.user.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.b209.hansotbab.fridge.entity.QFridge.fridge;

@Transactional
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final FridgeRepository fridgeRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ReviewResponseDTO> findAllReview(Long fridgeId, UUID uuid) {
        Fridge fridge = fridgeRepository.findByFridgeIdAndIsDeleteIsFalse(fridgeId).orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 냉장고를 찾을 수 없습니다: " + fridgeId));
        List<Review> reviewList = reviewRepository.findAllByFridge(fridge);

        List<ReviewResponseDTO> reviewResponseDTOList = reviewList.stream().map(review -> new ReviewResponseDTO(review, uuid)).collect(Collectors.toList());
        return reviewResponseDTOList;
    }

    @Transactional(readOnly = true)
    @Override
    public ReviewResponseDTO findReviewInfo(Long reviewId, UUID uuid) {
        Review review = reviewRepository.findByReviewIdAndIsDeleteFalse(reviewId).orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 후기를 찾을 수 없습니다." + reviewId));
        ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO(review, uuid);
        return reviewResponseDTO;
    }

    @Override
    public void createReview(Long productId, ReviewRequestDTO reviewRequestDTO, UUID uuid) {
        Product product = productRepository.findByProductIdAndIsDeleteIsFalse(productId).orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 음식을 찾을 수 없습니다: " + productId));
        Fridge fridge = product.getFridge();
        validateReviewRequiredFields(reviewRequestDTO);
        User user = userRepository.findByUuidAndIsDeleteFalse(uuid).orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 사용자를 찾을 수 없습니다: " + uuid));
        Review review = reviewRequestDTO.toReview(product, user, fridge);
        reviewRepository.save(review);
    }

    @Override
    public void updateReview(Long reviewId, ReviewRequestDTO reviewRequestDTO, UUID uuid) {
        Review review = reviewRepository.findByReviewIdAndIsDeleteFalse(reviewId).orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 후기를 찾을 수 없습니다: " + reviewId));
        validateReviewRequiredFields(reviewRequestDTO);
        userRepository.findByUuidAndIsDeleteFalse(UUID.randomUUID()).orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 사용자를 찾을 수 없습니다: " + uuid));
        review.update(reviewRequestDTO);
    }

    @Override
    public void deleteReview(Long reviewId, UUID uuid) {
        Review review = reviewRepository.findByReviewIdAndIsDeleteFalse(reviewId).orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 후기를 찾을 수 없습니다: " + reviewId));
        review.updateDelete();
    }

    @Override
    public boolean updateReviewLike(ReviewLikeRequestDTO reviewLikeRequestDTO) {
        UUID uuid = UUID.fromString(reviewLikeRequestDTO.getUuid());
        User user = userRepository.findByUuidAndIsDeleteFalse(uuid).orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다"));

        List<ReviewLike> reviewLikes = reviewRepository.findByUserUuid(uuid);

        for (ReviewLike reviewLike : reviewLikes) {
            if (Objects.equals(reviewLike.getReview().getReviewId(), reviewLikeRequestDTO.getReviewId())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void validateReviewRequiredFields(ReviewRequestDTO reviewRequestDTO) {
        if (StringUtils.isBlank(reviewRequestDTO.getReviewContent())) {
            throw new IllegalArgumentException(("Reviewlist 내용은 필수입니다."));
        }
    }

    @Override
    public List<ReviewResponseDTO> findUserReview(UUID uuid) {
        User user = userRepository.findByUuidAndIsDeleteFalse(uuid).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다." + uuid));
        List<Product> productList = productRepository.findAllByUser(user);
        List<Review> reviewUserList = new ArrayList<>();
        for (Product product : productList) {
            List<Review> reviewList = reviewRepository.findAllByProduct(product);
            reviewUserList.addAll(reviewList);
        }
        return reviewUserList.stream().map(review -> new ReviewResponseDTO(review, uuid)).collect(Collectors.toList());
    }

    @Override
    public void createFridgeReview(Long fridgeId, ReviewRequestDTO reviewRequestDTO, UUID uuid) {
        Fridge fridge = fridgeRepository.findByFridgeIdAndIsDeleteIsFalse(fridgeId).orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 냉장고를 찾을 수 없습니다."));
        validateReviewRequiredFields(reviewRequestDTO);
        User user = userRepository.findByUuidAndIsDeleteFalse(uuid).orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 사용자를 찾을 수 없습니다: " + uuid));
        Review review = reviewRequestDTO.toReview(null, user, fridge);
        reviewRepository.save(review);
    }
}
