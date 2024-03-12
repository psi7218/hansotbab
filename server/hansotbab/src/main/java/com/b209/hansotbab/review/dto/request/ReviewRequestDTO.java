package com.b209.hansotbab.review.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewRequestDTO {

    private Long userId;
    private Long productId;
    private String reviewContent;

    @Builder
    public ReviewRequestDTO(Long userId, Long productId, String reviewContent){
        this.userId = userId;
        this.productId = productId;
        this.reviewContent = reviewContent;
    }

}
