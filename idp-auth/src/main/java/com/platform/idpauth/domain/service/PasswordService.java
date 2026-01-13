package com.platform.idpauth.domain.service;

import com.platform.common.model.model.SysUser;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:42
 */
public interface PasswordService {

    void verify(String rawPassword, SysUser user);

}

