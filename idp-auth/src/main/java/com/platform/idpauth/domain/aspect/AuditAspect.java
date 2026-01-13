package com.platform.idpauth.domain.aspect;

import com.platform.idpauth.domain.log.Audit;
import com.platform.idpauth.domain.utils.LogMasker;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.*;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class AuditAspect {

    private static final Logger AUDIT = LoggerFactory.getLogger("AUDIT");

    @Around("@annotation(audit)")
    public Object around(ProceedingJoinPoint pjp, Audit audit) throws Throwable {

        HttpServletRequest req = currentRequest();
        String ip = req != null ? req.getRemoteAddr() : "-";
        String ua = req != null ? req.getHeader("User-Agent") : "-";
        String traceId = MDC.get("traceId");

        long start = System.currentTimeMillis();
        try {
            Object result = pjp.proceed();

            // 注意：userId 需要你在鉴权后写入 MDC（比如 userinfo / token verify 处）
            String userId = MDC.get("userId");

            AUDIT.info("event={} status=SUCCESS traceId={} userId={} ip={} ua={} costMs={} {}",
                    audit.event().desc(),
                    safe(traceId),
                    safe(userId),
                    safe(ip),
                    safe(ua),
                    (System.currentTimeMillis() - start),
                    audit.recordArgs() ? ("args=" + safeArgs(pjp.getArgs())) : ""
            );
            return result;

        } catch (Throwable ex) {
            String userId = MDC.get("userId");
            AUDIT.info("event={} status=FAIL traceId={} userId={} ip={} ua={} costMs={} err={}",
                    audit.event().desc(),
                    safe(traceId),
                    safe(userId),
                    safe(ip),
                    safe(ua),
                    (System.currentTimeMillis() - start),
                    safe(ex.getMessage())
            );
            throw ex;
        }
    }

    private String safeArgs(Object[] args) {
        // 简单做法：把敏感字段屏蔽（password/token/captcha）
        String raw = Arrays.stream(args).map(String::valueOf).collect(Collectors.joining(","));
        raw = raw.replaceAll("(?i)password=\\S+", "password=******");
        raw = raw.replaceAll("(?i)accessToken=\\S+", "accessToken=" + LogMasker.maskToken("xxxxxx"));
        raw = raw.replaceAll("(?i)refreshToken=\\S+", "refreshToken=******");
        raw = raw.replaceAll("(?i)captcha=\\S+", "captcha=******");
        return raw;
    }

    private String safe(String s) {
        if (s == null) return "-";
        return s.replaceAll("[\\r\\n\\t]", "_");
    }

    private HttpServletRequest currentRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (!(ra instanceof ServletRequestAttributes sra)) return null;
        return sra.getRequest();
    }
}
