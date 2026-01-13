package com.platform.idpauth.domain.enums;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 15:57
 */

public enum AuthErrorCode {

    TOKEN_MISSING(401, "缺少token"),
    TOKEN_INVALID(401, "token无效或已过期"),
    LOGIN_FAIL(400, "用户名或密码错误"),
    ACCOUNT_LOCKED(403, "账号已锁定"),
    ACCOUNT_DISABLED(403, "账号已禁用"),
    USER_EXISTS(400, "账号已存在");

    private final int httpStatus;
    private final String defaultMsg;

    AuthErrorCode(int httpStatus, String defaultMsg) {
        this.httpStatus = httpStatus;
        this.defaultMsg = defaultMsg;
    }

    public int status() {
        return httpStatus;
    }

    public String msg() {
        return defaultMsg;
    }
}
