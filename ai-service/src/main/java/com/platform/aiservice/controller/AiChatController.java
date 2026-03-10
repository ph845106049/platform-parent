package com.platform.aiservice.controller;

import com.platform.aiservice.model.request.AiChatRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类说明：AiChatController 负责提供统一 AI 聊天接口。
 */
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiChatController {

    private final ChatClient.Builder chatClientBuilder;

    @PostMapping("/chat")
    public String chat(@Valid @RequestBody AiChatRequest request) {
        return chatClientBuilder.build()
                .prompt(request.getPrompt())
                .call()
                .content();
    }
}
