package com.platform.idpauth.application;

import com.platform.idpauth.application.command.LoginCommand;
import com.platform.idpauth.domain.chain.AuthCheck;
import com.platform.idpauth.domain.model.SysUser;
import com.platform.idpauth.domain.model.TokenPair;
import com.platform.idpauth.domain.strategy.LoginStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:38
 */
@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final List<AuthCheck> checks;
    private final List<LoginStrategy> strategies;
    private final TokenFacade tokenFacade;

    public TokenPair login(LoginCommand cmd) {
        checks.forEach(c -> c.check(cmd));

        LoginStrategy strategy = strategies.stream()
                .filter(s -> s.support(cmd.getLoginType()))
                .findFirst()
                .orElseThrow();

        SysUser user = strategy.authenticate(cmd);
        return tokenFacade.issue(user, cmd.getDeviceInfo());
    }
}
