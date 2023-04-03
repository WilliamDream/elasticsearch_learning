package com.william.es.controller.search;


import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
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
@RequestMapping("/querystring")
public class QueryStringController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/get")
    public ResponseEntity<SearchResponse> qs() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("book_index_001");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.trackTotalHits(true);
        sourceBuilder.from(0);
        sourceBuilder.size(20);
        //输入查询内容 格式  字段:内容
        QueryBuilder queryBuilder = new QueryStringQueryBuilder("");
        sourceBuilder.query(queryBuilder);
        sourceBuilder.sort("", SortOrder.ASC);
        request.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
