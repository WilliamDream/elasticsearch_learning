package com.william.es.controller.search;


import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/doc")
public class DocumentController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/insert")
    public ResponseEntity<IndexResponse> insertDoc() throws IOException {
        IndexRequest indexRequest = new IndexRequest("book_index_002");
        indexRequest.source("");
        IndexResponse indexResponse = restHighLevelClient
                .index(indexRequest, RequestOptions.DEFAULT);

        return new ResponseEntity<>(indexResponse, HttpStatus.OK);
    }


}
