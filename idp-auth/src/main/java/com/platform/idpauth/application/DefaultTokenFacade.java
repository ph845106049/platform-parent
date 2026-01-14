package com.platform.idpauth.application;

import com.platform.authclient.context.UserContext;
import com.platform.idpauth.domain.enums.AuthErrorCode;
import com.platform.idpauth.domain.exception.AuthException;
import com.platform.idpauth.domain.model.DeviceInfo;
import com.platform.idpauth.domain.model.SysUser;
import com.platform.idpauth.domain.model.TokenPair;
import com.platform.idpauth.infrastructure.store.SessionStore;

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
    public TokenPair issue(SysUser user, DeviceInfo device) {
        sessionStore.revokeByUser(user);
        return sessionStore.create(user, device);
    }

    @Override
    public SysUser verify(String accessToken) {
        if (accessToken == null || accessToken.isBlank()) {
            throw new AuthException(AuthErrorCode.TOKEN_MISSING, "缺少token");
        }
        SysUser user = sessionStore.getUser(accessToken);
        if (user == null) {
            throw new AuthException(AuthErrorCode.TOKEN_INVALID, "token无效或已过期");
        }
        return user;
    }


    @Override
    public void logout(String accessToken) {
        sessionStore.remove(accessToken);
    }
}
