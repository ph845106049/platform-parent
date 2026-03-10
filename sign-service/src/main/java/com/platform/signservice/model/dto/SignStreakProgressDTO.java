package com.platform.signservice.model.dto;

import lombok.Data;

/**
 * 类说明：SignStreakProgressDTO 负责返回用户连续签到目标与当前进度。
 */
@Data
public class SignStreakProgressDTO {

    private Long userId;

    private Integer goalDays;

    private Integer currentStreakDays;

    private Boolean reachedGoal;
}
