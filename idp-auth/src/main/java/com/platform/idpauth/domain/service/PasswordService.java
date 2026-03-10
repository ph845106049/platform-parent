package com.platform.idpauth.domain.service;

import com.platform.idpauth.domain.model.SysUser;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:42
 */
public interface PasswordService {

    void verify(String rawPassword, SysUser user);

}

