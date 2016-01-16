package com.elite.findmyphone.core.utils;

import com.elite.findmyphone.httpvisitor.RequestQueue;

/**
 * Created by wjc133.
 * Date: 2016/1/17
 * Time: 2:25
 * Description:
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
}
