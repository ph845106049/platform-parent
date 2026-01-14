package com.platform.authclient.context;

/**
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
