package com.platform.idpauth.infrastructure.store;

import com.platform.idpauth.domain.model.DeviceInfo;
import com.platform.idpauth.domain.model.SysUser;
import com.platform.idpauth.domain.model.TokenPair;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:41
 */
public interface SessionStore {
    TokenPair create(SysUser user, DeviceInfo device);

    Long getUserId(String token);

    SysUser getUser(String token);

    void revokeByUser(SysUser userId);

    void remove(String token);
}
