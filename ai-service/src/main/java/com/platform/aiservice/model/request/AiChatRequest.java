package com.platform.aiservice.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 类说明：AiChatRequest 负责承载 AI 对话请求参数。
 */
@Data
public class AiChatRequest {

    @NotBlank
    private String prompt;
}
