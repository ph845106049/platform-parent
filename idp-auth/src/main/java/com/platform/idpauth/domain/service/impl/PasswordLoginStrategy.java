package com.platform.idpauth.domain.service.impl;

import com.platform.idpauth.application.command.LoginCommand;
import com.platform.idpauth.domain.model.SysUser;
import com.platform.idpauth.domain.service.PasswordService;
import com.platform.idpauth.domain.strategy.LoginStrategy;
import com.platform.idpauth.infrastructure.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
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
    public SysUser authenticate(LoginCommand cmd) {
        SysUser user = userRepository.findByUsername(cmd.getUsername());
        passwordService.verify(cmd.getPassword(),user);
        return user;
    }
}
