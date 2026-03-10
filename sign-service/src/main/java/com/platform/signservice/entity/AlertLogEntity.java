package com.platform.signservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 类说明：AlertLogEntity 负责记录未签到提醒发送日志，避免同日重复通知。
 */
@TableName("risk_alert_log")
@Data
public class AlertLogEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private String alertType;

    private LocalDate alertDate;

    private LocalDateTime createdAt;
}
