package com.platform.common.base.model.dto;

import lombok.Data;

/**
 * 类说明：NoSignAlertMessage 负责承载连续未签到提醒消息事件。
 */
@Data
public class NoSignAlertMessage {

    private Long userId;

    private String username;

    private String targetMobile;

    private String content;
}
