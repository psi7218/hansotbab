package com.b209.hansotbab.review.dto.request;

import com.b209.hansotbab.fridge.entity.Fridge;
import com.b209.hansotbab.fridge.entity.Product;
import com.b209.hansotbab.review.entity.Review;
import com.b209.hansotbab.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDTO {

    private String reviewContent;

    @Builder
    public ReviewRequestDTO(String reviewContent){
        this.reviewContent = reviewContent;
    }

    public Review toReview(Product product, User user, Fridge fridge){
        return Review.builder()
                .user(user)
                .reviewContent(this.reviewContent)
                .product(product)
                .fridge(fridge)
                .build();
    }

}
