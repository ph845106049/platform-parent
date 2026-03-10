package com.platform.signservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 类说明：SignGoalEntity 负责存储用户自定义连续签到目标天数配置。
 */
@TableName("sign_goal_config")
@Data
public class SignGoalEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private Integer goalDays;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
