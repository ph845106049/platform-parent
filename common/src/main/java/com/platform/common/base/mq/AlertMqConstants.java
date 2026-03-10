package com.platform.common.base.mq;

/**
 * 类说明：AlertMqConstants 负责定义未签到提醒消息队列拓扑常量。
 */
public interface AlertMqConstants {

    String EXCHANGE = "alive.alert.exchange";
    String ROUTING_KEY = "alert.no-sign.3days";
    String QUEUE = "alive.alert.no-sign.queue";
}
