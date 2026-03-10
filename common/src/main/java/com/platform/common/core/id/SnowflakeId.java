package com.platform.common.core.id;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 */
public @interface SnowflakeId {
}
