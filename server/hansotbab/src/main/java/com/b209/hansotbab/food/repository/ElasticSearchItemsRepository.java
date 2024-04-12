package com.b209.hansotbab.food.repository;

import com.b209.hansotbab.food.entity.ElasticSearchItems;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ElasticSearchItemsRepository extends ElasticsearchRepository<ElasticSearchItems, String> {

    @Query("{\n" +
            "    \"match\": {\n" +
            "      \"name\": {\n" +
            "        \"query\": \"?0\",\n" +
            "        \"fuzziness\": 1\n" +
            "      }\n" +
            "    }\n" +
            "  }")
    List<ElasticSearchItems> findByName(String keyword, Pageable pageable);
}
