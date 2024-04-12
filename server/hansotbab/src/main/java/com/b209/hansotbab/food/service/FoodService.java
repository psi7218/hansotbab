package com.b209.hansotbab.food.service;

import com.b209.hansotbab.food.entity.ElasticSearchItems;
import com.b209.hansotbab.food.repository.ElasticSearchItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    private ElasticSearchItemsRepository elasticSearchItemsRepository;

    public List<ElasticSearchItems> searchFood(String keyword, Pageable pageable) {
        return elasticSearchItemsRepository.findByName(keyword, pageable);
    }


}
