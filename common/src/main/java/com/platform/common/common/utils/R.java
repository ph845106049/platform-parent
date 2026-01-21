package com.platform.common.common.utils;

import com.platform.common.common.enums.CommonConstants;
import com.platform.common.common.exception.BusinessRuntimeException;
import com.platform.common.common.exception.Code;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author uyaogui
 */
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;


    public static final int FAIL_CODE = 1;

    public static final int WARN_CODE = 2;

    public static final int INFO_CODE = 3;

    private int code;

    private int bizErrorCode;

    private String msg;

    private T data;

    public static <T> R<T> ok() {
        return restResult(null, CommonConstants.SUCCESS, null);
    }

    /**
     * 成功-无返回值
     *
     * @param clazz clazz
     * @return {@link R}<{@link T}>
     */
    public static <T> R<T> ok(Class<T> clazz) {
        return restResult(null, CommonConstants.SUCCESS, null);
    }

    /**
     * ok void
     *
     * @return {@link R}<{@link Void}>
     */
    public static R<Void> okVoid() {
        return ok(Void.class);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, CommonConstants.SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> R<T> ok(int bizErrorCode, T data, String msg) {
        return restResult(data, CommonConstants.SUCCESS, bizErrorCode, msg);
    }

    public static <T> R<T> error() {
        return restResult(null, CommonConstants.FAIL, null);
    }

    public static <T> R<T> error(String msg) {
        return restResult(null, CommonConstants.FAIL, msg);
    }

    public static <T> R<T> error(T data) {
        return restResult(data, CommonConstants.FAIL, null);
    }

    public static <T> R<T> error(T data, String msg) {
        return restResult(data, CommonConstants.FAIL, msg);
    }

    /**
     * 在页面上显示错误信息
     *
     * @param msg  消息
     * @return {@link R}<{@link T}>
     */
    public static <T> R<T> info(String msg) {
        return restResult(null, CommonConstants.INFO, msg);
    }

    /**
     * 错误
     *
     * @param code 密码
     * @param msg  消息
     * @return {@link R}<{@link T}>
     */
    public static <T> R<T> error(int code, String msg) {
        return restResult(null, code, 0, msg);
    }

    public static <T> R<T> error(int code) {
        return restResult(null, code, MessageUtils.getMessage(code));
    }

    public static <T> R<T> error(int code, int bizErrorCode, String msg) {
        return restResult(null, code, bizErrorCode, msg);
    }

    public static <T> R<T> error(int code, int bizErrorCode, String msg, Class<T> clazz) {
        return restResult(null, code, bizErrorCode, msg);
    }

    public static <T> R<T> error(Code code, Class<T> clazz) {
        return restResult(null, 2, code.getCode(), code.getMsg());
    }

    static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    /**
     * 休息结果
     *
     * @param data         数据
     * @param code         密码
     * @param bizErrorCode we错误代码
     * @param msg          消息
     * @return {@link R}<{@link T}>
     */
    static <T> R<T> restResult(T data, int code, int bizErrorCode, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setBizErrorCode(bizErrorCode);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public boolean success() {
        return code == 0 ? true : false;
    }

    /**
     * 强制性获取
     *
     * @return {@link T}
     */
    public T forceGet() {
        if (this.success()) {
            return this.getData();
        }
        throw new BusinessRuntimeException(this.getCode(), this.getMsg());
    }

    /**
     * 强制性获取，不抛出异常，失败返回 null
     *
     * @return {@link T}
     */
    public T forceGetData() {
        if (this.success()) {
            return this.getData();
        }
        return null;
    }
}
