package com.platform.idpauth.application;

import com.platform.common.model.model.DeviceInfo;
import com.platform.common.model.model.TokenPair;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:38
 */
public interface TokenFacade {

    TokenPair issue(Long userId, DeviceInfo device);

    Long verify(String accessToken);

    void logout(String accessToken);
}
