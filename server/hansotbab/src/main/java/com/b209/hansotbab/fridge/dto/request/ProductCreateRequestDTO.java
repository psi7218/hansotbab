package com.b209.hansotbab.fridge.dto.request;

import com.b209.hansotbab.fridge.entity.Fridge;
import com.b209.hansotbab.fridge.entity.Product;
import com.b209.hansotbab.fridge.entity.ProductCategory;
import com.b209.hansotbab.fridge.entity.ProductCondition;
import com.b209.hansotbab.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductCreateRequestDTO {

    private final String productName;
    private final String productCategory;
    private final Integer productAmount;
    private final List<ProductCondition> productConditions;
    private final String productMemo;

    @Builder
    public ProductCreateRequestDTO(String productName, String productCategory, Integer productAmount, List<ProductCondition> productConditions, String productMemo) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productAmount = productAmount;
        this.productConditions = productConditions;
        this.productMemo = productMemo;
    }


    public ProductCategory fixCategory(String oldCategory){
        return ProductCategory.valueOf(oldCategory.replace('/','_'));
    }
    public Product createProduct(User user, Fridge fridge, String s3name, String s3url) {
        System.out.println(fixCategory(this.productCategory));
        return Product.builder()
                .user(user)
                .fridge(fridge)
                .productName(this.productName)
                .productCategory(fixCategory(this.productCategory))
                .productAmount(this.productAmount)
                .productImageName(s3name)
                .productImageUrl(s3url)
                .productConditions(this.productConditions)
                .productMemo(this.productMemo)
                .productBringCount(0)
                .productIsSoldout(false)
                .productSoldout(false)
                .build();
    }
}
