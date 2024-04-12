package com.b209.hansotbab.review.entity;

import com.b209.hansotbab.global.entity.BaseEntity;
import com.b209.hansotbab.user.entity.User;
import com.b209.hansotbab.wishlist.entity.Wishlist;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Builder
    public ReviewLike(Long reviewLikeId, User user, Review reivew){
        this.reviewLikeId = reviewLikeId;
        this.user = user;
        this.review = reivew;
    }

}
