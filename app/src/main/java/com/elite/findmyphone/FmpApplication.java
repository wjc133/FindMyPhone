package com.elite.findmyphone;

import android.app.Application;

import com.elite.findmyphone.common.Env;
import com.elite.findmyphone.core.utils.BasicConfig;
import com.elite.findmyphone.core.utils.HttpUtils;
import com.elite.findmyphone.httpvisitor.HttpVisitor;
import com.elite.findmyphone.httpvisitor.RequestQueue;

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
        RequestQueue requestQueue = HttpVisitor.newRequestQueue();

        BasicConfig.INSTANCE.setAppContext(getApplicationContext());
        HttpUtils.INSTANCE.setRequestQueue(requestQueue);

        Env.instance().init();
    }
}
