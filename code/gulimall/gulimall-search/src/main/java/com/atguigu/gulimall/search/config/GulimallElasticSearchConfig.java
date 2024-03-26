package com.atguigu.gulimall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.*;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @program: gulimall
 * @description: 给容器中注入一个RestHighLevelClient
 * @author: LZ
 * @create: 2022-11-27 20:49
 **/
@SpringBootConfiguration
public class GulimallElasticSearchConfig {

    public static final RequestOptions COMMON_OPTIONS;
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//        builder.addHeader("Authorization", "Bearer " + TOKEN);
//        builder.setHttpAsyncResponseConsumerFactory(
//                new HttpAsyncResponseConsumerFactory
//                        .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    RestHighLevelClient client() {
        RestClientBuilder builder = RestClient.builder(new HttpHost("139.196.224.147", 9200,
                "http"));
        return new RestHighLevelClient(builder);
    }

}
