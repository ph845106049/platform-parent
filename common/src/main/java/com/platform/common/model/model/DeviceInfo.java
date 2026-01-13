package com.platform.idpauth.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfo {

    private String deviceId;
    private String deviceType;   // PC / MOBILE / PAD
    private String userAgent;
    private String ip;
    private LocalDateTime loginTime;
}
