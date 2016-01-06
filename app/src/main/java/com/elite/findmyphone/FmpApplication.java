package com.elite.findmyphone;

import android.app.Application;

import com.elite.findmyphone.common.Env;
import com.elite.findmyphone.core.utils.BasicConfig;

/**
 * Create by wjc133
 * Date: 2015/12/31
 * Time: 10:17
 */
public class FmpApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        BasicConfig.INSTANCE.setAppContext(getApplicationContext());

        Env.INSTANCE.init();
    }
}
