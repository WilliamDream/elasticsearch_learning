package com.william.es.controller.search;


import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /*@GetMapping("/")
    public ResponseEntity<> method() throws IOException {


        return new ResponseEntity<>(, HttpStatus.OK);
    }*/


}
