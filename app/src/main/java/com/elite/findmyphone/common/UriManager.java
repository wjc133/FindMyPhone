package com.elite.findmyphone.common;

import android.content.Context;

import com.elite.findmyphone.R;
import com.elite.findmyphone.core.UriProvider;

/**
 * Create by wjc133
 * Date: 2016/1/6
 * Time: 22:35
 */
public class UriManager {
    private static Context appContext;

    public static void init(Context context) {
        // TODO: 2016/1/6 这里暂时不区分开发环境，如果有需要可添加
        appContext = context;
        initDevUri();
    }

    public static void initDevUri() {
        UriProvider.init(appContext.getString(R.string.dev_tcloud_host));
    }
}
