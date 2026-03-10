package com.platform.signservice.mq;

import com.platform.common.base.model.dto.NoSignAlertMessage;
import com.platform.common.base.mq.AlertMqConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 类说明：RabbitAlertMessagePublisher 负责将未签到提醒事件发布到 RabbitMQ。
 */
@Component
@RequiredArgsConstructor
public class RabbitAlertMessagePublisher implements AlertMessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(NoSignAlertMessage message) {
        rabbitTemplate.convertAndSend(AlertMqConstants.EXCHANGE, AlertMqConstants.ROUTING_KEY, message);
    }
}
