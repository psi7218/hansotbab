package com.b209.hansotbab.fridge.dto.response;

import com.b209.hansotbab.fridge.entity.Product;
import com.b209.hansotbab.fridge.entity.ProductCategory;
import com.b209.hansotbab.fridge.entity.ProductCondition;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ProductDetailResponseDTO {

    private final Long productId;
    private final Long fridgeId;
    private final String userUuid;
    private final String userNickname;
    private final String userProfileImage;
    private final String productName;
    private final ProductCategory productCategory;
    private final Integer productAmount;
    private final String productImageUrl;
    private final List<ProductCondition> productConditions;
    private final String productMemo;
    private final Integer productBringCount;
    private final String productStatus;
    private final LocalDateTime productRegDate;
    private final LocalDateTime productModDate;
    private final List<String> productBringUserUuids;

    @Builder
    public ProductDetailResponseDTO(Long productId, Long fridgeId, String userUuid, String userNickname, String userProfileImage, String productName, ProductCategory productCategory, Integer productAmount, String productImageUrl, List<ProductCondition> productConditions, String productMemo, Integer productBringCount, String productStatus, LocalDateTime productRegDate, LocalDateTime productModDate, List<String> productBringUserUuids) {
        this.productId = productId;
        this.fridgeId = fridgeId;
        this.userUuid = userUuid;
        this.userNickname = userNickname;
        this.userProfileImage = userProfileImage;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productAmount = productAmount;
        this.productImageUrl = productImageUrl;
        this.productConditions = productConditions;
        this.productMemo = productMemo;
        this.productBringCount = productBringCount;
        this.productStatus = productStatus;
        this.productRegDate = productRegDate;
        this.productModDate = productModDate;
        this.productBringUserUuids = productBringUserUuids;
    }

    public static ProductDetailResponseDTO fromEntity(Product product, String productStatus, List<String> productBringUserUuids){
        return ProductDetailResponseDTO.builder()
                .productId(product.getProductId())
                .fridgeId(product.getFridge().getFridgeId())
                .userUuid(String.valueOf(product.getUser().getUuid()))
                .userNickname(product.getUser().getNickname())
                .userProfileImage(product.getUser().getProfileImgUrl())
                .productName(product.getProductName())
                .productCategory(product.getProductCategory())
                .productAmount(product.getProductAmount())
                .productImageUrl(product.getProductImageUrl())
                .productConditions(product.getProductConditions())
                .productMemo(product.getProductMemo())
                .productBringCount(product.getProductBringCount())
                .productStatus(productStatus)
                .productRegDate(product.getCreatedDate())
                .productModDate(product.getModifiedDate())
                .productBringUserUuids(productBringUserUuids)
                .build();
    }
}
