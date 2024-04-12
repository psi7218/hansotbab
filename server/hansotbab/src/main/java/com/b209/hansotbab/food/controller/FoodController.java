package com.b209.hansotbab.food.controller;

import com.b209.hansotbab.food.JsonDataUtils;
import com.b209.hansotbab.food.entity.ElasticSearchItems;
import com.b209.hansotbab.food.service.FoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private JsonDataUtils jsonDataUtils;

    @GetMapping("")
    public ResponseEntity<Void> createJsonFile() {
        jsonDataUtils.getJsonData();
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ElasticSearchItems>> searchFood(
            @PageableDefault(size = 5) Pageable pageable,
            @RequestParam(value = "keyword") String keyword
    ){
        return ResponseEntity.ok(foodService.searchFood(keyword, pageable));
    }
}
