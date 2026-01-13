package com.platform.idpauth.domain.service.impl;

import com.platform.common.enums.AuthErrorCode;
import com.platform.common.exception.AuthException;
import com.platform.common.model.model.SysUser;
import com.platform.idpauth.domain.service.PasswordService;
import com.platform.idpauth.infrastructure.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 12:10
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultPasswordServiceImpl implements PasswordService {

    private final PasswordEncoder passwordEncoder;

    private final StringRedisTemplate redisTemplate;

    private final UserRepository userRepository;

    private static final int MAX_FAIL = 5;

    private static final Duration LOCK_TIME = Duration.ofMinutes(30);

    @Override
    public void verify(String rawPassword, SysUser user) {

        if (user == null) {
            throw new AuthException(AuthErrorCode.LOGIN_FAIL,AuthErrorCode.LOGIN_FAIL.msg());
        }
        if (user.getEnabled() == 0) {
            throw new AuthException(AuthErrorCode.ACCOUNT_DISABLED,AuthErrorCode.ACCOUNT_DISABLED.msg());
        }
        if (user.getLocked() == 1) {
            throw new AuthException(AuthErrorCode.ACCOUNT_LOCKED,AuthErrorCode.ACCOUNT_LOCKED.msg());
        }

        String failKey = "IDP:LOGIN:FAIL:" + user.getUsername();

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            Long fail = redisTemplate.opsForValue().increment(failKey);
            redisTemplate.expire(failKey, LOCK_TIME);

            if (fail >= MAX_FAIL) {
                log.warn("用户 {} 连续登录失败 {} 次，已锁定", user.getUsername(), fail);
                user.setLocked(1);
                userRepository.lock(user.getId()); // 你在 mapper 里实现 update
            }
            throw new RuntimeException("用户名或密码错误");
        }

        // 登录成功，清空失败次数
        redisTemplate.delete(failKey);
    }

}
