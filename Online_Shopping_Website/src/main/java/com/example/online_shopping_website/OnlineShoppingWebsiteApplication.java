package com.example.online_shopping_website;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.example.online_shopping_website.mapper")   // 指定mapper接口
@EnableScheduling
public class OnlineShoppingWebsiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineShoppingWebsiteApplication.class, args);
    }

}
