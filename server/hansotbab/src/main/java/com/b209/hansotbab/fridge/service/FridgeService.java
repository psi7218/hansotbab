package com.b209.hansotbab.fridge.service;

import com.b209.hansotbab.fridge.dto.response.FridgeListResponseDTO;
import com.b209.hansotbab.fridge.entity.Fridge;
import com.b209.hansotbab.fridge.entity.FridgeLike;
import com.b209.hansotbab.fridge.entity.Product;
import com.b209.hansotbab.fridge.repository.FridgeLikeRepository;
import com.b209.hansotbab.fridge.repository.FridgeRepository;
import com.b209.hansotbab.user.entity.User;
import com.b209.hansotbab.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class FridgeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FridgeRepository fridgeRepository;

    @Autowired
    private FridgeLikeRepository fridgeLikeRepository;

    public List<FridgeListResponseDTO> findAllFridge() {
        List<Fridge> fridges = fridgeRepository.findAll();

        List<FridgeListResponseDTO> fridgeListResponseDTOS = new ArrayList<>();
        for (Fridge fridge : fridges) {
            List<Product> products = fridge.getProducts();
            Long fridgeDonated = 0L;
            for (Product product : products) {
                if (!product.isDelete() && !product.isProductSoldout())
                    fridgeDonated++;
            }
            fridgeListResponseDTOS.add(FridgeListResponseDTO.fromEntity(fridge, fridgeDonated));
        }
        return fridgeListResponseDTOS;
    }
    public List<FridgeListResponseDTO> findAllFridgeLike(String uuid) {
        User user = userRepository.findByUuidAndIsDeleteFalse(UUID.fromString(uuid))
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다"));

        List<FridgeLike> fridgeLikes = fridgeLikeRepository.findByUserUuid(UUID.fromString(uuid));

        List<FridgeListResponseDTO> fridgeListResponseDTOS = new ArrayList<>();
        for (FridgeLike fridgeLike : fridgeLikes) {
            Fridge fridge = fridgeLike.getFridge();
            List<Product> products = fridge.getProducts();
            Long fridgeDonated = 0L;
            for (Product product : products) {
                if (!product.isDelete() && !product.isProductSoldout())
                    fridgeDonated++;
            }
            fridgeListResponseDTOS.add(FridgeListResponseDTO.fromEntity(fridge, fridgeDonated));
        }
        return fridgeListResponseDTOS;
     }

    public Boolean isFridgeLike(String uuid, Long fridgeId) {
        User user = userRepository.findByUuidAndIsDeleteFalse(UUID.fromString(uuid))
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다"));
        Optional<FridgeLike> fridgeLike = fridgeLikeRepository.findByUserUuidAndFridgeFridgeId(UUID.fromString(uuid), fridgeId);
        if (fridgeLike.isPresent()) {
            return true;
        } else {
            return false;
        }
    }
}
