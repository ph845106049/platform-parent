package com.platform.common.store;

import com.platform.common.model.model.DeviceInfo;
import com.platform.common.model.model.TokenPair;

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
