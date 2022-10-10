package com.atguigu.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 1 整合mybatis-plus
 * 导入依赖
 * <dependency>
 * 			<groupId>com.baomidou</groupId>
 * 			<artifactId>mybatis-plus-boot-starter</artifactId>
 * 			<version>3.2.0</version>
 * 		</dependency>
 * 2配置
 *  配置数据源
 *   1）导入数据库驱动 mysql-connector
 *   2) 在application.yaml文件中配置数据源相关信息
 *
 *
 *  配置mybatis-plus
 *   1) 使用@mapperScan 扫描mapper文件位置
 *   2） 告诉mybatis-plus sql 映射问价位置
 *
 *
 * 逻辑删除
 * 1)配置全局的逻辑删除规则(省略)
 * 2)配置逻辑删除的组件bean(省略)
 * 3)加上逻辑删除的注解@TableLogic
 */
@EnableDiscoveryClient
@MapperScan("com.atguigu.gulimall.product.dao")
@SpringBootApplication
public class GulimallProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(GulimallProductApplication.class, args);
	}

}
