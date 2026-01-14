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

    private String extractBearer(String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            throw new IllegalArgumentException("缺少 Authorization Bearer token");
        }
        return auth.substring(7);
    }

    @GetMapping("/me")
    public Map<String, Object> me() {
        // 这里假设你已经在 idp-auth 的过滤器里把 userId 放进了 SecurityContext 或 MDC
        // 先用最简单的方式：从 SecurityContext 取 principal
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Map<String, Object> r = new HashMap<>();
        r.put("userId", principal);
        r.put("username", ""); // 有就填，没有先空
        return r;
    }

}

