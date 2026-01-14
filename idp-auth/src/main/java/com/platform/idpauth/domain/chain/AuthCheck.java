package com.platform.idpauth.domain.chain;

import com.platform.idpauth.application.command.LoginCommand;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:43
 */
public interface AuthCheck {

    void check(LoginCommand cmd);

}
