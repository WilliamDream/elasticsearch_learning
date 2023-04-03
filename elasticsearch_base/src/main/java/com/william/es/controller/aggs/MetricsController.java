package com.william.es.controller.aggs;


import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.SignificantTermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.StatsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 统计书的最高价，最低价，平均加等
     * @return
     * @throws IOException
     */
    @GetMapping("/aggBookPrice")
    public ResponseEntity<SearchResponse> aggBookPrice() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("book_index_001");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.trackTotalHits(true);
        sourceBuilder.from(0);
        sourceBuilder.size(20);
        AggregationBuilder aggregationBuilder = AggregationBuilders.stats("book_price_stats")
                .field("price");
//        AggregationBuilder maxPriceAgg = AggregationBuilders.max("price");
        sourceBuilder.aggregation(aggregationBuilder);

        request.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/aggBookPriceWithCondition")
    public ResponseEntity<SearchResponse> aggBookPriceWithCondition() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("book_index_001");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.trackTotalHits(true);
        sourceBuilder.from(0);
        sourceBuilder.size(20);
        AggregationBuilder aggregationBuilder = AggregationBuilders.stats("book_price_stats")
                .field("price");
        sourceBuilder.aggregation(aggregationBuilder);
        QueryBuilder queryBuilder = new TermQueryBuilder("category","technology");
        sourceBuilder.query(queryBuilder);

        request.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





}
