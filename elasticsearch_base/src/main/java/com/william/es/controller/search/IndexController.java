package com.william.es.controller.search;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @GetMapping("/createIndex")
    public ResponseEntity<CreateIndexResponse> createIndex() throws IOException {

        CreateIndexRequest createIndexRequest = new CreateIndexRequest("book_index_002");
        CreateIndexResponse createIndexResponse =  restHighLevelClient
                .indices().create(createIndexRequest, RequestOptions.DEFAULT);
        return new ResponseEntity<CreateIndexResponse>(createIndexResponse, HttpStatus.OK);
    }



}
