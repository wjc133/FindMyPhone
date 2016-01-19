package com.elite.findmyphone.core;

/**
 * Create by wjc133
 * Date: 2015/12/31
 * Time: 10:30
 */
public enum CoreError {
    SERVER_ERROR(999),
    NETWORK_ERROR(1000),

    MISSING_APIKEY(300202, "Missing apikey");

    private int code;
    private String message;
    private Throwable throwable;

    CoreError(int code) {
        this.code = code;
    }

    CoreError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    CoreError(int code, String message, Throwable throwable) {
        this.code = code;
        this.message = message;
        this.throwable = throwable;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public Throwable throwable() {
        return throwable;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
