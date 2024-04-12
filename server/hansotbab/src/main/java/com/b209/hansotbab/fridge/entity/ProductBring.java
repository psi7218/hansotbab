package com.b209.hansotbab.fridge.entity;

import com.b209.hansotbab.global.entity.BaseTimeEntity;
import com.b209.hansotbab.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductBring extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productBringId;

    @ManyToOne(fetch = LAZY )
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = LAZY )
    @JoinColumn(name = "user_uuid")
    private User user;

    @Builder
    public ProductBring(Long productBringId, Product product, User user) {
        this.productBringId = productBringId;
        this.product = product;
        this.user = user;
    }
}
