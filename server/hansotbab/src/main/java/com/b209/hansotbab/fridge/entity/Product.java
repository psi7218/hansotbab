package com.b209.hansotbab.fridge.entity;

import com.b209.hansotbab.fridge.dto.request.ProductUpdateRequestDTO;
import com.b209.hansotbab.global.entity.BaseEntity;
import com.b209.hansotbab.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne(fetch = LAZY )
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY )
    @JoinColumn(name = "fridge_id", nullable = false)
    private Fridge fridge;

    @Column(nullable = false)
    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory productCategory;

    @Column(nullable = false)
    private Integer productAmount;

    @Column(nullable = false)
    private String productImageName;

    @Column(nullable = false)
    private String productImageUrl;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private List<ProductCondition> productConditions;

    @Column(nullable = false)
    private String productMemo;

    @Column(nullable = false)
    private Integer productBringCount;

    @Column(nullable = false)
    private boolean productIsSoldout;

    @Column(nullable = false)
    private boolean productSoldout;


    @Builder
    public Product(Long productId, User user, Fridge fridge, String productName, ProductCategory productCategory, Integer productAmount, String productImageName, String productImageUrl, List<ProductCondition> productConditions, String productMemo, Integer productBringCount, boolean productIsSoldout, boolean productSoldout) {
        this.productId = productId;
        this.user = user;
        this.fridge = fridge;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productAmount = productAmount;
        this.productImageName = productImageName;
        this.productImageUrl = productImageUrl;
        this.productConditions = productConditions;
        this.productMemo = productMemo;
        this.productBringCount = productBringCount;
        this.productIsSoldout = productIsSoldout;
        this.productSoldout = productSoldout;
    }


    public ProductCategory fixCategory(String oldCategory){
        return ProductCategory.valueOf(oldCategory.replace('/','_'));
    }

    public void updateProduct(ProductUpdateRequestDTO productUpdateRequestDTO, String s3name, String s3url) {
        this.productName = productUpdateRequestDTO.getProductName();
        this.productCategory = fixCategory(String.valueOf(productUpdateRequestDTO.getProductCategory()));
        this.productAmount = productUpdateRequestDTO.getProductAmount();
        this.productImageName = s3name;
        this.productImageUrl = s3url;
        this.productConditions = productUpdateRequestDTO.getProductConditions();
        this.productMemo = productUpdateRequestDTO.getProductMemo();
    }

    public void deleteProduct(){
        this.isDelete = true;
    }

    public void soldoutProduct() {
        this.productSoldout = true;
    }

    public void isSoldoutProduct() {
        this.productIsSoldout = true;
    }

    public void addBringCount() {
        this.productBringCount++;
    }

}
