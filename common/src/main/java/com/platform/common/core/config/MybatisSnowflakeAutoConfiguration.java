package com.platform.common.core.config;

import com.platform.common.core.mybatis.SnowflakeIdInterceptor;
import org.apache.ibatis.executor.Executor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisSnowflakeAutoConfiguration {

    @Bean
    @ConditionalOnClass(Executor.class)
    public SnowflakeIdInterceptor snowflakeIdInterceptor() {
        return new SnowflakeIdInterceptor();
    }
}
