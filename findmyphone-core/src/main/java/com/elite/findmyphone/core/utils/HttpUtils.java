package com.elite.findmyphone.core.utils;

import com.elite.findmyphone.api.ServerResult;
import com.elite.findmyphone.httpvisitor.RequestQueue;

import java.util.Map;

/**
 * Created by wjc133.
 * Date: 2016/1/17
 * Time: 2:25
 * Description:Http请求工具
 */
public enum HttpUtils {
    INSTANCE;

    private RequestQueue requestQueue;

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public void setRequestQueue(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public <T> ServerResult<T> doSync(String url, Map<String, String> params) {
        return null;
    }
}
