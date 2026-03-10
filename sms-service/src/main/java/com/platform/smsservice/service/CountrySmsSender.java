package com.platform.smsservice.service;

import com.platform.smsservice.model.SmsSendContext;

/**
 * 类说明：CountrySmsSender 负责定义按国家路由的短信发送实现契约。
 */
public interface CountrySmsSender {

    boolean supports(int countryCode);

    void send(SmsSendContext context);
}
