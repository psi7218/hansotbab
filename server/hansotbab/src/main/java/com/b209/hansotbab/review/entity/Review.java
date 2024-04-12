package com.b209.hansotbab.review.entity;

import com.b209.hansotbab.fridge.entity.Fridge;
import com.b209.hansotbab.fridge.entity.Product;
import com.b209.hansotbab.global.entity.BaseEntity;
import com.b209.hansotbab.review.dto.request.ReviewRequestDTO;
import com.b209.hansotbab.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false, length = 20)
    private String reviewContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fridge_id", nullable = false)
    private Fridge fridge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewLike> reviewLikes = new ArrayList<>();

    @Builder
    public Review(Long reviewId, String reviewContent, User user, Fridge fridge, Product product) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.user = user;
        this.fridge = fridge;
        this.product = product;
    }

    public void update(ReviewRequestDTO reviewRequestDTO) {
        this.reviewContent = reviewRequestDTO.getReviewContent();
    }

    public void updateDelete() {
        this.isDelete = true;
    }
}
