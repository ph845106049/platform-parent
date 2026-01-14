package com.platform.authclient.config;

import com.platform.authclient.security.BearerAuthFilter;

import com.platform.authclient.security.TokenVerifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConditionalOnProperty(prefix = "platform.auth", name = "enabled", havingValue = "true")
public class AuthClientAutoConfiguration {

    @Bean
    public RestTemplate authClientRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public BearerAuthFilter bearerAuthFilter(TokenVerifier tokenVerifier) {
        return new BearerAuthFilter(tokenVerifier);
    }
}
