package com.platform.signservice.mq;

import com.platform.common.base.model.dto.NoSignAlertMessage;

/**
 * 类说明：AlertMessagePublisher 负责定义未签到提醒事件发布能力。
 */
public interface AlertMessagePublisher {

    void publish(NoSignAlertMessage message);
}
