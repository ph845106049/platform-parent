package com.platform.common.log;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 13:36
 */
public class LogMasker {

    public static String maskToken(String token) {
        if (token == null) return null;
        if (token.length() <= 6) return "******";
        return token.substring(0, 6) + "******";
    }

    public static String maskMobile(String mobile) {
        if (mobile == null || mobile.length() < 7) return mobile;
        return mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4);
    }

    public static String maskEmail(String email) {
        if (email == null) return null;
        int at = email.indexOf('@');
        if (at <= 1) return "***" + email.substring(at);
        return email.substring(0, 1) + "***" + email.substring(at);
    }
}
