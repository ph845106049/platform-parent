package com.platform.common.command;

import lombok.Data;

/**
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
