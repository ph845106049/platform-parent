package com.platform.signservice.controller;

import com.platform.common.base.model.dto.SignRecordDTO;
import com.platform.signservice.model.dto.SignStreakProgressDTO;
import com.platform.signservice.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sign")
@RequiredArgsConstructor

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 */
public class SignController {

    private final SignService signService;

    /**
     * 查询今天是否签到
     */
    @GetMapping("/today")
    public Boolean hasSignedToday(@RequestParam Long userId) {
        return signService.hasSignedToday(userId);
    }

    /**
     * 查询昨日签到信息
     */
    @GetMapping("/yesterday")
    public SignRecordDTO getYesterdaySign(@RequestParam Long userId) {
        return signService.getYesterdaySign(userId);
    }

    /**
     * 用户签到
     */
    @PostMapping
    public String sign(@RequestParam Long userId,
                       @RequestParam(required = false) String message) {
        signService.sign(userId, message);
        return "签到成功";
    }

    /**
     * 设置连续签到目标天数
     */
    @PostMapping("/streak-goal")
    public String setStreakGoal(@RequestParam Long userId, @RequestParam Integer goalDays) {
        signService.setStreakGoalDays(userId, goalDays);
        return "设置成功";
    }

    /**
     * 获取连续签到进度
     */
    @GetMapping("/streak-progress")
    public SignStreakProgressDTO getStreakProgress(@RequestParam Long userId) {
        return signService.getStreakProgress(userId);
    }

}
