package com.b209.hansotbab.fridge.controller;

import com.b209.hansotbab.fridge.dto.request.ProductCreateRequestDTO;
import com.b209.hansotbab.fridge.dto.request.ProductUpdateRequestDTO;
import com.b209.hansotbab.fridge.dto.response.FridgeListResponseDTO;
import com.b209.hansotbab.fridge.dto.response.ProductDetailResponseDTO;
import com.b209.hansotbab.fridge.dto.response.ProductListResponseDTO;
import com.b209.hansotbab.fridge.entity.ProductCategory;
import com.b209.hansotbab.fridge.entity.ProductCondition;
import com.b209.hansotbab.fridge.service.FridgeService;
import com.b209.hansotbab.fridge.service.ProductService;
import com.b209.hansotbab.fridge.service.WebClientService;
import com.b209.hansotbab.user.entity.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/fridge")
public class FridgeController {

    private final FridgeService fridgeService;
    private final ProductService productService;
    private final WebClientService webClientService;

    public FridgeController(FridgeService fridgeService, ProductService productService,
                            WebClientService webClientService) {
        this.fridgeService = fridgeService;
        this.productService = productService;
        this.webClientService = webClientService;
    }

    @GetMapping("")
    public ResponseEntity<List<FridgeListResponseDTO>> findAllFridge() {
        // 로그인하지 않은 사람의 정보 불러오기
        return ResponseEntity.ok(fridgeService.findAllFridge());
    }

    @GetMapping("/{fridgeId}")
    public ResponseEntity<Boolean> isFridgeLike(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long fridgeId
    ) {
        // 로그인하지 않은 사람의 정보 불러오기
        if (userPrincipal.getUuid().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(fridgeService.isFridgeLike(userPrincipal.getUuid(), fridgeId));
    }

    @GetMapping("/likes")
    public ResponseEntity<List<FridgeListResponseDTO>> findAllFridgeLike(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        // 로그인하지 않은 사람의 정보 불러오기
        if (userPrincipal.getUuid().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(fridgeService.findAllFridgeLike(userPrincipal.getUuid()));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<ProductListResponseDTO>> findProductRecent() {
        return ResponseEntity.ok(productService.findProductRecent());
    }

    @GetMapping("/{fridgeId}/products")
    public ResponseEntity<List<ProductListResponseDTO>> findAllProduct(@PathVariable Long fridgeId) {
        if (fridgeId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(productService.findAllProduct(fridgeId));
    }

    @GetMapping("/{fridgeId}/{category}/products")
    public ResponseEntity<List<ProductListResponseDTO>> findAllProductCategory(
            @PathVariable Long fridgeId,
            @PathVariable ProductCategory category
    ) {
        if (fridgeId == null || category == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(productService.findAllProductCategory(fridgeId, category));
    }

    @GetMapping("/{fridgeId}/{productId}")
    public ResponseEntity<ProductDetailResponseDTO> findProduct(
            @PathVariable Long fridgeId,
            @PathVariable Long productId
    ) {
        if (fridgeId == null || productId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        productService.findProduct(fridgeId, productId);
        return ResponseEntity.ok(productService.findProduct(fridgeId, productId));
    }

    @PostMapping("/{fridgeId}/likes")
    public ResponseEntity<Void> updateFridgeLike(
            @PathVariable Long fridgeId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        if (fridgeId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        // 로그인하지 않은 사람이 좋아요 누를 때
        if (userPrincipal.getUuid().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        productService.updateFridgeLike(userPrincipal.getUuid(), fridgeId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{fridgeId}/register")
    public ResponseEntity<Void> createProduct(
            @PathVariable Long fridgeId,
            @RequestParam(value = "productName") String productName,
            @RequestParam(value = "productCategory") String productCategory,
            @RequestParam(value = "productAmount") String productAmount,
            @RequestParam(value = "productConditions") List<ProductCondition> productConditions,
            @RequestParam(value = "productMemo") String productMemo,
            @RequestParam(value = "file") MultipartFile file,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) throws IOException {
        if (fridgeId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // 로그인하지 않은 사람이 작성할 때
        if (userPrincipal.getUuid().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ProductCreateRequestDTO productCreateRequestDTO = ProductCreateRequestDTO.builder()
                .productName(productName)
                .productCategory(productCategory)
                .productAmount(Integer.parseInt(productAmount))
                .productConditions(productConditions)
                .productMemo(productMemo)
                .build();

        productService.createProduct(userPrincipal.getUuid(), fridgeId, file, productCreateRequestDTO);
        webClientService.post(userPrincipal.getUuid(), fridgeId.toString(), file, productCreateRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/{fridgeId}/{productId}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long fridgeId,
            @PathVariable Long productId,
            @RequestParam(value = "userUuid") String userUuid,
            @RequestParam(value = "productName") String productName,
            @RequestParam(value = "productCategory") String productCategory,
            @RequestParam(value = "productAmount") String productAmount,
            @RequestParam(value = "productConditions") List<ProductCondition> productConditions,
            @RequestParam(value = "productMemo") String productMemo,
            @RequestParam(value = "file") MultipartFile file,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        if (fridgeId == null || productId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // 로그인하지 않은 사람이 작성할 때
        if (userPrincipal.getUuid().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 로그인한 유저와 글 수정하려는 사용자가 다를 경우
        if (!userPrincipal.getUuid().equals(userUuid)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        ProductUpdateRequestDTO productUpdateRequestDTO = ProductUpdateRequestDTO.builder()
                .userUuid(userUuid)
                .productName(productName)
                .productCategory(productCategory)
                .productAmount(Integer.parseInt(productAmount))
                .productConditions(productConditions)
                .productMemo(productMemo)
                .build();
        productService.updateProduct(fridgeId, productId, file, productUpdateRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{fridgeId}/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long fridgeId,
            @PathVariable Long productId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        // 냉장고번호랑, 위시리스트번호가 유효값이 아닐 때
        if (fridgeId == null | productId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // 로그인 하지 않은 사람이 삭제할 때
        if (userPrincipal.getUuid().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        productService.deleteProduct(fridgeId, productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{fridgeId}/{productId}/sold-out")
    public ResponseEntity<Void> productSoldOut(
            @PathVariable Long fridgeId,
            @PathVariable Long productId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        if (fridgeId == null || productId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // 로그인하지 않은 사람이 시도할 때
        if (userPrincipal.getUuid().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        productService.soldoutProduct(userPrincipal.getUuid(), fridgeId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{fridgeId}/{productId}/pickup")
    public ResponseEntity<Void> pickupProduct(
            @PathVariable Long fridgeId,
            @PathVariable Long productId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        if (fridgeId == null || productId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        // 로그인하지 않은 사람이 작성할 때
        if (userPrincipal.getUuid().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        productService.pickupProduct(userPrincipal.getUuid(), fridgeId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{fridgeId}/{productId}/is-soldout")
    public ResponseEntity<Void> productIsSoldout(
            @PathVariable Long fridgeId,
            @PathVariable Long productId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        if (fridgeId == null || productId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        // 로그인하지 않은 사람이 누를때
        if (userPrincipal.getUuid().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        productService.isSoldoutProduct(fridgeId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/donate")
    public ResponseEntity<List<ProductListResponseDTO>> findAllDonate(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        // 로그인하지 않은 사용자의 정보 불러올때
        if (userPrincipal.getUuid().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(productService.findAllDonate(userPrincipal.getUuid()));
    }

    @GetMapping("/receive")
    public ResponseEntity<List<ProductListResponseDTO>> findAllReceive(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        // 로그인하지 않은 사용자의 정보 불러올때
        if (userPrincipal.getUuid().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(productService.findAllReceive(userPrincipal.getUuid()));
    }

}
