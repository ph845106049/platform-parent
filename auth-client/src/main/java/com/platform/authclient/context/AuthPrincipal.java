package com.platform.authclient.context;

import java.io.Serializable;

public class AuthPrincipal implements Serializable {

    private Long userId;
    private String username;

    public AuthPrincipal() {}

    public AuthPrincipal(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
