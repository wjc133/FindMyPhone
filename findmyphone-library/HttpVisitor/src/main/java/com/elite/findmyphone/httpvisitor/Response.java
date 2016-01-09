package com.elite.findmyphone.httpvisitor;

import com.elite.findmyphone.httpvisitor.exception.HttpVisitorError;

/**
 * Created by wjc133.
 * Date: 2016/1/8
 * Time: 17:53
 * Description:
 */
public class Response<T> {
    public interface Listener<T> {
        public void onResponse(T response);
    }

    public interface ErrorListener {
        public void onErrorResponse(HttpVisitorError exception);
    }

//    public static
}
