package com.platform.smsservice.service.impl;

import com.platform.smsservice.model.SmsSendContext;
import com.platform.smsservice.service.template.AbstractSmsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 类说明：InternationalSmsSender 负责非中国大陆手机号的短信发送策略实现。
 */
@Component
@Slf4j
public class InternationalSmsSender extends AbstractSmsTemplate {

    @Override
    public boolean supports(int countryCode) {
        return countryCode != 86;
    }

    @Override
    protected String buildProviderPayload(SmsSendContext context) {
        return "template=ALIVE_INTL_GENERAL,mobile=" + context.getE164Mobile()
                + ",countryCode=" + context.getCountryCode() + ",content=" + context.getContent();
    }

    @Override
    protected void doSend(SmsSendContext context, String providerPayload) {
        // TODO: 对接国际短信网关（Twilio/MessageBird 等）
        log.info("国际短信网关发送, payload={}", providerPayload);
    }

    @Override
    protected String senderName() {
        return "INTL_SMS";
    }
}
