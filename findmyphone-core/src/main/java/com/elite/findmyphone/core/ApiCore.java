package com.elite.findmyphone.core;

import com.elite.findmyphone.api.ServerResult;
import com.elite.findmyphone.core.base.AbstractBaseCore;
import com.elite.findmyphone.core.common.exception.ApiKeyException;
import com.elite.findmyphone.core.common.exception.CoreException;

/**
 * Create by wjc133
 * Date: 2015/12/31
 * Time: 10:24
 * 需要调用Api的业务类应该实现此接口
 */
public class ApiCore extends AbstractBaseCore {
    public static <T> void checkResultThrowException(ServerResult<T> result) throws CoreException {
        if (result != null) {
            int code = result.getCode();
            if (code == CoreError.MISSING_APIKEY.code()) {
                throw new ApiKeyException(CoreError.MISSING_APIKEY);
            }
        }
    }
}
