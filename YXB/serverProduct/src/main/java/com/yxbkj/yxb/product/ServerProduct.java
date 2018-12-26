package com.yxbkj.yxb.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.yxbkj.yxb.product.mapper")
@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.yxbkj.yxb.*")
public class ServerProduct {
    public static void main(String[] args) {
        SpringApplication.run(ServerProduct.class, args);
    }
}
