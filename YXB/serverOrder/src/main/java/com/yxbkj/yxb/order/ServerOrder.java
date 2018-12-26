package com.yxbkj.yxb.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.yxbkj.yxb.order.mapper")
@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.yxbkj.yxb.*")
public class ServerOrder {
    public static void main(String[] args) {
        SpringApplication.run(ServerOrder.class, args);
    }
}
