package com.b209.hansotbab.fridge.dto.response;

import com.b209.hansotbab.fridge.entity.Product;
import com.b209.hansotbab.fridge.entity.ProductCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductListResponseDTO {

    private final Long productId;
    private final Long fridgeId;
    private final String productName;
    private final ProductCategory productCategory;
    private final String productImageUrl;
    private final String productStatus;
    private final LocalDateTime productRegDate;
    private final LocalDateTime productModDate;

    @Builder
    public ProductListResponseDTO(Long productId, Long fridgeId, String productName, ProductCategory productCategory, String productImageUrl, String productStatus, LocalDateTime productRegDate, LocalDateTime productModDate) {
        this.productId = productId;
        this.fridgeId = fridgeId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productImageUrl = productImageUrl;
        this.productStatus = productStatus;
        this.productRegDate = productRegDate;
        this.productModDate = productModDate;
    }

    public static ProductListResponseDTO fromEntity(Product product, String productStatus) {
        return ProductListResponseDTO.builder()
                .productId(product.getProductId())
                .fridgeId(product.getFridge().getFridgeId())
                .productName(product.getProductName())
                .productCategory(product.getProductCategory())
                .productImageUrl(product.getProductImageUrl())
                .productStatus(productStatus)
                .productRegDate(product.getCreatedDate())
                .productModDate(product.getModifiedDate())
                .build();
    }
}
