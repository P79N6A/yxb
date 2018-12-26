package com.yxbkj.yxb.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.yxbkj.yxb.system.mapper")
@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.yxbkj.yxb.*")
public class ServerSystem {
    public static void main(String[] args) {
        SpringApplication.run(ServerSystem.class, args);
    }
}
