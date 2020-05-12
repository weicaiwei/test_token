package com.example.test_token;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@SpringBootApplication
@EnableDubboConfiguration
public class TestTokenApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestTokenApplication.class, args);
    }

}
