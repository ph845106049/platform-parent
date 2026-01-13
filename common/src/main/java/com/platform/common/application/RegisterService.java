package com.platform.idpauth.application;

import com.platform.idpauth.application.command.RegisterCommand;
import com.platform.idpauth.domain.enums.AuthErrorCode;
import com.platform.idpauth.domain.exception.AuthException;
import com.platform.idpauth.domain.model.SysUser;
import com.platform.idpauth.infrastructure.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 12:29
 */
@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterCommand cmd) {

        if (userRepository.existsByUsername(cmd.getUsername())) {
            throw new AuthException(AuthErrorCode.USER_EXISTS, AuthErrorCode.USER_EXISTS.msg());
        }

        SysUser user = new SysUser();
        user.setUsername(cmd.getUsername());
        user.setPassword(passwordEncoder.encode(cmd.getPassword()));
        user.setRealName(cmd.getRealName());
        user.setMobile(cmd.getMobile());
        user.setEmail(cmd.getEmail());
        user.setEnabled(1);
        user.setLocked(0);

        userRepository.save(user);
    }
}
