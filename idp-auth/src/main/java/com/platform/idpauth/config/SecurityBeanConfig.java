package com.platform.idpauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 12:18
 */
@Configuration
public class SecurityBeanConfig {

    /**
     * 统一密码加密器（BCrypt）
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
