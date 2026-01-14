package com.platform.common.core.id;

public class SnowflakeIdGenerator {

    private static final long WORKER_ID = 1;
    private static final long DATACENTER_ID = 1;
    private static final Snowflake snowflake =
            new Snowflake(WORKER_ID, DATACENTER_ID);

    public static long nextId() {
        return snowflake.nextId();
    }
}
