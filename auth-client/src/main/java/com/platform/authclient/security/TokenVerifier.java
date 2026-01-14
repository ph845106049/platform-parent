package com.platform.authclient.security;

import com.platform.authclient.context.AuthPrincipal;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月14日 16:03
 */
public interface TokenVerifier {

    /**
     * 校验 token，返回登录用户
     * @throws RuntimeException token 无效 / 过期
     */
    AuthPrincipal verify(String token);
}
