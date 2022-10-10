package com.atguigu.gulimall.coupon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 *  命名空间: 配置隔离
 *  1）默认 public (保留空间)；默认新增的所有配置都在public空间
 *     1 、开发 测试 生产 利用命名空间做环境隔离
 *        spring.cloud.nacos.config.namespace=a1bb3cc7-f2b4-4c72-8385-6ff83349c6e7
 *     2、 每一个微服务之间互相隔离配置
 *  2)配置集 : 所有的配置集合
 *  3）配置集ID:类似文件名   Data ID
 *  4)配置分组：默认是default-group
 *  每个微服务创建自己的命名空间，使用配置分组区分环境 dev test pro 环境
 *
 *
 */

@EnableDiscoveryClient
@SpringBootApplication
public class GulimallCouponApplication {

	public static void main(String[] args) {
		SpringApplication.run(GulimallCouponApplication.class, args);
	}

}
