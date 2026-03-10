package com.platform.common.core.id;


/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 */
public class Snowflake {

    private final long workerId;
    private final long datacenterId;

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public Snowflake() {
        this(1L, 1L);
    }

    public Snowflake(long workerId, long datacenterId) {
        // 5bit: 0~31
        if (workerId < 0 || workerId > 31) {
            throw new IllegalArgumentException("workerId must be between 0 and 31");
        }
        if (datacenterId < 0 || datacenterId > 31) {
            throw new IllegalArgumentException("datacenterId must be between 0 and 31");
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & 4095; // 12bit: 0~4095
            if (sequence == 0) {
                while ((timestamp = System.currentTimeMillis()) <= lastTimestamp) {
                    // spin
                }
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        return ((timestamp - 1288834974657L) << 22)
                | (datacenterId << 17)
                | (workerId << 12)
                | sequence;
    }
}
