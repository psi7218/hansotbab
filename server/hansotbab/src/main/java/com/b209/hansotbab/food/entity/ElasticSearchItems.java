package com.b209.hansotbab.food.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.lang.annotation.Documented;

@Document(indexName = "auto_test")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ElasticSearchItems {
    @Id
    private String id;

    private String code;

    private String name;

    private String cat;

    private String cat1;

    private String cat2;
}
