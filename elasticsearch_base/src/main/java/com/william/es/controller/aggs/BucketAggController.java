package com.william.es.controller.aggs;


import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/bucket")
public class BucketAggController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 根据书的类型聚合
     * @return
     * @throws IOException
     */
    @GetMapping("/aggBookCategory")
    public ResponseEntity<SearchResponse> aggBookCategory() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("book_index_001");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.trackTotalHits(true);
        sourceBuilder.size(0);

        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("category_aggs")
                .field("category.keyword").size(10);
        //平行聚合，再加一个聚合
        AggregationBuilder priceAggregationBuilder = AggregationBuilders.terms("price_agg")
                .field("price").size(10);


        sourceBuilder.aggregation(aggregationBuilder);
//        sourceBuilder.aggregation(priceAggregationBuilder);

        request.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 嵌套聚合
     * @return
     * @throws IOException
     */
    @GetMapping("/nestedAgg")
    public ResponseEntity<SearchResponse> nestedAgg() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("book_index_001");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.trackTotalHits(true);
        sourceBuilder.size(0);

        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("category_agg")
                .field("category.keyword").size(10);

        AggregationBuilder priceAggregationBuilder = AggregationBuilders.terms("price_agg")
                .field("price").size(10);
        aggregationBuilder.subAggregation(priceAggregationBuilder);
        sourceBuilder.aggregation(aggregationBuilder);

        request.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
