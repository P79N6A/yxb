package com.yxbkj.yxb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.yxbkj.yxb.domain.mapper*")
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@PropertySource(value = "classpath:app.properties", ignoreResourceNotFound = true , encoding="UTF-8")
public class YxbApplication {

    public static void main(String[] args) {
        SpringApplication.run(YxbApplication.class, args);
    }


}
