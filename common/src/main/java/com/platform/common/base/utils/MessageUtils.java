/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * Copyright (c) 2020 云好药 All rights reserved.
 *
 * http://www.yunhaoyao.com
 *
 * 版权所有，侵权必究！
 */

package com.platform.common.base.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 类说明：MessageUtils 负责读取国际化消息并根据当前语言环境返回文本。
 * 国际化
 *
 * @author admin
 * @since 1.0.0
 */
public class MessageUtils {
    private static MessageSource messageSource;
    static {
        messageSource = (MessageSource)SpringContextUtils.getBean("messageSource");
    }

    public static String getMessage(int code){
        return getMessage(code, new String[0]);
    }

    public static String getMessage(int code, String... params){
        return messageSource.getMessage(code+"", params, LocaleContextHolder.getLocale());
    }
}
