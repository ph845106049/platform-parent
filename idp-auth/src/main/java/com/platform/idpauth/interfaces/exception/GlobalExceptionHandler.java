package com.platform.idpauth.interfaces.exception;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 13:38
 */
import com.platform.idpauth.domain.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 类说明：GlobalExceptionHandler 负责统一处理接口层抛出的异常并转换响应。
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAuth(AuthException ex) {

        log.error("AuthException occurred, code={}, msg={}", ex.getCode(), ex.getMessage(), ex);

        Map<String, Object> body = new HashMap<>();
        body.put("code", ex.getCode().name());
        body.put("msg", ex.getMessage());

        return ResponseEntity.status(ex.getCode().status()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOther(Exception ex) {

        log.error("Unhandled system exception", ex);

        Map<String, Object> body = new HashMap<>();
        body.put("code", "SYSTEM_ERROR");
        body.put("msg", "系统异常");

        return ResponseEntity.status(500).body(body);
    }
}
