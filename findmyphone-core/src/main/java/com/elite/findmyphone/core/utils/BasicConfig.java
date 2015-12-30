package com.elite.findmyphone.core.utils;

import android.content.Context;

/**
 * Create by wjc133
 * Date: 2015/12/30
 * Time: 20:47
 */
public enum BasicConfig {
    INSTANCE;

    private Context mContext;

    /**
     * @return Application context
     */
    public Context getAppContext() {
        return mContext;
    }

    public void setAppContext(Context context) {
        mContext = context;
    }
}

