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

    public static void init(Env.UriSetting uriSetting, Context context) {
        if (null == uriSetting || null == context) {
            return;
        }

        appContext = context;
        if (uriSetting == Env.UriSetting.DEV) {//开发环境地址
            initDevUri();
        } else if (uriSetting == Env.UriSetting.PRODUCT) {//生产环境地址
            initProductUri();
        } else if (uriSetting == Env.UriSetting.TEST) {//测试环境地址
            initTestUri();
        }
    }

    private static void initTestUri() {
        UriProvider.init(appContext.getString(R.string.test_tcloud_host),
                appContext.getString(R.string.test_baidu_host));
    }

    private static void initProductUri() {
        UriProvider.init(appContext.getString(R.string.product_tcloud_host),
                appContext.getString(R.string.product_baidu_host));
    }

    public static void initDevUri() {
        UriProvider.init(appContext.getString(R.string.dev_tcloud_host),
                appContext.getString(R.string.dev_baidu_host));
    }
}
