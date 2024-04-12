package com.b209.hansotbab.fridge.service;

import com.b209.hansotbab.fridge.dto.request.ProductCreateRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class WebClientService {

    public void post(String uuid, String fridgeId, MultipartFile image, ProductCreateRequestDTO product) throws IOException {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("user", uuid);
        bodyMap.put("fridge", fridgeId);
        bodyMap.put("name", product.getProductName());
        bodyMap.put("category", product.getProductCategory());
        bodyMap.put("image", image.getName());
        bodyMap.put("conditions", product.getProductConditions().toString());
        bodyMap.put("memo", product.getProductMemo());

        // webClient 기본 설정
        WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("http://j10b209a.p.ssafy.io:8000")
                        .build();

        // api 요청
        BodyInserter<?, ? super ClientHttpRequest> BodyInserter;
        Map<String, Object> response =
                webClient
                        .post()
                        .uri("/hdfs/products")
                        //.accept(MediaType.APPLICATION_JSON)
                        //.contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(bodyMap)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();

        // 결과 확인
        log.info(response.toString());
    }
}
