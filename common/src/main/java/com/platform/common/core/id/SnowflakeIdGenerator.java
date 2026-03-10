package com.platform.common.core.id;


/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 */
public class SnowflakeIdGenerator {

    private static final long WORKER_ID = 1;
    private static final long DATACENTER_ID = 1;
    private static final Snowflake snowflake =
            new Snowflake(WORKER_ID, DATACENTER_ID);

    public static long nextId() {
        return snowflake.nextId();
    }
}
