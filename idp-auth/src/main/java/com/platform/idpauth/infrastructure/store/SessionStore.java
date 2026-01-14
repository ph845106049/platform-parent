package com.platform.idpauth.infrastructure.store;

import com.platform.idpauth.domain.model.DeviceInfo;
import com.platform.idpauth.domain.model.TokenPair;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:41
 */
public interface SessionStore {
    TokenPair create(Long userId, DeviceInfo device);

    Long getUserId(String token);

    void revokeByUser(Long userId);

    void remove(String token);
}
