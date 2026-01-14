package com.platform.idpauth.filter;

import com.platform.authclient.context.AuthPrincipal;
import com.platform.authclient.context.UserContext;
import com.platform.idpauth.application.TokenFacade;
import com.platform.idpauth.domain.model.SysUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class UserContextFilter extends OncePerRequestFilter {

    private final TokenFacade tokenFacade;

    public UserContextFilter(TokenFacade tokenFacade) {
        this.tokenFacade = tokenFacade;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws ServletException, IOException {

        try {
            String auth = req.getHeader(HttpHeaders.AUTHORIZATION);
            if (auth != null && auth.startsWith("Bearer ")) {
                String token = auth.substring(7);

                SysUser user = tokenFacade.verify(token);

                // 关键：写入 ThreadLocal
                UserContext.set(new AuthPrincipal(user.getId(),user.getUsername(),user.getRealName()));

                // 可选：日志链路
                MDC.put("userId", String.valueOf(user.getId()));
            }
            chain.doFilter(req, resp);
        } finally {
            // Tomcat 线程复用，不清理会串号
            MDC.remove("userId");
            UserContext.clear();
        }
    }
}
