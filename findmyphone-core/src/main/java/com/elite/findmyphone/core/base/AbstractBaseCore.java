package com.elite.findmyphone.core.base;

import android.content.Context;

import com.elite.findmyphone.core.utils.BasicConfig;

/**
 * Create by wjc133
 * Date: 2015/12/30
 * Time: 20:44
 */
public class AbstractBaseCore implements BaseCore {
    public AbstractBaseCore() {
        // 因为需要反射，所以必须确保有默认构造函数
    }

    //方便在项目中获取Context
    protected Context getContext() {
        return BasicConfig.INSTANCE.getAppContext();
    }


    protected void notifyClients(Class<? extends CoreClient> clientClass, String methodName, Object... args) {
        CoreManager.notifyClients(clientClass, methodName, args);
    }
}
