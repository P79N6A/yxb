package com.yxbkj.yxb.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.yxbkj.yxb.member.mapper")
@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.yxbkj.yxb.*")
public class ServerMember {
    public static void main(String[] args) {
        SpringApplication.run(ServerMember.class, args);
    }
}
