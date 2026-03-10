package com.platform.idpauth.domain.enums;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 16:15
 */
public enum AuditEvent {

    LOGIN("登录"),
    LOGOUT("登出"),
    REGISTER("注册"),
    CANCEL_ACCOUNT("注销"),
    USERINFO("获取用户信息"),
    REFRESH_TOKEN("刷新令牌");

    private final String desc;

    AuditEvent(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return desc;
    }
}
