package com.atguigu.gulimall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RedissonConfig {

    @Bean(destroyMethod = "shutdown",name = "redisson")
    public RedissonClient redisson() throws IOException{
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://139.196.224.147:6379").setPassword("fes945@lizheng");
        return Redisson.create(config);
    }


}
