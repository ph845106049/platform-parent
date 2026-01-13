package com.platform.idpauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.platform.idpauth","com.platform.common"})
@MapperScan(basePackages = {"com.platform.idpauth.infrastructure.repository"})
public class IdpAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdpAuthApplication.class, args);
    }

}
