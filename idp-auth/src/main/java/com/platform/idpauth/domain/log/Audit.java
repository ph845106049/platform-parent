package com.platform.idpauth.domain.log;

import com.platform.idpauth.domain.enums.AuditEvent;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 */
public @interface Audit {

    /** 事件类型：LOGIN / LOGOUT / REGISTER / USERINFO 等 */
    AuditEvent event();

    /**
     * 是否记录入参（默认 false，避免误打 password）
     * 如果要记录，也会被脱敏过滤
     */
    boolean recordArgs() default false;
}
