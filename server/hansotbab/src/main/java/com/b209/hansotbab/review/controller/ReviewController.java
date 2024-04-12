package com.b209.hansotbab.review.controller;

import com.b209.hansotbab.review.dto.request.ReviewLikeRequestDTO;
import com.b209.hansotbab.review.dto.request.ReviewRequestDTO;
import com.b209.hansotbab.review.dto.response.ReviewResponseDTO;
import com.b209.hansotbab.review.service.ReviewService;
import com.b209.hansotbab.user.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value="/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/fridge/{fridgeId}")
    public ResponseEntity<?> findAllReview(@PathVariable Long fridgeId, @AuthenticationPrincipal UserPrincipal user) {
        if (fridgeId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        UUID uuid = UUID.fromString(user.getUuid());
        List<ReviewResponseDTO> ReviewResponseDtoList = reviewService.findAllReview(fridgeId, uuid);
        return ResponseEntity.status(HttpStatus.OK).body(ReviewResponseDtoList);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<?> findReviewInfo(@PathVariable Long reviewId, @AuthenticationPrincipal UserPrincipal user){
        if (reviewId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        UUID uuid = UUID.fromString(user.getUuid());
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.findReviewInfo(reviewId, uuid));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<?> createReview(@PathVariable Long productId, @RequestBody ReviewRequestDTO reviewRequestDTO, @AuthenticationPrincipal UserPrincipal user) {
        if (productId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        UUID uuid = UUID.fromString(user.getUuid());
        reviewService.createReview(productId, reviewRequestDTO, uuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDTO reviewRequestDTO, @AuthenticationPrincipal UserPrincipal user){
        if (reviewId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        UUID uuid = UUID.fromString(user.getUuid());
        reviewService.updateReview(reviewId, reviewRequestDTO, uuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @DeleteMapping("/{reviewId})")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId, @AuthenticationPrincipal UserPrincipal user ){
        if (reviewId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        UUID uuid = UUID.fromString(user.getUuid());
        reviewService.deleteReview(reviewId, uuid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/likes") // 후기좋아요등록,취소
    public ResponseEntity<?> reviewLike(@RequestBody ReviewLikeRequestDTO reviewLikeRequestDTO){

        if(reviewLikeRequestDTO.getReviewId() == null || reviewLikeRequestDTO.getUuid() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Boolean isLike = reviewService.updateReviewLike(reviewLikeRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(isLike);

    }
    @GetMapping
    public ResponseEntity<?> findUserReview(@AuthenticationPrincipal UserPrincipal user) {
        UUID uuid = UUID.fromString(user.getUuid());
        List<ReviewResponseDTO> ReviewResponseDtoList = reviewService.findUserReview(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(ReviewResponseDtoList);
    }

    @PostMapping("/fridge/{fridgeId}")
    public ResponseEntity<?> createFridgeReview(@PathVariable Long fridgeId, @RequestBody ReviewRequestDTO reviewRequestDTO, @AuthenticationPrincipal UserPrincipal user) {
        if (fridgeId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        UUID uuid = UUID.fromString(user.getUuid());
        reviewService.createFridgeReview(fridgeId, reviewRequestDTO, uuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
