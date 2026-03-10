package com.platform.idpauth.domain.chain;

import com.platform.idpauth.application.command.LoginCommand;
import com.platform.idpauth.domain.enums.AuthErrorCode;
import com.platform.idpauth.domain.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:48
 */
@Component
@RequiredArgsConstructor
public class RateLimitCheck implements AuthCheck {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void check(LoginCommand cmd) {
        String key = "IDP:LOGIN:U:" + cmd.getUsername();
        Long cnt = redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, Duration.ofMinutes(10));
        if (cnt > 10) {
            throw new AuthException(AuthErrorCode.ACCOUNT_LOCKED,AuthErrorCode.ACCOUNT_LOCKED.msg());
        }
    }
}
