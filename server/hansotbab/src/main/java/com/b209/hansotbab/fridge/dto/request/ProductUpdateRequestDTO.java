package com.b209.hansotbab.fridge.dto.request;

import com.b209.hansotbab.fridge.entity.ProductCategory;
import com.b209.hansotbab.fridge.entity.ProductCondition;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductUpdateRequestDTO {

    private final String userUuid;
    private final String productName;
    private final String productCategory;
    private final Integer productAmount;
    private final List<ProductCondition> productConditions;
    private final String productMemo;

    @Builder
    public ProductUpdateRequestDTO(String userUuid, String productName, String productCategory, Integer productAmount, List<ProductCondition> productConditions, String productMemo) {
        this.userUuid = userUuid;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productAmount = productAmount;
        this.productConditions = productConditions;
        this.productMemo = productMemo;
    }
}
