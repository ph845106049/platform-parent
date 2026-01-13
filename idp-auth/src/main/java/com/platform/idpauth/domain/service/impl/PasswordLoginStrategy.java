package com.platform.idpauth.domain.service.impl;

import com.platform.idpauth.application.command.LoginCommand;
import com.platform.common.model.model.SysUser;
import com.platform.idpauth.domain.service.PasswordService;
import com.platform.idpauth.domain.strategy.LoginStrategy;
import com.platform.idpauth.infrastructure.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:47
 */
@Component
@RequiredArgsConstructor
public class PasswordLoginStrategy implements LoginStrategy {

    private final UserRepository userRepository;

    private final PasswordService passwordService;

    @Override
    public boolean support(String loginType) {
        return "PASSWORD".equals(loginType);
    }

    @Override
    public Long authenticate(LoginCommand cmd) {
        SysUser user = userRepository.findByUsername(cmd.getUsername());
        passwordService.verify(cmd.getPassword(),user);
        return user.getId();
    }
}
