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
@RequestMapping("/metrics")
public class PipelineAggController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/aggBookPrice")
    public ResponseEntity<SearchResponse> aggBookPrice() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("book_index_001");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.trackTotalHits(true);
        sourceBuilder.from(0);
        sourceBuilder.size(20);
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("book_category_agg")
                .field("category").size(100);
        AggregationBuilder aggregationBuilder1 = AggregationBuilders.terms("book_price")
                .field("price").size(100);
        AggregationBuilder aggregationBuilder2 = AggregationBuilders.avg("").field("");

//        AggregationBuilder maxPriceAgg = AggregationBuilders.max("price");
        sourceBuilder.aggregation(aggregationBuilder);

        request.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
