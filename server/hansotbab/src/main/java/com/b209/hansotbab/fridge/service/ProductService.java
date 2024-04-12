package com.b209.hansotbab.fridge.service;

import com.b209.hansotbab.fridge.dto.request.ProductCreateRequestDTO;
import com.b209.hansotbab.fridge.dto.request.ProductUpdateRequestDTO;
import com.b209.hansotbab.fridge.dto.response.ProductDetailResponseDTO;
import com.b209.hansotbab.fridge.dto.response.ProductListResponseDTO;
import com.b209.hansotbab.fridge.entity.*;
import com.b209.hansotbab.fridge.repository.FridgeLikeRepository;
import com.b209.hansotbab.fridge.repository.FridgeRepository;
import com.b209.hansotbab.fridge.repository.ProductBringRepository;
import com.b209.hansotbab.fridge.repository.ProductRepository;
import com.b209.hansotbab.user.entity.User;
import com.b209.hansotbab.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Transactional
public class ProductService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FridgeRepository fridgeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductBringRepository productBringRepository;

    @Autowired
    private FridgeLikeRepository fridgeLikeRepository;

    @Autowired
    private S3Service s3Service;

    public void createProduct(String uuid, Long fridgeId, MultipartFile file, ProductCreateRequestDTO productCreateRequestDTO) {
        User user = userRepository.findByUuidAndIsDeleteFalse(UUID.fromString(uuid))
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다"));
        Fridge fridge = fridgeRepository.findByFridgeIdAndIsDeleteIsFalse(fridgeId)
                .orElseThrow(() -> new EntityNotFoundException("해당 냉장고가 존재하지 않습니다"));

        String[] s3 = s3Service.uploadFile(file);
        Product product = productCreateRequestDTO.createProduct(user, fridge, s3[0], s3[1]);

        productRepository.save(product);
    }

    public void updateProduct(Long fridgeId, Long productId, MultipartFile file, ProductUpdateRequestDTO productUpdateReqestDTO) {
        User user = userRepository.findByUuidAndIsDeleteFalse(UUID.fromString(productUpdateReqestDTO.getUserUuid()))
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다"));
        Fridge fridge = fridgeRepository.findByFridgeIdAndIsDeleteIsFalse(fridgeId)
                .orElseThrow(() -> new EntityNotFoundException("해당 냉장고가 존재하지 않습니다"));
        Product product = productRepository.findByProductIdAndIsDeleteIsFalse(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 품목이 존재하지 않습니다"));
        if (!Objects.equals(product.getFridge().getFridgeId(), fridge.getFridgeId())) {
            throw new EntityNotFoundException("해당 냉장고에 품목이 존재하지 않습니다");
        }

        String[] s3 = s3Service.uploadNewFile(file, product);

        product.updateProduct(productUpdateReqestDTO, s3[0], s3[1]);
    }


    public void deleteProduct(Long fridgeId, Long productId) {
        Fridge fridge = fridgeRepository.findByFridgeIdAndIsDeleteIsFalse(fridgeId)
                .orElseThrow(() -> new EntityNotFoundException("해당 냉장고가 존재하지 않습니다"));
        Product product = productRepository.findByProductIdAndIsDeleteIsFalse(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 품목이 존재하지 않습니다"));
        if (product.getFridge().getFridgeId() != fridge.getFridgeId()) {
            throw new EntityNotFoundException("해당 냉장고에 품목이 존재하지 않습니다");
        }
        product.deleteProduct();
    }

    public ProductDetailResponseDTO findProduct(Long fridgeId, Long productId) {
        Fridge fridge = fridgeRepository.findByFridgeIdAndIsDeleteIsFalse(fridgeId)
                .orElseThrow(() -> new EntityNotFoundException("해당 냉장고가 존재하지 않습니다"));
        Product product = productRepository.findByProductIdAndIsDeleteIsFalse(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 품목이 존재하지 않습니다"));
        if (product.getFridge().getFridgeId() != fridge.getFridgeId()) {
            throw new EntityNotFoundException("해당 냉장고에 품목이 존재하지 않습니다");
        }
        List<ProductBring> productBrings = productBringRepository.findAllByProductProductId(productId);
        List<String> productBringUserUuids = new ArrayList<>();
        for(ProductBring productBring: productBrings){
            productBringUserUuids.add(String.valueOf(productBring.getUser().getUuid()));
        }
        return ProductDetailResponseDTO.fromEntity(product, calculateStatus(product), productBringUserUuids);
    }

    public List<ProductListResponseDTO> findProductRecent() {
        List<Product> products = productRepository.findTop7ByIsDeleteIsFalseOrderByCreatedDateDesc();

        List<ProductListResponseDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            productDTOS.add(ProductListResponseDTO.fromEntity(product, calculateStatus(product)));
        }
        return productDTOS;
    }


    public void soldoutProduct(String uuid, Long fridgeId, Long productId) {
        Fridge fridge = fridgeRepository.findByFridgeIdAndIsDeleteIsFalse(fridgeId)
                .orElseThrow(() -> new EntityNotFoundException("해당 냉장고가 존재하지 않습니다"));
        // 관리자가 아닌경우
        if(!fridge.getFridgeAdmins().contains(uuid)){
            throw new IllegalStateException("해당 냉장고 관리자가 아닙니다");
        }
        Product product = productRepository.findByProductIdAndIsDeleteIsFalse(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 품목이 존재하지 않습니다"));
        if (product.getFridge().getFridgeId() != fridge.getFridgeId()) {
            throw new EntityNotFoundException("해당 냉장고에 품목이 존재하지 않습니다");
        }

        product.soldoutProduct();
    }


    public void isSoldoutProduct(Long fridgeId, Long productId) {
        Fridge fridge = fridgeRepository.findByFridgeIdAndIsDeleteIsFalse(fridgeId)
                .orElseThrow(() -> new EntityNotFoundException("해당 냉장고가 존재하지 않습니다"));
        Product product = productRepository.findByProductIdAndIsDeleteIsFalse(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 품목이 존재하지 않습니다"));
        if (product.getFridge().getFridgeId() != fridge.getFridgeId()) {
            throw new EntityNotFoundException("해당 냉장고에 품목이 존재하지 않습니다");
        }

        product.isSoldoutProduct();
    }

    public List<ProductListResponseDTO> findAllProduct(Long fridgeId) {
        Fridge fridge = fridgeRepository.findByFridgeIdAndIsDeleteIsFalse(fridgeId)
                .orElseThrow(() -> new EntityNotFoundException("해당 냉장고가 존재하지 않습니다"));
        List<Product> products = productRepository.findAllByFridgeFridgeIdAndIsDeleteIsFalseOrderByCreatedDateDesc(fridgeId);

        List<ProductListResponseDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            productDTOS.add(ProductListResponseDTO.fromEntity(product, calculateStatus(product)));
        }
        return productDTOS;
    }

    public List<ProductListResponseDTO> findAllProductCategory(Long fridgeId, ProductCategory category) {
        Fridge fridge = fridgeRepository.findByFridgeIdAndIsDeleteIsFalse(fridgeId)
                .orElseThrow(() -> new EntityNotFoundException("해당 냉장고가 존재하지 않습니다"));
        List<Product> products = productRepository.findAllByFridgeFridgeIdAndProductCategoryEqualsAndIsDeleteIsFalseOrderByCreatedDateDesc(fridgeId, category);

        List<ProductListResponseDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            productDTOS.add(ProductListResponseDTO.fromEntity(product, calculateStatus(product)));
        }
        return productDTOS;
    }

    public void pickupProduct(String uuid, Long fridgeId, Long productId) {
        User user = userRepository.findByUuidAndIsDeleteFalse(UUID.fromString(uuid))
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다"));
        Fridge fridge = fridgeRepository.findByFridgeIdAndIsDeleteIsFalse(fridgeId)
                .orElseThrow(() -> new EntityNotFoundException("해당 냉장고가 존재하지 않습니다"));
        Product product = productRepository.findByProductIdAndIsDeleteIsFalse(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 품목이 존재하지 않습니다"));
        if (product.getFridge().getFridgeId() != fridge.getFridgeId()) {
            throw new EntityNotFoundException("해당 냉장고에 품목이 존재하지 않습니다");
        }

        Optional<ProductBring> productBring = productBringRepository.findByUserUuidAndProductProductId(UUID.fromString(uuid), productId);
        if (productBring.isPresent()) {
            throw new IllegalStateException("이미 음식을 가져갔습니다");
        } else {
            ProductBring createProductBring = ProductBring.builder()
                    .product(product)
                    .user(user)
                    .build();
            productBringRepository.save(createProductBring);
            product.addBringCount();
        }
    }

    public void updateFridgeLike(String uuid, Long fridgeId) {
        User user = userRepository.findByUuidAndIsDeleteFalse(UUID.fromString(uuid))
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다"));
        Fridge fridge = fridgeRepository.findByFridgeIdAndIsDeleteIsFalse(fridgeId)
                .orElseThrow(() -> new EntityNotFoundException("해당 냉장고가 존재하지 않습니다"));

        Optional<FridgeLike> fridgeLike = fridgeLikeRepository.findByUserUuidAndFridgeFridgeId(UUID.fromString(uuid), fridgeId);
        if (fridgeLike.isPresent()) {
            fridgeLikeRepository.delete(fridgeLike.get());
        } else {
            FridgeLike createFridgeLike = FridgeLike.builder()
                    .fridge(fridge)
                    .user(user)
                    .build();
            fridgeLikeRepository.save(createFridgeLike);
        }
    }

    public List<ProductListResponseDTO> findAllDonate(String uuid) {
        User user = userRepository.findByUuidAndIsDeleteFalse(UUID.fromString(uuid))
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다"));
        List<Product> products = productRepository.findAllByUserUuidAndIsDeleteIsFalseOrderByCreatedDateDesc(UUID.fromString(uuid));

        List<ProductListResponseDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            productDTOS.add(ProductListResponseDTO.fromEntity(product, calculateStatus(product)));
        }
        return productDTOS;
    }

    public List<ProductListResponseDTO> findAllReceive(String uuid) {
        User user = userRepository.findByUuidAndIsDeleteFalse(UUID.fromString(uuid))
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다"));
        List<ProductBring> productBrings = productBringRepository.findAllByUserUuid(UUID.fromString(uuid));

        List<ProductListResponseDTO> productListResponseDTOS = new ArrayList<>();
        for (ProductBring productBring : productBrings) {
            Product product = productBring.getProduct();
            productListResponseDTOS.add(ProductListResponseDTO.fromEntity(product,  calculateStatus(product)));
        }
        return productListResponseDTOS;
    }

    public String calculateStatus(Product product) {
        /**
         * 계산 로직(임시)
         * 1. SOLDOUT : 관리자가 soldout으로 상태 변경
         * 2. INSUFFICIENT : 가져간 인원 > 기부 수량/2
         *               또는 재고여부=false
         * 3. ENOUGH : 이외
         */
        if(product.isProductSoldout()) return "나눔 완료 :O";
        if(product.getProductBringCount() > product.getProductAmount()/2 || product.isProductIsSoldout())
            return "남아있을 가능성이 낮아요 :(";
        return "남아있을 가능성이 높아요 :)";
    }
}
