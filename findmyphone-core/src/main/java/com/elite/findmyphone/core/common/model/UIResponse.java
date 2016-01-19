package com.elite.findmyphone.core.common.model;

import com.elite.findmyphone.core.CoreError;

/**
 * Create by wjc133
 * Date: 2016/1/19
 * Time: 17:49
 */
public class UIResponse<T> {

    private CoreError error;

    private String message = "";
    private T data;

    public UIResponse() {
    }

    public boolean isSuccess() {
        return null == error;
    }

    public static <T> UIResponse<T> success() {
        return success(null);
    }

    public static <T> UIResponse<T> success(T data) {
        return success(data, "");
    }

    public static <T> UIResponse<T> success(T data, String message) {
        UIResponse result = new UIResponse();
        result.data = data;
        result.message = message;
        return result;
    }

    public static <T> UIResponse<T> error(CoreError e) {
        UIResponse result = new UIResponse();
        result.error = e;
        return result;
    }

    public String getMessage() {
        return this.message;
    }

    public UIResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public UIResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public CoreError getError() {
        return error;
    }

    public void setError(CoreError error) {
        this.error = error;
    }
}
