package com.yxbkj.yxb;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@ComponentScan("com.yxbkj.yxb.*")
public class ApiApp {
    public static void main(String[] args) {
        SpringApplication.run(ApiApp.class, args);
    }

}
