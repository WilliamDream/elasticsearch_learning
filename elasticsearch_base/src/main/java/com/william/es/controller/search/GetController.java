package com.william.es.controller.search;


import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/esget")
public class GetController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/{docId}")
    public ResponseEntity<GetResponse> getById(@PathVariable String docId) throws IOException {
        GetRequest request = new GetRequest("book_index_002",docId);
        FetchSourceContext context = new FetchSourceContext(false);
        request.fetchSourceContext(context);
        request.version(2);//数据版本

        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);

        return new ResponseEntity<>(getResponse, HttpStatus.OK);
    }


}
