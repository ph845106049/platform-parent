package com.platform.smsservice.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 类说明：SmsNotifyRequest 负责承载紧急提醒短信发送请求参数。
 */
@Data
public class SmsNotifyRequest {

    private Long targetUserId;

    @NotBlank
    private String targetMobile;

    @NotBlank
    private String content;
}
