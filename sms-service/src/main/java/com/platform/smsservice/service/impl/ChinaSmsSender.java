package com.platform.smsservice.service.impl;

import com.platform.smsservice.model.SmsSendContext;
import com.platform.smsservice.service.template.AbstractSmsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 类说明：ChinaSmsSender 负责中国大陆手机号的短信发送策略实现。
 */
@Component
@Slf4j
public class ChinaSmsSender extends AbstractSmsTemplate {

    private static final int CHINA_COUNTRY_CODE = 86;

    @Override
    public boolean supports(int countryCode) {
        return countryCode == CHINA_COUNTRY_CODE;
    }

    @Override
    protected String buildProviderPayload(SmsSendContext context) {
        return "template=ALIVE_CN_GENERAL,mobile=" + context.getE164Mobile() + ",content=" + context.getContent();
    }

    @Override
    protected void doSend(SmsSendContext context, String providerPayload) {
        // TODO: 对接国内短信网关（阿里云/腾讯云等）
        log.info("国内短信网关发送, payload={}", providerPayload);
    }

    @Override
    protected String senderName() {
        return "CN_SMS";
    }
}
