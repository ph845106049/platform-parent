package com.platform.idpauth.domain.exception;

import com.platform.idpauth.domain.enums.AuthErrorCode;
import lombok.Data;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 15:58
 */
public class AuthException extends RuntimeException {
    private final AuthErrorCode code;

    public AuthException(AuthErrorCode code, String message) {
        super(message);
        this.code = code;
    }

    public AuthErrorCode getCode() {
        return code;
    }
}
