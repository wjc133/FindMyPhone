package com.elite.findmyphone.core;

/**
 * Create by wjc133
 * Date: 2015/12/31
 * Time: 10:30
 */
public enum CoreError {
    NETWORK_ERROR(1000);

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
}
