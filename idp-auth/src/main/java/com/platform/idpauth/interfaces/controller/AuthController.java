package com.platform.idpauth.interfaces.controller;

import com.platform.idpauth.application.AuthFacade;
import com.platform.idpauth.application.RegisterService;
import com.platform.idpauth.application.TokenFacade;
import com.platform.idpauth.application.command.LoginCommand;
import com.platform.idpauth.application.command.RegisterCommand;
import com.platform.idpauth.domain.log.Audit;
import com.platform.idpauth.domain.model.TokenPair;
import com.platform.idpauth.domain.enums.AuditEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 */
public class AuthController {

    private final AuthFacade authFacade;

    private final TokenFacade tokenFacade;

    private final RegisterService registerService;

    @Audit(event = AuditEvent.LOGIN)
    @PostMapping("/login")
    public TokenPair login(@RequestBody LoginCommand cmd) {
        return authFacade.login(cmd);
    }

    @Audit(event = AuditEvent.LOGOUT)
    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String auth) {
        tokenFacade.logout(extractBearer(auth));
    }

    @Audit(event = AuditEvent.REGISTER)
    @PostMapping("/register")
    public void register(@RequestBody RegisterCommand cmd) {
        registerService.register(cmd);
    }

    @Audit(event = AuditEvent.CANCEL_ACCOUNT)
    @PostMapping("/cancel")
    public void cancelAccount() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        registerService.cancelAccount(username);
    }

    private String extractBearer(String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            throw new IllegalArgumentException("缺少 Authorization Bearer token");
        }
        return auth.substring(7);
    }

}

