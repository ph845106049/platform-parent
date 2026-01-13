package com.platform.idpauth.application;

import com.platform.common.enums.AuthErrorCode;
import com.platform.common.exception.AuthException;
import com.platform.common.model.model.DeviceInfo;
import com.platform.common.model.model.TokenPair;
import com.platform.common.store.SessionStore;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:49
 */
@Component
@RequiredArgsConstructor
public class DefaultTokenFacade implements TokenFacade {

    private final SessionStore sessionStore;

    @Override
    public TokenPair issue(Long userId, DeviceInfo device) {
        sessionStore.revokeByUser(userId);
        return sessionStore.create(userId, device);
    }

    @Override
    public Long verify(String accessToken) {
        if (accessToken == null || accessToken.isBlank()) {
            throw new AuthException(AuthErrorCode.TOKEN_MISSING, "缺少token");
        }
        Long userId = sessionStore.getUserId(accessToken);
        if (userId == null) {
            throw new AuthException(AuthErrorCode.TOKEN_INVALID, "token无效或已过期");
        }
        return userId;
    }


    @Override
    public void logout(String accessToken) {
        sessionStore.remove(accessToken);
    }
}
