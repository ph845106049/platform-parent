package com.platform.authclient.context;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月14日 16:03
 */
public class UserContext {

    private static final ThreadLocal<AuthPrincipal> CONTEXT = new ThreadLocal<>();

    public static void set(AuthPrincipal principal) {
        CONTEXT.set(principal);
    }

    public static AuthPrincipal get() {
        return CONTEXT.get();
    }

    public static Long getUserId() {
        AuthPrincipal p = CONTEXT.get();
        return p == null ? null : p.getUserId();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
