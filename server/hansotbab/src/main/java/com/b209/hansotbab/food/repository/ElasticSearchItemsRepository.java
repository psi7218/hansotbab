package com.b209.hansotbab.food.repository;

import com.b209.hansotbab.food.entity.ElasticSearchItems;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

public interface ElasticSearchItemsRepository extends ElasticsearchRepository<ElasticSearchItems, String> {
}
