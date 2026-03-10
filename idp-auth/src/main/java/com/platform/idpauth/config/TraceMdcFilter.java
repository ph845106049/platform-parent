package com.platform.idpauth.config;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 13:34
 */
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.UUID;

/**
 * 类说明：TraceMdcFilter 负责在请求链路中注入并维护 traceId 日志上下文。
 */
@Component
public class TraceMdcFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) {
        String traceId = req.getHeader("X-Trace-Id");
        if (traceId == null || traceId.isBlank()) {
            traceId = UUID.randomUUID().toString().replace("-", "");
        }

        MDC.put("traceId", traceId);
        // userId 后面在鉴权成功时再塞（比如 userinfo/业务接口）
        try {
            resp.setHeader("X-Trace-Id", traceId);
            chain.doFilter(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            MDC.clear();
        }
    }
}
