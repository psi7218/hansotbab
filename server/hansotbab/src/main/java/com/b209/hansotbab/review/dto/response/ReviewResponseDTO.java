package com.b209.hansotbab.review.dto.response;

import com.b209.hansotbab.fridge.entity.Fridge;
import com.b209.hansotbab.fridge.entity.Product;
import com.b209.hansotbab.review.entity.Review;
import com.b209.hansotbab.review.entity.ReviewLike;
import com.b209.hansotbab.user.entity.User;
import com.b209.hansotbab.wishlist.entity.WishlistLike;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public class ReviewResponseDTO {

    private final Long reviewId;
    private final Long fridgeId;
    private final Long productId;
    private final String userNickname;
    private final String reviewContent;
    private final Long reviewLikes; // 좋아요수
    private final boolean isLikeReview; // 로그인한 유저의 좋아요 여부
    private final String uuid; // 음식 기부자의 uuid
    private LocalDateTime regDate;

    @Builder
    public ReviewResponseDTO(Review review, UUID loginUuid) {
        this.reviewId = review.getReviewId();
        this.fridgeId = review.getFridge().getFridgeId();
        this.productId = review.getProduct() != null ? review.getProduct().getProductId() : null;
        this.userNickname = review.getUser().getNickname();
        this.reviewContent = review.getReviewContent();
        this.reviewLikes = (long)review.getReviewLikes().size();
        this.isLikeReview = isLikeReview(loginUuid, review.getReviewLikes());
        this.uuid = String.valueOf(review.getUser().getUuid()); // 음식 기부
        this.regDate = review.getCreatedDate();
    }

    /**
     * 로그인한 유저가 좋아요를 눌렀는지 판단하는 메소드입니다.
     * @param loginUuid 로그인한 유저의 uuid
     * @param reviewLikes 좋아요를 누른 유저 리스트
     * @return 로그인한 유저가 좋아요를 눌렀다면 true 반환, 그 외 false 반환
     */
    private boolean isLikeReview(UUID loginUuid, List<ReviewLike> reviewLikes) {
        if (Objects.isNull(loginUuid)) {
            return false;
        }
        for (ReviewLike reviewLike : reviewLikes) {
            if (reviewLike.getUser().getUuid().equals(loginUuid)) {
                return true;
            }
        }
        return false;
    }
}
