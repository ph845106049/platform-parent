package com.platform.idpauth.domain.strategy;

import com.platform.idpauth.application.command.LoginCommand;
import com.platform.idpauth.domain.model.SysUser;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:43
 */
public interface LoginStrategy {

    boolean support(String loginType);

    SysUser authenticate(LoginCommand cmd);

}
