package com.platform.idpauth.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 12:05
 */
@Data
public class SysUser{

    private Long id;

    /** 登录账号 */
    private String username;

    /** BCrypt 加密密码 */
    private String password;

    /** 真实姓名 */
    private String realName;

    /** 手机号 */
    private String mobile;

    /** 邮箱 */
    private String email;

    /** 是否启用：1启用 0禁用 */
    private Integer enabled;

    /** 是否锁定：1锁定 0正常 */
    private Integer locked;

    /** 最近登录IP */
    private String lastLoginIp;

    /** 最近登录时间 */
    private LocalDateTime lastLoginTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}

