package com.platform.smsservice.controller;

import com.platform.smsservice.model.request.SmsNotifyRequest;
import com.platform.smsservice.service.SmsDispatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类说明：SmsController 负责接收并处理短信通知请求。
 */
@RestController
@RequestMapping("/api/sms")
@Slf4j
@RequiredArgsConstructor
public class SmsController {

    private final SmsDispatchService smsDispatchService;

    @PostMapping("/notify")
    public void notify(@Valid @RequestBody SmsNotifyRequest request) {
        smsDispatchService.dispatch(request);
        log.info("短信请求处理完成, targetUserId={}, targetMobile={}",
                request.getTargetUserId(), request.getTargetMobile());
    }
}
