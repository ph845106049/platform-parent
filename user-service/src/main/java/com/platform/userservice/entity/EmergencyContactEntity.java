package com.platform.userservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 类说明：EmergencyContactEntity 负责存储用户与紧急联系人的绑定关系。
 */
@TableName("emergency_contact")
@Data
public class EmergencyContactEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 发起绑定请求的用户
     */
    private Long userId;

    private String contactName;

    private String contactMobile;

    private LocalDateTime createdAt;

    private LocalDateTime boundAt;
}
