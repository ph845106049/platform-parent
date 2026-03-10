package com.platform.idpauth.application.command;

import com.platform.idpauth.domain.model.DeviceInfo;
import lombok.Data;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:43
 */
@Data
public class LoginCommand {

    private String username;

    private String password;

    private String loginType;

    // PASSWORD / SMS / OAUTH
    private String captcha;

    private DeviceInfo deviceInfo;

}
