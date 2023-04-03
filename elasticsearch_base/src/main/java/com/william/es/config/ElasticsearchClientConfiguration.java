package com.william.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
public class ElasticsearchClientConfiguration {

    /**
     * 建立连接超时时间
     */
    public static int CONNECT_TIMEOUT_MILLIS            = 1000;
    /**
     * 数据传输过程中的超时时间
     */
    public static int SOCKET_TIMEOUT_MILLIS             = 30000;
    /**
     * 从连接池获取连接的超时时间
     */
    public static int CONNECTION_REQUEST_TIMEOUT_MILLIS = 500;

    /**
     * 路由节点的最大连接数
     */
    public static int MAX_CONN_PER_ROUTE = 10;
    /**
     * client最大连接数量
     */
    public static int MAX_CONN_TOTAL     = 30;

    @Value("${elasticsearch.cluster.nodes:}")
    private String clusterNodes;

    private RestHighLevelClient restHighLevelClient;

    /**
     * 价值ES集群
     * @return
     */
    private HttpHost[] loadHttpHosts() {
        String[] clusterNodesArray = clusterNodes.split(",");
        HttpHost[] httpHosts = new HttpHost[clusterNodesArray.length];
        for (int i = 0; i < clusterNodesArray.length; i++) {
            String clusterNode = clusterNodesArray[i];
            String[] hostAndPort = clusterNode.split(":");
            httpHosts[i] = new HttpHost(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
        }
        return httpHosts;
    }

    /**
     * 初始化rest客户端对象
     * @return
     */
    @Bean
    public RestHighLevelClient restClient() {
        // 创建restClient的构造器
        RestClientBuilder restClientBuilder = RestClient.builder(loadHttpHosts());
        // 设置连接超时时间等参数
        setConnectTimeOutConfig(restClientBuilder);
        setConnectConfig(restClientBuilder);
        restHighLevelClient = new RestHighLevelClient(restClientBuilder);
        return restHighLevelClient;
    }

    private void setConnectTimeOutConfig(RestClientBuilder restClientBuilder) {
        restClientBuilder.setRequestConfigCallback(requestClientBuilder -> {
            requestClientBuilder.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
            requestClientBuilder.setSocketTimeout(SOCKET_TIMEOUT_MILLIS);
            requestClientBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT_MILLIS);
            return requestClientBuilder;
        });
    }

    private void setConnectConfig(RestClientBuilder restClientBuilder) {
        restClientBuilder.setHttpClientConfigCallback(httpClientBuilder->{
            httpClientBuilder.setMaxConnTotal(MAX_CONN_TOTAL);
            httpClientBuilder.setMaxConnPerRoute(MAX_CONN_PER_ROUTE);
            return httpClientBuilder;
        });
    }

    /**
     * 关闭客户端
     */
    @PreDestroy
    public void close() {
        if (restHighLevelClient != null) {
            try {
                restHighLevelClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
