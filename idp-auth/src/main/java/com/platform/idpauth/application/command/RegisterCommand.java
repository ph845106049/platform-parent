package com.platform.idpauth.application.command;

import lombok.Data;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 12:26
 */
@Data
public class RegisterCommand {

    private String username;

    private String password;

    private String realName;

    private String mobile;

    private String email;

}
