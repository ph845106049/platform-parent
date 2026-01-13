package com.platform.common.command;

import com.platform.idpauth.domain.model.DeviceInfo;
import lombok.Data;

/**
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
