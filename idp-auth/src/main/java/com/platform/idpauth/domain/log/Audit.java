package com.platform.idpauth.domain.log;

import com.platform.common.enums.AuditEvent;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Audit {

    /** 事件类型：LOGIN / LOGOUT / REGISTER / USERINFO 等 */
    AuditEvent event();

    /**
     * 是否记录入参（默认 false，避免误打 password）
     * 如果要记录，也会被脱敏过滤
     */
    boolean recordArgs() default false;
}
