package com.b209.hansotbab.review.dto.request;

import lombok.Getter;

@Getter
public class ReviewLikeRequestDTO {

    private final Long userId;
    private final Long fridgeId;
    private final Long reviewId;

    public ReviewLikeRequestDTO(Long userId, Long fridgeId, Long reviewId){
        this.userId = userId;
        this.fridgeId = fridgeId;
        this.reviewId = reviewId;
    }

}
