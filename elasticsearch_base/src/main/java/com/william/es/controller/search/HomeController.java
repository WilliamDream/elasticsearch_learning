package com.william.es.controller.search;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
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
@RequestMapping("/es")
public class HomeController {


    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/healthy")
    public ResponseEntity<?> healthy() throws IOException {
        ClusterHealthRequest clusterHealthRequest = new ClusterHealthRequest();
        ClusterHealthResponse clusterHealthResponse = restHighLevelClient.cluster()
                .health(clusterHealthRequest, RequestOptions.DEFAULT);
        return new ResponseEntity<>(clusterHealthResponse, HttpStatus.OK);
    }



}
