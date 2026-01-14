package com.platform.idpauth.application;

import com.platform.idpauth.domain.model.DeviceInfo;
import com.platform.idpauth.domain.model.SysUser;
import com.platform.idpauth.domain.model.TokenPair;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:38
 */
public interface TokenFacade {

    TokenPair issue(SysUser userId, DeviceInfo device);

    SysUser verify(String accessToken);

    void logout(String accessToken);
}
