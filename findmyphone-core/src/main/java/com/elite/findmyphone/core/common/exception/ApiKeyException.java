package com.elite.findmyphone.core.common.exception;

import com.elite.findmyphone.core.CoreError;

/**
 * Create by wjc133
 * Date: 2016/1/19
 * Time: 18:33
 */
public class ApiKeyException extends CoreException {
    public ApiKeyException(CoreError coreError) {
        super(coreError);
    }

    public ApiKeyException(String detailMessage) {
        super(detailMessage);
    }

    public ApiKeyException(Throwable throwable) {
        super(throwable);
    }

    public ApiKeyException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
