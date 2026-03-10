package com.platform.authclient.context;

import lombok.Data;
import java.io.Serializable;

@Data

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 */
public class AuthPrincipal implements Serializable {

    private Long userId;
    private String username;
    private String realName;

    public AuthPrincipal() {}

    public AuthPrincipal(Long userId, String username,String realName) {
        this.userId = userId;
        this.username = username;
        this.realName = realName;
    }
}
