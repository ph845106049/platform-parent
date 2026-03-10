package com.platform.userservice.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 类说明：EmergencyContactDTO 负责返回紧急联系人关系的展示数据。
 */
@Data
public class EmergencyContactDTO {

    private Long id;

    private Long userId;

    private String username;

    private String contactName;

    private String contactMobile;

    private LocalDateTime createdAt;

    private LocalDateTime boundAt;
}
