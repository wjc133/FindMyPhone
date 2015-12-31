package com.elite.findmyphone.core;

/**
 * Create by wjc133
 * Date: 2015/12/31
 * Time: 10:30
 */
public class CoreException extends Exception {

    private CoreError error;

    private static final long serialVersionUID = 1L;

    public CoreException(CoreError coreError) {
        super(coreError.message(), coreError.throwable());
        error = coreError;
    }

    public CoreException(String detailMessage) {
        super(detailMessage);
    }

    public CoreException(Throwable throwable) {
        super(throwable);
    }

    public CoreException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public CoreError getError() {
        return error;
    }

    public int getCode() {
        return getError().code();
    }
}
