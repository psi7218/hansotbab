package com.b209.hansotbab.review.dto.request;

import lombok.Getter;

@Getter
public class ReviewLikeRequestDTO {

    private final String uuid;
    private final Long fridgeId;
    private final Long reviewId;

    public ReviewLikeRequestDTO(String uuid, Long fridgeId, Long reviewId){
        this.uuid = uuid;
        this.fridgeId = fridgeId;
        this.reviewId = reviewId;
    }

}
