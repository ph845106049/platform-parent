package com.platform.signservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 */
public class SignServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignServiceApplication.class, args);
    }

}
