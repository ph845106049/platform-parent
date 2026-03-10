package com.platform.idpauth.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenPair {

    /** 访问令牌：用于业务系统访问资源 */
    private String accessToken;

    /** 刷新令牌：用于续期 accessToken */
    private String refreshToken;

    /** accessToken 过期时间（秒） */
    private long expiresIn;

    /** 本次登录的设备指纹 */
    private DeviceInfo deviceInfo;

    /** 登录用户ID */
    private SysUser user;

    /** 创建时间 */
    private LocalDateTime issuedAt;
}
