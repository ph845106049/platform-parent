package com.platform.common.core.mybatis;

import com.platform.common.core.id.Snowflake;
import com.platform.common.core.id.SnowflakeId;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Field;
import java.util.Map;

@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class SnowflakeIdInterceptor implements Interceptor {

    private final Snowflake snowflake = new Snowflake();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object param = invocation.getArgs()[1];

        if (param == null) {
            return invocation.proceed();
        }

        if (param instanceof Map<?, ?> map) {
            for (Object value : map.values()) {
                fillIdIfNecessary(value);
            }
        } else {
            fillIdIfNecessary(param);
        }
        return invocation.proceed();
    }

    private void fillIdIfNecessary(Object obj) throws IllegalAccessException {
        if (obj == null) return;

        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(SnowflakeId.class)) {
                field.setAccessible(true);
                if (field.get(obj) == null) {
                    field.set(obj, snowflake.nextId());
                }
            }
        }
    }
}
