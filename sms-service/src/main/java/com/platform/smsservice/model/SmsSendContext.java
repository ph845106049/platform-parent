package com.platform.smsservice.model;

import lombok.Builder;
import lombok.Getter;

/**
 * 类说明：SmsSendContext 负责承载短信路由后的标准发送上下文。
 */
@Getter
@Builder
public class SmsSendContext {

    private final Long targetUserId;

    private final String targetMobile;

    private final String e164Mobile;

    private final int countryCode;

    private final String regionCode;

    private final String content;
}
