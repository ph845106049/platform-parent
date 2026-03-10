package com.platform.idpauth.domain.chain;

import com.platform.idpauth.application.command.LoginCommand;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:43
 */
public interface AuthCheck {

    void check(LoginCommand cmd);

}
