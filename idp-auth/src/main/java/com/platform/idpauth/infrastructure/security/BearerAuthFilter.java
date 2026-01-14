package com.platform.idpauth.infrastructure.security;

import com.platform.idpauth.application.TokenFacade;

import com.platform.idpauth.domain.exception.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class BearerAuthFilter extends OncePerRequestFilter {

    private final TokenFacade tokenFacade;

    public BearerAuthFilter(TokenFacade tokenFacade) {
        this.tokenFacade = tokenFacade;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // 放行登录/注册/健康检查等
        return path.equals("/api/auth/login")
                || path.equals("/api/auth/register")
                || path.startsWith("/actuator");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws ServletException, IOException {

        String auth = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (auth == null || !auth.startsWith("Bearer ")) {
            write401(resp, "TOKEN_MISSING", "缺少 Authorization Bearer token");
            return;
        }

        String token = auth.substring(7);

        try {
            Long userId = tokenFacade.verify(token); // ✅ 放进 try

            // ✅ verify 成功才塞上下文
            var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
            var authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            MDC.put("userId", String.valueOf(userId));

            chain.doFilter(req, resp);

        } catch (AuthException ex) {
            // ✅ 401 + 业务错误码
            write401(resp, ex.getCode().name(), ex.getMessage());
            return;

        } finally {
            SecurityContextHolder.clearContext();
            MDC.remove("userId");
        }
    }

    private void write401(HttpServletResponse resp, String code, String msg) throws IOException {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write("{\"code\":\"" + code + "\",\"msg\":\"" + msg + "\"}");
    }

}
