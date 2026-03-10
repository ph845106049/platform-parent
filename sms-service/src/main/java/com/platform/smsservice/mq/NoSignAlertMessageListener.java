package com.platform.smsservice.mq;

import com.platform.common.base.model.dto.NoSignAlertMessage;
import com.platform.common.base.mq.AlertMqConstants;
import com.platform.smsservice.model.request.SmsNotifyRequest;
import com.platform.smsservice.service.SmsDispatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 类说明：NoSignAlertMessageListener 负责消费未签到提醒事件并触发短信发送。
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NoSignAlertMessageListener {

    private final SmsDispatchService smsDispatchService;

    @RabbitListener(queues = AlertMqConstants.QUEUE)
    public void onMessage(NoSignAlertMessage message) {
        SmsNotifyRequest request = new SmsNotifyRequest();
        request.setTargetUserId(message.getUserId());
        request.setTargetMobile(message.getTargetMobile());
        request.setContent(message.getContent());
        smsDispatchService.dispatch(request);
        log.info("消费提醒消息完成, userId={}, mobile={}", message.getUserId(), message.getTargetMobile());
    }
}
