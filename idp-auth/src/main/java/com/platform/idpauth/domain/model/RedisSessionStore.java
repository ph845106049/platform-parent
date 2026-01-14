package com.platform.idpauth.domain.model;

import com.platform.idpauth.infrastructure.store.SessionStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:50
 */
@Component
@RequiredArgsConstructor
public class RedisSessionStore implements SessionStore {

    private final StringRedisTemplate redisTemplate;

    @Override
    public TokenPair create(Long userId, DeviceInfo device) {
        String at = "at_" + UUID.randomUUID().toString().replace("-", "");
        long expiresIn = 30 * 60;
        redisTemplate.opsForValue().set("IDP:AT:" + at, userId.toString(), expiresIn, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("IDP:USER:" + userId, at, expiresIn, TimeUnit.SECONDS);
        return new TokenPair(
                at,
                null,                 // 先不实现 refreshToken
                expiresIn,
                device,
                userId,
                LocalDateTime.now()
        );
    }


    @Override
    public Long getUserId(String token) {
        String v = redisTemplate.opsForValue().get("IDP:AT:" + token);
        return v == null ? null : Long.valueOf(v);
    }

    @Override
    public void revokeByUser(Long userId) {
        String at = redisTemplate.opsForValue().get("IDP:USER:" + userId);
        if (at != null) redisTemplate.delete("IDP:AT:" + at);
        redisTemplate.delete("IDP:USER:" + userId);
    }

    @Override
    public void remove(String token) {
        redisTemplate.delete("IDP:AT:" + token);
    }

}
