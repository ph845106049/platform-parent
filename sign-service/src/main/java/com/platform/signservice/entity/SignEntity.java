package com.platform.signservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月19日 16:34
 */
@TableName("sign_record")
@Data
public class SignEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private LocalDate signDate;

    /**
     * 签到时给“明天的自己”留的一句话
     */
    private String messageForTomorrow;

    private LocalDateTime createdAt;
}
