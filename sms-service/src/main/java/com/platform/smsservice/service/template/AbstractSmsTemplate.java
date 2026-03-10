package com.platform.smsservice.service.template;

import com.platform.smsservice.model.SmsSendContext;
import com.platform.smsservice.service.CountrySmsSender;
import lombok.extern.slf4j.Slf4j;

/**
 * 类说明：AbstractSmsTemplate 负责统一短信发送模板流程并留出国家差异扩展点。
 */
@Slf4j
public abstract class AbstractSmsTemplate implements CountrySmsSender {

    @Override
    public final void send(SmsSendContext context) {
        validateContext(context);
        String providerPayload = buildProviderPayload(context);
        doSend(context, providerPayload);
        afterSend(context);
    }

    protected void validateContext(SmsSendContext context) {
        if (context.getE164Mobile() == null || context.getE164Mobile().isBlank()) {
            throw new IllegalArgumentException("目标手机号不能为空");
        }
        if (context.getContent() == null || context.getContent().isBlank()) {
            throw new IllegalArgumentException("短信内容不能为空");
        }
    }

    protected abstract String buildProviderPayload(SmsSendContext context);

    protected abstract void doSend(SmsSendContext context, String providerPayload);

    protected void afterSend(SmsSendContext context) {
        log.info("短信发送完成, channel={}, region={}, mobile={}",
                senderName(), context.getRegionCode(), context.getE164Mobile());
    }

    protected abstract String senderName();
}
