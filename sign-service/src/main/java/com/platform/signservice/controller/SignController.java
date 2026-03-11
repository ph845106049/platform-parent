package com.platform.signservice.controller;

import com.platform.authclient.context.UserContext;
import com.platform.common.base.model.dto.SignRecordDTO;
import com.platform.signservice.model.dto.SignStreakProgressDTO;
import com.platform.signservice.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sign")
@RequiredArgsConstructor

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 */
public class SignController {

    private final SignService signService;

    private Long currentUserId() {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "请先登录后再操作");
        }
        return userId;
    }

    /**
     * 查询今天是否签到
     */
    @GetMapping("/today")
    public Boolean hasSignedToday() {
        return signService.hasSignedToday(currentUserId());
    }

    /**
     * 查询昨日签到信息
     */
    @GetMapping("/yesterday")
    public SignRecordDTO getYesterdaySign() {
        return signService.getYesterdaySign(currentUserId());
    }

    /**
     * 用户签到
     */
    @PostMapping
    public String sign(@RequestParam(required = false) String message) {
        signService.sign(currentUserId(), message);
        return "签到成功";
    }

    /**
     * 设置连续签到目标天数
     */
    @PostMapping("/streak-goal")
    public String setStreakGoal(@RequestParam Integer goalDays) {
        signService.setStreakGoalDays(currentUserId(), goalDays);
        return "设置成功";
    }

    /**
     * 获取连续签到进度
     */
    @GetMapping("/streak-progress")
    public SignStreakProgressDTO getStreakProgress() {
        return signService.getStreakProgress(currentUserId());
    }

}
